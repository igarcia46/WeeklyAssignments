import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class Exercise35_01 extends Application {

    private Connection conn;

    // main screen
    private final TextArea output = new TextArea();
    private final Button btnConnect = new Button("Connect to Database");
    private final Button btnBatch = new Button("Batch Update");
    private final Button btnNonBatch = new Button("Non-Batch Update");

    @Override
    public void start(Stage stage) {
        output.setEditable(false);
        output.setWrapText(true);
        output.setPrefRowCount(10);

        btnBatch.setDisable(true);
        btnNonBatch.setDisable(true);

        btnConnect.setOnAction(e -> showDBDialog(stage));

        btnBatch.setOnAction(e -> {
            if (conn == null) return;
            try {
                long ms = insertRows(true);
                log("Batch update completed");
                log("The elapsed time is " + ms + " ms\n");
            } catch (SQLException ex) {
                showError("Batch update failed", ex);
            }
        });

        btnNonBatch.setOnAction(e -> {
            if (conn == null) return;
            try {
                long ms = insertRows(false);
                log("Non-Batch update completed");
                log("The elapsed time is " + ms + " ms\n");
            } catch (SQLException ex) {
                showError("Non-batch update failed", ex);
            }
        });

        HBox top = new HBox(btnConnect);
        top.setAlignment(Pos.CENTER_RIGHT);

        HBox buttons = new HBox(10, btnBatch, btnNonBatch);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(8, 0, 0, 0));

        VBox root = new VBox(10, top, output, buttons);
        root.setPadding(new Insets(12));

        stage.setTitle("Exercise35_01");
        stage.setScene(new Scene(root, 420, 240));
        stage.show();

        // connect right away
        autoConnectSqlite();
    }

    private void autoConnectSqlite() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:exercise35_01.db");
            ensureTempTable();
            log("Connected to jdbc:sqlite:exercise35_01.db");
            btnBatch.setDisable(false);
            btnNonBatch.setDisable(false);
        } catch (SQLException ex) {
            // if fails, user can connect using manual connection with credds
            log("Auto-connect failed. Use 'Connect to Database'.");
        }
    }

    private void showDBDialog(Stage owner) {
        Dialog<DBInfo> dialog = new Dialog<>();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Connect to DB");

        ButtonType connectType = new ButtonType("Connect to DB", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(connectType, ButtonType.CANCEL);

        ComboBox<String> cbDriver = new ComboBox<>();
        cbDriver.getItems().addAll(
                "org.sqlite.JDBC",
                "com.mysql.cj.jdbc.Driver"
        );
        cbDriver.setEditable(true);
        cbDriver.getSelectionModel().selectFirst();

        ComboBox<String> cbUrl = new ComboBox<>();
        cbUrl.getItems().addAll(
                "jdbc:sqlite:exercise35_01.db",
                "jdbc:mysql://localhost/javabook"
        );
        cbUrl.setEditable(true);
        cbUrl.getSelectionModel().selectFirst();

        TextField tfUser = new TextField();
        PasswordField pfPass = new PasswordField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(12));

        grid.addRow(0, new Label("JDBC Driver"), cbDriver);
        grid.addRow(1, new Label("Database URL"), cbUrl);
        grid.addRow(2, new Label("Username"), tfUser);
        grid.addRow(3, new Label("Password"), pfPass);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(110);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(c1, c2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == connectType) {
                return new DBInfo(cbDriver.getEditor().getText(),
                        cbUrl.getEditor().getText(),
                        tfUser.getText(),
                        pfPass.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(info -> {
            try {
                // some drivers need explicit load
                if (info.driver != null && !info.driver.isBlank()) {
                    Class.forName(info.driver.trim());
                }

                // SQLite ignores username and password
                if (info.user != null && !info.user.isBlank()) {
                    conn = DriverManager.getConnection(info.url.trim(), info.user, info.pass);
                } else {
                    conn = DriverManager.getConnection(info.url.trim());
                }

                ensureTempTable();

                log("Connected to " + info.url.trim());
                btnBatch.setDisable(false);
                btnNonBatch.setDisable(false);

            } catch (Exception ex) {
                showError("Connection failed", ex);
            }
        });
    }

    private void ensureTempTable() throws SQLException {
        // if it fails because it exists, we ignore.
        try (Statement st = conn.createStatement()) {
            try {
                st.executeUpdate("CREATE TABLE Temp (num1 DOUBLE, num2 DOUBLE, num3 DOUBLE)");
            } catch (SQLException ignored) {
                // likely "table already exists"
            }
        }
    }

    private void clearTempTable() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM Temp");
        }
    }

    private long insertRows(boolean batch) throws SQLException {
        ensureTempTable();
        clearTempTable();

        String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";

        boolean oldAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);

        long start = System.nanoTime();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (batch) {
                for (int i = 0; i < 1000; i++) {
                    ps.setDouble(1, Math.random());
                    ps.setDouble(2, Math.random());
                    ps.setDouble(3, Math.random());
                    ps.addBatch();
                }
                ps.executeBatch();
            } else {
                for (int i = 0; i < 1000; i++) {
                    ps.setDouble(1, Math.random());
                    ps.setDouble(2, Math.random());
                    ps.setDouble(3, Math.random());
                    ps.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(oldAutoCommit);
        }

        long end = System.nanoTime();
        return (end - start) / 1_000_000;
    }

    private void log(String msg) {
        output.appendText(msg + "\n");
    }

    private void showError(String title, Exception ex) {
        log(title + ": " + ex.getMessage());

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(ex.toString());
        a.showAndWait();
    }

    @Override
    public void stop() {
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    private static class DBInfo {
        final String driver, url, user, pass;
        DBInfo(String driver, String url, String user, String pass) {
            this.driver = driver;
            this.url = url;
            this.user = user;
            this.pass = pass;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
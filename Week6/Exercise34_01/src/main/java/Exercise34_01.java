import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class Exercise34_01 extends Application {

    private Connection conn;

    TextField tfId = new TextField();
    TextField tfLast = new TextField();
    TextField tfFirst = new TextField();
    TextField tfMi = new TextField();
    TextField tfAddress = new TextField();
    TextField tfCity = new TextField();
    TextField tfState = new TextField();
    TextField tfPhone = new TextField();
    TextField tfEmail = new TextField();

    Label status = new Label();

    @Override
    public void start(Stage stage) throws Exception {

        connectDB();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        int r = 0;
        grid.addRow(r++, new Label("ID"), tfId);
        grid.addRow(r++, new Label("Last Name"), tfLast);
        grid.addRow(r++, new Label("First Name"), tfFirst);
        grid.addRow(r++, new Label("MI"), tfMi);
        grid.addRow(r++, new Label("Address"), tfAddress);
        grid.addRow(r++, new Label("City"), tfCity);
        grid.addRow(r++, new Label("State"), tfState);
        grid.addRow(r++, new Label("Phone"), tfPhone);
        grid.addRow(r++, new Label("Email"), tfEmail);

        Button btnView = new Button("View");
        Button btnInsert = new Button("Insert");
        Button btnUpdate = new Button("Update");
        Button btnClear = new Button("Clear");

        btnView.setOnAction(e -> view());
        btnInsert.setOnAction(e -> insert());
        btnUpdate.setOnAction(e -> update());
        btnClear.setOnAction(e -> clear());

        HBox buttons = new HBox(10, btnView, btnInsert, btnUpdate, btnClear);

        VBox root = new VBox(15, grid, buttons, status);
        root.setPadding(new Insets(15));

        stage.setScene(new Scene(root, 400, 450));
        stage.setTitle("Staff Database Editor");
        stage.show();
    }

    private void connectDB() throws Exception {
        Class.forName("org.sqlite.JDBC"); // driver
        conn = DriverManager.getConnection("jdbc:sqlite:staff.db");

        Statement st = conn.createStatement();
        st.executeUpdate("""
            CREATE TABLE IF NOT EXISTS Staff(
                id CHAR(9) PRIMARY KEY,
                lastName VARCHAR(15),
                firstName VARCHAR(15),
                mi CHAR(1),
                address VARCHAR(20),
                city VARCHAR(20),
                state CHAR(2),
                telephone CHAR(10),
                email VARCHAR(40)
            )
        """);
    }

    private void view() {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM Staff WHERE id=?");
            ps.setString(1, tfId.getText());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tfLast.setText(rs.getString("lastName"));
                tfFirst.setText(rs.getString("firstName"));
                tfMi.setText(rs.getString("mi"));
                tfAddress.setText(rs.getString("address"));
                tfCity.setText(rs.getString("city"));
                tfState.setText(rs.getString("state"));
                tfPhone.setText(rs.getString("telephone"));
                tfEmail.setText(rs.getString("email"));
                status.setText("Record found");
            } else {
                status.setText("Record not found");
            }
        } catch (Exception ex) {
            status.setText(ex.getMessage());
        }
    }

    private void insert() {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Staff VALUES(?,?,?,?,?,?,?,?,?)");

            fillStatement(ps);
            ps.executeUpdate();
            status.setText("Inserted.");
        } catch (Exception ex) {
            status.setText(ex.getMessage());
        }
    }

    private void update() {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                UPDATE Staff SET
                lastName=?,
                firstName=?,
                mi=?,
                address=?,
                city=?,
                state=?,
                telephone=?,
                email=?
                WHERE id=?
            """);

            ps.setString(1, tfLast.getText());
            ps.setString(2, tfFirst.getText());
            ps.setString(3, tfMi.getText());
            ps.setString(4, tfAddress.getText());
            ps.setString(5, tfCity.getText());
            ps.setString(6, tfState.getText());
            ps.setString(7, tfPhone.getText());
            ps.setString(8, tfEmail.getText());
            ps.setString(9, tfId.getText());

            int rows = ps.executeUpdate();
            status.setText(rows > 0 ? "Updated." : "Record not found.");
        } catch (Exception ex) {
            status.setText(ex.getMessage());
        }
    }

    private void clear() {
        tfLast.clear();
        tfFirst.clear();
        tfMi.clear();
        tfAddress.clear();
        tfCity.clear();
        tfState.clear();
        tfPhone.clear();
        tfEmail.clear();
        status.setText("");
    }

    private void fillStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, tfId.getText());
        ps.setString(2, tfLast.getText());
        ps.setString(3, tfFirst.getText());
        ps.setString(4, tfMi.getText());
        ps.setString(5, tfAddress.getText());
        ps.setString(6, tfCity.getText());
        ps.setString(7, tfState.getText());
        ps.setString(8, tfPhone.getText());
        ps.setString(9, tfEmail.getText());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
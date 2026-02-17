import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Exercise14_01 extends Application {

    @Override
    public void start(Stage stage) {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        ImageView iv1 = load("/images/flag1.gif");
        ImageView iv2 = load("/images/flag2.gif");
        ImageView iv3 = load("/images/flag6.gif");
        ImageView iv4 = load("/images/flag7.gif");

        grid.add(iv1, 0, 0);
        grid.add(iv2, 1, 0);
        grid.add(iv3, 0, 1);
        grid.add(iv4, 1, 1);

        Scene scene = new Scene(grid, 400, 250);
        stage.setTitle("Exercise14_01");
        stage.setScene(scene);
        stage.show();
    }

    private ImageView load(String path) {
        Image img = new Image(getClass().getResourceAsStream(path));
        ImageView iv = new ImageView(img);
        iv.setFitWidth(180);
        iv.setFitHeight(120);
        iv.setPreserveRatio(true);
        return iv;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

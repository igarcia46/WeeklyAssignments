import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Exercise15_07 extends Application {

    @Override
    public void start(Stage stage) {

        Pane pane = new Pane();

        // circle centered in window
        Circle circle = new Circle(150, 100, 50);
        circle.setFill(Color.WHITE); // default
        circle.setStroke(Color.BLACK);

        // mouse pressed --> black
        circle.setOnMousePressed(e -> circle.setFill(Color.BLACK));

        // mouse released --> white
        circle.setOnMouseReleased(e -> circle.setFill(Color.WHITE));

        pane.getChildren().add(circle);

        Scene scene = new Scene(pane, 300, 200);
        stage.setTitle("Exercise15_07");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class Exercise16_17 extends Application {

    @Override
    public void start(Stage stage) {

        Label text = new Label("Show Colors");
        text.setStyle("-fx-font-size: 24px;");

        // rgb sliders
        Slider red = new Slider(0, 255, 0);
        Slider green = new Slider(0, 255, 0);
        Slider blue = new Slider(0, 255, 0);
        Slider opacity = new Slider(0, 1, 1);

        // method to update color
        Runnable updateColor = () -> {
            text.setTextFill(Color.rgb(
                    (int) red.getValue(),
                    (int) green.getValue(),
                    (int) blue.getValue(),
                    opacity.getValue()
            ));
        };

        // rgb slider listeners
        red.valueProperty().addListener(e -> updateColor.run());
        green.valueProperty().addListener(e -> updateColor.run());
        blue.valueProperty().addListener(e -> updateColor.run());
        opacity.valueProperty().addListener(e -> updateColor.run());

        // layout
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setMaxSize(300, 200);

        pane.add(text, 1, 0);

        pane.add(new Label("Red"), 0, 1);
        pane.add(red, 1, 1);

        pane.add(new Label("Green"), 0, 2);
        pane.add(green, 1, 2);

        pane.add(new Label("Blue"), 0, 3);
        pane.add(blue, 1, 3);

        pane.add(new Label("Opacity"), 0, 4);
        pane.add(opacity, 1, 4);

        StackPane root = new StackPane(pane);
        Scene scene = new Scene(root, 350, 250);
        stage.setTitle("Exercise16_17");
        stage.setScene(scene);
        stage.show();

        updateColor.run(); // initialize color
    }

    public static void main(String[] args) {
        launch();
    }
}

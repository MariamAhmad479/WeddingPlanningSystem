package factory;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LightGUIFactory implements GUIFactory {

    @Override
    public Button createButton(String text) {
        Button button = new Button(text);

        button.setStyle(
            "-fx-background-color: #F18AAD;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-background-radius: 8;"
        );

        return button;
    }

    @Override
    public Label createLabel(String text) {
        Label label = new Label(text);

        label.setStyle(
            "-fx-text-fill: #333333;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;"
        );

        return label;
    }

    @Override
    public TextField createTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);

        field.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-radius: 5;"
        );

        return field;
    }

    @Override
    public VBox createCard(int spacing) {
        VBox box = new VBox(spacing);

        box.setPadding(new Insets(15));

        box.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #dddddd;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        );

        return box;
    }
}
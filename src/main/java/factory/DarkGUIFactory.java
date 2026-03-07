package factory;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DarkGUIFactory implements GUIFactory {

    @Override
    public Button createButton(String text) {
        Button button = new Button(text);

        button.setStyle(
            "-fx-background-color: #444444;" +
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
            "-fx-text-fill: white;" +
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
            "-fx-background-color: #2b2b2b;" +
            "-fx-text-fill: white;" +
            "-fx-border-color: #555555;" +
            "-fx-border-radius: 5;"
        );

        return field;
    }

    @Override
    public VBox createCard(int spacing) {
        VBox box = new VBox(spacing);

        box.setPadding(new Insets(15));

        box.setStyle(
            "-fx-background-color: #2b2b2b;" +
            "-fx-border-color: #444444;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        );

        return box;
    }
}
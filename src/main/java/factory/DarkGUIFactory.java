package factory;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DarkGUIFactory implements GUIFactory {

    @Override
    public Button createButton(String text) {
        Button btn = new Button(text);
        btn.getStyleClass().add("factory-button");
        return btn;
    }

    @Override
    public TextField createTextField(String promptText) {
        TextField tf = new TextField();
        tf.setPromptText(promptText);
        tf.getStyleClass().add("factory-textfield");
        return tf;
    }

    @Override
    public Label createLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: #E4E6EB; -fx-font-size: 14px;");
        return lbl;
    }

    @Override
    public VBox createCard(double spacing, Node... children) {
        VBox card = new VBox(spacing, children);
        card.setStyle("-fx-background-color: #242526; -fx-background-radius: 8; -fx-border-color: #3A3B3C; -fx-border-radius: 8; -fx-padding: 16;");
        return card;
    }
}

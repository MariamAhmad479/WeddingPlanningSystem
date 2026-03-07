package factory;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public interface GUIFactory {
    Button createButton(String text);
    TextField createTextField(String promptText);
    Label createLabel(String text);
    VBox createCard(double spacing, javafx.scene.Node... children);
}

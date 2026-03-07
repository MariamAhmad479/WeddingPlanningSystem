package factory;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public interface GUIFactory {

    Button createButton(String text);

    Label createLabel(String text);

    TextField createTextField(String prompt);

    VBox createCard(int spacing);
}
package app;

import boundary.GehazListUI;
import boundary.GuestListUI;
import factory.GUIFactory;
import factory.ThemeManager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        buildMainScreen(primaryStage);
    }

    private void buildMainScreen(Stage primaryStage) {
        GUIFactory factory = ThemeManager.getFactory();

        Label title = factory.createLabel("Ayza Atgawez");
        title.setStyle(title.getStyle() + "-fx-font-size: 28px; -fx-font-weight: bold;");

        Button themeButton = factory.createButton("Switch Theme");
        themeButton.setPrefWidth(240);
        themeButton.setPrefHeight(50);

        Button gehazBtn = factory.createButton("My Gehaz");
        gehazBtn.setPrefWidth(240);
        gehazBtn.setPrefHeight(50);

        Button guestBtn = factory.createButton("Guest List");
        guestBtn.setPrefWidth(240);
        guestBtn.setPrefHeight(50);

        themeButton.setOnAction(e -> {
            if (ThemeManager.isDarkTheme()) {
                ThemeManager.setLightTheme();
            } else {
                ThemeManager.setDarkTheme();
            }
            buildMainScreen(primaryStage);
        });

        gehazBtn.setOnAction(e -> {
            GehazListUI gehazUI = new GehazListUI();
            gehazUI.start(primaryStage);
        });

        guestBtn.setOnAction(e -> {
            GuestListUI guestUI = new GuestListUI();
            guestUI.start(primaryStage);
        });

        VBox root = factory.createCard(20);
        root.getChildren().addAll(title, themeButton, gehazBtn, guestBtn);
        root.setAlignment(Pos.CENTER);

        if (ThemeManager.isDarkTheme()) {
            root.setStyle(root.getStyle() + "-fx-background-color: #1e1e1e;");
        } else {
            root.setStyle(root.getStyle() + "-fx-background-color: #F9F6F1;");
        }

        Scene scene = new Scene(root, 600, 450);

        primaryStage.setTitle("Ayza Atgawez");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
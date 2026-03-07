package app;

import boundary.GehazListUI;
import boundary.GuestListUI;
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

        // ===== Title =====
        Label title = new Label("Ayza Atgawez");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // ===== Buttons =====
        Button gehazBtn = new Button("My Gehaz");
        gehazBtn.setPrefWidth(240);
        gehazBtn.setPrefHeight(50);
        gehazBtn.setStyle("-fx-font-size: 16px;");

        Button guestBtn = new Button("Guest List");
        guestBtn.setPrefWidth(240);
        guestBtn.setPrefHeight(50);
        guestBtn.setStyle("-fx-font-size: 16px;");

        // ===== Button Actions =====
        gehazBtn.setOnAction(e -> {
            GehazListUI gehazUI = new GehazListUI();
            gehazUI.start(primaryStage);
        });

        guestBtn.setOnAction(e -> {
            GuestListUI guestUI = new GuestListUI();
            guestUI.start(primaryStage);
        });

        // ===== Layout =====
        VBox root = new VBox(20, title, gehazBtn, guestBtn);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #F9F6F1;");

        Scene scene = new Scene(root, 600, 450);

        primaryStage.setTitle("Ayza Atgawez");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

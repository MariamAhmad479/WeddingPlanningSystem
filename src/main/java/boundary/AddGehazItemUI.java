package boundary;

import controller.GehazItemController;
import entity.GehazItemData;
import enumeration.GehazCategory;
import enumeration.GehazStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddGehazItemUI {

    private final GehazItemController controller;
    private final String brideId;
    private boolean saved = false;

    public AddGehazItemUI(GehazItemController controller, String brideId) {
        this.controller = controller;
        this.brideId = brideId;
    }

    public boolean show(Stage owner) {
        saved = false;

        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Add New Item");

        // ===== Header =====
        Label header = new Label("Add New Item");
        header.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #111;");

        // ===== Fields =====
        TextField nameField = new TextField();
        nameField.setPromptText("Item Name");

        ComboBox<GehazCategory> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll(GehazCategory.values());
        categoryBox.setPromptText("Category");

        ComboBox<GehazStatus> statusBox = new ComboBox<>();
        statusBox.getItems().addAll(GehazStatus.values());
        statusBox.setPromptText("Status");

        TextField costField = new TextField();
        costField.setPromptText("Cost (EGP)");

        Label validation = new Label("");
        validation.setStyle("-fx-text-fill: #B00020; -fx-font-weight: bold;");

        VBox form = new VBox(12,
                labeledField("Item Name", nameField),
                labeledField("Category", categoryBox),
                labeledField("Status", statusBox),
                labeledField("Cost (EGP)", costField),
                validation
        );

        // ===== Buttons =====
        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("btn-outline");
        cancel.setOnAction(e -> dialog.close());

        Button saveBtn = new Button("Save");
        saveBtn.getStyleClass().add("btn-primary");

        saveBtn.setOnAction(e -> {

            validation.setText("");

            String name = nameField.getText().trim();
            GehazCategory category = categoryBox.getValue();
            GehazStatus status = statusBox.getValue();

            if (name.isEmpty()) {
                validation.setText("Item name is required.");
                return;
            }
            if (category == null) {
                validation.setText("Please select a category.");
                return;
            }
            if (status == null) {
                validation.setText("Please select a status.");
                return;
            }

            // clean cost input
            String raw = costField.getText().trim();
            raw = raw.replace(",", "");
            raw = raw.replaceAll("[^0-9.]", "");

            double cost;
            try {
                cost = Double.parseDouble(raw);
                if (cost < 0) throw new NumberFormatException();
            } catch (Exception ex) {
                validation.setText("Please enter a valid cost (numbers only).");
                return;
            }

            GehazItemData data = new GehazItemData(name, category, status, cost);

            try {
                controller.addGehazItem(brideId, data);
                saved = true;

                dialog.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                validation.setText("Failed to save item. Please try again.");
            }
        });

        HBox buttons = new HBox(10, cancel, saveBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        // ===== Card styling =====
        VBox card = new VBox(16, header, form, buttons);
        card.setPadding(new Insets(20));
        card.setMaxWidth(520);
        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-border-color: #E7DCC7;" +
                "-fx-border-width: 1;"
        );

        StackPane root = new StackPane(card);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: rgba(0,0,0,0.12);");

        Scene scene = new Scene(root, 640, 520);
        scene.getStylesheets().add(getClass().getResource("guestlist.css").toExternalForm());

        dialog.setScene(scene);
        dialog.showAndWait();

        return saved;
    }

    private VBox labeledField(String label, javafx.scene.Node field) {
        Label l = new Label(label);
        l.setStyle("-fx-font-weight: bold; -fx-text-fill: #111;");
        return new VBox(6, l, field);
    }

    public void showSuccess(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.showAndWait();
    }

    public void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.showAndWait();
    }
}

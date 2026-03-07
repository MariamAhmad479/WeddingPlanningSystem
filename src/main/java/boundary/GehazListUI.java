package boundary;

import controller.GehazItemController;
import DAO.GehazItemAccessor;
import entity.GehazItem;
import entity.GehazItemData;
import entity.GehazSummary;
import enumeration.GehazCategory;
import enumeration.GehazStatus;
import factory.ThemeManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GehazListUI {

    private final GehazItemController controller;
    private final GehazItemAccessor accessor;
    private String currentBrideId = "BRIDE_001";

    private final ObservableList<GehazItem> items = FXCollections.observableArrayList();
    private TableView<GehazItem> table;

    private Label progressValue;
    private Label budgetValue;
    private Label purchasedValue;
    private Label remainingValue;

    private HBox banner;
    private Label bannerText;
    private boolean bannerVisible = false;

    public GehazListUI() {
        controller = new GehazItemController();
        controller.setCurrentBride(currentBrideId);

        accessor = new factory.SqlDAOFactory().getGehazItemDAO();
    }

    public void start(Stage stage) {
        boolean dark = ThemeManager.isDarkTheme();

        HBox navbar = buildNavbar("My Gehaz");

        Label title = new Label("My Gehaz");
        title.setStyle(
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "#4A1F25;")
        );

        progressValue = new Label("0%");
        budgetValue = new Label("0 EGP");
        purchasedValue = new Label("0 EGP");
        remainingValue = new Label("0 EGP");

        HBox kpis = new HBox(
                gehazCard("Progress", progressValue),
                gehazCard("Budget", budgetValue),
                gehazCard("Total Purchased", purchasedValue),
                gehazCard("Remaining Budget", remainingValue)
        );
        kpis.setSpacing(12);

        Button viewRec = new Button("View Recommendations");
        stylePrimaryButton(viewRec, dark);

        Button addItemBtn = new Button("Add Item");
        stylePrimaryButton(addItemBtn, dark);

        addItemBtn.setOnAction(e -> {
            AddGehazItemUI addDialog = new AddGehazItemUI(controller, currentBrideId);
            boolean saved = addDialog.show(stage);
            if (saved) refresh();
        });

        HBox actionsRow = new HBox(10, viewRec, new Region(), addItemBtn);
        HBox.setHgrow(actionsRow.getChildren().get(1), Priority.ALWAYS);
        actionsRow.setAlignment(Pos.CENTER_LEFT);

        bannerText = new Label("");
        bannerText.setStyle(
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "#4A1F25;")
        );

        banner = new HBox(bannerText);
        banner.setPadding(new Insets(10));
        banner.setStyle(
                "-fx-background-color: " + (dark ? "#3A252A;" : "#F7E7CC;") +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-border-color: " + (dark ? "#5B3A40;" : "#D9C8B0;")
        );
        banner.setVisible(false);
        banner.setManaged(false);

        viewRec.setOnAction(e -> toggleRecommendations());

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(items);
        table.setEditable(true);
        styleTable(table, dark);

        TableColumn<GehazItem, Number> numCol = new TableColumn<>("");
        numCol.setCellValueFactory(col -> new javafx.beans.property.ReadOnlyObjectWrapper<>(
                table.getItems().indexOf(col.getValue()) + 1
        ));
        numCol.setMaxWidth(45);
        numCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<GehazItem, String> itemCol = new TableColumn<>("Item");
        itemCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));

        TableColumn<GehazItem, GehazCategory> catCol = new TableColumn<>("Category");
        catCol.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getCategory()));
        catCol.setCellFactory(col -> new TableCell<>() {
            private final Label pill = new Label();

            {
                setAlignment(Pos.CENTER);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                pill.setStyle(
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 14;" +
                        "-fx-padding: 4 14 4 14;" +
                        "-fx-text-fill: white;"
                );
            }

            @Override
            protected void updateItem(GehazCategory category, boolean empty) {
                super.updateItem(category, empty);

                if (empty || category == null) {
                    setGraphic(null);
                    return;
                }

                boolean essential = category == GehazCategory.ESSENTIAL;

                pill.setText(essential ? "Essential" : "Optional");
                pill.setStyle(
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 14;" +
                        "-fx-padding: 4 14 4 14;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: " + (essential ? "#8799E3" : "#E387BE") + ";"
                );

                setGraphic(pill);
            }
        });

        TableColumn<GehazItem, GehazStatus> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStatus()));
        statusCol.setEditable(true);

        statusCol.setCellFactory(col -> new TableCell<>() {
            private final ComboBox<GehazStatus> combo =
                    new ComboBox<>(FXCollections.observableArrayList(GehazStatus.values()));

            private final Label pill = new Label();

            {
                pill.setStyle(
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;" +
                        "-fx-padding: 4 12 4 12;"
                );

                combo.setMaxWidth(Double.MAX_VALUE);

                combo.setOnAction(e -> {
                    GehazItem item = getTableView().getItems().get(getIndex());
                    GehazStatus newStatus = combo.getValue();

                    if (item != null && newStatus != null) {
                        GehazItemData updatedData = new GehazItemData(
                                item.getName(),
                                item.getCategory(),
                                newStatus,
                                item.getCost()
                        );

                        controller.submitEditItem(item.getItemId(), updatedData);
                        commitEdit(newStatus);
                        refresh();
                    }
                });

                setAlignment(Pos.CENTER);
            }

            @Override
            public void startEdit() {
                super.startEdit();
                if (getItem() == null) return;

                combo.setValue(getItem());
                setGraphic(combo);
                setText(null);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                showPill(getItem());
            }

            @Override
            protected void updateItem(GehazStatus status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                if (isEditing()) {
                    combo.setValue(status);
                    setGraphic(combo);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    showPill(status);
                }
            }

            private void showPill(GehazStatus status) {
                boolean purchased = status == GehazStatus.PURCHASED;

                pill.setText(purchased ? "Purchased" : "Not Purchased");
                pill.setStyle(
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;" +
                        "-fx-padding: 4 12 4 12;" +
                        "-fx-text-fill: #111;" +
                        "-fx-background-color: " + (purchased ? "#87E395" : "#DDB2B9") + ";"
                );

                setGraphic(pill);
                setText(null);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        });

        TableColumn<GehazItem, String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory(d -> new SimpleStringProperty(formatEGP(d.getValue().getCost())));

        TableColumn<GehazItem, String> editCol = new TableColumn<>("");
        editCol.setCellFactory(col -> new TableCell<>() {
            private final Button edit = new Button("✎");

            {
                styleOutlineButton(edit, dark);
                edit.setOnAction(e -> {
                    GehazItem item = getTableView().getItems().get(getIndex());
                    openEditItemDialog(stage, item);
                });
            }

            @Override
            protected void updateItem(String it, boolean empty) {
                super.updateItem(it, empty);
                if (empty) setGraphic(null);
                else setGraphic(edit);
            }
        });
        editCol.setMaxWidth(70);

        TableColumn<GehazItem, String> deleteCol = new TableColumn<>("");
        deleteCol.setCellFactory(col -> new TableCell<>() {
            private final Button del = new Button("🗑");

            {
                styleOutlineButton(del, dark);
                del.setOnAction(e -> {
                    GehazItem item = getTableView().getItems().get(getIndex());
                    controller.deleteItem(item.getItemId());
                    refresh();
                });
            }

            @Override
            protected void updateItem(String it, boolean empty) {
                super.updateItem(it, empty);
                if (empty) setGraphic(null);
                else setGraphic(del);
            }
        });
        deleteCol.setMaxWidth(70);

        table.getColumns().addAll(numCol, itemCol, catCol, statusCol, costCol, editCol, deleteCol);

        VBox page = new VBox(12, title, kpis, actionsRow, banner, table);
        page.setPadding(new Insets(20));
        page.setStyle("-fx-background-color: " + (dark ? "#1E1517;" : "#F6F1EB;"));

        VBox footer = buildFooter();

        VBox root = new VBox(navbar, page, footer);
        root.setStyle("-fx-background-color: " + (dark ? "#1E1517;" : "#F6F1EB;"));

        Scene scene = new Scene(root, 900, 650);

        stage.setTitle("Ayza Atgawez - My Gehaz");
        stage.setScene(scene);
        stage.show();

        refresh();
    }

    private void refresh() {
        List<GehazItem> list = controller.requestGehazData(currentBrideId);
        items.setAll(list);
        table.refresh();

        GehazSummary summary = computeSummary(list);

        progressValue.setText(summary.progressPercentage + "%");
        budgetValue.setText(formatEGP(summary.totalBudget));
        purchasedValue.setText(formatEGP(summary.totalPurchased));
        remainingValue.setText(formatEGP(summary.remainingBudget));

        if (bannerVisible) {
            bannerText.setText(buildRecommendationText(items));
        }
    }

    private VBox gehazCard(String labelText, Label valueLabel) {
        boolean dark = ThemeManager.isDarkTheme();

        Label label = new Label(labelText);
        label.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-text-fill: " + (dark ? "#D4A85A;" : "#C49A4A;")
        );

        valueLabel.setStyle(
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "#111111;")
        );

        VBox card = new VBox(6, label, valueLabel);
        card.setPadding(new Insets(16));
        card.setMinWidth(190);
        card.setStyle(
                "-fx-background-color: " + (dark ? "#2A1D20;" : "#F9F6F1;") +
                "-fx-border-color: " + (dark ? "#5B3A40;" : "#D9C8B0;") +
                "-fx-border-radius: 12;" +
                "-fx-background-radius: 12;"
        );
        return card;
    }

    private String formatEGP(double amount) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        nf.setMaximumFractionDigits(0);
        return nf.format(amount) + " EGP";
    }

    private GehazSummary computeSummary(List<GehazItem> list) {
        GehazSummary s = new GehazSummary();

        double budget = accessor.getBudget(currentBrideId);
        if (budget == 0) budget = 100000;

        double purchasedCost = 0;
        int purchasedCount = 0;

        for (GehazItem it : list) {
            if (it.getStatus() == GehazStatus.PURCHASED) {
                purchasedCost += it.getCost();
                purchasedCount++;
            }
        }

        s.totalBudget = budget;
        s.totalPurchased = purchasedCost;
        s.remainingBudget = Math.max(0, budget - purchasedCost);

        int totalItems = list.size();
        s.progressPercentage = (totalItems == 0) ? 0 : (int) Math.round((purchasedCount * 100.0) / totalItems);

        return s;
    }

    private HBox buildNavbar(String active) {
        boolean dark = ThemeManager.isDarkTheme();

        HBox navbar = new HBox(18);
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(18, 24, 18, 24));
        navbar.setStyle("-fx-background-color: " + (dark ? "#EC008C;" : "#F18AAD;"));

        Label navAppointments = navLabel("Appointments", false, dark);
        Label navGehaz = navLabel("My Gehaz", active.equals("My Gehaz"), dark);
        Label navLogo = navLogo("Ayza Atgawez", dark);
        Label navGuestList = navLabel("Guest List", active.equals("Guest List"), dark);
        Label navWedding = navLabel("My Wedding", false, dark);

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        navbar.getChildren().addAll(
                navAppointments, navGehaz, leftSpacer, navLogo, rightSpacer, navGuestList, navWedding
        );
        return navbar;
    }

    private VBox buildFooter() {
        boolean dark = ThemeManager.isDarkTheme();

        HBox footer = new HBox(60);
        footer.setPadding(new Insets(30));
        footer.setStyle("-fx-background-color: " + (dark ? "#2A1D20;" : "#6E2430;"));

        Label aboutTitle = footerTitle("About Us", dark);
        Label aboutText = footerText(
                "Ayza Atgawez is your complete wedding\n" +
                        "planning companion, making your journey\n" +
                        "to the big day simple and stress-free.",
                dark
        );
        VBox col1 = new VBox(8, aboutTitle, aboutText);

        Label quickTitle = footerTitle("Quick Links", dark);
        VBox quickLinks = new VBox(6,
                footerLink("Appointments", dark),
                footerLink("My Gehaz", dark),
                footerLink("Guest List", dark),
                footerLink("Wedding Checklist", dark)
        );
        VBox col2 = new VBox(8, quickTitle, quickLinks);

        Label servicesTitle = footerTitle("Services", dark);
        VBox servicesLinks = new VBox(6,
                footerLink("Booking Vendors", dark),
                footerLink("Guest List & RSVP", dark),
                footerLink("Wedding Checklist", dark),
                footerLink("Gehaz Tracking", dark)
        );
        VBox col3 = new VBox(8, servicesTitle, servicesLinks);

        Label contactTitle = footerTitle("Contact Us", dark);
        Label contactText = footerText(
                "Email: info@ayzaatgawez.com\n" +
                        "Phone: +20 123 456 7890\n" +
                        "Address: Cairo, Egypt",
                dark
        );
        VBox col4 = new VBox(8, contactTitle, contactText);

        footer.getChildren().addAll(col1, col2, col3, col4);

        Label bottom = new Label("© 2024 Ayza Atgawez. All rights reserved.");
        bottom.setStyle(
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "white;") +
                "-fx-font-size: 13px;"
        );

        HBox bottomRow = new HBox(bottom);
        bottomRow.setAlignment(Pos.CENTER);
        bottomRow.setPadding(new Insets(8));
        bottomRow.setStyle("-fx-background-color: " + (dark ? "#2A1D20;" : "#6E2430;"));

        return new VBox(10, footer, bottomRow);
    }

    private Label footerLink(String text, boolean dark) {
        Label l = new Label(text);
        l.setStyle(
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "white;") +
                "-fx-font-size: 14px;"
        );
        return l;
    }

    private Label footerTitle(String text, boolean dark) {
        Label l = new Label(text);
        l.setStyle(
                "-fx-text-fill: " + (dark ? "#FFFFFF;" : "white;") +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;"
        );
        return l;
    }

    private Label footerText(String text, boolean dark) {
        Label l = new Label(text);
        l.setStyle(
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "white;") +
                "-fx-font-size: 14px;"
        );
        return l;
    }

    private Label navLabel(String text, boolean active, boolean dark) {
        Label l = new Label(text);
        l.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + (dark ? "#FFF4F7;" : "#4A1F25;") +
                (active ? "-fx-underline: true;" : "")
        );
        return l;
    }

    private Label navLogo(String text, boolean dark) {
        Label l = new Label(text);
        l.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + (dark ? "#FFF4F7;" : "#4A1F25;")
        );
        return l;
    }

    private void stylePrimaryButton(Button button, boolean dark) {
        button.setStyle(
                "-fx-background-color: " + (dark ? "#7E3140;" : "#6E2430;") +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 12;" +
                "-fx-cursor: hand;"
        );
    }

    private void styleOutlineButton(Button button, boolean dark) {
        button.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "#6E2430;") +
                "-fx-border-color: " + (dark ? "#B57A88;" : "#8A4A58;") +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );
    }

    private void styleTable(TableView<?> table, boolean dark) {
        table.setStyle(
                "-fx-background-color: " + (dark ? "#2A1D20;" : "#F9F6F1;") +
                "-fx-control-inner-background: " + (dark ? "#2A1D20;" : "#F9F6F1;") +
                "-fx-table-cell-border-color: " + (dark ? "#5B3A40;" : "#E7DCC7;") +
                "-fx-padding: 6;"
        );
    }

    private void openEditItemDialog(Stage owner, GehazItem item) {
        boolean dark = ThemeManager.isDarkTheme();

        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Edit Item");

        Label header = new Label("Edit Item");
        header.setStyle(
                "-fx-font-size: 22px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "#111111;")
        );

        TextField nameField = new TextField(item.getName());

        ComboBox<GehazCategory> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll(GehazCategory.values());
        categoryBox.setValue(item.getCategory());

        ComboBox<GehazStatus> statusBox = new ComboBox<>();
        statusBox.getItems().addAll(GehazStatus.values());
        statusBox.setValue(item.getStatus());

        TextField costField = new TextField(String.valueOf(item.getCost()));

        Label validation = new Label("");
        validation.setStyle("-fx-text-fill: #B00020; -fx-font-weight: bold;");

        VBox form = new VBox(12,
                labeledField("Item Name", nameField),
                labeledField("Category", categoryBox),
                labeledField("Status", statusBox),
                labeledField("Cost (EGP)", costField),
                validation
        );

        Button cancel = new Button("Cancel");
        styleOutlineButton(cancel, dark);
        cancel.setOnAction(e -> dialog.close());

        Button save = new Button("Save");
        stylePrimaryButton(save, dark);

        save.setOnAction(e -> {
            String name = nameField.getText().trim();
            GehazCategory cat = categoryBox.getValue();
            GehazStatus st = statusBox.getValue();

            if (name.isEmpty()) {
                validation.setText("Please enter item name.");
                return;
            }
            if (cat == null) {
                validation.setText("Please select a category.");
                return;
            }
            if (st == null) {
                validation.setText("Please select a status.");
                return;
            }

            double cost;
            try {
                cost = Double.parseDouble(costField.getText().trim());
                if (cost < 0) throw new NumberFormatException();
            } catch (Exception ex) {
                validation.setText("Please enter a valid positive cost.");
                return;
            }

            item.updateDetails(name, cat, cost);
            item.setStatus(st);

            GehazItemData updatedData = new GehazItemData(
                    nameField.getText(),
                    categoryBox.getValue(),
                    statusBox.getValue(),
                    Double.parseDouble(costField.getText())
            );

            controller.submitEditItem(item.getItemId(), updatedData);

            dialog.close();
            refresh();
        });

        HBox buttons = new HBox(10, cancel, save);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        VBox content = new VBox(16, header, form, buttons);
        content.setPadding(new Insets(20));
        content.setStyle(
                "-fx-background-color: " + (dark ? "#2A1D20;" : "white;") +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-border-color: " + (dark ? "#5B3A40;" : "#E7DCC7;")
        );

        Scene scene = new Scene(new StackPane(content), 520, 460);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private VBox labeledField(String labelText, javafx.scene.Node field) {
        boolean dark = ThemeManager.isDarkTheme();

        Label label = new Label(labelText);
        label.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "#111111;")
        );

        return new VBox(6, label, field);
    }

    private void toggleRecommendations() {
        bannerVisible = !bannerVisible;

        if (bannerVisible) {
            bannerText.setText(buildRecommendationText(items));
            banner.setVisible(true);
            banner.setManaged(true);
        } else {
            banner.setVisible(false);
            banner.setManaged(false);
        }
    }

    private String buildRecommendationText(List<GehazItem> list) {
        if (list == null || list.isEmpty()) {
            return "You haven’t added any items yet. Add essentials first to start tracking progress.";
        }

        int essentialTotal = 0;
        int essentialPurchased = 0;
        int essentialRemaining = 0;

        int optionalTotal = 0;
        int optionalPurchased = 0;

        for (GehazItem it : list) {
            if (it.getCategory() == null || it.getStatus() == null) continue;

            boolean purchased = it.getStatus() == GehazStatus.PURCHASED;

            if (it.getCategory().name().equalsIgnoreCase("ESSENTIAL")) {
                essentialTotal++;
                if (purchased) essentialPurchased++;
                else essentialRemaining++;
            } else {
                optionalTotal++;
                if (purchased) optionalPurchased++;
            }
        }

        if (essentialTotal > 0) {
            int pct = (int) Math.round((essentialPurchased * 100.0) / essentialTotal);

            if (essentialRemaining > 0) {
                return "Essentials are " + pct + "% done. You have " + essentialRemaining + " essential item(s) remaining.";
            } else {
                return "All essentials are completed! You can now focus on optional items (" +
                        optionalPurchased + "/" + optionalTotal + " purchased).";
            }
        }

        return "No essentials found yet. Add essential items first to get a meaningful progress tracking.";
    }
}
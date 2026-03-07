package boundary;

import controller.GehazItemController;
import DAO.GehazItemAccessor;
import entity.GehazItem;
import entity.GehazItemData;
import entity.GehazSummary;
import enumeration.GehazCategory;
import enumeration.GehazStatus;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
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

        // ===== Navbar====
        HBox navbar = buildNavbar("My Gehaz");

        // ===== Title =====
        Label title = new Label("My Gehaz");
        title.getStyleClass().add("page-title");

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

        // ===== Buttons row =====
        Button viewRec = new Button("View Recommendations");
        viewRec.getStyleClass().add("btn-primary");

        Button addItemBtn = new Button("Add Item");
        addItemBtn.getStyleClass().add("btn-primary");
        addItemBtn.setOnAction(e -> {
            AddGehazItemUI addDialog = new AddGehazItemUI(controller, currentBrideId);
            boolean saved = addDialog.show(stage);
            if (saved) refresh();
        });

        HBox actionsRow = new HBox(10, viewRec, new Region(), addItemBtn);
        HBox.setHgrow(actionsRow.getChildren().get(1), Priority.ALWAYS);
        actionsRow.setAlignment(Pos.CENTER_LEFT);

        // ===== Info banner =====
        bannerText = new Label("");
        bannerText.getStyleClass().add("info-banner-text");

        banner = new HBox(bannerText);
        banner.getStyleClass().add("info-banner");
        banner.setVisible(false);
        banner.setManaged(false); 

        viewRec.setOnAction(e -> toggleRecommendations());


        // ===== Table =====
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(items);

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
    statusCol.setCellValueFactory(data ->
            new SimpleObjectProperty<>(data.getValue().getStatus())
    );

    table.setEditable(true);
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
        costCol.setCellValueFactory(d -> new SimpleStringProperty(
                formatEGP(d.getValue().getCost())
        ));

        TableColumn<GehazItem, String> editCol = new TableColumn<>("");
        editCol.setCellFactory(col -> new TableCell<>() {
            private final Button edit = new Button("✎");
            {
                edit.getStyleClass().add("btn-outline");
                edit.setOnAction(e -> {
                    GehazItem item = getTableView().getItems().get(getIndex());
                    openEditItemDialog(stage, item); // we’ll add this function below
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
                del.getStyleClass().add("btn-outline");
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



        // ===== Page layout =====
        VBox page = new VBox(12, title, kpis, actionsRow, banner, table);
        page.getStyleClass().add("page");

        // Footer 
        VBox footer = buildFooter();

        VBox root = new VBox(navbar, page, footer);

        Scene scene = new Scene(root, 900, 650);
        scene.getStylesheets().add(getClass().getResource("guestlist.css").toExternalForm());

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

    // ===== helpers =====
    private VBox gehazCard(String labelText, Label valueLabel) {
        Label label = new Label(labelText);
        label.getStyleClass().add("kpi-label");
        valueLabel.getStyleClass().add("kpi-value");

        VBox card = new VBox(6, label, valueLabel);
        card.getStyleClass().add("card-box");
        card.setMinWidth(190);
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
        if (budget == 0) budget = 100000; // demo default

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
        HBox navbar = new HBox(18);
        navbar.getStyleClass().add("navbar");
        navbar.setAlignment(Pos.CENTER_LEFT);

        Label navAppointments = new Label("Appointments");
        navAppointments.getStyleClass().add("nav-item");

        Label navGehaz = new Label("My Gehaz");
        navGehaz.getStyleClass().add(active.equals("My Gehaz") ? "nav-item-active" : "nav-item");

        Label navLogo = new Label("Ayza Atgawez");
        navLogo.getStyleClass().add("nav-center-logo");

        Label navGuestList = new Label("Guest List");
        navGuestList.getStyleClass().add(active.equals("Guest List") ? "nav-item-active" : "nav-item");

        Label navWedding = new Label("My Wedding");
        navWedding.getStyleClass().add("nav-item");

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
        HBox footer = new HBox(60);
        footer.getStyleClass().add("footer");

        Label aboutTitle = new Label("About Us");
        aboutTitle.getStyleClass().add("footer-title");

        Label aboutText = new Label(
                "Ayza Atgawez is your complete wedding\n" +
                "planning companion, making your journey\n" +
                "to the big day simple and stress-free."
        );
        aboutText.getStyleClass().add("footer-text");
        VBox col1 = new VBox(8, aboutTitle, aboutText);

        Label quickTitle = new Label("Quick Links");
        quickTitle.getStyleClass().add("footer-title");
        VBox quickLinks = new VBox(6,
                footerLink("Appointments"),
                footerLink("My Gehaz"),
                footerLink("Guest List"),
                footerLink("Wedding Checklist")
        );
        VBox col2 = new VBox(8, quickTitle, quickLinks);

        Label servicesTitle = new Label("Services");
        servicesTitle.getStyleClass().add("footer-title");
        VBox servicesLinks = new VBox(6,
                footerLink("Booking Vendors"),
                footerLink("Guest List & RSVP"),
                footerLink("Wedding Checklist"),
                footerLink("Gehaz Tracking")
        );
        VBox col3 = new VBox(8, servicesTitle, servicesLinks);

        Label contactTitle = new Label("Contact Us");
        contactTitle.getStyleClass().add("footer-title");
        Label contactText = new Label(
                "Email: info@ayzaatgawez.com\n" +
                "Phone: +20 123 456 7890\n" +
                "Address: Cairo, Egypt"
        );
        contactText.getStyleClass().add("footer-text");
        VBox col4 = new VBox(8, contactTitle, contactText);

        footer.getChildren().addAll(col1, col2, col3, col4);

        Label bottom = new Label("© 2024 Ayza Atgawez. All rights reserved.");
        bottom.getStyleClass().add("footer-bottom");

        HBox bottomRow = new HBox(bottom);
        bottomRow.setAlignment(Pos.CENTER);

        VBox footerBlock = new VBox(10, footer, bottomRow);
        footerBlock.getStyleClass().add("footer");
        return footerBlock;
    }

    private Label footerLink(String text) {
        Label l = new Label(text);
        l.getStyleClass().add("footer-link");
        return l;
    }

    private void openEditItemDialog(Stage owner, GehazItem item) {

    Stage dialog = new Stage();
    dialog.initOwner(owner);
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.setTitle("Edit Item");

    Label header = new Label("Edit Item");
    header.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #111;");

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
    cancel.getStyleClass().add("btn-outline");
    cancel.setOnAction(e -> dialog.close());

    Button save = new Button("Save");
    save.getStyleClass().add("btn-primary");

    save.setOnAction(e -> {
        String name = nameField.getText().trim();
        GehazCategory cat = categoryBox.getValue();
        GehazStatus st = statusBox.getValue();

        if (name.isEmpty()) { validation.setText("Please enter item name."); return; }
        if (cat == null) { validation.setText("Please select a category."); return; }
        if (st == null) { validation.setText("Please select a status."); return; }

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
    content.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #E7DCC7;");

    Scene scene = new Scene(new StackPane(content), 520, 460);
    scene.getStylesheets().add(getClass().getResource("guestlist.css").toExternalForm());
    dialog.setScene(scene);

    dialog.showAndWait();
}

    private VBox labeledField(String labelText, javafx.scene.Node field) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #111;");
        VBox box = new VBox(6, label, field);
        return box;
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

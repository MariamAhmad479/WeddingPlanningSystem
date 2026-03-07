package boundary;

import controller.GuestListController;
import entity.Guest;
import entity.GuestData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import factory.GUIFactory;
import factory.LightGUIFactory;

public class GuestListUI {

    private GuestListController controller;
    private TableView<Guest> table;
    private ObservableList<Guest> guests;

    private Label totalGuestsValue;
    private Label attendingValue;
    private Label declinedValue;
    private Label pendingValue;
    private GUIFactory guiFactory;

    public GuestListUI() {
        controller = new GuestListController();
        controller.setCurrentBride("BRIDE_001"); // simulated login
        guests = FXCollections.observableArrayList();
        guiFactory = new LightGUIFactory();
    }

    public void start(Stage stage) {

    HBox navbar = new HBox(18);
    navbar.getStyleClass().add("navbar");
    navbar.setAlignment(Pos.CENTER_LEFT);

    Label navAppointments = new Label("Appointments");
    navAppointments.getStyleClass().add("nav-item");

    Label navGehaz = new Label("My Gehaz");
    navGehaz.getStyleClass().add("nav-item");

    Label navLogo = new Label("Ayza Atgawez");
    navLogo.getStyleClass().add("nav-center-logo");

    Label navGuestList = new Label("Guest List");
    navGuestList.getStyleClass().add("nav-item-active");

    Label navWedding = new Label("My Wedding");
    navWedding.getStyleClass().add("nav-item");

    Region leftSpacer = new Region();
    Region rightSpacer = new Region();
    HBox.setHgrow(leftSpacer, Priority.ALWAYS);
    HBox.setHgrow(rightSpacer, Priority.ALWAYS);

    navbar.getChildren().addAll(
            navAppointments,
            navGehaz,
            leftSpacer,
            navLogo,
            rightSpacer,
            navGuestList,
            navWedding
    );

        Label pageTitle = new Label("Guest List");
        pageTitle.getStyleClass().add("page-title");

        totalGuestsValue = new Label("0");
        attendingValue = new Label("0");
        declinedValue = new Label("0");
        pendingValue = new Label("0");

        HBox kpiRow = new HBox(
                kpiCard("Guests", totalGuestsValue),
                kpiCard("Attending", attendingValue),
                kpiCard("Declined", declinedValue),
                kpiCard("Pending", pendingValue)
        );
        kpiRow.setSpacing(0);
        kpiRow.setPadding(new Insets(8, 0, 8, 0));

        Label sectionTitle = new Label("My Guests");
        sectionTitle.getStyleClass().add("section-title");

        Button addGuestBtn = guiFactory.createButton("Add Guest");
        Button sendInvitationBtn = guiFactory.createButton("Send Invitation");
        Button sendReminderBtn = guiFactory.createButton("Send Reminder");

        addGuestBtn.getStyleClass().add("btn-primary");
        sendInvitationBtn.getStyleClass().add("btn-primary");
        sendReminderBtn.getStyleClass().add("btn-primary");

        addGuestBtn.setOnAction(e -> openAddGuestDialog());
        sendReminderBtn.setOnAction(e -> controller.sendReminderToPendingGuests());

        HBox actions = new HBox(10, addGuestBtn, sendInvitationBtn, sendReminderBtn);
        actions.setAlignment(Pos.CENTER_RIGHT);

        BorderPane sectionBar = new BorderPane();
        sectionBar.setLeft(sectionTitle);
        sectionBar.setRight(actions);
        sectionBar.setPadding(new Insets(8, 0, 8, 0));

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Guest, String> nameCol = new TableColumn<>("Guest Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getGuestName()));

        TableColumn<Guest, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getGuestCategory()));

        TableColumn<Guest, String> plusCol = new TableColumn<>("Plus Ones");
        plusCol.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getPlusOneCount())));

        TableColumn<Guest, String> rsvpCol = new TableColumn<>("RSVP Status");
        rsvpCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRsvpStatus().name()));

        table.getColumns().addAll(nameCol, categoryCol, plusCol, rsvpCol);
        table.setItems(guests);

        VBox page = new VBox(10, pageTitle, kpiRow, sectionBar, table);
        page.getStyleClass().add("page");

        HBox footer = new HBox(60);
        footer.getStyleClass().add("footer");

        // Column 1: About Us
        Label aboutTitle = new Label("About Us");
        aboutTitle.getStyleClass().add("footer-title");

        Label aboutText = new Label(
                "Ayza Atgawez is your complete wedding\n" +
                "planning companion, making your journey\n" +
                "to the big day simple and stress-free."
        );
        aboutText.getStyleClass().add("footer-text");

        VBox col1 = new VBox(8, aboutTitle, aboutText);

        // Column 2: Quick Links
        Label quickTitle = new Label("Quick Links");
        quickTitle.getStyleClass().add("footer-title");

        VBox quickLinks = new VBox(6,
                footerLink("Appointments"),
                footerLink("My Gehaz"),
                footerLink("Guest List"),
                footerLink("Wedding Checklist")
        );
        VBox col2 = new VBox(8, quickTitle, quickLinks);

        // Column 3: Services
        Label servicesTitle = new Label("Services");
        servicesTitle.getStyleClass().add("footer-title");

        VBox servicesLinks = new VBox(6,
                footerLink("Booking Vendors"),
                footerLink("Guest List & RSVP"),
                footerLink("Wedding Checklist"),
                footerLink("Gehaz Tracking")
        );
        VBox col3 = new VBox(8, servicesTitle, servicesLinks);

        // Column 4: Contact Us
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

        // Bottom copyright
        Label copyright = new Label("© 2024 Ayza Atgawez. All rights reserved.");
        copyright.getStyleClass().add("footer-bottom");
        HBox copyrightRow = new HBox(copyright);
        copyrightRow.setAlignment(Pos.CENTER);
        copyrightRow.getStyleClass().add("footer");

        VBox footerBlock = new VBox(10, footer, copyrightRow);
        footerBlock.getStyleClass().add("footer");


        VBox root = new VBox(navbar, page, footerBlock);

        Scene scene = new Scene(root, 820, 520);

        scene.getStylesheets().add(getClass().getResource("guestlist.css").toExternalForm());

        stage.setTitle("Ayza Atgawez - Guest List");
        stage.setScene(scene);
        stage.show();

        refreshUI();
    }

    private VBox kpiCard(String labelText, Label valueLabel) {
        Label label = new Label(labelText);
        label.getStyleClass().add("kpi-label");

        valueLabel.getStyleClass().add("kpi-value");

        VBox card = new VBox(6, label, valueLabel);
        card.getStyleClass().add("kpi-card");
        card.setMinWidth(170);
        return card;
    }

    private void refreshUI() {
        guests.setAll(controller.requestGuestList());

        int total = guests.size();
        int attending = 0;
        int declined = 0;
        int pending = 0;

        for (Guest g : guests) {
           switch (g.getRsvpStatus()) {
                case ATTENDING:
                    attending++;
                    break;
                case NOT_ATTENDING:
                    declined++;
                    break;
                case PENDING:
                    pending++;
                    break;
                default:
                    break;
            }

        }

        totalGuestsValue.setText(String.valueOf(total));
        attendingValue.setText(String.valueOf(attending));
        declinedValue.setText(String.valueOf(declined));
        pendingValue.setText(String.valueOf(pending));
    }

    private void openAddGuestDialog() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Guest");

        // ===== Header =====
        Label header = new Label("Add Guest");
        header.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #111;");

        // ===== Fields =====
        TextField nameField = guiFactory.createTextField("Guest name");

        TextField emailField = guiFactory.createTextField("example@email.com");

        TextField categoryField = guiFactory.createTextField("e.g., Family / Friends / Colleagues");

        TextField plusOneField = guiFactory.createTextField("0");

        plusOneField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;
            if (!newVal.matches("\\d*")) {
                plusOneField.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });

        Label validation = new Label("");
        validation.setStyle("-fx-text-fill: #B00020; -fx-font-weight: bold;");

        VBox form = new VBox(12,
                labeledField("Guest Name", nameField),
                labeledField("Email", emailField),
                labeledField("Category", categoryField),
                labeledField("Plus Ones", plusOneField),
                validation
        );

        // ===== Buttons =====
        ButtonType cancelType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType saveType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(cancelType, saveType);

        Button saveBtn = (Button) dialog.getDialogPane().lookupButton(saveType);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(cancelType);
        saveBtn.getStyleClass().add("btn-primary");
        cancelBtn.getStyleClass().add("btn-outline");

        VBox content = new VBox(16, header, form);
        content.setPadding(new Insets(20));
        content.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-border-color: #E7DCC7;"
        );

        dialog.getDialogPane().setContent(content);

        try {
            dialog.getDialogPane().getStylesheets().add(
                    getClass().getResource("guestlist.css").toExternalForm()
            );
        } catch (Exception ignored) {}

        saveBtn.addEventFilter(javafx.event.ActionEvent.ACTION, ev -> {

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String category = categoryField.getText().trim();

            if (name.isEmpty()) {
                validation.setText("Please enter guest name.");
                ev.consume();
                return;
            }

            if (email.isEmpty() || !email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
                validation.setText("Please enter a valid email.");
                ev.consume();
                return;
            }

            if (category.isEmpty()) {
                validation.setText("Please enter guest category.");
                ev.consume();
                return;
            }

            int plusOnes = 0;
            try {
                String txt = plusOneField.getText().trim();
                plusOnes = (txt.isEmpty()) ? 0 : Integer.parseInt(txt);
                if (plusOnes < 0) plusOnes = 0;
            } catch (Exception ex) {
                validation.setText("Plus ones must be a number.");
                ev.consume();
                return;
            }

            GuestData data = new GuestData(name, email, category, plusOnes);

            controller.createGuest(data);
            refreshUI();
        });

        dialog.showAndWait();
    }

    private VBox labeledField(String labelText, javafx.scene.Node field) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #111;");
        VBox box = new VBox(6, label, field);
        return box;
    }


    private Label footerLink(String text) {
    Label l = new Label(text);
    l.getStyleClass().add("footer-link");
    return l;
}

}

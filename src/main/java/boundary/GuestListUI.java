package boundary;

import controller.GuestListController;
import entity.Guest;
import entity.GuestData;
import factory.ThemeManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GuestListUI {

    private GuestListController controller;
    private TableView<Guest> table;
    private ObservableList<Guest> guests;

    private Label totalGuestsValue;
    private Label attendingValue;
    private Label declinedValue;
    private Label pendingValue;

    public GuestListUI() {
        controller = new GuestListController();
        controller.setCurrentBride("BRIDE_001");
        guests = FXCollections.observableArrayList();
    }

    public void start(Stage stage) {
        boolean dark = ThemeManager.isDarkTheme();

        HBox navbar = buildNavbar(dark);

        Label pageTitle = new Label("Guest List");
        pageTitle.setStyle(
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "#4A1F25;")
        );

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
        kpiRow.setSpacing(12);
        kpiRow.setPadding(new Insets(8, 0, 8, 0));

        Label sectionTitle = new Label("My Guests");
        sectionTitle.setStyle(
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "#4A1F25;")
        );

        Button addGuestBtn = new Button("Add Guest");
        stylePrimaryButton(addGuestBtn, dark);

        Button sendInvitationBtn = new Button("Send Invitation");
        stylePrimaryButton(sendInvitationBtn, dark);

        Button sendReminderBtn = new Button("Send Reminder");
        stylePrimaryButton(sendReminderBtn, dark);

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
        table.setItems(guests);
        styleTable(table, dark);

        TableColumn<Guest, String> nameCol = new TableColumn<>("Guest Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getGuestName()));

        TableColumn<Guest, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getGuestCategory()));

        TableColumn<Guest, String> plusCol = new TableColumn<>("Plus Ones");
        plusCol.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getPlusOneCount())));

        TableColumn<Guest, String> rsvpCol = new TableColumn<>("RSVP Status");
        rsvpCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRsvpStatus().name()));

        table.getColumns().addAll(nameCol, categoryCol, plusCol, rsvpCol);

        VBox page = new VBox(10, pageTitle, kpiRow, sectionBar, table);
        page.setPadding(new Insets(20));
        page.setStyle("-fx-background-color: " + (dark ? "#1E1517;" : "#F6F1EB;"));

        VBox footerBlock = buildFooter(dark);

        VBox root = new VBox(navbar, page, footerBlock);
        root.setStyle("-fx-background-color: " + (dark ? "#1E1517;" : "#F6F1EB;"));

        Scene scene = new Scene(root, 900, 650);

        stage.setTitle("Ayza Atgawez - Guest List");
        stage.setScene(scene);
        stage.show();

        refreshUI();
    }

    private VBox kpiCard(String labelText, Label valueLabel) {
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
        card.setMinWidth(170);
        card.setStyle(
                "-fx-background-color: " + (dark ? "#2A1D20;" : "#F9F6F1;") +
                "-fx-border-color: " + (dark ? "#5B3A40;" : "#D9C8B0;") +
                "-fx-border-radius: 12;" +
                "-fx-background-radius: 12;"
        );
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
        boolean dark = ThemeManager.isDarkTheme();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Guest");

        Label header = new Label("Add Guest");
        header.setStyle(
                "-fx-font-size: 22px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "#111111;")
        );

        TextField nameField = themedTextField("Guest name", dark);
        TextField emailField = themedTextField("example@email.com", dark);
        TextField categoryField = themedTextField("e.g., Family / Friends / Colleagues", dark);
        TextField plusOneField = themedTextField("0", dark);

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

        ButtonType cancelType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType saveType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(cancelType, saveType);

        Button saveBtn = (Button) dialog.getDialogPane().lookupButton(saveType);
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(cancelType);

        stylePrimaryButton(saveBtn, dark);
        styleOutlineButton(cancelBtn, dark);

        VBox content = new VBox(16, header, form);
        content.setPadding(new Insets(20));
        content.setStyle(
                "-fx-background-color: " + (dark ? "#2A1D20;" : "white;") +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-border-color: " + (dark ? "#5B3A40;" : "#E7DCC7;")
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().setStyle("-fx-background-color: " + (dark ? "#2A1D20;" : "white;"));

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
        boolean dark = ThemeManager.isDarkTheme();

        Label label = new Label(labelText);
        label.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "#111111;")
        );

        return new VBox(6, label, field);
    }

    private TextField themedTextField(String prompt, boolean dark) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle(
                "-fx-background-color: " + (dark ? "#2A1D20;" : "white;") +
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "#4A1F25;") +
                "-fx-prompt-text-fill: " + (dark ? "#B998A0;" : "#A88A8F;") +
                "-fx-border-color: " + (dark ? "#5B3A40;" : "#D9C8B0;") +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;"
        );
        return field;
    }

    private HBox buildNavbar(boolean dark) {
        HBox navbar = new HBox(18);
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(18, 24, 18, 24));
        navbar.setStyle("-fx-background-color: " + (dark ? "#EC008C;" : "#F18AAD;"));

        Label navAppointments = navLabel("Appointments", false, dark);
        Label navGehaz = navLabel("My Gehaz", false, dark);
        Label navLogo = navLogo("Ayza Atgawez", dark);
        Label navGuestList = navLabel("Guest List", true, dark);
        Label navWedding = navLabel("My Wedding", false, dark);

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

        return navbar;
    }

    private VBox buildFooter(boolean dark) {
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

        Label copyright = new Label("© 2024 Ayza Atgawez. All rights reserved.");
        copyright.setStyle(
                "-fx-text-fill: " + (dark ? "#F7EDEE;" : "white;") +
                "-fx-font-size: 13px;"
        );

        HBox copyrightRow = new HBox(copyright);
        copyrightRow.setAlignment(Pos.CENTER);
        copyrightRow.setPadding(new Insets(8));
        copyrightRow.setStyle("-fx-background-color: " + (dark ? "#2A1D20;" : "#6E2430;"));

        return new VBox(10, footer, copyrightRow);
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
}
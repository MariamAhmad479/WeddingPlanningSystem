package app;

import java.util.Date;

import entity.GehazItem;
import entity.meal.GuestMeal;
import entity.meal.PremiumDessertDecorator;
import entity.meal.StandardMeal;
import entity.meal.VeganMealDecorator;
import enumeration.GehazCategory;
import enumeration.GehazStatus;
import factory.GehazItemType;
import factory.GehazItemTypeFactory;
import service.calendar.CalendarSync;
import service.calendar.GoogleCalendarAPI;
import service.calendar.GoogleCalendarAdapter;
import service.document.Document;
import service.document.DocumentRenderer;
import service.document.GuestListReport;
import service.document.PdfRenderer;
import service.document.VendorContract;
import service.document.WordRenderer;
import service.notification.CancellationNotification;
import service.notification.EmailSender;
import service.notification.MessageSender;
import service.notification.Notification;
import service.notification.ReminderNotification;
import service.notification.SmsSender;
import service.payment.PaymentProcessor;
import service.payment.StripePaymentAPI;
import service.payment.StripePaymentAdapter;
import service.vendor.BasicPhotography;
import service.vendor.DronePhotographyDecorator;
import service.vendor.PrintedAlbumDecorator;
import service.vendor.VendorService;

public class PatternDemo {

    public static void main(String[] args) {
        runBridgePattern();
        runAdapterPattern();
        runFlyweightPattern();
        runDecoratorPattern();
    }

    private static void runBridgePattern() {
        System.out.println("\n======================================");
        System.out.println("1. BRIDGE PATTERN");
        System.out.println("======================================");

        System.out.println("\n--- Bridge Case 1: Notification System ---");
        MessageSender emailSender = new EmailSender();
        MessageSender smsSender = new SmsSender();

        Notification reminderByEmail =
                new ReminderNotification(emailSender, "Nour Ahmed", "Wedding RSVP Reminder");
        Notification reminderBySms =
                new ReminderNotification(smsSender, "Sara Khalil", "Wedding RSVP Reminder");
        Notification cancellationByEmail =
                new CancellationNotification(emailSender, "APT-2026-015");

        reminderByEmail.send();
        reminderBySms.send();
        cancellationByEmail.send();

        System.out.println("\n--- Bridge Case 2: Document Export ---");
        DocumentRenderer pdfRenderer = new PdfRenderer();
        DocumentRenderer wordRenderer = new WordRenderer();

        Document vendorContract = new VendorContract(
                pdfRenderer,
                "Cairo Lens Studio",
                "Photography package for 6 hours + couple session"
        );

        Document guestListReport = new GuestListReport(
                wordRenderer,
                250,
                180
        );

        vendorContract.exportDocument();
        guestListReport.exportDocument();
    }

    private static void runAdapterPattern() {
        System.out.println("\n======================================");
        System.out.println("2. ADAPTER PATTERN");
        System.out.println("======================================");

        System.out.println("\n--- Adapter Case 1: Stripe Payment ---");
        PaymentProcessor paymentProcessor =
                new StripePaymentAdapter(new StripePaymentAPI());

        boolean paymentResult = paymentProcessor.processPayment("bride001", 2500.00);
        System.out.println("Payment success? " + paymentResult);

        System.out.println("\n--- Adapter Case 2: Google Calendar ---");
        CalendarSync calendarSync =
                new GoogleCalendarAdapter(new GoogleCalendarAPI());

        calendarSync.addAppointment("Wedding Dress Fitting", new Date());
        calendarSync.removeAppointment("Wedding Dress Fitting");
    }

    private static void runFlyweightPattern() {
        System.out.println("\n======================================");
        System.out.println("3. FLYWEIGHT PATTERN");
        System.out.println("======================================");

        GehazItemType fridgeType1 =
                GehazItemTypeFactory.getGehazItemType("Refrigerator", GehazCategory.ESSENTIAL, 15000.0);
        GehazItemType sofaType1 =
                GehazItemTypeFactory.getGehazItemType("Sofa", GehazCategory.ESSENTIAL, 8000.0);
        GehazItemType fridgeType2 =
                GehazItemTypeFactory.getGehazItemType("Refrigerator", GehazCategory.ESSENTIAL, 15000.0);

        System.out.println("\n[Flyweight] fridgeType1 == fridgeType2 ? " + (fridgeType1 == fridgeType2));

        GehazItem item1 = new GehazItem("bride-001", fridgeType1);
        item1.setCost(15000.0);
        item1.setStatus(GehazStatus.NOT_PURCHASED);

        GehazItem item2 = new GehazItem("bride-002", fridgeType1);
        item2.setCost(14500.0);
        item2.setStatus(GehazStatus.NOT_PURCHASED);

        GehazItem item3 = new GehazItem("bride-003", fridgeType2);
        item3.setCost(15000.0);
        item3.setStatus(GehazStatus.NOT_PURCHASED);

        GehazItem item4 = new GehazItem("bride-001", sofaType1);
        item4.setCost(8000.0);
        item4.setStatus(GehazStatus.NOT_PURCHASED);

        GehazItem item5 = new GehazItem("bride-002", sofaType1);
        item5.setCost(8000.0);
        item5.setStatus(GehazStatus.NOT_PURCHASED);

        GehazItem item6 = new GehazItem("bride-003", sofaType1);
        item6.setCost(8000.0);
        item6.setStatus(GehazStatus.NOT_PURCHASED);

        System.out.println("\n--- Gehaz Items (extrinsic unique, intrinsic shared) ---");
        printGehazItem(item1);
        printGehazItem(item2);
        printGehazItem(item3);
        printGehazItem(item4);
        printGehazItem(item5);
        printGehazItem(item6);

        System.out.println("\n[Flyweight] GehazItemType objects in cache: "
                + GehazItemTypeFactory.getCacheSize()
                + " (shared across 6 GehazItem instances)");
    }

    private static void runDecoratorPattern() {
        System.out.println("\n======================================");
        System.out.println("4. DECORATOR PATTERN");
        System.out.println("======================================");

        System.out.println("\n--- Decorator Case 1: Vendor Service Packages ---");
        VendorService basicPhotography = new BasicPhotography();
        VendorService withDrone = new DronePhotographyDecorator(basicPhotography);
        VendorService fullPackage = new PrintedAlbumDecorator(withDrone);

        System.out.println("Base package:");
        System.out.println("Description: " + basicPhotography.getDescription());
        System.out.println("Cost: " + basicPhotography.getCost());

        System.out.println("\nWith drone:");
        System.out.println("Description: " + withDrone.getDescription());
        System.out.println("Cost: " + withDrone.getCost());

        System.out.println("\nFull package:");
        System.out.println("Description: " + fullPackage.getDescription());
        System.out.println("Cost: " + fullPackage.getCost());

        System.out.println("\n--- Decorator Case 2: Guest Meal Customization ---");
        GuestMeal standardMeal = new StandardMeal();
        GuestMeal veganMeal = new VeganMealDecorator(standardMeal);
        GuestMeal premiumVeganMeal = new PremiumDessertDecorator(veganMeal);

        System.out.println("Standard meal:");
        System.out.println("Description: " + standardMeal.getMealDescription());
        System.out.println("Cost: " + standardMeal.getCost());

        System.out.println("\nVegan meal:");
        System.out.println("Description: " + veganMeal.getMealDescription());
        System.out.println("Cost: " + veganMeal.getCost());

        System.out.println("\nVegan + premium dessert:");
        System.out.println("Description: " + premiumVeganMeal.getMealDescription());
        System.out.println("Cost: " + premiumVeganMeal.getCost());
    }

    private static void printGehazItem(GehazItem item) {
        System.out.printf(
                "brideId=%-12s name=%-15s cost=%-8.1f status=%s%n",
                item.getBrideId(),
                item.getName(),
                item.getCost(),
                item.getStatus()
        );
    }
}
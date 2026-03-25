package app;

import java.util.Date;
import java.util.List;
import DAO.GuestDB;
import entity.Guest;
import DAO.GehazItemAccessor;
import entity.GehazItem;
import entity.meal.GuestMeal;
import entity.meal.PremiumDessertDecorator;
import entity.meal.StandardMeal;
import entity.meal.VeganMealDecorator;
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
        
        GuestDB guestAccessor = new GuestDB();
        List<Guest> guests = guestAccessor.getGuests("BRIDE_001");


        for (Guest guest : guests) {
                    Notification reminder = new ReminderNotification(
                            emailSender,
                            guest.getGuestName(),
                            guest.getEmail(),
                            "Wedding Email RSVP Reminder"
                    );
                    reminder.send();
                }
        
        for (Guest guest : guests) {
                    Notification reminder = new ReminderNotification(
                            smsSender,
                            guest.getGuestName(),
                            guest.getSms(),
                            "Wedding Sms RSVP Reminder"
                    );
                    reminder.send();
                }
        
        int totalGuests = guests.size();
        int attendingGuests = 0;

        for (Guest guest : guests) {
            if (guest.getRsvpStatus() != null && guest.getRsvpStatus().name().equals("ATTENDING")) {
                attendingGuests++;
            }
        }
        
        Notification cancellationByEmail =
                new CancellationNotification(emailSender, "APT-2026-015");

        cancellationByEmail.send();

        System.out.println("\n--- Bridge Case 2: Document Export ---");
        DocumentRenderer renderer = new PdfRenderer();
        Document vendorContract = new VendorContract(
                renderer,
                "Cairo Lens Studio",
                "Photography package for 6 hours + couple session"
        );
        vendorContract.exportDocument();
        
        DocumentRenderer wordRenderer = new WordRenderer();
        Document report = new GuestListReport(wordRenderer, totalGuests, attendingGuests);
        report.exportDocument();
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

        GehazItemAccessor gehazAccessor = new GehazItemAccessor();
        List<GehazItem> items = gehazAccessor.getItemsByBrideId("BRIDE_001");

        System.out.println("\n--- Gehaz Items (extrinsic unique, intrinsic shared) ---");
        for (GehazItem item : items) {
            System.out.println(
                    "itemId=" + item.getItemId() +
                    ", brideId=" + item.getBrideId() +
                    ", name=" + item.getName() +
                    ", category=" + item.getCategory() +
                    ", status=" + item.getStatus() +
                    ", cost=" + item.getCost()
            );
        }

        int totalgehaz = items.size();
        System.out.println("\n[Flyweight] GehazItemType objects in cache: "
                + GehazItemTypeFactory.getCacheSize()
                + " shared across " + totalgehaz + " GehazItem instances");
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
}
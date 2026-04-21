package app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entity.GehazItem;
import entity.GehazItemList;
import entity.Vendor;
import entity.iterator.GehazItemFilter;
import enumeration.GehazCategory;
import enumeration.GehazStatus;
import factory.GehazItemType;
import factory.GehazItemTypeFactory;
import service.booking.AvailabilityComponent;
import service.booking.BookingCoordinator;
import service.booking.NotificationComponent;
import service.booking.PaymentComponent;
import service.dashboard.BudgetWidget;
import service.dashboard.ChecklistWidget;
import service.dashboard.GuestWidget;
import service.dashboard.WeddingDashboardCoordinator;
import service.event.CalendarEventListener;
import service.event.ChecklistEventListener;
import service.event.EventListener;
import service.event.EventManager;
import service.event.NotificationEventListener;
import service.onboarding.CatererOnboarding;
import service.onboarding.PhotographerOnboarding;
import service.onboarding.VendorOnboardingTemplate;
import service.payment.BankTransferPayment;
import service.payment.CreditCardPayment;
import service.payment.FawryPayment;
import service.payment.PaymentContext;
import service.report.BudgetReportGenerator;
import service.report.GuestListReportGenerator;
import service.report.ReportGenerator;
import service.search.SortByName;
import service.search.SortByPrice;
import service.search.SortByRating;
import service.search.VendorSearchContext;

public class BehavioralPatternDemo {

    private static final String DIVIDER = "========================================";
    private static final String SUB_DIVIDER = "----------------------------------------";

    public static void main(String[] args) {
        runTemplateMethodReportDemo();
        runTemplateMethodOnboardingDemo();
        runMediatorBookingDemo();
        runMediatorDashboardDemo();
        runStrategyVendorSortingDemo();
        runStrategyPaymentDemo();
        runObserverSingletonDemo();
        runIteratorDemo();
    }

    // =========================================================
    // TEMPLATE METHOD - REPORT GENERATION
    // =========================================================

    private static void runTemplateMethodReportDemo() {
        printSection("TEMPLATE METHOD DEMO - REPORT GENERATION");

        printCase("1) Budget Report Demo");
        ReportGenerator budgetReport = new BudgetReportGenerator(150000, 92000);
        budgetReport.generateReport();

        printCase("2) Guest List Report Demo");
        ReportGenerator guestReport = new GuestListReportGenerator(250, 180, 40);
        guestReport.generateReport();

        printCase("3) Guest List Report Demo (All Responded)");
        ReportGenerator guestReportAllResponded = new GuestListReportGenerator(100, 70, 30);
        guestReportAllResponded.generateReport();
    }

    // =========================================================
    // TEMPLATE METHOD - VENDOR ONBOARDING
    // =========================================================

    private static void runTemplateMethodOnboardingDemo() {
        printSection("TEMPLATE METHOD DEMO - VENDOR ONBOARDING");

        VendorOnboardingTemplate photographerOnboarding = new PhotographerOnboarding();
        VendorOnboardingTemplate catererOnboarding = new CatererOnboarding();

        printCase("1) Photographer Onboarding");
        photographerOnboarding.onboard("Lens Studio");

        printCase("2) Caterer Onboarding");
        catererOnboarding.onboard("Royal Catering");

        printCase("3) Another Photographer Onboarding");
        photographerOnboarding.onboard("Golden Moments Photography");
    }

    // =========================================================
    // MEDIATOR - BOOKING WORKFLOW
    // =========================================================

    private static void runMediatorBookingDemo() {
        printSection("MEDIATOR DEMO - BOOKING WORKFLOW");

        AvailabilityComponent availabilityComponent = new AvailabilityComponent(null);
        PaymentComponent paymentComponent = new PaymentComponent(null);
        NotificationComponent notificationComponent = new NotificationComponent(null);

        BookingCoordinator bookingCoordinator =
                new BookingCoordinator(availabilityComponent, paymentComponent, notificationComponent);

        printCase("1) Booking Request Flow");
        bookingCoordinator.setBookingContext(
                "BRIDE-001",
                "VENDOR-PHOTO-101",
                "2026-08-15",
                5000.0
        );
        availabilityComponent.requestBooking("VENDOR-PHOTO-101", "2026-08-15");

        printCase("2) Booking Cancellation Flow");
        bookingCoordinator.setBookingContext(
                "BRIDE-001",
                "VENDOR-PHOTO-101",
                "2026-08-15",
                5000.0
        );
        availabilityComponent.requestCancellation("VENDOR-PHOTO-101", "2026-08-15");

        printCase("3) Another Vendor Booking Flow");
        bookingCoordinator.setBookingContext(
                "BRIDE-002",
                "VENDOR-CATER-202",
                "2026-09-01",
                8000.0
        );
        availabilityComponent.requestBooking("VENDOR-CATER-202", "2026-09-01");
    }

    // =========================================================
    // MEDIATOR - WEDDING DASHBOARD
    // =========================================================

    private static void runMediatorDashboardDemo() {
        printSection("MEDIATOR DEMO - WEDDING DASHBOARD");

        WeddingDashboardCoordinator dashboardCoordinator = new WeddingDashboardCoordinator();

        GuestWidget guestWidget = new GuestWidget(dashboardCoordinator);
        ChecklistWidget checklistWidget = new ChecklistWidget(dashboardCoordinator);
        BudgetWidget budgetWidget = new BudgetWidget(dashboardCoordinator, 100000.0);

        dashboardCoordinator.setGuestWidget(guestWidget);
        dashboardCoordinator.setChecklistWidget(checklistWidget);
        dashboardCoordinator.setBudgetWidget(budgetWidget);

        printCase("1) Add Guests");
        guestWidget.addGuest("Mariam");
        guestWidget.addGuest("Ahmed");
        guestWidget.addGuest("Salma");

        printCase("2) Remove Guest");
        guestWidget.removeGuest("Ahmed");

        printCase("3) Update Checklist");
        checklistWidget.completeTask("Book Photographer");
        checklistWidget.completeTask("Choose Wedding Dress");
        checklistWidget.completeTask("Confirm Venue");

        printCase("4) Final Dashboard Snapshot");
        guestWidget.refresh();
        checklistWidget.refresh();
        budgetWidget.refresh();
    }

    // =========================================================
    // STRATEGY - VENDOR SEARCH SORTING
    // =========================================================

    private static void runStrategyVendorSortingDemo() {
        printSection("STRATEGY DEMO - VENDOR SEARCH SORTING");

        List<Vendor> vendors = new ArrayList<>();
        vendors.add(new Vendor("V001", "Golden Lens", "Photography", 12000, 4.8, 150));
        vendors.add(new Vendor("V002", "Bella Catering", "Catering", 18000, 4.5, 95));
        vendors.add(new Vendor("V003", "Amira Studio", "Photography", 10000, 4.9, 210));
        vendors.add(new Vendor("V004", "Dream Venue", "Venue", 50000, 4.6, 80));

        printCase("Original Vendor List");
        printVendors(vendors);

        VendorSearchContext vendorSearchContext = new VendorSearchContext(new SortByRating());

        printCase("1) Sort By Rating");
        printVendors(vendorSearchContext.sortVendors(vendors));

        vendorSearchContext.setStrategy(new SortByPrice());
        printCase("2) Sort By Price");
        printVendors(vendorSearchContext.sortVendors(vendors));

        vendorSearchContext.setStrategy(new SortByName());
        printCase("3) Sort By Name");
        printVendors(vendorSearchContext.sortVendors(vendors));
    }

    // =========================================================
    // STRATEGY - PAYMENT PROCESSING
    // =========================================================

    private static void runStrategyPaymentDemo() {
        printSection("STRATEGY DEMO - PAYMENT PROCESSING");

        String userId = "BRIDE-001";
        double depositAmount = 5000.0;
        double remainingAmount = 12000.0;

        PaymentContext paymentContext = new PaymentContext(
                new CreditCardPayment("1234567812345678")
        );

        printCase("1) Credit Card Payment");
        paymentContext.executePayment(userId, depositAmount);

        paymentContext.setStrategy(new FawryPayment("01012345678"));
        printCase("2) Fawry Payment");
        paymentContext.executePayment(userId, depositAmount);

        paymentContext.setStrategy(new BankTransferPayment("EG380019000500000000263180002"));
        printCase("3) Bank Transfer Payment");
        paymentContext.executePayment(userId, remainingAmount);

        paymentContext.setStrategy(new CreditCardPayment("5555444433331111"));
        printCase("4) Credit Card Payment Again");
        paymentContext.executePayment("BRIDE-002", 8000.0);
    }

    // =========================================================
    // OBSERVER + SINGLETON - EVENT BUS
    // =========================================================

    private static void runObserverSingletonDemo() {
        printSection("OBSERVER + SINGLETON DEMO - EVENT BUS");

        EventManager eventManager = EventManager.getInstance();
        eventManager.clearAll();

        EventListener notificationListener = new NotificationEventListener();
        EventListener calendarListener = new CalendarEventListener();
        EventListener checklistListener = new ChecklistEventListener();

        printCase("1) Subscribe listeners");
        eventManager.subscribe("BOOKING_CONFIRMED", notificationListener);
        eventManager.subscribe("BOOKING_CONFIRMED", calendarListener);
        eventManager.subscribe("BOOKING_CONFIRMED", checklistListener);

        eventManager.subscribe("BOOKING_CANCELLED", notificationListener);
        eventManager.subscribe("BOOKING_CANCELLED", calendarListener);
        eventManager.subscribe("BOOKING_CANCELLED", checklistListener);

        printCase("2) Publish BOOKING_CONFIRMED");
        eventManager.publish(
                "BOOKING_CONFIRMED",
                "Bride BRIDE-001 booked vendor VENDOR-PHOTO-101 on 2026-08-15"
        );

        printCase("3) Publish BOOKING_CANCELLED");
        eventManager.publish(
                "BOOKING_CANCELLED",
                "Bride BRIDE-001 cancelled vendor VENDOR-PHOTO-101 on 2026-08-15"
        );

        printCase("4) Unsubscribe one listener and publish again");
        eventManager.unsubscribe("BOOKING_CONFIRMED", checklistListener);
        eventManager.publish(
                "BOOKING_CONFIRMED",
                "Bride BRIDE-002 booked vendor VENDOR-CATER-202 on 2026-09-01"
        );

        printCase("5) Singleton check");
        EventManager secondReference = EventManager.getInstance();
        System.out.println("Same instance? " + (eventManager == secondReference));

        printCase("6) Publish event with no listeners");
        eventManager.publish("PAYMENT_FAILED", "Payment failure for booking BK-909");
    }

    // =========================================================
    // ITERATOR - GEHAZ ITEM FILTERING
    // =========================================================

    private static void runIteratorDemo() {
        printSection("ITERATOR DEMO - GEHAZ ITEM FILTERING");

        GehazItemList gehazItemList = new GehazItemList();
        gehazItemList.setBudget(50000.0);

        GehazItemType fridgeType =
                GehazItemTypeFactory.getGehazItemType("Refrigerator", GehazCategory.ESSENTIAL, 15000.0);
        GehazItemType sofaType =
                GehazItemTypeFactory.getGehazItemType("Sofa", GehazCategory.ESSENTIAL, 8000.0);
        GehazItemType microwaveType =
                GehazItemTypeFactory.getGehazItemType("Microwave", GehazCategory.OPTIONAL, 6000.0);
        GehazItemType lampType =
                GehazItemTypeFactory.getGehazItemType("Lamp", GehazCategory.OPTIONAL, 1200.0);

        GehazItem item1 = new GehazItem("bride-001", fridgeType);
        item1.setCost(15000.0);
        item1.setStatus(GehazStatus.PURCHASED);

        GehazItem item2 = new GehazItem("bride-001", sofaType);
        item2.setCost(8000.0);
        item2.setStatus(GehazStatus.NOT_PURCHASED);

        GehazItem item3 = new GehazItem("bride-001", microwaveType);
        item3.setCost(5500.0);
        item3.setStatus(GehazStatus.PURCHASED);

        GehazItem item4 = new GehazItem("bride-001", lampType);
        item4.setCost(1200.0);
        item4.setStatus(GehazStatus.NOT_PURCHASED);

        gehazItemList.addItem(item1);
        gehazItemList.addItem(item2);
        gehazItemList.addItem(item3);
        gehazItemList.addItem(item4);

        printCase("1) Iterate over ALL Gehaz items");
        for (GehazItem item : gehazItemList) {
            printItem(item);
        }

        printCase("2) Filter by Status = PURCHASED");
        Iterator<GehazItem> purchasedIterator =
                gehazItemList.filteredIterator(GehazItemFilter.byStatus(GehazStatus.PURCHASED));
        while (purchasedIterator.hasNext()) {
            printItem(purchasedIterator.next());
        }

        printCase("3) Filter by Category = ESSENTIAL");
        Iterator<GehazItem> essentialIterator =
                gehazItemList.filteredIterator(GehazItemFilter.byCategory(GehazCategory.ESSENTIAL));
        while (essentialIterator.hasNext()) {
            printItem(essentialIterator.next());
        }

        printCase("4) Filter by Cost > 5000");
        Iterator<GehazItem> expensiveIterator =
                gehazItemList.filteredIterator(GehazItemFilter.byCostAbove(5000.0));
        while (expensiveIterator.hasNext()) {
            printItem(expensiveIterator.next());
        }

        printCase("5) Combined custom filter");
        Iterator<GehazItem> customIterator =
                gehazItemList.filteredIterator(
                        item -> item.getStatus() == GehazStatus.PURCHASED
                                && item.getCost() > 10000
                );
        while (customIterator.hasNext()) {
            printItem(customIterator.next());
        }
    }

    // =========================================================
    // HELPERS
    // =========================================================

    private static void printSection(String title) {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println(" " + title + " ");
        System.out.println(DIVIDER);
    }

    private static void printCase(String title) {
        System.out.println();
        System.out.println(title);
        System.out.println(SUB_DIVIDER);
    }

    private static void printVendors(List<Vendor> vendors) {
        for (Vendor vendor : vendors) {
            System.out.println("Vendor ID: " + vendor.getVendorId());
            System.out.println("Business Name: " + vendor.getBusinessName());
            System.out.println("Category: " + vendor.getServiceCategory());
            System.out.println("Starting Price: EGP " + vendor.getStartingPrice());
            System.out.println("Average Rating: " + vendor.getAverageRating());
            System.out.println("Total Reviews: " + vendor.getTotalReviews());
            System.out.println(SUB_DIVIDER);
        }
    }

    private static void printItem(GehazItem item) {
        System.out.println("Name: " + item.getName());
        System.out.println("Category: " + item.getCategory());
        System.out.println("Cost: EGP " + item.getCost());
        System.out.println("Status: " + item.getStatus());
        System.out.println(SUB_DIVIDER);
    }
}
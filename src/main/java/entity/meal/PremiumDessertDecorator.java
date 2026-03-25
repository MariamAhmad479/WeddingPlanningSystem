package entity.meal;

public class PremiumDessertDecorator extends MealDecorator {
    public PremiumDessertDecorator(GuestMeal decoratedMeal) {
        super(decoratedMeal);
    }

    @Override
    public String getMealDescription() {
        return super.getMealDescription() + " + Deluxe Chocolate Lava Cake";
    }

    @Override
    public double getCost() {
        return super.getCost() + 20.00;
    }
}

package entity.meal;

public class VeganMealDecorator extends MealDecorator {
    public VeganMealDecorator(GuestMeal decoratedMeal) {
        super(decoratedMeal);
    }

    @Override
    public String getMealDescription() {
        // Substitute the main course description lightly just to show variation
        return super.getMealDescription().replace("Chicken", "Tofu") + " (Vegan Preparation)";
    }

    @Override
    public double getCost() {
        // Specialty ingredients add a slight premium
        return super.getCost() + 15.00; 
    }
}

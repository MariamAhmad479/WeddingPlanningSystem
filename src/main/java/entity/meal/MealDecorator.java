package entity.meal;

public abstract class MealDecorator implements GuestMeal {
    protected GuestMeal decoratedMeal;

    public MealDecorator(GuestMeal decoratedMeal) {
        this.decoratedMeal = decoratedMeal;
    }

    @Override
    public String getMealDescription() {
        return decoratedMeal.getMealDescription();
    }

    @Override
    public double getCost() {
        return decoratedMeal.getCost();
    }
}

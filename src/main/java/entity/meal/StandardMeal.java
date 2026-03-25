package entity.meal;

public class StandardMeal implements GuestMeal {
    @Override
    public String getMealDescription() {
        return "Standard Chicken and Rice";
    }

    @Override
    public double getCost() {
        return 50.00;
    }
}

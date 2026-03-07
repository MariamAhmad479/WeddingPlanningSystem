package factory;

import entity.Bride;
import entity.Guest;
import entity.User;
import entity.Vendor;

public class UserFactory {

    public User createUser(String userType) {
        if (userType == null || userType.trim().isEmpty()) {
            return null;
        }

        switch (userType.toUpperCase()) {
            case "BRIDE":
                return new Bride();
            case "VENDOR":
                return new Vendor();
            case "GUEST":
                return new Guest();
            default:
                throw new IllegalArgumentException("Unknown user type: " + userType);
        }
    }
}

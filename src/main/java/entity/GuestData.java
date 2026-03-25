package entity;

public class GuestData {

    private String name;
    private String category;
    private int plusOneCount;
    public String email;
    public String sms;

    public GuestData() {
    }

    public GuestData(String name,String email, String category, int plusOneCount, String sms) {
        this.name = name;
        this.email = email;
        this.category = category;
        this.plusOneCount = plusOneCount;
        this.sms = sms;
    }
    
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPlusOneCount() {
        return plusOneCount;
    }
    
    public String getSms() {
        return sms;
    }
}

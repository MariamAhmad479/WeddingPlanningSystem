package entity;

/**
 * 
 */
public class Bride implements User {

    /**
     * Default constructor
     */
    public Bride() {
    }

    /**
     * 
     */
    private String brideId;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    // private Date eventDate;

    /**
     * 
     */
    private String location;

    /**
     * 
     */
    private double totalBudget;

    /**
     * 
     */
    private int totalGuestCount;

    /**
     * 
     */
    private String GroomsName;

    /**
     * 
     */
    private int completedTasksCount;

    /**
     * 
     */
    // private List<String> weddingChecklist;

    /**
     * 
     */
    private Integer checklistProgress;

    /**
     * @param name
     * @return
     */
    public void setName(String name) {
        // TODO implement here
    }

    /**
     * @param email
     * @return
     */
    public void setEmail(String email) {
        // TODO implement here
    }

    /**
     * @param date
     * @return
     */
    /*
     * public void setEventDate(Date date) {
     * // TODO implement here
     * }
     */

    /**
     * @param location
     * @return
     */
    /*
     * public void setLocation(String location) {
     * // TODO implement here
     * }
     */

    /**
     * @param budget
     * @return
     */
    public void setTotalBudget(double budget) {
        // TODO implement here
    }

    /**
     * @param GroomsName
     * @return
     */
    public void setGroomsName(String GroomsName) {
        // TODO implement here
    }

    /**
     * @param newCount
     * @return
     */
    public void updateTotalGuestCount(int newCount) {
        // TODO implement here
    }

    /**
     * @return
     */
    /*
     * public List<String> getWeddingChecklist() {
     * // TODO implement here
     * return null;
     * }
     */

    /**
     * @param completedCount
     * @return
     */
    public void updateProgress(Integer completedCount) {
        // TODO implement here
    }

    @Override
    public String getUserType() {
        return "BRIDE";
    }
}

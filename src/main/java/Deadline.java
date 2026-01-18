public class Deadline extends Task {
    private String endTime;

    public Deadline(String description, String endTime) {
        super(description);
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + endTime + ")";
    }

    public static void getNormalDescription() {
        System.out.println("The correct call to description is : ");
    }
}

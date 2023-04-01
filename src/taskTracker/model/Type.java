package taskTracker.model;

public enum Type {
    TASK("Task"),
    EPIC("Epic"),
    SUBTASK("Subtask");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
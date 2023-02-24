package util;



public enum PreTaskType {
    TASK("task"),
    EPIC("epic"),
    SUB("sub");

    private final String value;

    PreTaskType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
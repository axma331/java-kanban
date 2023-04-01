package taskTracker.model;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int taskId, int epicId) {
        super(name, description, taskId);
        this.epicId = epicId;
    }

    public Subtask(String[] arr) {
        super(arr);
        this.epicId = Integer.parseInt(arr[5]);
    }

//getters

    public int getEpicId() {
        return epicId;
    }

    // Comparison

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subTask = (Subtask) o;
        return epicId == subTask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toStringForFile() {
        return super.toStringForFile() + epicId;
    }

    public String toString() {
        return '{' + this.getClass().getSimpleName() +
                "id=" + getId() +
                ", name='" + getName() +
                ", description='" + getDescription().length() +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                "}\n";
    }
}

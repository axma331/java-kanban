package taskTracker.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int taskId, int epicId, Instant startTime, Duration duration) {
        super(name, description, taskId, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(String[] arr) {
        super(arr);
        this.epicId = Integer.parseInt(arr[7]);
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
        return Objects.equals(this.epicId, subTask.epicId)
                && Objects.equals(getName(), subTask.getName())
                && Objects.equals(getDescription(), subTask.getDescription())
                && Objects.equals(getId(), subTask.getId())
                && Objects.equals(getStatus(), subTask.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + epicId;
    }

    public String toString() {
        return super.toString().substring(0, super.toString().length() - 1) +
                ", epicId=" + epicId +
                "}";
    }
}

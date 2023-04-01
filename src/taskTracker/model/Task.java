package taskTracker.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;
    private Instant startTime;
    private Duration duration;

    // Constructor

    /**
     * Default конструктор для создания предварительной задачи, до помещения его в TaskManager, где будет присвоен id.
     * В момент создания id = 0, а status = NEW.
     *
     * @param name        наименование задачи
     * @param description описание задачи
     */
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = -1;
        this.status = Status.NEW;
        this.startTime = Instant.MIN;
        this.duration = Duration.ZERO;
    }

    public Task(String name, String description, int taskId, Instant startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.id = taskId;
        this.status = Status.NEW;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String[] arr) {
        this.id = Integer.parseInt(arr[0]);
        this.name = arr[2];
        this.status = Status.valueOf(arr[3]);
        this.description = arr[4];
        this.startTime = Instant.parse(arr[5]);
        this.duration = Duration.parse(arr[6]);
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Duration getDuration() {
        return duration;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return startTime.plus(duration);
    }

    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status newStatus) {
        this.status = newStatus;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    // Comparison

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task other = (Task) o;
        return id == other.id && Objects.equals(name, other.name) && Objects.equals(description, other.description)
                && status == other.status;
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(name, description, id, status, startTime, duration);
    }

    public String toStringForFile() {
        return new StringBuilder()
                .append(id).append(",")
                .append(this.getClass().getSimpleName()).append(",")
                .append(name).append(",")
                .append(status).append(",")
                .append(description).append(",")
                .append(startTime).append(",")
                .append(duration).append(",").toString();
    }

    @Override
    public String toString() {
        return '{' + this.getClass().getSimpleName() +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description.length() + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", duration=" + duration +  "}\n";
    }
}

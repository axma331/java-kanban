package taskTracker.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;


public class Task {

    private int id;
    private String name;
    private String description;
    private Status status;
    private Instant startTime;
    private Duration duration;

    // Constructor

    /**
     * Default конструктор для создания предварительной задачи, до помещения его в TaskManager, где будет присвоен id.
     * В момент создания id = -1, status = NEW, startTime = Instant.MIN, duration = 0.
     */
    public Task(String name, String description) {
        this.id = -1;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.startTime = Instant.MIN;
        this.duration = Duration.ZERO;
    }

    public Task(String name, String description, int taskId, Instant startTime, Duration duration) {
        this.id = taskId;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.startTime = Objects.requireNonNullElse(startTime, Instant.MIN);
        this.duration = Objects.requireNonNullElse(duration, Duration.ZERO);
    }

    public Task(Task task) {
        this.id = task.id;
        this.name = task.name;
        this.description = task.description;
        this.status = task.status;
        this.startTime = task.startTime;
        this.duration = task.duration;
    }

    /**
     * Конструктор для восстановления задачи с архива.
     * @param arr массив строк содержащий следующие значения:
     *            <br>id=arr[0],<br>taskType=arr[1],<br>name=arr[2],<br>description=arr[3],
     *            <br>status=arr[4],<br>startTime=arr[5],<br>duration=arr[6]
     */
    public Task(String[] arr) {
        this.id = Integer.parseInt(arr[0]);
        this.name = arr[2];
        this.description = arr[3];
        this.status = Status.valueOf(arr[4]);
        this.startTime = Instant.parse(arr[5]);
        this.duration = Duration.parse(arr[6]);
    }

    // Getters

    public Integer getId() {
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

    public Instant getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public Instant getEndTime() {
        return startTime.plus(duration);
    }

    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status newStatus) {
        this.status = newStatus;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    // Comparison

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task other = (Task) o;
        return id == other.id && Objects.equals(name, other.name) && Objects.equals(description, other.description)
                && status == other.status && startTime.equals(other.startTime) && duration.equals(other.duration);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(id, name, description, status, startTime, duration);
    }

    //Converter for save

    public String toSaveString() { //todo нарушает ли это принципы SOLID: SRP?
        return id + ',' +
                this.getClass().getSimpleName() + ',' +
                name + ',' +
                description + ',' +
                status + ',' +
                startTime + ',' +
                duration + ',';
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ' ' +
                '{' +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description.length() + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", endTime=" + getEndTime() +
                "}";
    }

}

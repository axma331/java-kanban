package model;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;

    // Constructor

    /**
     * Default конструктор для создания предварительной задачи, до помещения его в TaskManager, где будет присвоен id.
     * В момент создания id = 0, а status = NEW.
     * @param name наименование задачи
     * @param description описание задачи
     */
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = 0;
        this.status = Status.NEW;
    }

    public Task(String name, String description, int taskId) {
        this.name = name;
        this.description = description;
        this.id = taskId;
        this.status = Status.NEW;
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

    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status newStatus) {
        this.status = newStatus;
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
        return 31 * Objects.hash(name, description, id, status);
    }

    @Override
    public String toString() {
        return '{' + this.getClass().getSimpleName() +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description.length() + '\'' +
                ", status=" + status +
                "}\n";
    }
}

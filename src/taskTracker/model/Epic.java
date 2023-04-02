package taskTracker.model;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private List<Integer> subTasks;
    Instant endTime;

    public Epic(String name, String description) {
        super(name, description);
        this.subTasks = new ArrayList<>();
    }

    public Epic(String name, String description, int taskId, Instant startTime, Duration duration) {
        super(name, description, taskId, startTime, duration);
        this.subTasks = new ArrayList<>();
    }

    public Epic(String[] arr) {
        super(arr);
        this.subTasks = new ArrayList<>();
    }

    // Getters

    public List<Integer> getSubTasks() {
        return subTasks;
    }

    public int getSubtaskByIdx(int index) {
        if (index >= this.subTasks.size()) {
            System.out.println("Не верно указан index!");
        }
        return this.subTasks.get(index);
    }

    // Setters

    public void addSubTasks(List<Integer> subTasks) {
        for (Integer subTask : subTasks) {
            if (!this.subTasks.contains(subTask))
                this.subTasks.add(subTask);
        }
    }

    public boolean addSubTask(int subTaskId) {
        if (!subTasks.contains(subTaskId)) {
            this.subTasks.add(subTaskId);
            return true;
        }
        return false;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    // Clear subtask

    public boolean deleteSubTask(Integer subTask) {
        if (this.subTasks.contains(subTask)) {
            this.subTasks.remove(subTask);
            return true;
        }
        return false;
    }

    public void clearSubtask() {
        this.subTasks.clear();
    }

    // Comparison

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return subTasks.equals(epic.subTasks)
                && getName().equals(epic.getName())
                && getDescription().equals(epic.getDescription())
                && getId().equals(epic.getId())
                && getStatus().equals(epic.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }

    @Override
    public String toString() {
        return '{' + this.getClass().getSimpleName() +
                "id=" + getId() +
                ", name='" + getName() +
                ", description='" + getDescription().length() +
                ", status=" + getStatus() +
                ", subtasks=" + subTasks +
                ", startTime=" + getStartTime() +
                ", duration=" + getDuration() +
                ", endTime=" + getEndTime() + "}\n";
    }
}

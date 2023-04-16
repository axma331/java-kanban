package taskTracker.model;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Epic extends Task {

    private final List<Integer> subTasks;
    private Instant endTime;

    public Epic(String name, String description) {
        super(name, description);
        this.subTasks = new ArrayList<>();
    }

    public Epic(String name, String description, int taskId, Instant startTime, Duration duration) {
        super(name, description, taskId, startTime, duration);
        this.subTasks = new ArrayList<>();
    }

    /**
     * Конструктор для восстановления задачи с архива.<br>Значение subTasks обновляются при восстановлении самой SubTask
     */
    public Epic(String[] arr) {
        super(arr);
        this.subTasks = new ArrayList<>();
    }

    // Getters

/*
    public int getSubtaskByIdx(int index) {
        if (index >= this.subTasks.size()) {
            System.out.println("Не верно указан index!");
        }
        return this.subTasks.get(index);
    }
*/

    public List<Integer> getSubTasks() {
        return subTasks;
    }

    @Override
    public Instant getEndTime() {
        return endTime;
    }

    // Setters

    public void addSubTasksId(List<Integer> subTasks) {

        for (Integer subTask : subTasks) {
            addSubTask(subTask);
        }
    }

    public boolean addSubTask(int subTaskId) {

        if (!subTasks.contains(subTaskId)) {
            return subTasks.add(subTaskId);
        }
        return false;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    // Clear subtask

    public boolean deleteSubtask(Integer subTask) {

        if (subTasks.contains(subTask)) {
            return subTasks.remove(subTask);
        }
        return false;
    }

    public void deleteAllSubtasks() {
        subTasks.clear();
    }

    // Comparison

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasks, epic.subTasks)
                && Objects.equals(endTime, epic.endTime);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(super.hashCode(), subTasks, endTime);
    }

    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length() - 1) +
                ", subtasks=" + subTasks +
                "}";
    }

}

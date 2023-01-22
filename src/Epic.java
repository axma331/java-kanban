import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private List<Integer> subTasks;

    public Epic(String name, String description) {
        super(name, description);
        this.subTasks = new ArrayList<>();
    }

    public Epic(String name, String description, int taskId) {
        super(name, description, taskId);
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

    public void addSubTask(int subTaskId) {
        this.subTasks.add(subTaskId);
    }

    // Clear subtask

    public void deleteSubTask(int subTask) {
        if (!this.subTasks.contains(subTask))
            this.subTasks.remove(subTask);
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
        return Objects.equals(subTasks, epic.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "id=" + getId() +
                ", name='" + getName() +
                ", description='" + getDescription().length() +
                ", status=" + getStatus() +
                ", subtasks=" + subTasks +
                '\n';
    }
}

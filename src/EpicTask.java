import java.util.ArrayList;
import java.util.List;

public class EpicTask extends AbstractTask {
    private List<Integer> subTasks;

    public EpicTask(String name, String description, int taskId, List<Integer> subTasks) {
        super(name, description, taskId);
        this.subTasks = subTasks;
    }

    public EpicTask(String name, String description, int taskId, List<Integer> subTasks, TaskStatus status) {
        super(name, description, taskId);
        this.subTasks = subTasks;
        this.setStatus(status);
    }

    public List<Integer> getSubTasks() {
        return subTasks;
    }

    public static final class PreEpicTask {
        private String name;
        private String description;

        public PreEpicTask(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "\t\t{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
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

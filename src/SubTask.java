import java.util.Objects;

public class SubTask extends AbstractTask {
    private int epicId;

    public SubTask(String name, String description, int taskId, int epicId) {
        super(name, description, taskId);
        this.epicId = epicId;
    }

    public SubTask(String name, String description, int taskId, int epicId, TaskStatus status) {
        super(name, description, taskId);
        this.epicId = epicId;
        this.setStatus(status);
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicId == subTask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    public String toString() { //TODO использовать super.toString()
        return this.getClass().getSimpleName() +
                "id=" + getId() +
                ", name='" + getName() +
                ", description='" + getDescription().length() +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '\n';
    }

    public static final class PreSubTask {
        private String name;
        private String description;
        private int epicId;

        public PreSubTask(String name, String description, int epicId) {
            this.name = name;
            this.description = description;
            this.epicId = epicId;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getEpicId() {
            return epicId;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "\t\t{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", epicId=" + epicId +
                    '}';
        }
    }
}

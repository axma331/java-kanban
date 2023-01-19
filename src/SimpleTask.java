public class SimpleTask extends AbstractTask {
    public SimpleTask(String name, String description, int taskId) {
        super(name, description, taskId);
    }

    public SimpleTask(String name, String description, int id, TaskStatus status) {
        super(name, description, id);
        super.setStatus(status);
    }


    public static final class PreSimpleTask {
        private String name;
        private String description;

        public PreSimpleTask(String name, String description) {
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
            return this.getClass().getSimpleName() + "\t{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}

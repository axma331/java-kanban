public abstract class AbstractTask {
   private String name;
   private String description;
   private int id;
   private TaskStatus status;

   public AbstractTask(String name, String description, int id) {
      this.name = name;
      this.description = description;
      this.id = id;
      this.status = TaskStatus.NEW;
   }



   public int getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public String getDescription() {
      return description;
   }

   public TaskStatus getStatus() {
      return status;
   }


   public AbstractTask setStatus(TaskStatus status) {
      return  null;
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName() +
              "id=" + id +
              ", name='" + name + '\'' +
              ", description='" + description.length() + '\'' +
              ", status=" + status +
              '\n';
   }

}

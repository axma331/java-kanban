import java.util.Objects;

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


   public void setStatus(TaskStatus status) {
      this.status = status;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      AbstractTask other = (AbstractTask) o;
      return id == other.id && Objects.equals(name, other.name) && Objects.equals(description, other.description)
              && status == other.status;
   }

   @Override
   public int hashCode() { //todo надо ли тут производить доумножение отдельно хеш-кода кажорого поля на 31, если
         // и в метоже  hash  идет реализация "result = 31 * result + (element == null ? 0 : element.hashCode())"
      // И надо ли преопределять в дочерних классах хашкод, если они не отличаются по полям от родителя?
      return Objects.hash(name, description, id, status);
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

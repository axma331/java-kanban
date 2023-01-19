import java.util.*;

public class TaskManager {
    private HashMap<Integer, AbstractTask> tasks;
    private TaskIdGenerator taskIdGenerator;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.taskIdGenerator = new TaskIdGenerator();
    }

    //Add task

    public void addTask(SimpleTask.PreSimpleTask preTask) {
        SimpleTask task = new SimpleTask(
                preTask.getName(),
                preTask.getDescription(),
                taskIdGenerator.getNextFreeId()
        );
        tasks.put(task.getId(), task);
    }

    public void addTask(EpicTask.PreEpicTask preTask) {
        EpicTask task = new EpicTask(
                preTask.getName(),
                preTask.getDescription(),
                taskIdGenerator.getNextFreeId(),
                new ArrayList<>()
        );
        tasks.put(task.getId(), task);
    }

    public void addTask(SubTask.PreSubTask preTask) {
        SubTask task = new SubTask(
                preTask.getName(),
                preTask.getDescription(),
                taskIdGenerator.getNextFreeId(),
                preTask.getEpicId()
        );
        tasks.put(task.getId(), task);
    }


    //Getters for tasks

    public List<AbstractTask> getAllTaskList() {
//        ArrayList<AbstractTask> taskList = new ArrayList<>();
//        for (int id : tasks.keySet()) {
//            taskList.add(tasks.get(id));
//        }
//        return taskList;

//        return tasks.values().stream().collect(Collectors.toList());

        return new ArrayList<>(tasks.values());
    }

    public AbstractTask getAnyTaskById(int taskId) {
        return tasks.get(taskId);
    }


    public List<SimpleTask> getSimpleTaskList(){
        return (List<SimpleTask>) getTasksListByType(SimpleTask.class);
    }

    public List<EpicTask> getEpicTaskList() {
        return (List<EpicTask>) getTasksListByType(EpicTask.class);
    }

    public List<SubTask> getSubTaskList() {
        return (List<SubTask>) getTasksListByType(SubTask.class);
    }

    public List<SubTask> getSubTaskListById(int epicId) {
        List<SubTask> taskList = new ArrayList<>();
        for (SubTask subTask : (List<SubTask>) getTasksListByType(SubTask.class)) {
            if (subTask.getEpicId() == epicId) {
                taskList.add(subTask);
            }
        }
        return taskList;
    }

    //Getters util

    private List<?> getTasksListByType(Class<?> type) {
        List<AbstractTask> taskList = new ArrayList<>();
        for (AbstractTask task : tasks.values()) {
            if (task.getClass() == type) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    public List<Integer> getIdOfAllTasks() {
        return new ArrayList<>(tasks.keySet());
    }

    //Delete tasks

    public void deleteAllTasks() {
        tasks.clear();
    }
    
   public void deleteTaskById(int taskId) { //TODO надо ли провeрять существование id?
        if (tasks.get(taskId).getClass() == EpicTask.class) {
            for (SubTask subTask : getSubTaskListById(taskId)) {
                tasks.remove(subTask.getId());
            }
        }
        tasks.remove(taskId);
   }

   //Update task

   public void updateTask(AbstractTask taskWithIOldId) {
        tasks.put(taskWithIOldId.getId(), taskWithIOldId);
    }

    //Additional methods


    private void keepOrCalculateTaskStatus(){
//        Фраза «информация приходит вместе с информацией по задаче» означает, что не существует отдельного метода,
//        который занимался бы только обновлением статуса задачи.
//        Вместо этого статус задачи обновляется вместе с полным обновлением задачи.

//        1. Пользователь не должен иметь возможности поменять статус эпика самостоятельно.
//        2. Когда меняется статус любой подзадачи в эпике, вам необходимо проверить,
//        что статус эпика изменится соответствующим образом. При этом изменение статуса эпика может и не произойти,
//        если в нём, к примеру, всё ещё есть незакрытые задачи
    }

    @Override
    public String toString() {
        return "TasksManager:\n" + tasks;
    }

    static final class TaskIdGenerator {
        private static int id = 0;

        int getNextFreeId(){
            return ++id;
        }
    }
}

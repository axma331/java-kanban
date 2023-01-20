import java.net.Inet4Address;
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

    /**
     * Добавление новой подзадачи в менеджер задач.<br>
     * Так же в Epic обновляется информация о подзадачах.
     * @param preTask предварительная задача
     */
    public void addTask(SubTask.PreSubTask preTask) {
        SubTask task = new SubTask(
                preTask.getName(),
                preTask.getDescription(),
                taskIdGenerator.getNextFreeId(),
                preTask.getEpicId()
        );
        tasks.put(task.getId(), task);
        updateInfoAboutSubTasks(task.getEpicId());
    }

    //Getters for tasks

    public List<AbstractTask> getAllTaskList() {
        return new ArrayList<>(tasks.values());
    }

    public AbstractTask getTaskById(int taskId) { //todo надо ли информировать пользователя об отсутствии задачи или выбрасывать искл. во избежании NullPointerExp.?
        return tasks.get(taskId);
    }

    public List<SimpleTask> getSimpleTaskList() { //todo не совсем понимаю, почему идет подсветка возврата...Подскажете?
        return (List<SimpleTask>) getTasksListByType(SimpleTask.class);
    }

    public List<EpicTask> getEpicTaskList() {
        return (List<EpicTask>) getTasksListByType(EpicTask.class);
    }

    public List<SubTask> getAllSubTaskList() {
        return (List<SubTask>) getTasksListByType(SubTask.class);
    }

    public List<SubTask> getSubTaskListByEpicId(int epicId) {
        List<SubTask> taskList = new ArrayList<>();
        for (SubTask subTask : (List<SubTask>) getTasksListByType(SubTask.class)) {
            if (subTask.getEpicId() == epicId) {
                taskList.add(subTask);
            }
        }
        return taskList;
    }

    public List<Integer> getSubTaskIdListByEpicId(int epicId) {
        List<Integer> list = new ArrayList<>();
        for (SubTask subTask : getSubTaskListByEpicId(epicId)) {
            list.add(subTask.getId());
        }
        return list;
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

    public List<Integer> getIdOfAllTasks() {  //todo вероятно вообще бесполезная функция :)
        return new ArrayList<>(tasks.keySet());
    }

    //Delete tasks

    public void deleteAllTasks() {
        tasks.clear();
    }

    /**
     * Удаление задачи с указанным идентификатором. Если задача является Epic, то так уже удаляется все его подзадачи.<br>
     * В случае отсутствия указанного идентификатора, выводится предупреждение!
     * @param id идентификатор задачи
     */
    public void deleteTaskById(int id) {  //Согласен, что правильнее выбросить самописное исключение, но пока так:)
        if (!tasks.containsKey(id)) {
            System.out.println("Задача №" + id + " отсутствует!");
            return;
        } else if (tasks.get(id).getClass() == EpicTask.class) {
            for (SubTask subTask : getSubTaskListByEpicId(id)) {
                tasks.remove(subTask.getId());
            }
        }
        tasks.remove(id);
    }

    //Update task

    public void updateTask(AbstractTask oldTask, TaskStatus newStatus) {
            //todo Согласен, что надо декомпозировать данный метод,
            // но пока не пришло понимание как это лучше не пришло понимание как это лучше сделать,и да есть дублирование кода :(
            // Честно говоря мне не очень нравиться как я сделал в данном методе т.к. я создал дополнительный конструктор
            // для каждого класса, когда, как мне кажется, правильнее было бы использовать тот что есть и просто дописать сетеры
            // на недостающие параметры. Что скажете? И так же я использовал абстрактный класс для обобщение методов (один для нескольких классов),
            // но что-то не очень хорошо у меня это получилось. Теперь я понял, что можно было просто сделать просто sybtask и epic,
            // которые наследуются от task, который сам является simpletask-ом.
        if (SimpleTask.class == oldTask.getClass()) {
            SimpleTask task = new SimpleTask(oldTask.getName(),
                    oldTask.getDescription(),
                    oldTask.getId(),
                    newStatus);
            tasks.put(task.getId(), task);
            System.out.println("You changed " + task.getClass().getSimpleName() + "s " +
                    "status from " + oldTask.getStatus() + " to " + newStatus);
        } else if (SubTask.class == oldTask.getClass()) {
            SubTask task = new SubTask(oldTask.getName(),
                    oldTask.getDescription(),
                    oldTask.getId(),
                    ((SubTask) oldTask).getEpicId(),
                    newStatus);
            tasks.put(task.getId(), task);
            System.out.println("You changed " + task.getClass().getSimpleName() + "s " +
                    "status from " + oldTask.getStatus() + " to " + newStatus);
            //todo rогда меняется статус любой подзадачи в эпике, нам необходимо актуализировать статус,
            // конечно при демпозиции код был бы более лаконичным, но понимание об этом пришло только сейчас,а пока так
            updateTask(tasks.get(task.getEpicId()), null);
        } else if (EpicTask.class == oldTask.getClass()) {
            int numberOfSubtasks = ((EpicTask) oldTask).getSubTasks().size();
            int count = 0;
            for (int subTaskId : ((EpicTask) oldTask).getSubTasks()) {
                if (tasks.get(subTaskId).getStatus() == TaskStatus.DONE
                        && ++count == numberOfSubtasks) {
                    updateEpicTaskStatus(oldTask, TaskStatus.DONE);
                    return;
                } else if (tasks.get(subTaskId).getStatus() == TaskStatus.IN_PROGRESS) {
                    updateEpicTaskStatus(oldTask, TaskStatus.IN_PROGRESS);
                    return;
                }
            }
            if (count != 0) {
                updateEpicTaskStatus(oldTask, TaskStatus.IN_PROGRESS);
            }
            System.out.println("The status " + oldTask.getStatus() + " of " + oldTask.getClass().getSimpleName() + "s " +
                    " remained unchanged.");
        }
    }

    private void updateEpicTaskStatus(AbstractTask oldTask, TaskStatus newStatus) {
        EpicTask task = new EpicTask(oldTask.getName(),
                oldTask.getDescription(),
                oldTask.getId(),
                ((EpicTask) oldTask).getSubTasks(),
                newStatus);
        tasks.put(task.getId(), task);
        System.out.println("You changed " + task.getClass().getSimpleName() + "s " +
                "status from " + oldTask.getStatus() + " to " + newStatus);
    }

    private void updateInfoAboutSubTasks(int epicId) {
        EpicTask oldTask = (EpicTask) getTaskById(epicId);
        EpicTask task = new EpicTask(oldTask.getName(),
                oldTask.getDescription(),
                oldTask.getId(),
                getSubTaskIdListByEpicId(epicId),
                oldTask.getStatus());
        tasks.put(task.getId(), task);
    }

    //Additional methods

    @Override
    public String toString() {
        return "TasksManager:\n" + tasks;
    }

    static final class TaskIdGenerator {
        private static int id = 0;

        int getNextFreeId() {
            return ++id;
        }
    }
}

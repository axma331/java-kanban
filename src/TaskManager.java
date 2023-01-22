import java.util.*;

public class TaskManager {
    private static int id = -1;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    /**
     * Генератор уникальных идентификаторов для менеджера задач. Начальное значение -1.
     *
     * @return следующий уникальный идентификатор задачи
     */
    private int getNextFreeId() {
        return ++id;
    }

    //Add task

    /**
     * Добавление новой задачи в менеджер. При добавление Subtask происходит обновление списка подзадач в Epic.
     *
     * @param preTask задача предварительно созданная для добавления в менеджер.
     */
    public void addTask(Task preTask) {
        if (Task.class == preTask.getClass()) {
            Task task = new Task(preTask.getName(), preTask.getDescription(), getNextFreeId());
            tasks.put(task.getId(), task);
        } else if (Epic.class == preTask.getClass()) {
            Epic task = new Epic(preTask.getName(), preTask.getDescription(), getNextFreeId());
            epics.put(task.getId(), task);
        } else if (Subtask.class == preTask.getClass()) {
            Subtask task = new Subtask(preTask.getName(), preTask.getDescription(), getNextFreeId(), ((Subtask) preTask).getEpicId());
            subtasks.put(task.getId(), task);
            updateInfoAboutSubTasks(task.getEpicId());
        }
    }

    private void updateInfoAboutSubTasks(int epicId) {
        if (epics.containsKey(epicId)) {
            Epic task = epics.get(epicId);
            Epic updatedTask = new Epic(task.getName(), task.getDescription(), epicId);
            updatedTask.addSubTasks(getSubTaskIdListByEpicId(epicId));
            epics.put(task.getId(), updatedTask);
        } else {
            System.out.println("Epic c id №" + epicId + " не существует. Привязка невозможна!");
        }
    }

    public List<Integer> getSubTaskIdListByEpicId(int epicId) {
        List<Integer> list = new ArrayList<>();
        for (Subtask subTask : getSubTaskListByEpicId(epicId)) {
            list.add(subTask.getId());
        }
        return list;
    }

    public List<Subtask> getSubTaskListByEpicId(int epicId) {
        List<Subtask> list = new ArrayList<>();
        for (Subtask subTask : getSubtaskList()) {
            if (subTask.getEpicId() == epicId) {
                list.add(subTask);
            }
        }
        return list;
    }

    public Task getTaskById(int taskId) { //todo надо ли информировать пользователя об отсутствии задачи или выбрасывать искл. во избежании NullPointerExp.?
        if (tasks.containsKey(taskId)) {
            return tasks.get(taskId);
        } else if (epics.containsKey(taskId)) {
            return epics.get(taskId);
        } else if (subtasks.containsKey(taskId)) {
            return subtasks.get(taskId);
        }
        System.out.println("Задача с таким идентификатором не найдена!");
        return null;
    }

    public List<Task> getTaskList() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getEpicList() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getSubtaskList() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Task> getAllTaskList() {
        List<Task> list = getTaskList();
        list.addAll(getEpicList());
        list.addAll(getSubtaskList());
        return list;
    }

    //Delete tasks

    public void deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    /**
     * Удаление задачи с указанным идентификатором. Если задача является Epic, то так уже удаляется все его подзадачи.<br>
     * В случае отсутствия указанного идентификатора, выводится предупреждение!
     *
     * @param id идентификатор задачи
     */
    public void deleteTaskById(int id) {
        if (!getIdOfAllTasks().contains(id)) {
            System.out.println("Задача №" + id + " отсутствует в списке!");
        } else if (epics.containsKey(id)) {
            for (int subtaskId : epics.get(id).getSubTasks()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        } else if (subtasks.containsKey(id)) {
            int epicId = subtasks.get(id).getEpicId();
            subtasks.remove(id);
            updateInfoAboutSubTasks(epicId);
        } else {
            tasks.remove(id);
        }
    }

    public Set<Integer> getIdOfAllTasks() {
        Set<Integer> keySet = new HashSet<>();
        keySet.addAll(tasks.keySet());
        keySet.addAll(epics.keySet());
        keySet.addAll(subtasks.keySet());
        return keySet;
    }

    //Update task

    public void updateTask(Task oldTask, TaskStatus newStatus) {
        if (Task.class == oldTask.getClass()) {
            Task task = new Task(oldTask.getName(), oldTask.getDescription(), oldTask.getId());
            task.setStatus(newStatus);
            tasks.put(task.getId(), task);
            System.out.println("You changed " + task.getClass().getSimpleName() + "s " +
                    "status from " + oldTask.getStatus() + " to " + newStatus);
        } else if (Subtask.class == oldTask.getClass()) {
            Subtask subtask = new Subtask(oldTask.getName(), oldTask.getDescription(), oldTask.getId(),
                    ((Subtask) oldTask).getEpicId());
            subtask.setStatus(newStatus);
            subtasks.put(subtask.getId(), subtask);
            System.out.println("You changed " + subtask.getClass().getSimpleName() + "s " +
                    "status from " + oldTask.getStatus() + " to " + newStatus);
            checkStatusOfEpic(epics.get(subtask.getEpicId()));
        } else if (Epic.class == oldTask.getClass()) {
            checkStatusOfEpic((Epic) oldTask);
        }
    }

    public void checkStatusOfEpic(Epic oldTask) {
        int numberOfSubtasks = oldTask.getSubTasks().size();
        int count = 0;
        for (int subTaskId : oldTask.getSubTasks()) {
            if (TaskStatus.DONE == subtasks.get(subTaskId).getStatus()
                    && ++count == numberOfSubtasks) {
                updateStatusOfEpic(oldTask, TaskStatus.DONE);
                return;
            } else if (TaskStatus.IN_PROGRESS == subtasks.get(subTaskId).getStatus()) {
                updateStatusOfEpic(oldTask, TaskStatus.IN_PROGRESS);
                return;
            }
        }
        if (count != 0) {
            updateStatusOfEpic(oldTask, TaskStatus.IN_PROGRESS);
        }
        System.out.println("The status " + oldTask.getStatus() + " of " + oldTask.getClass().getSimpleName() + "s " +
                " remained unchanged.");
    }

    private void updateStatusOfEpic(Epic oldTask, TaskStatus newStatus) {
        Epic epic = new Epic(oldTask.getName(), oldTask.getDescription(), oldTask.getId());
        epic.addSubTasks(oldTask.getSubTasks());
        epic.setStatus(newStatus);
        tasks.put(epic.getId(), epic);
        System.out.println("You changed " + epic.getClass().getSimpleName() + "s " +
                "status from " + oldTask.getStatus() + " to " + newStatus);
    }


    //Additional methods

    @Override
    public String toString() {
        return "TasksManager:\n" +
                tasks + '\n' +
                epics + '\n' +
                subtasks;
    }
}

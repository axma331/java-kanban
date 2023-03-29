package managers;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private static int id = -1;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;

    private final InMemoryHistoryManager history;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.history = new InMemoryHistoryManager();
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
    @Override
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

    @Override
    public List<Integer> getSubTaskIdListByEpicId(int epicId) {
        List<Subtask> subtaskList = subtasks.values().stream()
                .filter(x -> x.getEpicId() == epicId)
                .collect(Collectors.toList());
        return subtaskList.stream().map(Subtask::getId).collect(Collectors.toList());
    }

    @Override
    public List<Subtask> getSubTaskListByEpicId(int epicId) {
        List<Subtask> subtaskList = subtasks.values().stream()
                .filter(x -> x.getEpicId() == epicId)
                .collect(Collectors.toList());
        subtaskList.forEach(history::add);
        return subtaskList;
    }

    public List<Integer> getSubTaskIdList() {
        return subtasks.values().stream().map(Subtask::getId).collect(Collectors.toList());
    }

    public List<Integer> getTaskIdList() {
        return tasks.values().stream().map(Task::getId).collect(Collectors.toList());
    }

    public List<Integer> getEpicIdList() {
        return epics.values().stream().map(Epic::getId).collect(Collectors.toList());
    }

    public int getEpicIdBySubtaskId(int id) {
        return epics.get(subtasks.get(id).getEpicId()).getId();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task;
        if (tasks.containsKey(taskId)) {
            task = tasks.get(taskId);
            history.add(task);
            return task;
        } else if (epics.containsKey(taskId)) {
            task = epics.get(taskId);
            history.add(task);
            return task;
        } else if (subtasks.containsKey(taskId)) {
            task = subtasks.get(taskId);
            history.add(task);
            return task;
        }
        System.out.println("Задача с таким идентификатором не найдена!");
        return null;
    }

    @Override
    public List<Task> getTaskList() {
        tasks.values().forEach(history::add);
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpicList() {
        epics.values().forEach(history::add);
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getSubtaskList() {
        subtasks.values().forEach(history::add);
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Task> getAllTaskList() {
        List<Task> list = getTaskList();
        list.addAll(getEpicList());
        list.addAll(getSubtaskList());
        return list;
    }

    //Delete tasks

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        history.clear();
    }

    public void clearHistory() {
        history.clear();
    }


    /**
     * Удаление задачи с указанным идентификатором. Если задача является Epic, то так уже удаляется все его подзадачи.<br>
     * В случае отсутствия указанного идентификатора, выводится предупреждение!
     *
     * @param id идентификатор задачи
     */
    @Override
    public void deleteTaskById(int id) {
        if (!getIdOfAllTasks().contains(id)) {
            System.out.println("Задача №" + id + " отсутствует в списке!");
        } else if (epics.containsKey(id)) {
            for (int subtaskId : epics.get(id).getSubTasks()) {
                subtasks.remove(subtaskId);
                history.remove(subtaskId);
            }
            epics.remove(id);
        } else if (subtasks.containsKey(id)) {
            int epicId = subtasks.get(id).getEpicId();
            subtasks.remove(id);
            updateInfoAboutSubTasks(epicId);
        } else {
            tasks.remove(id);
        }
        history.remove(id);
    }

    @Override
    public Set<Integer> getIdOfAllTasks() {
        Set<Integer> keySet = new HashSet<>();
        keySet.addAll(tasks.keySet());
        keySet.addAll(epics.keySet());
        keySet.addAll(subtasks.keySet());
        return keySet;
    }

    //Update task

    @Override
    public void updateTask(Task oldTask, Status newStatus) {
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

    @Override
    public void checkStatusOfEpic(Epic oldTask) {
        int numberOfSubtasks = oldTask.getSubTasks().size();
        int count = 0;
        for (int subTaskId : oldTask.getSubTasks()) {
            if (Status.DONE == subtasks.get(subTaskId).getStatus()
                    && ++count == numberOfSubtasks) {
                updateStatusOfEpic(oldTask, Status.DONE);
                return;
            } else if (Status.IN_PROGRESS == subtasks.get(subTaskId).getStatus()) {
                updateStatusOfEpic(oldTask, Status.IN_PROGRESS);
                return;
            }
        }
        if (count != 0) {
            updateStatusOfEpic(oldTask, Status.IN_PROGRESS);
        }
        System.out.println("The status " + oldTask.getStatus() + " of " + oldTask.getClass().getSimpleName() + "s " +
                " remained unchanged.");
    }

    private void updateStatusOfEpic(Epic oldTask, Status newStatus) {
        Epic epic = new Epic(oldTask.getName(), oldTask.getDescription(), oldTask.getId());
        epic.addSubTasks(oldTask.getSubTasks());
        epic.setStatus(newStatus);
        tasks.put(epic.getId(), epic);
        System.out.println("You changed " + epic.getClass().getSimpleName() + "s " +
                "status from " + oldTask.getStatus() + " to " + newStatus);
    }

    public List<Integer> epicIdSetUtil() {
        return epics.values().stream().map(Epic::getId).collect(Collectors.toList());
    }

    //History

    public List<Task> getHistory() {
        return this.history.getHistory();
    }

    protected HistoryManager getHistoryManager() {
        return this.history;
    }

    public void setHistoryFromFile(List<Integer> idList) {
        for (Integer id : idList) {
            history.add(getTaskById(id));
        }
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

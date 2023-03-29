package managers;

import exception.ManagerSaveException;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import model.Type;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static final String BACKUP = "storage.csv";
    private static final String TITLE = "id,type,name,status,description,epic";


    public static void main(String[] args) throws ManagerSaveException {
        TaskManager manager = loadFromFile(new File(BACKUP));
        System.out.println(manager);


//        Заведите несколько разных задач, эпиков и подзадач.
//        Запросите некоторые из них, чтобы заполнилась история просмотра.
//        Создайте новый FileBackedTasksManager менеджер из этого же файла.
//        Проверьте, что история просмотра восстановилась верно и все задачи, эпики, подзадачи, которые были в старом, есть в новом менеджере.
    }

    public static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException {
        TaskManager manager = new FileBackedTaskManager();
        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String line;
            boolean isHistory = false;
            boolean firstStep = true;

            while (reader.ready()){
                line = reader.readLine();

                if (firstStep) {
                    firstStep = false;
                    continue;
                }

                if (line.isBlank()) {
                    isHistory = true;
                    continue;
                }
                if (!isHistory) {
                    manager.addTask(fromString(line));
                } else {
                    manager.setHistoryFromFile(historyFromString(line));
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Во время чтения файла произошла ошибка!", e);
        }
        return (FileBackedTaskManager) manager;
    }

    private void save() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP));) {
            writer.write(TITLE);
            writer.newLine();

            List<Task> tasksList = getAllTaskList();
            for (Task task : tasksList) {
                writer.write(task.toStringForFile());
                writer.newLine();
            }
            writer.newLine();
            writer.write(historyToString(getHistoryManager()));
//            writer.flush();
        } catch (IOException e) {
            System.out.println("Во время записи файла произошла ошибка!");
        }
    }

    public static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        Task lastTask = history.get(history.size() - 1);
        StringBuilder sb = new StringBuilder();
        return history.stream()
                .map(t -> sb.append(t.getId()))
                .filter(t -> !t.equals(lastTask))
                .map(t -> sb.append(","))
                .toString();
    }

    public static List<Integer> historyFromString(String value) {
        return Arrays.stream(value.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static Task fromString(String value) {
        String[] taskAttributes = value.split(",");

        switch (Type.valueOf(taskAttributes[1].toUpperCase())) {
            case TASK:
                return new Task(taskAttributes);
            case EPIC:
                return new Epic(taskAttributes);
            case SUBTASK:
                return new Subtask(taskAttributes);
            default:
                System.out.println("Не распознанная задача!");
        }
        return null;
    }


    public FileBackedTaskManager() {
        super();
    }

    @Override
    public void addTask(Task preTask) {
        super.addTask(preTask);
        save();
    }

    @Override
    public List<Integer> getSubTaskIdListByEpicId(int epicId) {
        List<Integer> list = super.getSubTaskIdListByEpicId(epicId);
        save();
        return list;
    }

    @Override
    public List<Subtask> getSubTaskListByEpicId(int epicId) {
        List<Subtask> list = super.getSubTaskListByEpicId(epicId);
        save();
        return list;
    }

    @Override
    public List<Integer> getSubTaskIdList() {
        List<Integer> List = super.getSubTaskIdList();
        save();
        return List;
    }

    @Override
    public List<Integer> getTaskIdList() {
        List<Integer> list = super.getTaskIdList();
        save();
        return list;
    }

    @Override
    public List<Integer> getEpicIdList() {
        List<Integer> list = super.getEpicIdList();
        save();
        return list;
    }

    @Override
    public int getEpicIdBySubtaskId(int id) {
        int epicId = super.getEpicIdBySubtaskId(id);
        save();
        return epicId;
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public List<Task> getTaskList() {
        List<Task> list = super.getTaskList();
        save();
        return list;
    }

    @Override
    public List<Epic> getEpicList() {
        List<Epic> list = super.getEpicList();
        save();
        return list;
    }

    @Override
    public List<Subtask> getSubtaskList() {
        List<Subtask> list = super.getSubtaskList();
        save();
        return list;
    }

    @Override
    public List<Task> getAllTaskList() {
        List<Task> list = super.getAllTaskList();
        save();
        return list;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void clearHistory() {
        super.clearHistory();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public Set<Integer> getIdOfAllTasks() {
        Set<Integer> set = super.getIdOfAllTasks();
        save();
        return set;
    }

    @Override
    public void updateTask(Task oldTask, Status newStatus) {
        super.updateTask(oldTask, newStatus);
        save();
    }

    @Override
    public void checkStatusOfEpic(Epic oldTask) {
        super.checkStatusOfEpic(oldTask);
        save();
    }

    @Override
    public List<Integer> epicIdSetUtil() {
        List<Integer> list = super.epicIdSetUtil();
        save();
        return list;
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    @Override
    protected HistoryManager getHistoryManager() {
        return super.getHistoryManager();
    }

}

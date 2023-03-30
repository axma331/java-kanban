package managers;

import exception.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;
import model.Type;
import util.TaskMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static final String BACKUP = "storage.csv";
    private static final String TITLE = "id,type,name,status,description,epic";

    public static void main(String[] args) {
        try {
            TaskManager manager = loadFromFile(new File(BACKUP));

            System.out.println("Part 1: saving tasks");
            {
                TaskMapper map = new TaskMapper(manager);
                manager.addTask(map.mapper(Type.TASK));
                manager.addTask(map.mapper(Type.EPIC));
                manager.addTask(map.mapper(Type.SUBTASK));
                manager.addTask(map.mapper(Type.SUBTASK));
                manager.addTask(map.mapper(Type.TASK));
                manager.addTask(map.mapper(Type.EPIC));
            }

            TaskManager managerT = loadFromFile(new File(BACKUP));

            System.out.println("Part 2: loading tasks");
            {
                System.out.println(manager);
                System.out.println(managerT);
                System.out.println("History:\n" + manager.getHistory());
                System.out.println("History:\n" + managerT.getHistory());
            }

        } catch (ManagerSaveException e) {
            e.getMessage();
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException {
        TaskManager manager = new FileBackedTaskManager();
        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String line;
            boolean isHistory = false;
            boolean firstStep = true;

            while (reader.ready()) {
                line = reader.readLine();

                if (firstStep) {
                    firstStep = false;
                    continue;
                }

                if (line.isBlank() && !firstStep) {
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

            for (Task task : getAllTaskList()) {
                writer.write(task.toStringForFile());
                writer.newLine();
            }

            writer.newLine();
            writer.write(historyToString(getHistoryManager()));
        } catch (IOException e) {
            System.out.println("Во время записи файла произошла ошибка!");
        }
    }

    public static String historyToString(HistoryManager manager) {
        return manager.getHistory().stream()
                .map(t -> new StringBuilder().append(t.getId()))
                .collect(Collectors.joining(","));
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> list = new ArrayList<>();
        try {
            list = Arrays.stream(value.split(","))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            System.out.println("Попытка преобразовать не число в int!");
        }
        return list;
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

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

}

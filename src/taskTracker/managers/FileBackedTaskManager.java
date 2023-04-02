package taskTracker.managers;

import taskTracker.model.Epic;
import taskTracker.model.Subtask;
import taskTracker.model.Task;
import taskTracker.model.Type;
import taskTracker.util.TaskMapper;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static final Path path = Path.of("resources/main/storage.csv");
    private static final String TITLE = "id,type,name,status,description,start_time,duration,epic";

    public static void main(String[] args) {
            TaskManager manager = loadFromFile(path);

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

            TaskManager managerT = loadFromFile(path);

            System.out.println("Part 2: loading tasks");
            {
                System.out.println(manager);
                System.out.println(managerT);
                System.out.println("History:\n" + manager.getHistory());
                System.out.println("History:\n" + managerT.getHistory());
            }
    }

    public static FileBackedTaskManager loadFromFile(Path path) {
        TaskManager manager = new FileBackedTaskManager();
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toAbsolutePath().toString(), UTF_8))) {
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
            System.out.printf("Во время чтения файла произошла ошибка{}%n", e.getMessage());
        }
        return (FileBackedTaskManager) manager;
    }

    private void save() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toAbsolutePath().toString(), UTF_8))) {
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

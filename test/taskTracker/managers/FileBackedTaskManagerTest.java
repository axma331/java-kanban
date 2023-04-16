package taskTracker.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskTracker.exception.ManagerLoadException;
import taskTracker.model.Epic;
import taskTracker.model.Subtask;
import taskTracker.model.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private final Path path = Path.of("resources/test/storageForCheck.csv");
    private FileBackedTaskManager manager;

    @BeforeEach
    public void beforeEach() {
        fillFile();
        manager = FileBackedTaskManager.loadFromFile(path);
    }

    @AfterEach
    private void afterEach() {
        manager.resetIdGenerator();
        path.toFile().delete();
    }

    @Test
    void testLoad() {

        manager = FileBackedTaskManager.loadFromFile(path);
        ManagerLoadException ex = assertThrows(
                ManagerLoadException.class,
                () -> FileBackedTaskManager.loadFromFile(Path.of("not-exist/no-file.csv")));

        assertEquals(ex.getMessage(), "Файла загрузки не существует",
                "Отсутствие ошибки при отсутствующем файле");

        ex = assertThrows(
                ManagerLoadException.class,
                () -> FileBackedTaskManager.loadFromFile(Path.of("resources/test/brokenStorage.csv")));

        assertEquals(ex.getMessage(), "Файл не удалось считать",
                "Отсутствие ошибки при битом файле");

        String[] checkData = {
                "0,Task,Task_task_0,Task_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "4,Task,Task_task_1,Task_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "1,Epic,Epic_task_0,Epic_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "2,Subtask,Subtask_task_0,Subtask_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,1",
                "3,Subtask,Subtask_task_1,Subtask_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,1"
        };

        Integer[] history = {0, 4, 1, 2, 3};
        Object[] array = manager.getAllTasks().stream().map(Task::toSaveString).toArray();
        assertArrayEquals(array, checkData,
                "Данные задач загрузки не совпадают");
        assertArrayEquals(manager.getHistory().stream().map(Task::getId).toArray(), history,
                "Данные загрузки истории не совпадают");
        path.toFile().delete();
    }

    @Test
    void testDeleteAllTasks() {
        manager = FileBackedTaskManager.loadFromFile(path);

        manager.deleteAllTasks();

        int counter = 0;

        try (var bw = new BufferedReader(
                new FileReader(path.toAbsolutePath().toString(), StandardCharsets.UTF_8))) {
            while (bw.readLine() != null) {
                counter++;
            }
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка чтения файла");
        }

        assertEquals(counter, 2,
                "В файле остались лишние строки");
    }

    @Test
    void testGetAnyTask() {
        manager = FileBackedTaskManager.loadFromFile(path);

        manager.getTaskById(1);

        manager = FileBackedTaskManager.loadFromFile(path);

        String[] checkData = {
                "0,Task,Task_task_0,Task_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "4,Task,Task_task_1,Task_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "1,Epic,Epic_task_0,Epic_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "2,Subtask,Subtask_task_0,Subtask_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,1",
                "3,Subtask,Subtask_task_1,Subtask_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,1"
        };

        Integer[] history = {0, 4, 1, 2, 3};

        assertArrayEquals(manager.getAllTasks().stream().map(Task::toSaveString).toArray(), checkData,
                "Данные задач загрузки не совпадают");
        assertArrayEquals(manager.getHistory().stream().map(Task::getId).toArray(), history,
                "Данные загрузки истории не совпадают");
    }

    @Test
    void testAddTask() {
        manager = FileBackedTaskManager.loadFromFile(path);

        Task task = new Task("Task_task_3", "Task_description_3");
        manager.addTask(task);

        manager = FileBackedTaskManager.loadFromFile(path);

        String[] checkData = {
                "0,Task,Task_task_0,Task_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "4,Task,Task_task_1,Task_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "5,Task,Task_task_3,Task_description_3,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "1,Epic,Epic_task_0,Epic_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "2,Subtask,Subtask_task_0,Subtask_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,1",
                "3,Subtask,Subtask_task_1,Subtask_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,1"
        };

        Integer[] history = {0, 4, 5, 1, 2, 3};

        assertArrayEquals(manager.getAllTasks().stream().map(Task::toSaveString).toArray(), checkData,
                "Данные задач загрузки не совпадают");
        assertArrayEquals(manager.getHistory().stream().map(Task::getId).toArray(), history,
                "Данные загрузки истории не совпадают");
    }

    @Test
    void testAddSubtask() {
        manager = FileBackedTaskManager.loadFromFile(path);

        Subtask task = new Subtask("Subtask_task_2", "Subtask_description_2", 1);

        manager.addTask(task);

        manager = FileBackedTaskManager.loadFromFile(path);

        String[] checkData = {
                "0,Task,Task_task_0,Task_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "4,Task,Task_task_1,Task_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "1,Epic,Epic_task_0,Epic_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "2,Subtask,Subtask_task_0,Subtask_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,1",
                "3,Subtask,Subtask_task_1,Subtask_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,1",
                "5,Subtask,Subtask_task_2,Subtask_description_2,NEW,-1000000000-01-01T00:00:00Z,PT0S,1"
        };

        Integer[] history = {0, 4, 1, 2, 3, 5};

        assertArrayEquals(manager.getAllTasks().stream().map(Task::toSaveString).toArray(), checkData,
                "Данные задач загрузки не совпадают");
        assertArrayEquals(manager.getHistory().stream().map(Task::getId).toArray(), history,
                "Данные загрузки истории не совпадают");
    }

//    @Test
//    void testUpdateTask() { //todo перезатирается история в файле, надо понять как решить проблему с сохранением во время считывания, т.к. считывая таску оно снова добавляет его в файл. А так все работает
//        Task task = new Task("Task_task_1", "Task_description_1");
//        task.setId(4);
//
//        manager.updateTask(task, Status.IN_PROGRESS);
//
//        manager = FileBackedTaskManager.loadFromFile(path);
//
//        String[] checkData = {
//                "0,Task,Task_task_0,Task_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
//                "4,Task,Task_task_1,Task_description_1,IN_PROGRESS,-1000000000-01-01T00:00:00Z,PT0S,",
//                "1,Epic,Epic_task_0,Epic_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
//                "2,Subtask,Subtask_task_0,Subtask_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,1",
//                "3,Subtask,Subtask_task_1,Subtask_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,1"
//        };
//
//        Integer[] history = {0,4,1,2,3};
//
//        assertArrayEquals(manager.getAllTaskList().stream().map(Task::toStringForFile).toArray(), checkData,
//                "Данные задач загрузки не совпадают");
//        assertArrayEquals(manager.getHistory().stream().map(Task::getId).toArray(), history,
//                "Данные загрузки истории не совпадают");
//    }


//    @Test
//    void testUpdateSubtask() {
//        Subtask task = new Subtask("Subtask_task_0", "Subtask_description_0", 1);
//        task.setId(2);
//
//        manager.updateTask(task, Status.IN_PROGRESS);
//
//        manager = FileBackedTaskManager.loadFromFile(path);
//
//        String[] checkData = {
//                "0,Task,Task_task_0,Task_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
//                "4,Task,Task_task_1,Task_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
//                "1,Epic,Epic_task_0,Epic_description_0,IN_PROGRESS,-1000000000-01-01T00:00:00Z,PT0S,",
//                "2,Subtask,Subtask_task_0,Subtask_description_0,IN_PROGRESS,-1000000000-01-01T00:00:00Z,PT0S,1",
//                "3,Subtask,Subtask_task_1,Subtask_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,1"
//        };
//
//        Integer[] history = {0,4,1,2,3};
//
//        assertArrayEquals(manager.getAllTaskList().stream().map(Task::toStringForFile).toArray(),
//                checkData,
//                "Данные задач загрузки не совпадают");
//        assertArrayEquals(manager.getHistory().stream().map(Task::getId).toArray(), history,
//                "Данные загрузки истории не совпадают");
//
//        Subtask task1 = new Subtask("Subtask_task_0", "Subtask_description_0", 1);
//        task.setId(2);
//
//        manager.updateTask(task1, Status.DONE);
//
//        assertArrayEquals(manager.getAllTaskList().stream().map(Task::toStringForFile).toArray(),
//                checkData,
//                "Данные задач загрузки не совпадают");
//        assertArrayEquals(manager.getHistory().stream().map(Task::getId).toArray(), history,
//                "Данные загрузки истории не совпадают");
//    }

    @Test
    void testDeleteAnyTask() {

        manager.deleteTaskById(4);
        manager.deleteTaskById(2);

        FileBackedTaskManager newManager = FileBackedTaskManager.loadFromFile(path);

        String[] checkData = {
                "0,Task,Task_task_0,Task_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "1,Epic,Epic_task_0,Epic_description_0,NEW,-1000000000-01-01T00:00:00Z,PT0S,",
                "3,Subtask,Subtask_task_1,Subtask_description_1,NEW,-1000000000-01-01T00:00:00Z,PT0S,1"
        };

        Integer[] history = {0, 1, 3};

        assertArrayEquals(newManager.getAllTasks().stream().map(Task::toSaveString).toArray(),
                checkData,
                "Данные задач загрузки не совпадают");
        assertArrayEquals(newManager.getHistory().stream().map(Task::getId).toArray(), history,
                "Данные загрузки истории не совпадают");
    }


    private void fillFile() {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println("Не удалось создать файл");
            }
        }

        System.out.println("Saving tasks");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toAbsolutePath().toString(), UTF_8))) {
            {
                writer.write("id,type,name,description,status,start_time,duration,epic");
                writer.newLine();
            }
            {
                writer.write(new Task("Task_task_0", "Task_description_0").toSaveString());
                writer.newLine();
                writer.write(new Epic("Epic_task_0", "Epic_description_0").toSaveString());
                writer.newLine();
                writer.write(new Subtask("Subtask_task_0", "Subtask_description_0", 1).toSaveString());
                writer.newLine();
                writer.write(new Subtask("Subtask_task_1", "Subtask_description_1", 1).toSaveString());
                writer.newLine();
                writer.write(new Task("Task_task_1", "Task_description_1").toSaveString());
                writer.newLine();
            }
            {
                writer.newLine();
                writer.write("0,4,1,2,3,5");
            }
        } catch (IOException e) {
            System.out.println("Во время записи файла произошла ошибка!");
        }
    }
}
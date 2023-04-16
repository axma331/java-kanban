package taskTracker.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskTracker.model.Epic;
import taskTracker.model.Status;
import taskTracker.model.Subtask;
import taskTracker.model.Task;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private static InMemoryTaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void testGetDeleteAllTasks() {
        taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("name_epic", "description_epic");
        epic.setId(1);
        Subtask subtask1 = new Subtask("name_subtask1", "description_subtask1", epic.getId());
        subtask1.setId(2);
        epic.addSubTask(2);
        Subtask subtask2 = new Subtask("name_subtask1", "description_subtask1", epic.getId());
        subtask2.setId(3);
        Task task = new Task("name_task", "description_task");
        task.setId(4);

        assertArrayEquals(taskManager.getAllTasks().toArray(), new Task[0],
                "Некорректный пустой список");

        taskManager.addTask(epic);
        taskManager.addTask(subtask1);
        taskManager.addTask(task);

        List<Task> list = new ArrayList<>();
        list.add(task);
        list.add(epic);
        list.add(subtask1);

        assertArrayEquals(taskManager.getAllTasks().toArray(), list.toArray(new Task[0]),
                "Некорректный список из 1 задачи, 1 эпика, 1 подзадачи");

        taskManager.addTask(subtask2);
        epic.addSubTask(3);
        list.add(subtask2);

        assertArrayEquals(taskManager.getAllTasks().toArray(), list.toArray(new Task[0]),
                "Некорректный список из 1 задачи, 1 эпика, 2 подзадач");

        taskManager.deleteAllTasks();

        assertArrayEquals(taskManager.getAllTasks().toArray(), new Task[0],
                "Некорректный список после удаления всех задач");
    }

    @Test
    void testgetTaskById() {
        taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("name_epic", "description_epic");
        epic.setId(1);

        assertNull(taskManager.getTaskById(5),
                "Получение задачи не возвращает null, если нет задачи");

        taskManager.addTask(epic);

        assertEquals(taskManager.getTaskById(1), epic,
                "Не возращает задачу, если она есть");
    }

    @Test
    void testAddTask() {
        taskManager = new InMemoryTaskManager();
        Task task = new Task("name_task", "description_task");
        task.setId(4);
        task.setStatus(Status.DONE);

        assertFalse(taskManager.addTask(null),
                "При добавлении null вместо задачи не возвращает false");
        assertTrue(taskManager.addTask(task),
                "При добавлении задачи не возращает true");
        assertEquals(taskManager.getTaskById(4).getStatus(), Status.NEW,
                "После добавления задачи не корректно назначен статус");
    }

    @Test
    void testAddEpic() {
        taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("name_epic", "description_epic");
        epic.setId(7);
        epic.setStatus(Status.NEW);

        assertFalse(taskManager.addTask(null),
                "При добавлении null вместо эпика не возвращает false");
        assertTrue(taskManager.addTask(epic),
                "При добавлении эпика не возращает true");
        Epic epic1 = new Epic("name_epic", "description_epic");
        epic1.setId(7);
        assertEquals(taskManager.getTaskById(7), epic1,
                "После добавления эпика не корректно назначен ID");
        assertArrayEquals(((Epic) taskManager.getTaskById(7)).getSubTasks().toArray(), new Subtask[0],
                "После добавления эпика не обнулён список подзадач");
        assertEquals(taskManager.getTaskById(7).getStatus(), Status.NEW,
                "После добавления эпика не корректно назначен статус");
    }

    @Test
    void testAddSubtask() {
        taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("name_epic", "description_epic");
        epic.setId(1);
        Subtask subtask1 = new Subtask("name_subtask1", "description_subtask1", 1);
        subtask1.setId(2);
        subtask1.setStatus(Status.DONE);
        Subtask subtask2 = new Subtask("name_subtask2", "description_subtask2", 5);
        subtask2.setId(3);

        taskManager.addTask(epic);
        taskManager.addTask(subtask1);

        assertFalse(taskManager.addTask(null),
                "При добавлении null вместо подзадачи не возвращает false");
        assertFalse(taskManager.addTask(subtask2),
                "При добавлении подзадачи с не существующим эпиком не возвращает false");
        Subtask subtask = new Subtask("name_subtask1", "description_subtask1", 1);
        subtask.setId(2);
        assertEquals(taskManager.getTaskById(2), subtask,
                "После добавления подзадачи не корректно назначен ID");
        assertEquals(taskManager.getTaskById(2).getStatus(), Status.NEW,
                "После добавления эпика не корректно назначен статус");
    }

    @Test
    void testUpdateTask() {
        taskManager = new InMemoryTaskManager();
        Task task = new Task("name_task1", "description_task1");
        task.setId(1);
        Task updateTask = new Task("name_task2", "description_task2");
        updateTask.setId(1);
        updateTask.setStatus(Status.DONE);

        taskManager.addTask(task);

        assertTrue(taskManager.updateTask(updateTask, Status.DONE),
                "При обновлении правильной задачи не возращает true");
        Task task1 = new Task("name_task2", "description_task2");
        task1.setId(1);
        task1.setStatus(Status.DONE);
        assertEquals(taskManager.getTaskById(1), task1,
                "После обновления задачи не корректно обновлены поля");
        assertEquals(taskManager.getTaskById(1).getStatus(), Status.DONE,
                "После обновления задачи не корректно назначен статус");
    }


    @Test
    void testDeleteAnyTask() {
        taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("name_epic", "description_epic");

        Subtask subtask = new Subtask(
                "name_subtask",
                "description_subtask",
                1);
        Subtask subtask2 = new Subtask(
                "name_subtask",
                "description_subtask",
                1);

        Task task = new Task("name_task", "description_task");
        task.setId(1);
        task.setStatus(Status.NEW);

        taskManager.addTask(task);

        assertFalse(taskManager.deleteTaskById(4),
                "При попытке удаления несуществующей задачи не возвращает false");

        taskManager.deleteTaskById(1);

        assertArrayEquals(taskManager.getAllTasks().toArray(), new Task[0],
                "При удалении простой задачи список не пуст");

    }

    @Test
    void testGetEpicSubtasks() {
        taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("name_epic", "description_epic");

        Subtask subtask = new Subtask(
                "name_subtask",
                "description_subtask",
                1);
        Subtask subtask2 = new Subtask(
                "name_subtask",
                "description_subtask",
                1);

        taskManager.addTask(epic);

        assertArrayEquals(taskManager.getEpicSubtasks(0).toArray(), new Task[0],
                "Ошибка при получении списка подзадач у эпика без подзадач");

    }

    @Test
    void testGetHistory() {
        taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("name_epic", "description_epic");

        Subtask subtask = new Subtask(
                "name_subtask",
                "description_subtask",
                1);
        Subtask subtask2 = new Subtask(
                "name_subtask",
                "description_subtask",
                1);

        taskManager.addTask(epic);
        taskManager.addTask(subtask);
        taskManager.addTask(subtask2);

        assertArrayEquals(taskManager.getHistory().toArray(), new Task[0],
                "Ошибка при получении пустой истории");
    }

    @Test
    void testEpicStatus() {
        taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("name_epic", "description_epic");
        epic.setId(1);
        epic.setStatus(Status.NEW);

        taskManager.addTask(epic);

        assertEquals(
                epic.getStatus(),
                Status.NEW,
                "При создании статус эпика отличен от NEW");

        Subtask subtask1 = new Subtask(
                "name_subtask1",
                "description_subtask1",
                1);
        Subtask subtask2 = new Subtask(
                "name_subtask2",
                "description_subtask2",
                1);

        taskManager.addTask(subtask1);
        taskManager.addTask(subtask2);

        assertEquals(
                epic.getStatus(),
                Status.NEW,
                "При добавлении новых подзадач статус эпика отличен от NEW");

    }

    @Test
    void testGetPrioritizedTasks() {
        taskManager = new InMemoryTaskManager();

        Task task1 = new Task("name.task1","description.task1", 1,
                Instant.ofEpochSecond(1678136400),
                Duration.ofMinutes(15 * 2));  // Mon Mar 06 2023 21:00:00 GMT+0000
        Task task2 = new Task("name.task2","description.task2", 1,
                Instant.ofEpochSecond(1678309200),
                Duration.ofMinutes(15 * 2));  // Wed Mar 08 2023 21:00:00 GMT+0000
        Task task3 = new Task("name.task3","description.task3", 1,
                Instant.ofEpochSecond(1678222800),
                Duration.ofMinutes(15 * 2));  // Tue Mar 07 2023 21:00:00 GMT+0000

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        List<Task> list = new ArrayList<>();
        list.add(task1);
        list.add(task3);
        list.add(task2);

        assertArrayEquals(taskManager.getPrioritizedTasks().toArray(), list.toArray(),
                "getPrioritizedTasks() работает не корректно");
    }

    @Test
    void testSetEpicDurationTime() {
        taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("name.epic", "description.epic");
        epic.setId(1);
        epic.setStatus(Status.NEW);
        Subtask subtask1 = new Subtask(
                "name.subtask1",
                "description.subtask1",
                2,
                1,
                Instant.ofEpochSecond(1678136400),
                Duration.ofMinutes(15 * 2));
        Subtask subtask2 = new Subtask(
                "name.subtask2",
                "description.subtask2",
                3,
                1,
                Instant.ofEpochSecond(1678309200),
                Duration.ofMinutes(15 * 3));

        taskManager.addTask(epic);
        taskManager.addTask(subtask1);
        taskManager.addTask(subtask2);

        assertEquals(taskManager.getTaskById(1).getStartTime(), Instant.ofEpochSecond(1678136400),
                "Начальное время рассчитывается некорректно");
        assertEquals(taskManager.getTaskById(3).getEndTime(),
                Instant.ofEpochSecond(1678309200).plus(Duration.ofMinutes(15 * 3)),
                "Конечное время рассчитывается некорректно");
        assertEquals(taskManager.getTaskById(1).getDuration(),
                Duration.ofMinutes(15 * 5),
                "Длительность рассчитывается некорректно");
    }

}
package taskTracker.managers;

import org.junit.jupiter.api.Test;
import taskTracker.model.Status;
import taskTracker.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class InMemoryHistoryManagerTest {
    @Test
    void testAddGet() {
        InMemoryHistoryManager manager = new InMemoryHistoryManager();

        assertArrayEquals(manager.getHistory().toArray(new Task[0]), new Task[0],
                "История должна быть пуста");

        Task task = new Task("name", "description");
        task.setId(1);
        task.setStatus(Status.IN_PROGRESS);
        manager.add(task);

        assertArrayEquals(manager.getHistory().toArray(new Task[0]), List.of(task).toArray(),
                "В истории должна быть одна задача");

        manager.add(task);

        assertArrayEquals(manager.getHistory().toArray(new Task[0]), List.of(task).toArray(),
                "В истории должна быть одна задача");

        Task task2 = new Task("name", "description");
        task2.setId(2);
        manager.add(task2);

        List<Object> list = List.of(task,task2);

        assertArrayEquals(manager.getHistory().toArray(new Task[0]),
                List.of(task,task2).toArray(),
                "В истории должно быть две задачи");

        manager.add(task);

        assertArrayEquals(manager.getHistory().toArray(new Task[0]),
                List.of(task2,task).toArray(),
                "Нарушен порядок истории");
    }

    @Test
    void testRemove() {
        InMemoryHistoryManager manager = new InMemoryHistoryManager();
        Task task1 = new Task("name", "description");
        task1.setId(1);
        task1.setStatus(Status.IN_PROGRESS);
        Task task2 = new Task("name", "description");
        task2.setId(2);
        Task task3 = new Task("name", "description");
        task3.setId(3);
        task3.setStatus(Status.DONE);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        manager.remove(2);

        assertArrayEquals(manager.getHistory().toArray(new Task[0]),
                List.of(new Task(task1), new Task(task3)).toArray(),
                "В истории при удалении не с концов происходит ошибка");

        manager.add(task2);
        manager.add(task3);

        manager.remove(3);

        assertArrayEquals(manager.getHistory().toArray(new Task[0]),
                List.of(new Task(task1), new Task(task2)).toArray(),
                "В истории при удалении с конца происходит ошибка");

        manager.add(task3);

        manager.remove(1);

        assertArrayEquals(manager.getHistory().toArray(new Task[0]),
                List.of(new Task(task2), new Task(task3)).toArray(),
                "В истории при удалении с конца происходит ошибка");
    }

    @Test
    void testClear() {
        InMemoryHistoryManager manager = new InMemoryHistoryManager();
        Task task1 = new Task("name", "description");
        task1.setId(1);
        task1.setStatus(Status.IN_PROGRESS);
        Task task2 = new Task("name", "description");
        task2.setId(2);
        Task task3 = new Task("name", "description");
        task3.setId(3);
        task3.setStatus(Status.DONE);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        manager.clear();

        assertArrayEquals(manager.getHistory().toArray(new Task[0]), new Task[0],
                "История должна быть пуста");
    }

}
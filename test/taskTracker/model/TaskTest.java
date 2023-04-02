package taskTracker.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TaskTest {

    @Test
    void getStatusTest() {
    }

    @Test
    void setIdTest() {
        Task task = new Task("name_task", "description_task");
        task.setId(1);

        assertEquals(task.getId(), 1, "ID не совпадает");

        task.setId(2);

        assertEquals(task.getId(), 2, "ID не совпадает");
    }

    @Test
    void setStatusTest() {
        Task task = new Task("name_task", "description_task");

        assertEquals(task.getStatus(), Status.NEW, "Статус не совпадает");

        task.setStatus(Status.DONE);

        assertEquals(task.getStatus(), Status.DONE, "Статус не совпадает");
    }

    @Test
    void setNameTest() {
        Task task = new Task("name_task", "description_task");

        assertEquals(task.getName(), "name_task", "Название не совпадает");

        task.setName("new_name_task");

        assertEquals(task.getName(), "new_name_task", "Новое название не совпадает");
    }

    @Test
    void setDescriptionTest() {
        Task task = new Task("name_task", "description_task");

        assertEquals(task.getDescription(), "description_task", "Описание не совпадает");

        task.setDescription("new_description_task");

        assertEquals(task.getDescription(), "new_description_task", "Описание не совпадает");
    }

    @Test
    void testEqualsTest() {
        Task task1 = new Task("name_task", "description_task");
        task1.setId(2);
        Task task2 = new Task("name_task", "description_task");
        task2.setId(2);

        assertEquals(task1, task2, "Идентичные задачи не совпадают");

        task1.setId(1);

        assertNotEquals(task1, task2, "Различные задачи совпадают");

        task1.setId(2);
        task1.setStatus(Status.DONE);

        assertNotEquals(task1, task2, "Различные задачи совпадают");

        task1.setStatus(Status.NEW);
        task1.setName("");

        assertNotEquals(task1, task2, "Различные задачи совпадают");

        task1.setName("name_task");
        task1.setDescription("");

        assertNotEquals(task1, task2, "Различные задачи совпадают");
    }

    @Test
    void hashCodeTest() {
        Task task1 = new Task("name_task", "description_task");
        task1.setId(2);
        Task task2 = new Task("name_task", "description_task");
        task2.setId(2);

        assertEquals(task1.hashCode(), task1.hashCode());
        assertEquals(task1.hashCode(), task2.hashCode());

        task2.setName("");

        assertNotEquals(task1.hashCode(), task2.hashCode());
        assertEquals(task2.hashCode(), task2.hashCode());

        task2.setId(2);

        assertNotEquals(task1.hashCode(), task2.hashCode());
        assertEquals(task2.hashCode(), task2.hashCode());
    }
}
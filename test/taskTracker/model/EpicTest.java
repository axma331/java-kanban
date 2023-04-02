package taskTracker.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void addSubTasks() {
        Epic epic = new Epic("name_epic", "description_epic");
        Subtask subtask1 = new Subtask(
                "name_subtask1",
                "description_subtask1",
                epic.getId());
        subtask1.setId(2);
        Subtask subtask2 = new Subtask(
                "name_subtask2",
                "description_subtask2",
                epic.getId());
        subtask2.setId(3);

        assertArrayEquals(epic.getSubTasks().toArray(), new Integer[0], "Пустой список подзадач отличается");

        epic.addSubTask(subtask1.getId());
        epic.addSubTask(subtask2.getId());

        List<Integer> subtasks = new ArrayList<>();

        subtasks.add(subtask1.getId());
        subtasks.add(subtask2.getId());

        assertArrayEquals(epic.getSubTasks().toArray(), subtasks.toArray(), "Список подзадач отличается");
        assertFalse(epic.addSubTask(2), "Ошибка при добавлении существующей подзадачи");
    }

    @Test
    void deleteSubTask() {
        Epic epic = new Epic("name_epic", "description_epic");
        epic.setId(1);
        Subtask subtask1 = new Subtask(
                "name_subtask1",
                "description_subtask1",
                epic.getId());
        subtask1.setId(2);
        Subtask subtask2 = new Subtask(
                "name_subtask2",
                "description_subtask2",
                epic.getId());
        subtask2.setId(3);

        epic.addSubTask(subtask1.getId());
        epic.addSubTask(subtask2.getId());

        int id = subtask2.getId();
        epic.deleteSubTask(id);

        List<Integer> subtasks = new ArrayList<>();
        subtasks.add(subtask1.getId());

        assertArrayEquals(epic.getSubTasks().toArray(), subtasks.toArray(), "Список подзадач отличается");
        assertFalse(epic.deleteSubTask(1), "Ошибка при удалении не существующей подзадачи");
    }

    @Test
    void testEquals() {
        Epic epic1 = new Epic("name_epic", "description_epic");
        epic1.setId(1);
        Epic epic2 = new Epic("name_epic", "description_epic");
        epic2.setId(1);

        assertEquals(epic1, epic2, "Идентичные эпики не совпадают.");

        epic2.setId(2);

        assertNotEquals(epic1, epic2, "Различные эпики совпадают.");

        epic2.setId(1);
        epic2.setStatus(Status.DONE);

        assertNotEquals(epic1, epic2, "Различные эпики совпадают.");

        epic2.setStatus(Status.NEW);
        epic2.setName("");

        assertNotEquals(epic1, epic2, "Различные эпики совпадают.");

        epic2.setName("name_epic");
        epic2.setDescription("");

        assertNotEquals(epic1, epic2, "Различные эпики совпадают.");

        epic2.setDescription("description_epic");
        epic2.addSubTask(3);

        assertNotEquals(epic1, epic2, "Различные эпики совпадают.");
    }

    @Test
    void testHashCode() {
        Epic epic1 = new Epic("name_epic", "description_epic");
        epic1.setId(1);
        Epic epic2 = new Epic("name_epic", "description_epic");
        epic2.setId(1);

        assertEquals(epic1.hashCode(), epic1.hashCode());
        assertEquals(epic1.hashCode(), epic2.hashCode());

        epic1.addSubTask(2);

        assertNotEquals(epic1.hashCode(), epic2.hashCode());
        assertEquals(epic2.hashCode(), epic2.hashCode());
    }
}
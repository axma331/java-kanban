package taskTracker.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SubtaskTest {

    @Test
    void getEpicId() {
        Subtask subtask = new Subtask("name_subtask", "description_subtask", 1);

        assertEquals(subtask.getEpicId(), 1, "Полученные эпики не совпадают");
    }

    @Test
    void testEquals() {
        Subtask subtask1 = new Subtask("name_subtask", "description_subtask", 1);
        subtask1.setId(2);
        Subtask subtask2 = new Subtask("name_subtask", "description_subtask",1);
        subtask2.setId(2);

        assertEquals(subtask1, subtask2, "Идентичные подзадачи не совпадают");

        subtask2.setId(1);

        assertNotEquals(subtask1, subtask2, "Различные подзадачи совпадают");

        subtask2.setId(2);
        subtask2.setStatus(Status.DONE);

        assertNotEquals(subtask1, subtask2, "Различные подзадачи совпадают");

        subtask2.setStatus(Status.NEW);
        subtask2.setName("");

        assertNotEquals(subtask1, subtask2, "Различные подзадачи совпадают");

        subtask2.setName("name_subtask");
        subtask2.setDescription("");

        assertNotEquals(subtask1, subtask2, "Различные подзадачи совпадают");
    }

    @Test
    void testHashCode() {
        Subtask subtask1 = new Subtask("name_subtask1", "description_subtask1", 1);
        Subtask subtask2 = new Subtask("name_subtask1", "description_subtask1", 1);

        assertEquals(subtask1.hashCode(), subtask1.hashCode());
        assertEquals(subtask1.hashCode(), subtask2.hashCode());

        subtask2.setId(2);

        assertNotEquals(subtask1.hashCode(), subtask2.hashCode());
        assertEquals(subtask2.hashCode(), subtask2.hashCode());
    }

    @Test
    void toStringForFile() {
        Subtask subtask1 = new Subtask("name_subtask1", "description_subtask1", 1);

        assertEquals(
                subtask1.toStringForFile(),
                "-1,Subtask,name_subtask1,description_subtask1,NEW,-1000000000-01-01T00:00:00Z,PT0S,1",
                "toSaveString() не совпадает");
    }
}
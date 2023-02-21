package managers;

import model.Task;
import util.CustomLinkedList;

import java.util.Collections;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_HISTORY_LENGTH = 10;
    private final CustomLinkedList taskHistory = new CustomLinkedList();

    /**
     * Добавление в историю просмотренной задачи.
     *
     * @param task просмотренная задача
     */
    @Override
    public void add(Task task) {

        if (taskHistory.size() < MAX_HISTORY_LENGTH) {
            taskHistory.add(task);
        } else {
            taskHistory.removeFirst();
            taskHistory.add(task);
        }
    }

    @Override
    public void remove(int taskId) {
            taskHistory.remove(taskId);
    }

    @Override
    public List<Task> getHistory() {
        return taskHistory.getTasks();
    }
}

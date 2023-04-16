package taskTracker.managers;

import taskTracker.model.Task;
import taskTracker.util.CustomLinkedList;

import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList taskHistory = new CustomLinkedList();

    private static final int MAX_HISTORY_LENGTH = 10;

    @Override
    public List<Task> getHistory() {
        return taskHistory.getTasks();
    }

    /**
     * Добавление в историю просмотренной задачи.
     */
    @Override
    public void add(Task task) {

        taskHistory.add(task);
        if (taskHistory.size() > MAX_HISTORY_LENGTH) {
            taskHistory.removeFirst();
        }
    }

    @Override
    public void remove(int taskId) {
            taskHistory.remove(taskId);
    }

    @Override
    public void clear() {
        taskHistory.clear();
    }

}

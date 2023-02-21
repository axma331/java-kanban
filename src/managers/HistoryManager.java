package managers;

import model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    void remove(int taskId);
    List<Task> getHistory();
}

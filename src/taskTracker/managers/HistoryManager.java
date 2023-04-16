package taskTracker.managers;

import taskTracker.model.Task;

import java.util.List;


public interface HistoryManager {

    List<Task> getHistory();
    void add(Task task);
    void remove(int taskId);
    void clear();

}

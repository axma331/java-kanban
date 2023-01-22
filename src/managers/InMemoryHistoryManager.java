package managers;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_HISTORY_LENGTH = 10;
    List<Task> taskHistory = new ArrayList<>(MAX_HISTORY_LENGTH);


    @Override
    public void add(Task task) {

        if (taskHistory.size() < MAX_HISTORY_LENGTH) {
            taskHistory.add(task);
        } else {
            taskHistory.remove(0);
            taskHistory.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }
}

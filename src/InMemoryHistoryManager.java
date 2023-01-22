import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_HISTORY_LENGTH = 10;
    private static int id = 0;
    List<Task> taskHistory = new ArrayList<>(MAX_HISTORY_LENGTH);


    @Override
    public void add(Task task) {

        if (taskHistory.size() < MAX_HISTORY_LENGTH) {
            taskHistory.add(task);
        } else {
            taskHistory.add(0, task);
            taskHistory.remove(MAX_HISTORY_LENGTH);
        }
    }

    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }
}

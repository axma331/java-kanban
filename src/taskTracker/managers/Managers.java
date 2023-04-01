package taskTracker.managers;

public class Managers {
    static public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    static public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

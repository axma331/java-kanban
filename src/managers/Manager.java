package managers;

public class Manager {
    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

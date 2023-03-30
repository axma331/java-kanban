package managers;

public class Managers {
    static public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    static public TaskManager getFileBacked(){
        return new FileBackedTaskManager();
    }

    static public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

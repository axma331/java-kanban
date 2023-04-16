package taskTracker.managers;

import taskTracker.model.Epic;
import taskTracker.model.Status;
import taskTracker.model.Subtask;
import taskTracker.model.Task;

import java.util.List;
import java.util.Set;


public interface TaskManager {

    Task getTaskById(int taskId);

    List<Task> getTasks();

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    List<Task> getAllTasks();


    List<Subtask> getEpicSubtasks(int epicId);

    List<Integer> getEpicSubtasksList(int epicId);

    Set<Integer> getIdOfAllTasks();

    int getEpicIdBySubtaskId(int id);


    boolean addTask(Task preTask);

    boolean updateTask(Task oldTask, Status newStatus);

    void checkStatusOfEpic(Epic oldTask);


    boolean deleteTaskById(int id);

    void deleteAllTasks();


    List<Task> getHistory();

    void setHistoryFromFile(List<Integer> idList);

    void clearHistory();


    List<Task> getPrioritizedTasks();

}


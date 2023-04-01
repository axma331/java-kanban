package taskTracker.managers;

import taskTracker.model.Epic;
import taskTracker.model.Status;
import taskTracker.model.Subtask;
import taskTracker.model.Task;

import java.util.List;
import java.util.Set;

public interface TaskManager {


    void addTask(Task preTask);

    List<Integer> getSubTaskIdListByEpicId(int epicId);

    List<Subtask> getSubTaskListByEpicId(int epicId);

    Task getTaskById(int taskId);

    List<Task> getTaskList();

    List<Epic> getEpicList();

    List<Subtask> getSubtaskList();

    List<Task> getAllTaskList();

    void deleteAllTasks();

    void deleteTaskById(int id);

    Set<Integer> getIdOfAllTasks();

    void updateTask(Task oldTask, Status newStatus);

    void checkStatusOfEpic(Epic oldTask);

    List<Task> getHistory();

    void clearHistory();

    int getEpicIdBySubtaskId(int id);

    void setHistoryFromFile(List<Integer> idList);
}


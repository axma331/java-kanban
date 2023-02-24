package managers;

import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;

import java.util.*;

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
}


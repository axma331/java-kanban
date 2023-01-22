import java.util.*;

interface TaskManager {


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

    void updateTask(Task oldTask, TaskStatus newStatus);

    void checkStatusOfEpic(Epic oldTask);

}


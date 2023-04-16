import taskTracker.managers.Managers;
import taskTracker.managers.TaskManager;
import taskTracker.model.Status;
import taskTracker.model.Subtask;
import taskTracker.model.Task;
import taskTracker.model.Type;
import taskTracker.util.TaskMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        TaskMapper map = new TaskMapper(manager);

        System.out.println("Part 1: create new tasks end add to list");
        {
            manager.addTask(map.mapper(Type.TASK));
            manager.addTask(map.mapper(Type.EPIC));
            manager.addTask(map.mapper(Type.SUBTASK));
            manager.addTask(map.mapper(Type.SUBTASK));
            manager.addTask(map.mapper(Type.TASK));
            manager.addTask(map.mapper(Type.EPIC));
            manager.addTask(map.mapper(Type.EPIC));
            manager.addTask(map.mapper(Type.SUBTASK));
            manager.addTask(map.mapper(Type.SUBTASK));
            manager.addTask(map.mapper(Type.SUBTASK));
            manager.addTask(map.mapper(Type.EPIC));
            manager.addTask(map.mapper(Type.TASK));
            manager.addTask(map.mapper(Type.SUBTASK));
            manager.addTask(map.mapper(Type.TASK));
            System.out.println(manager);
        }
        System.out.println("Part 2: check get method");
        {
            {
                System.out.println("History is Empty:\n" + manager.getHistory().stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println("Task list:\n" + manager.getTaskList().stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println("History contains Tasks:\n" + manager.getHistory().stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println("Epic list:\n" + manager.getEpicList().stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println("History contains Tasks and Epics:\n" + manager.getHistory().stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println("Subtask list:\n" + manager.getSubtaskList().stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println("History contains Tasks, Epics and Subtask:\n" + manager.getHistory().stream().map(Object::toString).collect(Collectors.joining("\n")));
            }
            {
                manager.clearHistory();
                System.out.println("History clear: " + manager.getHistory().stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println("Get all tasks:\n" + manager.getAllTaskList().stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println("History by all tasks:\n" + manager.getHistory().stream().map(Object::toString).collect(Collectors.joining("\n")));
            }
            {
                System.out.println("Get task with id 1:\n" + manager.getTaskById(1));
                System.out.println("Get task with id 6:\n" + manager.getTaskById(6));
                System.out.println("History:\n" + manager.getHistory().stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println("Get id of all tasks: " + manager.getIdOfAllTasks());
                System.out.println("History:\n" + manager.getHistory().stream().map(Object::toString).collect(Collectors.joining("\n")));
            }
            {
                System.out.println("SubTaskList 1:\n" + manager.getSubTaskListByEpicId(map.randomTaskId(Type.EPIC)).stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println("SubTaskList 2:\n" + manager.getSubTaskListByEpicId(map.randomTaskId(Type.EPIC)).stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println("History:\n" + manager.getHistory().stream().map(Object::toString).collect(Collectors.joining("\n")));
                System.out.println(manager);
            }
        }
        System.out.println("Part 3: task delete check");
        {
            int randomTask;
            {
                randomTask = map.randomTaskId(Type.EPIC);
                System.out.println("Delete task with id " + randomTask);
                manager.deleteTaskById(randomTask);
            }
            {
                randomTask = map.randomTaskId(Type.TASK);
                System.out.println("Delete task with id " + randomTask);
                manager.deleteTaskById(randomTask);
            }
            {
                randomTask = map.randomTaskId(Type.SUBTASK);
                System.out.println("Delete task with id " + randomTask);
                manager.deleteTaskById(randomTask);
            }

            System.out.println(manager);
            System.out.println("History:\n" + manager.getHistory().stream().map(Object::toString).collect(Collectors.joining("\n")));
        }
        System.out.println("Part 4: check update of task data ");
        {
            manager.addTask(map.mapper(Type.EPIC));
            manager.addTask(map.mapper(Type.SUBTASK));
            manager.addTask(map.mapper(Type.SUBTASK));
            manager.addTask(map.mapper(Type.SUBTASK));

            int randomId;
            Task taskById;
            System.out.println("4.1 Check Task");
            {
                randomId = map.randomTaskId(Type.TASK);
                taskById = manager.getTaskById(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                manager.updateTask(taskById, Status.IN_PROGRESS);
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
            }
            System.out.println("4.2 Check epic");
            {
                randomId = map.randomTaskId(Type.SUBTASK);
                taskById = manager.getTaskById(randomId);
                int epicId = manager.getEpicIdBySubtaskId(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                System.out.println("Epic " + manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId).stream().map(Object::toString).collect(Collectors.joining("\n")));
                manager.updateTask(taskById, Status.IN_PROGRESS);
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
                System.out.println(manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId).stream().map(Object::toString).collect(Collectors.joining("\n")));
            }
            System.out.println("4.3 Check epic: in progress");
            {
                randomId = map.randomTaskId(Type.SUBTASK);
                taskById = manager.getTaskById(randomId);
                int epicId = manager.getEpicIdBySubtaskId(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                System.out.println("Epic " + manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId).stream().map(Object::toString).collect(Collectors.joining("\n")));
                manager.updateTask(taskById, Status.DONE);
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
                System.out.println(manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId).stream().map(Object::toString).collect(Collectors.joining("\n")));
            }
            System.out.println("4.4 Check epic: done");
            {
                randomId = map.randomTaskId(Type.EPIC);
                taskById = manager.getTaskById(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                List<Subtask> subtasks = manager.getSubTaskListByEpicId(randomId);
                System.out.println(subtasks);
                subtasks.forEach(x -> manager.updateTask(x, Status.DONE));
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
                System.out.println(manager.getSubTaskListByEpicId(randomId).stream().map(Object::toString).collect(Collectors.joining("\n")));
            }
        }
    }
}

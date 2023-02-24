import managers.Managers;
import managers.TaskManager;
import model.*;
import util.PreTaskType;
import util.TaskMapper;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        TaskMapper map = new TaskMapper(manager);

        System.out.println("Part 1: create new tasks end add to list");
        {
            manager.addTask(map.mapper(PreTaskType.TASK));
            manager.addTask(map.mapper(PreTaskType.EPIC));
            manager.addTask(map.mapper(PreTaskType.SUB));
            manager.addTask(map.mapper(PreTaskType.SUB));
            manager.addTask(map.mapper(PreTaskType.TASK));
            manager.addTask(map.mapper(PreTaskType.EPIC));
            manager.addTask(map.mapper(PreTaskType.EPIC));
            manager.addTask(map.mapper(PreTaskType.SUB));
            manager.addTask(map.mapper(PreTaskType.SUB));
            manager.addTask(map.mapper(PreTaskType.SUB));
            manager.addTask(map.mapper(PreTaskType.EPIC));
            manager.addTask(map.mapper(PreTaskType.TASK));
            manager.addTask(map.mapper(PreTaskType.SUB));
            manager.addTask(map.mapper(PreTaskType.TASK));
            System.out.println(manager);
        }
        System.out.println("Part 2: check get method");
        {
            {
                System.out.println("History is Empty:\n" + manager.getHistory());
                System.out.println("Task list:\n" + manager.getTaskList());
                System.out.println("History contains Tasks:\n" + manager.getHistory());
                System.out.println("Epic list:\n" + manager.getEpicList());
                System.out.println("History contains Tasks and Epics:\n" + manager.getHistory());
                System.out.println("Subtask list:\n" + manager.getSubtaskList());
                System.out.println("History contains Tasks, Epics and Subtask:\n" + manager.getHistory());
            }
            {
                manager.clearHistory();
                System.out.println("History clear: " + manager.getHistory());
                System.out.println("Get all tasks:\n" + manager.getAllTaskList());
                System.out.println("History by all tasks:\n" + manager.getHistory());
            }
            {
                System.out.println("Get task with id 1:\n" + manager.getTaskById(1));
                System.out.println("Get task with id 6:\n" + manager.getTaskById(6));
                System.out.println("History:\n" + manager.getHistory());
                System.out.println("Get id of all tasks: " + manager.getIdOfAllTasks());
                System.out.println("History:\n" + manager.getHistory());
            }
            {
                System.out.println("SubTaskList 1:\n" + manager.getSubTaskListByEpicId(map.randomTaskId(PreTaskType.EPIC)));
                System.out.println("SubTaskList 2:\n" + manager.getSubTaskListByEpicId(map.randomTaskId(PreTaskType.EPIC)));
                System.out.println("History:\n" + manager.getHistory());
                System.out.println(manager);
            }
        }
        System.out.println("Part 3: task delete check");
        {
            int randomTask;
            {
                randomTask = map.randomTaskId(PreTaskType.EPIC);
                System.out.println("Delete task with id " + randomTask);
                manager.deleteTaskById(randomTask);
            }
            {
                randomTask = map.randomTaskId(PreTaskType.TASK);
                System.out.println("Delete task with id " + randomTask);
                manager.deleteTaskById(randomTask);
            }
            {
                randomTask = map.randomTaskId(PreTaskType.SUB);
                System.out.println("Delete task with id " + randomTask);
                manager.deleteTaskById(randomTask);
            }

            System.out.println(manager);
            System.out.println("History:\n" + manager.getHistory());
        }
        System.out.println("Part 4: check update of task data ");
        {
            manager.addTask(map.mapper(PreTaskType.EPIC));
            manager.addTask(map.mapper(PreTaskType.SUB));
            manager.addTask(map.mapper(PreTaskType.SUB));
            manager.addTask(map.mapper(PreTaskType.SUB));

            int randomId;
            Task taskById;
            System.out.println("4.1 Check Task");
            {
                randomId = map.randomTaskId(PreTaskType.TASK);
                taskById = manager.getTaskById(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                manager.updateTask(taskById, Status.IN_PROGRESS);
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
            }
            System.out.println("4.2 Check epic");
            {
                randomId = map.randomTaskId(PreTaskType.SUB);
                taskById = manager.getTaskById(randomId);
                int epicId = manager.getEpicIdBySubtaskId(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                System.out.println("Epic " + manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId));
                manager.updateTask(taskById, Status.IN_PROGRESS);
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
                System.out.println(manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId));
            }
            System.out.println("4.3 Check epic: in progress");
            {
                randomId = map.randomTaskId(PreTaskType.SUB);
                taskById = manager.getTaskById(randomId);
                int epicId = manager.getEpicIdBySubtaskId(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                System.out.println("Epic " + manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId));
                manager.updateTask(taskById, Status.DONE);
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
                System.out.println(manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId));
            }
            System.out.println("4.4 Check epic: done");
            {
                randomId = map.randomTaskId(PreTaskType.EPIC);
                taskById = manager.getTaskById(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                List<Subtask> subtasks = manager.getSubTaskListByEpicId(randomId);
                System.out.println(subtasks);
                subtasks.forEach(x -> manager.updateTask(x, Status.DONE));
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
                System.out.println(manager.getSubTaskListByEpicId(randomId));
            }
        }
    }
}

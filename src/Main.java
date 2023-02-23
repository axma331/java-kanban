import managers.InMemoryTaskManager;
import model.*;

import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        TaskMapper mapper = new TaskMapper(manager);

        System.out.println("Part 1: create new tasks end add to list");
        {
            manager.addTask(mapper.mapper(PreTaskType.TASK));
            manager.addTask(mapper.mapper(PreTaskType.EPIC));
            manager.addTask(mapper.mapper(PreTaskType.SUB));
            manager.addTask(mapper.mapper(PreTaskType.SUB));
            manager.addTask(mapper.mapper(PreTaskType.TASK));
            manager.addTask(mapper.mapper(PreTaskType.EPIC));
            manager.addTask(mapper.mapper(PreTaskType.EPIC));
            manager.addTask(mapper.mapper(PreTaskType.SUB));
            manager.addTask(mapper.mapper(PreTaskType.SUB));
            manager.addTask(mapper.mapper(PreTaskType.SUB));
            manager.addTask(mapper.mapper(PreTaskType.EPIC));
            manager.addTask(mapper.mapper(PreTaskType.TASK));
            manager.addTask(mapper.mapper(PreTaskType.SUB));
            manager.addTask(mapper.mapper(PreTaskType.TASK));
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
                System.out.println("SubTaskList 1:\n" + manager.getSubTaskListByEpicId(mapper.randomTaskId(PreTaskType.EPIC)));
                System.out.println("SubTaskList 2:\n" + manager.getSubTaskListByEpicId(mapper.randomTaskId(PreTaskType.EPIC)));
                System.out.println("History:\n" + manager.getHistory());
                System.out.println(manager);
            }
        }
        System.out.println("Part 3: task delete check");
        {
            int randomTask = 0;
            {
                randomTask = mapper.randomTaskId(PreTaskType.EPIC);
                System.out.println("Delete task with id " + randomTask);
                manager.deleteTaskById(randomTask);
            }
            {
                randomTask = mapper.randomTaskId(PreTaskType.TASK);
                System.out.println("Delete task with id " + randomTask);
                manager.deleteTaskById(randomTask);
            }
            {
                randomTask = mapper.randomTaskId(PreTaskType.SUB);
                System.out.println("Delete task with id " + randomTask);
                manager.deleteTaskById(randomTask);
            }

            System.out.println(manager);
            System.out.println("History:\n" + manager.getHistory());
        }
        System.out.println("Part 4: check update of task data ");
        {
            manager.addTask(mapper.mapper(PreTaskType.EPIC));
            manager.addTask(mapper.mapper(PreTaskType.SUB));
            manager.addTask(mapper.mapper(PreTaskType.SUB));
            manager.addTask(mapper.mapper(PreTaskType.SUB));

            int randomId;
            Task taskById;
            System.out.println("4.1 Check Task");
            {
                randomId = mapper.randomTaskId(PreTaskType.TASK);
                taskById = manager.getTaskById(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                manager.updateTask(taskById, TaskStatus.IN_PROGRESS);
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
            }
            System.out.println("4.2 Check epic");
            {
                randomId = mapper.randomTaskId(PreTaskType.SUB);
                taskById = manager.getTaskById(randomId);
                int epicId = manager.getEpicIdBySubtaskId(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                System.out.println("Epic " + manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId));
                manager.updateTask(taskById, TaskStatus.IN_PROGRESS);
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
                System.out.println(manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId));
            }
            System.out.println("4.3 Check epic: in progress");
            {
                randomId = mapper.randomTaskId(PreTaskType.SUB);
                taskById = manager.getTaskById(randomId);
                int epicId = manager.getEpicIdBySubtaskId(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                System.out.println("Epic " + manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId));
                manager.updateTask(taskById, TaskStatus.DONE);
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
                System.out.println(manager.getTaskById(epicId));
                System.out.println(manager.getSubTaskListByEpicId(epicId));
            }
            System.out.println("4.4 Check epic: done");
            {
                randomId = mapper.randomTaskId(PreTaskType.EPIC);
                taskById = manager.getTaskById(randomId);
                System.out.println("Task id=" + randomId + "\n" + taskById.getClass().getSimpleName() + " " + taskById);
                List<Subtask> subtasks = manager.getSubTaskListByEpicId(randomId);
                System.out.println(subtasks);
                subtasks.forEach(x -> manager.updateTask(x, TaskStatus.DONE));
                System.out.println(taskById.getClass().getSimpleName() + " " + manager.getTaskById(randomId));
                System.out.println(manager.getSubTaskListByEpicId(randomId));
            }
        }

//        System.out.println("Part 5:");
//        Task task2 = new Task("task_task_2", "task_description_2");
//        Task task3 = new Task("task_task_3", "task_description_3");
//        Epic eTask3 = new Epic("epic_task_3", "epic_description_3");
//        manager.addTask(task2);
//        manager.addTask(task3);
//        manager.addTask(eTask2);
//        manager.addTask(eTask3);
//        Subtask sTask7 = new Subtask("sub_task_7", "sub_description_7",
//                manager.getEpicList().get(manager.getEpicList().size() - 1).getId());
//        manager.addTask(sTask4);
//        manager.addTask(sTask7);
//        System.out.println(manager);
//        System.out.println("AllTaskList:\n" + manager.getAllTaskList());
//        randomId = manager.getIdOfAllTasks().size() / 2;
//        System.out.println("getAnyTaskById: "+ manager.getTaskById(manager.getIdOfAllTasks()[randomId]));
//
//        System.out.println("Part 6:");
//        Subtask sTask8 = new Subtask("sub_task_8", "sub_description_8",
//                15);
//        manager.addTask(sTask8);
//        manager.updateTask(manager.getTaskById(16), TaskStatus.DONE);
//        System.out.println(manager);
//
//        System.out.println("Part 7:");
//        taskById = manager.getTaskById(randomId);
//        taskById = manager.getTaskById(randomId);
//        taskById = manager.getTaskById(randomId);
//        taskById = manager.getTaskById(randomId);
//        taskById = manager.getTaskById(randomId);
//        taskById = manager.getTaskById(randomId);
//        taskById = manager.getTaskById(randomId);
//        taskById = manager.getTaskById(randomId);
//        taskById = manager.getTaskById(16);
//        taskById = manager.getTaskById(16);
//        taskById = manager.getTaskById(13);
//        taskById = manager.getTaskById(randomId);
//
//
//        System.out.println(manager.getHistory());
//
//
//        System.out.println("Part 8:");
//
//        manager.deleteTaskById(33);
//        System.out.println(manager.getTaskById(33));
//        manager.deleteAllTasks();
//        System.out.println(manager);
//
//        System.out.println("Test for Strint № 5");
//        Epic eTask_51 = new Epic("epic_task_51", "epic_description_51");
//        manager.addTask(eTask_51);
//        Epic eTask_52 = new Epic("epic_task_52", "epic_description_52");
//        manager.addTask(eTask_52);
//        Subtask sTask52_1 = new Subtask("sub_task_52_1", "sub_description_52_1", eTask_52.getId());
//        Subtask sTask52_2 = new Subtask("sub_task_52_2", "sub_description_52_2", eTask_52.getId());
//        Subtask sTask52_3 = new Subtask("sub_task_52_3", "sub_description_52_3", eTask_52.getId());
//        manager.addTask(sTask52_1);
//        manager.addTask(sTask52_2);
//        manager.addTask(sTask52_3);
//        System.out.println("Get any tasks");
//        manager.getSubtaskList();
//        manager.getHistory();
//        manager.getEpicList();
//        manager.getHistory();
//        manager.getTaskById(sTask52_2.getId());
//        manager.getHistory();
//        System.out.println("Delete task from history");
//        manager.deleteTaskById(sTask52_1.getId());
//        manager.getHistory();
//        System.out.println("Delete Epic from history with his subtasks");
//        manager.deleteTaskById(eTask_52.getId());
//        manager.getHistory();
//        System.out.println("The end!");

    }

    private static final class TaskMapper<T> {
        InMemoryTaskManager manager;
        private static int idT = 0;
        private static int idE = 0;
        private static int idS = 0;

        public TaskMapper(InMemoryTaskManager manager) {
            this.manager = manager;
        }

        /**
         * Создание предварительную задачу с названием и описанием.
         *
         * @param pref тип создаваемой предварительной задачи
         * @return объект предварительной задачи
         */
        Task mapper(PreTaskType pref) {
            String type = pref.getValue();
            String name = "_task_";
            String description = "_description_";
            if (pref.equals(PreTaskType.TASK)) {
                return new Task(type + name + idT, type + description + idT++);
            } else if (pref.equals(PreTaskType.EPIC)) {
                return new Epic(type + name + idE, type + description + idE++);
            } else if (pref.equals(PreTaskType.SUB)) {
                return new Subtask(type + name + idS, type + description + idS++, randomTaskId(PreTaskType.EPIC));
            }
            return null;
        }

        public int randomTaskId(PreTaskType type) {
            Random random = new Random();
            switch (type) {
                case TASK:
                    return manager.getTaskIdList().get(random.nextInt(manager.getTaskIdList().size()));
                case EPIC:
                    return manager.getEpicIdList().get(random.nextInt(manager.getEpicIdList().size()));
                case SUB:
                    return manager.getSubTaskIdList().get(random.nextInt(manager.getSubTaskIdList().size()));
            }
            return 0;
        }
    }

    private enum PreTaskType {
        TASK("task"),
        EPIC("epic"),
        SUB("sub");

        private final String value;

        PreTaskType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}

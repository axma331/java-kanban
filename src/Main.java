public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        System.out.println("Part 1:");
        Task task = new Task("simple_task_0", "simple_description_0");
        Epic eTask = new Epic("epic_task_0", "epic_description_0");
        Task task1 = new Task("simple_task_1", "simple_description_1");
        Epic eTask1 = new Epic("epic_task_1", "epic_description_1");
        manager.addTask(task);
        manager.addTask(eTask);
        manager.addTask(task1);
        manager.addTask(eTask1);
        Subtask sTask = new Subtask("sub_task_1", "sub_description_1", manager.getEpicList().get(0).getId());
        Subtask sTask2 = new Subtask("sub_task_2", "sub_description_2", manager.getEpicList().get(0).getId());
        Subtask sTask3 = new Subtask("sub_task_3", "sub_description_3", manager.getEpicList().get(1).getId());
        manager.addTask(sTask);
        manager.addTask(sTask2);
        manager.addTask(sTask3);
        System.out.println("All tasks: '\n" + manager.getAllTaskList() + '\n');
        System.out.println("Task list:\n" + manager.getTaskList());
        System.out.println("Epic list:\n" + manager.getEpicList());
        System.out.println("Subtask list:\n" + manager.getSubtaskList());
        System.out.println(manager);
        System.out.println(manager.getIdOfAllTasks());



        System.out.println("Part 2:");
        System.out.println("SubTaskList 1:\n" + manager.getSubTaskListByEpicId(manager.getEpicList().get(0).getId()));
        System.out.println("SubTaskList 2:\n" + manager.getSubTaskListByEpicId(manager.getEpicList().get(1).getId()));
        System.out.println(manager);

        System.out.println("Part 3:");
        manager.deleteTaskById(manager.getEpicList().get(1).getId());
        System.out.println(manager);

        System.out.println("Part 4:");

        Epic eTask2 = new Epic("epic_task_2", "epic_description_2");
        manager.addTask(eTask2);
        Subtask sTask4 = new Subtask("sub_task_4", "sub_description_4",
                manager.getEpicList().get(0).getId());
        Subtask sTask5 = new Subtask("sub_task_5", "sub_description_5",
                manager.getEpicList().get(1).getId());
        Subtask sTask6 = new Subtask("sub_task_6", "sub_description_6",
                manager.getEpicList().get(0).getId());
        manager.addTask(sTask4);
        manager.addTask(sTask5);
        manager.addTask(sTask6);


        int radId = manager.getIdOfAllTasks().size() / 2;
        Task taskById = manager.getTaskById(radId);
        System.out.println(taskById.getClass() + "   " + taskById);
        manager.updateTask(taskById, TaskStatus.IN_PROGRESS);
        System.out.println(taskById.getClass() + "   " + taskById);
        radId = manager.getIdOfAllTasks().size() / 2;
        Task newTaskById = manager.getTaskById(radId);
        System.out.println(newTaskById.getClass() + "   " + newTaskById);
        System.out.println(manager);
        System.out.println(manager.getEpicList());
        System.out.println(manager.getSubTaskListByEpicId(2));
        radId = manager.getIdOfAllTasks().size() / 2;
        manager.updateTask(manager.getTaskById(radId), TaskStatus.IN_PROGRESS);
        System.out.println(manager.getSubTaskListByEpicId(2));
        System.out.println(manager);



        System.out.println("Part 5:");
        Task task2 = new Task("simple_task_2", "simple_description_2");
        Task task3 = new Task("simple_task_3", "simple_description_3");
        Epic eTask3 = new Epic("epic_task_3", "epic_description_3");
        manager.addTask(task2);
        manager.addTask(task3);
        manager.addTask(eTask2);
        manager.addTask(eTask3);
        Subtask sTask7 = new Subtask("sub_task_7", "sub_description_7",
                manager.getEpicList().get(manager.getEpicList().size() - 1).getId());
        manager.addTask(sTask4);
        manager.addTask(sTask7);
        System.out.println(manager);
        System.out.println("AllTaskList:\n" + manager.getAllTaskList());
        radId = manager.getIdOfAllTasks().size() / 2;
//        System.out.println("getAnyTaskById: "+ manager.getTaskById(manager.getIdOfAllTasks()[radId]));

        System.out.println("Part 6:");
        Subtask sTask8 = new Subtask("sub_task_8", "sub_description_8",
                15);
        manager.addTask(sTask8);
        manager.updateTask(manager.getTaskById(16), TaskStatus.DONE);
        System.out.println(manager);

        System.out.println("Part 7:");
        taskById = manager.getTaskById(radId);
        taskById = manager.getTaskById(radId);
        taskById = manager.getTaskById(radId);
        taskById = manager.getTaskById(radId);
        taskById = manager.getTaskById(radId);
        taskById = manager.getTaskById(radId);
        taskById = manager.getTaskById(radId);
        taskById = manager.getTaskById(radId);
        taskById = manager.getTaskById(16);
        taskById = manager.getTaskById(16);


        System.out.println(manager.getHistory());


        System.out.println("Part 8:");

        manager.deleteTaskById(33);
        System.out.println(manager.getTaskById(33));
        manager.deleteAllTasks();
        System.out.println(manager);

    }
}

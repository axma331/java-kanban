public class Main {

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

//        System.out.println("Part 1:");
        SimpleTask.PreSimpleTask task = new SimpleTask.PreSimpleTask("simple_task_0", "simple_description_0");
        EpicTask.PreEpicTask eTask = new EpicTask.PreEpicTask("epic_task_0", "epic_description_0");
        SimpleTask.PreSimpleTask task1 = new SimpleTask.PreSimpleTask("simple_task_1", "simple_description_1");
        EpicTask.PreEpicTask eTask1 = new EpicTask.PreEpicTask("epic_task_1", "epic_description_1");
        manager.addTask(task);
        manager.addTask(eTask);
        manager.addTask(task1);
        manager.addTask(eTask1);
        System.out.println("SimpleTaskList:\n" + manager.getSimpleTaskList());
        System.out.println("EpicTaskList:\n" + manager.getEpicTaskList());
        System.out.println(manager);

        System.out.println("Part 2:");
        SubTask.PreSubTask sTask = new SubTask.PreSubTask("sub_task_1", "sub_description_1",
                manager.getEpicTaskList().get(0).getId());
        SubTask.PreSubTask sTask2 = new SubTask.PreSubTask("sub_task_2", "sub_description_2",
                manager.getEpicTaskList().get(0).getId());
        SubTask.PreSubTask sTask3 = new SubTask.PreSubTask("sub_task_3", "sub_description_3",
                manager.getEpicTaskList().get(1).getId());
        manager.addTask(sTask);
        manager.addTask(sTask2);
        manager.addTask(sTask3);
        System.out.println("AllSubTaskList:\n" + manager.getAllSubTaskList());
        System.out.println("SubTaskList:\n" + manager.getSubTaskListByEpicId(manager.getEpicTaskList().get(1).getId()));
        System.out.println("SubTaskList:\n" + manager.getSubTaskListByEpicId(manager.getEpicTaskList().get(0).getId()));
        System.out.println(manager);

        System.out.println("Part 3:");
        manager.deleteTaskById(manager.getEpicTaskList().get(1).getId());//todo проверить получение удаленной задачи
        System.out.println(manager);

        System.out.println("Part 4:");

        EpicTask.PreEpicTask eTask2 = new EpicTask.PreEpicTask("epic_task_2", "epic_description_2");
        manager.addTask(eTask2);
        SubTask.PreSubTask sTask4 = new SubTask.PreSubTask("sub_task_4", "sub_description_4",
                manager.getEpicTaskList().get(0).getId());
        SubTask.PreSubTask sTask5 = new SubTask.PreSubTask("sub_task_5", "sub_description_5",
                manager.getEpicTaskList().get(1).getId());
        SubTask.PreSubTask sTask6 = new SubTask.PreSubTask("sub_task_6", "sub_description_6",
                manager.getEpicTaskList().get(0).getId());
        manager.addTask(sTask4);
        manager.addTask(sTask5);
        manager.addTask(sTask6);


        AbstractTask taskById = manager.getTaskById(3);
        System.out.println(taskById.getClass() + "   " + taskById);
        manager.updateTask(taskById, TaskStatus.IN_PROGRESS);
        System.out.println(taskById.getClass() + "   " + taskById);
        AbstractTask newTaskById = manager.getTaskById(3);
        System.out.println(newTaskById.getClass() + "   " + newTaskById);
        System.out.println(manager);
        System.out.println(manager.getEpicTaskList());
        System.out.println(manager.getSubTaskListByEpicId(2));
        manager.updateTask(manager.getTaskById(6), TaskStatus.IN_PROGRESS);
        System.out.println(manager.getSubTaskListByEpicId(2));
        System.out.println(manager);



        System.out.println("Part 5:");
        SimpleTask.PreSimpleTask task2 = new SimpleTask.PreSimpleTask("simple_task_2", "simple_description_2");
        SimpleTask.PreSimpleTask task3 = new SimpleTask.PreSimpleTask("simple_task_3", "simple_description_3");
        EpicTask.PreEpicTask eTask3 = new EpicTask.PreEpicTask("epic_task_3", "epic_description_3");
        manager.addTask(task2);
        manager.addTask(task3);
        manager.addTask(eTask2);
        manager.addTask(eTask3);
        SubTask.PreSubTask sTask7 = new SubTask.PreSubTask("sub_task_7", "sub_description_7",
                manager.getEpicTaskList().get(manager.getEpicTaskList().size() - 1).getId());
        manager.addTask(sTask4);
        manager.addTask(sTask7);
        System.out.println(manager);
        System.out.println("AllTaskList:\n" + manager.getAllTaskList());
        int radId = manager.getIdOfAllTasks().size() / 2;
        System.out.println("getAnyTaskById: "+ manager.getTaskById(manager.getIdOfAllTasks().get(radId)));

        System.out.println("Part 6:");
        SubTask.PreSubTask sTask8 = new SubTask.PreSubTask("sub_task_8", "sub_description_8",
                15);
        manager.addTask(sTask8);
        manager.updateTask(manager.getTaskById(17), TaskStatus.DONE);
        System.out.println(manager);

        System.out.println("Part 7:");

        manager.deleteTaskById(33);
        System.out.println(manager.getTaskById(33));
        manager.deleteAllTasks();
        System.out.println(manager);

    }
}

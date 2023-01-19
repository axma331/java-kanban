public class Main {

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        System.out.println("Part 1:");
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
        System.out.println("AllSubTaskList:\n" + manager.getSubTaskList());
        System.out.println("SubTaskList:\n" + manager.getSubTaskListById(manager.getEpicTaskList().get(1).getId()));
        System.out.println("SubTaskList:\n" + manager.getSubTaskListById(manager.getEpicTaskList().get(0).getId()));
        System.out.println(manager);

        System.out.println("Part 3:");
        manager.deleteTaskById(manager.getEpicTaskList().get(1).getId());//todo проверить получение удаленной задачи
        System.out.println(manager);

        System.out.println("Part 4:");
//        AbstractTask taskById = manager.getAnyTaskById(3).setStatus(TaskStatus.IN_PROGRESS);
        AbstractTask taskById = manager.getTaskById(1);
        System.out.println(taskById.getClass() + "   " + taskById);
        manager.updateTask(taskById, TaskStatus.IN_PROGRESS);
//        AbstractTask newTaskById = manager.getTaskById(1);
        System.out.println(taskById.getClass() + "   " + taskById);
        System.out.println(manager);

//        System.out.println("Part 5:");
//        SimpleTask.PreSimpleTask task2 = new SimpleTask.PreSimpleTask("simple_task_2", "simple_description_2");
//        SimpleTask.PreSimpleTask task3 = new SimpleTask.PreSimpleTask("simple_task_3", "simple_description_3");
//        EpicTask.PreEpicTask eTask2 = new EpicTask.PreEpicTask("epic_task_2", "epic_description_2");
//        manager.addTask(task2);
//        manager.addTask(task3);
//        manager.addTask(eTask2);
//        SubTask.PreSubTask sTask4 = new SubTask.PreSubTask("sub_task_4", "sub_description_4",
//                manager.getEpicTaskList().get(manager.getEpicTaskList().size() - 1).getId());
//        manager.addTask(sTask4);
//        System.out.println(manager);
//        System.out.println("AllTaskList:\n" + manager.getAllTaskList());
//        int radId = manager.getIdOfAllTasks().size() / 2;
//        System.out.println("getAnyTaskById: "+ manager.getTaskById(manager.getIdOfAllTasks().get(radId)));
//
//        System.out.println("Part 6:");
//
//        manager.deleteAllTasks();
//        System.out.println(manager);

//        SimpleTask.PreSimpleTask task1 = new SimpleTask.PreSimpleTask("simple_task1_1", "simple_description1_1");
//

    }
}

package util;

import managers.InMemoryTaskManager;
import managers.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.Type;

import java.util.Random;

public class TaskMapper <T>{
        InMemoryTaskManager manager;
        private static int idT = 0;
        private static int idE = 0;
        private static int idS = 0;

        public TaskMapper(TaskManager manager) {
            this.manager = (InMemoryTaskManager) manager;
        }

        /**
         * Создание предварительную задачу с названием и описанием.
         *
         * @param pref тип создаваемой предварительной задачи
         * @return объект предварительной задачи
         */
        public Task mapper(Type pref) {
            String type = pref.getValue();
            String name = "_task_";
            String description = "_description_";
            if (pref.equals(Type.TASK)) {
                return new Task(type + name + idT, type + description + idT++);
            } else if (pref.equals(Type.EPIC)) {
                return new Epic(type + name + idE, type + description + idE++);
            } else if (pref.equals(Type.SUBTASK)) {
                return new Subtask(type + name + idS, type + description + idS++, randomTaskId(Type.EPIC));
            }
            return null;
        }

        public int randomTaskId(Type type) {
            Random random = new Random();
            switch (type) {
                case TASK:
                    return manager.getTaskIdList().get(random.nextInt(manager.getTaskIdList().size()));
                case EPIC:
                    return manager.getEpicIdList().get(random.nextInt(manager.getEpicIdList().size()));
                case SUBTASK:
                    return manager.getSubTaskIdList().get(random.nextInt(manager.getSubTaskIdList().size()));
            }
            return 0;
        }
    }

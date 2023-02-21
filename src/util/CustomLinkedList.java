package util;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomLinkedList {
    private Node head = null;
    private Node tail = null;
    private final Map<Integer, Node> searchByHistory = new HashMap<>();


    public int size() {
        return searchByHistory.size();
    }

    public void add(Task task) {
        removeNode(searchByHistory.get(task.getId()));
        linkLast(searchByHistory.put(task.getId(), new Node(task)));
    }

    public void linkLast(Node node) {
        final Node t = this.tail;
        tail = node;
        if (t == null) {
            head = node;
        } else {
            t.next = node;
        }
    }

    public void removeFirst() {
        removeNode(head);
    }
    public void removeNode(Node node) {
        if (node == null) return;
        final Node prev = node.prev;
        final Node next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
    }

    public void remove(int id) {
        removeNode(searchByHistory.get(id));
    }

    public List<Task> getTasks() {
        Node node = head;
        if (node == null) return null; //todo стоит ли возвращать null или все же лучше пустой список?

        List<Task> list = new ArrayList<>(searchByHistory.size());
        do {
            list.add(node.getTask());
            node = head.next;
        } while (node != null);
        return list;
    }

    private final static class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Task task) {
            this.task = task;
            this.prev = null;
            this.next = null;
        }

        public Task getTask() {
            return task;
        }
    }
}

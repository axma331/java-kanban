package taskTracker.util;

import taskTracker.model.Task;

import java.util.*;

public class CustomLinkedList {
    private Node head = null;
    private Node tail = null;
    private final Map<Integer, Node> searchByHistory = new HashMap<>();


    public int size() {
        return searchByHistory.size();
    }

    public void add(Task task) {
        if (Objects.nonNull(task)) {
            removeNode(searchByHistory.get(task.getId()));
            Node node = new Node(task);
            searchByHistory.put(task.getId(), node);
            linkLast(node);
        }
    }

    public void linkLast(Node node) {
        final Node t = this.tail;
        tail = node;
        if (t == null) {
            head = node;
        } else {
            t.next = node;
            node.prev = t;
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
        searchByHistory.remove(node.task.getId());
    }

    public void remove(int id) {
        removeNode(searchByHistory.get(id));
    }

    public void clear() {
        while (head != null) removeFirst();
        searchByHistory.clear();
    }

    public List<Task> getTasks() {
        List<Task> list = new ArrayList<>(searchByHistory.size());

        Node node = head;
        if (node == null) return list;

        do {
            list.add(node.getTask());
            node = node.next;
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

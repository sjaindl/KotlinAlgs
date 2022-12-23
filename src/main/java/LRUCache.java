import java.util.*;

class LRUCache {
    
    private int capacity;
    private LRUList queue;
    private Map<Integer, Node> lookup;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        queue = new LRUList();
        lookup = new HashMap();
    }
    
    public int get(int key) {
        Node node = lookup.get(key);
        if (node == null) return -1;
        promote(node);
        
        return node.value;
    }
    
    public void put(int key, int value) {
        Node node = lookup.get(key);
        if (node == null) {
            // not in cache, insert
            node = new Node(key, value);
            if (queue.size() == capacity) {
                Node removed = queue.removeLast();
                lookup.remove(removed.key);
            }
            queue.addFirst(node);
            lookup.put(key, node);
        } else {
            // update cache
            node.value = value;
            promote(node);
        }
    }
    
    private void promote(Node node) {
        queue.remove(node);
        queue.addFirst(node);
    }

    class LRUList {
        Node head;
        Node tail;
        int size;

        int size() {
            return size;
        }

        void remove(Node node) {
            if (size == 0) { return; }
            if (size == 1) { head = null; tail = null; }
            else if (node == head) { head = head.next; head.prev = null; }
            else if (node == tail) { tail = tail.prev; tail.next = null; }
            else { 
                Node prev = node.prev;
                Node next = node.next;
                prev.next = next;
                next.prev = prev;
                node.prev = null; 
                node.next = null;
             }


            size--;
        }

        Node removeLast() {
            if (tail == null) { return null; }
            if (size == 1) {
                Node temp = tail;
                head = null;
                tail = null;
                size--;
                return temp;
            }

            Node oldTail = tail;
            Node temp = tail.prev;
            temp.next = null;
            tail = temp;
            size--;

            return oldTail;
        }

        void addFirst(Node node) {
            if (head == null) {
                head = node; 
                tail = node;
            } else {
                node.next = head;
                head.prev = node;
                head = node;
            }
            size++;
        }
    }
    
    class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }

        Node(int key, int value, Node prev, Node next) {
            this.key = key;
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (!(obj instanceof Node)) return false;
            if (obj == this) return true;
            Node other = (Node) obj;

            return other.key == key && other.value == value;
        }

        @Override
        public int hashCode() {
            
            return Objects.hash(key, value);
        }
    }

    public static void main(String[] args) {
        LRUCache lru = new LRUCache(2);
        lru.put(2, 2);
        lru.put(1, 1);
        System.out.println(lru.get(1));
        lru.put(3, 3);
        System.out.println(lru.get(2));
        lru.put(4, 4);
        System.out.println(lru.get(1));
        System.out.println(lru.get(3));
        System.out.println(lru.get(4));
    }
}
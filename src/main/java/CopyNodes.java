/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/
import java.util.*;

class CopyNode {
    class Node {
        int val;
        Node next;
        Node random;
    
        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        if (head == null) return null;
        
        NodeMapping original = buildMapping(head);
        Node newHead = copyNodes(head);
        NodeMapping copy = buildMapping(newHead);
        
        copyRandom(original, copy, newHead);
        
        return newHead;
    }
    
    private void copyRandom(NodeMapping original, NodeMapping copy, Node head) {
        Node current = head;
        int index = 0;
        while (current != null) {
            Node ori = original.indexToNodeMap.get(index);
            
            if (ori.random != null) {
                int oriIndex = original.nodeToIndexMap.get(ori.random);
                Node copyNode = copy.indexToNodeMap.get(oriIndex);
                current.random = copyNode;
            }
            
            current = current.next;
            index++;
        }
    }
    
    private Node copyNodes(Node head) {
        Node newHead = new Node(head.val);
        
        Node prev = newHead;
        Node current = head.next;
        while (current != null) {
            Node newNode = new Node(current.val);
            prev.next = newNode;
            
            prev = newNode; //copied node
            current = current.next; //ori node
        }
        
        return newHead;
    }
    
    private NodeMapping buildMapping(Node head) {
        NodeMapping mapping = new NodeMapping();
        
        Node current = head;
        int index = 0;
        while (current != null) {
            mapping.nodeToIndexMap.put(current, index);
            mapping.indexToNodeMap.put(index, current);
            
            current = current.next;
            index++;
        }
        
        return mapping;
    }
    
    class NodeMapping {
        Map<Integer, Node> indexToNodeMap = new HashMap<>();
        Map<Node, Integer> nodeToIndexMap = new HashMap<>();
    }
}

/* class NodeMapping (ori + copy)
   Map<Integer, Node> indexToNodeMap O(N) space
   Map<Node, Integer> nodeToIndexMap O(N) space
 
 1. iterate through ori list -> store in NodeMapping  (ori node only)   O(n)
 2. copy head  O(1)
 3. copy each node that can be reached with next ptr (without random, null) + store in pairs O(1)
 4. random: lookup & copy
 
 */

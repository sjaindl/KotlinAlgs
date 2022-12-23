/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class ReverseLinkedList {

    // 1 <- 2 <- 3 <- 4
    // 1 <- 2 <- 3 <- 4


    public ListNode reverseListIterative(ListNode head) {
        ListNode prev = null;

        while(head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }

        return prev;
    }

    public ListNode reverseList(ListNode head) {
        // 1 -> 2 -> 3 -> 4
        // 1 <- 2 <- 3 <- 4
        
        // 4 -> 3 -> 2 -> 1
        //SLL, head
        
        return reverseListRec(head, null);
    }
    
    //1,null
    //2,1
    //3,2
    //4,3
    //null,4
    
    private ListNode reverseListRec(ListNode head, ListNode prev) {
        if (head == null) {
            return prev;
        }
        
        ListNode next = head.next; //4
        head.next = prev; 
        return reverseListRec(next, head);
    }

    public static void main(String[] args) {
        ReverseLinkedList sum = new ReverseLinkedList();

        ListNode tail = new ListNode(5);
        ListNode ln4 = new ListNode(4, tail);
        ListNode ln3 = new ListNode(3, ln4);
        ListNode ln2 = new ListNode(2, ln3);
        ListNode root = new ListNode(1, ln2);

        ListNode reversed = sum.reverseList(root);
        
        System.out.println(reversed);

        ListNode tail_it = new ListNode(5);
        ListNode ln4_it = new ListNode(4, tail_it);
        ListNode ln3_it = new ListNode(3, ln4_it);
        ListNode ln2_it = new ListNode(2, ln3_it);
        ListNode root_it = new ListNode(1, ln2_it);

        ListNode reversedIt = sum.reverseListIterative(root_it);
        
        System.out.println(reversedIt);        
    }
}
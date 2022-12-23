public class SortedListMerger {
    
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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        //[1,2,4]
        //[1,3,4]
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        
        ListNode head = l1.val < l2.val ? l1 : l2; //1
        ListNode cur = head; //1,1,2
        
        if (l1.val < l2.val) {
            l1 = l1.next;
        } else {
            l2 = l2.next;
        }
        
        //[4]
        //[3,4]
        
        while (l1 != null || l2 != null) {
            if (l1 == null) {
                cur.next = l2;
                break;
            } else if (l2 == null) {
                cur.next = l1;
                break;
            } else if (l1.val < l2.val) {
                cur.next = l1;
                cur = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                cur = l2;
                l2 = l2.next;
            }
        }
        
        return head;
    }
}
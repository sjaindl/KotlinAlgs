/**
 * Definition for singly-linked list.
 */


class AddTwoNumbersSolution {

    public static void main(String[] args) {
        ThreeSum sum = new ThreeSum();
        int[] nums = { -1, 0, 1, 2, -1, -4 };
        System.out.println(sum.threeSum(nums));
    }
  
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
      if(l1 == null && l2 == null) return null;
      
      int carry = 0;
      ListNode result = ListNode();
      ListNode head = ListNode();
      
      while(l1 != null && l2 != null) {
          int value = carry;
          if (l1 != null) value += l1.val;
          if (l2 != null) value += l2.val;
          
          if (head.val == null) {
              result = new ListNode(value);
              head = result;
          } else {
              result.next = new ListNode(value);
              result = result.next;
          }
          
          if(l1 != null && l2 != null && l1.val + l2.val + carry > 9) {
              carry = 1;
          } else {
              carry = 0;
          }
      }
      
      if (carry == 1) {
          result.next = new ListNode(carry);
      }
      
      return head;
  }
}

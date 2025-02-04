class ListNode{
    int data;
    ListNode next;

    ListNode(int data) {
        this.data = data;
        this.next = null;
    }
    
    ListNode(int data, ListNode next) {
        this.data = data;
        this.next = next;
    }
}

class LinkedList {
    
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        
        ListNode slow = head, fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;        
            fast = fast.next.next;  
            
            if (slow == fast) {
                return true;
            }
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = head.next;
        
        System.out.println("Has Cycle? " + list.hasCycle(head));
    }
}
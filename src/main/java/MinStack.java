import java.util.*;

class MinStack {
    
    Stack<StackElement> stack;
    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<>();
    }
    
    public void push(int val) {
        int min = val;
        if (!stack.isEmpty()) {
            min = Math.min(min, getMin());
        }
        StackElement element = new StackElement(val, min);
        stack.push(element);
    }
    
    public void pop() {
        stack.pop();
    }
    
    public int top() {
        return stack.peek().value;
    }
    
    public int getMin() {
        return stack.peek().minValue;
    }
    
    class StackElement {
        int value;
        int minValue;
        
        StackElement(int value, int minValue) {
            this.value = value;
            this.minValue = minValue;
        }
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
import java.util.*;

;public class Codec_BFS {
    /* 
 * Definition for a binary tree node.
 * */

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    final char NULL_ID = '*';

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "";
        
        StringBuilder serialized = new StringBuilder(); //12**34**5
        
        //bfs serialization:
        LinkedList<TreeNode> queue = new LinkedList<>();//5
        queue.addFirst(root); 
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.removeLast(); //4
            serialized.append(((char) current.val)); // - '0'

            if (current.left != null) {
                queue.addFirst(current.left);
            } 
            else serialized.append(NULL_ID);

            if (current.right != null) queue.addFirst(current.right);
            else serialized.append(NULL_ID);
        }
        
        return serialized.toString();
    }
    
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0 || data.charAt(0) == NULL_ID) {
            return null;
        }
        
        return deserialize(data.toCharArray(), 0).root;
    }
        
/* 
    12**34**5
    
                1
        2               3
                    4       5


                1
        2
       * *

*/
    
    private SubTree deserialize(char[] data, int index) { 
        if (index >= data.length || data[index] == NULL_ID) {
            // BC
            return new SubTree(1, null);
        }
        
        int val = (int) (data[index]);
        
        TreeNode root = new TreeNode(val);
        
        SubTree left = deserialize(data, index + 1); 
        SubTree right = deserialize(data, index + 1 + left.numElements); 
        
        root.left = left.root;
        root.right = right.root; 
        
        return new SubTree(left.numElements + right.numElements + 1, root);
    }

    
    class SubTree {
        int numElements;
        TreeNode root;
        
        SubTree(int numElements, TreeNode root) {
            this.numElements = numElements;
            this.root = root;
        }
    }

    public static void main(String[] args) {
        Codec_BFS codec = new Codec_BFS();
        TreeNode root = codec.deserialize("12");
        String serial = codec.serialize(root);
        System.out.println(serial);
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
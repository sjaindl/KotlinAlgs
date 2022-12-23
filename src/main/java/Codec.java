import java.util.*;

public class Codec {
    /* 
 * Definition for a binary tree node.
 * */

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    final char NULL_ID = '*';

    // public String rserialize(TreeNode root, String str) {
    //     // Recursive serialization.
    //     if (root == null) {
    //       str += "null,";
    //     } else {
    //       str += str.valueOf(root.val) + ",";
    //       str = rserialize(root.left, str);
    //       str = rserialize(root.right, str);
    //     }
    //     return str;
    //   }
    
    //   // Encodes a tree to a single string.
    //   public String serialize(TreeNode root) {
    //     return rserialize(root, "");
    //   }

    // public TreeNode rdeserialize(List<String> l) {
    //     // Recursive deserialization.
    //     if (l.get(0).equals("null")) {
    //       l.remove(0);
    //       return null;
    //     }
    
    //     TreeNode root = new TreeNode(Integer.valueOf(l.get(0)));
    //     l.remove(0);
    //     root.left = rdeserialize(l);
    //     root.right = rdeserialize(l);
    
    //     return root;
    //   }
    
    //   // Decodes your encoded data to tree.
    //   public TreeNode deserialize(String data) {
    //     String[] data_array = data.split(",");
    //     List<String> data_list = new LinkedList<String>(Arrays.asList(data_array));
    //     return rdeserialize(data_list);
    //   }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeRec(root, sb);
        return sb.toString();
    }

    public void serializeRec(TreeNode root, StringBuilder sb) {
        if (root == null) sb.append(NULL_ID);
        else {
            sb.append(root.val);
            serializeRec(root.left, sb);
            serializeRec(root.right, sb);
        }
    }

     // Decodes your encoded data to tree.
     public TreeNode deserialize(String data) {
        List<Character> strings = new ArrayList<>();
        for (char character : data.toCharArray()) {
            strings.add(character);
        }

        return deserializeRec(strings);
    }

    private TreeNode deserializeRec(List<Character> strings) {
        if (strings.isEmpty()) return null;

        char value = strings.remove(0);
        if (value == NULL_ID) {
            return null;
        }

        TreeNode root = new TreeNode(value - '0');

        root.left = deserializeRec(strings);
        root.right = deserializeRec(strings);

        return root;
    }

    // Encodes a tree to a single string.
    public String serializeIterative(TreeNode root) {
        if (root == null) return "";
        
        StringBuilder serialized = new StringBuilder(); //12**34**5
        
        //preorder serialization:
        LinkedList<TreeNode> queue = new LinkedList<>();//5
        queue.addFirst(root); 
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.removeFirst(); //4
            serialized.append(current.val);
            
            // if (queue.isEmpty() && current.left == null && current.right == null) {
            //     break; //no unnecessary null nodes at end
            // }

            if (current.right == null) {
                    serialized.append(NULL_ID);
            }
            else queue.addFirst(current.right);
            
            if (current.left == null) serialized.append(NULL_ID);
            else queue.addFirst(current.left);
        }
        
        return serialized.toString(); //.replace(String.valueOf(NULL_ID), "null");
    }

    public TreeNode deserializeWrong(String data) {
        if (data == null || data.length() == 0 || data.charAt(0) == NULL_ID) {
            return null;
        }
        
        return deserialize(data.toCharArray(), 0).root;
    }
    
    private SubTree deserialize(char[] data, int index) { //0 
        if (index >= data.length || data[index] == NULL_ID) {
            // BC
            return new SubTree(1, null);
        }
        
        int val = data[index] - '0'; //+ '0'
        
        TreeNode root = new TreeNode(val); // 1
        
        SubTree left = deserialize(data, index + 1); //(3, 2)
        SubTree right = deserialize(data, index + 1 + left.numElements); //4
        
        root.left = left.root;  // 2
        root.right = right.root; 
        
        return new SubTree(left.numElements + right.numElements + 1, root); //3
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
        Codec codec = new Codec();
        TreeNode root = new Codec().new TreeNode(1);
        root.left = new Codec().new TreeNode(2);
        root.right = new Codec().new TreeNode(5);
        root.left.left = new Codec().new TreeNode(3);
        root.left.right = new Codec().new TreeNode(4);

        String serial = codec.serialize(root);
        System.out.println(serial);

        TreeNode des = codec.deserialize(serial);
        String seri = codec.serialize(des);
        System.out.println(seri);
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));

public class FlipEqui {

    /**
 * Definition for a binary tree node.*/ 
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // recursive
    public boolean flipEquivRecCorrect(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null || root2 == null || root1.val != root2.val) return false;

        return flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right)
        || flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left);
    }

    // Canonical
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null || root2 == null || root1.val != root2.val) return false;

        int root1Left = root1.left != null ? root1.left.val : -1;
        int root1Right = root1.right != null ? root1.right.val : -1;
        int root2Left = root2.left != null ? root2.left.val : -1;
        int root2Right = root2.right != null ? root2.right.val : -1;

        TreeNode root1Smaller = root1Left < root1Right ? root1.left : root1.right;
        TreeNode root1Bigger = root1Left < root1Right ? root1.right : root1.left;
        TreeNode root2Smaller = root2Left < root2Right ? root2.left : root2.right;
        TreeNode root2Bigger = root2Left < root2Right ? root2.right : root2.left;

        return flipEquiv(root1Smaller, root2Smaller) && flipEquiv(root1Bigger, root2Bigger);
    }

    public boolean flipEquivComplicatedAndWrong(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null && root2 != null) return false;
        if (root2 == null && root1 != null) return false;
        if (root1.val != root2.val) return false;
        if (isLeaveNode(root1) && isLeaveNode(root2)) return true;
        if (root1.left != null && root1.right != null && root2.left == null || root2.right == null) return false;
        if (root2.left != null && root2.right != null && root1.left == null || root1.right == null) return false;
        if (root1.left == null && root2.left != null) return flipEquiv(root1.right, root2.left);
        if (root1.right == null && root2.right != null) return flipEquiv(root1.left, root2.right);
        if (root1.left == null && root2.left == null) return flipEquiv(root1.right, root2.right);
        if (root1.right == null && root2.right == null) return flipEquiv(root1.left, root2.left);
        
        if (root1.left.val != root2.left.val || root1.right.val != root2.right.val) {
            //must flip
            return flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left);
        }
        
        return flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right)
            || flipEquiv(root1.right, root2.left) && flipEquiv(root1.left, root2.right);
    }
    
    private boolean isLeaveNode(TreeNode node) {
        return node.left == null && node.right == null;
    }

    public static void main(String[] args) {
        FlipEqui flip = new FlipEqui();

        TreeNode root1 = new FlipEqui().new TreeNode(1);
        root1.left = new FlipEqui().new TreeNode(2);
        root1.right = new FlipEqui().new TreeNode(3);
        
        TreeNode root2 = new FlipEqui().new TreeNode(1);
        root2.left = new FlipEqui().new TreeNode(2);
        root2.left.left = new FlipEqui().new TreeNode(3);

        flip.flipEquiv(root1, root2);
    }
}
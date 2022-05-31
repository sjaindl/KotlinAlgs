import java.io.*;
import java.util.*;

class Solution {

    static class Node {

        int cost;
        Node[] children;
        Node parent;

        Node(int cost) {
            this.cost = cost;
            children = null;
            parent = null;
        }
    }

    static class SalesPath {

        static int getCheapestCost(Node rootNode) {
            if (rootNode == null) return 0;

            int minValue = Integer.MAX_VALUE;

            LinkedList<Node> queue = new LinkedList<>();
            queue.add(rootNode);

            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                if (cur.children == null || cur.children.length == 0) {
                    minValue = Math.min(minValue, cur.cost);
                    continue;
                }

                for (int childIndex = 0; childIndex < cur.children.length; childIndex++) {
                    Node child = cur.children[childIndex];
                    child.cost += cur.cost;
                    queue.add(child);
                }
            }

            return minValue;
        }

        static int getCheapestCostDFS(Node rootNode) {
            if (rootNode == null) return 0;

            return getCheapestCostDFSRec(rootNode, rootNode.cost);
        }

        private static int getCheapestCostDFSRec(Node rootNode, int pathCost) {
            if (rootNode == null || rootNode.children == null) return pathCost;

            //System.out.println(rootNode.cost);
            //System.out.println(pathCost);

            int minCost = Integer.MAX_VALUE;

            for (int childIndex = 0; childIndex < rootNode.children.length; childIndex++) {
                Node child = rootNode.children[childIndex];
                minCost = Math.min(minCost, getCheapestCostDFSRec(child, pathCost + child.cost));
            }

            return minCost;
        }
    }


    /*********************************************
     * Driver program to test above method     *
     *********************************************/

    public static void main(String[] args) {
        Node one_3 = new Node(1);

        Node one_2 = new Node(1);
        one_2.children = new Node[]{one_3};
        Node ten = new Node(10);

        Node four = new Node(4);
        Node two = new Node(2);
        two.children = new Node[]{one_2};
        Node zero = new Node(0);
        zero.children = new Node[]{ten};
        Node one = new Node(1);
        Node five_2 = new Node(5);

        Node five = new Node(5);
        five.children = new Node[]{four};
        Node three = new Node(3);
        three.children = new Node[]{two, zero};
        Node six = new Node(6);
        six.children = new Node[]{one, five};

        Node root = new Node(0);
        root.children = new Node[]{five, three, six};

        int cost = SalesPath.getCheapestCostDFS(root);
        System.out.println(cost);

        int cost2 = SalesPath.getCheapestCost(root);
        System.out.println(cost2);
    }
}

/*
SalesPath
getCheapestCost(Node rootNode)
input: Node rootNode (= Honda company)
  .. directed weighted tree
  .. any num of children (car distributors)
  .. back link to parent
  .. cost = shipping costs to it of a car

  .. cars shipped top-down, starting at root
  .. leaf nodes: dealers selling cars

  .. sales path with sum of costs

  .. not sorted

output: int minimumCost (sum along path with min cost)

e.g. 0→3→0→10, cost = 13
min = 0->3->2->1->1 = 7 or 0->6->1 = 7

BCR: O(N) runtime

BF:
start at root.
BFS vs DFS

BFS iteratively, no call stack space, using queue

queue = new LinkedList<Node>()  // holding sum of costs along the path
add root to queue
while !queue is empty:
  val next = queue.pop()

  if has no child: update minCost

  for each child:
    add to queue: next

return minCost
*/

/*
question:

Sales Path
The car manufacturer Honda holds their distribution system in the form of a tree (not necessarily binary). The root is the company itself, and every node in the tree represents a car distributor that receives cars from the parent node and ships them to its children nodes. The leaf nodes are car dealerships that sell cars direct to consumers. In addition, every node holds an integer that is the cost of shipping a car to it.

Take for example the tree below:

alt

A path from Honda’s factory to a car dealership, which is a path from the root to a leaf in the tree, is called a Sales Path. The cost of a Sales Path is the sum of the costs for every node in the path. For example, in the tree above one Sales Path is 0→3→0→10, and its cost is 13 (0+3+0+10).

Honda wishes to find the minimal Sales Path cost in its distribution tree. Given a node rootNode, write a function getCheapestCost that calculates the minimal Sales Path cost in the tree.

Implement your function in the most efficient manner and analyze its time and space complexities.

For example:

Given the rootNode of the tree in diagram above

Your function would return:

7 since it’s the minimal Sales Path cost (there are actually two Sales Paths in the tree whose cost is 7: 0→6→1 and 0→3→2→1→1)

Constraints:

[time limit] 5000ms

[input] Node rootNode

0 ≤ rootNode.cost ≤ 100000
[output] integer
 */
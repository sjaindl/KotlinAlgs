// https://www.geeksforgeeks.org/graph-coloring-applications/

// https://www.youtube.com/watch?v=-gOh1aG0_zQ
/*
Process vertices in order of highest in-degree
assign min color after checking all neighbours
 Welsh–Powell Algorithm, still not optimal but better that basic greedy algo
 */

// Greedy:
// https://www.javatpoint.com/how-to-find-chromatic-number-graph-coloring-algorithm
/*
Step 1: In the first step, we will color the first vertex with first color.

Step 2: Now, we will one by one consider all the remaining vertices (V -1) and do the following:

We will color the currently picked vertex with the help of lowest number color if and only if the same color is not used to color any of its adjacent vertices.
If its adjacent vertices are using it, then we will select the next least numbered color.
If we have already used all the previous colors, then a new color will be used to fill or assign to the currently picked vertex.
Disadvantages of Greedy Algorithm

The greedy algorithm contains a lot of drawbacks, which are described as follows:

In the greedy algorithm, the minimum number of colors is not always used.
Sometimes, the number of colors is based on the order in which the vertices are processed.
 */
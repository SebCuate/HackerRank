/**
 * NAME  BST
 * Column	TYPE
 * N		Integer
 * P		Integer
 * 
1 2
2 4
3 2
4 15
5 6
6 4
7 6
8 9
9 11
10 9
11 15
12 13
13 11
14 13
15 NULL
 * 
 */

/*Querie para
 * You are given a table, BST, containing two columns: N and P, where N represents the value of a node in Binary Tree, and P is the parent of N.
 * 
 * Column	TYPE
 * N		Integer
 * P		Integer
 * 
 * Write a query to find the node type of Binary Tree ordered by the value of the node. Output one of the following for each node:
 * Root: If node is root node.
 * Leaf: If node is leaf node.
 * Inner: If node is neither root nor leaf node.
 * */
SELECT 
    CASE
        WHEN P is null then CONCAT (N, ' ','Root')
        WHEN N in (Select P FROM BST) then CONCAT (N, ' ','Inner')
        ELSE CONCAT (N, ' ','Leaf')
    END
FROM BST
order by N asc


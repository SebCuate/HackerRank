package dataStructures.trees;

import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

import java.util.*;

/**
 * This exercise have the following rules
 * -In-order traversal
 * -Switch the subsequents subtrees of the specified DEPTH
 * -Root is considered depth 1
 * 
 *  
 * @author SebasCU
 *
 */
public class AlgoSwapSolucion01 {
	
//	static Node tree = new Node(1);

	public static void main(String... arg) {
		
		//Build a Binary Tree
    	Node tree = new Node(1); //Depth 1
        tree.left = new Node(2); //Depth 2
        tree.right = new Node(3);
        tree.left.left = new Node(4); //Depth 3
        tree.left.right = new Node(5);
        tree.right.left = new Node(6);
        tree.right.right = new Node(7);
        tree.left.left.left = new Node(8); //Depth 4
        tree.left.left.right = new Node(9);
        tree.left.right.left = new Node(10);
        tree.left.right.right = new Node(11);
        tree.right.left.left = new Node(12);
        tree.right.left.right = new Node(13);
        tree.right.right.left = new Node(14);
        tree.right.right.right = new Node(15);
		tree.left.left.left.left = new Node(16); //Depth 5
        tree.left.left.left.right = new Node(17);
        tree.left.left.right.left = new Node(18);
        tree.left.left.right.right = new Node(19);
        tree.left.right.left.left = new Node(20);
        tree.left.right.left.right = new Node(21);
        tree.left.right.right.left = new Node(22);
        tree.left.right.right.right = new Node(23);
		tree.right.left.left.left = new Node(24);
        tree.right.left.left.right = new Node(25);
        tree.right.left.right.left = new Node(26);
        tree.right.left.right.right = new Node(27);
        tree.right.right.left.left = new Node(28);
        tree.right.right.left.right = new Node(29);
        tree.right.right.right.left = new Node(30);
        tree.right.right.right.right = new Node(31);
		
		
//		n = sc.nextInt();
//		int[][] data = new int[n][2];
//		for (int i = 0; i < n; i++) {
//			data[i][0] = sc.nextInt();
//			data[i][1] = sc.nextInt();
//		}
//		tree = ConstuctTree(data);
        
        Scanner sc = new Scanner(System.in);
        int t, k;
		t = sc.nextInt();
		while (t-- > 0) {
			k = sc.nextInt();
			levelWise(tree, k);
			inOrder(tree);
		}
	}

	private static void levelWise(Node root, int k) {
		Stack<Node> currentlevel = new Stack<>();
		Stack<Node> nextlevel = new Stack<>();
		int level = 1;
		Node temp;
		currentlevel.push(root);
		while (!currentlevel.empty()) {
			temp = currentlevel.peek();
			currentlevel.pop();
			if (temp.left != null)
				nextlevel.push(temp.left);
			if (temp.right != null)
				nextlevel.push(temp.right);
			if (level % k == 0) {
				Node n = temp.left;
				temp.left = temp.right;
				temp.right = n;
			}
			if (currentlevel.empty()) {
				Stack<Node> t = currentlevel;
				currentlevel = nextlevel;
				nextlevel = t;
				level++;
			}
		}
	}
	
	private static void inOrder(Node node) {
		
		if (node == null)
            return;
 
        // First recur on left child
        inOrder(node.left);
 
        // Then print the data of node
        System.out.print(node.data + " ");
 
        // Now recur on right child
        inOrder(node.right);
        
	}

	private static Node ConstuctTree(int[][] data) {
		Node root = new Node(1);
		Queue<Node> q = new LinkedList<>();
		q.add(root);
		for (int i = 0; i < data.length; i++) {
			Node left, right;
			if (data[i][0] != -1)
				left = new Node(data[i][0]);
			else
				left = null;
			if (data[i][1] != -1)
				right = new Node(data[i][1]);
			else
				right = null;

			Node temp = q.remove();
			temp.left = left;
			temp.right = right;

			if (left != null)
				q.add(left);
			if (right != null)
				q.add(right);
		}
		return root;
	}
	
	
	
}

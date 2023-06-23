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
	static NodeAlgoSwapSolucion01 root = new NodeAlgoSwapSolucion01(1);

	public static void main(String... arg) {
		Scanner sc = new Scanner(System.in);
		int n, t, k;
		n = sc.nextInt();
		int[][] tree = new int[n][2];
		for (int i = 0; i < n; i++) {
			tree[i][0] = sc.nextInt();
			tree[i][1] = sc.nextInt();
		}
		root = ConstuctTree(tree);
		t = sc.nextInt();
		while (t-- > 0) {
			k = sc.nextInt();
			levelWise(root, k);
			InOrderRec(root);
			System.out.println();
		}
	}

	private static void levelWise(NodeAlgoSwapSolucion01 root, int k) {
		Stack<NodeAlgoSwapSolucion01> currentlevel = new Stack<>();
		Stack<NodeAlgoSwapSolucion01> nextlevel = new Stack<>();
		int level = 1;
		NodeAlgoSwapSolucion01 temp;
		currentlevel.push(root);
		while (!currentlevel.empty()) {
			temp = currentlevel.peek();
			currentlevel.pop();
			if (temp.left != null)
				nextlevel.push(temp.left);
			if (temp.right != null)
				nextlevel.push(temp.right);
			if (level % k == 0) {
				NodeAlgoSwapSolucion01 n = temp.left;
				temp.left = temp.right;
				temp.right = n;
			}
			if (currentlevel.empty()) {
				Stack<NodeAlgoSwapSolucion01> t = currentlevel;
				currentlevel = nextlevel;
				nextlevel = t;
				level++;
			}
		}
	}

	private static void InOrderRec(NodeAlgoSwapSolucion01 root) {
		if (root == null)
			return;
		InOrderRec(root.left);
		sout(root.data);
		InOrderRec(root.right);
	}

	private static NodeAlgoSwapSolucion01 ConstuctTree(int[][] tree) {
		NodeAlgoSwapSolucion01 root = new NodeAlgoSwapSolucion01(1);
		Queue<NodeAlgoSwapSolucion01> q = new LinkedList<>();
		q.add(root);
		for (int i = 0; i < tree.length; i++) {
			NodeAlgoSwapSolucion01 left, right;
			if (tree[i][0] != -1)
				left = new NodeAlgoSwapSolucion01(tree[i][0]);
			else
				left = null;
			if (tree[i][1] != -1)
				right = new NodeAlgoSwapSolucion01(tree[i][1]);
			else
				right = null;

			NodeAlgoSwapSolucion01 temp = q.remove();
			temp.left = left;
			temp.right = right;

			if (left != null)
				q.add(left);
			if (right != null)
				q.add(right);
		}
		return root;
	}

	private static void sout(int info) {
		System.out.printf("%d ", info);
	}
}

class NodeAlgoSwapSolucion01 {
	int data;
	NodeAlgoSwapSolucion01 left, right;

	NodeAlgoSwapSolucion01(int item) {
		data = item;
		left = null;
		right = null;
	}
}

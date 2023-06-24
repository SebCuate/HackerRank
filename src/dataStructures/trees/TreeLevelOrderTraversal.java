package dataStructures.trees;

import java.util.*;
import java.io.*;


public class TreeLevelOrderTraversal {

	private static void levelOrder(Node root) {
		
		int h = height(root);
		
		for (int i = 0; i <= h; i++) {
			printLevel(root, i);
		}

    }
	
	private static void printLevel(Node root, int level) {
		if (root == null)
            return;
        if (level == 0)
            System.out.print(root.data + " ");
        else if (level > 0) {
        	printLevel(root.left, level - 1);
        	printLevel(root.right, level - 1);
        }
	}
	
	private static int height(Node root) {
      	if(root == null) {
            return -1;
        }
        return Math.max(height(root.left), height(root.right)) + 1;
    }

	public static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
        	Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        Node root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        scan.close();
        levelOrder(root);
    }	
}
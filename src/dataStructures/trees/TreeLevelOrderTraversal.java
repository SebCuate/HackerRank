package dataStructures.trees;

import java.util.*;
import java.io.*;

class NodeForTreeLevelOrderTraversal {
	NodeForTreeLevelOrderTraversal left;
	NodeForTreeLevelOrderTraversal right;
    int data;
    
    NodeForTreeLevelOrderTraversal(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class TreeLevelOrderTraversal {

	/* 
    
    class Node 
    	int data;
    	Node left;
    	Node right;
	*/
	private static void levelOrder(NodeForTreeLevelOrderTraversal root) {
		
		int h = height(root);
		
		for (int i = 0; i <= h; i++) {
			printLevel(root, i);
		}

    }
	
	private static void printLevel(NodeForTreeLevelOrderTraversal root, int level) {
		if (root == null)
            return;
        if (level == 0)
            System.out.print(root.data + " ");
        else if (level > 0) {
        	printLevel(root.left, level - 1);
        	printLevel(root.right, level - 1);
        }
	}
	
	private static int height(NodeForTreeLevelOrderTraversal root) {
      	if(root == null) {
            return -1;
        }
        return Math.max(height(root.left), height(root.right)) + 1;
    }

	public static NodeForTreeLevelOrderTraversal insert(NodeForTreeLevelOrderTraversal root, int data) {
        if(root == null) {
            return new NodeForTreeLevelOrderTraversal(data);
        } else {
        	NodeForTreeLevelOrderTraversal cur;
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
        NodeForTreeLevelOrderTraversal root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        scan.close();
        levelOrder(root);
    }	
}
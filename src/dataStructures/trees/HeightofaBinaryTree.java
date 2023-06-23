package dataStructures.trees;

import java.util.*;
import java.io.*;

class NodeHeightofaBinaryTree {
    NodeHeightofaBinaryTree left;
    NodeHeightofaBinaryTree right;
    int data;
    
    NodeHeightofaBinaryTree(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class HeightofaBinaryTree {

	/*
    class Node 
    	int data;
    	Node left;
    	Node right;
	*/
	private static int height(NodeHeightofaBinaryTree root) {
      	if(root == null) {
            return -1;
        }
        return Math.max(height(root.left), height(root.right)) + 1;
    }

	private static NodeHeightofaBinaryTree insert(NodeHeightofaBinaryTree root, int data) {
        if(root == null) {
            return new NodeHeightofaBinaryTree(data);
        } else {
            NodeHeightofaBinaryTree cur;
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
        NodeHeightofaBinaryTree root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        scan.close();
        int height = height(root);
        System.out.println(height);
    }	

}

package dataStructures.trees;

import java.util.*;
import java.io.*;

class HeightofaBinaryTree {

	/*
    class Node 
    	int data;
    	Node left;
    	Node right;
	*/
	private static int height(Node node) {
		if (node == null)
            return -1;
		else if (node.left == null && node.right == null)
			return 0;
		
        return Math.max(height(node.left), height(node.right)) + 1;
    }

	private static Node insert(Node root, int data) {
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
//        Scanner scan = new Scanner(System.in);
//        int t = scan.nextInt();
//        Node root = null;
//        while(t-- > 0) {
//            int data = scan.nextInt();
//            root = insert(root, data);
//        }
//        scan.close();
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
        tree.right.right.right.right.right = new Node(32);
        
        System.out.println(height(tree));
    }	

}

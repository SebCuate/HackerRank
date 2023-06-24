package dataStructures.trees;

public class DepthTransversalTechniques {

    // Driver code
    public static void main(String[] args){
    	
    	//Build a Binary Tree
    	Node tree = new Node(1); //Depth 1
        tree.left = new Node(2); //Depth 2
        tree.right = new Node(3);
//        tree.left.left = new Node(4); //Depth 3
//        tree.left.right = new Node(5);
//        tree.right.left = new Node(6);
        tree.right.right = new Node(7);
//        tree.left.left.left = new Node(8); //Depth 4
//        tree.left.left.right = new Node(9);
//        tree.left.right.left = new Node(10);
//        tree.left.right.right = new Node(11);
//        tree.right.left.left = new Node(12);
//        tree.right.left.right = new Node(13);
//        tree.right.right.left = new Node(14);
        tree.right.right.right = new Node(15);
//		tree.left.left.left.left = new Node(16); //Depth 5
//        tree.left.left.left.right = new Node(17);
//        tree.left.left.right.left = new Node(18);
//        tree.left.left.right.right = new Node(19);
//        tree.left.right.left.left = new Node(20);
//        tree.left.right.left.right = new Node(21);
//        tree.left.right.right.left = new Node(22);
//        tree.left.right.right.right = new Node(23);
//		tree.right.left.left.left = new Node(24);
//        tree.right.left.left.right = new Node(25);
//        tree.right.left.right.left = new Node(26);
//        tree.right.left.right.right = new Node(27);
//        tree.right.right.left.left = new Node(28);
//        tree.right.right.left.right = new Node(29);
//        tree.right.right.right.left = new Node(30);
        tree.right.right.right.right = new Node(31);
 
        System.out.println("the high of the tree is");
        System.out.println(getHeight(tree));
        
        // Functions call
        System.out.println(
            "\nIn-Order traversal of binary tree is"
        	);
        inOrder(tree);
        
        System.out.println(
            "\nPre-Order traversal of binary tree is"
            );
        preOrder(tree);
        
        System.out.println(
            "\nPost-Order traversal of binary tree is"
            );
        postOrder(tree);
        
        System.out.println(
                "\nLevel-Order traversal of binary tree is"
                );
        levelOrder(tree);
        
        System.out.println(
                "\nBoundary traversal of binary tree is"
                );
        boundaryOrderLeftToRight(tree);
        
    }


	//----
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
	
	//----
	
    private static void preOrder(Node node) {
		
		if (node == null)
            return;
 
        // First print data of node
        System.out.print(node.data + " ");
 
        // Then recur on left subtree
        preOrder(node.left);
 
        // Now recur on right subtree
        preOrder(node.right);
	}
    
	//----
    
	private static void postOrder(Node node) {
		
		if (node == null)
            return;
 
        // First print data of node
		postOrder(node.left);

        // Now recur on right subtree
        postOrder(node.right);
 
        // Then recur on left subtree
		System.out.print(node.data + " ");
	}
	
	//----
	
	private static void levelOrder(Node node) {
		
		int h = getHeight(node);
		
		for (int i = 1; i <= h; i++) {
			printLevel(node, i);
			System.out.println();
		}

    }
	
	private static void printLevel(Node node, int level) {
		if (node == null)
            return;
        if (level == 1)
            System.out.print(node.data + " ");
        else if (level > 1) {
        	printLevel(node.left, level - 1);
        	printLevel(node.right, level - 1);
        }
	}
	
	//----
	
	private static void boundaryOrderLeftToRight(Node node) {
		// Print all left leaves
        System.out.print(node.data + " ");
// 
        // Then recur on left subtree
        preOrderLeft(node.left);
        
        // print bottom leaves except first left and last right
		printLeaves(node);
//        printLevel(node, getHeight(node));
        		
        // Then recur on right subtree from bottom
        postOrderOnlyRightFromBotom(node.right);
		
	}
	
	private static void preOrderLeft(Node node) {
		// Print all left leaves 
		if (node == null || (node.left == null && node.right == null) )
            return;
 
        // First print data of node
        System.out.print(node.data + " ");
 
        // Then recur on left subtree
        preOrderLeft(node.left);
	}
	
	private static void postOrderOnlyRightFromBotom(Node node) {
		// Print all right leaves 
		if (node == null || (node.left == null && node.right == null) )
            return;
 
		// Then recur on right leaf
        postOrderOnlyRightFromBotom(node.right);
		
        // print data of node
        System.out.print(node.data + " ");
        		
	}
	
	private static void printLeaves(Node node){
        if (node == null)
            return;
 
        printLeaves(node.left);
        // Print it if it is a leaf node
        if (node.left == null && node.right == null)
            System.out.print(node.data + " ");
        printLeaves(node.right);
    }
	
	//----
	
	private static int getHeight(Node node) {
		if (node == null)
            return 0;
		else if (node.left == null && node.right == null)
			return 1;
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }
	
	
	

}
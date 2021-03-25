
/**
 * CS 251: Data Structures and Algorithms
 * Project 3: Part 1
 *
 * TODO: implement scapegoat.
 *
 * @author Yunyu Liu, TODO: add your username here
 * @username liu3154, TODO: add your Purdue username here
 * @sources TODO: list your sources here
 *
 * Use the algorithms written in sorting to implement this class.
 *
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Scapegoat {

    // Root node
    private Node root;
    // max node count required to implement deletion.
    private int MaxNodeCount = 0;
    // total node number
    private int NodeCount = 0;
    // parameter alpha
    private static final double threshold = 0.57;

    public class Node {
        T data;
        Node parent;
        Node left;
        Node right;
        public Node (T data, Node parent, Node left, Node right){
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
        public String toString(){
            return "[data="+data+"]";
        }
    }




    /**
     *
     * Constructor
     *
     */
    public Scapegoat() {
        root = null;
    }


    /**
     *
     * Constructor
     *
     */
    public Scapegoat(T data) {
        root = new Node(data, null, null, null);
        NodeCount ++;
    }


    /**
     *
     * @return return the root of the scapegoat tree
     *
     */
    public Node root(){
        return this.root;
    }


    /**
     *
     * This function finds the first scapegoat node and returns it.
     *
     * @return void
     *
     */
    private Node scapegoatNode(Node node) {
        // TODO:
        // You should use this function to find the scapegoat.
        // First you start from the node, climbing to the root.
        // Check if the current node satisfy alpha-weight-balanced property.
        // return the first node which doesn't satisfy the property.
        // -----------------------

        return node;
        // -----------------------
    }


    /**
     *
     * This function re-builds the tree rooted at node into a perfectly balanced tree.
     *
     * @return void
     *
     */
    public Node rebuild(Node node) {
        // TODO
        // rebuild the subtree whose root is node
        // 1) use the inorder traversal and get all the nodes
        // 2) brutally make it into a binary tree.
        // 3) return the new root of this new subtree.
        // How to make it into a binary tree:
        // You should use recursive methods to build the new binary tree.
        // Once you get the inorder traversal, you need to set the middle one to the root first.
        // Then recursively consider the left part and the right part of the traversal.

        return node;
        // -----------------------
    }


    /**
     *
     * This function adds an element into the tree.
     *
     * @return void
     *
     */
    public void add(T data) {
        if (root == null) {
            root = new Node(data, null, null, null);
            NodeCount ++;
        } else {
            // TODO:
            // -----------------------
            // find the parent node of the target node
            // you can try to use the find function here, however, since you also need the depth value of the node,
            // I strongly recommend you rewrite the find function here.

            // -----------------------

            // TODO:
            // -----------------------
            // now that you find the node,
            // 1) check where to insert the new data(left child or right child)
            // 2) update the NodeCount
            // 3) check if the tree still satisfies the alpha-height-balanced property
            // 4) if not, find a scapegoat. Climb from the new node back up to the root and select the first node that isn't alpha-weight-balanced.
            // 5) rebuild the tree

            // -----------------------
        }
    }


    /**
     *
     * This function removes an element from the tree.
     *
     * @return void
     *
     */
    public void remove(T data) {
        // TODO
        // this part is the same as the BST deletion
        // You first find the succNode, then replace the target node with the succNode.
        // rebuild the tree of required

    }


    // preorder traversal
    public List<Node> preorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(node);
        if(node.left != null){
            nodes.addAll(preorder(node.left));
        }
        if(node.right != null){
            nodes.addAll(preorder(node.right));
        }
        return nodes;
    }


    // inorder traversal
    public List<Node> inorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        if(node.left != null){
            nodes.addAll(inorder(node.left));
        }
        nodes.add(node);
        if(node.right != null){
            nodes.addAll(inorder(node.right));
        }
        return nodes;
    }


    // not used, but you can use this to debug
    public void print() {
        List<Node> nodes = inorder(root);
        for(int i=0;i<nodes.size();i++){
            System.out.println(nodes.get(i).toString());
        }
    }


    // return the node whose data is the same as the given data.
    public Node find(T data) {
        Node current = root;
        int result;
        while(current != null){
            result = data.compareTo(current.data);
            if(result == 0){
                return current;
            }else if(result > 0){
                current = current.right;
            }else{
                current = current.left;
            }
        }
        return null;
    }


    // find the succNode
    public Node succNode(Node node) {
        Node succ = null;
        int result;
        Node current = node;
        while(current != null){
            result = node.data.compareTo(current.data);
            if(result < 0){
                succ = current;
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return succ;
    }

    // used in scapegoatNode function, not a delicated one...
    // Just the brute force calculating the node's children's nubmer. Can be accelerated.
    private int size (Node node) {
        if (node == null)
            return 0;
        return 1 + size(node.left) + size(node.right);
    }


    // BFS(not used, you may use this to help you debug)
    public List<Node> breadthFirstSearch() {
        Node node = root;
        List<Node> nodes = new ArrayList<Node>();
        Deque<Node> deque = new ArrayDeque<Node>();
        if(node != null){
            deque.offer(node);
        }
        while(!deque.isEmpty()){
            Node first = deque.poll();
            nodes.add(first);
            if(first.left != null){
                deque.offer(first.left);
            }
            if(first.right != null){
                deque.offer(first.right);
            }
        }
        return nodes;
    }


    public static void main(String[] args) {
        // write your code here
        Scapegoat tree = new Scapegoat();
        tree.add(new T(40));
        tree.add(new T(10));
        tree.remove(new T(40));
        System.out.println();

        tree.add(new T(8));
        tree.add(new T(12));
        tree.add(new T(7));
        tree.add(new T(9));
        tree.add(new T(11));
        tree.add(new T(14));
        tree.add(new T(16));
        System.out.println("adding 16: "+tree.breadthFirstSearch());
        tree.add(new T(18));
        System.out.println("adding 18: "+tree.breadthFirstSearch());
        System.out.println();

        tree.remove(new T(14));
        tree.remove(new T(16));
        System.out.println("removing 14,16: "+tree.breadthFirstSearch());
        tree.remove(new T(12));
        System.out.println("removing 12: "+tree.breadthFirstSearch());
        tree.remove(new T(18));
        System.out.println("removing 18: "+tree.breadthFirstSearch());
    }


}

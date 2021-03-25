
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
        while(size(node) <= threshold * size(node.parent) )
            node = node.parent;
        
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
        List<Node> a = inorder(node);

        Node p = node.parent;
        int ns = size(node);
        Node new_node = buildBalanced(a, 0, ns);

        if(p == null )
        {
            root = new_node;
            root.parent = null;
        }
        else if( p.right == node )
        {
            p.right = new_node;
            p.right.parent = p;
        }
        else
        {
            p.left = new_node;
            p.left.parent = p;
        }

        node = new_node;

        return node;
        // -----------------------
    }

    protected Node buildBalanced(List<Node> a, int i, int ns) 
    {
        if (ns == 0)
            return null;
        int m = ns / 2;
        Node mid = a.get(i + m);
        mid.left = buildBalanced(a, i, m);
        if (mid.left != null)
            mid.left.parent = mid;
        mid.right = buildBalanced(a, i + m + 1, ns - m - 1);
        if (mid.right != null)
            mid.right.parent = mid;

        return mid;
    }

    private double getHeightBalanced(int q) 
    {
        final double log_alpha = Math.log(1/threshold);
        double h = Math.log(q) / log_alpha;
        
        return h;
    }

    private static final int Log32(int Val)
    {
        final double lg32=2.4663034623764317;
        return (int)Math.ceil(lg32*Math.log(Val));
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
            MaxNodeCount = Math.max(MaxNodeCount, NodeCount);
        } else {
            // TODO:
            // -----------------------
            // find the parent node of the target node
            // you can try to use the find function here, however, since you also need the depth value of the node,
            // I strongly recommend you rewrite the find function here.
            
            Node u = new Node(data, null, null, null);
            Node w = root;
            
            boolean done = false;
            int d = 0;

            do {
                if( data.compareTo(w.data) < 0 )  // left insert
                {
                    if( w.left == null )
                    {
                        w.left = u;
                        u.parent = w;
                        done = true;
                    }
                    else
                    {
                        w = w.left;
                    }
                }
                else if( data.compareTo(w.data) > 0 )
                {
                    if( w.right == null )
                    {
                        w.right = u;
                        u.parent = w;
                        done = true;
                    }
                    else
                        w = w.right;
                }
                else
                    break;

                d++;

            } while(!done);
            
            // -----------------------

            // TODO:
            // -----------------------
            // now that you find the node,
            // 1) check where to insert the new data(left child or right child)
            // 2) update the NodeCount
            // 3) check if the tree still satisfies the alpha-height-balanced property
            // 4) if not, find a scapegoat. Climb from the new node back up to the root and select the first node that isn't alpha-weight-balanced.
            // 5) rebuild the tree
            if( done )
            {
                NodeCount++;
                MaxNodeCount++;
            }

            if (d > getHeightBalanced(NodeCount)) {
            // if (d > Log32(NodeCount)) {
                /* depth exceeded, find scapegoat */
                w = scapegoatNode(u.parent);
                // if( data.a != 193 )
                rebuild(w.parent);
            }

            // -----------------------
        }

        int real_size = size(root);
        if( NodeCount != real_size )
        {
            print_tree();
            NodeCount = real_size;
        }

        MaxNodeCount = Math.max(MaxNodeCount, NodeCount);
    }

    T minValue(Node node)
    {
        T minv = node.data;
        while (node.left != null)
        {
            minv = node.left.data;
            node = node.left;
        }
        return minv;
    }

    Node deleteNode(Node node, T key)
    {
        /* Base Case: If the tree is empty */
        if (node == null)
            return node;
        
        /* Otherwise, recur down the tree */
        if (key.compareTo(node.data) < 0 )
            node.left = deleteNode(node.left, key);
        else if (key.compareTo(node.data) > 0)
            node.right = deleteNode(node.right, key);
 
        // if key is same as root's
        // key, then This is the
        // node to be deleted
        else {
            // node with only one child or no child
            if (node.left == null)
            {
                NodeCount--;
                return node.right;
            }
            else if (node.right == null)
            {
                NodeCount--;
                return node.left;
            }
 
            node.data = minValue(node.right);
 
            // Delete the inorder successor
            node.right = deleteNode(node.right, node.data);
        }
 
        return node;
    }

    private Node remove(Node node)
    {
        if( node == null )
            return null;

        Node parent = node.parent;

        Node succ_node = succNode(node);
        if( succ_node == null )
        {
            NodeCount--;
            if( parent == null ) // root
            {
                root = node.left;
                root.parent = null;
                return root;
            }
            else
            {               
                if( node == parent.right )
                    parent.right = node.left;
                else
                    parent.left = node.left;
                
                if( node.left != null )
                    node.left.parent = parent;
                return node.left;
            }
        }

        node.data = succ_node.data;
        
        remove(succ_node);

        return node;
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
        Node node = find(data);
        if( node == null )
            return;

        remove(node);

        // root = deleteNode(root, data);

        // int real_size = size(root);
        // if( NodeCount != real_size )
        // {
        //     print_tree();
        //     NodeCount = real_size;
        // }

        // rebuild
        double threshold_node_count = threshold * MaxNodeCount;
        if( NodeCount <= threshold_node_count )
        {            
            root = rebuild(root);      
            MaxNodeCount = NodeCount;
        }
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

    private void print_tree(Node node, int depth)
    {
        if(node == null)
            return;
        print_tree(node.right, depth + 1);
        for(int i = 0; i < depth; i++)
        {
            System.out.print("   ");
        }
        System.out.println(node.data.a);
        print_tree(node.left, depth + 1);
    }
    public void print_tree()
    {
        print_tree(root, 0);
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


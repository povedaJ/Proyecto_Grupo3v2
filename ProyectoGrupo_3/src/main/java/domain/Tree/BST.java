package domain.Tree;

import domain.Node;

public class BST implements Tree {
    private BTreeNode root;
    private Node root0;
    public BST() {
        this.root = null;
    }

    public BTreeNode getRoot() {
        return root;
    }

    @Override
    public int size() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
        }
        return size(root);
    }

    private int size(BTreeNode node){
        if(node==null)
            return 0;
        else
            return 1+size(node.left)+size(node.right);
    }

    @Override
    public void clear() {
        this.root = null;
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }

    @Override
    public boolean contains(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
        }
        return binarySearch(root, element);
    }

    private boolean binarySearch(BTreeNode node, Object element){
        if(node==null)
            return false;
        else if(util.Utility.compare(node.data, element)==0)
            return true; //ya lo encontro
        else if(util.Utility.compare(element, node.data)< 0)
                return binarySearch(node.left, element);
        else return binarySearch(node.right, element);
    }

    @Override
    public void add(Object element) {
        root = add(root, element);
    }

    private BTreeNode add(BTreeNode node, Object element){
        if(node==null){ //el arbol esta vacio
            node = new BTreeNode(element);
        }else
            if(util.Utility.compare(element, node.data)< 0)
                node.left = add(node.left, element);
            else if(util.Utility.compare(element, node.data)> 0)//va como hijo der
                    node.right = add(node.right, element);
        return node;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
        }
        root = remove(root, element);
    }

    private BTreeNode remove(BTreeNode node, Object element){
        if(node!=null){
            if(util.Utility.compare(element, node.data)< 0)
                node.left = remove(node.left, element);
            else if(util.Utility.compare(element, node.data)> 0)
                node.right = remove(node.right, element);
            else if(util.Utility.compare(node.data, element)==0){ //ya encontramos el elemento a eliminar
                //Caso 1. Es un nodo sin hijos. Es una hoja
                if(node.left==null && node.right==null)
                    return null;
                //Caso 2. El nodo solo tiene un hijo
                else if(node.left!=null && node.right==null)
                    return node.left; //retorna el subarbol izq y sustituye el nodo actual
                else if(node.left==null && node.right!=null)
                    return node.right; //retorna el subarbol derecho y sustituye el nodo actual
                //Caso 3. El nodo tiene 2 hijos
                else if(node.left!=null && node.right!=null){
                    Object value = min(node.right);
                    node.data = value;
                    node.right = remove(node.right, value);
                }
            }
        }
        return node; //retorna el nodo modificado
    }

    @Override
    public int height(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
        }
        return height(root, element, 0);
    }

    private int height(BTreeNode node, Object element, int counter){
        if(node==null)
            return -1;
        else if(util.Utility.compare(node.data, element)==0)
            return counter;
        else //en este caso debe buscar por la izq y por la der
        if(util.Utility.compare(element, node.data)< 0)
            return height(node.left, element, ++counter);
        else return height(node.right, element, ++counter);
            //return Math.max(height(node.left, element, ++counter), height(node.right, element, counter));
    }

    @Override
    public int height() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
        }
        return height(root)-1; //pq no cuente el nivel de la raiz
    }

    private int height(BTreeNode node){
        if(node==null)
            return 0;
        else
            return 1+Math.max(height(node.left), height(node.right));
    }

    @Override
    public Object min() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return min(root);
    }

    private Object min(BTreeNode node){
        if(node.left!=null)
            return min(node.left);
        return node.data;
    }

    @Override
    public Object max() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return max(root);
    }

    private Object max(BTreeNode node){
        if(node.right!=null)
            return max(node.right);
        return node.data;
    }

    @Override
    public String preOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return "PreOrder Transversal Tour: "+preOrder(root)+"\n";
    }

    //metodo interno
    //preOrder: node-left-right
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result=node.data+"("+node.path+"), ";
            result+=preOrder(node.left);
            result+=preOrder(node.right);
        }
        return result;
    }


    @Override
    public String InOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return "InOrder Transversal Tour: "+inOrder(root)+"\n";
    }

    //metodo interno
    //preOrder: left-node-right
    private String inOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result=inOrder(node.left);
            result+=node.data+", ";
            result+=inOrder(node.right);
        }
        return result;
    }

    @Override
    public String postOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return "PostOrder Transversal Tour: "+postOrder(root)+"\n";
    }

    //metodo interno
    //preOrder: left-right-node
    private String postOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result=postOrder(node.left);
            result+=postOrder(node.right);
            result+=node.data+", ";
        }
        return result;
    }

    @Override
    public String toString() {
        if(isEmpty()) return "Binary Search tree is empty";
        String result = "Binary Search Tree Tour...\n";
        result+="PreOrder: "+preOrder(root)+"\n";
        result+="InOrder: "+inOrder(root)+"\n";
        result+="PostOrder: "+postOrder(root)+"\n";
        return result;
    }

    @Override
    public String printNodesWithChildren() throws TreeException {
        return null;
    }

    @Override
    public String printNodes1Child() throws TreeException {
        return null;
    }

    @Override
    public String printNodes2Children() throws TreeException {
        return null;
    }

    @Override
    public String printLeaves() throws TreeException {
        return null;
    }

    @Override
    public Object grandFather(Object element) throws TreeException {
        return null;
    }

    @Override
    public Object father(Object element) throws TreeException {
        return null;
    }

    @Override
    public Object brother(Object element) throws TreeException {
        return null;
    }

    @Override
    public Object cousins(Object data) throws TreeException {
        return null;
    }

    @Override
    public Object subTree(Object data) throws TreeException {
        return null;
    }

    @Override
    public int totalLeaves() throws TreeException {
        return countLeaves(root);
    }
    //    if(node==null)
    //            return 0;
//        else
//                return 1+size(node.left)+size(node.right);
    private int countLeaves(BTreeNode node) {
        if (node == null) {return 0;}

        if (node.left == null && node.right == null) {//node es hoja
            //System.out.println(node.data);
            return 1;
        }
        // cuenta las hojas de los subarboles izquierdo y derecho
        int leftLeaves = countLeaves(node.left);
        int rightLeaves = countLeaves(node.right);

        return leftLeaves + rightLeaves;
    }
    public String toStringLEAVES() {
        if(isEmpty()) return "Binary tree is empty";
        String result = "Binary Tree Tour...\n";
        result+="PreOrder: "+preOrder(root)+"\n";
        return result;
    }

    public void insert(int key, String  value) {
        root = insertNode(root, key, value);
    }

    private BTreeNode insertNode(BTreeNode node, int key, String value) {
        if (node == null) {
            return new BTreeNode(key,  value);
        }

        if (key < node.key) {
            node.left = insertNode(node.left, key, value);
        } else if (key > node.key) {
            node.right = insertNode(node.right, key, value);
        }

        return node;
    }

    public Node[] getTopKNodes(int k) {
        Node[] topNodes = new Node[k];
        getTopKNodesUtil(root, topNodes, 0, k);
        return topNodes;
    }

    private int getTopKNodesUtil(BTreeNode node, Node[] topNodes, int index, int k) {
        if (node == null || index >= k) {
            return index;
        }

        index = getTopKNodesUtil(node.right, topNodes, index, k);
        if (index < k) {
            topNodes[index++] = new Node(node.key, node.value);
            index = getTopKNodesUtil(node.left, topNodes, index, k);
        }

        return index;
    }
}

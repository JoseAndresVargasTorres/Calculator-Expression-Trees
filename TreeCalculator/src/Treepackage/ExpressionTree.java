package Treepackage;

import java.util.Stack;

/**
 * The ExpressionTree class is responsible
 * for evaluating expressions in the tree.
 *@author Carlos Contreras
 *@author José Vargas
 *@author Nicol Otárola
 */
public class ExpressionTree {
    private Node head;
    private String infixExpr;
    private String postfixExpr;

    public ExpressionTree(String expr) {
        this.infixExpr = expr;
        this.head = buildTree();
        this.postfixExpr = Postfix.convert(expr);
    }

    /**
     * This Method for creating a new expression tree, destroys old one
    * @param expr 
    */
    public void newExpr(String expr) {
        this.infixExpr = expr;
        this.head = buildTree();
        this.postfixExpr = Postfix.convert(expr);
    }

    /**
     * This method builds the syntax tree for a given expression
     * @return root of the tree
     */
    private Node buildTree() {
        Postfix postfix = new Postfix(infixExpr);
        if(!postfix.isValidExpr())
            throw new IllegalStateException("Invalid Expression.");

        Stack<String> postfixTkns = new Stack<>();
        postfixTkns.addAll(postfix.getPostTkns());
        String currSymbol = postfixTkns.pop();

        Node root, curr;

        // if all we have is one token, that becomes the head
        if(postfixTkns.isEmpty()) {
            head = new Node(null, null, null, Double.parseDouble(currSymbol));
            return head;
        }

        // the last token becomes the root
        root = new Node(null, null, null, Node.evalOperator(currSymbol));

        curr = root;
        // while there are still tokens to parse
        while(!postfixTkns.isEmpty()) {
            currSymbol = postfixTkns.pop();

            // if curr is a complete node, this will set curr to its most recent
            // incomplete ancestor. if curr isn't, this just sets curr equal to curr
            curr = findIncompleteParent(curr);
            // if the current symbol is an operator
            if(Node.evalOperator(currSymbol) != Node.Operation.NONE) {
                // either place it as a new node in curr.right
                // or place it as a new node in curr.left
                // then set curr to point to the new node
                if(curr.right == null) {
                    curr.right = new Node(curr, null, null, Node.evalOperator(currSymbol));
                    curr = curr.right;
                }
                else {
                    curr.left = new Node(curr, null, null, Node.evalOperator(currSymbol));
                    curr = curr.left;
                }
            }
            else {
                // if curr is an operand, set its right/ left node (whichever's available)
                // to be a new DIGIT node with that operand as its value
                if(curr.right == null) {
                    curr.right = new Node(curr, null, null, Double.parseDouble(currSymbol));
                }
                else {
                    curr.left = new Node(curr, null, null, Double.parseDouble(currSymbol));
                }
            }
        }

        head = root;
        return root;
    }

    /**
     *The findIncompleteParentet method returns the previous
     * node of n, or the n itself 
     */
    private Node findIncompleteParent(Node n) {
        while(n.left != null && n.right != null)
            n = n.parent;
        return n;
    }

    /**
     * WalkTree is the method used to walk through all the
     * nodes in the tree.
     * @param head
     * @return new StringBuilder
     */
    private String walkTree(Node head) {
        StringBuilder sb = new StringBuilder();

        if(head == null)
            return "";
        if(head.left != null)
            sb.append(walkTree(head.left));
        if(head.right != null)
            sb.append(walkTree(head.right));
        sb.append(head.toString());
        return sb.toString();
    }

    /**
     * Method to evaluate the whole tree
     * @return head
     */
    public double eval() {
        return eval(head);
    }

    /**
     * Method to evaluate a section of a tree
     * @param head
     */
    private double eval(Node head) {
        if(this.isEmpty())
            throw new IllegalStateException("Attempting to eval uninitialized tree.");


        if(head.isDigit())
            return head.operand;

        // eval the left, eval the right, value = left op right
        double left = eval(head.left);
        double right = eval(head.right);
        double value;

        switch(head.op) {
            case ADD:
                value = left + right;
                break;
            case SUB:
                value = left - right;
                break;
            case MUL:
                value = left * right;
                break;
            case DIV:
                value = left / right;
                break;
            case POW:
                value = left % right;
                break;
            
            default: value = 0;
        }

        return value;
    }
    /**
     * This method is in charge of verifying if
     * the head is still empty
     * @return boolean 
     */
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    /**
     * This method is responsible for chaining the data
     * @return the postfixed expression
     */
    public String toString() {
        return postfixExpr;
    }

}

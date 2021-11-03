package Treepackage;

/**
 *The node class is in charge of managing the nodes according
 *to location, if it is a number or an operator
 */
public class Node {
    public static enum Operation {ADD, SUB, MUL, DIV, POW, NONE};
    public static enum Type {LIST, DIGIT};

    // left and right nodes
    protected Node left;
    protected Node right;
    protected Node parent;

    // could be an operator, could be an operand
    protected Operation op;
    protected Double operand;
    protected Type type;

    // standard constructor, should not be used
    Node(Node parent, Node left, Node right, Operation op, Type type, Double operand) {
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.op = op;
        this.type = type;
        this.operand = operand;
    }

    // copy constructor for operations
    Node(Node parent, Node left, Node right, Operation op) {
        this(parent, left, right, op, Type.LIST, null);
    }

    // copy constructor for digits
    Node(Node parent, Node left, Node right, Double operand) {
        this(parent, left, right, Operation.NONE, Type.DIGIT, operand);
    }
    
    /**
     * This method is in charge of verifying if it is a digit
     */
    public boolean isDigit() {
        return this.type == Type.DIGIT;
    }
    
    /**
     * This method is in charge of verifying if it is a operator
     */
    public boolean isOperator() {
        return this.type == Type.LIST;
    }

    /**
     * This method is in charge of verifying if the node is on
     * the left side
     */
    public boolean isLeaf() {
        return (left == null && right == null);
    }

    /**
     * It is responsible for defining the type of operator
     * @param operator
     * @return type of operator
     */
    protected static Operation evalOperator(String operator) {
        switch(operator) {
            case "+":
                return Operation.ADD;
            case "-":
                return Operation.SUB;
            case "*":
                return Operation.MUL;
            case "/":
                return Operation.DIV;
            case "%":
                return Operation.POW;
            
            default:
                return Node.Operation.NONE;
        }
    }

    @Override
    /**
     * This method determines if the string 
     * corresponds to a digit or an operator
     */
    public String toString() {
        String str = "";

        str += "type = " + this.type.toString() + "\n";
        switch(type) {
            case DIGIT:
                str += "Digit = " + operand + "\n";
                break;
            case LIST:
                str += "Operator = " + op.toString() + "\n";
        }

        return str;
    }
}

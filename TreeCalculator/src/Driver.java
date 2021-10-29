public class Driver {
    public static void main(String[] args) {
        // create a new expression tree with this expression to be parsed
        ExpressionTree tree = new ExpressionTree("9 + 10 * ( 13 * 3 )");
        // print the value of the expression
        System.out.println(tree.eval());
        // print the expression in postfix notation
        System.out.println(tree);
    }
}

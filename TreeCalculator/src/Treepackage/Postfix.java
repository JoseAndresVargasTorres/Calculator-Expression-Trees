package Treepackage;

import Treepackage.MathParser;
import java.util.ArrayList;
import java.util.Stack;

/**
 *In this class the infix expression is converted to postfix
 * @author Usuario
 */
public class Postfix {

    private final String infixExpr;
    private final String postfixExpr;
    private final ArrayList<String> infixTkns;
    private final ArrayList<String> postfixTkns;

    Postfix(String infixExpr) {
        this.infixExpr = infixExpr;
        this.infixTkns = tokenizeExpr(infixExpr);
        this.postfixTkns = convertToPostfix(infixExpr);
        this.postfixExpr = convertPostTkns();
    }


    /**
     *This method converts an arraylist of postfix tkns into an expression
     */
    private String convertPostTkns() {
        return convertPostTkns(postfixTkns);
    }

    /** This method converts postfix tokens into a string
     * @param postfixTkns
     */
    private static String convertPostTkns(ArrayList<String> postfixTkns) {
        if(postfixTkns.isEmpty())
            return " ";

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < postfixTkns.size()- 1; i++)
            sb.append(postfixTkns.get(i).concat(" "));
        sb.append(postfixTkns.get(postfixTkns.size()-1));

        return sb.toString();
    }

    /**
     * This method is responsible for returning the suffix expression to an
     * infix expression
     * @param infixExpr 
     */
    public static String convert(String infixExpr) {
        return convertPostTkns(convertToPostfix(infixExpr));
    }

    /**
     * ArrayList takes care of returning the suffix expression to an infix
     * expression as an ArrayList of tokens
     * @param infixExpr 
     */
    public static ArrayList<String> convertTkns(String infixExpr) {
        return convertToPostfix(infixExpr);
    }

    // getters
    /**
     * Get postfix expression
     */
    public String getPostExpr() {
        return this.postfixExpr;
    }

    /**
     * Get infix expression
     */
    public String getInExpr() {
        return this.infixExpr;
    }
    
    /**
     * Get infix token
     */
    public ArrayList<String> getInTkns() {
        return this.infixTkns;
    }

    /**
     * Get postfix token
     */
    public ArrayList<String> getPostTkns() {
        return this.postfixTkns;
    }

    /**
     * This method is in charge of converting an infix expression into
     * a postfix expression as an ArrayList of tokens
     * @param expr
     * @return postfix expression
     */
    private static ArrayList<String> convertToPostfix(String expr) {
        // if expr is empty or null, return an empty ArrayList
        if(expr == null || expr.equals(""))
            return new ArrayList<>();

        ArrayList<String> infix = tokenizeExpr(expr);
        ArrayList<String> postfix = new ArrayList<>();
        Stack<String> stack = new Stack<>();


        // for each token in the infix expression
        for(String token : infix) {

            // if the token is not an operator
            // put it in the postfix expression
            if(!isOperator(token)) {
                postfix.add(token);
                
            }

            // if the token is an opening parentheses,
            // just push it onto the stack
            else if(token.equals("(")) {
                stack.push(token);

            }

            // if the token is a closing parentheses,
            // empty the stack into postfix until an opening parentheses
            // is encountered. Then pops the opening parenteses from the stack
            else if(token.equals(")")) {
                while(!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.add(stack.pop());
                }
                stack.pop();
            }

            // if the token is an operator
            // check if precedence of the operator at the top of the stack
            // is greater than the precedance of the token
            // if it is, pop the stack into postfix until it isn't
            // then push our token to the top of the stack
            else {
                while(!stack.isEmpty() && (prec(stack.peek()) >= prec(token)) && !stack.peek().equals("(")) {
                    postfix.add(stack.pop());
                    
                }

                stack.push(token);

            }
        }


        // now empty the stack into postfix
        while(!stack.isEmpty()) {
            postfix.add(stack.pop());
        }

        return postfix ;
    }


    /**
     * Method that is responsible for returning 
     * the order of precedence of the operators
     * @param token
     * @return priority number
     */
    private static int prec(String token) {
        switch(token) {
            case "(": case ")":
                return 7;
            case "==": case "!=":
                return 6;
            case "%":
                return 4;
            case "*": case "/":
                return 4;
            
            case "+": case "-":
                return 3;
            case "&&":
                return 2;
            case "||":
                return 1;
            case "=":
                return 0;
            default: return -1;
        }
    }

    /**
     * This method is responsible for returning if a given
     * token is an operator
     * @param str
     */
    public static boolean isOperator(String str) {
        return
                str.equals("+")
                        || str.equals("-")
                        || str.equals("*")
                        || str.equals("/")
                        || str.equals("%")
                        
                        || str.equals("(")
                        || str.equals(")")
                        || str.equals("=")
                        || str.equals("==")
                        || str.equals("||")
                        || str.equals("&&");
    }

    /**
     * This method takes care of checking if the postfix
     * expression is valid
     * @return 
     */
    public boolean isValidExpr() {
        int operators = 0, operands = 0;

        if(infixExpr == null || infixExpr.equals(""))
            return false;

        // if we only have one value, and its an operand, return true
        if(postfixTkns.size() == 1) {
            try {
                Double.parseDouble(postfixTkns.get(0));
                return true;
            }
            catch(NumberFormatException e) {
                return false;
            }
        }

        // check that the first two values are operands
        try {
            Double.parseDouble(postfixTkns.get(0));
            Double.parseDouble(postfixTkns.get(1));
        }
        catch(NumberFormatException e) {
            return false;
        }

        // if the last expression isn't an operator, this cannot be valid
        if(!isOperator(postfixTkns.get(postfixTkns.size()-1)))
            return false;

        // check that there is one less operand than operators
        for(String token : postfixTkns) {
            if(isOperator(token))
                operators++;
            else
                operands++;
        }

        if (operators != operands - 1)
            return false;


        return true;
    }

    /**
     * This method takes care of tokenizing
     * an expression in an ArrayList
     * @param expr
     */
    private static ArrayList<String> tokenizeExpr(String expr) {
        if(expr == null)
            return null;

        ArrayList<String> tkns = MathParser.parse(expr);

        return tkns;
    }

    @Override
    public String toString() {
        return this.postfixExpr;
    }
}

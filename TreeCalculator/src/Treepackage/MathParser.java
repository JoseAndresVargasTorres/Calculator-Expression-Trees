package Treepackage;

import java.util.ArrayList;

/**
 * This class contains the mathematic operators.
 */
public class MathParser {

    private final String expr;
    private final static char[] operators = {'+', '-', '/', '*', '(', ')', '%'};

    /**
     * This method parser the expressions 
     * @param expr 
     */
    public MathParser(String expr) {
        this.expr = expr;
    }
    
    /**
     * This method parses ArrayList
     * @return 
     */
    public ArrayList<String> parse() {
        return parse(expr);
    }
    
    /**
     * This method is responsible for looping through
     * the expression and determining what are the 
     * characters and what are the operators of the expression
     * @param expr
     * @return ArrayList
     */
    public static ArrayList<String> parse(String expr) {
        expr = expr.trim();
        ArrayList<String> tkns = new ArrayList<>();

        for(int i = 0; i < expr.length(); i++) {
            if(Character.isWhitespace(expr.charAt(i)))
                continue;
            else if(isOperator(expr.charAt(i)))
                tkns.add(String.valueOf(expr.charAt(i)));
            else {
                int end = i;
                while(end < expr.length() && Character.isDigit(expr.charAt(end)))
                    end++;

                tkns.add(expr.substring(i, end));
                i = end-1;
            }
        }

        return tkns;
    }

    /**
     * This method is responsible for verifying
     * that parameter c is an operator
     * @param c
     * @return boolean
     */
    public static boolean isOperator(char c) {
        for(char op : operators) {
            if(op == c) {
                return true;
            }
        }
        return false;
    }

}


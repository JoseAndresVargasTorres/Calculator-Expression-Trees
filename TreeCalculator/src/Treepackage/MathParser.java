package Treepackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jose
 */
import java.util.ArrayList;

public class MathParser {

    private final String expr;
    private final static char[] operators = {'+', '-', '/', '*', '(', ')', '%'};

    public MathParser(String expr) {
        this.expr = expr;
    }

    public ArrayList<String> parse() {
        return parse(expr);
    }

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

    public static boolean isOperator(char c) {
        for(char op : operators) {
            if(op == c) {
                return true;
            }
        }
        return false;
    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressiontree;

/**
 *
 * @author jose
 */
import java.util.Stack;
import java.util.*;

public class Main {

    static int decider;
    static char checker;
    static Node root = null;

    public static void main(String[] args) {

        // Create the expression string
        String expressionString = "((13+15)*5-1)*5"; //[] [32+3*5+][5 3 * 5 +][15 5 +][20]

        // Convert it to a character array and call it 'infix'
        

        // pass the infix array in to the postfixer which is a
        // function to create the postfix form of the expression
        String postfix = inf2postf(expressionString);
        System.out.println("Infix : " + expressionString);
        System.out.println("Postfix : " + postfix);
        
        

    }

    // function to convert postfix to infix
    private static String inf2postf(String infix) {

        String postfix = "";
        Stack<Character> operator = new Stack<Character>();
        char popped;

        for (int i = 0; i < infix.length(); i++) {

            char get = infix.charAt(i);

            if (!isOperator(get))
                postfix += get;

            else if (get == ')')
                while ((popped = operator.pop()) != '(')
                    postfix += popped;

            else {
                while (!operator.isEmpty() && get != '(' && precedence(operator.peek()) >= precedence(get))
                    postfix += operator.pop();

                operator.push(get);
            }
        }
        // pop any remaining operator
        while (!operator.isEmpty())
            postfix += operator.pop();

        return postfix;
    }

    private static boolean isOperator(char i) {
        return precedence(i) > 0;
    }

    private static int precedence(char i) {

        if (i == '(' || i == ')') return 1;
        else if (i == '-' || i == '+') return 2;
        else if (i == '*' || i == '/') return 3;
        else return 0;
    }

}

class NodeInt {
    double data;
    NodeInt(double data) {
        this.data = data;
    }
}

class Node {
    char data;
    Node left;
    Node right;
    Node(char data) {
        this.data = data;
    }
}
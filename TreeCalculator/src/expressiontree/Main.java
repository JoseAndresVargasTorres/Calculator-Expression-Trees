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
    static int precedence(char c){
        switch (c){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    public static void main(String[] args) {

        // Create the expression string
        String expressionString = "5*3/8+(95%5–10)"; //[] [32+3*5+][5 3 * 5 +][15 5 +][20]       //5 * 3 / 8 + ( 95 % 5 – 10 )
        String exp = "13 15 + 5 * 1 - 5 *";
        String[] exp2 = exp.split(" ");
        // Convert it to a character array and call it 'infix'
        

        // pass the infix array in to the postfixer which is a
        // function to create the postfix form of the expression
        String postfix = inf2postf(expressionString);
        System.out.println("Infix : " + expressionString);
        System.out.println("Postfix :" + postfix);
        
        String pel = postfix.replaceAll("\\s+", " ");
        String[] postfixeval  = pel.split(" ");
        String[] modifiedArray = Arrays.copyOfRange(postfixeval, 1, postfixeval.length);
        //String[] modifiedArray2 = Arrays.copyOfRange(modifiedArray, 1, modifiedArray.length);
        System.out.println("El resultado es: "+evalPostfix(modifiedArray));
        /*
        if(postfixeval[0]== " " && postfixeval[postfixeval.length-1] == " "){
            String[] modifiedArray = Arrays.copyOfRange(postfixeval, 1, postfixeval.length);
            String[] finalpostfix = Arrays.copyOfRange(modifiedArray, 0, modifiedArray.length-1);
            System.out.println("El resultado es: "+evalPostfix(finalpostfix));
            
        }else if(postfixeval[0]== " "){
            String[] modifiedArray = Arrays.copyOfRange(postfixeval, 1, postfixeval.length);
            System.out.println("El resultado es: "+evalPostfix(modifiedArray));
        }else if(postfixeval[postfixeval.length-1] == " "){
            String[] finalpostfix = Arrays.copyOfRange(postfixeval, 0, postfixeval.length-1);
        }else{
            System.out.println("El resultado es: "+evalPostfix(postfixeval));
            System.out.println("El resultado es: ");

        }
        */
       
        /*
        String[] modifiedArray = Arrays.copyOfRange(postfixeval, 1, postfixeval.length);
        System.out.println("El resultado es: "+evalPostfix(postfixeval));
        */
        
        
        
        
        

    }
    // Function to evaluate a given postfix expression
   public static Double evalPostfix(String[] exp)
   {
      // base case
      if (exp == null || exp.length ==0 ) {
         System.exit(-1);
      }

      // create an empty stack
      Stack<Double> stack = new Stack<>();

      // traverse the given expression
      for (int i = 0; i < exp.length;i++)
      {
         // if the current character is an operand, push it into the stack
         if (isNumeric(exp[i])) {

            stack.push(Double.parseDouble(exp[i]));
         }
         // if the current character is an operator
         else {
            // remove the top two elements from the stack

            double x = stack.pop();
            double y = stack.pop();
            //String y = stack.pop();

            // evaluate the expression 'x op y', and push the
            // result back to the stack
            if (exp[i].contains("+")) {
               stack.push(y + x);
            }
            else if (exp[i].contains("-")) {
               stack.push(y - x);
            }
            else if (exp[i].contains("*")) {
               stack.push(y * x);
            }
            else if (exp[i].contains("/")) {
               stack.push(y / x);
            }else if(exp[i].contains("%")){
                stack.push(y % x);
            }
            else{
                System.out.println("No funka");
            }
         }
      }

      // At this point, the stack is left with only one element, i.e.,
      // expression result
      return stack.pop();
   }

   public static boolean isNumeric(String strNum) {
      if (strNum == null) {
         return false;
      }
      try {
         double d = Double.parseDouble(strNum);
      } catch (NumberFormatException nfe) {
         return false;
      }
      return true;
   }
   

    // function to convert postfix to infix
    private static String inf2postf(String expression) {

        String result = "";
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i <expression.length() ; i++) {
            char c = expression.charAt(i);

            //check if char is operator
            if(precedence(c)>0){
                while(stack.isEmpty()==false && precedence(stack.peek())>=precedence(c)){
                    result += stack.pop();
                }
                stack.push(c);
            }else if(c==')'){
                char x = stack.pop();
                while(x!='('){
                    result += x;
                    x = stack.pop();
                }
            }else if(c=='('){
                stack.push(c);
            }else{
                //character is neither operator nor ( 
                result += c;
            }
        }
        for (int i = 0; i <=stack.size() ; i++) {
            result += stack.pop();
        }
        return result;

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
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*

/**
 *
 * @author jose
 */
/*
package expressiontree;
import java.util.Stack;
public class Main234 {
   // Function to evaluate a given postfix expression
   public static int evalPostfix(String[] exp)
   {
      // base case
      if (exp == null || exp.length == 0) {
         System.exit(-1);
      }

      // create an empty stack
      Stack<Integer> stack = new Stack<>();

      // traverse the given expression
      for (int i = 0; i< exp.length;i++)
      {
         // if the current character is an operand, push it into the stack
         if (isNumeric(exp[i])) {

            stack.push(Integer.parseInt(exp[i]));
         }
         // if the current character is an operator
         else {
            // remove the top two elements from the stack

            int x = stack.pop();
            int y = stack.pop();
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

   public static void main(String[] args)
   {
      String exp = "13 15 + 5 * 1 - 5 *";
      String[] exp2 = exp.split(" ");
      System.out.println(evalPostfix(exp2));
   }
 
}
*/
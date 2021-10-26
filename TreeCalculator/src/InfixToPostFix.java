/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jose
 */
import java.util.Arrays;
import java.util.Stack;

public class InfixToPostFix {

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
    
    static String infixToPostFix(String expression){

        String result = "";
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i <expression.length() ; i++) {
            char c = expression.charAt(i);

            //check if char is operator
            if(precedence(c)>0){
                while(stack.isEmpty()==false && precedence(stack.peek())>=precedence(c)){
                    result = result + " " + stack.pop();
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
        
        // en al variable result ya estan todos los numeros
        for (int i = 0; i <=stack.size() ; i++) {
            result = result + " " + stack.pop();
        }
        return result;
    }
    static Double evalPostfix(String[] exp)
   {
      // base case
      if (exp == null || exp.length <0 ) {
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

   static boolean isNumeric(String strNum) {
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
    public static void main(String[] args) {
        String exp = " " + "( 5 * 7 ) + ( 12 / 6 )"+" ";                                            //( 5 * 7 ) + ( 12 / 6 )        //5 * 3 / 8 + ( 95 % 5 - 10 ) 
        System.out.println("Infix Expression: " + exp);
        System.out.println("Postfix Expression: " + infixToPostFix(exp));
        
        String exp2 = infixToPostFix(exp);
        String pel = exp2.replaceAll("\\s+", " ");
        System.out.println(pel);
        
       
        String[] exparray = pel.split(" ");
        if(exparray[0]== ""){
            System.out.println("hola");
            String[] modifiedArray = Arrays.copyOfRange(exparray, 1, exparray.length);
            System.out.println("The result is: " + evalPostfix(modifiedArray));
        }else{
            System.out.println("The result is: " + evalPostfix(exparray));
        }
        
    }
}
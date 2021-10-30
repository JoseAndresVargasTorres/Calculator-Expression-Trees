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
public class Driver {
    public static void main(String[] args) {
        // create a new expression tree with this expression to be parsed
        ExpressionTree tree = new ExpressionTree("5 * 3 / 8 + ( 95 % 5 – 10 )");
        System.out.println("Hola     "+tree.toString());
        // print the value of the expression
        System.out.println(tree.eval());
        // print the expression in postfix notation
        System.out.println(tree);
    }
}

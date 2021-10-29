

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.*;
import javax.swing.JOptionPane;


/**
 *Server:
 *In this class the server, contain the factor of connection
 *and function for calculated the expressions 
 * @author Carlos Contreras
 * @author José Vargas
 * @author Nicol Otárola
 */
public class Server extends javax.swing.JFrame {

    /**
     * Method creates new form Server
     */
    ServerSocket ss;
    HashMap clientColl=new HashMap();
    public Server() {
        try{
            initComponents();
            ss=new ServerSocket(2089);
            this.sStatus.setText("Server started");
            
            new ClientAccept().start();
        
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    /**
     * The ClientAccep class contains the execute method which
     * is responsible for accepting the connection from multiple 
     * clients and keeping the read and write methods active
     * @throws I0Ex
     */
    class ClientAccept extends Thread{
        public void run(){
            while(true){
                try{
                    Socket s = ss.accept();
                    String i = new DataInputStream(s.getInputStream()).readUTF();
                    if(clientColl.containsKey(i)){
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("You Are Already Registered....!!");
                    }else{
                        clientColl.put(i, s);
                        msgBox.append(i + " entered to Calculator Tree Program !\n");
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("");
                        new MsgRead(s,i).start();
                        new PrepareClientList().start();
                    }
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    
    /**
     * MsgRead creates a Thread that defines the run method
     */
    class MsgRead extends Thread{
        Socket s;
        String ID;

        private MsgRead(Socket s, String ID) {
            this.s=s;
            this.ID=ID;
        }
        
                        
        /**
         * Method to read the data entered in the text spaces and
         * save in the CSV document the information received 
         * @throws IOException
         */
        public void run(){
            
    
            while (!clientColl.isEmpty()) {
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    String date = dtf.format(LocalDateTime.now()).toString();
                    String filepath = "Datos.csv";
                    
                    String input = new DataInputStream(s.getInputStream()).readUTF();
                    DataOutputStream output =new DataOutputStream(s.getOutputStream());
                    String expresion = input;
                    System.out.println(expresion);
                    
                    
                    if (input.contains("12345678")) {
                        input = input.substring(8);
                        List<String> realinput = Arrays.asList(input.split(":"));                        
                        msgBox.append("< The client " + realinput.get(0) + ", wants to calculate the following expression> " + realinput.get(1)+"\n");
                        String infix  = realinput.get(1)+" ";
                        String exp2 = infixToPostFix(infix);
                        String pel = exp2.replaceAll("\\s+", " ");
                        String[] exparray = pel.split(" ");                        
                        if(exparray[0]== ""){
                            System.out.println("hola");
                            String[] modifiedArray = Arrays.copyOfRange(exparray, 1, exparray.length);
                            String result = Double.toString(evalPostfix(modifiedArray));
                            output.writeUTF("recibo"+result);
                            saveRecord(date,realinput.get(0),realinput.get(1),result,filepath);
                        }else{
                            String result = Double.toString(evalPostfix(exparray));
                            output.writeUTF("recibo"+result);
                            saveRecord(date,realinput.get(0),realinput.get(1),result,filepath);
                        }        
                        
                        
                         
                        //saveRecord(date,realinput.get(0),realinput.get(1),result,filepath);
                        Set k = clientColl.keySet();
                        //System.out.println(k);
                        
                        
                        
                        
                        Iterator itr = k.iterator();
                        while (itr.hasNext()) {
                            String key = (String) itr.next();
                            if (!key.equalsIgnoreCase(ID)) {
                                try {
                                    new DataOutputStream(s.getOutputStream()).writeUTF(input);
                                } catch (Exception ex) {

                                    clientColl.remove(key);
                                    msgBox.append(key + ": salió!");
                                    new PrepareClientList().start();
                                }
                            }
                        }
                    } else if (input.equals("mkoihgteazdcvgyhujb096785542AXTY")) {
                        clientColl.remove(ID);
                        msgBox.append(ID + ": salió! \n");
                        new PrepareClientList().start();
                        Set<String> k = clientColl.keySet();
                        Iterator itr = k.iterator();
                        while (itr.hasNext()) {
                            String key = (String) itr.next();
                            if (!key.equalsIgnoreCase(ID)) {
                                try {
                                    new DataOutputStream(s.getOutputStream()).writeUTF(input);
                                } catch (Exception ex) {
                                    clientColl.remove(key);
                                    msgBox.append(key + ": salió!");
                                    new PrepareClientList().start();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * PrepareClientList creates a Thread that defines the run method
     */
    class PrepareClientList extends Thread{
        
        /**
         * This method create the clients list 
         */
        public void run(){
            try{
                String ids="";
                Set k=clientColl.keySet();
                Iterator itr=k.iterator();
                while(itr.hasNext()){
                    String key=(String)itr.next();

                    ids+=key+",";
                }
                if(ids.length()!=0)
                    ids=ids.substring(0,ids.length()-1);

                itr=k.iterator();
                while(itr.hasNext()){
                    String key=(String)itr.next();

                    try{

                        new DataOutputStream(((Socket)clientColl.get(key)).getOutputStream()).writeUTF(":;.,/="+ids);
                    }catch(Exception ex){
                        clientColl.remove(key);
                        msgBox.append(key+": removed!");
                  
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * This method is responsible for saving the data record 
     * @param Date
     * @param name
     * @param exp
     * @param result
     * @param filepath 
     */
    public static void saveRecord(String Date, String name, String exp, String result, String filepath){
        
        try{
            FileWriter fw = new FileWriter(filepath,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            
            pw.println(Date+","+name+","+exp+","+result);
            pw.flush();
            pw.close();
            
            System.out.println("Record saved");
            
        }catch(Exception E){
            System.out.println("Record not saved");
        }
    }
    
    /**
     * Method that is responsible for returning 
     * the order of precedence of the operators
     * @param c
     * @return priority number
     */
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
    
    /**
     * This method convert given infix expression to postfix expression.
     * @param expression
     * @return postfix expression
     */
    static String infixToPostFix(String expression){

        String result = "";
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i <expression.length() ; i++) {
            char c = expression.charAt(i);

            //check if char is operator
            if(precedence(c)>0){
                while(stack.isEmpty()==false && precedence(stack.peek())>=precedence(c)){
                    result = result + " "+ stack.pop();
                }
                stack.push(c);
            }else if(c==')'){
                char x = stack.pop();
                while(x!='('){
                    result = result + " "+ x;
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
            result = result + " " +stack.pop();
        }
        return result;
    }
    /**
     * Method that evaluates a given postfix expression
     * @param exp
     * @return expression result
     */
    static Double evalPostfix(String[] exp)
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
    
    /**
     * Method that verifies that the string is numeric, in this case
     * it is checking that the values are of type Double.
     * @param strNum
     * @return boolean
     */
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgBox = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        sStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        msgBox.setColumns(20);
        msgBox.setRows(5);
        jScrollPane1.setViewportView(msgBox);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Server Status:");

        sStatus.setText(".........");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(sStatus))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea msgBox;
    private javax.swing.JLabel sStatus;
    // End of variables declaration//GEN-END:variables


}

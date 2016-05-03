package loginnconvclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;


// Importing from same package to avoid writing long variable names 
import loginnconvclient.DataParser.CommandType;
import loginnconvclient.DataParser.StrType;

public class LoginNConvClient {

    public static LoginPasswdValue values = new LoginPasswdValue();
    
    public static void main(String[] args) throws Exception 
    {
        Socket soc = new Socket("127.0.0.1",9081);
        PrintWriter nos = new PrintWriter(
                            new BufferedWriter(
                                 new OutputStreamWriter(
                                   soc.getOutputStream()
                                 )
                            ),true
                          );
        
        LoginMsgDispatcher lpdispatcher = new LoginMsgDispatcher(nos);
        
        JFrame mainFrame = new JFrame("GUI Chat Client");
        
        JPanel usrnPanel = new JPanel();
        JLabel usrnLabel = new JLabel("User name");
        JTextField usrnTxtFld = new JTextField(25);
        usrnPanel.add(usrnLabel);
        usrnPanel.add(usrnTxtFld);
        
        JPanel psswdPanel = new JPanel();
        JLabel psswdLabel = new JLabel("Password");
        JTextField psswdTxtFld = new JTextField(25);
        psswdPanel.add(psswdLabel);
        psswdPanel.add(psswdTxtFld);
        
        JPanel xPanel = new JPanel();
        xPanel.add(usrnPanel);
        xPanel.add(psswdPanel);
        
        JPanel bttnPanel = new JPanel();
        JButton signupBttn = new JButton("Sign Up");
        bttnPanel.add(signupBttn);
        xPanel.add(bttnPanel);
        
        JButton loginBttn = new JButton("Login");
        bttnPanel.add(loginBttn);
        xPanel.add(bttnPanel);
                
        mainFrame.setLayout(new GridLayout(2,1));
        mainFrame.add(xPanel);

        mainFrame.setSize(400,300);
        mainFrame.setVisible(true);  
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LoginBttn lBttnHandle = new LoginBttn(usrnTxtFld, psswdTxtFld, nos);
        loginBttn.addActionListener(lBttnHandle);

        SignupBttn sBttnHandle = new SignupBttn(usrnTxtFld, psswdTxtFld, nos);
        signupBttn.addActionListener(sBttnHandle);
        
        lpdispatcher.run();
        
        String replyStr = new String();
        BufferedReader nis = new BufferedReader(
                                 new InputStreamReader(
                                     soc.getInputStream()
                                 )
        );
        
        do
        {
            replyStr = nis.readLine();
            if(replyStr.equals("NoAuth"))
            {
               JOptionPane.showMessageDialog(null, "Authentication Failed"); 
            }
        }while(!replyStr.equals("Auth"));
        System.out.println("Authentication reply received");

        JFrame chatFrame = new JFrame();
        JButton sendBttn = new JButton("Send");
        JTextArea chatArea = new JTextArea(10,10);
        chatArea.setEditable(false);
        
        JTextField chatField = new JTextField(20);
        JPanel chatPanel = new JPanel();
        chatPanel.add(chatArea);
        chatPanel.add(chatField);
        chatPanel.add(sendBttn);
        chatFrame.add(chatPanel,BorderLayout.SOUTH);
        chatFrame.setSize(400, 300);
        chatFrame.setVisible(true);
        
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SendBttn sendBttnHandle = new SendBttn(chatField, chatArea, nos);
        sendBttn.addActionListener(sendBttnHandle);
        
        while(true)
        {
            ;
        }
    }
}



class LoginBttn implements ActionListener{
    
    JTextField usr;
    JTextField pass;
    PrintWriter nos;
    
    LoginBttn(JTextField usr, JTextField pass, PrintWriter nos){
        this.usr = usr;
        this.pass = pass;
        this.nos = nos;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String usrname = usr.getText();
        String passwd = pass.getText();
        
        if(usrname.length()>0 && passwd.length()>0)
        {
            LoginNConvClient.values.setUsrnameStr(usrname);
            LoginNConvClient.values.setPasswdStr(passwd);
            LoginNConvClient.values.setButtnPressedType(CommandType.LogIn);
        }
    }    
}


class SignupBttn implements ActionListener{
    JTextField usr;
    JTextField pass;
    PrintWriter nos;
    
    SignupBttn(JTextField usr, JTextField pass, PrintWriter nos){
        this.usr = usr;
        this.pass = pass;
        this.nos = nos;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        String usrname = usr.getText();
        String passwd = pass.getText();
        
        if(usrname.length()>0 && passwd.length()>0)
        {
            LoginNConvClient.values.setUsrnameStr(usrname);
            LoginNConvClient.values.setPasswdStr(passwd);
            LoginNConvClient.values.setButtnPressedType(CommandType.SignUp);
        }
    }
}



class LoginPasswdValue{
    String usrStr;
    String passStr;
    CommandType bttnPressed= CommandType.LogIn;

    synchronized
         public String getUsrnameStr()
    {
        System.out.println("getUsrname");
        if(usrStr == null || usrStr.length() < 0)
        {
            System.out.println("waiting 4 usrname");
            try
            {
                wait();
            }catch (Exception e){}
        }
        return usrStr;
    }
    
    synchronized
         public String getPasswdStr()
    {
         System.out.println("getPsswd");
        if(passStr == null || passStr.length() < 0)
        {
            System.out.println("waiting 4 psswd");
            try
            {
                wait();
            }catch (Exception e){}
        }
        return passStr;
    }
         
    synchronized 
            public CommandType getButtnPressedType()
            {
                return bttnPressed;
            }   
    
    synchronized
         public void setUsrnameStr(String usr)
    {
        System.out.println("Set usrname");
        this.usrStr = usr;
        notify();
    }
    
    synchronized
         public void setPasswdStr(String pass)
    {
        System.out.println("Set passwd");
        this.passStr = pass;
        notify();
    }
         
    synchronized 
            public void setButtnPressedType(CommandType bttn)
            {
                bttnPressed = bttn;
            }
}





class SendBttn implements ActionListener{
    
    JTextField txtField;
    JTextArea txtArea;
    PrintWriter nos;
    
    SendBttn(JTextField txtf, JTextArea txta, PrintWriter nos){
        this.txtField = txtf;
        this.txtArea = txta;
        this.nos = nos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Date d = new Date();
        String usrStr = txtField.getText();
        String passStr = txtArea.getText();
    }
    
}
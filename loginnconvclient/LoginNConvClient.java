package loginnconvclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;


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
        
        JButton loginBttn = new JButton("Login");
        JPanel bttnPanel = new JPanel();
        bttnPanel.add(loginBttn);
        xPanel.add(bttnPanel);
        
        mainFrame.setLayout(new GridLayout(2,1));
        mainFrame.add(xPanel);
        
        mainFrame.setSize(400,300);
        mainFrame.setVisible(true);  
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        LoginBttn lBttnHandle = new LoginBttn(usrnTxtFld, psswdTxtFld, nos);
        loginBttn.addActionListener(lBttnHandle);
        
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
        System.out.println("Authentication reply received\n");

       mainFrame.dispose();
        

        JFrame chatFrame = new JFrame();
     
        JTextArea chatArea = new JTextArea(100,150);
        chatArea.setEditable(true);
        
        JTextField chatField = new JTextField(20);
           JButton sendBttn = new JButton("Send");
        JPanel chatPanel = new JPanel();
        JPanel chatPanel1 = new JPanel();
        chatPanel.add(chatField);
        chatPanel.add(sendBttn);
        chatFrame.add(chatArea);
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
        Date d = new Date();
        String usrname = usr.getText();
        String passwd = pass.getText();
        
        if(usrname.length()>0 && passwd.length()>0)
        {
            LoginNConvClient.values.setUsrnameStr(usrname);
            LoginNConvClient.values.setPasswdStr(passwd);
        }
    }
    
}



class LoginPasswdValue{
    String usrStr;
    String passStr;

    synchronized
         public String getUsrnameStr()
    {
        System.out.println("getUsrname\n");
        if(usrStr == null || usrStr.length() < 0)
        {
            System.out.println("waiting 4 usrname\n");
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
         System.out.println("getPsswd\n");
        if(passStr == null || passStr.length() < 0)
        {
            System.out.println("waiting 4 psswd\n");
            try
            {
                wait();
            }catch (Exception e){}
        }
        return passStr;
    }
    
    synchronized
         public void setUsrnameStr(String usr)
    {
        System.out.println("Set usrname\n");
        this.usrStr = usr;
        notify();
    }
    
    synchronized
         public void setPasswdStr(String pass)
    {
        System.out.println("Set passwd\n");
        this.passStr = pass;
        notify();
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

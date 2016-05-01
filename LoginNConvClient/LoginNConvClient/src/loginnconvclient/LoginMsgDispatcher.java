package loginnconvclient;

import java.io.*;

// Importing from same package to avoid writing long variable names 
import loginnconvclient.DataParser.CommandType;
import loginnconvclient.DataParser.StrType;

public class LoginMsgDispatcher extends Thread {
    
    PrintWriter nos;
    
    LoginMsgDispatcher(PrintWriter nos)
    {
        this.nos = nos;
    }
    
    @Override
    public void run() 
    {
        String usrStr = LoginNConvClient.values.getUsrnameStr();
        String passStr = LoginNConvClient.values.getPasswdStr();
        CommandType bttn = LoginNConvClient.values.getButtnPressedType();
        DataParser parser = new DataParser();
        
        usrStr = parser.setTxData(usrStr, StrType.Command, bttn);
        passStr = parser.setTxData(passStr, StrType.Command, bttn);      
        
        nos.println(usrStr);
        System.out.println("Username Sent\n");
        nos.println(passStr);
        System.out.println("Password Sent\n");
    }
}


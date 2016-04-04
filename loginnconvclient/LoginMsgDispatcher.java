/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginnconvclient;

import java.io.*;

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
        
        nos.println(usrStr);
        System.out.println("Username Sent\n");
        nos.println(passStr);
        System.out.println("Password Sent\n");
    }
}


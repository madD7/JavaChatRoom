package loginnconvserver;

import java.net.*;
import java.io.*;

public class LoginNConvServer {

    public static void main(String[] args) throws Exception {
        System.out.println("Server Signing ON");
        ServerSocket ss = new ServerSocket(9081);
        Socket soc = ss.accept();
        DataParser parser = new DataParser();
        
        BufferedReader nis = new BufferedReader(
                    new InputStreamReader(
                            soc.getInputStream()
                    )
            );
        
        PrintWriter nos = new PrintWriter(
                              new BufferedWriter(
                                  new OutputStreamWriter(
                                          soc.getOutputStream()
                                  )
                              )
                        ,true);
        
        String usrnStr = nis.readLine();
        String psswdStr = nis.readLine();
        
        System.out.println("Username = "+ usrnStr);
        System.out.println("Password = "+ psswdStr);
   
        usrnStr = parser.getRxData(usrnStr);
        psswdStr = parser.getRxData(psswdStr);
        System.out.println(usrnStr);
        System.out.println(psswdStr);        
        
        nos.println("Auth");
        
        System.out.println("Server Singing OFF");
    }    
}

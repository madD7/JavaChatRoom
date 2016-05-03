package loginnconvserver;


public class DataParser {
    
    public enum StrType
    {
        Command,
        Data
    }
    
    public enum CommandType
    {
        SignUp,
        LogIn,
        LogOut
    }
    
    String setTxData(String data, StrType strtyp, CommandType cmdtyp)
    {
        String charstrtyp=" ", charcmdtyp=" ";
        
        switch(strtyp)
        {
            case Command:
                charstrtyp = "C";
                break;
                
            case Data:
                charstrtyp = "D";
                break;
        }
        
        switch(cmdtyp)
        {
            case SignUp:
                charcmdtyp = "S";
                break;
                
            case LogIn:
                charcmdtyp = "I";
                break;
                
            case LogOut:
                charcmdtyp = "O";
                break;
        }
        
        String temp = charstrtyp + "|" + charcmdtyp + "|" + data;
        data = temp;
        return data; 
    }
    
    String getRxData(String data)
    {
        String charstrtyp = data.substring(0, data.indexOf('|'));
        String charcmdtyp = data.substring(data.indexOf('|')+1);
        
        data = charcmdtyp.substring(charcmdtyp.indexOf('|')+1);
        
//        System.out.println(charstrtyp);
//        System.out.println(charcmdtyp);
//        System.out.println(data);
        
        return data;
    }
}
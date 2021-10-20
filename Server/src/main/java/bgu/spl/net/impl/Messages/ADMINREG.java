package bgu.spl.net.impl.Messages;
import bgu.spl.net.impl.Database;
import bgu.spl.net.impl.User;

import java.util.ArrayList;

public class ADMINREG extends Message {


    private String username;
    private String password;

    public ADMINREG (String _userName, String _password, short _opCode) {
        super(_opCode);
        this.username = _userName;
        this.password = _password;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> execute() {
        short ack = 12;
        short err = 13;
        ArrayList<String> output=new ArrayList<>();
        if(myName!=null&&!myName.equals(username)){
            output.add(String.valueOf(err)+ String.valueOf(opCode));
            return output;
        }
        Database db = Database.getInstance();
        User newUser = new User(username, password,true);
        if (db.addUser(newUser)) {
            output.add(String.valueOf(ack)+ String.valueOf(opCode));
        }
        else{
            output.add(String.valueOf(err)+ String.valueOf(opCode));
        }
        return output;
    }

}

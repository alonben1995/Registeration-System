package bgu.spl.net.impl.Messages;

import bgu.spl.net.impl.Database;
import bgu.spl.net.impl.User;

import java.util.ArrayList;

public class STUDENTREG extends Message{

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }


    public STUDENTREG (String _userName, String _password, short _opCode) {
        super(_opCode);
        this.username = _userName;
        this.password = _password;

    }

    public short getOpCode() {
        return opCode;
    }

    public ArrayList<String>  execute() {
        short ack = 12;
        short err = 13;
        Database db = Database.getInstance();
        ArrayList<String> output = new ArrayList<>();
        if(myName!=null&&!myName.equals(username)){
            output.add(String.valueOf(err)+ String.valueOf(opCode));
            return output;
        }
        User newUser = new User(username, password,false);
        if (db.addUser(newUser)) {
            output.add(String.valueOf(ack) + String.valueOf(opCode));
        }
        else{
            output.add(String.valueOf(err) + String.valueOf(opCode));
        }
        return output;
    }

}


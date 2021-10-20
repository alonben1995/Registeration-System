package bgu.spl.net.impl.Messages;
import bgu.spl.net.impl.Database;
import bgu.spl.net.impl.User;

import java.util.ArrayList;

public class LOGIN extends Message {


    private String username;
    private String password;


    public LOGIN(String _userName, String _password, short _opCode) {
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
        Database db = Database.getInstance();
        ArrayList<String> output = new ArrayList<>();
        User registeredUser;
        if(myName!=null&&!myName.equals(username)){
            output.add(String.valueOf(err)+ String.valueOf(opCode));
            return output;
        }
        try {
            registeredUser = db.getUser(username);
        } catch (IllegalArgumentException e) { // user doesnt exist
            output.add(String.valueOf(err)+ String.valueOf(opCode));
            return output;
        }
        if (!this.password.equals(registeredUser.getPassword())) {
            output.add(String.valueOf(err)+ String.valueOf(opCode));
        } else if (!db.loginUser(username)) {
            output.add(String.valueOf(err)+ String.valueOf(opCode));
        } else {
            output.add(String.valueOf(ack)+ String.valueOf(opCode));
        }
        return output;
    }
}


package bgu.spl.net.impl.Messages;

import bgu.spl.net.impl.Database;

import java.util.ArrayList;

public class LOGOUT extends Message {



    public LOGOUT (short _opCode) {
        super(_opCode);
    }


    public ArrayList<String> execute() {
        short ack = 12;
        short err = 13;
        Database db = Database.getInstance();
        ArrayList<String> output = new ArrayList<>();
        if (!db.logoutUser(myName)) {
            output.add(String.valueOf(err)+ String.valueOf(opCode));
        }
        else {
            output.add(String.valueOf(ack)+ String.valueOf(opCode));
        }
        return output;
    }

}

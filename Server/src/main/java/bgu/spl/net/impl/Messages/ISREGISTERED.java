package bgu.spl.net.impl.Messages;

import bgu.spl.net.impl.Database;

import java.util.ArrayList;

public class ISREGISTERED extends Message{
    private short courseNum;


    public ISREGISTERED(short courseNum, short opCode) {
        super(opCode);
        this.courseNum = courseNum;

    }

    public ArrayList<String> execute() {
        short ack = 12;
        short err = 13;
        Database db = Database.getInstance();
        ArrayList<String> output=new ArrayList<>();
        boolean registered;
        try{
            registered = db.isRegistered(myName,courseNum);
        }catch (IllegalArgumentException I){
            output.add(String.valueOf(err)+ String.valueOf(opCode));
            return  output;
        }
        if(registered){
            output.add(String.valueOf(ack)+ String.valueOf(opCode));
            output.add("REGISTERED");
        }
        else {
            output.add(String.valueOf(ack)+ String.valueOf(opCode));
            output.add("NOT REGISTERED");
        }
        return output;
    }


}

package bgu.spl.net.impl.Messages;

import bgu.spl.net.impl.Database;

import java.util.ArrayList;

public class COURSEREG extends Message {
    private short courseNum;

    public COURSEREG(short courseNum, short opCode) {
        super(opCode);
        this.courseNum = courseNum;
    }

    public ArrayList<String> execute() {
        short ack = 12;
        short err = 13;
        Database db = Database.getInstance();
        ArrayList<String> output=new ArrayList<>();
        if(db.registerToCourse(myName,courseNum)){
            output.add(String.valueOf(ack)+ String.valueOf(opCode));
        }
        else {
            output.add(String.valueOf(err)+ String.valueOf(opCode));
        }
        return output;
    }


}

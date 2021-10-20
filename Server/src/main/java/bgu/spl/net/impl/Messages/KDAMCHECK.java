package bgu.spl.net.impl.Messages;

import bgu.spl.net.impl.Database;
import bgu.spl.net.impl.User;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Arrays;

public class KDAMCHECK extends Message{
    private short courseNum;


    public KDAMCHECK(short courseNum, short opCode) {
        super(opCode);
        this.courseNum = courseNum;

    }


    public ArrayList<String> execute() {
        short ack = 12;
        short err = 13;
        Database db = Database.getInstance();
        ArrayList<String> output=new ArrayList<>();
        if(myName==null){
            output.add(String.valueOf(err)+ String.valueOf(opCode));
            return output;
        }
        User me =db.getUser(myName);
        if(!me.isLoggedIn()){
            output.add(String.valueOf(err)+ String.valueOf(opCode));
            return output;
        }
        int[] kdam = db.checkKdamCourse(courseNum);
        if(kdam == null){
            output.add(String.valueOf(err)+ String.valueOf(opCode));
        }
        else {
            if(kdam.length==0){
                output.add(String.valueOf(ack) + String.valueOf(opCode));
                output.add("[]");
            }
            else {
                output.add(String.valueOf(ack) + String.valueOf(opCode));
                String kdamString = "[";
                for (int i = 0; i < kdam.length; i++) {
                    kdamString = kdamString + kdam[i] + ",";
                }
                kdamString = kdamString.substring(0, kdamString.length() - 1) + "]";
                output.add(kdamString);
            }
        }
        return output;
    }
}

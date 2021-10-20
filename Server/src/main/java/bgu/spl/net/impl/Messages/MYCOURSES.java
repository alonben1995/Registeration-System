package bgu.spl.net.impl.Messages;

import bgu.spl.net.impl.Database;
import bgu.spl.net.impl.User;

import java.util.ArrayList;

public class MYCOURSES extends Message{



    public MYCOURSES(short opCode) {
        super(opCode);
    }


    public ArrayList<String> execute() {
        short ack = 12;
        short err = 13;
        Database db = Database.getInstance();
        ArrayList<String> output = new ArrayList<>();

        User user;
        try {
            user = db.getUser(myName);
        } catch (IllegalArgumentException I) {
            output.add(String.valueOf(err) + String.valueOf(opCode));
            return output;
        }
        ArrayList<Short> courses = user.getRegisteredCourses();
        String str1;
        str1=courses.toString().replaceAll(" ","").replaceAll("\\u0000","");
        output.add(String.valueOf(ack) + String.valueOf(opCode));
        output.add(str1);
        return output;
    }
    }


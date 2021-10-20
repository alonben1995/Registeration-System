package bgu.spl.net.impl.Messages;
import bgu.spl.net.impl.Database;
import bgu.spl.net.impl.User;
import bgu.spl.net.impl.Course;

import java.util.ArrayList;
import java.util.Locale;

public class COURSESTAT extends Message {
    private short courseNum;


    public COURSESTAT (short _courseNum, short _opCode) {
        super(_opCode);


        this.courseNum = _courseNum;
    }



    public ArrayList<String> execute() {
        short ack = 12;
        short err = 13;
        Database db = Database.getInstance();
        User registeredUser;
        Course existCourse;
        ArrayList<String> output=new ArrayList<>();
        try {
            registeredUser = db.getUser(myName);
            existCourse = db.getCourse(courseNum);
        }
        catch (IllegalArgumentException e) { // user doesnt exist
            output.add(String.valueOf(err) +String.valueOf(opCode));
            return output;
        }
        // if were here - the course and the user both exist.
        if (! registeredUser.isLoggedIn() || ! registeredUser.isAdmin()) {
            output.add(String.valueOf(err) +String.valueOf(opCode));
            return output;
        }
        else {
            output.add(String.valueOf(ack) +String.valueOf(opCode));

            String course = "Course: "+"("+existCourse.getCourseNum()+") " + existCourse.getCourseName()+"\n";
            int num=existCourse.getNumOfMaxStudents()-existCourse.getRegisteredStudents().size();
            String seatsAvailable = "Seats Available: " + num+"/"+existCourse.getNumOfMaxStudents()+"\n";
            existCourse.getRegisteredStudents().sort(String.CASE_INSENSITIVE_ORDER);
            String studentsRegistered = "Students Registered: " + existCourse.getRegisteredStudents().toString().replaceAll(" ","").replaceAll("\\u0000","");
            String toAdd = course + seatsAvailable + studentsRegistered;
            output.add(toAdd);
            return output;
        }
    }
}

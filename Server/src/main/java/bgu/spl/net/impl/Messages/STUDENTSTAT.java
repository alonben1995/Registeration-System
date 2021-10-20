package bgu.spl.net.impl.Messages;

import bgu.spl.net.impl.Course;
import bgu.spl.net.impl.Database;
import bgu.spl.net.impl.User;

import java.util.ArrayList;

public class STUDENTSTAT extends Message{

    private String studentNameToCheck; //user name of student we check status for


    public STUDENTSTAT(String _studentToCheck, short _opCode) {
        super(_opCode);
        this.studentNameToCheck = _studentToCheck;

    }


    public ArrayList<String> execute() {
        short ack = 12;
        short err = 13;
        Database db = Database.getInstance();
        ArrayList<String> output = new ArrayList<>();
        User registeredUser;
        User Student;
        try {
            registeredUser = db.getUser(myName);
            Student = db.getUser(studentNameToCheck);
        }
        catch (IllegalArgumentException e) { // user doesnt exist
            output.add(String.valueOf(err) + String.valueOf(opCode));
            return output;
        }
        // if were here - both users exist
        if (! registeredUser.isLoggedIn() || ! registeredUser.isAdmin()||Student.isAdmin()) {
            output.add(String.valueOf(err) + String.valueOf(opCode));
        }
        else {
            output.add(String.valueOf(ack) + String.valueOf(opCode));
            String s;
            String str1 = "Student: " + studentNameToCheck + "\n";

            ArrayList<Short> unSortedCourses = Student.getRegisteredCourses();
            ArrayList<Short> sortedCourses = sortCourses(unSortedCourses, db.getOrderedCourseNum());
            String str2 = "Courses: " + sortedCourses.toString().replaceAll(" ","").replaceAll("\\u0000","");
            s = str1 + str2;
            output.add(s);
        }
        return output;
    }

    private static ArrayList<Short> sortCourses (ArrayList<Short> unSortedCourses, ArrayList<Short> orderedCourseNum) {
        ArrayList<Short> output = new ArrayList<>();
        for (short courseNum : orderedCourseNum) {
            if (unSortedCourses.contains(courseNum)) {
                output.add(courseNum);
            }
        }
        return output;
    }
}

package bgu.spl.net.impl;

import java.util.ArrayList;

/**
 * this Class represents a Course.
 * members: courseNum, courseName, kdamCoursesList, numOfMaxStudents, registeredStudents.
 * registeredStudents will be an Array that represents the students that are registered to this course,
 * you can register them and unregister them from this course.
 */
public class Course {
    private short courseNum;
    private String courseName;
    private int[] kdamCoursesList;
    private int numOfMaxStudents;
    private ArrayList<String> registeredStudents;

    public Course(short _courseNum, String _courseName, int[] _kdamCoursesList
            ,int _numOfMaxStudents) {
        courseNum = _courseNum;
        courseName = _courseName;
        kdamCoursesList = _kdamCoursesList;
        numOfMaxStudents = _numOfMaxStudents;
        registeredStudents = new ArrayList<>();
    }

    public short getCourseNum() {
        return courseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public int[] getKdamCoursesList() {
        return kdamCoursesList;
    }

    public int getNumOfMaxStudents() {
        return numOfMaxStudents;
    }

    public ArrayList<String> getRegisteredStudents() {
        return registeredStudents;
    }

    public boolean addStudent (String username) {
        if (registeredStudents.contains(username)) {
            return false; // you cant register to a course you are already registered to.
        }
        registeredStudents.add(username);
        return true;
    }

    public boolean removeStudent (String username) {
        if (!registeredStudents.contains(username)) {
            return false; // you cant unregister to a course you are already unregistered to.
        }
        registeredStudents.remove(username);
        return  true;
    }


}

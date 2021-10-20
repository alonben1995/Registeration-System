package bgu.spl.net.impl;

import java.util.ArrayList;

/**
 * this class represents a User.
 * User has its own username, Passwordm and a registeredCourses List.
 * you can add Courses and remove Courses from a specific user
 */
public class User {
    private String userName;
    private String password;
    private ArrayList<Short> registeredCourses;
    private boolean isAdmin;
    private boolean isLoggedIn;

    public User (String _username, String _password, boolean _isAdmin) {
        userName = _username;
        password = _password;
        isAdmin = _isAdmin;
        registeredCourses = new ArrayList<>();
        isLoggedIn = false;
    }



    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Short> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean addCourse(short courseNum) {
        if (registeredCourses.contains(courseNum)) {
            return false;
        }
        registeredCourses.add(courseNum);
        return true;
    }

    public boolean removeCourse(short courseNum) {
        if (!registeredCourses.contains(courseNum)) {
            return false;
        }
        registeredCourses.remove(Short.valueOf(courseNum));
        return true;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void LoginUser () {
        this.isLoggedIn = true;
    }

    public void LogoutUser () {
        this.isLoggedIn = false;
    }

    public boolean isLoggedIn () {
        return  isLoggedIn;
    }

}

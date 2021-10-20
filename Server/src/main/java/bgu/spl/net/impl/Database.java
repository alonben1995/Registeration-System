package bgu.spl.net.impl;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

	private ConcurrentHashMap<Short, Course> courseMap;
	private ConcurrentHashMap<String, User> userMap;
	private Object userLock;



	private ArrayList<Short> orderedCourseNum;

	//to prevent user from creating new Database
	private Database() {
		courseMap = new ConcurrentHashMap<>();
		userMap = new ConcurrentHashMap<>();
		userLock = new Object();
		orderedCourseNum = new ArrayList<Short>();

	}

	private static class SingletonHolder {
		private static Database instance = new Database();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * loads the courses from the file path specified
	 * into the Database, returns true if successful.
	 */
	public boolean initialize(String coursesFilePath) {
		FileReader fileReader = new FileReader(coursesFilePath);
		fileReader.read();
		return true;
	}


	public void addCourse(Course course) {
		courseMap.put(course.getCourseNum(), course);
	}

	public void addCourseNum(short cn) {
		orderedCourseNum.add(cn);
	}

	public boolean addUser(User user) {
		synchronized (userLock) {
		if (userMap.containsKey(user.getUserName())) {
			return false;
		} else {
			userMap.put(user.getUserName(), user);
			return true;
		}
	}
}

	/**
	 * assumes user is logged in
	 * @param userName
	 * @param courseNum
	 * @return
	 */
	public boolean registerToCourse(String userName, short courseNum) {
		if (!userMap.containsKey(userName)) {
			return false;
		}
		User user = userMap.get(userName);
		if (user.isAdmin()) {
			return false;
		}
		if (!courseMap.containsKey(courseNum)) {
			return false;
		}
		if (!user.isLoggedIn()) {
			return false;
		}

		Course course = courseMap.get(courseNum);
		synchronized (course) {
			if (course.getRegisteredStudents().size() == course.getNumOfMaxStudents()) {
				return false;
			}
			for (int n : course.getKdamCoursesList()) {
				Short num= (short)n;
				if (!user.getRegisteredCourses().contains(num)) {
					return false;
				}
			}
			if (user.getRegisteredCourses().contains(courseNum)) { // Already Registered
				return false;
			}
			//if we got here - perform registration.
			course.addStudent(user.getUserName());
			userMap.get(user.getUserName()).addCourse(courseNum);
			return true;
		}
	}

	public ArrayList<Short> getOrderedCourseNum() {
		return orderedCourseNum;
	}

	public boolean isRegistered(String userName, short courseNum){
		if (!userMap.containsKey(userName)) {
			return false;
		}
		User user = userMap.get(userName);
		if (user.isAdmin()) {
			throw new IllegalArgumentException("Admin Can't call the method");
		}
		if (!courseMap.containsKey(courseNum)) {
			throw new IllegalArgumentException("The Course does not exist");
		}
		if (!user.isLoggedIn()) {
			return false;
		}
		synchronized (courseMap.get(courseNum)) {
			return (user.getRegisteredCourses().contains(courseNum));
		}
	}

	public boolean unRegisterToCourse(String userName, short courseNum) {
		if (userName==null){
			return false;
		}
		if (!userMap.containsKey(userName)){
			return false;
		}
		User user = userMap.get(userName);
		if (user.isAdmin()) {
			return false;
		}
		if (!courseMap.containsKey(courseNum)) {
			return false;
		}
		if (!user.getRegisteredCourses().contains(courseNum)) { // Already UnRegistered
			return false;
		}
		if (!user.isLoggedIn()) {
			return false;
		}
		//if we got here - perform unRegistration.
		Course course = courseMap.get(courseNum);
		synchronized (course) {
			course.removeStudent(user.getUserName());
			user.removeCourse(courseNum);
			return true;
		}
	}

	public User getUser (String userName) {
		if (userName==null||!userMap.containsKey(userName)) {
			throw new IllegalArgumentException("The User does not exist");
		}
		return userMap.get(userName);
	}

	public Course getCourse (short courseNum) {
		if (!courseMap.containsKey(courseNum)) {
			throw new IllegalArgumentException("The Course does not exist");
		}
		return courseMap.get(courseNum);
	}

	public boolean loginUser (String userName) {
		if (!userMap.containsKey(userName)) {
			return false;
		}
		User user = userMap.get(userName);
		if (user.isLoggedIn()) {
			return false;
		}
		user.LoginUser();
		return true;
	}

	public boolean logoutUser (String userName) {
		if(userName==null){
			return false;
		}
		if (!userMap.containsKey(userName)) {
			return false;
		}
		User user = userMap.get(userName);
		if (!user.isLoggedIn()) {
			return false;
		}
		user.LogoutUser();
		return true;
}

	public int[] checkKdamCourse (short courseNum) {
		if (!courseMap.containsKey(courseNum)) {
			return null;
		}
		Course course = courseMap.get(courseNum);
		int[] list = course.getKdamCoursesList();
		int[] output = new int[list.length];
		int index = 0;
		for (int i=0; i< orderedCourseNum.size(); i++) {
			if (containsKey(list,orderedCourseNum.get(i))) {
				output[index] = orderedCourseNum.get(i);
				index ++;
			}
		}
		return output;
	}

	private static boolean containsKey (int[] arr, int key) {
		boolean output = false;
		for (int i = 0; i < arr.length & !output ; i++) {
			if (arr[i] == key)
				output = true;
		}
		return output;
	}

}

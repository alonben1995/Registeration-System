package bgu.spl.net.impl;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class FileReader {

    private String filePath;

    public FileReader (String filePath) {
        this.filePath = filePath;
    }

    public void read() {
        try {
            Database db = Database.getInstance();
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Course newCourse = parseLine (data);
                db.addCourseNum(newCourse.getCourseNum());
                db.addCourse (newCourse);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
    }
    private Course parseLine(String s) {
        short courseNum;
        String courseName;
        int[] kdamCourseList;
        int numOfMaxStudents;

        String[] arr = s.split("\\|");
        courseNum = Short.parseShort(arr[0]);
        courseName = arr[1];
        numOfMaxStudents = Integer.parseInt(arr[3]);
        if (!arr[2].equals("[]")) {
            kdamCourseList = Stream.of(arr[2].substring(1, arr[2].length()-1).split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        else { kdamCourseList = new int[0];}
        Course course = new Course(courseNum, courseName, kdamCourseList, numOfMaxStudents);
        return course;
    }

}



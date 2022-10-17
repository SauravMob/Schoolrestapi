package controllers;

import models.Student;
import play.mvc.Controller;
import java.util.List;

public class Department extends Controller {

    public static void admission(int id, String firstname, String lastname, int age, String department) {
        Student student = new Student(id, firstname, lastname, age, department);
        student.create();
        renderJSON("Congratulations!! Your admission is successful" + "\n" +
                student.firstname + "\n" +
                student.lastname + "\n" +
                student.age + "\n" +
                student.department);
    }

    public static void list() {
        List<Student> student = Student.find("order by Id asc").fetch();
        renderJSON(student);
    }

    public static void student(int Id) {
        Student student = Student.findById(Id);
        renderText(student.firstname+"\n"+student.lastname+"\n"+student.age+"\n"+student.department);
    }

}

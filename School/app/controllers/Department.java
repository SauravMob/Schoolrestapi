package controllers;
import models.Student;
import play.mvc.Controller;
import java.util.List;

public class Department extends Controller
{

    //To post the student data in database
    public static void admission(int id, String firstname, String lastname, int age, String department)
    {
        Student student = new Student(id, firstname, lastname, age, department);
        student.create();
        renderJSON(student);
    }

    //To get all the students list
    public static void list(int page, int Number)
    {
        if(Number == 0 && page == 0)
        {
            page = 1;
            Number = 5;
        }

        List<Student> student = Student.find("order by Id asc").fetch(page, Number);
        renderJSON(student);
    }

    //To get th specific student details by passing id of student in search bar
    public static void student(int Id)
    {
        if (Id == 0)
        {
            list(1,5);
        }
        Student student = Student.findById(Id);
        renderJSON(student);
    }
}

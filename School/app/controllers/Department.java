package controllers;

import models.Student;
import org.h2.index.PageIndex;
import org.h2.store.Page;
import play.mvc.Controller;

import javax.inject.Inject;
import javax.print.attribute.standard.PageRanges;
import javax.xml.ws.WebServiceClient;
import java.awt.print.Pageable;
import java.util.List;

public class Department extends Controller
{

    //To post the student data in database
    public static void admission(int id, String firstname, String lastname, int age, String department)
    {
        Student student = new Student(id, firstname, lastname, age, department);
        student.create();
        renderJSON("Congratulations!! Your admission is successful" + "\n" +
                student.firstname + "\n" +
                student.lastname + "\n" +
                student.age + "\n" +
                student.department);
    }

    //To get all the students list
    public static void list(int page, int Number)
    {

        if(Number == 0)
        {
            Number = 5;
        }
        else
        {
            List<Student> student = Student.find("order by Id asc").fetch(page, Number);
            renderJSON(student);
        }
    }

    //To get th specific student details by passing id of student in search bar
    public static void student(int Id)
    {
        Student student = Student.findById(Id);
        renderText(student.firstname+"\n"+student.lastname+"\n"+student.age+"\n"+student.department);
    }

}

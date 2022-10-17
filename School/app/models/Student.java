package models;

import play.db.jpa.GenericModel;
import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student extends GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int id;

    @Column(name = "First_Name")
    public String firstname;

    @Column(name = "Last_Name")
    public String lastname;

    @Column(name = "Age")
    public int age;

    @Column(name = "Department")
    public String department;

    public Student(int id, String firstname, String lastname, int age, String department)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.department = department;
    }
}

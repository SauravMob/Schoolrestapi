package controllers;

import models.Student;
import net.spy.memcached.MemcachedClient;
import play.mvc.Controller;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Scheduler extends Controller
{
    static MemcachedClient mc;

    static
    {
        try
        {
            mc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    //To store details from database into memcached
    public static void main() throws IOException
    {
        for (int id = 1; id <= Student.count(); id++)
        {
            final Student student = Student.findById(id);
            final int Id = id;

            ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

            ScheduledFuture scheduledFuture = service.scheduleAtFixedRate(new Runnable()
            {
                @Override
                public void run()
                {
                    mc.set("fname" + Id, 5, student.firstname);
                    mc.set("lname" + Id, 5, student.lastname);
                    mc.set("age" + Id, 5, student.age);
                    mc.set("dept" + Id, 5, student.department);
                }
            }, 1, 2, TimeUnit.SECONDS);
        }
    }

    //To render every detail from memcached
    public static void view(int Id)
    {
        renderText("Firstname: " + mc.get("fname" + params.get("Id")) + "\n" +
                    "Lastname: " + mc.get("lname" + params.get("Id")) + "\n" +
                    "Age: " + mc.get("age" + params.get("Id")) + "\n" +
                    "Department: " + mc.get("dept" + params.get("Id")));
    }

    //To render every detail from memcached
    public static void all()
    {
        for (int i = 1; i <= Student.count(); i++)
        {
            System.out.println(("Firstname: " + mc.get("fname" + i) + "\n" +
                                "Lastname: " + mc.get("lname" + i) + "\n" +
                                "Age: " + mc.get("age" + i) + "\n" +
                                "Department: " + mc.get("dept" + i) + "\n"));
        }
    }
}

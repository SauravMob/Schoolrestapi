package controllers;

import java.io.IOException;
import java.util.concurrent.*;
import net.spy.memcached.MemcachedClient;
import play.mvc.Controller;
import java.net.InetSocketAddress;

public class Scheduler extends Controller
{
    public static void main() throws InterruptedException, ExecutionException
    {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

        ScheduledFuture scheduledFuture = service.schedule(new Callable()
        {
            @Override
            public Object call() throws IOException
            {
                MemcachedClient mc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
                return "Connection successful!!"+ "\n" +
                        "set status:"+mc.set("Naruto",5,"Uzumaki").isDone() + "\n" +
                        "Get cache:"+mc.get("Naruto");

            }
        },1, TimeUnit.SECONDS);
        service.shutdown();
        renderText("result= "+ scheduledFuture.get());
    }
}

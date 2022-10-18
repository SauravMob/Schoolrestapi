package controllers;

import groovy.json.JsonOutput;
import play.mvc.Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class College extends Controller
{
    public static void page()
    {
        render();
    }

    //To get all the students list
    public static void get(int page, int Number) throws IOException
    {
        URL getRequest = new URL("http://localhost:9001/Department/list?page="+ params.get("page")+"&Number="+params.get("Number"));
        String read = null;

        HttpURLConnection connection = (HttpURLConnection) getRequest.openConnection();
        connection.setRequestMethod("GET");
        int responsecode = connection.getResponseCode();

        if (responsecode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();

            while ((read = in.readLine()) != null)
            {
                response.append(read);
            }
            in.close();
            renderJSON(response.toString());
        }

        else
        {
            System.out.println("GET NOT WORKED");
        }
    }

    public static void Specific()
    {
        render();
    }

    //To get th specific student details by passing id of student in search bar
    public static void get1(int id) throws IOException
    {
        URL getRequest = new URL("http://localhost:9001/Department/student/"+params.get("id"));
        String read = null;

        HttpURLConnection connection = (HttpURLConnection) getRequest.openConnection();
        connection.setRequestMethod("GET");
        int responsecode = connection.getResponseCode();

        if (responsecode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((read = in.readLine()) != null)
            {
                response.append(read);
            }
            in.close();
            renderJSON(response.toString());
        }
        else
        {
            System.out.println("GET NOT WORKED");
        }
    }

    public static void register()
    {
        render();
    }

    //To post the student data in database
    public static void post(int id, String firstname, String lastname, int age, String department) throws IOException
    {
        URL postRequest = new URL("http://localhost:9001/Department/admission");

        Map<String,Object> params = new LinkedHashMap<String, Object>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("age", age);
        params.put("department", department);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet())
        {
            if(postData.length() != 0) postData.append('&');
            postData.append((URLEncoder.encode(param.getKey(), "UTF-8")));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte [] postDatabytes = postData.toString().getBytes("UTF-8");

        String read = null;
        HttpURLConnection connection = (HttpURLConnection) postRequest.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(postDatabytes.length));
        connection.setDoOutput(true);
        connection.getOutputStream().write(postDatabytes);

        int responseCode = connection.getResponseCode();
        System.out.println("POST response code:: "+ responseCode);

        if(responseCode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputline;
            StringBuffer response = new StringBuffer();

            while ((inputline = in.readLine()) != null)
            {
                response.append(inputline);
            }
            in.close();
            renderJSON("Congratulations for adding: "+"\n"+ firstname + "\n" + lastname + "\n" + age + "\n" + department);
        }

        else
        {
            System.out.println("POST doesn't work");
        }
    }
}

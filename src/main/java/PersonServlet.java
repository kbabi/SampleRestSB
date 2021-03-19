
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonServlet extends HttpServlet{


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String requestUri = req.getRequestURI();
        String personName = requestUri.substring("/people/".length());

        Person p = DataStore.getInstance().getPerson(personName);

        if(p != null){
            ObjectMapper objectMapper = new ObjectMapper();
            res.getOutputStream().println(objectMapper.writeValueAsString(p));

            /*String json = "{\n";
            json += "\"name\": " + .quote(p.getName()) + ",\n";
            json += "\"about\": " + JSONObject.quote(p.getAbout()) + ",\n";
            json += "\"birthYear\": " + p.getBirthYear() + "\n";
            json += "}";
            response.getOutputStream().println(json);*/
        }
        else{
            //That person wasn't found, so return an empty JSON object. We could also return an error.
            res.getOutputStream().println("{person not found}");
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String name = req.getParameter("name");
        String about = req.getParameter("about");
        Integer birthYear = Integer.valueOf(req.getParameter("birthyear"));

        Person p = new Person(name,about,birthYear);


        DataStore.getInstance().putPerson(p);
    }
}

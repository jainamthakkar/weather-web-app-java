import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String city = request.getParameter("city");

        if (city != null && !city.isEmpty()) {
            try {
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?appid=&units=metric&q=" + city;
                URL url = new URL(apiUrl);
                Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8.toString());
                String jsonString = scanner.useDelimiter("\\A").next();
                scanner.close();

                out.println(jsonString);
            } catch (Exception e) {
                JSONObject errorJson = new JSONObject();
                errorJson.put("error", "Error retrieving weather data.");
                out.println(errorJson.toString());
                e.printStackTrace();
            }
        } else {
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", "Please enter a city name.");
            out.println(errorJson.toString());
        }
    }
}

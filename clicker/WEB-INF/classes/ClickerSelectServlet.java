// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/select")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class ClickerSelectServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      // Print an HTML page as the output of the query
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head><title>Query Response</title></head>");
      out.println("<body>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         
         // Step 4: Process the query result
         // Print the <form> start tag
         out.println("<form method='get' action='select'>");

         out.println("<input type='checkbox'name='choice' value='a' />");
         
         out.println("<input type='checkbox'name='choice' value='b' />");
         
         out.println("<input type='checkbox'name='choice' value='c' />");
         
         out.println("<input type='checkbox'name='choice' value='d' />");
 
         // Print the submit button and </form> end-tag
         out.println("<p><input type='submit' value='REMBATTTTTT' />");
         out.println("</form>");

         String choice = request.getParameter("choice");
         String sqlStr = "INSERT INTO responses (ID, questionNo, choice) VALUES (0, 8, '"
                           + choice + "')";
         int count = stmt.executeUpdate(sqlStr); // run the SQL statement
      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
}
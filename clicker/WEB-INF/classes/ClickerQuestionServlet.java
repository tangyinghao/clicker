// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/question")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class ClickerQuestionServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      HttpSession session = request.getSession(true);

      // Print an HTML page as the output of the query
      out.println("<!DOCTYPE html>");
      out.println("<html lang=\"en\">");

      //HEADER
      out.println("<head style='background-color: #FFFFFF'>");
      out.println("<meta charset=\"UTF-8\">");
      out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
      out.println("<title>Statistics Display</title>");
      out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js\"></script>");
      out.println("<link rel=\"stylesheet\" href=\"styles.css\">");
      out.println("</head>");

      out.println("<body style='background-color: #FFFFFF'>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
            String qnNum, qnString, imgAdd, optA, optB, optC, optD;

            ResultSet resultQn = stmt.executeQuery("SELECT * FROM questions WHERE qnNo=8");
            if (resultQn.next()) {
                    qnNum = resultQn.getString("qnNo");
                    qnString = resultQn.getString("qnText");
                    imgAdd = resultQn.getString("qnImg");
                    optA = resultQn.getString("optionA");
                    optB = resultQn.getString("optionB");
                    optC = resultQn.getString("optionC");
                    optD = resultQn.getString("optionD");

                    out.println("<h1 style='color: #232323'>Question " + qnNum + ".</h1>");
                    out.println("<img src='" + imgAdd + "' alt='Handsome Man' style='width: 10vw'>");
                    out.println("<h2 style='color: #232323'>"+ qnString +"</h2>");
                    out.println("<h3 style='color: #232323'>A: "+ optA +"</h3>");
                    out.println("<h3 style='color: #232323'>B: "+ optB +"</h3>");
                    out.println("<h3 style='color: #232323'>C: "+ optC +"</h3>");
                    out.println("<h3 style='color: #232323'>D: "+ optD +"</h3>");
                }





      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
}
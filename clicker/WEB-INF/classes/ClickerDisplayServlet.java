// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/display")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class ClickerDisplayServlet extends HttpServlet {

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

            String qnNum, qnString, imgAdd, optA, optB, optC, optD, corrAns;

            ResultSet resultQn = stmt.executeQuery("SELECT * FROM questions WHERE qnNo=8");
            if (resultQn.next()) {
                    qnNum = resultQn.getString("qnNo");
                    qnString = resultQn.getString("qnText");
                    imgAdd = resultQn.getString("qnImg");
                    optA = resultQn.getString("optionA");
                    optB = resultQn.getString("optionB");
                    optC = resultQn.getString("optionC");
                    optD = resultQn.getString("optionD");
                    corrAns = resultQn.getString("correctAns");

                    out.println("<h1 style='color: #232323'>Question " + qnNum + ".</h1>");
                    out.println("<img src='" + imgAdd + "' alt='Handsome Man' style='width: 10vw'>");
                    out.println("<h2 style='color: #232323'>"+ qnString +"</h2>");
                    out.println("<h3 style='color: #232323'>A: "+ optA +"</h3>");
                    out.println("<h3 style='color: #232323'>B: "+ optB +"</h3>");
                    out.println("<h3 style='color: #232323'>C: "+ optC +"</h3>");
                    out.println("<h3 style='color: #232323'>D: "+ optD +"</h3>");
                    out.println("<h3 style='color: #232323'></h3>");
                    out.println("<h2 style='color: #232323'>Correct Answer: "+ corrAns +"</h2>");

            int numA = 0, numB = 0, numC = 0, numD = 0;

            ResultSet resultA = stmt.executeQuery("SELECT count(*) FROM responses WHERE questionNo=8 AND choice='a'");
            if (resultA.next()) {
                    numA = resultA.getInt("count(*)");
                }

            ResultSet resultB = stmt.executeQuery("SELECT count(*) FROM responses WHERE questionNo=8 AND choice='b'");
            if (resultB.next()) {
                    numB = resultB.getInt("count(*)");
                }

            ResultSet resultC = stmt.executeQuery("SELECT count(*) FROM responses WHERE questionNo=8 AND choice='c'");
            if (resultC.next()) {
                    numC = resultC.getInt("count(*)");
                }

            ResultSet resultD = stmt.executeQuery("SELECT count(*) FROM responses WHERE questionNo=8 AND choice='d'");
            if (resultD.next()) {
                    numD = resultD.getInt("count(*)");
                }

            out.println("<canvas id=\"myChart\" style=\"width:100%;max-width:700px\"></canvas>");
            out.println("<script>");
            out.println("var xValues = ['A', 'B', 'C', 'D'];");
            out.println("var yValues = ['" + numA + "', '" + numB + "', '" + numC + "', '" + numD + "'];");
            out.println("var barColors = ['red', 'blue','orange','green'];");
            out.println("var barLabels = ['A', 'B','C', 'D'];");
            out.println("new Chart('myChart', {");
            out.println("    type: 'bar',");
            out.println("    data: {");
            out.println("        labels: xValues,");
            out.println("        datasets: [{");
            out.println("            data: yValues,");
            out.println("            backgroundColor: barColors,");
            out.println("            label: 'Number of Votes'");
            out.println("        }]");
            out.println("    },");
            out.println("    options: {");
            out.println("        scales: {");
            out.println("           y: {min: '0'}");
            out.println("        }");
            out.println("    }");
            out.println("});");
            out.println("</script>");
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
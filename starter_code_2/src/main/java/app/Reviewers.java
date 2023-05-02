package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Random;
import javax.servlet.http.Cookie;

/**
 * Temporary HTML as an example page.
 * 
 * Based on the Project Workshop code examples. This page currently: - Provides
 * a link back to the index page - Displays the list of movies from the Movies
 * Database using the JDBCConnection
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2021. email: halil.ali@rmit.edu.au
 */
public class Reviewers implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/reviewers.html";

   @Override
   public void handle(Context context) throws Exception {
      // Create a simple HTML webpage in a String
      // display login form if user is not logged in
      // context.redirect("/");

      String html = "<html>\n";
      html +="<meta charset='UTF-8'>";

      MongoDBConnection mongodb = MongoDBConnection.getConnection();

       String rid = context.sessionAttribute("password");
       System.out.println("ACCPAGE: current reviewpage id: " + rid);


      // Add some Header information
      html = html + "<head>" + "<title>Accomodation</title>\n";

      // Add some CSS (external file)
      html = html + "<link rel='stylesheet' type='text/css' href='common.css' />\n";
      html += "<script src='common.js'></script>";

      html += "<div class='header'>";
      html += "<header>";
      html += "<h1><p style='color: #FF5A5F'>Airbnb</p></h1>";
      html += "</header>";
      html += "</div>";

      // Add the body
      html = html + "<body>\n";

      ArrayList<String> reviewpage = mongodb.reviewpage(rid);
         

         html += "<table>";
         html += "<tr>";
         html += "<td>";
         html += "<h1><b style='color: #FF5A5F'>Reviews</b></h1><BR>";
         for (int y = 0; y < reviewpage.size(); y++) {
            html += "<p> " + reviewpage.get(y) +"";
         }

         String listID = context.formParam("listID");
         String delID = context.formParam("delID");
         String modID = context.formParam("modID");
         String modCom = context.formParam("modify");

         if (context.method().equals("POST") && context.formParam("delID") != null  && context.formParam("listID") != null) {
            ArrayList<String> deleteReview = mongodb.deleteReview(delID,listID);

         }


         html += "</td>";
         html += "<td>";
         html += "</table>";

      

      html = html + "</body>" + "</html>\n";
      context.html(html);
   }
}

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
public class Accpage implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/accpage.html";

   @Override
   public void handle(Context context) throws Exception {
      // Create a simple HTML webpage in a String
      // display login form if user is not logged in
      // context.redirect("/");

      String html = "<html>\n";
      html +="<meta charset='UTF-8'>";

      MongoDBConnection mongodb = MongoDBConnection.getConnection();

      String aid = context.sessionAttribute("aid");
      String username = context.sessionAttribute("id");
      String password = context.sessionAttribute("password");
      //String password = "hello";
      System.out.println("ACCPAGE: current accpage id: " + aid);

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
      html += "<p><b><a href='/'>Go Back To Home Page</a></b></p>\n";

      // Add the body
      html = html + "<body>\n";

      if (password == null) {
         ArrayList<String> accpage = mongodb.accpage(aid);
         for (int i = 0; i < accpage.size(); i += accpage.size()) {

            html += "<table>";
            html += "<tr>";

            html += "<td>";
            html += "<b style='color: #FF5A5F'>Name :</b>" + accpage.get(i) + "<BR>";
            html += "<b style='color: #FF5A5F'>Summary :</b>" + accpage.get(i + 1) + "<BR>";
            html += "<b style='color: #FF5A5F'>Street :</b>" + accpage.get(i + 14) + "<BR>";
            html += "<b style='color: #FF5A5F'>Suburb :</b>" + accpage.get(i + 2) + "<BR>";
            html += "<b style='color: #FF5A5F'>Government Area :</b>" + accpage.get(i + 3) + "<BR>";
            html += "<b style='color: #FF5A5F'>Area :</b>" + accpage.get(i + 4) + "<BR>";
            html += "<b style='color: #FF5A5F'>Country :</b>" + accpage.get(i + 5) + "<BR>";
            html += "<b style='color: #FF5A5F'>Score (Out Of 100):</b>" + accpage.get(i + 6) + "<BR>";
            html += "<b style='color: #FF5A5F'>Price : $</b>" + accpage.get(i + 7) + " PER NIGHT<BR>";
            html += "<b style='color: #FF5A5F'>Property Type :</b>" + accpage.get(i + 8) + "<BR>";
            html += "<b style='color: #FF5A5F'>Bedrooms :</b>" + accpage.get(i + 10) + "<BR>";
            html += "<b style='color: #FF5A5F'>Accommodates :</b>" + accpage.get(i + 11) + "<BR>";
            html += "<b style='color: #FF5A5F'>SuperHost :</b>" + accpage.get(i + 12) + "<BR>";
            html += "<br><br>";
            html += "<b style='color: #FF5A5F'>Amenities :</b>" + accpage.get(i + 9) + "<BR>";
            html += "</td>";

            html += "<td>";
            html += "<img src='" + accpage.get(i + 13) + "' alt='image' width='700' height='450' >";
            html += "</td>";
            html += "</tr>";
            html += "<tr>";
            html += "<td>";
            html += "<b style='color: #FF5A5F'>Reviews :</b><BR>";
            for (int y = 16; y < accpage.size(); y++) {
               html += "<p> " + accpage.get(y) + "";
            }
         }
      } else {
         ArrayList<String> accpage = mongodb.accpage(aid);
         for (int i = 0; i < accpage.size(); i += accpage.size()) {

            html += "<table>";
            html += "<tr>";
            html += "<td>";
            html += "<b style='color: #FF5A5F'>Name :</b>" + accpage.get(i) + "<BR>";
            html += "<b style='color: #FF5A5F'>Summary :</b>" + accpage.get(i + 1) + "<BR>";
            html += "<b style='color: #FF5A5F'>Street :</b>" + accpage.get(i + 14) + "<BR>";
            html += "<b style='color: #FF5A5F'>Suburb :</b>" + accpage.get(i + 2) + "<BR>";
            html += "<b style='color: #FF5A5F'>Government Area :</b>" + accpage.get(i + 3) + "<BR>";
            html += "<b style='color: #FF5A5F'>Area :</b>" + accpage.get(i + 4) + "<BR>";
            html += "<b style='color: #FF5A5F'>Country :</b>" + accpage.get(i + 5) + "<BR>";
            html += "<b style='color: #FF5A5F'>Score (Out Of 100):</b>" + accpage.get(i + 6) + "<BR>";
            html += "<b style='color: #FF5A5F'>Price : $</b>" + accpage.get(i + 7) + " PER NIGHT<BR>";
            html += "<b style='color: #FF5A5F'>Property Type :</b>" + accpage.get(i + 8) + "<BR>";
            html += "<b style='color: #FF5A5F'>Bedrooms :</b>" + accpage.get(i + 10) + "<BR>";
            html += "<b style='color: #FF5A5F'>Accommodates :</b>" + accpage.get(i + 11) + "<BR>";
            html += "<b style='color: #FF5A5F'>SuperHost :</b>" + accpage.get(i + 12) + "<BR>";
            html += "<br><br>";
            html += "<b style='color: #FF5A5F'>Amenities :</b>" + accpage.get(i + 9) + "<BR>";
            html += "</td>";

            html += "<td>";
            html += "<img src='" + accpage.get(i + 13) + "' alt='image' width='700' height='450' >";
            html += "</td>";
            html += "</tr>";

            html += "<tr>";
            html += "<td>";

            html +="<form action='' method='post'>";
            html += "<b style='color: #FF5A5F'>Write a Review? </b><BR>";
            //html += "<input style='width: 500px; height: 125px;' id='comment' name='comment'><br>";
            html += "<input type='text' id='comment' name='comment'>";
            html += "<button type='submit' class='btn btn-primary'>Add</button> \n";
            html += "</form>";
            
            html += "<b style='color: #FF5A5F'>Reviews :</b><BR>";
            for (int y = 15; y < accpage.size(); y++) {
               html += "<p> " + accpage.get(y) + "";

            }
            String comment = context.formParam("comment");


            if (context.method().equals("POST") && context.formParam("comment") != null) {
               ArrayList<String> addReview = mongodb.addReview(aid, comment, password, username);
            }
         }
         


      }
      // html += "<b style='color: #FF5A5F'>Date :</b>" + accpage.get(i + 13)+"<BR>";
      // html += "<b style='color: #FF5A5F'>Name :</b>" + accpage.get(i + 14)+"<BR>";
      // html += "<b style='color: #FF5A5F'>Comment :</b>" + accpage.get(i +
      // 15)+"<BR>";

      html += "</td>";
      html += "<td>";

      html += "</table>";

      html = html + "</body>" + "</html>\n";
      context.html(html);
   }



}

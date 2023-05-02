package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Random;
import javax.servlet.http.Cookie;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin by writing the raw HTML into a Java
 * String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class Index implements Handler {
   static MongoDBConnection mongodb = MongoDBConnection.getConnection();

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/";
   // public static String usernames[] = { "jack", "tom", "Alex", "Mame","Ralf"};
   // public static String passwords[] = { "jack", "tom", "3135623",
   // "6741662","4609491"};

   @Override
   public void handle(Context context) throws Exception {
      MongoDBConnection mongodb = MongoDBConnection.getConnection();
      // Create a simple HTML webpage in a String
      String html = "<html>\n";
      String user = "";

      // Add some Header information
      html += "<meta charset='UTF-8'>";
      html += "<head>" + "<title>AirBnb</title>\n";

      // Add some CSS (external file)
      html += "<link rel='stylesheet' type='text/css' href='common.css' />\n";

      html += "<div class='header'>";
      html += "<header>";
      html += "<h1><p style='color: #FF5A5F'>Airbnb</p></h1>";
      html += "</header>";
      html += "</div>";

      // Add the body
      html += "<body>\n";
      html += "<div class='row'>";
      // random number gen for beds and rooms

      Random rand = new Random();
      int upperbound = 10;
      int int_random = rand.nextInt(upperbound);
      // get next next boolean value
      boolean boolvalue = rand.nextBoolean();
      String bvalue = String.valueOf(boolvalue);

      // Add HTML for the logo.png image

      user = Util.getLoggedInUser(context);
      // display login form if user is not logged in
      if (user == null) {

         // get username and password (and hidden field to identify login button pressed)
         String username = context.formParam("username");
         String password = context.formParam("password");
         String login_hidden = context.formParam("login_hidden");

         if (username == null) {
            username = "";
         }
         html += "<div class='column1' style='background-color:#F8F8F8;'>";
         html += "<h2><p style='color: #FF5A5F'>Login</p></h2>";
         html += "<form action='/' method='post'>\n";
         html += "   <div class='form-group'>\n";
         html += "      <label for='username'>Username:</label>\n";
         html += "      <input class='form-control' id='username' name='username' placeholder='Enter Username' value='"
               + username + "' required>\n";
         html += "   </div>\n";
         html += "   <div class='form-group'>\n";
         html += "<br>";
         html += "      <label for='password'>Password: </label>\n";
         html += "      <input type='password' class='form-control' id='password' name='password' placeholder='Enter Password' required>\n";
         html += "   </div>\n";
         html += "   <input type='hidden' id='login_hidden' name='login_hidden' value='true'>";
         html += "<br>";
         html += "   <button type='submit' class='btn btn-primary'>Login</button>\n";
         html += "</form>\n";

         // if login button pressed proceess username and password fields
         if (login_hidden != null && login_hidden.equals("true")) {
            if (username == null || username == "" || password == null || password == "") {
               // If username or password NULL/empty, prompt for authorisation details
               html += "Enter a username and password\n";
            } else {
               // If NOT NULL, then test password for match against array of users (in your
               // code you will run a query)!
               if (checkPasswordDB(context, username, password)) {
                  // matching password found, reload page
                  context.redirect("/");
               } else {
                  // no password match found
                  html += "<b>Incorrect Username or Password</b>";
               }
            }
         }
         html += "</div>";

         html += "<div class='column' style='background-color:#F8F8F8;' style='text-align: center'>";
         html += "<h2><p style='color: #FF5A5F'>Search</p></h2>";

         html += " <form action='/' method='post'>\n";

         html += "<table style='width:100%'>";
         html += "<tr>";
         html += "<th>";

         // country html += "<div class='form-group'>\n";
         html += "<label for='country'><b><p style='color: #C23B23'>Country:</b></label>\n";
         html += "      <select id='country' name='country'>\n";
         html += "         <option value=''>--Select Option --</option>\n";
         html += "         <option>Australia</option>\n";
         html += "         <option>Turkey</option>\n";
         html += "         <option>China</option>\n";
         html += "         <option>Hong Kong</option>\n";
         html += "         <option>Portugal</option>\n";
         html += "         <option>Brazil</option>\n";
         html += "         <option>United States</option>\n";
         html += "         <option>Canada</option>\n";
         html += "         <option>Spain</option>\n";
         html += "      </select>\n";
         html += "   </div>";

         // country//market (area)
         html += "<label for='market'><b><p style='color: #F39A27'>Market:</b></label>\n";
         html += "<input type='text' placeholder='Enter Market' name='market' id='market'><br>\n";

         // bedrooms //+ (int_random + 1) +
         html += "   <div class='form-group'>\n";
         html += "      <label for='bedrooms'><b><p style='color: #FF5A5F'># Of Bedrooms:</b></label>\n";
         html += "      <select id='bedrooms' name='bedrooms'>\n";
         html += "         <option value='noinput'>--Select Option or Leave Blank--</option>\n";
         html += "         <option>1</option>\n";
         html += "         <option>2</option>\n";
         html += "         <option>3</option>\n";
         html += "         <option>4</option>\n";
         html += "         <option>5</option>\n";
         html += "         <option>6</option>\n";
         html += "         <option>7</option>\n";
         html += "         <option>8</option>\n";
         html += "         <option>9</option>\n";
         html += "         <option>10</option>\n";
         html += "      </select>\n";
         html += "   </div>";

         // beds // + (int_random + 1) +
         html += "   <div class='form-group'>\n";
         html += "      <label for='beds'><b><p style='color: #03C03C'># Of Beds:</b></label>\n";
         html += "      <select id='beds' name='beds'>\n";
         html += "         <option value='0'>--Select Option or Leave Blank--</option>\n";
         html += "         <option>1</option>\n";
         html += "         <option>2</option>\n";
         html += "         <option>3</option>\n";
         html += "         <option>4</option>\n";
         html += "         <option>5</option>\n";
         html += "         <option>6</option>\n";
         html += "         <option>7</option>\n";
         html += "         <option>8</option>\n";
         html += "         <option>9</option>\n";
         html += "         <option>11</option>\n";
         html += "         <option>12</option>\n";
         html += "         <option>13</option>\n";
         html += "         <option>14</option>\n";
         html += "         <option>15</option>\n";
         html += "         <option>16</option>\n";
         html += "         <option>17</option>\n";
         html += "         <option>18</option>\n";
         html += "         <option>19</option>\n";
         html += "         <option>20</option>\n";
         html += "      </select>\n";
         html += "   </div>\n";
         // propety type
         html += "   <div class='form-group'>\n";
         html += "      <label for='property_type'><b><p style='color: #579ABE'>Property Type:</b></label>\n";
         html += "      <select id='property_type' name='property_type'>\n";
         html += "         <option value=''>--Select Option or Leave Blank--</option>\n";
         html += "         <option>House</option>\n";
         html += "         <option>Apartment</option>\n";
         html += "         <option>Condominium</option>\n";
         html += "         <option>Loft</option>\n";
         html += "         <option>Guesthouse</option>\n";
         html += "         <option>Hostel</option>\n";
         html += "         <option>Serviced apartment</option>\n";
         html += "         <option>Bed and breakfast</option>\n";
         html += "         <option>Treehouse</option>\n";
         html += "         <option>Guest Suite</option>\n";
         html += "         <option>Townhouse</option>\n";
         html += "         <option>Villa</option>\n";
         html += "         <option>Cabin</option>\n";
         html += "         <option>Chalet</option>\n";
         html += "         <option>Boat</option>\n";
         html += "         <option>Farm stay</option>\n";
         html += "         <option>Cottage</option>\n";
         html += "         <option>Other</option>\n";
         html += "      </select>\n";
         html += "   </div>\n";
         // amenities

         // Max price
         html += "   <div class='form-group'>\n";
         html += "      <label for='price'><b><p style='color: #976ED7'>Maximum Price($ Per Night):</b></label>\n";
         html += "      <select id='price' name='price'>\n";
         html += "         <option value='999999'>--Select Option or Leave Blank--</option>\n";
         html += "         <option value='999999'>Unlimited</option>\n";
         html += "         <option value='1500'>$1500</option>\n";
         html += "         <option value='1250'>$1250</option>\n";
         html += "         <option value='1000'>$1000</option>\n";
         html += "         <option value='750'>$750</option>\n";
         html += "         <option value='500'>$500</option>\n";
         html += "         <option value='400'>$400</option>\n";
         html += "         <option value='350'>$350</option>\n";
         html += "         <option value='300'>$300</option>\n";
         html += "         <option value='250'>$250</option>\n";
         html += "         <option value='200'>$200</option>\n";
         html += "         <option value='150'>$150</option>\n";
         html += "         <option value='100'>$100</option>\n";
         html += "         <option value='75'>$75</option>\n";
         html += "         <option value='50'>$50</option>\n";
         html += "         <option value='25'>$25</option>\n";
         html += "         <option value='10'>$10</option>\n";
         html += "         <option value='1'>$1</option>\n";
         html += "      </select>\n";
         html += "   </div>\n";

         // minimum score rating
         html += "   <div class='form-group'>\n";
         html += "      <label for='review_scores_rating'><b><p style='color: #FF5A5F'>Minimum Rating(out of 100):</b></label>\n";
         html += "      <select id='review_scores_rating' name='review_scores_rating'>\n";
         html += "         <option value='0'>-Select Option or Leave Blank-</option>\n";
         html += "         <option>100</option>\n";
         html += "         <option>80</option>\n";
         html += "         <option>60</option>\n";
         html += "         <option>50</option>\n";
         html += "         <option>40</option>\n";
         html += "         <option>30</option>\n";
         html += "         <option>20</option>\n";
         html += "         <option>10</option>\n";
         html += "      </select>\n";
         html += "   </div>\n";

         // SuperHost//+ bvalue +
         html += "   <div class='form-group'>\n";
         html += "      <label for='host_is_superhost'><b><p style='color: #F39A27'>Super Host:</b></label>\n";
         html += "      <select id='host_is_superhost' name='host_is_superhost'>\n";
         html += "         <option value='noinput'>--Select Option or Leave Blank--</option>\n";
         html += "         <option value='true'>True</option>\n";
         html += "         <option value='false'>False</option>\n";
         html += "      </select>\n";
         html += "   </div>\n";

         html += "<button type='submit' class='btn btn-primary'>Search</button> \n";

         html += "</th>";

         html += "<th>";

         // country//market (area)
         html += "<label for='searchR'><b><p style='color: #FF5A5F'>Search With a Keyword: </p></b></label>\n";
         html += "<input type='text' placeholder='Search With Key Word' name='searchR' id='searchR' > ";
         html += "<br>";

         html += "<label><b><p style='color: #FF5A5F'>Select Amendities: </p></b></label>\n";

         html += "<input type='checkbox' id='WiFi' name='WiFi' value='Wifi'>";
         html += "<label for='WiFi'>Wi-Fi</label><br><br>";
         html += "<input type='checkbox' id='Microwave' name='Microwave' value='Microwave'>";
         html += "<label for='Microwave'>Microwave</label><br><br>";
         html += "<input type='checkbox' id='Kitchen' name='Kitchen' value='Kitchen'>";
         html += "<label for='Kitchen'> Kitchen</label><br><br>";

         html += "<input type='checkbox' id='pets' name='pets' value='Pets allowed'>";
         html += "<label for='pets'>Pets Allowed</label><br><br>";
         html += "<input type='checkbox' id='checkIn' name='checkIn' value='24-hour check-in'>";
         html += "<label for='checkIn'>24-Hour Check-In</label><br><br>";
         html += "<input type='checkbox' id='Heating' name='Heating' value='Heating'>";
         html += "<label for='Heating'> Heating</label><br><br>";

         html += "</th>";
         html += "</tr>";

         html += "</table>";
         html += " </form>";

         // gotta somehow intergrate a hidden value if they dont search by a certain
         // parameter, like a mongodb wildcard
         // <input type='hidden' id='myhidden' value=''>
         Formparams search = new Formparams();

         search.country = context.formParam("country");
         search.market = context.formParam("market");
         search.bedrooms = context.formParam("bedrooms");
         search.beds = context.formParam("beds");
         search.property_type = context.formParam("property_type");
         search.price = context.formParam("price");
         search.review_scores_rating = context.formParam("review_scores_rating");
         search.host_is_superhost = context.formParam("host_is_superhost");
         search.searchR = context.formParam("searchR");

         search.WiFi = context.formParam("WiFi");
         search.Microwave = context.formParam("Microwave");
         search.Kitchen = context.formParam("Kitchen");
         search.pets = context.formParam("pets");
         search.checkIn = context.formParam("checkIn");
         search.Heating = context.formParam("Heating");

         if (context.method().equals("POST") && context.formParam("aid") != null) {
            System.out.println(context.formParam("aid"));
            context.sessionAttribute("aid", context.formParam("aid"));
            context.redirect("/accpage.html");
         }

         if (context.method().equals("POST") && context.formParam("country") != null) {

            ArrayList<String> searchACC = mongodb.searchACC(search);
            context.sessionAttribute("searchobject", search);

            // System.out.println(searchACC);
            html += "<br>";
            html += "<br>";

            html += "<table style='width:100%'>";
            html += "<tr>";
            html += "<td><p style='color: #FF5A5F'><b></b></td>";
            html += "<td><p style='color: #FF5A5F'><b></b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Listing Name</b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Bedrooms</b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Beds</b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Price</b></td>";
            html += "</tr>";

            int i = 0;
            for (i = 0; i < searchACC.size(); i += 6) {
               System.out.println(i);

               html += "<tr>";
               html += "<td>";

               html += "<form action='' method='post'>\n";
               html += "<input type='hidden' name='aid' id='aid' value='" + searchACC.get(i) + "'>\n";
               html += "<button type='submit' class='btn btn-primary'>See Full Page</button>\n";
               html += " </form>\n";

               html += "</td>";
               html += "<td>" + "<img src='" + searchACC.get(i + 1) + "' alt='image' width='225' height='150' >"
                     + "</td>";
               html += "<td>" + searchACC.get(i + 2) + "</td>";
               html += "<td>" + searchACC.get(i + 3) + "</td>";
               html += "<td>" + searchACC.get(i + 4) + "</td>";
               html += "<td>" + "$" + searchACC.get(i + 5) + "</td>";
               html += "</tr>";
            }
            html += "</table>";
            if (i >= 54) {
               html += "<br>";

               html += "<form action='/' method='post'>\n";
               html += "<input type='hidden' name='skipval' id='skipval' value='60'>\n";
               html += "<button type='submit' class='btn btn-primary'>Next Page</button>\n";
               html += " </form>\n";

               html += "<br>";
            }
         }
         if (context.method().equals("POST") && context.formParam("skipval") != null) {
            search = context.sessionAttribute("searchobject");

            System.out.println(search);
            ArrayList<String> searchACCSKIP10 = mongodb.searchACCSKIP10(search);
            html += "<br>";
            html += "<br>";

            html += "<table style='width:100%'>";
            html += "<tr>";
            html += "<td><p style='color: #FF5A5F'><b></b></td>";
            html += "<td><p style='color: #FF5A5F'><b></b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Listing Name</b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Bedrooms</b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Beds</b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Price</b></td>";
            html += "</tr>";
            int i = 60;
            for (i = 0; i < searchACCSKIP10.size(); i += 6) {
               System.out.println(i);
               html += "<tr>";
               html += "<td>";

               html += "<form action='' method='post'>\n";
               html += "<input type='hidden' name='aid' id='aid' value='" + searchACCSKIP10.get(i) + "'>\n";
               html += "<button type='submit' class='btn btn-primary'>See Full Page</button>\n";
               html += " </form>\n";

               html += "</td>";
               html += "<td>" + "<img src='" + searchACCSKIP10.get(i + 1) + "' alt='image' width='225' height='150' >"
                     + "</td>";
               html += "<td>" + searchACCSKIP10.get(i + 2) + "</td>";
               html += "<td>" + searchACCSKIP10.get(i + 3) + "</td>";
               html += "<td>" + searchACCSKIP10.get(i + 4) + "</td>";
               html += "<td>" + "$" + searchACCSKIP10.get(i + 5) + "</td>";
               html += "</tr>";

            }

            html += "</table>";
            html += "<br>";
            html += "<form action='/' method='post'>";
            html += "<input type='hidden' name='backval' id='backval' value='60'>";
            html += "<button type='submit' class='btn btn-primary'>Previous Page</button>";
            html += " </form>";
            html += "<form action='/' method='post'>";
            html += "<input type='hidden' name='skipval' id='skipval' value='60'>";
            html += "<button type='submit' class='btn btn-primary'>Next Page</button>";
            html += " </form>";
            html += "<br>";

         }

         if (context.method().equals("POST") && context.formParam("backval") != null) {
            search = context.sessionAttribute("searchobject");

            ArrayList<String> searchACC = mongodb.searchACC(search);

            // System.out.println(searchACC);
            html += "<br>";
            html += "<br>";

            html += "<table style='width:100%'>";
            html += "<tr>";
            html += "<td><p style='color: #FF5A5F'><b></b></td>";
            html += "<td><p style='color: #FF5A5F'><b></b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Listing Name</b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Bedrooms</b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Beds</b></td>";
            html += "<td><p style='color: #FF5A5F'><b>Price</b></td>";
            html += "</tr>";

            int i = 0;
            for (i = 0; i < searchACC.size(); i += 6) {
               System.out.println(i);

               html += "<tr>";
               html += "<td>";

               html += "<form action='' method='post'>\n";
               html += "<input type='hidden' name='aid' id='aid' value='" + searchACC.get(i) + "'>\n";
               html += "<button type='submit' class='btn btn-primary'>See Full Page</button>\n";
               html += " </form>\n";

               html += "</td>";
               html += "<td>" + "<img src='" + searchACC.get(i + 1) + "' alt='image' width='225' height='150' >"
                     + "</td>";
               html += "<td>" + searchACC.get(i + 2) + "</td>";
               html += "<td>" + searchACC.get(i + 3) + "</td>";
               html += "<td>" + searchACC.get(i + 4) + "</td>";
               html += "<td>" + "$" + searchACC.get(i + 5) + "</td>";
               html += "</tr>";
            }
            html += "</table>";
            if (i >= 54) {
               html += "<br>";

               html += "<form action='/' method='post'>\n";
               html += "<input type='hidden' name='skipval' id='skipval' value='60'>\n";
               html += "<button type='submit' class='btn btn-primary'>Next Page</button>\n";
               html += " </form>\n";

               html += "<br>";
            }
         }
         html += "</div>";
         html += "<br>";
         html += "<br>";

      } else {
         // user is logged in - check if logout button pressed
         String logout_hidden = context.formParam("logout_hidden");

         if (logout_hidden != null && logout_hidden.equals("true")) {
            // logout clicked
            logout(context);
         } else {
            // logout not clicked - show logout button
            html += "<div class='column1' style='background-color:#F8F8F8;' style='text-align: center'>";
            html += "<h2><p style='color: #FF5A5F'>Current User</p></h2>";
            html += "<h2>" + user + "</h2>";
            html += "<form action='/' method='post'>\n";
            html += "   <input type='hidden' id='logout_hidden' name='logout_hidden' value='true'>";
            html += "   <button type='submit' class='btn btn-primary'>Logout</button>\n";
            html += "</form>\n";

            html += "<a href='reviewers.html'>Reviews</a>\n";

            // Finish the List HTML
            html += "</ul>\n";
            html += "</div>";

            html += "<div class='column' style='background-color:#F8F8F8;' style='text-align: center'>";
            html += "<h2><p style='color: #FF5A5F'>Search</p></h2>";

            html += " <form action='/' method='post'>\n";

            html += "<table style='width:100%'>";
            html += "<tr>";
            html += "<th>";

            // country html += "<div class='form-group'>\n";
            html += "<label for='country'><b><p style='color: #C23B23'>Country:</b></label>\n";
            html += "      <select id='country' name='country'>\n";
            html += "         <option value=''>--Select Option --</option>\n";
            html += "         <option>Australia</option>\n";
            html += "         <option>Turkey</option>\n";
            html += "         <option>China</option>\n";
            html += "         <option>Hong Kong</option>\n";
            html += "         <option>Portugal</option>\n";
            html += "         <option>Brazil</option>\n";
            html += "         <option>United States</option>\n";
            html += "         <option>Canada</option>\n";
            html += "         <option>Spain</option>\n";
            html += "      </select>\n";
            html += "   </div>";

            // country//market (area)
            html += "<label for='market'><b><p style='color: #F39A27'>Market:</b></label>\n";
            html += "<input type='text' placeholder='Enter Market' name='market' id='market'><br>\n";

            // bedrooms //+ (int_random + 1) +
            html += "   <div class='form-group'>\n";
            html += "      <label for='bedrooms'><b><p style='color: #FF5A5F'># Of Bedrooms:</b></label>\n";
            html += "      <select id='bedrooms' name='bedrooms'>\n";
            html += "         <option value='noinput'>--Select Option or Leave Blank--</option>\n";
            html += "         <option>1</option>\n";
            html += "         <option>2</option>\n";
            html += "         <option>3</option>\n";
            html += "         <option>4</option>\n";
            html += "         <option>5</option>\n";
            html += "         <option>6</option>\n";
            html += "         <option>7</option>\n";
            html += "         <option>8</option>\n";
            html += "         <option>9</option>\n";
            html += "         <option>10</option>\n";
            html += "      </select>\n";
            html += "   </div>";

            // beds // + (int_random + 1) +
            html += "   <div class='form-group'>\n";
            html += "      <label for='beds'><b><p style='color: #03C03C'># Of Beds:</b></label>\n";
            html += "      <select id='beds' name='beds'>\n";
            html += "         <option value='0'>--Select Option or Leave Blank--</option>\n";
            html += "         <option>1</option>\n";
            html += "         <option>2</option>\n";
            html += "         <option>3</option>\n";
            html += "         <option>4</option>\n";
            html += "         <option>5</option>\n";
            html += "         <option>6</option>\n";
            html += "         <option>7</option>\n";
            html += "         <option>8</option>\n";
            html += "         <option>9</option>\n";
            html += "         <option>11</option>\n";
            html += "         <option>12</option>\n";
            html += "         <option>13</option>\n";
            html += "         <option>14</option>\n";
            html += "         <option>15</option>\n";
            html += "         <option>16</option>\n";
            html += "         <option>17</option>\n";
            html += "         <option>18</option>\n";
            html += "         <option>19</option>\n";
            html += "         <option>20</option>\n";
            html += "      </select>\n";
            html += "   </div>\n";
            // propety type
            html += "   <div class='form-group'>\n";
            html += "      <label for='property_type'><b><p style='color: #579ABE'>Property Type:</b></label>\n";
            html += "      <select id='property_type' name='property_type'>\n";
            html += "         <option value=''>--Select Option or Leave Blank--</option>\n";
            html += "         <option>House</option>\n";
            html += "         <option>Apartment</option>\n";
            html += "         <option>Condominium</option>\n";
            html += "         <option>Loft</option>\n";
            html += "         <option>Guesthouse</option>\n";
            html += "         <option>Hostel</option>\n";
            html += "         <option>Serviced apartment</option>\n";
            html += "         <option>Bed and breakfast</option>\n";
            html += "         <option>Treehouse</option>\n";
            html += "         <option>Guest Suite</option>\n";
            html += "         <option>Townhouse</option>\n";
            html += "         <option>Villa</option>\n";
            html += "         <option>Cabin</option>\n";
            html += "         <option>Chalet</option>\n";
            html += "         <option>Boat</option>\n";
            html += "         <option>Farm stay</option>\n";
            html += "         <option>Cottage</option>\n";
            html += "         <option>Other</option>\n";
            html += "      </select>\n";
            html += "   </div>\n";
            // amenities

            // Max price
            html += "   <div class='form-group'>\n";
            html += "      <label for='price'><b><p style='color: #976ED7'>Maximum Price($ Per Night):</b></label>\n";
            html += "      <select id='price' name='price'>\n";
            html += "         <option value='999999'>--Select Option or Leave Blank--</option>\n";
            html += "         <option value='999999'>Unlimited</option>\n";
            html += "         <option value='1500'>$1500</option>\n";
            html += "         <option value='1250'>$1250</option>\n";
            html += "         <option value='1000'>$1000</option>\n";
            html += "         <option value='750'>$750</option>\n";
            html += "         <option value='500'>$500</option>\n";
            html += "         <option value='400'>$400</option>\n";
            html += "         <option value='350'>$350</option>\n";
            html += "         <option value='300'>$300</option>\n";
            html += "         <option value='250'>$250</option>\n";
            html += "         <option value='200'>$200</option>\n";
            html += "         <option value='150'>$150</option>\n";
            html += "         <option value='100'>$100</option>\n";
            html += "         <option value='75'>$75</option>\n";
            html += "         <option value='50'>$50</option>\n";
            html += "         <option value='25'>$25</option>\n";
            html += "         <option value='10'>$10</option>\n";
            html += "         <option value='1'>$1</option>\n";
            html += "      </select>\n";
            html += "   </div>\n";

            // minimum score rating
            html += "   <div class='form-group'>\n";
            html += "      <label for='review_scores_rating'><b><p style='color: #FF5A5F'>Minimum Rating(out of 100):</b></label>\n";
            html += "      <select id='review_scores_rating' name='review_scores_rating'>\n";
            html += "         <option value='0'>-Select Option or Leave Blank-</option>\n";
            html += "         <option>100</option>\n";
            html += "         <option>80</option>\n";
            html += "         <option>60</option>\n";
            html += "         <option>50</option>\n";
            html += "         <option>40</option>\n";
            html += "         <option>30</option>\n";
            html += "         <option>20</option>\n";
            html += "         <option>10</option>\n";
            html += "      </select>\n";
            html += "   </div>\n";

            // SuperHost//+ bvalue +
            html += "   <div class='form-group'>\n";
            html += "      <label for='host_is_superhost'><b><p style='color: #F39A27'>Super Host:</b></label>\n";
            html += "      <select id='host_is_superhost' name='host_is_superhost'>\n";
            html += "         <option value='noinput'>--Select Option or Leave Blank--</option>\n";
            html += "         <option value='true'>True</option>\n";
            html += "         <option value='false'>False</option>\n";
            html += "      </select>\n";
            html += "   </div>\n";

            html += "<button type='submit' class='btn btn-primary'>Search</button> \n";

            html += "</th>";

            html += "<th>";

            // country//market (area)
            html += "<label for='searchR'><b><p style='color: #FF5A5F'>Search With a Keyword: </p></b></label>\n";
            html += "<input type='text' placeholder='Search With Key Word' name='searchR' id='searchR' > ";
            html += "<br>";

            html += "<label><b><p style='color: #FF5A5F'>Select Amendities: </p></b></label>\n";

            html += "<input type='checkbox' id='WiFi' name='WiFi' value='Wifi'>";
            html += "<label for='WiFi'>Wi-Fi</label><br><br>";
            html += "<input type='checkbox' id='Microwave' name='Microwave' value='Microwave'>";
            html += "<label for='Microwave'>Microwave</label><br><br>";
            html += "<input type='checkbox' id='Kitchen' name='Kitchen' value='Kitchen'>";
            html += "<label for='Kitchen'> Kitchen</label><br><br>";

            html += "<input type='checkbox' id='pets' name='pets' value='Pets allowed'>";
            html += "<label for='pets'>Pets Allowed</label><br><br>";
            html += "<input type='checkbox' id='checkIn' name='checkIn' value='24-hour check-in'>";
            html += "<label for='checkIn'>24-Hour Check-In</label><br><br>";
            html += "<input type='checkbox' id='Heating' name='Heating' value='Heating'>";
            html += "<label for='Heating'> Heating</label><br><br>";

            html += "</th>";
            html += "</tr>";

            html += "</table>";
            html += " </form>";

            // gotta somehow intergrate a hidden value if they dont search by a certain
            // parameter, like a mongodb wildcard
            // <input type='hidden' id='myhidden' value=''>
            Formparams search = new Formparams();

            search.country = context.formParam("country");
            search.market = context.formParam("market");
            search.bedrooms = context.formParam("bedrooms");
            search.beds = context.formParam("beds");
            search.property_type = context.formParam("property_type");
            search.price = context.formParam("price");
            search.review_scores_rating = context.formParam("review_scores_rating");
            search.host_is_superhost = context.formParam("host_is_superhost");
            search.searchR = context.formParam("searchR");

            search.WiFi = context.formParam("WiFi");
            search.Microwave = context.formParam("Microwave");
            search.Kitchen = context.formParam("Kitchen");
            search.pets = context.formParam("pets");
            search.checkIn = context.formParam("checkIn");
            search.Heating = context.formParam("Heating");

            if (context.method().equals("POST") && context.formParam("aid") != null) {
               System.out.println(context.formParam("aid"));
               context.sessionAttribute("aid", context.formParam("aid"));
               context.redirect("/accpage.html");
            }

            if (context.method().equals("POST") && context.formParam("country") != null) {

               ArrayList<String> searchACC = mongodb.searchACC(search);
               context.sessionAttribute("searchobject", search);

               // System.out.println(searchACC);
               html += "<br>";
               html += "<br>";

               html += "<table style='width:100%'>";
               html += "<tr>";
               html += "<td><p style='color: #FF5A5F'><b></b></td>";
               html += "<td><p style='color: #FF5A5F'><b></b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Listing Name</b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Bedrooms</b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Beds</b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Price</b></td>";
               html += "</tr>";

               int i = 0;
               for (i = 0; i < searchACC.size(); i += 6) {
                  // System.out.println(i);

                  html += "<tr>";
                  html += "<td>";

                  html += "<form action='' method='post'>\n";
                  html += "<input type='hidden' name='aid' id='aid' value='" + searchACC.get(i) + "'>\n";
                  html += "<button type='submit' class='btn btn-primary'>See Full Page</button>\n";
                  html += " </form>\n";

                  html += "</td>";
                  html += "<td>" + "<img src='" + searchACC.get(i + 1) + "' alt='image' width='225' height='150' >"
                        + "</td>";
                  html += "<td>" + searchACC.get(i + 2) + "</td>";
                  html += "<td>" + searchACC.get(i + 3) + "</td>";
                  html += "<td>" + searchACC.get(i + 4) + "</td>";
                  html += "<td>" + "$" + searchACC.get(i + 5) + "</td>";
                  html += "</tr>";
               }
               html += "</table>";
               if (i >= 54) {
                  html += "<br>";

                  html += "<form action='/' method='post'>\n";
                  html += "<input type='hidden' name='skipval' id='skipval' value='60'>\n";
                  html += "<button type='submit' class='btn btn-primary'>Next Page</button>\n";
                  html += " </form>\n";

                  html += "<br>";
               }
            }
            if (context.method().equals("POST") && context.formParam("skipval") != null) {
               search = context.sessionAttribute("searchobject");

               System.out.println(search);
               ArrayList<String> searchACCSKIP10 = mongodb.searchACCSKIP10(search);
               html += "<br>";
               html += "<br>";

               html += "<table style='width:100%'>";
               html += "<tr>";
               html += "<td><p style='color: #FF5A5F'><b></b></td>";
               html += "<td><p style='color: #FF5A5F'><b></b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Listing Name</b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Bedrooms</b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Beds</b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Price</b></td>";
               html += "</tr>";
               int i = 60;
               for (i = 0; i < searchACCSKIP10.size(); i += 6) {
                  // System.out.println(i);
                  html += "<tr>";
                  html += "<td>";

                  html += "<form action='' method='post'>\n";
                  html += "<input type='hidden' name='aid' id='aid' value='" + searchACCSKIP10.get(i) + "'>\n";
                  html += "<button type='submit' class='btn btn-primary'>See Full Page</button>\n";
                  html += " </form>\n";

                  html += "</td>";
                  html += "<td>" + "<img src='" + searchACCSKIP10.get(i + 1)
                        + "' alt='image' width='225' height='150' >" + "</td>";
                  html += "<td>" + searchACCSKIP10.get(i + 2) + "</td>";
                  html += "<td>" + searchACCSKIP10.get(i + 3) + "</td>";
                  html += "<td>" + searchACCSKIP10.get(i + 4) + "</td>";
                  html += "<td>" + "$" + searchACCSKIP10.get(i + 5) + "</td>";
                  html += "</tr>";

               }

               html += "</table>";
               html += "<br>";
               html += "<form action='/' method='post'>";
               html += "<input type='hidden' name='backval' id='backval' value='60'>";
               html += "<button type='submit' class='btn btn-primary'>Previous Page</button>";
               html += " </form>";
               html += "<form action='/' method='post'>";
               html += "<input type='hidden' name='skipval' id='skipval' value='60'>";
               html += "<button type='submit' class='btn btn-primary'>Next Page</button>";
               html += " </form>";
               html += "<br>";

            }

            if (context.method().equals("POST") && context.formParam("backval") != null) {
               search = context.sessionAttribute("searchobject");

               ArrayList<String> searchACC = mongodb.searchACC(search);

               // System.out.println(searchACC);
               html += "<br>";
               html += "<br>";

               html += "<table style='width:100%'>";
               html += "<tr>";
               html += "<td><p style='color: #FF5A5F'><b></b></td>";
               html += "<td><p style='color: #FF5A5F'><b></b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Listing Name</b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Bedrooms</b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Beds</b></td>";
               html += "<td><p style='color: #FF5A5F'><b>Price</b></td>";
               html += "</tr>";

               int i = 0;
               for (i = 0; i < searchACC.size(); i += 6) {
                  // System.out.println(i);

                  html += "<tr>";
                  html += "<td>";

                  html += "<form action='' method='post'>\n";
                  html += "<input type='hidden' name='aid' id='aid' value='" + searchACC.get(i) + "'>\n";
                  html += "<button type='submit' class='btn btn-primary'>See Full Page</button>\n";
                  html += " </form>\n";

                  html += "</td>";
                  html += "<td>" + "<img src='" + searchACC.get(i + 1) + "' alt='image' width='225' height='150' >"
                        + "</td>";
                  html += "<td>" + searchACC.get(i + 2) + "</td>";
                  html += "<td>" + searchACC.get(i + 3) + "</td>";
                  html += "<td>" + searchACC.get(i + 4) + "</td>";
                  html += "<td>" + "$" + searchACC.get(i + 5) + "</td>";
                  html += "</tr>";
               }
               html += "</table>";
               if (i >= 54) {
                  html += "<br>";

                  html += "<form action='/' method='post'>\n";
                  html += "<input type='hidden' name='skipval' id='skipval' value='60'>\n";
                  html += "<button type='submit' class='btn btn-primary'>Next Page</button>\n";
                  html += " </form>\n";

                  html += "<br>";
               }
            }
            html += "</div>";
            html += "<br>";
            html += "<br>";
         }
      }

      // Finish the HTML webpage
      html += "</body>" + "</html>\n";

      // DO NOT MODIFY THIS
      // Makes Javalin render the webpage
      context.html(html);
   }
   /*
    check if username and password matches agains array of users (in your code,
    this needs to query the database)
    public boolean checkPassword(Context context, String username, String password) {
       boolean passwordMatchFound = false;
       for (int i = 0; i < usernames.length; i++) {
          if (usernames[i].equalsIgnoreCase(username) && passwords[i].equals(password)) {
              match found - login the user
             login(context, username,password);
             passwordMatchFound = true;
          }
       }
       return passwordMatchFound;
    } */

   public boolean checkPasswordDB(Context context, String username, String password) {
      boolean passwordMatchFound = false;
      MongoDBConnection mongodb = MongoDBConnection.getConnection();
      ArrayList<String> loginuser = mongodb.login(username, password);
      if (!loginuser.isEmpty()) {
         login(context, username, password);
         passwordMatchFound = true;
      }
      return passwordMatchFound;
   }

   // logout the current user by removing their cookie and session id details
   public void logout(Context context) {
      String id = context.cookie("id");
      context.sessionAttribute(id);
      context.removeCookie("id");
      context.sessionAttribute(id, null);
      context.sessionAttribute("id", null);
      context.sessionAttribute("password", null);
      // reload the page
      context.redirect("/");
   }

   // login the user by creating a random user id and associating in their cookie
   // and session
   public void login(Context context, String username, String password) {
      String id = UUID.randomUUID().toString();
      // context.sessionAttribute("id", id);
      context.sessionAttribute(id, username);
      context.sessionAttribute("id", username);
      context.sessionAttribute("password", password);
      context.cookie("id", id);
   }

   public String accpage(String aid) {

      String html = "";

      MongoDBConnection mongodb = MongoDBConnection.getConnection();
      ArrayList<String> accpage = mongodb.accpage(aid);

      if (accpage.isEmpty()) {
         html += "<script>location.href = '/home.html'; </script>\n";
      } else {
         html += "<script>location.href = '/home.html'; </script>\n";
      }
      return html;
   }

}

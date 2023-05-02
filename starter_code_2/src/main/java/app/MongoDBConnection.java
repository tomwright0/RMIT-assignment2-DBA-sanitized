package app;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Projections.include;

import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class MongoDBConnection {
   /**
    * mongodb+srv://username:password@atlas.server.address/database Goto
    * https://cloud.mongodb.com/ click connect > connect your application > Driver
    * Java > Version 4.3 or later copy connection string if you have time out
    * issues (due to firewalls) choose verion 3.3 or earlier instead
    **/
   private static final String DATABASE_URL = "****SANITIZED*****";
   // private static final String DATABASE_URL =
   static MongoClient client;
   MongoDatabase database;
   MongoCollection<Document> allListings;
   private static MongoDBConnection mongodb = null;

   public static MongoDBConnection getConnection() {
      // check that MongoDBConnection is available (if not establish)
      if (mongodb == null) {
         mongodb = new MongoDBConnection();
      }
      return mongodb;
   }

   public MongoDBConnection() {
      System.out.println("Creating MongoDB Connection Object");

      try {
         client = MongoClients.create(DATABASE_URL);
         database = client.getDatabase("sample_airbnb");
         allListings = database.getCollection("listingsAndReviews");
      } catch (Exception e) {
         // If there is an error, lets just print the error
         System.err.println(e.getMessage());
      }
   }

   public static void closeConnection() {
      try {
         if (client != null) {
            client.close();
            System.out.println("Database Connection closed");
         }
      } catch (Exception e) {
         // connection close failed.
         System.err.println(e.getMessage());
      }
   }

   public String nullToString(Object object) {
      return (object == null) ? "" : object.toString();
   }

   private Object generateRandomID() {
      return null;
   }

   public ArrayList<String> searchACC(Formparams search) {
      MongoCursor<Document> cursor;
      if (search.bedrooms.equals("noinput") && search.host_is_superhost.equals("noinput")) {
         System.out.println("1");
         cursor = allListings
               .find(and(eq("address.country", Pattern.compile(".*" + search.country + ".*")),
                     eq("address.market", Pattern.compile(".*" + search.market + ".*")),
                     gte("beds", Integer.parseInt(search.beds)),
                     eq("property_type", Pattern.compile(".*" + search.property_type + ".*")),
                     lte("price", Integer.parseInt(search.price)),
                     eq("name", Pattern.compile(".*" + search.searchR + ".*")),
                     all("amenities",
                           Arrays.asList(Pattern.compile(".*" + nullToString(search.WiFi) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Microwave) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Kitchen) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.pets) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.checkIn) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Heating) + ".*")))))
               .projection(fields(include("images.picture_url", "name", "bedrooms", "beds", "price")))
               .sort(eq("price", -1)).limit(10).iterator();

      } else if (search.host_is_superhost.equals("noinput")) {
         System.out.println("2");
         cursor = allListings
               .find(and(eq("address.country", Pattern.compile(".*" + search.country + ".*")),
                     eq("address.market", Pattern.compile(".*" + search.market + ".*")),
                     eq("bedrooms", Integer.parseInt(search.bedrooms)), gte("beds", Integer.parseInt(search.beds)),
                     eq("property_type", Pattern.compile(".*" + search.property_type + ".*")),
                     lte("price", Integer.parseInt(search.price)),
                     gte("review_scores.review_scores_rating", Integer.parseInt(search.review_scores_rating)),
                     eq("name", Pattern.compile(".*" + search.searchR + ".*")),
                     all("amenities",
                           Arrays.asList(Pattern.compile(".*" + nullToString(search.WiFi) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Microwave) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Kitchen) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.pets) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.checkIn) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Heating) + ".*")))))
               .projection(fields(include("images.picture_url", "name", "bedrooms", "beds", "price")))
               .sort(eq("price", -1)).limit(10).iterator();

      } else if (search.bedrooms.equals("noinput")) {
         System.out.println("3");
         cursor = allListings
               .find(and(eq("address.country", Pattern.compile(".*" + search.country + ".*")),
                     eq("address.market", Pattern.compile(".*" + search.market + ".*")),
                     eq("property_type", Pattern.compile(".*" + search.property_type + ".*")),
                     gte("beds", Integer.parseInt(search.beds)), lte("price", Integer.parseInt(search.price)),
                     gte("review_scores.review_scores_rating", Integer.parseInt(search.review_scores_rating)),
                     eq("host.host_is_superhost", Boolean.parseBoolean(search.host_is_superhost)),
                     eq("name", Pattern.compile(".*" + search.searchR + ".*")),
                     all("amenities",
                           Arrays.asList(Pattern.compile(".*" + nullToString(search.WiFi) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Microwave) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Kitchen) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.pets) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.checkIn) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Heating) + ".*")))))
               .projection(fields(include("images.picture_url", "name", "bedrooms", "beds", "price")))
               .sort(eq("price", -1)).limit(10).iterator();

      } else {
         System.out.println("4");

         cursor = allListings
               .find(and(eq("address.country", Pattern.compile(".*" + search.country + ".*")),
                     eq("address.market", Pattern.compile(".*" + search.market + ".*")),
                     eq("bedrooms", Integer.parseInt(search.bedrooms)), gte("beds", Integer.parseInt(search.beds)),
                     eq("property_type", Pattern.compile(".*" + search.property_type + ".*")),
                     lte("price", Integer.parseInt(search.price)),
                     gte("review_scores.review_scores_rating", Integer.parseInt(search.review_scores_rating)),
                     eq("host.host_is_superhost", Boolean.parseBoolean(search.host_is_superhost)),
                     eq("name", Pattern.compile(".*" + search.searchR + ".*")),
                     all("amenities",
                           Arrays.asList(Pattern.compile(".*" + nullToString(search.WiFi) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Microwave) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Kitchen) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.pets) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.checkIn) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Heating) + ".*")))))
               .projection(fields(include("images.picture_url", "name", "bedrooms", "beds", "price")))
               .sort(eq("price", -1)).limit(10).iterator();
      }

      ArrayList<String> searchACC = new ArrayList<String>();
      try {
         while (cursor.hasNext()) {

            Document record = cursor.next();
            searchACC.add(nullToString(record.get("_id")));
            searchACC.add(nullToString(((Document) record.get("images")).get("picture_url")));
            searchACC.add(nullToString(record.get("name")));
            searchACC.add(nullToString(record.get("bedrooms")));
            searchACC.add(nullToString(record.get("beds")));
            searchACC.add(nullToString(record.get("price")));
            // System.out.println(cursor);
         }
      } finally {
         cursor.close();
      }
      return searchACC;
   }

   public ArrayList<String> searchACCSKIP10(Formparams search) {
      MongoCursor<Document> cursor;
      if (search.bedrooms.equals("noinput") && search.host_is_superhost.equals("noinput")) {
         System.out.println("1");
         cursor = allListings
               .find(and(eq("address.country", Pattern.compile(".*" + search.country + ".*")),
                     eq("address.market", Pattern.compile(".*" + search.market + ".*")),
                     gte("beds", Integer.parseInt(search.beds)),
                     eq("property_type", Pattern.compile(".*" + search.property_type + ".*")),
                     lte("price", Integer.parseInt(search.price)),
                     eq("name", Pattern.compile(".*" + search.searchR + ".*")),
                     all("amenities",
                           Arrays.asList(Pattern.compile(".*" + nullToString(search.WiFi) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Microwave) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Kitchen) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.pets) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.checkIn) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Heating) + ".*")))))
               .projection(fields(include("images.picture_url", "name", "bedrooms", "beds", "price")))
               .sort(eq("price", -1)).limit(10).skip(10).iterator();

      } else if (search.host_is_superhost.equals("noinput")) {
         System.out.println("2");
         cursor = allListings
               .find(and(eq("address.country", Pattern.compile(".*" + search.country + ".*")),
                     eq("address.market", Pattern.compile(".*" + search.market + ".*")),
                     eq("bedrooms", Integer.parseInt(search.bedrooms)), gte("beds", Integer.parseInt(search.beds)),
                     eq("property_type", Pattern.compile(".*" + search.property_type + ".*")),
                     lte("price", Integer.parseInt(search.price)),
                     gte("review_scores.review_scores_rating", Integer.parseInt(search.review_scores_rating)),
                     eq("name", Pattern.compile(".*" + search.searchR + ".*")),
                     all("amenities",
                           Arrays.asList(Pattern.compile(".*" + nullToString(search.WiFi) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Microwave) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Kitchen) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.pets) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.checkIn) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Heating) + ".*")))))
               .projection(fields(include("images.picture_url", "name", "bedrooms", "beds", "price")))
               .sort(eq("price", -1)).limit(10).skip(10).iterator();

      } else if (search.bedrooms.equals("noinput")) {
         System.out.println("3");
         cursor = allListings
               .find(and(eq("address.country", Pattern.compile(".*" + search.country + ".*")),
                     eq("address.market", Pattern.compile(".*" + search.market + ".*")),
                     eq("property_type", Pattern.compile(".*" + search.property_type + ".*")),
                     gte("beds", Integer.parseInt(search.beds)), lte("price", Integer.parseInt(search.price)),
                     gte("review_scores.review_scores_rating", Integer.parseInt(search.review_scores_rating)),
                     eq("host.host_is_superhost", Boolean.parseBoolean(search.host_is_superhost)),
                     eq("name", Pattern.compile(".*" + search.searchR + ".*")),
                     all("amenities",
                           Arrays.asList(Pattern.compile(".*" + nullToString(search.WiFi) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Microwave) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Kitchen) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.pets) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.checkIn) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Heating) + ".*")))))
               .projection(fields(include("images.picture_url", "name", "bedrooms", "beds", "price")))
               .sort(eq("price", -1)).limit(10).skip(10).iterator();

      } else {
         System.out.println("4");

         cursor = allListings
               .find(and(eq("address.country", Pattern.compile(".*" + search.country + ".*")),
                     eq("address.market", Pattern.compile(".*" + search.market + ".*")),
                     eq("bedrooms", Integer.parseInt(search.bedrooms)), gte("beds", Integer.parseInt(search.beds)),
                     eq("property_type", Pattern.compile(".*" + search.property_type + ".*")),
                     lte("price", Integer.parseInt(search.price)),
                     gte("review_scores.review_scores_rating", Integer.parseInt(search.review_scores_rating)),
                     eq("host.host_is_superhost", Boolean.parseBoolean(search.host_is_superhost)),
                     eq("name", Pattern.compile(".*" + search.searchR + ".*")),
                     all("amenities",
                           Arrays.asList(Pattern.compile(".*" + nullToString(search.WiFi) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Microwave) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Kitchen) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.pets) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.checkIn) + ".*"),
                                 Pattern.compile(".*" + nullToString(search.Heating) + ".*")))))
               .projection(fields(include("images.picture_url", "name", "bedrooms", "beds", "price")))
               .sort(eq("price", -1)).limit(10).skip(10).iterator();
      }

      ArrayList<String> searchACCSKIP10 = new ArrayList<String>();
      try {
         while (cursor.hasNext()) {

            Document record = cursor.next();
            searchACCSKIP10.add(nullToString(record.get("_id")));
            searchACCSKIP10.add(nullToString(((Document) record.get("images")).get("picture_url")));
            searchACCSKIP10.add(nullToString(record.get("name")));
            searchACCSKIP10.add(nullToString(record.get("bedrooms")));
            searchACCSKIP10.add(nullToString(record.get("beds")));
            searchACCSKIP10.add(nullToString(record.get("price")));
            // System.out.println(cursor);
         }
      } finally {
         cursor.close();
      }
      return searchACCSKIP10;
   }

   public ArrayList<String> accpage(String aid) {
      MongoCursor<Document> cursor = allListings.find(and(eq("_id", Pattern.compile(".*" + aid + ".*"))))
            .projection(fields(include("name", "summary", "address.suburb", "address.government_area", "address.market",
                  "address.country", "review_scores.review_scores_rating", "price", "property_type", "amenities",
                  "bedrooms", "accommodates", "host.host_is_superhost", "images.picture_url", "reviews",
                  "address.street")))
            .iterator();

      ArrayList<String> accpage = new ArrayList<String>();
      try {
         while (cursor.hasNext()) {
            Document record = cursor.next();
            accpage.add(nullToString(record.get("name")));
            accpage.add(nullToString(record.get("summary")));
            accpage.add(nullToString(((Document) record.get("address")).get("suburb")));
            accpage.add(nullToString(((Document) record.get("address")).get("government_area")));
            accpage.add(nullToString(((Document) record.get("address")).get("market")));
            accpage.add(nullToString(((Document) record.get("address")).get("country")));
            accpage.add(nullToString(((Document) record.get("review_scores")).get("review_scores_rating")));
            accpage.add(nullToString(record.get("price")));
            accpage.add(nullToString(record.get("property_type")));
            accpage.add(nullToString(record.get("amenities")));
            accpage.add(nullToString(record.get("bedrooms")));
            accpage.add(nullToString(record.get("accommodates")));
            accpage.add(nullToString(((Document) record.get("host")).get("host_is_superhost")));
            accpage.add(nullToString(((Document) record.get("images")).get("picture_url")));
            accpage.add(nullToString(((Document) record.get("address")).get("street")));

            // Grabs the reviews, seperates them into a list entry each. so
            // currentreview.get(0) would be the first review etc...
            List<Document> currentreview = record.getList("reviews", Document.class);
            ArrayList<Review> reviewList = new ArrayList<Review>();
            for (Document d : currentreview) {
               reviewList.add(new Review(d.getString("_id"), d.getDate("date"), d.getString("listing_id"),
                     d.getString("reviewer_id"), d.getString("reviewer_name"), d.getString("comments")));
            }
            for (Review str : reviewList) {
               System.out.println(str.getEntireReview());
               accpage.add(str.getEntireReview());
            }
         }
      } finally {
         cursor.close();
      }
      return accpage;
   }

   public ArrayList<String> reviewpage(String rid) {
      MongoCursor<Document> cursor = allListings.find(and(eq("reviews.reviewer_id", Pattern.compile("" + rid + ""))))
            .projection(fields(include("reviews.$"))).iterator();

      ArrayList<String> reviewpage = new ArrayList<String>();
      try {
         while (cursor.hasNext()) {
            Document record = cursor.next();

            // Grabs the reviews, seperates them into a list entry each. so
            // currentreview.get(0) would be the first review etc...
            List<Document> currentreview = record.getList("reviews", Document.class);

            ArrayList<Review> reviewerList = new ArrayList<Review>();
            for (Document d : currentreview) {
               reviewerList.add(new Review(d.getString("_id"), d.getDate("date"), d.getString("listing_id"),
                     d.getString("reviewer_id"), d.getString("reviewer_name"), d.getString("comments")));
            }
            for (Review str : reviewerList) {
               // System.out.println(str.getEntireReview());
               // Adds the "entire review" string to reviewpage.
               reviewpage.add(str.getReviewHTML());
            }
         }
      } finally {
         cursor.close();
      }
      return reviewpage;
   }

   public ArrayList<String> addReview(String aid, String comment, String password, String username) {
      MongoCollection<Document> cursor = allListings;
      Document review = new Document("_id", generateRandomID());
      review.append("date", java.time.LocalDate.now()).append("listing_id", aid).append("reviewer_id", password)
            .append("reviewer_name", username).append("comments", comment);
      DBObject list = new BasicDBObject("reviews", review);
      try {
         allListings.updateOne(eq("_id", aid), new Document().append("$push", list));
      } finally {
      }
      return null;
   }

   public ArrayList<String> login(String username, String password) {
      ArrayList<String> curruser = new ArrayList<String>();
      // MongoCursor<Document> cursor = allListings.find(eq("_id",
      // Pattern.compile(".*.*")))
      // .projection(fields(include("reviews"), exclude("_id"))).iterator();
      MongoCursor<Document> cursor = allListings
            .find(and(eq("reviews.reviewer_name", "" + username + ""), eq("reviews.reviewer_id", "" + password + "")))
            .projection(fields(include("reviews.$"), exclude("_id"))).iterator();
      while (cursor.hasNext()) {
         Document record = cursor.next();
         List<Document> currentreview = record.getList("reviews", Document.class);
         for (Document d : currentreview) {
            System.out.println(d.getString("reviewer_name"));
            System.out.println(d.getString("reviewer_id"));
            if ((d.getString("reviewer_name").equals(username)) && (d.getString("reviewer_id").equals(password))) {
               curruser.add(d.getString("reviewer_name"));
               curruser.add(d.getString("reviewer_id"));
            }
         }
      }
      System.out.println(curruser.get(0));
      return curruser;
   }

   public ArrayList<String> deleteReview(String listID, String delID) {
      try {
         Bson fil = Filters.eq("_id", listID);
         Bson del = Updates.pull("reviews", new Document("_id", delID));
         allListings.updateOne(fil, del);
      } finally {
      }
      return null;

   }
}
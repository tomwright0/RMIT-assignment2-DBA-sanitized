package app;

import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;



public class Review {
    private String ID;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
    private Date date;
    private String listing_id;
    private String reviewer_id;
    private String reviewer_name;
    private String comments;
    private String entirereview;
    private String reviewhtml;

    public Review(String ID, Date date, String listing_id, String reviewer_id, String reviewer_name, String comments) {
        this.ID = ID;
        this.date = date;
        this.listing_id = listing_id;
        this.reviewer_id = reviewer_id;
        this.reviewer_name = reviewer_name;
        this.comments = comments;
        this.entirereview = "<b>Date: </b>" + dateFormat.format(this.date) + "<br><b>Reviewer: </b>" + this.reviewer_name + "<br><b>Comment: </b>" + this.comments;
        this.reviewhtml = this.entirereview +

       "<form action='' method='post'>\n" +
       "<input type='hidden' name='listID' id='listID' value='"+ listing_id +"'>\n"+
       "<input type='hidden' name='delID' id='delID' value='"+ ID +"'>\n"+
       "<button type='submit' class='btn btn-primary'>Delete</button>\n"+
       " </form>\n"+

       "<form action='' method='post'>\n"+
       "<input type='hidden' name='modID' id='modID' value='"+ID+"'>\n"+
       "<input type='text' placeholder='Modify Comment' name='modify' id='modify'><br><br>"+        
       "<button type='submit' class='btn btn-primary'>Modify</button>\n"+
       " </form>\n";

       
    }


    
    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getListing_id() {
        return this.listing_id;
    }

    public void setListing_id(String listing_id) {
        this.listing_id = listing_id;
    }

    public String getReviewer_id() {
        return this.reviewer_id;
    }

    public void setReviewer_id(String reviewer_id) {
        this.reviewer_id = reviewer_id;
    }

    public String getReviewer_name() {
        return this.reviewer_name;
    }

    public void setReviewer_name(String reviewer_name) {
        this.reviewer_name = reviewer_name;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getEntireReview() {
        return this.entirereview;
    }

    public String getReviewHTML() {
        return this.reviewhtml;
    }


}

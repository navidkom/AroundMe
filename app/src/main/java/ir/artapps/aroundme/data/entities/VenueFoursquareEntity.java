
package ir.artapps.aroundme.data.entities;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class VenueFoursquareEntity {

    @SerializedName("id")
    @Expose
    private String     id;

    @SerializedName("name")
    @Expose
    private String     name;

    @SerializedName("location")
    @Expose
    private Location   location;

    @SerializedName("canonicalUrl")
    @Expose
    private String     canonicalUrl;

    @SerializedName("contact")
    @Expose
    private Contact contact;

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;

    @SerializedName("verified")
    @Expose
    private Boolean verified;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("dislike")
    @Expose
    private Boolean dislike;

    @SerializedName("ok")
    @Expose
    private Boolean ok;

    @SerializedName("rating")
    @Expose
    private Double rating;

    @SerializedName("ratingColor")
    @Expose
    private String ratingColor;

    @SerializedName("ratingSignals")
    @Expose
    private Integer ratingSignals;

    @SerializedName("createdAt")
    @Expose
    private Integer createdAt;

    @SerializedName("shortUrl")
    @Expose
    private String shortUrl;

    @SerializedName("timeZone")
    @Expose
    private String timeZone;


    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getDislike() {
        return dislike;
    }

    public void setDislike(Boolean dislike) {
        this.dislike = dislike;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public Integer getRatingSignals() {
        return ratingSignals;
    }

    public void setRatingSignals(Integer ratingSignals) {
        this.ratingSignals = ratingSignals;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }


}

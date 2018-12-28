package ir.artapps.aroundme.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Venue implements Comparable<Venue>, Parcelable {

    private double         distance   = 0;
    private String         id;
    private String         name;
    private Location       location;
    private double         latitude;
    private double         longitude;
    private String         icon;
    private String         iconPrefix;
    private String         canonicalUrl;
    private Contact        contact;
    private List<Category> categories = null;
    private Boolean        verified;
    private String         url;
    private Boolean        dislike;
    private Boolean        ok;
    private Double         rating;
    private String         ratingColor;
    private Integer        ratingSignals;
    private Integer        createdAt;
    private String         shortUrl;
    private String         timeZone;
    private PhotoItem      bestPhoto;

    public PhotoItem getBestPhoto() {
        return bestPhoto;
    }

    public void setBestPhoto(PhotoItem bestPhoto) {
        this.bestPhoto = bestPhoto;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconPrefix() {
        return iconPrefix;
    }

    public void setIconPrefix(String iconPrefix) {
        this.iconPrefix = iconPrefix;
    }

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

    @Override
    public int compareTo(@NonNull Venue o) {
        return Double.compare(distance, o.distance);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.distance);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.location, flags);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.icon);
        dest.writeString(this.iconPrefix);
        dest.writeString(this.canonicalUrl);
        dest.writeParcelable(this.contact, flags);
        dest.writeTypedList(this.categories);
        dest.writeValue(this.verified);
        dest.writeString(this.url);
        dest.writeValue(this.dislike);
        dest.writeValue(this.ok);
        dest.writeValue(this.rating);
        dest.writeString(this.ratingColor);
        dest.writeValue(this.ratingSignals);
        dest.writeValue(this.createdAt);
        dest.writeString(this.shortUrl);
        dest.writeString(this.timeZone);
        dest.writeParcelable(this.bestPhoto, flags);
    }

    public Venue() {
    }

    protected Venue(Parcel in) {
        this.distance = in.readDouble();
        this.id = in.readString();
        this.name = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.icon = in.readString();
        this.iconPrefix = in.readString();
        this.canonicalUrl = in.readString();
        this.contact = in.readParcelable(Contact.class.getClassLoader());
        this.categories = in.createTypedArrayList(Category.CREATOR);
        this.verified = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.url = in.readString();
        this.dislike = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.ok = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.rating = (Double) in.readValue(Double.class.getClassLoader());
        this.ratingColor = in.readString();
        this.ratingSignals = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createdAt = (Integer) in.readValue(Integer.class.getClassLoader());
        this.shortUrl = in.readString();
        this.timeZone = in.readString();
        this.bestPhoto = in.readParcelable(PhotoItem.class.getClassLoader());
    }

    public static final Creator<Venue> CREATOR = new Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel source) {
            return new Venue(source);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };
}

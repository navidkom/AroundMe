package ir.artapps.aroundme.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static ir.artapps.aroundme.data.entities.Venue.TABLE_NAME;


@Entity(tableName = TABLE_NAME, indices = {@Index(value = {"id"},
        unique = true)})
public class Venue implements Comparable<Venue>,Parcelable {

    @Ignore
    public static transient final String TABLE_NAME = "venue";

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    @Ignore
    private double distance = 0;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_")
    public transient long id_;


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("latitude")
    @Expose
    private double latitude;

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

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("icon")
    @Expose
    private String icon;

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

    @SerializedName("iconPrefix")
    @Expose
    private String iconPrefix;

    @SerializedName("canonicalUrl")
    @Expose
    private String canonicalUrl;

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

    @Override
    public int compareTo(@NonNull Venue o) {
        return Double.compare(distance , o.distance);
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
        dest.writeList(this.categories);
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
        this.categories = new ArrayList<Category>();
        in.readList(this.categories, Category.class.getClassLoader());
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
    }

    public static final Parcelable.Creator<Venue> CREATOR = new Parcelable.Creator<Venue>() {
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

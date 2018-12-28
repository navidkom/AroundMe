package ir.artapps.aroundme.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by navid on 27,December,2018
 */
public class PhotoItem implements Parcelable {
    @SerializedName("id")
    @Expose
    private String  id;
    @SerializedName("createdAt")
    @Expose
    private Integer createdAt;
    @SerializedName("source")
    @Expose
    private Source  source;
    @SerializedName("prefix")
    @Expose
    private String  prefix;
    @SerializedName("suffix")
    @Expose
    private String  suffix;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("visibility")
    @Expose
    private String  visibility;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getImageUrl() {
        return String.format("%s%sx%s%s", prefix, width, height, suffix);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeValue(this.createdAt);
        dest.writeParcelable(this.source, flags);
        dest.writeString(this.prefix);
        dest.writeString(this.suffix);
        dest.writeValue(this.width);
        dest.writeValue(this.height);
        dest.writeString(this.visibility);
    }

    public PhotoItem() {
    }

    protected PhotoItem(Parcel in) {
        this.id = in.readString();
        this.createdAt = (Integer) in.readValue(Integer.class.getClassLoader());
        this.source = in.readParcelable(Source.class.getClassLoader());
        this.prefix = in.readString();
        this.suffix = in.readString();
        this.width = (Integer) in.readValue(Integer.class.getClassLoader());
        this.height = (Integer) in.readValue(Integer.class.getClassLoader());
        this.visibility = in.readString();
    }

    public static final Parcelable.Creator<PhotoItem> CREATOR = new Parcelable.Creator<PhotoItem>() {
        @Override
        public PhotoItem createFromParcel(Parcel source) {
            return new PhotoItem(source);
        }

        @Override
        public PhotoItem[] newArray(int size) {
            return new PhotoItem[size];
        }
    };
}

package ir.artapps.aroundme.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabeledLatLng implements Parcelable {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeValue(this.lat);
        dest.writeValue(this.lng);
    }


    protected LabeledLatLng(Parcel in) {
        this.label = in.readString();
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.lng = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<LabeledLatLng> CREATOR = new Parcelable.Creator<LabeledLatLng>() {
        @Override
        public LabeledLatLng createFromParcel(Parcel source) {
            return new LabeledLatLng(source);
        }

        @Override
        public LabeledLatLng[] newArray(int size) {
            return new LabeledLatLng[size];
        }
    };

}

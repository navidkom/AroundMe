package ir.artapps.aroundme.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Icon implements Parcelable{

    private final static int SIZE = 64;

    @SerializedName("prefix")
    @Expose
    public String prefix;

    @SerializedName("suffix")
    @Expose
    public String suffix;

    public String getUrl() {
        if (prefix != null && suffix != null) {
            return prefix + SIZE + suffix;
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.prefix);
        dest.writeValue(this.suffix);
    }


    protected Icon(Parcel in) {
        this.prefix = in.readString();
        this.suffix = in.readString();
    }

    public static final Parcelable.Creator<Icon> CREATOR = new Parcelable.Creator<Icon>() {
        @Override
        public Icon createFromParcel(Parcel source) {
            return new Icon(source);
        }

        @Override
        public Icon[] newArray(int size) {
            return new Icon[size];
        }
    };
}

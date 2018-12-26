package ir.artapps.aroundme.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Icon {

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
}

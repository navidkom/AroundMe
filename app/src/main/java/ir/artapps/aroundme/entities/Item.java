
package ir.artapps.aroundme.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("venue")
    @Expose
    private VenueFoursquareEntity venue;

    public VenueFoursquareEntity getVenue() {
        return venue;
    }

    public void setVenue(VenueFoursquareEntity venue) {
        this.venue = venue;
    }
}

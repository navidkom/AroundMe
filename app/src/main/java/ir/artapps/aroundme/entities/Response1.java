package ir.artapps.aroundme.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by navid on 22,December,2018
 */
public class Response1 {

    @SerializedName("venue")
    @Expose
    private VenueFoursquareEntity venues = null;
    @SerializedName("confident")
    @Expose
    private Boolean confident;

    public VenueFoursquareEntity getVenue() {
        return venues;
    }

    public void setVenue(VenueFoursquareEntity venue) {
        this.venues = venues;
    }

    public Boolean getConfident() {
        return confident;
    }

    public void setConfident(Boolean confident) {
        this.confident = confident;
    }
}

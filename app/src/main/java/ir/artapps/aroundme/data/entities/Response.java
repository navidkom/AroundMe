package ir.artapps.aroundme.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by navid on 22,December,2018
 */
public class Response {

    @SerializedName("venues")
    @Expose
    private List<VenueFoursquareEntity> venues = null;
    @SerializedName("confident")
    @Expose
    private Boolean confident;

    public List<VenueFoursquareEntity> getVenues() {
        return venues;
    }

    public void setVenues(List<VenueFoursquareEntity> venues) {
        this.venues = venues;
    }

    public Boolean getConfident() {
        return confident;
    }

    public void setConfident(Boolean confident) {
        this.confident = confident;
    }
}

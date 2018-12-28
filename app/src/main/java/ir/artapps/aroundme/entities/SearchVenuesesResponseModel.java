package ir.artapps.aroundme.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by navid on 20,December,2018
 */
public class SearchVenuesesResponseModel {

    @SerializedName("meta")
    @Expose
    private Meta     meta;
    @SerializedName("response")
    @Expose
    private ExploreResponse response;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ExploreResponse getResponse() {
        return response;
    }

    public void setResponse(ExploreResponse response) {
        this.response = response;
    }

}

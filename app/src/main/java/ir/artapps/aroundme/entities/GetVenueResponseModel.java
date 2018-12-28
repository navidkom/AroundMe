package ir.artapps.aroundme.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by navid on 27,December,2018
 */
public class GetVenueResponseModel {

    @SerializedName("meta")
    @Expose
    private Meta      meta;
    @SerializedName("response")
    @Expose
    private Response1 response;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response1 getResponse() {
        return response;
    }

    public void setResponse(Response1 response) {
        this.response = response;
    }
}

package ir.artapps.aroundme.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.artapps.aroundme.data.entities.Meta;
import ir.artapps.aroundme.data.entities.Response;


/**
 * Created by navid on 20,December,2018
 */
public class SearchVenuesesResponseModel {

    @SerializedName("meta")
    @Expose
    private Meta     meta;
    @SerializedName("response")
    @Expose
    private Response response;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}

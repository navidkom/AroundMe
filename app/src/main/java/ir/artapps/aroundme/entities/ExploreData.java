
package ir.artapps.aroundme.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExploreData {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("response")
    @Expose
    private ExploreResponse mExploreResponse;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ExploreResponse getmExploreResponse() {
        return mExploreResponse;
    }

    public void setmExploreResponse(ExploreResponse mExploreResponse) {
        this.mExploreResponse = mExploreResponse;
    }
}

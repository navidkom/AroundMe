package ir.artapps.aroundme.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by navid on 22,December,2018
 */
public class Meta {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}

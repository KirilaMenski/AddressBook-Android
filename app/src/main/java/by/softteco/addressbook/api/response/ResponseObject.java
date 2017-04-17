package by.softteco.addressbook.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kirila on 17.4.17.
 */

public class ResponseObject implements Serializable {

    @SerializedName("routes")
    private List<Leg> mRoute;

    public ResponseObject() {
    }

    public ResponseObject(List<Leg> route) {
        mRoute = route;
    }

    public List<Leg> getRoute() {
        return mRoute;
    }

    public void setRoute(List<Leg> route) {
        mRoute = route;
    }
}

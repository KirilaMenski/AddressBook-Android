package by.softteco.addressbook.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kirila on 17.4.17.
 */
public class Leg implements Serializable {

    @SerializedName("legs")
    private List<Route> mRoutes;
    @SerializedName("overview_polyline")
    private Polyline mPolyline;

    public Leg() {

    }

    public Leg(List<Route> routes, Polyline polyline) {
        mRoutes = routes;
        mPolyline = polyline;
    }

    public List<Route> getRoutes() {
        return mRoutes;
    }

    public void setRoutes(List<Route> routes) {
        mRoutes = routes;
    }

    public Polyline getPolyline() {
        return mPolyline;
    }

    public void setPolyline(Polyline polyline) {
        mPolyline = polyline;
    }
}

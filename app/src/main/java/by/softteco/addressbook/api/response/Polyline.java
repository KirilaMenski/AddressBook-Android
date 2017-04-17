package by.softteco.addressbook.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kirila on 17.4.17.
 */
public class Polyline implements Serializable {

    @SerializedName("points")
    private String mPoints;

    public Polyline() {
    }

    public Polyline(String points) {
        mPoints = points;
    }

    public String getPoints() {
        return mPoints;
    }

    public void setPoints(String points) {
        mPoints = points;
    }
}

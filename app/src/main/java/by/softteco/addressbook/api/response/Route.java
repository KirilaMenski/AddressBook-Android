package by.softteco.addressbook.api.response;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kirila on 17.4.17.
 */

public class Route implements Serializable {

    @SerializedName("distance")
    private Distance mDistance;
    @SerializedName("duration")
    private Duration mDuration;
    @SerializedName("end_address")
    private String mEndAddress;
    @SerializedName("end_location")
    private LatLng mEndLocation;
    @SerializedName("start_address")
    private String mStartAddress;
    @SerializedName("start_location")
    private LatLng mStartLocation;
    @SerializedName("points")
    private List<LatLng> mPoints;

    public Route(){

    }

    public Distance getDistance() {
        return mDistance;
    }

    public void setDistance(Distance distance) {
        mDistance = distance;
    }

    public Duration getDuration() {
        return mDuration;
    }

    public void setDuration(Duration duration) {
        mDuration = duration;
    }

    public String getEndAddress() {
        return mEndAddress;
    }

    public void setEndAddress(String endAddress) {
        mEndAddress = endAddress;
    }

    public LatLng getEndLocation() {
        return mEndLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        mEndLocation = endLocation;
    }

    public String getStartAddress() {
        return mStartAddress;
    }

    public void setStartAddress(String startAddress) {
        mStartAddress = startAddress;
    }

    public LatLng getStartLocation() {
        return mStartLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        mStartLocation = startLocation;
    }

    public List<LatLng> getPoints() {
        return mPoints;
    }

    public void setPoints(List<LatLng> points) {
        mPoints = points;
    }
}

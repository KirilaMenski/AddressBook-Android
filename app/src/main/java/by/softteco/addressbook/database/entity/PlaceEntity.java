package by.softteco.addressbook.database.entity;

/**
 * Created by kirila on 12.4.17.
 */

public class PlaceEntity {

    private int mId;
    private String mName;
    private String mAddress;
    private double mLatitude;
    private double mLongitude;

    public PlaceEntity() {

    }

    public PlaceEntity(int id, String name, String address, double latitude, double longitude) {
        mId = id;
        mName = name;
        mAddress = address;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }
}

package by.softteco.addressbook.database.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by kirila on 12.4.17.
 */
@DatabaseTable(tableName = "places")
public class PlaceEntity {

    @DatabaseField(generatedId = true, columnName = "id")
    private int mId;
    @DatabaseField(columnName = "name")
    private String mName;
    @DatabaseField(columnName = "address")
    private String mAddress;
    @DatabaseField(columnName = "latitude")
    private double mLatitude;
    @DatabaseField(columnName = "longitude")
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

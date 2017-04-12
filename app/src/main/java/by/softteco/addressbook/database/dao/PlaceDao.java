package by.softteco.addressbook.database.dao;

import java.util.List;

import by.softteco.addressbook.database.entity.PlaceEntity;

/**
 * Created by kirila on 12.4.17.
 */

public interface PlaceDao {

    void addPlace(PlaceEntity placeEntity);

    void updatePlace(PlaceEntity placeEntity);

    void deletePlace(PlaceEntity placeEntity);

    List<PlaceEntity> getAllPlaces();

}

package by.softteco.addressbook.database.daoimpl;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import by.softteco.addressbook.AddressBookApp;
import by.softteco.addressbook.database.DatabaseHelper;
import by.softteco.addressbook.database.dao.PlaceDao;
import by.softteco.addressbook.database.entity.PlaceEntity;

/**
 * Created by kirila on 12.4.17.
 */

public class PlaceDaoImpl implements PlaceDao {

    private static PlaceDaoImpl mInstance;
    private WeakReference<Context> mContext;
    private DatabaseHelper mDatabaseHelper;
    private Dao<PlaceEntity, Integer> mDao;

    public static synchronized PlaceDaoImpl getInstance() {
        if (mInstance == null) {
            mInstance = new PlaceDaoImpl();
        }
        return mInstance;
    }

    private PlaceDaoImpl() {
        mContext = new WeakReference<>(AddressBookApp.getAppContext());
        mDatabaseHelper = OpenHelperManager.getHelper(mContext.get(), DatabaseHelper.class);
        mDao = mDatabaseHelper.getPlaceDao();
    }

    @Override
    public void addPlace(PlaceEntity placeEntity) {
        try {
            mDao.create(placeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePlace(PlaceEntity placeEntity) {
        try {
            mDao.update(placeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePlace(PlaceEntity placeEntity) {
        try {
            mDao.delete(placeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PlaceEntity> getAllPlaces() {
        List<PlaceEntity> placeEntities = new ArrayList<>();
        try {
            QueryBuilder<PlaceEntity, Integer> queryBuilder = mDao.queryBuilder();
            placeEntities = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return placeEntities;
    }
}

package by.softteco.addressbook.database;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import by.softteco.addressbook.database.entity.PlaceEntity;

/**
 * Created by kirila on 12.4.17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "Place.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<PlaceEntity, Integer> mPlaceDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, PlaceEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<PlaceEntity, Integer> getPlaceDao() {
        if (mPlaceDao == null) {
            try {
                mPlaceDao = getDao(PlaceEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mPlaceDao;
    }

}

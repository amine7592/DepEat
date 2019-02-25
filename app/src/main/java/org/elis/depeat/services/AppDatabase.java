package org.elis.depeat.services;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import org.elis.depeat.dao.OrderDao;
import org.elis.depeat.datamodels.Order;
import org.elis.depeat.datamodels.ProductTypeConverter;


@Database(entities = {Order.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase INSTANCE;

    public abstract OrderDao orderDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "orders-database")
                    .build();
        }
        return INSTANCE;


    }

    public static void destroyInstance() {
        INSTANCE = null;
    }



}

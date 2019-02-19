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
                    context.getApplicationContext(), AppDatabase.class, "orders-database")
                    // allow queries on the main thread.
                    // Don't do this on a real app! See PersistenceBasicSample for an example.
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;


    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

}

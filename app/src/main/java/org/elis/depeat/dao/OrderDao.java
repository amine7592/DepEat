package org.elis.depeat.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.elis.depeat.datamodels.Order;

import java.util.List;
@Dao
public interface OrderDao {

    @Query("SELECT * FROM `Order`")
    List<Order> getAll();


    @Insert
    void insert(Order order);

    @Delete
    void delete(Order order);
}

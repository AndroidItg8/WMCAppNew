package itg8.com.wmcapp.database;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import itg8.com.wmcapp.cilty.model.CityModel;

/**
 * Created by Android itg 8 on 10/25/2017.
 */

public class BaseDatabaseHelper {

    private DatabaseHelper dbHelper;
    private static BaseDatabaseHelper obj;

    public DatabaseHelper getHelper(Context context) {

        if (dbHelper == null) {
            dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return dbHelper;
    }

    public  void releaseHelper(){
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }

    public  static  BaseDatabaseHelper getBaseInstance()
    {
        if (obj == null) {
            obj = new BaseDatabaseHelper();
        }
        return obj;


    }


    public void clearCityTable()
    {

        try {
            TableUtils.clearTable(dbHelper.getConnectionSource(), CityModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

package itg8.com.wmcapp.database;

import android.content.Context;

import com.google.gson.Gson;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.database.BaseDatabaseHelper;
import itg8.com.wmcapp.database.Crud;
import itg8.com.wmcapp.database.DatabaseHelper;

/**
 * Created by swapnilmeshram on 08/11/17.
 */

public class CityTableManipulate implements Crud {

    private final DatabaseHelper helper;

    public CityTableManipulate(Context context) {
        helper = BaseDatabaseHelper.getBaseInstance().getHelper(context);
    }

    @Override
    public int create(Object item) {
        try {
            return helper.getmDAOCity().create((CityModel) item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Object item) {
        return 0;
    }

    @Override
    public int deleteAll() {

        try {
            TableUtils.clearTable(helper.getConnectionSource(), CityModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public CityModel getCity(String city) {
        CityModel model=null;
        try{
            model=helper.getmDAOCity().queryBuilder().where().eq(CityModel.FIELD_NAME,city).queryForFirst();
        }catch (SQLException e){
            e.printStackTrace();
        }
        Logs.d("cityModel : "+new Gson().toJson(model));
        return model;
    }
}

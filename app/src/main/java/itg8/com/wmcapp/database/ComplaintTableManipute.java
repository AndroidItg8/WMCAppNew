package itg8.com.wmcapp.database;

import android.content.Context;

import com.google.gson.Gson;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;

/**
 * Created by Android itg 8 on 11/13/2017.
 */

public class ComplaintTableManipute implements Crud.ComplaintCrud, Crud {

    private final DatabaseHelper helper;

    public ComplaintTableManipute(Context context) {
        helper = BaseDatabaseHelper.getBaseInstance().getHelper(context);
    }
    @Override
    public TempComplaintModel getComplaint(String value, String key) {
        TempComplaintModel model=null;
        try{
            model=helper.getmDAOComplaint().queryBuilder().where().eq(key,value).queryForFirst();
        }catch (SQLException e){
            e.printStackTrace();
        }
        Logs.d("cityModel : "+new Gson().toJson(model));
        return model;
    }



    @Override
    public int create(Object item) {
        try {
            return helper.getmDAOComplaint().create((TempComplaintModel) item);
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
            TableUtils.clearTable(helper.getConnectionSource(), TempComplaintModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }




}

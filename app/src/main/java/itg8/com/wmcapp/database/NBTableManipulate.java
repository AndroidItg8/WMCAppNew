package itg8.com.wmcapp.database;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import itg8.com.wmcapp.board.model.NoticeBoardModel;
import itg8.com.wmcapp.board.model.TempNoticeBoardModel;

/**
 * Created by Android itg 8 on 11/28/2017.
 */

public class NBTableManipulate implements Crud<TempNoticeBoardModel> ,Crud.DeleteNBItem{
    private final DatabaseHelper helper;

    public NBTableManipulate(Context context) {
        helper = BaseDatabaseHelper.getBaseInstance().getHelper(context);
    }

    @Override
    public int create(Object item) {
        try {
            return helper.getmDAONoticeBoard().create((TempNoticeBoardModel) item);
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
            TableUtils.clearTable(helper.getConnectionSource(), NoticeBoardModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public List<TempNoticeBoardModel> getAll() {
        try {
            return helper.getmDAONoticeBoard().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int deleteNBItem(int value, String key) {
        try {

            DeleteBuilder<TempNoticeBoardModel, Integer> deleteBuilder = helper.getmDAONoticeBoard().deleteBuilder();
           deleteBuilder.where().eq(key, value);
           return deleteBuilder.delete();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public List<TempNoticeBoardModel> getNoticeBoardItem(String typeNoticeUnsync, String fieldType) {
        try {
            return helper.getmDAONoticeBoard().queryForEq(fieldType,typeNoticeUnsync);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    public void getDeleteAll() {
        try {
            helper.getmDAONoticeBoard().delete(getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package itg8.com.wmcapp.database;

import java.util.List;

import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;

/**
 * Created by swapnilmeshram on 08/11/17.
 */

public interface Crud<T> {
    public int create(Object item);
    public int delete(Object item);
    public  int deleteAll();
    public List<T> getAll();

     public interface ComplaintCrud{
         TempComplaintModel getComplaint(String value, String key);

       List<TempComplaintModel> getAllComplaint();
          int  deleteComplaint(int value, String key);

     }
      public  interface CityCrud{
          CityModel getCity(String value,String key);
      }
      public interface DeleteNBItem{

         int deleteNBItem(int value,String key);
      }

}

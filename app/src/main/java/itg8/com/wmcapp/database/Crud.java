package itg8.com.wmcapp.database;

import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;

/**
 * Created by swapnilmeshram on 08/11/17.
 */

public interface Crud {
    public int create(Object item);
    public int delete(Object item);
    public  int deleteAll();

     public interface ComplaintCrud{
         TempComplaintModel getComplaint(String value, String key);

     }
      public  interface CityCrud{
          CityModel getCity(String value,String key);

      }

}

package itg8.com.wmcapp.database;

/**
 * Created by swapnilmeshram on 08/11/17.
 */

public interface Crud {
    public int create(Object item);
    public int delete(Object item);
    public  int deleteAll();
}

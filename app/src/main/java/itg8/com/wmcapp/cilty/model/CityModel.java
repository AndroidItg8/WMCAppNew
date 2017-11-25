
package itg8.com.wmcapp.cilty.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@Generated("org.jsonschema2pojo")
@DatabaseTable(tableName=CityModel.TABLE_CITY)
public class CityModel implements Parcelable
{

    public static final String TABLE_CITY ="TableCity";
    public static final String FIELD_ID ="id";
    public static final String FIELD_PID ="pid";
    public static final String FIELD_NAME ="name";
    public static final String FIELD_STATE_ID ="state id";
    @DatabaseField(columnName = FIELD_PID , generatedId = true)
    @Expose
    private long tblId;

    @DatabaseField(columnName = FIELD_ID)
    @SerializedName("ID")
    @Expose
    private int ID;

    @DatabaseField(columnName = FIELD_NAME)
    @SerializedName("Name")
    @Expose
    private String Name;

    @DatabaseField(columnName = FIELD_STATE_ID)

    @SerializedName("StateID")
    @Expose
    private int StateID;

     private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public final static Parcelable.Creator<CityModel> CREATOR = new Creator<CityModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CityModel createFromParcel(Parcel in) {
            CityModel instance = new CityModel();
            instance.ID = ((int) in.readValue((int.class.getClassLoader())));
            instance.Name = ((String) in.readValue((String.class.getClassLoader())));
            instance.StateID = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public CityModel[] newArray(int size) {
            return (new CityModel[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The ID
     */
    public int getID() {
        return ID;
    }

    /**
     * 
     * @param ID
     *     The ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * 
     * @return
     *     The Name
     */
    public String getName() {
        return Name;
    }

    /**
     * 
     * @param Name
     *     The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * 
     * @return
     *     The StateID
     */
    public int getStateID() {
        return StateID;
    }

    /**
     * 
     * @param StateID
     *     The StateID
     */
    public void setStateID(int StateID) {
        this.StateID = StateID;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ID);
        dest.writeValue(Name);
        dest.writeValue(StateID);
    }

    public int describeContents() {
        return  0;
    }

}

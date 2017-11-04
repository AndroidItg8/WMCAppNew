
package itg8.com.wmcapp.cilty.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CityModel implements Parcelable
{

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("StateID")
    @Expose
    private int StateID;
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

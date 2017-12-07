
package itg8.com.wmcapp.prabhag.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class PrabhagModel implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("Prabhag_Name")
    @Expose
    private String PrabhagName;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("adddate")
    @Expose
    private String adddate;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("WardList")
    @Expose
    private List<itg8.com.wmcapp.prabhag.model.WardList> WardList = new ArrayList<itg8.com.wmcapp.prabhag.model.WardList>();

    public void setPragbhagSelected(boolean pragbhagSelected) {
        isPragbhagSelected = pragbhagSelected;
    }

    private  boolean isPragbhagSelected;
    public final static Parcelable.Creator<PrabhagModel> CREATOR = new Creator<PrabhagModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PrabhagModel createFromParcel(Parcel in) {
            PrabhagModel instance = new PrabhagModel();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.PrabhagName = ((String) in.readValue((String.class.getClassLoader())));
            instance.Address = ((String) in.readValue((String.class.getClassLoader())));
            instance.Description = ((String) in.readValue((String.class.getClassLoader())));
            instance.adddate = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.WardList, (itg8.com.wmcapp.prabhag.model.WardList.class.getClassLoader()));
            return instance;
        }

        public PrabhagModel[] newArray(int size) {
            return (new PrabhagModel[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The pkid
     */
    public int getPkid() {
        return pkid;
    }

    /**
     * 
     * @param pkid
     *     The pkid
     */
    public void setPkid(int pkid) {
        this.pkid = pkid;
    }

    /**
     * 
     * @return
     *     The PrabhagName
     */
    public String getPrabhagName() {
        return PrabhagName;
    }

    /**
     * 
     * @param PrabhagName
     *     The Prabhag_Name
     */
    public void setPrabhagName(String PrabhagName) {
        this.PrabhagName = PrabhagName;
    }

    /**
     * 
     * @return
     *     The Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * 
     * @param Address
     *     The Address
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

    /**
     * 
     * @return
     *     The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * 
     * @param Description
     *     The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * 
     * @return
     *     The adddate
     */
    public String getAdddate() {
        return adddate;
    }

    /**
     * 
     * @param adddate
     *     The adddate
     */
    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    /**
     * 
     * @return
     *     The status
     */
    public int getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The WardList
     */
    public List<itg8.com.wmcapp.prabhag.model.WardList> getWardList() {
        return WardList;
    }

    /**
     * 
     * @param WardList
     *     The WardList
     */
    public void setWardList(List<itg8.com.wmcapp.prabhag.model.WardList> WardList) {
        this.WardList = WardList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(PrabhagName);
        dest.writeValue(Address);
        dest.writeValue(Description);
        dest.writeValue(adddate);
        dest.writeValue(status);
        dest.writeList(WardList);
    }

    public int describeContents() {
        return  0;
    }

    public boolean isPragbhagSelected() {
        return false;
    }
}

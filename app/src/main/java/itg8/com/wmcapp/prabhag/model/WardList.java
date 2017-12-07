
package itg8.com.wmcapp.prabhag.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class WardList implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("prabhag_fkid")
    @Expose
    private int prabhagFkid;
    @SerializedName("ward_Name")
    @Expose
    private String wardName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("adddate")
    @Expose
    private String adddate;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("MemberList")
    @Expose
    private List<itg8.com.wmcapp.prabhag.model.MemberList> MemberList = new ArrayList<itg8.com.wmcapp.prabhag.model.MemberList>();
    public final static Parcelable.Creator<WardList> CREATOR = new Creator<WardList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WardList createFromParcel(Parcel in) {
            WardList instance = new WardList();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.prabhagFkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.wardName = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.address = ((String) in.readValue((String.class.getClassLoader())));
            instance.adddate = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.MemberList, (itg8.com.wmcapp.prabhag.model.MemberList.class.getClassLoader()));
            return instance;
        }

        public WardList[] newArray(int size) {
            return (new WardList[size]);
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
     *     The prabhagFkid
     */
    public int getPrabhagFkid() {
        return prabhagFkid;
    }

    /**
     * 
     * @param prabhagFkid
     *     The prabhag_fkid
     */
    public void setPrabhagFkid(int prabhagFkid) {
        this.prabhagFkid = prabhagFkid;
    }

    /**
     * 
     * @return
     *     The wardName
     */
    public String getWardName() {
        return wardName;
    }

    /**
     * 
     * @param wardName
     *     The ward_Name
     */
    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
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
     *     The MemberList
     */
    public List<itg8.com.wmcapp.prabhag.model.MemberList> getMemberList() {
        return MemberList;
    }

    /**
     * 
     * @param MemberList
     *     The MemberList
     */
    public void setMemberList(List<itg8.com.wmcapp.prabhag.model.MemberList> MemberList) {
        this.MemberList = MemberList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(prabhagFkid);
        dest.writeValue(wardName);
        dest.writeValue(description);
        dest.writeValue(address);
        dest.writeValue(adddate);
        dest.writeValue(status);
        dest.writeList(MemberList);
    }

    public int describeContents() {
        return  0;
    }

}

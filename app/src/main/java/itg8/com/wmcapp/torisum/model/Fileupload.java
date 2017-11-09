
package itg8.com.wmcapp.torisum.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Fileupload implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("Master_fkid")
    @Expose
    private int MasterFkid;
    @SerializedName("SubMaster_fkid")
    @Expose
    private int SubMasterFkid;
    @SerializedName("filepath")
    @Expose
    private String filepath;
    @SerializedName("Lastmodifieddatetime")
    @Expose
    private String Lastmodifieddatetime;
    @SerializedName("mid")
    @Expose
    private Object mid;
    @SerializedName("mdate")
    @Expose
    private Object mdate;
    public final static Parcelable.Creator<Fileupload> CREATOR = new Creator<Fileupload>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Fileupload createFromParcel(Parcel in) {
            Fileupload instance = new Fileupload();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.MasterFkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.SubMasterFkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.filepath = ((String) in.readValue((String.class.getClassLoader())));
            instance.Lastmodifieddatetime = ((String) in.readValue((String.class.getClassLoader())));
            instance.mid = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mdate = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public Fileupload[] newArray(int size) {
            return (new Fileupload[size]);
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
     *     The MasterFkid
     */
    public int getMasterFkid() {
        return MasterFkid;
    }

    /**
     * 
     * @param MasterFkid
     *     The Master_fkid
     */
    public void setMasterFkid(int MasterFkid) {
        this.MasterFkid = MasterFkid;
    }

    /**
     * 
     * @return
     *     The SubMasterFkid
     */
    public int getSubMasterFkid() {
        return SubMasterFkid;
    }

    /**
     * 
     * @param SubMasterFkid
     *     The SubMaster_fkid
     */
    public void setSubMasterFkid(int SubMasterFkid) {
        this.SubMasterFkid = SubMasterFkid;
    }

    /**
     * 
     * @return
     *     The filepath
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * 
     * @param filepath
     *     The filepath
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /**
     * 
     * @return
     *     The Lastmodifieddatetime
     */
    public String getLastmodifieddatetime() {
        return Lastmodifieddatetime;
    }

    /**
     * 
     * @param Lastmodifieddatetime
     *     The Lastmodifieddatetime
     */
    public void setLastmodifieddatetime(String Lastmodifieddatetime) {
        this.Lastmodifieddatetime = Lastmodifieddatetime;
    }

    /**
     * 
     * @return
     *     The mid
     */
    public Object getMid() {
        return mid;
    }

    /**
     * 
     * @param mid
     *     The mid
     */
    public void setMid(Object mid) {
        this.mid = mid;
    }

    /**
     * 
     * @return
     *     The mdate
     */
    public Object getMdate() {
        return mdate;
    }

    /**
     * 
     * @param mdate
     *     The mdate
     */
    public void setMdate(Object mdate) {
        this.mdate = mdate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(MasterFkid);
        dest.writeValue(SubMasterFkid);
        dest.writeValue(filepath);
        dest.writeValue(Lastmodifieddatetime);
        dest.writeValue(mid);
        dest.writeValue(mdate);
    }

    public int describeContents() {
        return  0;
    }

}

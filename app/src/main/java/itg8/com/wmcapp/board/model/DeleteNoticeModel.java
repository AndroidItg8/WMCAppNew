
package itg8.com.wmcapp.board.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DeleteNoticeModel implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("Notice_fkid")
    @Expose
    private int NoticeFkid;
    @SerializedName("User_fkid")
    @Expose
    private String UserFkid;
    @SerializedName("LastDatetime")
    @Expose
    private String LastDatetime;
    public final static Parcelable.Creator<DeleteNoticeModel> CREATOR = new Creator<DeleteNoticeModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DeleteNoticeModel createFromParcel(Parcel in) {
            DeleteNoticeModel instance = new DeleteNoticeModel();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.NoticeFkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.UserFkid = ((String) in.readValue((String.class.getClassLoader())));
            instance.LastDatetime = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public DeleteNoticeModel[] newArray(int size) {
            return (new DeleteNoticeModel[size]);
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
     *     The NoticeFkid
     */
    public int getNoticeFkid() {
        return NoticeFkid;
    }

    /**
     * 
     * @param NoticeFkid
     *     The Notice_fkid
     */
    public void setNoticeFkid(int NoticeFkid) {
        this.NoticeFkid = NoticeFkid;
    }

    /**
     * 
     * @return
     *     The UserFkid
     */
    public String getUserFkid() {
        return UserFkid;
    }

    /**
     * 
     * @param UserFkid
     *     The User_fkid
     */
    public void setUserFkid(String UserFkid) {
        this.UserFkid = UserFkid;
    }

    /**
     * 
     * @return
     *     The LastDatetime
     */
    public String getLastDatetime() {
        return LastDatetime;
    }

    /**
     * 
     * @param LastDatetime
     *     The LastDatetime
     */
    public void setLastDatetime(String LastDatetime) {
        this.LastDatetime = LastDatetime;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(NoticeFkid);
        dest.writeValue(UserFkid);
        dest.writeValue(LastDatetime);
    }

    public int describeContents() {
        return  0;
    }

}

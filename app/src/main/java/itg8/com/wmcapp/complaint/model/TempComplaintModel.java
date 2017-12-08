package itg8.com.wmcapp.complaint.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * Created by Android itg 8 on 11/13/2017.
 */
@DatabaseTable(tableName= TempComplaintModel.TABLE_COMPLAINT)

public class TempComplaintModel implements Parcelable {

    public static final String TABLE_COMPLAINT ="TableComplaint";
    public static final String FIELD_LATITIUDE ="latitude";
    public static final String FIELD_LONGITUDE ="longitude";
    public static final String FIELD_COMAPLAINT_NAME ="complaint_name";
    public static final String FIELD_CITY_ID ="city_id";
    public static final String FIELD_ADDRESS ="address";
    public static final String FIELD_SHOW_IDENTITY ="show_identity";
    public static final String FIELD_FILE_PATH ="file_path";
    public static final String FIELD_DESCRIPTION ="description";
    public static final String FIELD_CATEGORY_FKID ="category_id";
    public static final String FIELD_TBL_ID ="tbl_id";
    public static final String FIELD_LAST_MODIFIED_DATE ="date";

    @DatabaseField(generatedId = true, columnName = FIELD_TBL_ID )
    @Expose
    private long tblId;
    @DatabaseField(columnName = FIELD_LATITIUDE )
    @Expose
    private Double latitude;
    @DatabaseField(columnName = FIELD_LONGITUDE)
    @Expose
    private Double longitude;
    @DatabaseField(columnName = FIELD_COMAPLAINT_NAME)
    @Expose
    private String complaintName;
    @DatabaseField(columnName = FIELD_CITY_ID)
    @Expose
    private int cityId;

    public boolean isProgress() {
        return progress;
    }

    private boolean progress;

    public boolean isSync() {
        return sync;
    }

    private boolean sync;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private String cityName;

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @DatabaseField(columnName = FIELD_LAST_MODIFIED_DATE)
    @Expose
     private String lastModifiedDate;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @DatabaseField(columnName = FIELD_CATEGORY_FKID)
    @Expose
    private String categoryId;
    public String getAdd() {
        return add;
    }

    public void setAdd(String cityAdd) {
        this.add = cityAdd;
    }

    @DatabaseField(columnName = FIELD_ADDRESS)
    @Expose
    private String add;
    @DatabaseField(columnName = FIELD_SHOW_IDENTITY)
    @Expose
    private String showIdentity;

    public String getShowIdentity() {
        return showIdentity;
    }

    @DatabaseField(columnName = FIELD_FILE_PATH)
    @Expose
    private String filePath;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DatabaseField(columnName = FIELD_DESCRIPTION)
    @Expose
    private String description;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }





    public long getTblId() {
        return tblId;
    }

    public void setTblId(long tblId) {
        this.tblId = tblId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getComplaintName() {
        return complaintName;
    }

    public void setComplaintName(String complaintName) {
        this.complaintName = complaintName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setShowIdentity(String showIdentity) {
        this.showIdentity = showIdentity;
    }

    public TempComplaintModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.tblId);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.complaintName);
        dest.writeInt(this.cityId);
        dest.writeString(this.showIdentity);
        dest.writeString(this.filePath);
        dest.writeString(this.add);
        dest.writeString(this.description);
        dest.writeString(this.categoryId);
        dest.writeString(this.lastModifiedDate);
        dest.writeString(this.cityName);
    }

    protected TempComplaintModel(Parcel in) {
        this.tblId = in.readLong();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.complaintName = in.readString();
        this.cityId = in.readInt();
        this.showIdentity = in.readString();
        this.filePath = in.readString();
        this.add = in.readString();
        this.description = in.readString();
        this.categoryId = in.readString();
        this.lastModifiedDate  = in.readString();
        this.cityName  = in.readString();
    }

    public static final Parcelable.Creator<TempComplaintModel> CREATOR = new Parcelable.Creator<TempComplaintModel>() {
        @Override
        public TempComplaintModel createFromParcel(Parcel source) {
            return new TempComplaintModel(source);
        }

        @Override
        public TempComplaintModel[] newArray(int size) {
            return new TempComplaintModel[size];
        }
    };

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }
}

package itg8.com.wmcapp.board.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Android itg 8 on 11/28/2017.
 */

@DatabaseTable(tableName = TempNoticeBoardModel.TABLE_NAME)

public class TempNoticeBoardModel implements Parcelable {
     public   static final  String TABLE_NAME="TABLE_NOTICE";
     public   static final  String FIELD_ID="FIELD_ID";
     public   static final  String FIELD_NOTICE_ID="FIELD_NOTICE_ID";
     public   static final  String FIELD_TYPE="FIELD_TYPE";

    @DatabaseField(columnName = FIELD_ID , generatedId = true)
    @Expose
    private long tblId;

    @DatabaseField(columnName = FIELD_NOTICE_ID)
    @Expose
     private int noticeId;

    @DatabaseField(columnName = FIELD_TYPE)
    @Expose
     private String type;

    public long getTblId() {
        return tblId;
    }

    public void setTblId(long tblId) {
        this.tblId = tblId;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.tblId);
        dest.writeInt(this.noticeId);
        dest.writeString(this.type);
    }

    public TempNoticeBoardModel() {
    }

    protected TempNoticeBoardModel(Parcel in) {
        this.tblId = in.readLong();
        this.noticeId = in.readInt();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<TempNoticeBoardModel> CREATOR = new Parcelable.Creator<TempNoticeBoardModel>() {
        @Override
        public TempNoticeBoardModel createFromParcel(Parcel source) {
            return new TempNoticeBoardModel(source);
        }

        @Override
        public TempNoticeBoardModel[] newArray(int size) {
            return new TempNoticeBoardModel[size];
        }
    };
}

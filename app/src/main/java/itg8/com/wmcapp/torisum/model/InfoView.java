package itg8.com.wmcapp.torisum.model;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

import itg8.com.wmcapp.R;

/**
 * Created by Android itg 8 on 11/18/2017.
 */

@Layout(R.layout.item_category_tourism)
public class InfoView  {

    @ParentPosition
    private int mParentPosition;

    @ChildPosition
    private int mChildPosition;

    @View(R.id.titleTxt)
    private TextView titleTxt;

    @View(R.id.captionTxt)
    private TextView captionTxt;

    @View(R.id.timeTxt)
    private TextView timeTxt;

    @View(R.id.imageView)
    private ImageView imageView;

    private SubCatList mInfo;
    private Context mContext;

    public InfoView(Context context, SubCatList info) {
//         super(context);
        mContext = context;
        mInfo = info;
    }

    @Resolve
    public void onResolved() {
        titleTxt.setText(mInfo.getSubCategoryName());
        captionTxt.setText(mInfo.getSubCategoryDescription());
        timeTxt.setText(mInfo.getLastModifiedDate());
//        Glide.with(mContext).load(mInfo.getImageUrl()).into(imageView);
    }
}
package itg8.com.wmcapp.torisum.model;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

import java.util.ArrayList;
import java.util.List;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.widget.CustomFontTextView;

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
    private CustomFontTextView titleTxt;
    @View(R.id.checkbox)
    private CheckBox checkBox;




    private SubCatList mInfo;
    private Context mContext;

    public InfoView(Context context, SubCatList info) {
        mContext = context;
        mInfo = info;
    }

    @Resolve
    public void onResolved() {
        titleTxt.setText(CommonMethod.checkEmpty(mInfo.getSubCategoryName()));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                compoundButton.setChecked(true);


            }
        });


    }
}
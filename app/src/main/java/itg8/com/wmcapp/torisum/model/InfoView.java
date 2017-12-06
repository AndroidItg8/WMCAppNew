package itg8.com.wmcapp.torisum.model;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 11/18/2017.
 */

@Layout(R.layout.item_category_tourism)
public class InfoView {

    @ParentPosition
    private int mParentPosition;

    @ChildPosition
    private int mChildPosition;

    @View(R.id.titleTxt)
    private CustomFontTextView titleTxt;
    @View(R.id.checkbox)
    private CheckBox checkBox;
    private SubCatList mInfo;
    private ItemCheckListener listener;
    private Context mContext;


    public InfoView(Context context, SubCatList info, ItemCheckListener listener) {
        mContext = context;
        mInfo = info;
        this.listener = listener;
    }

    @Resolve
    public void onResolved() {
        titleTxt.setText(CommonMethod.checkEmpty(mInfo.getSubCategoryName()));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    listener.onItemCheck(mInfo);
                } else {
                    listener.onItemUnCheck(mInfo);

                }


            }
        });
        if (mInfo.isChecked()) {
            checkBox.setChecked(true);
            listener.onItemCheck(mInfo);
        }

    }

    public void clearItem() {
        mInfo.setChecked(false);
        checkBox.setChecked(false);
    }

    public interface ItemCheckListener {
        void onItemCheck(SubCatList info);

        void onItemUnCheck(SubCatList info);

    }

}
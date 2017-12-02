package itg8.com.wmcapp.setting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.setting.model.SecurityModel;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 12/2/2017.
 */

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {

    private Context mContext;
    private List<SecurityModel> securityList;

    public SettingAdapter(Context mContext, List<SecurityModel> securityList) {

        this.mContext = mContext;
        this.securityList = securityList;
    }

    @Override
    public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SettingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_secury_setting, parent, false));
    }

    @Override
    public void onBindViewHolder(SettingViewHolder holder, int position) {
        holder.lblHeading.setText(securityList.get(position).getHeading());
        holder.lblContent.setText(securityList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        Logs.d("Size:" + securityList.size());
        return securityList.size();
    }

    public class SettingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lbl_heading)
        CustomFontTextView lblHeading;
        @BindView(R.id.view)
        View view;
        @BindView(R.id.lbl_content)
        CustomFontTextView lblContent;

        public SettingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

package itg8.com.wmcapp.emergency;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.BaseViewHolder;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.OnRecyclerviewClickListener;
import itg8.com.wmcapp.emergency.model.ContactModel;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 11/20/2017.
 */

public class EmergencyCallListAdapter extends RecyclerView.Adapter<EmergencyCallListAdapter.ViewHolder> {

    private Context mContext;
    private List<ContactModel> list;
    OnRecyclerviewClickListener<ContactModel> listener;

    public EmergencyCallListAdapter(Context mContext, List<ContactModel> list, OnRecyclerviewClickListener<ContactModel> listener) {
        this.mContext = mContext;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_emergency_contact, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
         holder.contactModel = list.get(position);
//         holder.lblName.setText(CommonMethod.checkEmpty(list.get(position).getName));


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends BaseViewHolder<ContactModel> {
        @BindView(R.id.lbl_name)
        CustomFontTextView lblName;
        @BindView(R.id.lbl_address)
        CustomFontTextView lblAddress;
        @BindView(R.id.lbl_contact)
        CustomFontTextView lblContact;
        @BindView(R.id.img)
        ImageView img;
        ContactModel contactModel;
        public ViewHolder(View itemView) {
            super(itemView, listener);
            ButterKnife.bind(this, itemView);
        }
    }
}

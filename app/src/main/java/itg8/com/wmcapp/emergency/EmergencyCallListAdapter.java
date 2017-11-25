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
import itg8.com.wmcapp.emergency.model.Contact;
import itg8.com.wmcapp.emergency.model.ContactModel;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 11/20/2017.
 */

public class EmergencyCallListAdapter extends RecyclerView.Adapter<EmergencyCallListAdapter.ViewHolder> {

    private final List<Contact> list;
    private Context mContext;
    CallItemClickedListner listener;

    public EmergencyCallListAdapter(Context mContext, List<Contact> list, CallItemClickedListner listener) {
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
         holder.lblName.setText(CommonMethod.checkEmpty(list.get(position).getDeptName()));
         holder.lblAddress.setText(CommonMethod.checkEmpty(list.get(position).getAddress()));
         holder.lblContact.setText(CommonMethod.checkEmpty(list.get(position).getMobileNo()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lbl_name)
        CustomFontTextView lblName;
        @BindView(R.id.lbl_address)
        CustomFontTextView lblAddress;
        @BindView(R.id.lbl_contact)
        CustomFontTextView lblContact;
        @BindView(R.id.img)
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
             img.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     listener.onCallItem(getAdapterPosition(),list.get(getAdapterPosition()));
                 }
             });
        }
    }

    public interface CallItemClickedListner{
        void onCallItem(int position,Contact contact);
    }
}

package itg8.com.wmcapp.emergency;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.BaseViewHolder;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.OnRecyclerviewClickListener;
import itg8.com.wmcapp.emergency.model.EmergencyModel;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 11/20/2017.
 */

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.ViewHolder> {

    private Context mContext;
    private List<EmergencyModel> list;
    private OnRecyclerviewClickListener<EmergencyModel> listener;


    public EmergencyAdapter(Context mContext, List<EmergencyModel> list, OnRecyclerviewClickListener<EmergencyModel> listener) {
        this.mContext = mContext;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_emergency, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.lblName.setText(CommonMethod.checkEmpty(list.get(position).getDeptName()));



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends BaseViewHolder<EmergencyModel> {
        @BindView(R.id.lbl_name)
        CustomFontTextView lblName;
        @BindView(R.id.lbl_symbol)
        CustomFontTextView lblSymbol;
        public ViewHolder(View itemView) {
            super(itemView, listener);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
        }
    }
}

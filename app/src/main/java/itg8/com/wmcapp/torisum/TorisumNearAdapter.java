package itg8.com.wmcapp.torisum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.BaseViewHolder;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.OnRecyclerviewClickListener;
import itg8.com.wmcapp.torisum.model.TorisumModel;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 12/6/2017.
 */

public class TorisumNearAdapter extends RecyclerView.Adapter<TorisumNearAdapter.TorisumViewHolder> {

    private Context mContext;
    private List<TorisumModel> list;
    private OnRecyclerviewClickListener<TorisumModel> listener;

    public TorisumNearAdapter(Context mContext, List<TorisumModel> list, OnRecyclerviewClickListener<TorisumModel> listener) {
        this.mContext = mContext;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public TorisumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_torisum_near, parent, false);
        TorisumViewHolder holder = new TorisumViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TorisumViewHolder holder, int position) {
        holder.lblPlaceName.setText(CommonMethod.checkEmpty(list.get(position).getName()));
        holder.lblPlaceAddress.setText(CommonMethod.checkEmpty(list.get(position).getAddress()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class TorisumViewHolder  extends BaseViewHolder<TorisumModel> {

        @BindView(R.id.lbl_place_name)
        CustomFontTextView lblPlaceName;
        @BindView(R.id.lbl_place_address)
        CustomFontTextView lblPlaceAddress;


        public TorisumViewHolder(View itemView) {
            super(itemView, listener);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(), list.get(getAdapterPosition()));

                }
            });
        }
    }
}
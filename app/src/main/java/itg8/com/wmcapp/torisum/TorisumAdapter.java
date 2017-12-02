package itg8.com.wmcapp.torisum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

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
 * Created by Android itg 8 on 11/2/2017.
 */

public class TorisumAdapter extends RecyclerView.Adapter<TorisumAdapter.TorisumViewHolder> {

    private Context mContext;
    private List<TorisumModel> list;
    private OnRecyclerviewClickListener<TorisumModel> listener;

    public TorisumAdapter(Context mContext, List<TorisumModel> list, OnRecyclerviewClickListener listener) {
        this.mContext = mContext;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public TorisumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_torisum, parent, false);
        TorisumViewHolder holder = new TorisumViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TorisumViewHolder holder, int position) {
        holder.lblPlaceName.setText(list.get(position).getName());
        holder.lblPlaceDescription.setText(list.get(position).getDescription());
//        holder.lblTime.setText(CommonMethod.getFormattedDateTime(list.get(position).getAddedDate()));
//        holder.ratingBar.setNumStars(list.get(position).);
//        holder.lblReviews.setText(list.get(position).);

        Picasso.with(mContext)
                .load(CommonMethod.BASE_URL + list.get(position).getFileupload().get(0).getFilepath())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.bpkuti)
                .into(((TorisumViewHolder) holder).img, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(mContext)
                                .load(CommonMethod.BASE_URL + list.get(holder.getAdapterPosition()).getFileupload().get(0).getFilepath())
                                .into(((TorisumViewHolder) holder).img);
                    }
                });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface TorisumItemClickecListener {
        void onItemClicked(int position, TorisumModel model);
    }

    public class TorisumViewHolder extends BaseViewHolder<TorisumModel> {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.lbl_place_name)
        CustomFontTextView lblPlaceName;
        @BindView(R.id.lbl_place_description)
        CustomFontTextView lblPlaceDescription;
        @BindView(R.id.lbl_time)
        CustomFontTextView lblTime;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.lbl_reviews)
        CustomFontTextView lblReviews;

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

package itg8.com.wmcapp.complaint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.ProgressHolder;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.widget.CircularImageView;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 11/1/2017.
 */

public class ComplaintAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LOADING_VIEW = 1;
    private static final int NORMAL_VIEW = 2;

    private Context mContext;
    private List<ComplaintModel> models;

    public ComplaintAdapter(Context mContext) {
        this.mContext = mContext;
        models=new ArrayList<>();
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == NORMAL_VIEW) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_complaint, parent, false);
            holder = new ComplaintViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_progress, parent, false);
            holder = new ProgressHolder(view);
        }

        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return models.get(position) == null ? LOADING_VIEW : NORMAL_VIEW;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ComplaintViewHolder) {

            if (!TextUtils.isEmpty(models.get(position).getImagePath())) {
                ((ComplaintViewHolder) holder).imgGarbage.setVisibility(View.VISIBLE);

                Picasso.with(mContext)
                        .load(CommonMethod.BASE_URL + models.get(position).getImagePath())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(((ComplaintViewHolder) holder).imgGarbage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                // Try again online if cache failed
                                Picasso.with(mContext)
                                        .load(CommonMethod.BASE_URL + models.get(holder.getAdapterPosition()).getImagePath())
                                        .into(((ComplaintViewHolder) holder).imgGarbage);
                            }
                        });
            }else {
                ((ComplaintViewHolder) holder).imgGarbage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void addItems(List<ComplaintModel> o) {
        models.addAll(o);
        notifyDataSetChanged();

    }

    public void addFooter() {
        models.add(null);
        notifyItemInserted(models.size()-1);
    }

    public void removeFooter() {
        final int itemRemoved=models.size() - 1;
        models.remove(itemRemoved);
        notifyItemRemoved(itemRemoved);
        notifyItemRangeChanged(itemRemoved, models.size());
    }


    public class ComplaintViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        CircularImageView img;
        @BindView(R.id.lbl_name_value)
        CustomFontTextView lblNameValue;
        @BindView(R.id.lbl_days_value)
        CustomFontTextView lblDaysValue;
        @BindView(R.id.img_garbage)
        ImageView imgGarbage;

        public ComplaintViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

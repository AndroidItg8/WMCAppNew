package itg8.com.wmcapp.complaint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.complaint.model.LikeModel;
import itg8.com.wmcapp.widget.CircularImageView;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 11/25/2017.
 */

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder> {
    private final Context mContext;
    private final List<LikeModel> likeList;


    public LikeAdapter(Context mContext, List<LikeModel> likeList) {

        this.mContext = mContext;
        this.likeList = likeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_like, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.lblNameValue.setText(CommonMethod.checkEmptyProfile(likeList.get(position).getCustomername()));
        CommonMethod.setUserPicaso(mContext, likeList.get(position).getCustomername(),holder.imgGarbage);

    }

    @Override
    public int getItemCount() {
        return likeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        CircularImageView imgGarbage;
        @BindView(R.id.lbl_name_value)
        CustomFontTextView lblNameValue;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

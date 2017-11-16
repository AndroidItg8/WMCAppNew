package itg8.com.wmcapp.complaint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.widget.CircularImageView;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 11/16/2017.
 */

class ComplaintProfileOnlineAdapter extends RecyclerView.Adapter<ComplaintProfileOnlineAdapter.ComplaintViewHolder> {

    private final Context mContext;
    private final List<ComplaintModel> complaintMergeList;
    private OnRecyclerviewClickListener<ComplaintModel> listener;



    public ComplaintProfileOnlineAdapter(Context mContext, List<ComplaintModel> complaintMergeList,OnRecyclerviewClickListener<ComplaintModel>  listener ) {

        this.mContext = mContext;
        this.complaintMergeList = complaintMergeList;

        this.listener = listener;
    }


    @Override
    public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_profile_complaint, parent, false);
        ComplaintProfileOnlineAdapter.ComplaintViewHolder holder = new ComplaintProfileOnlineAdapter.ComplaintViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final ComplaintViewHolder holder, int position) {
        holder.complaintModel = complaintMergeList.get(position);
        if (holder.complaintModel.getImagePath() != null) {
            holder.complaintUrl = CommonMethod.BASE_URL + holder.complaintModel.getImagePath();
            holder.imgGarbage.setVisibility(View.VISIBLE);

            Picasso.with(holder.imgGarbage.getContext())
                    .load(holder.complaintUrl)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .error(R.drawable.bpkuti)
                    .into(holder.imgGarbage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            // Try again online if cache failed
                            Picasso.with(holder.imgGarbage.getContext())
                                    .load(holder.complaintUrl)
                                            .into((holder.imgGarbage));


//                            Picasso.with(mContext)
//                                    .load(CommonMethod.BASE_URL + list.get(holder.getAdapterPosition()).getFileupload().get(0).getFilepath())
//                                    .into(((TorisumAdapter.TorisumViewHolder) holder).img);
                        }
                    });
        } else {
            holder.imgGarbage.setVisibility(View.GONE);
        }

        holder.lblNameValue.setText(holder.complaintModel.getComplaintName());
        holder.lblProblemValue.setText(holder.complaintModel.getComplaintDescription());
        holder.lblCityName.setText(holder.complaintModel.getCityName());
        holder.lblCityName.setText(holder.complaintModel.getCityName());


    }


    @Override
    public int getItemCount() {
        return complaintMergeList.size();
    }

    public class ComplaintViewHolder extends BaseViewHolder<ComplaintModel> {

        @BindView(R.id.img)
        CircularImageView img;
        @BindView(R.id.lbl_name_value)
        CustomFontTextView lblNameValue;
        @BindView(R.id.lbl_days_value)
        CustomFontTextView lblDaysValue;
        @BindView(R.id.rl_top)
        RelativeLayout rlTop;
        @BindView(R.id.img_garbage)
        ImageView imgGarbage;
        @BindView(R.id.lbl_cityName)
        CustomFontTextView lblCityName;
        @BindView(R.id.rl_center)
        RelativeLayout rlCenter;
        @BindView(R.id.lbl_problem_value)
        CustomFontTextView lblProblemValue;
        @BindView(R.id.lbl_address_value)
        CustomFontTextView lblAddressValue;
        @BindView(R.id.lbl_vote)
        CustomFontTextView lblVote;
        @BindView(R.id.lbl_vote_value)
        CustomFontTextView lblVoteValue;
        @BindView(R.id.lbl_share)
        CustomFontTextView lblShare;
        @BindView(R.id.rl_bottom)
        RelativeLayout rlBottom;
        ComplaintModel complaintModel;
        String complaintUrl;

        public ComplaintViewHolder(View itemView) {
            super(itemView, listener);
            ButterKnife.bind(this,itemView);
             lblShare.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                listener.onClick(getAdapterPosition(),complaintMergeList.get(getAdapterPosition()) );
                 }
             });
        }
    }
}

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
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 11/16/2017.
 */

public class ComplaintProfilOfflineeAdapter extends RecyclerView.Adapter<ComplaintProfilOfflineeAdapter.UnSendComplaintViewHolder> {

    private final Context mContext;
    private final List<TempComplaintModel> complaintMergeList;
    UnSendItemClickedListner listner;

    public ComplaintProfilOfflineeAdapter(Context mContext, List<TempComplaintModel> complaintMergeList, UnSendItemClickedListner listner) {
        this.mContext = mContext;
        this.complaintMergeList = complaintMergeList;
        this.listner = listner;
    }

    @Override
    public UnSendComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_unsend_complaint, parent, false);
        ComplaintProfilOfflineeAdapter.UnSendComplaintViewHolder  holder = new ComplaintProfilOfflineeAdapter.UnSendComplaintViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UnSendComplaintViewHolder holder, int position) {

        holder.tempComplaintModel = complaintMergeList.get(position);
            if (holder.tempComplaintModel.getFilePath() != null) {
                holder.imgGarbage.setVisibility(View.VISIBLE);

                holder.tempCompliantUrl =  holder.tempComplaintModel.getFilePath();

                Picasso.with(holder.imgGarbage.getContext())
                        .load(holder.tempCompliantUrl)
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
                                        .load(holder.tempCompliantUrl)
                                        .into(holder.imgGarbage);
                            }
                        });

            } else {
                holder.imgGarbage.setVisibility(View.GONE);
            }

            holder.lblProblemValue.setText((holder.tempComplaintModel.getComplaintName() + "\n " + (holder.tempComplaintModel.getDescription())));
            holder.lblProblemValue.setText((holder.tempComplaintModel.getCityName()));
            holder.lblAddressValue.setText((holder.tempComplaintModel.getAdd()));

        }

    @Override
    public int getItemCount() {
        return complaintMergeList.size();
    }

    public class UnSendComplaintViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.rl_bottom)
        RelativeLayout rlBottom;
        @BindView(R.id.view)
        View view;
        @BindView(R.id.lbl_sync)
        CustomFontTextView lblSync;
        @BindView(R.id.lbl_share)
        CustomFontTextView lblShare;
        @BindView(R.id.lbl_sms)
        CustomFontTextView lblSms;
        TempComplaintModel tempComplaintModel;
        String tempCompliantUrl;

        public UnSendComplaintViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            lblShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onShareItemClicked(getAdapterPosition(), tempComplaintModel, imgGarbage);


                }
            });

            lblSync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onSyncItemClicked(getAdapterPosition(), tempComplaintModel);
                }
            });
            lblSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onSMSItemClicked(getAdapterPosition(), tempComplaintModel);
                }
            });
        }
    }


    public interface UnSendItemClickedListner {
        void onSyncItemClicked(int position, TempComplaintModel model);

        void onShareItemClicked(int position, Object model, ImageView view );

        void onSMSItemClicked(int position, TempComplaintModel model);
    }

}

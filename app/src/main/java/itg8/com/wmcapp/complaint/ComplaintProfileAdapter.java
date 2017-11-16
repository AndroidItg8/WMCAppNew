package itg8.com.wmcapp.complaint;

import android.content.Context;
import android.media.Image;
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
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.widget.CircularImageView;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 11/14/2017.
 */

public class ComplaintProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int COMPLAINT_VIEW = 0;
    private static final int TEMP_COMPLAINT_VIEW = 1;
    private final List<Object> complaintMergeList;
    private UnSendItemClickedListner listner;


    public ComplaintProfileAdapter(Context mContext, List<Object> complaintMergeList, UnSendItemClickedListner listner) {

        this.complaintMergeList = complaintMergeList;
        this.listner = listner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == COMPLAINT_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_profile_complaint, parent, false);
            holder = new ComplaintViewHolder(view);

        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_unsend_complaint, parent, false);
            holder = new UnSendComplaintViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ComplaintViewHolder) {
            if (complaintMergeList.get(position) instanceof ComplaintModel) {
                ((ComplaintViewHolder) holder).complaintModel = (ComplaintModel) complaintMergeList.get(position);

                if (((ComplaintViewHolder) holder).complaintModel.getImagePath() != null) {
                    ((ComplaintViewHolder) holder).complaintUrl = CommonMethod.BASE_URL + ((ComplaintViewHolder) holder).complaintModel.getImagePath();
                    ((ComplaintViewHolder) holder).imgGarbage.setVisibility(View.VISIBLE);

                    Picasso.with(((ComplaintViewHolder) holder).imgGarbage.getContext())
                            .load(((ComplaintViewHolder) holder).complaintUrl)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .error(R.drawable.bpkuti)
                            .into(((ComplaintViewHolder) holder).imgGarbage, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    // Try again online if cache failed
                                    Picasso.with(((ComplaintViewHolder) holder).imgGarbage.getContext())
                                            .load(((ComplaintViewHolder) holder).complaintUrl)
                                            .into(((ComplaintViewHolder) holder).imgGarbage);
                                }
                            });
                }

            } else {
                ((ComplaintViewHolder) holder).imgGarbage.setVisibility(View.GONE);
            }

            ((ComplaintViewHolder) holder).lblNameValue.setText(((ComplaintViewHolder) holder).complaintModel.getComplaintName());
            ((ComplaintViewHolder) holder).lblProblemValue.setText(((ComplaintViewHolder) holder).complaintModel.getComplaintDescription());
            ((ComplaintViewHolder) holder).lblCityName.setText(((ComplaintViewHolder) holder).complaintModel.getCityName());
            ((ComplaintViewHolder) holder).lblCityName.setText(((ComplaintViewHolder) holder).complaintModel.getCityName());


        }





        else if (holder instanceof UnSendComplaintViewHolder) {
            final UnSendComplaintViewHolder holder1 = (UnSendComplaintViewHolder) holder;
            if (complaintMergeList.get(position) instanceof TempComplaintModel) {
                (holder1).tempComplaintModel = (TempComplaintModel) complaintMergeList.get(position);

                if (holder1.tempComplaintModel.getFilePath() != null) {
                    holder1.imgGarbage.setVisibility(View.VISIBLE);

                    holder1.tempCompliantUrl =  holder1.tempComplaintModel.getFilePath();

                    Picasso.with(holder1.imgGarbage.getContext())
                            .load(holder1.tempCompliantUrl)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .error(R.drawable.bpkuti)
                            .into(holder1.imgGarbage, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    // Try again online if cache failed
                                    Picasso.with(holder1.imgGarbage.getContext())
                                            .load(holder1.tempCompliantUrl)
                                            .into(holder1.imgGarbage);
                                }
                            });

                } else {
                    holder1.imgGarbage.setVisibility(View.GONE);
                }

                holder1.lblProblemValue.setText((holder1.tempComplaintModel.getComplaintName() + "\n " + (holder1.tempComplaintModel.getDescription())));
                holder1.lblProblemValue.setText((holder1.tempComplaintModel.getCityName()));
                holder1.lblAddressValue.setText((holder1.tempComplaintModel.getAdd()));

            }


        }


    }

    @Override
    public int getItemCount() {
        return complaintMergeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return complaintMergeList.get(position) instanceof ComplaintModel ? COMPLAINT_VIEW : TEMP_COMPLAINT_VIEW;
    }

    public interface UnSendItemClickedListner {
        void onSyncItemClicked(int position, TempComplaintModel model);

        void onShareItemClicked(int position, Object model, ImageView view );

        void onSMSItemClicked(int position, TempComplaintModel model);
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder {

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


        public ComplaintViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            lblShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onShareItemClicked(getAdapterPosition(), complaintModel, imgGarbage);
                }
            });



        }
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
}

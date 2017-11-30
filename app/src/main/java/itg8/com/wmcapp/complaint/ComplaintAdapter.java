package itg8.com.wmcapp.complaint;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
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
    private static final int SHOW_PROGRESS = 0;
    private static final int HIDE_PROGRESS = 1;
    private static final int VOTED = 1;
    private static final int VOTE_UP = 0;


    private final Context mContext;
    private final int from;


    private ComplaintListner listner;
    private List<ComplaintModel> models;
    private int likedSize;
    private String status;
    private int colorStatus;
    private Drawable drawables= null;

    public ComplaintAdapter(Context mContext, int fromComplaint, ComplaintListner listner) {
        this.mContext = mContext;
        this.from = fromComplaint;
        this.listner = listner;
        models = new ArrayList<>();
    }

    public void showProgress(int position) {
        models.get(position).setProgress(true);
        notifyItemChanged(position);
    }

    public void hideProgress(int position) {
        models.get(position).setProgress(false);
        notifyItemChanged(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == NORMAL_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_complaint, parent, false);
            holder = new ComplaintViewHolder(view);
        } else {
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
        if (holder instanceof ComplaintViewHolder) {
            Logs.d("CompliantAdapter:" + CommonMethod.BASE_URL + models.get(position).getImagePath());
            ((ComplaintViewHolder) holder).url = CommonMethod.BASE_URL + models.get(position).getImagePath();
            Picasso.with(((ComplaintViewHolder) holder).imgGarbage.getContext())
                    .load(((ComplaintViewHolder) holder).url)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(((ComplaintViewHolder) holder).imgGarbage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            // Try again online if cache failed
                            Picasso.with(((ComplaintViewHolder) holder).imgGarbage.getContext())
                                    .load(((ComplaintViewHolder) holder).url)
                                    .into(((ComplaintViewHolder) holder).imgGarbage, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            ((ComplaintViewHolder) holder).imgGarbage.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onError() {
                                            ((ComplaintViewHolder) holder).imgGarbage.setVisibility(View.GONE);
                                        }
                                    });
                        }
                    });


            ((ComplaintViewHolder) holder).lblCityName.setText(CommonMethod.checkEmpty(models.get(position).getCityName()));
            ((ComplaintViewHolder) holder).lblAddressValue.setText(CommonMethod.checkEmpty(models.get(position).getComplaintName()));
            ((ComplaintViewHolder) holder).lblProblemValue.setText(CommonMethod.checkEmpty(models.get(position).getComplaintDescription()));
            CommonMethod.setUserPicaso(mContext, models.get(position).getUserProfilepic(), ((ComplaintViewHolder) holder).img);
            ((ComplaintViewHolder) holder).lblNameValue.setText(CommonMethod.checkEmptyProfile(models.get(position).getUserFullename()));
            ((ComplaintViewHolder) holder).lblDaysValue.setText(String.valueOf(CommonMethod.calculateDays(models.get(position).getAddedDate())));


            if (models.get(position).getActive() == CommonMethod.PENDING) {
                status = mContext.getString(R.string.pending_status);
                colorStatus = ContextCompat.getColor(mContext, R.color.colorGoogle);
                drawables = ContextCompat.getDrawable(mContext, R.drawable.bg_pending_status);


            } else if (models.get(position).getActive() == CommonMethod.CLOSED) {
                status = mContext.getString(R.string.solved_status);
                colorStatus = ContextCompat.getColor(mContext, R.color.colorGreen);
                drawables = ContextCompat.getDrawable(mContext, R.drawable.bg_status);

            } else
//                    if (models.get(position).getActive() == CommonMethod.PROCESS)
            {
                status = mContext.getString(R.string.process_status);
                colorStatus = ContextCompat.getColor(mContext, R.color.colorFacebook);
                drawables = ContextCompat.getDrawable(mContext, R.drawable.bg_process_status);

            }

            ((ComplaintViewHolder) holder).complaintStatus.setText(status);
            ((ComplaintViewHolder) holder).complaintStatus.setTextColor(colorStatus);
            ((ComplaintViewHolder) holder).complaintStatus.setBackground(drawables);





            if (models.get(position).getLikeList() != null) {
                likedSize = models.get(position).getLikeList().size();
                ((ComplaintViewHolder) holder).lblVoteValue.setText(CommonMethod.checkEmpty(String.valueOf(models.get(position).getLikeList().size())));
                Logs.d("LikeSize:" + likedSize);

            } else {
                likedSize = 0;
            }


            if (models.get(position).isProgress()) {
                ((ComplaintViewHolder) holder).progressViewLike.setVisibility(View.VISIBLE);
                ((ComplaintViewHolder) holder).lblVoteUp.setVisibility(View.GONE);

            } else {
                ((ComplaintViewHolder) holder).progressViewLike.setVisibility(View.GONE);
                ((ComplaintViewHolder) holder).lblVoteUp.setVisibility(View.VISIBLE);

            }
            if (from == CommonMethod.FROM_COMPLAINT) {
                ((ComplaintViewHolder) holder).lblShareOnline.setVisibility(View.GONE);
                ((ComplaintViewHolder) holder).llVoteUp.setVisibility(View.VISIBLE);
                ((ComplaintViewHolder) holder).view.setVisibility(View.VISIBLE);
            } else {
                ((ComplaintViewHolder) holder).lblShareOnline.setVisibility(View.VISIBLE);
                ((ComplaintViewHolder) holder).llVoteUp.setVisibility(View.GONE);
                ((ComplaintViewHolder) holder).view.setVisibility(View.GONE);
                ((ComplaintViewHolder) holder).lblShareOnline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listner.onShareClicked(holder.getAdapterPosition(), models.get(holder.getAdapterPosition()));
                    }
                });

            }
            if (models.get(position).getLikestatus() == VOTE_UP) {
                ((ComplaintViewHolder) holder).lblVoteUp.setText("VOTE UP");
                ((ComplaintViewHolder) holder).lblVoteUp.setTextColor(((ComplaintViewHolder) holder).lblVoteUp.getContext().getResources().getColor(R.color.colorBlack));
                ((ComplaintViewHolder) holder).frame.setClickable(true);
//                ((ComplaintViewHolder) holder).frame.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        listner.onVoteUpClicked(holder.getAdapterPosition(), models.get(holder.getAdapterPosition()));
//                        Logs.d("onVoteUpClicked:");
//                    }
//                });
            } else {
                ((ComplaintViewHolder) holder).lblVoteUp.setText("VOTED");
                ((ComplaintViewHolder) holder).lblVoteUp.setTextColor(((ComplaintViewHolder) holder).lblVoteUp.getContext().getResources().getColor(R.color.colorPrimary));
                ((ComplaintViewHolder) holder).frame.setClickable(false);

            }
            ((ComplaintViewHolder) holder).lblVoteValue.setText(String.valueOf(likedSize));


//            ((ComplaintViewHolder) holder).lblShare.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listner.onShareClicked(holder.getAdapterPosition(), models.get(holder.getAdapterPosition()));
//                }
//            });
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
        notifyItemInserted(models.size() - 1);
    }

    public void removeFooter() {
        final int itemRemoved = models.size() - 1;
        models.remove(itemRemoved);
        notifyItemRemoved(itemRemoved);
        notifyItemRangeChanged(itemRemoved, models.size());
    }

    public interface ComplaintListner {
        void onComplaintItemClicked(int position, ComplaintModel model);

        void onVoteUpClicked(int position, ComplaintModel model);

        void onShareClicked(int position, ComplaintModel model);
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
        @BindView(R.id.rl_bottom)
        RelativeLayout rlBottom;
        @BindView(R.id.view)
        View view;
        @BindView(R.id.lbl_voteUp)
        CustomFontTextView lblVoteUp;
        @BindView(R.id.lbl_share)
        CustomFontTextView lblShare;
        @BindView(R.id.progressViewLike)
        CircularProgressView progressViewLike;
        @BindView(R.id.frame)
        FrameLayout frame;
        String url;
        @BindView(R.id.lbl_share_online)
        CustomFontTextView lblShareOnline;
        @BindView(R.id.ll_vote_up)
        LinearLayout llVoteUp;
        @BindView(R.id.complaint_status)
        CustomFontTextView complaintStatus;

        public ComplaintViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onComplaintItemClicked(getAdapterPosition(), models.get(getAdapterPosition()));
                }
            });
            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onVoteUpClicked(getAdapterPosition(), models.get(getAdapterPosition()));
                }
            });
            lblShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onShareClicked(getAdapterPosition(), models.get(getAdapterPosition()));

                }
            });


        }
    }
}

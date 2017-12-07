package itg8.com.wmcapp.board;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.board.model.NoticeBoardModel;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.ProgressHolder;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 11/3/2017.
 */

class NoticeBoardAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LOADING_VIEW = 1;
    private static final int NORMAL_VIEW = 2;



    private Context context;
    private NoticeBoardListner listner;
    List<NoticeBoardModel> models;
    private int itemRemoved;
    private boolean footerAdded = false;

    public NoticeBoardAdater(Context context, NoticeBoardListner listner) {
        this.context = context;
        this.listner = listner;
        models = new ArrayList<>();
    }

    public void removeItem(int position) {
    models.remove(position);
    notifyItemRemoved(position);
    notifyItemRangeChanged(position,models.size());
    }

    public interface NoticeBoardListner {
        void onNBItemClicked(int position, NoticeBoardModel model);

        void onNBItemDeleteClicked(int position, NoticeBoardModel model);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == NORMAL_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_notice_board, parent, false);
            holder = new NoticeBoardViewHolder(view);
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
//        if(holder instanceof NoticeBoardProgress)
        if (holder instanceof NoticeBoardViewHolder) {
            ((NoticeBoardViewHolder) holder).lblTitleOnly.setText(models.get(position).getNoticeName());
            ((NoticeBoardViewHolder) holder).lblDescription.setText(models.get(position).getNoticeDescription());
            ((NoticeBoardViewHolder) holder).lblDate.setText((CharSequence) CommonMethod.convertStringToDate(models.get(position).getAddedDate()));
            if (!TextUtils.isEmpty(models.get(position).getImagePath())) {
                ((NoticeBoardViewHolder) holder).imgGarbage.setVisibility(View.VISIBLE);

                Picasso.with(context)
                        .load(CommonMethod.BASE_URL + models.get(position).getImagePath())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(((NoticeBoardViewHolder) holder).imgGarbage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                // Try again online if cache failed
                                Picasso.with(context)
                                        .load(CommonMethod.BASE_URL + models.get(holder.getAdapterPosition()).getImagePath())
                                        .into(((NoticeBoardViewHolder) holder).imgGarbage, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                ((NoticeBoardViewHolder) holder).imgGarbage.setVisibility(View.VISIBLE);
                                            }

                                            @Override
                                            public void onError() {
                                                ((NoticeBoardViewHolder) holder).imgGarbage.setVisibility(View.GONE);
                                            }
                                        });
                            }
                        });
            } else {
                ((NoticeBoardViewHolder) holder).imgGarbage.setVisibility(View.GONE);
            }
        }
    }

    private String getItem(int adapterPosition) {
        return CommonMethod.BASE_URL + models.get(adapterPosition).getImagePath();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void addItems(List<NoticeBoardModel> o) {
        models.addAll(o);
        notifyDataSetChanged();
    }

    public void addFooter() {
        models.add(null);
    }

    public void removeFooter() {
        models.remove(models.size() - 1);
    }

    public int getModelSize() {
        return models.size();
    }


    public class NoticeBoardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_garbage)
        ImageView imgGarbage;
        //        @BindView(R.id.customFontTextView)
//        TextView customFontTextView;
        @BindView(R.id.frm_img_view)
        FrameLayout frmImgView;
        @BindView(R.id.lbl_title_only)
        TextView lblTitleOnly;
        //        @BindView(R.id.rl_top)
//        RelativeLayout rlTop;
        @BindView(R.id.lbl_description)
        CustomFontTextView lblDescription;
        @BindView(R.id.lbl_date)
        CustomFontTextView lblDate;

        @BindView(R.id.img_delete)
        ImageView imgDelete;

        @BindView(R.id.progressBar)
        ProgressBar progressBar;


        public NoticeBoardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onNBItemClicked(getAdapterPosition(), models.get(getAdapterPosition()));
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listner.onNBItemDeleteClicked(getAdapterPosition(), models.get(getAdapterPosition()));
                }
            });

        }
    }


}

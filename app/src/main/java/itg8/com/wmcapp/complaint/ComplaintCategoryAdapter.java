package itg8.com.wmcapp.complaint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.complaint.model.ComplaintCategoryModel;


/**
 * Created by Android itg 8 on 12/8/2017.
 */

public class ComplaintCategoryAdapter extends RecyclerView.Adapter<ComplaintCategoryAdapter.ViewHolder> {


    private final Context context;
    private List<ComplaintCategoryModel> body;
    public ComplaintCategoryAdapter.ItemClickedListener listener;


    public ComplaintCategoryAdapter(Context context, List<ComplaintCategoryModel> body,  ComplaintCategoryAdapter.ItemClickedListener listener) {
        this.context = context;
        this.body = body;
        this.listener = listener;
    }


    @Override
    public ComplaintCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_prabhagitem, parent, false);
        return new ComplaintCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComplaintCategoryAdapter.ViewHolder holder, int position) {
        holder.txtId.setVisibility(View.GONE);
        holder.content.setText(body.get(position).getCategoryName());



    }

    @Override
    public int getItemCount() {
      return   body.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_id)
        TextView txtId;
        @BindView(R.id.content)
        TextView content;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(getAdapterPosition(), body.get(getAdapterPosition()));
                }
            });



        }


    }
     public interface ItemClickedListener{
        void onItemClicked(int position,ComplaintCategoryModel model);
     }

}
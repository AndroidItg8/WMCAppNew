package itg8.com.wmcapp.prabhag;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.prabhag.model.ContactModel;

/**
 * Created by swapnilmeshram on 02/11/17.
 */

class ContactRvAdapter extends RecyclerView.Adapter<ContactRvAdapter.ViewHolder> {

    private List<ContactModel> contactList;

    interface OnContactClickListener {
        void onMessageClicked(ContactModel model);

        void onCallClicked(ContactModel model);
    }


    OnContactClickListener listener;

    public ContactRvAdapter(List<ContactModel> contactList,OnContactClickListener listener) {
        this.contactList = contactList;
        this.listener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_contact_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.mobile1)
        TextView mobile1;
        @BindView(R.id.btnCall)
        ImageView btnCall;
        @BindView(R.id.btnMsg)
        ImageView btnMsg;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            btnCall.setOnClickListener(this);
            btnMsg.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.btnCall){
                listener.onCallClicked(contactList.get(getAdapterPosition()));
            }else if(view.getId()==R.id.btnMsg){
                listener.onMessageClicked(contactList.get(getAdapterPosition()));
            }
        }
    }
}

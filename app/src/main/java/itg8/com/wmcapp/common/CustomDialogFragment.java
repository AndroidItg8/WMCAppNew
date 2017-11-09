package itg8.com.wmcapp.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.home.HomeActivity;


public class CustomDialogFragment extends DialogFragment {

    private static final String PARAM1 = "param1";
    public static final String TAG = CustomDialogFragment.class.getSimpleName();
    private String[] items;
    ListView lvList;

    DialogItemClickListener listener;

    public interface DialogItemClickListener{

        void onItemClick(int position);
    }

    public static CustomDialogFragment newInstance(String[] items) {

        Bundle args = new Bundle();
        args.putStringArray(PARAM1,items);
        CustomDialogFragment fragment = new CustomDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items=getArguments().getStringArray(PARAM1);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof DialogItemClickListener)
            listener= (DialogItemClickListener) context;
        else
            throw  new IllegalStateException("Need to implement DialogItemClickListener in activity");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_dialog_fragment,container,false);
        lvList=view.findViewById(R.id.lv_items);
        if(items!=null && items.length>=0)
            createListView();
        else
            throw new IllegalArgumentException("No list provided in customDialog");
        return view;
    }

    private void createListView() {
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,items);
        lvList.setAdapter(adapter);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                listener.onItemClick(position);
                dismiss();
            }
        });
    }
}

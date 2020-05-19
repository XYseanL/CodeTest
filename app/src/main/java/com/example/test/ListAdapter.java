package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Phone>{
    private int resourceId;
    Phone phone = new Phone();
    public ListAdapter(Context context, int textViewResourceId, List<Phone> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final  int i = position;
        Phone phone = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
//            viewHolder.phoneImage = (ImageView)view.findViewById(R.id.phone_image);
//            viewHolder.textViewName = (TextView)view.findViewById(R.id.list_name);
//            viewHolder.textViewPhone = (TextView)view.findViewById(R.id.list_phone);
//            viewHolder.buttonChanges = (Button)view.findViewById(R.id.button_change);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.phoneImage.setImageResource(R.drawable.ic_launcher_foreground);
        assert phone != null;
        viewHolder.textViewName.setText(phone.getFirstN());

        viewHolder.buttonChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemChangesListener.onChangesClick(i);
            }
        });

        return view;
    }
    static class ViewHolder{
        ImageView phoneImage;
        TextView textViewName;
        TextView textViewPhone;
        Button buttonChanges;
    }

//    public interface onItemCallListener {
//        void onCallClick(int i);
//    }
//    private onItemCallListener mOnItemCallListener;
//    public void setOnItemCallClickListener(onItemCallListener mOnItemCallListener) {
//        this.mOnItemCallListener = mOnItemCallListener;
//    }

    public interface onItemChangesListener {
        void onChangesClick(int i);
    }
    private onItemChangesListener mOnItemChangesListener;
    public void setOnItemChangesClickListener(onItemChangesListener mOnItemChangersListener) {
        this.mOnItemChangesListener = mOnItemChangersListener;
    }

//    public interface onItemMassgasListener {
//        void onMassgasClick(int i);
//    }
//    private onItemMassgasListener mOnItemMassgasListener;
//    public void setOnItemMassgasClickListener(onItemMassgasListener mOnItemMassgasListener) {
//        this.mOnItemMassgasListener = mOnItemMassgasListener;
//    }

}

package com.example.db_dialog;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String>{

    int groupid;
    ArrayList<String> records;
    Context context;

    public CustomAdapter(Context context, int vg, int id, ArrayList<String> records){
        super(context,vg, id, records);
        this.context=context;
        groupid=vg;
        this.records=records;

    }
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(groupid, parent, false);

        String[] row_items=records.get(position).split("__");
        TextView t4id= (TextView) itemView.findViewById(R.id.tempatid);
        t4id.setText("No : "+row_items[0]);
        TextView t4catatan= (TextView) itemView.findViewById(R.id.tempatcatatan);
        t4catatan.setText("Catatan : "+row_items[1]);
        TextView t4medicine= (TextView) itemView.findViewById(R.id.tempatnrp);
        t4medicine.setText("NRP : "+row_items[2]);
        TextView t4moods= (TextView) itemView.findViewById(R.id.tempatnama);
        t4moods.setText("NAMA : "+row_items[3]);
        Button bt_del_period=(Button)itemView.findViewById(R.id.bt_delete_period);
        bt_del_period.setTag(row_items[0]);
        Button bt_edit_period=(Button)itemView.findViewById(R.id.bt_edit_period);
        bt_edit_period.setTag(row_items[0]);
        return itemView;
    }

}
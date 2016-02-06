package com.example.fangyulo.crunchtime;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class ListAdapter extends ArrayAdapter {

//    String[] Reps = {"Push ups", "Sit ups"};
//    String[] Min = {"Jogging", "Jumping Jacks"};
    private List list = new ArrayList();
    private int hidden;

    static class ListHolder
    {
        ImageView ICON;
        TextView TYPE;
        TextView AMT;
        TextView UNIT;
    }

    public ListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void setHidden(int i) {
        hidden = i;
    }

    public int getHidden() {
        return hidden;
    }

    public void clear() {
        list.clear();
        super.clear();
    }

    public void add(Exercise obj) {
        list.add(obj);
        super.add(obj);
    }

    public void remove(Exercise obj) {
        list.remove(obj);
        super.remove(obj);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ListHolder holder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_layout, parent, false);
            holder = new ListHolder();
            holder.ICON = (ImageView) row.findViewById(R.id.list_icon);
            holder.TYPE = (TextView) row.findViewById(R.id.list_type);
            holder.AMT = (TextView) row.findViewById(R.id.list_amt);
            holder.UNIT = (TextView) row.findViewById(R.id.list_unit);
            row.setTag(holder);
        }
        else {
            holder = (ListHolder) row.getTag();
        }
        Exercise obj = (Exercise) getItem(position);
        holder.ICON.setImageResource(obj.get_img());
        holder.TYPE.setText(obj.get_type());
        holder.AMT.setText(obj.get_amt());
        holder.UNIT.setText(obj.get_unit());

        return row;
//        View customView = convertView;
//        if (customView == null) {
//            customView = inflater.inflate(R.layout.list_layout, parent, false);
//        }
//        String exercise = getItem(position);
//        TextView type_txt = (TextView) customView.findViewById(R.id.list_type);
//        TextView amt_txt = (TextView) customView.findViewById(R.id.list_amt);
//        ImageView list_icon = (ImageView) customView.findViewById(R.id.list_icon);
//
//        type_txt.setText(exercise);
//
//        if (Arrays.asList(Min).contains(exercise)){
//            list_icon.setImageResource(R.drawable.ic_favorite_white_24dp);
//            amt_txt.setText(R.string.unit_min);
//        }
//        else if (Arrays.asList(Reps).contains(exercise)){
//            list_icon.setImageResource(R.drawable.ic_fitness_center_white_24dp);
//            amt_txt.setText(R.string.unit_reps);
//        }
//        else if (exercise.equals("Calories")) {
//            list_icon.setImageResource(R.drawable.ic_whatshot_white_24dp);
//            amt_txt.setText(R.string.unit_cal);
//        }
    }
}

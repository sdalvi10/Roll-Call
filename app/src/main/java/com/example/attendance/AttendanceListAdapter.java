package com.example.attendance;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class AttendanceListAdapter extends BaseAdapter {
    private Activity context;
    public List<Student> students;

    public AttendanceListAdapter(Activity context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int i) {
        return students.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder{
        TextView textViewRollNo;
        //TextView textViewStudentName;
        TextView textViewState;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        final int pos = i;
        final ViewHolder viewHolder;

        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.attendance_list_layout, null, false);
            viewHolder.textViewRollNo = (TextView) v.findViewById(R.id.attendanceListLayout_textViewRollNo);
            //viewHolder.textViewStudentName = (TextView) v.findViewById(R.id.attendanceListLayout_textViewStudentName);
            viewHolder.textViewState = (TextView) v.findViewById(R.id.attendanceListLayout_textViewState);

            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.textViewRollNo.setText(students.get(pos).getStudentRollNo());
        //viewHolder.textViewStudentName.setText(students.get(pos).getStudentName());
        viewHolder.textViewState.setText(students.get(pos).getStudentPresence());

        return v;
    }
}

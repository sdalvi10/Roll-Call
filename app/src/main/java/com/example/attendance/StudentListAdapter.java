package com.example.attendance;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;


import java.util.List;

public class StudentListAdapter extends BaseAdapter {
    private Activity context;
    public List<Student> students;

    public StudentListAdapter(Activity context, List<Student> students) {
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
        Switch switchPresence;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        final int pos = i;
        final ViewHolder viewHolder;

        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.list_layout, null, false);
            viewHolder.textViewRollNo = (TextView) v.findViewById(R.id.textViewRollNo);
            //viewHolder.textViewStudentName = (TextView) v.findViewById(R.id.textViewStudentName);
            viewHolder.textViewState = (TextView) v.findViewById(R.id.textViewState);
            viewHolder.switchPresence = (Switch) v.findViewById(R.id.switchPresentOrAbsent);

            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.textViewRollNo.setText(students.get(pos).getStudentRollNo());
        //viewHolder.textViewStudentName.setText(students.get(pos).getStudentName());
        students.get(pos).setStudentPresence("absent");
        viewHolder.textViewState.setText(students.get(pos).getStudentPresence());


        viewHolder.switchPresence.setOnCheckedChangeListener(null);
        viewHolder.switchPresence.setChecked(false);


        viewHolder.switchPresence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!viewHolder.switchPresence.isChecked()){
                    viewHolder.textViewState.setText("Absent");
                    students.get(pos).setStudentPresence("absent");
                }
                else{
                    viewHolder.textViewState.setText("Present");
                    students.get(pos).setStudentPresence("present");
                }

            }
        });

        if(students.get(pos).getStudentPresence() == "present"){
            viewHolder.switchPresence.setChecked(true);
        }

        return v;
    }

    public List<Student> getStudents(){
        return students;
    }
}

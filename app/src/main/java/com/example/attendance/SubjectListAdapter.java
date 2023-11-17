package com.example.attendance;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SubjectListAdapter extends BaseAdapter {
    private Activity context;
    public List<MySubject> subjects;
    private String user;

    public static final String SUBJECT_ID = "subjectId";

    public SubjectListAdapter(Activity context, List<MySubject> subjects, String user) {
        this.context = context;
        this.subjects = subjects;
        this.user = user;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int i) {
        return subjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private static class ViewHolder{
        TextView textViewSubject;
        TextView textViewYear;
        TextView textViewDepartment;
        TextView textViewShift;
        ImageButton imageButtonDelete;
        ImageButton imageButtonMarkAttendance;
        //ImageButton imageButtonUpdate;
        ImageButton imageButtonDisplayLectures;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        final int pos = i;
        final ViewHolder viewHolder;

        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.subject_list_layout, null, false);
            viewHolder.textViewSubject = (TextView) v.findViewById(R.id.subjectListLayout_textViewSubject);
            viewHolder.textViewYear = (TextView) v.findViewById(R.id.subjectListLayout_textViewYear);
            viewHolder.textViewDepartment = (TextView) v.findViewById(R.id.subjectListLayout_textViewDepartment);
            viewHolder.imageButtonDelete = v.findViewById(R.id.subjectListLayout_imageButtonDelete);
            viewHolder.imageButtonMarkAttendance = v.findViewById(R.id.subjectListLayout_imageButtonMarkAttendance);
            //viewHolder.imageButtonUpdate = v.findViewById(R.id.subjectListLayout_imageButtonUpdate);
            viewHolder.imageButtonDisplayLectures = v.findViewById(R.id.subjectListLayout_imageButtonDisplayLectures);
            viewHolder.textViewShift = v.findViewById(R.id.subjectListLayout_textViewShift);


            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.textViewSubject.setText(subjects.get(pos).getSubjectName());
        viewHolder.textViewYear.setText(subjects.get(pos).getYear());
        viewHolder.textViewDepartment.setText(subjects.get(pos).getDepartment());
        viewHolder.textViewShift.setText(subjects.get(pos).getShift());

        viewHolder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dr_my_subjects = FirebaseDatabase.getInstance().getReference("my-subjects").child(user).child(subjects.get(pos).getSubjectId());
                DatabaseReference dr_students = FirebaseDatabase.getInstance().getReference("students").child(subjects.get(pos).getSubjectId());
                DatabaseReference dr_lecture_records = FirebaseDatabase.getInstance().getReference("lecture-records").child(subjects.get(pos).getSubjectId());
                DatabaseReference dr_attendance_records = FirebaseDatabase.getInstance().getReference("attendance-records").child(subjects.get(pos).getSubjectId());

                dr_my_subjects.removeValue();
                dr_students.removeValue();
                dr_lecture_records.removeValue();
                dr_attendance_records.removeValue();

                Toast.makeText(context, "Subject Deleted", Toast.LENGTH_SHORT).show();

            }
        });

        viewHolder.imageButtonMarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MarkAttendanceActivity.class);
                intent.putExtra(SUBJECT_ID, subjects.get(pos).getSubjectId());
                context.startActivity(intent);
            }
        });

        viewHolder.imageButtonDisplayLectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DisplayLecturesActivity.class);
                intent.putExtra(SUBJECT_ID, subjects.get(pos).getSubjectId());
                context.startActivity(intent);
            }
        });

        /*viewHolder.imageButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateLectureActivity.class);
                intent.putExtra(SUBJECT_ID, subjects.get(pos).getSubjectId());
                context.startActivity(intent);
            }
        });*/

        return v;
    }

}

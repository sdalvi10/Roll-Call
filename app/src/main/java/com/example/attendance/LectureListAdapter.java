package com.example.attendance;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LectureListAdapter extends BaseAdapter {
    private Activity context;
    public List<Lecture> lectures;
    private String subjectId;

    public static final String SUBJECT_ID = "subjectId";
    public static final String LECTURE_ID = "lectureId";


    public LectureListAdapter(Activity context, List<Lecture> lectures, String subjectId) {
        this.context = context;
        this.lectures = lectures;
        this.subjectId = subjectId;
    }

    @Override
    public int getCount() {
        return lectures.size();
    }

    @Override
    public Object getItem(int i) {
        return lectures.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private static class ViewHolder{
        TextView textViewDate;
        TextView textViewTime;
        ImageButton imageButtonDelete;
        ImageButton imageButtonDisplay;
        ImageButton imageButtonUpdate;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        final int pos = i;
        final ViewHolder viewHolder;

        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.lecture_list_layout, null, false);
            viewHolder.textViewDate = (TextView) v.findViewById(R.id.lectureList_textViewDate);
            viewHolder.textViewTime = (TextView) v.findViewById(R.id.lectureList_textViewTime);
            viewHolder.imageButtonDelete = v.findViewById(R.id.lectureList_imageButtonDelete);
            viewHolder.imageButtonDisplay = v.findViewById(R.id.lectureList_imageButtonDisplay);
            viewHolder.imageButtonUpdate = v.findViewById(R.id.lectureList_imageButtonUpdate);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.textViewDate.setText(lectures.get(pos).getDate());
        viewHolder.textViewTime.setText(lectures.get(pos).getTime());

        viewHolder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dr_lecture_records = FirebaseDatabase.getInstance().getReference("lecture-records").child(subjectId).child(lectures.get(pos).getLectureId());
                DatabaseReference dr_attendance_records = FirebaseDatabase.getInstance().getReference("attendance-records").child(subjectId).child(lectures.get(pos).getLectureId());
                dr_lecture_records.removeValue();
                dr_attendance_records.removeValue();
                Toast.makeText(context, "Lecture Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.imageButtonDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DisplayAttendanceActivity.class);
                intent.putExtra(SUBJECT_ID, subjectId);
                intent.putExtra(LECTURE_ID, lectures.get(pos).getLectureId());
                context.startActivity(intent);
            }
        });

        viewHolder.imageButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAttendanceActivity.class);
                intent.putExtra(SUBJECT_ID, subjectId);
                intent.putExtra(LECTURE_ID, lectures.get(pos).getLectureId());
                context.startActivity(intent);
            }
        });

        return v;
    }
}

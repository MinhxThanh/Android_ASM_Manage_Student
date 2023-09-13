package pro.edu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pro.edu.R;
import pro.edu.helper.DateTimeHelper;
import pro.edu.model.Students;

public class StudentsAdapter extends BaseAdapter {
    private Context context;
    private List<Students> list;

    public StudentsAdapter(Context context, List<Students> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_students_item_list_view, null);
        }
        TextView tvStudentLVId = (TextView) view.findViewById(R.id.tvStudentLVId);
        TextView tvStudentLVName = (TextView) view.findViewById(R.id.tvStudentLVName);
        TextView tvStudentLVBirthDate = (TextView) view.findViewById(R.id.tvStudentLVBirthDate);

        Students student = list.get(i);

        tvStudentLVId.setText(student.getId());
        tvStudentLVName.setText(student.getName());
        tvStudentLVBirthDate.setText(DateTimeHelper.toString(student.getBirthDate()));

        return view;
    }
}

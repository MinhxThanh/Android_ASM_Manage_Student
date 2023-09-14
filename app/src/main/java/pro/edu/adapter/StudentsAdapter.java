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
        StudentViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_students_item_list_view, null);

            holder = new StudentViewHolder();
            holder.tvStudentLVName = view.findViewById(R.id.tvStudentLVName);
            holder.tvStudentLVId = view.findViewById(R.id.tvStudentLVId);
            holder.tvStudentLVBirthDate = view.findViewById(R.id.tvStudentLVBirthDate);

            view.setTag(holder);
        } else {
            holder = (StudentViewHolder) view.getTag();
        }

        holder.tvStudentLVId.setText(list.get(i).getId());
        holder.tvStudentLVName.setText(list.get(i).getName());
        holder.tvStudentLVBirthDate.setText(DateTimeHelper.toString(list.get(i).getBirthDate()));

        return view;
    }

    public static class StudentViewHolder {
        public TextView tvStudentLVId, tvStudentLVName, tvStudentLVBirthDate;
    }
}

package pro.edu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pro.edu.R;
import pro.edu.model.Classes;

public class ClassesAdapter extends BaseAdapter {
    private Context context;
    private List<Classes> list;

    public ClassesAdapter(Context context, List<Classes> list) {
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
        ClassesViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_classes_item_list_view, null);

            holder = new ClassesViewHolder();

            holder.tvClassCode = view.findViewById(R.id.tvClassCode);
            holder.tvClassName = view.findViewById(R.id.tvClassName);

            view.setTag(holder);
        } else {
            holder = (ClassesViewHolder) view.getTag();
        }

        Classes classes = list.get(i);
        holder.tvClassCode.setText("Code: " + classes.getCode());
        holder.tvClassName.setText("Name: " + classes.getName());

        return view;
    }

    public static class ClassesViewHolder {
        public TextView tvClassCode, tvClassName;
    }
}

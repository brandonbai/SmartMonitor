package org.jifeihu.smartshed.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.bean.Log;

import java.util.ArrayList;
import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {
    // 日志集合
    private List<Log> mList;

    public NewsRecyclerViewAdapter() {
        mList = new ArrayList<>();
    }

    /**
     * 设置数据
     * @param list
     */
    public void setLogs(List<Log> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_main, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.contentTextView.setText(mList.get(position).getContent());
        holder.timeTextView.setText(mList.get(position).getTime().toString());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView, contentTextView;

        public ViewHolder(View view) {
            super(view);
            timeTextView = (TextView) view.findViewById(R.id.text_view_time);
            contentTextView = (TextView) view.findViewById(R.id.text_view_content);
        }
    }
}

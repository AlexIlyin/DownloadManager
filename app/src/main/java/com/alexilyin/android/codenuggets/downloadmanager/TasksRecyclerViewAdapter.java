package com.alexilyin.android.codenuggets.downloadmanager;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexilyin on 11.05.17.
 */
class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.TaskViewHolder> {

    private static final String TAG = TasksRecyclerViewAdapter.class.getSimpleName();

    private ArrayList<Task> taskList = new ArrayList<>();

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.id = taskList.get(position).getId();
        holder.itemTitleTextView.setText(taskList.get(position).getTitle());
        holder.itemProgressBar.setProgress(taskList.get(position).getProgress());
        holder.itemProgressBar.setMax(taskList.get(position).getProgressMax());
        holder.itemProgressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFF00, PorterDuff.Mode.MULTIPLY);
        switch (taskList.get(position).getStatus()) {
            case NEW:
                holder.itemTitleTextView.setTypeface(null, Typeface.BOLD);
                holder.itemTitleTextView.setEnabled(true);
                break;
            case ACTIVE:
                holder.itemTitleTextView.setTypeface(null, Typeface.NORMAL);
                holder.itemTitleTextView.setEnabled(true);
                break;
            case PAUSED:
                holder.itemTitleTextView.setTypeface(null, Typeface.ITALIC);
                holder.itemTitleTextView.setEnabled(false);
                break;
            case DONE:
                holder.itemTitleTextView.setTypeface(null, Typeface.NORMAL);
                holder.itemTitleTextView.setEnabled(false);
                holder.itemProgressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFF00, PorterDuff.Mode.MULTIPLY);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void taskAdd(Task task) {
        taskList.add(task);
        notifyItemInserted(taskList.lastIndexOf(task));
    }

    public void taskChange(Task task) {
        int index = taskList.lastIndexOf(task);
        taskList.set(index, task);
        notifyItemChanged(index);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        int id;

        @BindView(R.id.itemTitleTextView)
        TextView itemTitleTextView;

        @BindView(R.id.itemProgressBar)
        ProgressBar itemProgressBar;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

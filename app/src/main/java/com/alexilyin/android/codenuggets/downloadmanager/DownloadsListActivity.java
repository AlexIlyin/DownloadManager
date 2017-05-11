package com.alexilyin.android.codenuggets.downloadmanager;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadsListActivity extends MvpAppCompatActivity implements DownloadsListView {

    private static final String TAG = DownloadsListActivity.class.getSimpleName();
    private TasksRecyclerViewAdapter tasksRecyclerViewAdapter;

    @InjectPresenter
    DownloadsListPresenter presenter;

    @BindView(R.id.taskListRecyclerView)
    RecyclerView taskListRecyclerView;

    @BindView(R.id.durationSeekBar)
    SeekBar durationSeekBar;

    @BindView(R.id.durationTextView)
    TextView durationTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads_list);
        ButterKnife.bind(this);

        tasksRecyclerViewAdapter = new TasksRecyclerViewAdapter();
        taskListRecyclerView.setAdapter(tasksRecyclerViewAdapter);
        taskListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        durationSeekBar.setOnSeekBarChangeListener(new DurationSeekBarChangeListener());

    }

    @OnClick(R.id.addTaskButton)
    public void onAddTaskButtonClick(View v) {
        presenter.requestTaskAdd(durationSeekBar.getProgress());
    }

    @Override
    public void showNewTask(Task newTask) {
        tasksRecyclerViewAdapter.taskAdd(newTask);
    }


    @Override
    public void showTaskChanges(Task task) {
        tasksRecyclerViewAdapter.taskChange(task);
    }


    class DurationSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            durationTextView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

}

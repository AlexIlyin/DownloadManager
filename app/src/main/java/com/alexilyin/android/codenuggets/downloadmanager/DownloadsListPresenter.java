package com.alexilyin.android.codenuggets.downloadmanager;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;

/**
 * Created by alexilyin on 11.05.17.
 */

@InjectViewState
public class DownloadsListPresenter extends MvpPresenter<DownloadsListView> {

    private static final String TAG = TasksRecyclerViewAdapter.class.getSimpleName();
    private static final int DEFAULT_DURATION = 15;

    public static final int UI_MSG_CHANGE = 0;

    private ArrayList<Task> taskList = new ArrayList<>();

    private WorkingThread wThread;
    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

                switch (msg.what) {
                    case UI_MSG_CHANGE:
                        if (msg.obj instanceof Task) {
                            Task task = (Task) msg.obj;
                            taskChanged(task);
                        }
                        break;
                }
            }

    };

    private void taskChanged(Task task) {
        switch (task.getStatus()) {
            case NEW:
                getViewState().showTaskChanges(task);
                break;
            case ACTIVE:
                getViewState().showTaskChanges(task);
                break;
            case PAUSED:
                getViewState().showTaskChanges(task);
                break;
            case DONE:
                getViewState().showTaskChanges(task);
                break;
        }
    }

    public void requestTaskAdd(int duration) {
        int id = taskList.size() + 1;
        String title = "Task no. " + taskList.size();
        int progress = 0;
        int progressMax = (duration > 0) ? duration : DEFAULT_DURATION;

        Task newTask = new Task(id, title, progress, progressMax);
        if (!taskList.contains(newTask)) {
            taskList.add(newTask);
        }

        if (wThread == null) {
            wThread = new WorkingThread("Working thread 1");
            wThread.start();
        }

        getViewState().showNewTask(newTask);
        wThread.addTask(newTask);
    }

    class WorkingThread extends HandlerThread {

        public static final int W_MSG_START_NEW_TASK = 0;

        private Handler wHandler;

        public WorkingThread(String name) {
            super(name);
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            wHandler = new Handler(getLooper()) {

                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case W_MSG_START_NEW_TASK:
                            if (msg.obj instanceof Task) {
                                Task task = (Task) msg.obj;
                                doWork(task);
                            }
                            break;
                    }
                }
            };
        }

        private void doWork(Task task) {
            int seconds = task.getProgress();
            int secondsTotal = task.getProgressMax();

            task.setStatus(Task.Status.ACTIVE);
            notifyChange(task);
            try {
                while (seconds < secondsTotal) {
                    Thread.sleep(1000);
                    task.setProgress(++seconds);
                    notifyChange(task);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (task.getProgress() == task.getProgressMax()) {
                    task.setStatus(Task.Status.DONE);
                } else {
                    task.setStatus(Task.Status.PAUSED);
                }

                notifyChange(task);
            }
        }


        // executes in Working thread
        private void notifyChange(Task task) {
            Message msg = uiHandler.obtainMessage(UI_MSG_CHANGE, task);
            uiHandler.sendMessage(msg);
        }

        // executes in UI thread
        public void addTask(Task task) {
            while (wHandler == null) {}
            Message msg = wHandler.obtainMessage(W_MSG_START_NEW_TASK, task);
            wHandler.sendMessage(msg);
        }
    }
}

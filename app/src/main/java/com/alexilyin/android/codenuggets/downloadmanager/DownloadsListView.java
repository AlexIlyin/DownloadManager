package com.alexilyin.android.codenuggets.downloadmanager;

import com.arellomobile.mvp.MvpView;

/**
 * Created by alexilyin on 11.05.17.
 */

public interface DownloadsListView extends MvpView {
    void showNewTask(Task newTask);

    void showTaskChanges(Task task);
}

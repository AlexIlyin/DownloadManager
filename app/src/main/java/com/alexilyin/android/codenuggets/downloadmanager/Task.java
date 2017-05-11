package com.alexilyin.android.codenuggets.downloadmanager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexilyin on 11.05.17.
 */
class Task {

    public enum Status {NEW, ACTIVE, PAUSED, DONE};
    private int id;
    private String title;
    private int progress;
    private int progressMax;
    private Status status;

    public Task(int id) {
        this.id = id;
    }

    public Task(int id, String title, int progress, int progressMax) {
        this.id = id;
        this.title = title;
        this.progress = progress;
        this.progressMax = progressMax;
        this.status = Status.NEW;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        title = in.readString();
        progress = in.readInt();
        progressMax = in.readInt();
    }


    // =======================================================================================
    // equals, hashCode
    // =======================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id == task.id;

    }

    @Override
    public int hashCode() {
        return id;
    }


    // =======================================================================================
    // Getters & Setters
    // =======================================================================================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgressMax() {
        return progressMax;
    }

    public void setProgressMax(int progressMax) {
        this.progressMax = progressMax;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

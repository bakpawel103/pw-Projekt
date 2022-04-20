package com.example.projekt.entities;

import com.example.projekt.AuctionArbiter;
import javafx.application.Platform;
import javafx.scene.control.TableView;

import java.util.*;

public class User extends Thread {
    private int id;
    private List<Integer> files;
    private float waitingTime;

    private boolean isBlocked;

    private TableView<User> usersTableView;

    public User(int id, List<Integer> files, float waitingTime, TableView<User> usersTableView) {
        this.id = id;
        this.files = files;
        Collections.sort(this.files);
        this.waitingTime = waitingTime;
        this.usersTableView = usersTableView;

        this.setDaemon(true);
        this.start();
    }

    public long getId() {
        return id;
    }

    public List<Integer> getFiles() {
        return files;
    }

    public float getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(float waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Platform.runLater(() -> {
                    setWaitingTime(getWaitingTime() + 1);

                    this.usersTableView.refresh();

                    if(getFiles().size() == 0) {
                        AuctionArbiter.getInstance().deleteUser(this);
                    }
                });

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

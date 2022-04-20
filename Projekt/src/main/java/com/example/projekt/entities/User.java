package com.example.projekt.entities;

import javafx.application.Platform;
import javafx.scene.control.TableView;

import java.util.*;

public class User extends Thread {
    private int id;
    private List<Integer> files;
    private float waitingTime;

    private TableView<User> usersTableView;

    public User(int id, List<Integer> files, float waitingTime, TableView<User> usersTableView) {
        this.id = id;
        this.files = files;
        this.waitingTime = waitingTime;
        this.usersTableView = usersTableView;

        this.setDaemon(true);
        this.start();
    }
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getFiles() {
        return files;
    }

    public void setFiles(List<Integer> files) {
        this.files = files;
    }

    public float getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(float waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Platform.runLater(() -> {
                    setWaitingTime(getWaitingTime() + 1);

                    this.usersTableView.refresh();
                });

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

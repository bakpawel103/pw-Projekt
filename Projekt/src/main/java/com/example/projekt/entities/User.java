package com.example.projekt.entities;

import com.example.projekt.AuctionArbiter;
import com.example.projekt.FileInfo;
import javafx.application.Platform;
import javafx.scene.control.TableView;

import java.util.*;
import java.util.stream.Collectors;

public class User extends Thread {
    private int id;
    private List<FileInfo> filesInfo;
    private float waitingTime;

    private TableView<User> usersTableView;

    public User(int id, List<FileInfo> filesInfo, float waitingTime, TableView<User> usersTableView) {
        this.id = id;
        this.filesInfo = filesInfo;
        this.filesInfo.sort(Comparator.comparing(FileInfo::getSize));
        this.waitingTime = waitingTime;
        this.usersTableView = usersTableView;

        this.setDaemon(true);
        this.start();
    }

    public long getId() {
        return id;
    }

    public List<FileInfo> getFilesInfo() {
        return filesInfo;
    }

    public List<Integer> getFiles() {
        return filesInfo.stream().map(FileInfo::getSize).collect(Collectors.toList());
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

                    if(getFilesInfo().size() == 0) {
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

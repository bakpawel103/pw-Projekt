package com.example.projekt.entities;

import javafx.scene.control.Label;

public class Server extends Thread {
    private int currentUserId;
    private Label currentUserIdLabel;

    public Server(int currentUserId, Label currentUserIdLabel) {
        this.currentUserId = currentUserId;
        this.currentUserIdLabel = currentUserIdLabel;

        this.setDaemon(true);
        this.start();
    }
    @Override
    public void run() {
        while (true) {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
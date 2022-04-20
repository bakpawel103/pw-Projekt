package com.example.projekt.entities;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class Server extends Thread {
    private String name;
    private User currentServingUser;
    private Label currentUserIdLabel;
    private ProgressBar progressBar;
    private Label progressBarLabel;

    private int currentUploadingProgress = 0;

    public Server(String name, Label currentUserIdLabel, ProgressBar progressBar, Label progressBarLabel) {
        this.name = name;
        this.currentUserIdLabel = currentUserIdLabel;
        this.progressBar = progressBar;
        this.progressBarLabel = progressBarLabel;

        this.setDaemon(true);
        this.start();
    }

    public boolean isBusy() {
        return currentServingUser != null;
    }

    public void setCurrentServingUser(User currentServingUser) {
        this.currentServingUser = currentServingUser;
        this.currentServingUser.setBlocked(true);
        currentUploadingProgress = 0;

        Platform.runLater(() -> {
            currentUserIdLabel.setText(String.valueOf(currentServingUser.getId()));
        });
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (isBusy()) {
                    currentUploadingProgress += 10;

                    Platform.runLater(() -> {
                        progressBar.setProgress((float) currentUploadingProgress / currentServingUser.getFiles().get(0));
                        progressBarLabel.setText(String.format("%.0f", (float) currentUploadingProgress / currentServingUser.getFiles().get(0) * 100) + "%");
                    });

                    if((float) currentUploadingProgress / currentServingUser.getFiles().get(0) >= 1.0f) {
                        Platform.runLater(() -> {
                            currentServingUser.getFiles().remove(0);
                            currentServingUser.setBlocked(false);
                            currentServingUser = null;

                            currentUserIdLabel.setText("");
                            progressBar.setProgress(0.0f);
                            progressBarLabel.setText("0%");
                        });
                    }
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
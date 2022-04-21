package com.example.projekt.entities;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class Server extends Thread {
    private String name;
    private User currentServingUser;
    private int currentServingUserSize;
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

    public void setCurrentServingUser(User currentServingUser, int currentServingUserSize) {
        this.currentServingUser = currentServingUser;
        this.currentServingUser
                .getFilesInfo()
                .stream()
                .filter(file -> !file.isBlocked())
                .findFirst().get().setBlocked(true);
        this.currentServingUserSize = currentServingUserSize;
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
                        progressBar.setProgress((float) currentUploadingProgress / currentServingUserSize);
                        progressBarLabel.setText(String.format("%.0f", (float) currentUploadingProgress / currentServingUserSize * 100) + "%");
                    });

                    if((float) currentUploadingProgress / currentServingUserSize >= 1.0f) {
                        Platform.runLater(() -> {
                            currentServingUser.getFilesInfo().remove(0);
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
package com.example.projekt;

import com.example.projekt.entities.Server;
import com.example.projekt.entities.User;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.List;

public final class AuctionArbiter extends Thread {
    private static AuctionArbiter INSTANCE;

    private ObservableList<User> users;
    private List<Server> servers;

    private AuctionArbiter() {

    }

    public static AuctionArbiter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuctionArbiter();
        }

        return INSTANCE;
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public void deleteUser(User user) {
        Platform.runLater(() -> {
            users.remove(user);
        });
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (Server server : servers) {
                    if (!server.isBusy()) {
                        User nextServingUser = getNextServingUser();
                        if (nextServingUser != null) {
                            server.setCurrentServingUser(nextServingUser);
                        }
                    }
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private User getNextServingUser() {
        User bestPredictionUser = null;
        float bestPredictionValue = 0;

        ObservableList<User> notBlockedUsers = users.filtered(user -> !user.isBlocked() && user.getFiles().size() > 0);

        for (User user : notBlockedUsers) {
            float size = (float) (1 / user.getFiles().get(0)) / notBlockedUsers.size();
            float arrive = (float) (Math.sqrt(user.getWaitingTime()) / notBlockedUsers.size());
            float prediction = size + arrive;
            if (prediction > bestPredictionValue) {
                bestPredictionValue = prediction;
                bestPredictionUser = user;
            }
        }

        return bestPredictionUser;
    }
}

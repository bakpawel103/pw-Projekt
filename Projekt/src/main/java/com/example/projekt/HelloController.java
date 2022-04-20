package com.example.projekt;

import com.example.projekt.entities.Server;
import com.example.projekt.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label currentUser_server1;
    @FXML
    private ProgressBar progressBar_server1;
    @FXML
    private Label progressBarLabel_server1;
    @FXML
    private Label currentUser_server2;
    @FXML
    private ProgressBar progressBar_server2;
    @FXML
    private Label progressBarLabel_server2;
    @FXML
    private Label currentUser_server3;
    @FXML
    private ProgressBar progressBar_server3;
    @FXML
    private Label progressBarLabel_server3;
    @FXML
    private Label currentUser_server4;
    @FXML
    private ProgressBar progressBar_server4;
    @FXML
    private Label progressBarLabel_server4;
    @FXML
    private Label currentUser_server5;
    @FXML
    private ProgressBar progressBar_server5;
    @FXML
    private Label progressBarLabel_server5;

    @FXML
    private TableView<User> usersTableView;

    @FXML
    private TableColumn<User, String> idColumn;
    @FXML
    private TableColumn<User, String> filesColumn;
    @FXML
    private TableColumn<User, String> waitingTimeColumn;

    private int userIteratorId = 0;

    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final List<Server> servers = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableView();

        initializeServers();

        initializeAuctionArbiter();
    }

    @FXML
    protected void onGenerateButtonClick() {
        users.add(generateUser());

        usersTableView.setItems(users);
        userIteratorId++;
    }

    private int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private void initializeTableView() {
        //set up the columns in the table
        idColumn.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        filesColumn.setCellValueFactory(new PropertyValueFactory<User, String>("files"));
        waitingTimeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("waitingTime"));

        usersTableView.setItems(users);
    }

    private void initializeServers() {
        servers.add(new Server("server_1", currentUser_server1, progressBar_server1, progressBarLabel_server1));
        servers.add(new Server("server_2", currentUser_server2, progressBar_server2, progressBarLabel_server2));
        servers.add(new Server("server_3", currentUser_server3, progressBar_server3, progressBarLabel_server3));
        servers.add(new Server("server_4", currentUser_server4, progressBar_server4, progressBarLabel_server4));
        servers.add(new Server("server_5", currentUser_server5, progressBar_server5, progressBarLabel_server5));
    }

    private void initializeAuctionArbiter() {
        AuctionArbiter auctionArbiter = AuctionArbiter.getInstance();
        auctionArbiter.setUsers(users);
        auctionArbiter.setServers(servers);

        auctionArbiter.setDaemon(true);
        auctionArbiter.start();
    }

    private User generateUser() {
        List<Integer> files = new ArrayList<>();
        for (int index = 0; index < getRandomNumberUsingNextInt(1, 5); index++) {
            files.add(getRandomNumberUsingNextInt(50, 200));
        }

        return new User(userIteratorId, files, 0.0f, usersTableView);
    }
}
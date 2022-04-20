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
    private Label label_server1;
    @FXML
    private Label currentUser_server2;
    @FXML
    private ProgressBar progressBar_server2;
    @FXML
    private Label label_server2;
    @FXML
    private Label currentUser_server3;
    @FXML
    private ProgressBar progressBar_server3;
    @FXML
    private Label label_server3;
    @FXML
    private Label currentUser_server4;
    @FXML
    private ProgressBar progressBar_server4;
    @FXML
    private Label label_server4;
    @FXML
    private Label currentUser_server5;
    @FXML
    private ProgressBar progressBar_server5;
    @FXML
    private Label label_server5;

    @FXML
    private TableView<User> usersTableView;

    @FXML
    private TableColumn<User, String> idColumn;
    @FXML
    private TableColumn<User, String> filesColumn;
    @FXML
    private TableColumn<User, String> waitingTimeColumn;

    private int userIteratorId = 0;

    private ObservableList<User> users;
    private List<Server> servers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set up the columns in the table
        idColumn.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        filesColumn.setCellValueFactory(new PropertyValueFactory<User, String>("files"));
        waitingTimeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("waitingTime"));

        //load dummy data
        users = FXCollections.observableArrayList();

        usersTableView.setItems(users);
    }

    @FXML
    protected void onGenerateButtonClick() {
        List<Integer> files = new ArrayList<Integer>();
        for(int index = 0; index < getRandomNumberUsingNextInt(1, 5); index++) {
            files.add(getRandomNumberUsingNextInt(10, 3000));
        }

        User user = new User(userIteratorId, files, 0.0f, usersTableView);
        users.add(user);

        usersTableView.setItems(users);
        userIteratorId++;
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
package com.polyTweet.controller;

import com.polyTweet.profile.Profile;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {

    private Profile profile;

    @FXML
    public Button profileButton, actualitiesButton, settingsButton;

    public HeaderController() {
        this.profile = MainView.getProfile();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    @FXML
    public void profileClick(ActionEvent e) {
        MainView.switchScene("profile");
    }

    @FXML
    private void actualitiesClick(ActionEvent e) {
        MainView.switchScene("actualities");
    }

    @FXML
    private void settingsClick(ActionEvent e) {
        MainView.switchScene("settings");
    }

    @FXML
    private void logoutClick() {
        MainView.closeWindow();
    }

}
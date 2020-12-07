package com.polyTweet.controller;

import com.polyTweet.profile.Profile;
import com.polyTweet.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    public TextField firstName, lastName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void registerClick(ActionEvent e) {

        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();

        System.out.println(firstName);
        System.out.println(lastName);

        if( firstName != "" && lastName != "") {
            this.firstName.setText("");
            this.lastName.setText("");
            Profile profile = new Profile(firstName, lastName);
            MainView.setProfile(profile);
            MainView.switchScene("actualities");
        } else {
            System.err.println("Error : Le prénom ou le nom est manquant !");
        }

    }

}
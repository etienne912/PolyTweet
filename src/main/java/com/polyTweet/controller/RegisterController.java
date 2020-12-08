package com.polyTweet.controller;

import com.polyTweet.node.Node;
import com.polyTweet.node.NodeInfo;
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
    public TextField firstName, lastName, localPortField, networkIpField, networkPortField, localIpField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void registerClick(ActionEvent e) {

        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();

        if(!firstName.equals("") && !lastName.equals("")) {

            Profile profile = new Profile(firstName, lastName);
            Node node = new Node(profile, new NodeInfo(this.localIpField.getText(), Integer.parseInt(this.localPortField.getText())));

            if( !this.networkIpField.getText().equals("") && !this.networkPortField.getText().equals("") )
                node.addNeighbor(new NodeInfo(this.networkIpField.getText(), Integer.parseInt(this.networkPortField.getText())));

            this.firstName.setText("");
            this.lastName.setText("");

            MainView.init(profile, node);
            MainView.switchScene("actualities");
        } else {
            System.err.println("Error : Le prénom ou le nom est manquant !");
        }

    }

}
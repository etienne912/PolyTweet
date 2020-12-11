package com.polyTweet.controller;

import com.jfoenix.controls.JFXListView;
import com.polyTweet.dao.Node;
import com.polyTweet.model.Profile;
import com.polyTweet.view.MainView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * Search list view controller.
 */
public class FollowedController implements Initializable {

    private final Profile profile;
    private final Node node;
    HashSet<Long> profiles;

    @FXML
    public JFXListView listResult;
    
    /**
     * Search list Controller.
     */
    public FollowedController() {
        this.profile = MainView.getProfile();
        this.node = MainView.getNode();
    }

    /**
     * Initialization of the view.
     * @param location location of the view
     * @param resources information for the initialisation
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.listResult.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        this.listResult.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> { // Listener on each filled cell of the list

                if( cell.getText() != null ) {

                    String[] entireName = cell.getText().split(" ");

                    for (Long id : profiles) {
                        
                        Profile followedProfile = node.searchProfile(id);
                        
                        if( followedProfile != null ){
                            if (entireName[0].equals(followedProfile.getFirstName()) && entireName[1].equals(followedProfile.getLastName())) {
                                if (followedProfile.equals(profile)) {
                                    MainView.switchScene("profile");
                                } else {
                                    MainView.initVisitProfile(followedProfile);
                                    MainView.switchScene("profileVisitor");
                                }
                                break;
                            }
                        }
                    }
                }
            });
            return cell ;
        });
    }
    
    /**
     * Display of all profiles followed by the user.
     * @param profiles Profiles list
     */
    public void initResult(HashSet<Long> profiles) {

        this.profiles = profiles;
        
        if( profiles.size() > 0 ) {
            ObservableList<String> items = FXCollections.observableArrayList();

            profiles.forEach(id -> {
                Profile followedProfile = node.searchProfile(id);

                if( followedProfile != null ) {
                    items.add(followedProfile.getFirstName() + " " + followedProfile.getLastName());
                }
            });

            this.listResult.setItems(items);
        }
    }

}
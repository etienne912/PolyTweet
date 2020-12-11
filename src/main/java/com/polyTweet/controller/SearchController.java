package com.polyTweet.controller;

import com.jfoenix.controls.JFXListView;
import com.polyTweet.Observable;
import com.polyTweet.Observer;
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
import java.util.List;
import java.util.ResourceBundle;

/**
 * Search list view controller.
 */
public class SearchController implements Initializable, Observer {

    private final Profile profile;
    private final Node node;
    private List<Profile> profiles;

    @FXML
    public JFXListView listResult;

    /**
     * Search list Controller.
     */
    public SearchController() {
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

                    for (Profile p : profiles) {
                        if (entireName[0].equals(p.getFirstName()) && entireName[1].equals(p.getLastName())) {
                            if (p.equals(profile)) {
                                MainView.switchScene("profile");
                            } else {
                                MainView.initVisitProfile(p);
                                MainView.switchScene("profileVisitor");
                            }
                            break;
                        }
                    }
                }
			});
			return cell ;
		});
    }

    /**
     * Display of all profiles which correspond to the search.
     * @param search Text to search profiles which contain it in their name
     */
    public void initResult(String search) {

        profiles = node.searchProfile(search);

        if( profiles.size() > 0 ) {
            ObservableList<String> items = FXCollections.observableArrayList();

            this.profiles.forEach(p -> items.add(p.getFirstName() + " " + p.getLastName()));

            this.listResult.setItems(items);
        }
    }

    /**
     * Display of all profiles followed by the user.
     * @param profiles Profiles list
     */
    public void initResult(HashSet<Long> profiles) {

        if( profiles.size() > 0 ) {
            ObservableList<String> items = FXCollections.observableArrayList();

            this.profiles.forEach(p -> items.add(p.getFirstName() + " " + p.getLastName()));

            this.listResult.setItems(items);
        }
    }

    /**
     * Function to update the view.
     * @param observable Observable element
     */
    @Override
    public void update(Observable observable) {}
}
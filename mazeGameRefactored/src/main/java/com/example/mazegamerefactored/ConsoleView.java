package com.example.mazegamerefactored;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class ConsoleView extends Application implements Serializable, Observer {
    static Controler myControler;
    static Stage myStage;
    static Timeline timeline;


    static void launchViewFromControler(Controler passedControler){
        myControler = passedControler;
        launch();
        System.out.println("returned to launch");
    }
    public static void main(String[] args){
       launch();

    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Trivia Maze Game");
        myStage = stage;
        //Controler.main(null);
        showMainMenu();
        System.out.println("retruend to start method");
    }

    public static void showMainMenu() {
        var vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20.0);

        var label = new Label("Select Trivia Questions");

        ObservableList<Pair<Integer, String>> list = FXCollections.observableArrayList(
                new Pair<>(1, "Simple Test Questions"),
                new Pair<>(2, "States and Capitals")
        );
        var comboBox = new ComboBox<>(list);
        comboBox.getSelectionModel().selectFirst();

        var btnCreate = new Button("Create Game");
        btnCreate.setOnAction(e -> {
            int triviaId = comboBox.getSelectionModel().getSelectedItem().getKey();
            createGame(triviaId);
        });

        //logic for game loading
        var btnLoad = new Button("Load Game");

        ObservableList<Game> listOfSavedGames = FXCollections.observableArrayList();
        listOfSavedGames.addAll(myControler.savedGames);

        var gamesComboBox = new ComboBox<>(listOfSavedGames);
        gamesComboBox.getSelectionModel().selectFirst();
        btnLoad.setOnAction(
                e -> {
                    Game g =gamesComboBox.getSelectionModel().getSelectedItem();
                    myControler.gameSelected( g );
                }
        );

        vbox.getChildren().addAll(label, comboBox, btnCreate, gamesComboBox, btnLoad);
        Scene scene = new Scene(vbox, 320, 240);
        myStage.setScene(scene);
        myStage.show();
        System.out.println("after show stage");

    }

    static void showGameScreen(Room[][] theRooms) {
        System.out.println("starting show screen");
        var grid = new GridPane();
        var vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20.0);

        grid.setStyle("-fx-background-color: #C0C0C0;");
        // Create the grid of MazeItem buttons
        for (int col = 0; col < myControler.loadedGame.getColCount(); col++) {
            for (int row = 0; row < myControler.loadedGame.getRowCount(); row++) {
                var btn = new MazeButton(row, col);
                btn.setStyle(theRooms[row][col]);
                btn.setOnMouseClicked(e -> {
                    MazeButton mButton = (MazeButton) e.getSource();
                    myControler.roomSelected(mButton.getRow(), mButton.getCol());

                });
                grid.add(btn, col, row, 1, 1);
            }
        }
        var btnSave = new Button("Save Game");
        btnSave.setOnAction(e -> showSaveGameScreen());

        var btnMenu = new Button("Menu");
        btnMenu.setOnAction(e -> showMainMenu());

        vbox.getChildren().addAll(btnSave, btnMenu, grid);

        myStage.setScene(new Scene(vbox));
        myStage.show();
    }

    static void showQuestion(Question q) {
        var vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20.0);

        if (timeline != null)
            timeline.stop();

        ProgressBar progressBar = new ProgressBar();
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(5), e -> {
                    System.out.println("Time's up!");
                    myControler.checkAnswer(0); // Put wrong answer
                }, new KeyValue(progressBar.progressProperty(), 1))
        );
        timeline.setCycleCount(1);

        var questionText = new Label(q.getQuestionText());
        vbox.getChildren().add(progressBar);
        vbox.getChildren().add(questionText);

        if (q.getIsMultipleChoice()) {
            for (Answer ans : q.getAnswers()) {
                Button btn = new Button(ans.getAnswerText());
                btn.setOnAction(e -> {
                    if (timeline != null)
                        timeline.stop();
                    myControler.checkAnswer(ans.getAnswerId());
                });
                vbox.getChildren().add(btn);
            }
        } else if (q.getIsTypedResponse()) {
            Label label1 = new Label("");
            TextField textField = new TextField();
            HBox hb = new HBox();
            hb.getChildren().addAll(label1, textField);
            hb.setSpacing(10);
            vbox.getChildren().add(hb);
            Button btn = new Button("Submit");
            vbox.getChildren().add(btn);
            btn.setOnAction(e -> {
                if (timeline != null)
                    timeline.stop();
                myControler.checkAnswer(textField.getText().trim());
            });

        }
        Scene scene = new Scene(vbox, 320, 240);

        myStage.setScene(scene);
        myStage.show();
        timeline.play();
    }

    private static void showSaveGameScreen() {

        var vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20.0);

        Label label1 = new Label("Save game as:");
        TextField textField = new TextField(myControler.loadedGame.savedGameName);
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);
        vbox.getChildren().add(hb);
        Button btn = new Button("Save");
        vbox.getChildren().add(btn);
        btn.setOnAction(e -> {
            myControler.saveState(textField.getText());
        });

        var btnDelete = new Button("Delete Game");
        vbox.getChildren().add(btnDelete);
        btnDelete.setOnAction(e -> {
            myControler.deleteGame();
        });

        myStage.setScene(new Scene(vbox, 320, 240));
        myStage.show();
    }

    static void createGame(int id) {
        myControler.createGame(id);

    }

    static void showFinishedGame(Boolean wonFinishedGame) {
        String msg = wonFinishedGame == true ? "You Won!" : "You Lost!";
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
        showMainMenu();
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Room[][]){ showGameScreen((Room[][]) arg); }
        if (arg instanceof Question){ showQuestion((Question) arg); }
        if (arg instanceof Boolean){ showFinishedGame((Boolean) arg); }
    }


}

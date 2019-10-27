/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multimedia_project;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

public class FXMLDocumentController implements Initializable {
    
    private MediaPlayer mediaPlayer;
    
    private String filepath;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekslider;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       FileChooser filechooser=new FileChooser();
       FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp4)","*.mp4");
       filechooser.getExtensionFilters().add(filter);
       File file= filechooser.showOpenDialog(null);
       filepath=file.toURI().toString();
       if(filepath!=null)
       {
           Media media=new Media(filepath);
           mediaPlayer=new MediaPlayer(media);
           mediaView.setMediaPlayer(mediaPlayer);
           DoubleProperty width=mediaView.fitWidthProperty();
           DoubleProperty height=mediaView.fitHeightProperty();
           width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
           height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
           slider.setValue(mediaPlayer.getVolume()*100);
           slider.valueProperty().addListener(new InvalidationListener(){
           public void invalidated(Observable observable){
               
           }
               @Override
               public void invalidated(javafx.beans.Observable observable) {
                   mediaPlayer.setVolume(slider.getValue()/100);
               }
                   });
           
           mediaPlayer.currentTimeProperty().addListener((new ChangeListener<javafx.util.Duration>() {
               @Override
               public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                  seekslider.setValue(newValue.toMinutes());
               }
           }));
           seekslider.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
               @Override
               public void handle(javafx.scene.input.MouseEvent event) {
                    mediaPlayer.seek(javafx.util.Duration.seconds(seekslider.getValue()));
               }
           });
           
           mediaPlayer.play();
       }
    }
    
    @FXML
    private void pauseVideo(ActionEvent event)
    {
        mediaPlayer.pause();
    } 
    @FXML
    private void playVideo(ActionEvent event)
    {
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    @FXML
    private void stopVideo(ActionEvent event)
    {
        mediaPlayer.stop();
    }
    @FXML
    private void fastVideo(ActionEvent event)
    {
        mediaPlayer.setRate(1.5);
    }
    @FXML
    private void fasterVideo(ActionEvent event)
    {
        mediaPlayer.setRate(2);
    }
    @FXML
    private void slowVideo(ActionEvent event)
    {
        mediaPlayer.setRate(0.75);
    }
    @FXML
    private void slowerVideo(ActionEvent event)
    {
        mediaPlayer.setRate(0.5);
    }
    @FXML
    private void exitVideo(ActionEvent event)
    {
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drunkmedia;

import drunkmedia.record.AudioCapture;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ADarkHero
 */
public class DrunkMediaController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button startCaptureButton;
    @FXML
    private Button stopCaptureButton;
    @FXML
    private Button setFilepathButton;
    @FXML
    private Button openFileButton;
    @FXML
    private Button openFilepathButton;
    @FXML
    private Label filePath;
    private static AudioCapture a = new AudioCapture();
    
    @FXML
    private void close(ActionEvent event) {
        a.finish();
        System.exit(0);
    }
    
    @FXML
    private void startCapture(ActionEvent event) {
        startCaptureButton.setText("Capturing...");
        stopCaptureButton.setDisable(false);
        
        Thread recorder = new Thread(new Runnable() {

            @Override
            public void run() {
                a.start();            
            }
        
        });
        
        recorder.start();
    }
    
    @FXML
    private void stopCapture(ActionEvent event) {
        startCaptureButton.setText("Start Capture");
        a.finish();
    }
    
    @FXML
    private void setFilepath(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Specify a file to save");
        chooser.setFileFilter(new FileNameExtensionFilter("Wave Files (.wav)", "wav"));
        
        int userSelection = chooser.showSaveDialog(null);
 
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            String filepath = fileToSave.getAbsolutePath();
            if (!filepath.endsWith(".wav")){
                filepath += ".wav";
            }
            File f = new File(filepath);
            a.setWavFile(f);
            filePath.setText(f.getAbsolutePath());
        }
    }
    
    @FXML
    private void openFilepath(ActionEvent event) throws IOException {
        try {    
            String filepath = filePath.getText();
            filepath = filepath.substring(0, filepath.lastIndexOf('\\'));
            filepath = filepath + "\\";

            Desktop.getDesktop().open(new File(filepath));
        }
        catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "Filepath does not exist!", "Warning",
        JOptionPane.WARNING_MESSAGE);
        }
    }
    
    @FXML
    private void openFile(ActionEvent event) throws IOException {
        try {
            Desktop.getDesktop().open(new File(filePath.getText()));
        }
        catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "File does not exist!", "Warning",
        JOptionPane.WARNING_MESSAGE);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filePath.setText(a.getWavFile().getAbsolutePath());
    }    
    
}

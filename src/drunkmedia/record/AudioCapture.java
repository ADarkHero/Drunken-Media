package drunkmedia.record;

import javax.sound.sampled.*;
import java.io.*;
 
/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class AudioCapture { 
    // path of the wav file
    File wavFile = new File("C:/RecordAudio.wav");
 
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;
    
    // is the audio capture currently started?
    boolean isStarted = false;
 
    /**
     * Defines an audio format
     */
    public AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
 
    /**
     * Captures the sound and record into a WAV file
     */
    public void start() {
        try {            
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing
 
            System.out.println("Start capturing...");
 
            AudioInputStream ais = new AudioInputStream(line);
 
            System.out.println("Start recording...");
            
            isStarted = true;
 
            // start recording
            AudioSystem.write(ais, fileType, wavFile);
 
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
 
    /**
     * Closes the target data line to finish capturing and recording
     */
    public void finish() {
        if(isStarted){
            line.stop();
            line.close();
            System.out.println("Finished");
            
            isStarted = false;
        }
    }
    public void stop() {
        finish();
    }
    
    public AudioCapture(String filePath){
        wavFile = new File(filePath);
    }
    
    public AudioCapture(){
        
    }

    public File getWavFile() {
        return wavFile;
    }

    public void setWavFile(File wavFile) {
        this.wavFile = wavFile;
    }
    
    
}
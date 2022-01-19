package game;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer implements Runnable{  
	private AudioInputStream ais;
    SourceDataLine sdl = null;
    
    public MusicPlayer(String audioName){
        try {
            ais = AudioSystem.getAudioInputStream(new File(audioName));
            AudioFormat af = ais.getFormat();
            //SourceDataLine sdl = null;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
            sdl = (SourceDataLine)AudioSystem.getLine(info);
            sdl.open(af);  
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void play() {
    	sdl.start();
            
        int nByte = 0;
        byte[] buffer = new byte[128];
        while(nByte != -1){
            try {
				nByte = ais.read(buffer,0,128);
				if(nByte >= 0){
					sdl.write(buffer, 0, nByte);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
            
        }
        sdl.stop();
    }

	@Override
	public void run() {
		this.play();
	}
}  
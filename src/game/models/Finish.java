/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.models;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Luboš
 */
public class Finish {
    
    private BufferedImage image;
    private int x;
    private int y;
    private int finishX;
    private int finishY;
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        finishX = x + image.getWidth()/2;
        finishY = y + image.getHeight()/2;

    }
    /*public Finish(int x, int y){
        this.x = x;
        this.y = y;
            try{
        image = ImageIO.read(new File("src/game/graphics/finish_door.png"));

        

    }
    catch(Exception e){
            e.printStackTrace();
        }
               
    }*/
    public int getx(){
        return x;
    }
    
    public int gety(){
        return y;
    }
    
    public int getFinishX(){
        return finishX;
    }
    
    public int getFinishY(){
        return finishY;
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    public void loadFinish(){
    
    try{
        image = ImageIO.read(new File("src/game/graphics/finish_door.png"));
    }
    catch(Exception e){
            e.printStackTrace();
        }
    }
    
}

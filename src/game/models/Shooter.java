/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.models;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Luboš
 */
public class Shooter {
    private BufferedImage image;
    private int x;
    private int y;
    boolean left;
    
    public Shooter(int x, int y, boolean left, String s){
        this.x = x;
        this.y = y;
        this.left = left;
        
        try{
           image = ImageIO.read(new File(s));
            
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public int getx(){
        return x;
    }
    
    public int gety(){
        return y;
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    public boolean isLeft(){
        return left;
    }
    
    public void update(){
        
    }
}

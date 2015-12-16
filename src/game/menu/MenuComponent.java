/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.menu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author pepek
 */
public class MenuComponent {
    
    private int width;
    private int height;
  
    private int x;
    private int y;
    
    private BufferedImage title;
    
    private String state;

    
    
    public MenuComponent(String FilePath,int x,int y,String state){
        
        this.state = state;
        this.x = x;
        this.y = y;
        
        try{
            title = ImageIO.read(new File(FilePath));
        }catch(Exception e){
            e.printStackTrace();
        }
        width = title.getWidth();
        height = title.getHeight();
        
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public BufferedImage getImage(){
        return this.title;
    }
    public void moveUp(){
        this.x = 400;
        this.y = 315;
    }
    public void moveDown(){
        this.x = 460;
        this.y = 395;
    }
    public String getState(){
        return this.state;
    }
    public void setState(String state){
        this.state = state;
    }
}

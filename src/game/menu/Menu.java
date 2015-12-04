/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.menu;

import game.menu.MenuComponent;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
/**
 *
 * @author pepek
 */
public class Menu {
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    
    private BufferedImage MenuBackground;
    private BufferedImage MenuPosition;
    private BufferedImage quit;
    private ArrayList<MenuComponent>components;
    
    private MenuComponent cmp;
    
    public Menu(String FilePath){
        components = new ArrayList<>();
        components.add(new MenuComponent("src/game/menu/StartGame1.png",479,300,"start"));
        components.add(new MenuComponent("src/game/menu/quit1.png",550,380,"quit"));
        cmp = new MenuComponent("src/game/menu/MenuKunai.png",400,315,"start");
        try{
            MenuBackground = ImageIO.read(new File(FilePath));
        }catch(Exception e){
            e.printStackTrace();
        }
         
    }
    
    
    public void render(){
        
    }
    public void update(){
        
    }
    public void draw(Graphics2D g){
        g.drawImage(MenuBackground, 0, 0, null);
        for(int i = 0; i < components.size(); i++){
            g.drawImage(components.get(i).getImage(), components.get(i).getX(), components.get(i).getY(), null);
        }
        g.drawImage(cmp.getImage(),cmp.getX(),cmp.getY(),null);
    }
    public void moveUp(){
        cmp.moveUp();
    }
    public void moveDown(){
        cmp.moveDown();
    }
    public String getState(){
        return cmp.getState();
    }
    public void setState(String state){
        cmp.setState(state);
    }
}

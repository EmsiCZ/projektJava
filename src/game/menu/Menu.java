/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.menu;

import game.menu.MenuComponent;
import java.awt.Graphics;
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
    private BufferedImage[] NinjaMimg;
    private BufferedImage[] NinjaFimg;
    private ArrayList<MenuComponent>components;
    private MenuComponent NinjaF;
    private MenuComponent NinjaM;
    private MenuComponent cmp;
    private String s = "NinjaM";
    private enum STATE{
        MAINMENU,
        CHARACTER
    };
    private STATE State = STATE.MAINMENU;
    
    public Menu(String FilePath){
        
        NinjaMimg = new BufferedImage[2];
        NinjaFimg = new BufferedImage[2];
        components = new ArrayList<>();
        
        components.add(new MenuComponent("src/game/menu/StartGame1.png",479,300,"start"));
        components.add(new MenuComponent("src/game/menu/quit1.png",550,380,"quit"));
        cmp = new MenuComponent("src/game/menu/MenuKunai.png",400,315,"start");
        
        NinjaM = new MenuComponent("src/game/menu/NinjaM.png",248,200,"male");
        NinjaF = new MenuComponent("src/game/menu/NinjaF.png",800,200,"female");
        
        NinjaMimg[0] = NinjaM.getImage();
        NinjaFimg[0] = NinjaF.getImage(); 
        try{
            MenuBackground = ImageIO.read(new File(FilePath));
            NinjaMimg[1] = ImageIO.read(new File("src/game/menu/NinjaMGP.png"));
            NinjaFimg[1] = ImageIO.read(new File("src/game/menu/NinjaFG.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
         
    }
    
    
    public void render(){
        
    }
    public void update(){
        
    }
    public void setMenu(){
        State = STATE.MAINMENU;
    }
    public void draw(Graphics2D g){
        
        g.drawImage(MenuBackground, 0, 0, null);
        if(State == STATE.MAINMENU){
            for(int i = 0; i < components.size(); i++){
                g.drawImage(components.get(i).getImage(), components.get(i).getX(), components.get(i).getY(), null);
            }
            g.drawImage(cmp.getImage(),cmp.getX(),cmp.getY(),null);
        }
        if(State == STATE.CHARACTER){
            if(s == "NinjaM"){
                g.drawImage(NinjaMimg[0], NinjaM.getX(), NinjaM.getY(),null);
                g.drawImage(NinjaFimg[1], NinjaF.getX(), NinjaF.getY(),null);
            }
            else if(s == "NinjaF"){
                g.drawImage(NinjaMimg[1], NinjaM.getX(), NinjaM.getY(),null);
                g.drawImage(NinjaFimg[0], NinjaF.getX(), NinjaF.getY(),null); 
            }
        }
        
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
    public void setMenuState(String s){
        if(s == "mainmenu")
            State = STATE.MAINMENU;
        else if(s == "character")
            State = STATE.CHARACTER;
    }
    public void character(String s){
        this.s = s;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.view;
import game.menu.Menu;
import game.models.Player;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import game.models.GameObject;
/**
 *
 * @author Luboš
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    
    private Thread thread;
    private boolean running;
    
    private BufferedImage image;
    private Graphics2D g;
    private BufferedImage DeadMenu;
    
    private int FPS = 60;
    private int targetTime = 1000/FPS;
    
    private TileMap tileMap;
    private Player player;
    private GameObject obj;
    private enum STATE{
        GAME, 
        MENU,
        CHARACTER,
        DEAD
    };
    private STATE State = STATE.MENU;
    
    private Menu menu;
    public void loadDead() {
        
        try{
        DeadMenu = ImageIO.read(new File("src/game/graphics/levelfailed.gif"));
         }catch(Exception e){
            e.printStackTrace();
        }
    }
    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }
    
    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }
    
    public void run(){
        
        init();
        initMenu();
        long startTime;
        long urdTime;
        long waitTime;
        
        while(running){
            
            startTime = System.nanoTime();
            
            update();
            render();
            draw();
            
            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;
            
            try{
                Thread.sleep(waitTime);
            }
            catch(Exception e){
            }
        }
        
    }
    
    public void init(){
        
        running = true;
        
            image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
            g = (Graphics2D) image.getGraphics();

            tileMap = new TileMap("src/game/levels/level1.txt", 64);
            tileMap.loadBackground("src/game/graphics/BG1280x720.gif");
            tileMap.loadTiles("src/game/graphics/tileset.gif");

            player = new Player(tileMap);
            player.loadPlayer("src/game/graphics/player/run_000.png");
            player.setx(100);
            player.sety(100);
           
        }
        
    
    public void initMenu(){
        running = true;
        loadDead();
        menu = new Menu("src/game/menu/MenuBackground.gif");
    }
    
    ///////////////////////////////////////////////////////
    private void update(){
        
            if(State == STATE.GAME){
                tileMap.update();
                player.update();
                if(player.isDead()){
                    menu.setMenu();
                    State = STATE.DEAD;
                    player.setDead(false);
                }
            }
        }
        
    private void render(){
        
        /*g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);*/
           if(State == STATE.MENU || State == STATE.CHARACTER){
               menu.draw(g);
           }
           if(State == STATE.GAME){
            tileMap.draw(g);
            player.draw(g); 
           // obj.draw(g);
           }
           if(State == STATE.DEAD){
               g.drawImage(DeadMenu, 0, 0, this);
           }
        }
        
    private void draw(){
        
        //if(State == STATE.GAME){
            Graphics g2 = getGraphics();
            g2.drawImage(image, 0, 0, null);
            g2.dispose();
       // }
        
    }
    
    public void keyTyped(KeyEvent key){}
    
    public void keyPressed(KeyEvent key){
        
        int code = key.getKeyCode();
        if(State == STATE.MENU){
            if(code == KeyEvent.VK_UP){
                menu.moveUp();
                menu.setState("start");
            }
            if(code == KeyEvent.VK_DOWN){
                menu.moveDown();
                menu.setState("quit");
            }
            if(code == KeyEvent.VK_ENTER){
                if(menu.getState() == "start"){
                    State = STATE.CHARACTER;
                    menu.setMenuState("character");
                    code = -1;
                }
                if(menu.getState() == "quit"){
                    System.exit(1);
                  
                }
            }
        }
        /********CHARACTER STATE***********/
        if(State == STATE.CHARACTER){
            if(code == KeyEvent.VK_LEFT){
                menu.character("NinjaM");
            }
            if(code == KeyEvent.VK_RIGHT){
                menu.character("NinjaF");
            }
            if(code == KeyEvent.VK_ENTER){
                State = STATE.GAME;
            }
        }
        /********GAME STATE*************/
        if(State == STATE.GAME){
            if(code == KeyEvent.VK_LEFT){
                player.setLeft(true);
            }
            if(code == KeyEvent.VK_RIGHT){
                player.setRight(true);
            }
            if(code == KeyEvent.VK_UP){
                player.setJumping(true);
            }
            if(code == KeyEvent.VK_SPACE){
                player.setThrowing(true);
            }
        }
        if(State == STATE.DEAD){
            if(code == KeyEvent.VK_ENTER){
                player.setx(100);
                player.sety(100);
                State = STATE.GAME;
            }
            if(code == KeyEvent.VK_ESCAPE){
                State = STATE.MENU;
            }
        }
    }
    public void keyReleased(KeyEvent key){
        
        int code = key.getKeyCode();
        
        if(code == KeyEvent.VK_LEFT){
            player.setLeft(false);
        }
        if(code == KeyEvent.VK_RIGHT){
            player.setRight(false);
        }
        if(code == KeyEvent.VK_SPACE){
            player.setThrowing(false);
        }
        
    }
}

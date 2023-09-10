
package snake_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author yogendra singh
 */
public class GamePannel extends JPanel implements ActionListener,KeyListener
{
    // snake diraction--------------------------------------
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean right= true;
    //snake body postion---------------------------------------
    private int[] snake_x_length=new int[750];
    private int[] snake_y_length=new int[750];
    private int length_of_snake=3;
    private ImageIcon snake_title= new ImageIcon(ClassLoader.getSystemResource("icones//snaketitle.jpg"));
    
    //all faces of snake in 
    private ImageIcon left_mouth= new ImageIcon(ClassLoader.getSystemResource("icones//leftmouth.png"));
    private ImageIcon right_mouth = new ImageIcon(ClassLoader.getSystemResource("icones//rightmouth.png"));
    private ImageIcon up_mouth = new ImageIcon(ClassLoader.getSystemResource("icones//upmouth.png"));
    private ImageIcon down_mouth = new ImageIcon(ClassLoader.getSystemResource("icones//downmouth.png"));
    private ImageIcon snake_image = new ImageIcon(ClassLoader.getSystemResource("icones//snakeimage.png"));
    private ImageIcon enemy_image = new ImageIcon(ClassLoader.getSystemResource("icones//enemy.png"));
    //for first move--------------------
    private int moves =0;
    
    //for  moving the sleep---------------------------
    private Timer timer;
    private int delay=100;
    
    //enemy x and y postion
    private int[] enemy_x_postion = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,575,525,550,600,625,650,675,700,725,750,775,800,825,850}; 
    private int[] enemy_y_postion = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,575,525,550,600,625}; 
    // random class Object
    
    private Random random = new Random();
    // Array index
    private int enemy_x,enemy_y;
    private int score=0;
    // for next level-----------------------------------------
    private int next_level=10;
    private boolean game_over=false;
    private boolean high_score=false;
    public GamePannel() 
    {
        //add ActionListener.
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
         // create timer class Object---------------------------
        timer = new Timer(delay, this);//after the given time(delay) object call ActionListener again and again.
        timer.start();
        newEnemy();
    }

    @Override
    public void paint(Graphics g) 
    {
        //-------------- header part of pnannel
       g.setColor(Color.white);
       g.drawRect(24, 10, 851, 55);
       g.setColor(Color.black);
       
       
        //-------------main game panel
       g.setColor(Color.black);
       g.drawRect(24,74, 851, 576);
       snake_title.paintIcon(this, g, 24, 10);
       g.setFont(new Font("Arial", Font.BOLD, 19));
       g.drawString("score: "+score, 700, 30);
       g.setFont(new Font("Arial", Font.BOLD, 19));
       g.drawString("length: "+length_of_snake, 700, 50);
       
       g.fillRect(25,75, 850, 575);
      
       //------------draw snake 
       
       if(moves==0)
       {
           // all the snake head and its circle have 25px length
           snake_x_length[0]=100;
           snake_x_length[1]=75;
           snake_x_length[2]=50;
           snake_y_length[0]=100;
           snake_y_length[1]=100;
           snake_y_length[2]=100;
          
       }
       if(left)
       {
           left_mouth.paintIcon(this, g, snake_x_length[0], snake_y_length[0]);
       }
       if(right)
       {
           right_mouth.paintIcon(this, g, snake_x_length[0], snake_y_length[0]);
       }
       if(up)
       {
           up_mouth.paintIcon(this, g, snake_x_length[0], snake_y_length[0]);
       }
       if(down)
       {
           down_mouth.paintIcon(this, g, snake_x_length[0], snake_y_length[0]);
       }
       for(int i =1;i<length_of_snake;i++)
       {
           snake_image.paintIcon(this, g, snake_x_length[i], snake_y_length[i]);
           // System.out.println(snake_x_length[i]);
           //System.out.println(snake_y_length[i]);
       }
       
       enemy_image.paintIcon(this, g, enemy_x, enemy_y);
       if(game_over)
       {
           g.setFont(new Font("system", Font.BOLD, 30));
           g.setColor(Color.red);
           g.drawString("GAME OVER ", 350, 350);
           
           g.setFont(new Font("system", Font.BOLD, 25));
           g.setColor(Color.white);
           g.drawString("tap space for restart", 350, 400);
           if(high_score)
           {
                g.setFont(new Font("system", Font.BOLD, 25));
                g.setColor(Color.GREEN);
                g.drawString("New High Score: "+ score, 350, 300);
           }
       }
       g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        for(int i = length_of_snake-1;i>0;i--)
        {
            snake_x_length[i]=snake_x_length[i-1];
            snake_y_length[i]=snake_y_length[i-1];
        }
        if(left)
        {
            snake_x_length[0]=snake_x_length[0]-25;
        }
        if(right)
        {
            snake_x_length[0]= snake_x_length[0]+25;
        }
        if(up)
        {
            snake_y_length[0] = snake_y_length[0]-25;
        }
        if(down)
        {
            snake_y_length[0] = snake_y_length[0]+25;
        }
       
        // for boundry condtion of snake
        
        if(snake_x_length[0]>850)
            snake_x_length[0]=25;
        if(snake_x_length[0]<25)
            snake_x_length[0]=850;
        
        if(snake_y_length[0]>625)
            snake_y_length[0]=75;
        if(snake_y_length[0]<75)
            snake_y_length[0]=625;
        
        collidsWithEnemy();
        gameOver();
        
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {  }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(e.getKeyCode()==KeyEvent.VK_UP && !down)
        {
                left=false;
                up=true;
                down=false;
                right=false; 
                moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && !up)
        {
                left=false;
                up=false;
                down=true;
                right=false; 
                moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT && !right)
        {
                left=true;
                up=false;
                down=false;
                right=false;
                moves++; 
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && !left)
        {
                left=false;
                up=false;
                down=false;
                right=true;
                moves++; 
        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE)
        {
            restart();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {  }

    // for providing enemy coordinate.
    private void newEnemy()
    {
      enemy_x = enemy_x_postion[random.nextInt(34)];
      enemy_y = enemy_y_postion[random.nextInt(23)];
    
    }

    // this method track collide between enemy and snake
    private void collidsWithEnemy()
    {
        if(snake_x_length[0]==enemy_x && snake_y_length[0]==enemy_y)
        {
            newEnemy();
            length_of_snake++;
            score++;
        }
    }
    private void gameOver()
    {
        for(int i=1;i<length_of_snake;i++)
        {
            if(snake_x_length[0]==snake_x_length[i] && snake_y_length[0]==snake_y_length[i])
            {
                timer.stop();
                game_over = true;
                // score inserted into the database.
                try
                {
                    Connection con1 = dbconnection.DBConnection.getConnection();
                    PreparedStatement ps1 = con1.prepareStatement("select * from snake");
                    ResultSet rs = ps1.executeQuery();
                    rs.next();
                    int previous_score =rs.getInt(1);
                    if(previous_score<score)
                    {
                        Connection con2 = dbconnection.DBConnection.getConnection();
                        PreparedStatement ps2 = con2.prepareStatement("update snake set score=? where score=?"); 
                        ps2.setInt(1, score);
                        ps2.setInt(2, previous_score);
                        int a = ps2.executeUpdate();
                        if(a>0)
                        {
                            high_score=true;
                        }
                    }
                    
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }   
        }
    }

    private void restart() 
    {
        left=false;
        right = true;
        down = false;
        up = false;
        length_of_snake=3;
        score=0;
        moves=0;
        game_over=false;
        timer.start();
        repaint();
    }
}

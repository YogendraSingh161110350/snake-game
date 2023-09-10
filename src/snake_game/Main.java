
package snake_game;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author 91740
 */
public class Main
{

    public Main()
    {
        
    }
    
    public static void main(String[] args) 
    {
        JFrame jf = new JFrame();
        jf.setTitle("Snake Game");
        jf.setBounds(450, 200, 905, 700);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jf.setLayout(null);
        jf.setResizable(false);
        GamePannel gp = new GamePannel();
        gp.setBackground(Color.BLACK);
        jf.add(gp);
        jf.setVisible(true);
    }
}

package CirclePacking;

import javax.swing.*;
import java.awt.*;

public class Circle extends JPanel {
    int x;
    int y;
    int r;
    boolean growing = true;
    Color color;

    Circle(int x_, int y_,Color color){
        this.x = x_;
        this.y = y_;
        this.r = 1;
        this.color = color;
    }

    public void update(){
        if(growing){
            r+=1;
            if(r>5)r=5;
        }
        repaint();
    }

    boolean edges(int width, int height){
        return  (x+r > width || x-r < 0 || y+r > height || y-r < 0);
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g2d.fillOval(x-r, y-r, r*2, r*2);
    }

}

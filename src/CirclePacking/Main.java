package CirclePacking;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Circle Packing Animation");
        CirclePackingAnimation cpa = new CirclePackingAnimation();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);

        frame.setLayout(new BorderLayout());
        frame.add(cpa,BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}

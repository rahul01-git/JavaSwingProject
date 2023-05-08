package PuzzleJava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Home implements ActionListener {
    JFrame frame = new JFrame();
    JButton easy = new JButton("Easy Level");
    JButton medium = new JButton("Medium Level");
    JButton hard = new JButton("Hard Level");
    JLabel label = new JLabel("Puzzle Game");

    Home ()
    {
        frame.setSize(500,400);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        easy.setBounds(160,100,130,30);
        medium.setBounds(160,150,130,30);
        hard.setBounds(160,200,130,30);

        easy.setFocusable(false);
        medium.setFocusable(false);
        hard.setFocusable(false);

        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);

        label.setBounds(120,10,320,30);
        label.setFont(new Font("Arial",Font.BOLD,35));
        label.setForeground(Color.RED);



        frame.add(label);
        frame.add(easy);
        frame.add(medium);
        frame.add(hard);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easy) {
            int row = 2, col = 2;
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File img = chooser.getSelectedFile();
            frame.dispose();
            try {
                Puzzle puzzle = new Puzzle(row,col,img);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }



        if (e.getSource() == medium) {
            int row = 3, col = 3;
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File img = chooser.getSelectedFile();
            frame.dispose();
            try {
                Puzzle puzzle = new Puzzle(row,col,img);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


        if (e.getSource() == hard) {
            int row = 4, col = 4;
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File img = chooser.getSelectedFile();
            frame.dispose();
            try {
                Puzzle puzzle = new Puzzle(row,col,img);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}

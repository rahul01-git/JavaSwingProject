package CirclePacking;

import MainClass.MainMenu;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;


public class CirclePackingAnimation extends JPanel {
    ArrayList<Circle> circles;
    Random random = new Random();
    private BufferedImage image;
    boolean shouldRepaint = true;
    private JSlider totalSlider;
    BackgroundMusic backgroundMusic = new BackgroundMusic();
    private int total=10;
    private boolean resetRequested = false;

    public CirclePackingAnimation() {
        setBackground(Color.BLACK);
        while (image == null){
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter  filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedImage tempImage = ImageIO.read(selectedFile);
                    image = resizeImage(tempImage, 800, 600);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        backgroundMusic.playMusic();
        setup();
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resized;
    }

    void setup(){
        circles = new ArrayList<Circle>();
        setLayout(new BorderLayout());
        JPanel sliderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalSlider = new JSlider(JSlider.HORIZONTAL,10,360,10);
        totalSlider.setMinorTickSpacing(10);
        totalSlider.setMajorTickSpacing(50);
        totalSlider.setPaintTicks(true);
        totalSlider.setPaintLabels(true);

        JLabel totalLabel = new JLabel("Circles Generation Rate = " + totalSlider.getValue()+"\n");
        totalSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                int total = source.getValue();
                setTotal(total);
                repaint();
                totalLabel.setText("Circles Generation Rate = " + total +"\n");
                totalSlider.setEnabled(shouldRepaint);
            }
        });

        sliderPanel.add(totalLabel,BorderLayout.EAST);
        sliderPanel.add(totalSlider,BorderLayout.CENTER);
        add(sliderPanel,BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (resetRequested) {
            g.clearRect(0, 0, getWidth(), getHeight());
            resetRequested = false;
        }

        int count = 0;
        int attempts = 0;
        while (count<total){
            Circle newC = newCircle();
            if(newC != null){
                circles.add(newC);
                count++;
            }
            attempts++;
            if(attempts>1000){
                System.out.println("finished");
                shouldRepaint = false;
                backgroundMusic.stopMusic();
                break;
            }
        }
        for (Circle c : circles) {
            if (c.growing){
                if(c.edges(image.getWidth(), image.getHeight())){
                    c.growing=false;
                }else{
                    for(Circle other : circles){
                        if(c != other){
                            double d = dist(c.x,c.y,other.x,other.y);
                            if(d<c.r + other.r){
                                c.growing = false;
                                break;
                            }
                        }
                    }
                }
            }

            c.update();
            c.paint(g);
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(shouldRepaint)
            repaint();
        else{
            backgroundMusic.stopMusic();
            JOptionPane.showMessageDialog(this, "Animation Finished");

            SwingUtilities.invokeLater(() -> {
                JFrame circlePackingFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                circlePackingFrame.dispose(); // Close the CirclePackingAnimation frame

                MainMenu.main(new String[]{});
            });
        }
    }


    private Circle newCircle() {
        int x = random.nextInt(image.getWidth());
        int y = random.nextInt(image.getHeight());
        Color color = new Color(image.getRGB(x, y), true);
        boolean valid = true;
        for (Circle c : circles) {
            double d = dist(x, y, c.x, c.y);
            if(d < c.r){
                valid = false;
                break;
            }
        }

        if(valid){
            return new Circle(x,y,color);
        }else return null;
    }

    public static double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void setTotal(int total){
        this.total = total;
    }

}

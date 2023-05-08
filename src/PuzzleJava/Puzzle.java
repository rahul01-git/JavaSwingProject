package PuzzleJava;
import MainClass.MainMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.geom.AffineTransform;

public class Puzzle extends Component implements MouseListener, ActionListener {
    JFrame jFrame = new JFrame("PuzzleJava");
    JLabel label = new JLabel("Puzzle Game");

    JButton upload = new JButton("UPLOAD IMAGE");

    JLabel originalImage = new JLabel();
    ImageIcon imageIcon ;  //= new ImageIcon("C:\\Game_Project\\Game_Puzzle\\src\\image.jpg");
    JButton shuffle = new JButton("Shuffle");
    BufferedImage bufferedImage; // = ImageIO.read(new File("C:\\Game_Project\\Game_Puzzle\\src\\image.jpg"));

   // int rows, cols;

    ArrayList<JLabel> labelList = new ArrayList<JLabel>();
    JLabel lastLabel = null;
    JLabel emptyLabel = null;

    JPanel puzzlePanel;
    private final BufferedImage[] pieces;
    private final int rows;
    private final int cols;
    JLabel pictureBox = new JLabel();

    Puzzle(int rows,int cols,File imageFile) throws IOException,NullPointerException {

        jFrame.setResizable(false);
        //jFrame.setLocation(5,10);
        jFrame.setLayout(null);


        bufferedImage = ImageIO.read(imageFile);




        bufferedImage = resizeImage(bufferedImage, 600, 600);

        imageIcon = new ImageIcon(bufferedImage);
        jFrame.setSize(bufferedImage.getWidth()*2+200, bufferedImage.getHeight()+200);

        this.rows = rows;
        this.cols = cols;
        this.pieces = new BufferedImage[rows * cols];



          int puzzlePanel_height = bufferedImage.getHeight();
          int puzzlePanel_width = bufferedImage.getWidth();

        int subImage_width = puzzlePanel_width/cols;
        int subImage_height = puzzlePanel_height/rows;


        int currentImage = 0;
         puzzlePanel = new JPanel(new GridLayout(rows, cols));
        puzzlePanel.setBounds(60, 60, puzzlePanel_width, puzzlePanel_height);
        puzzlePanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));





        label.setForeground(Color.RED);
        label.setBounds(150, 10, 300, 60);
        label.setFont(new Font("Arial", Font.BOLD, 45));

        originalImage.setIcon(imageIcon);
        originalImage.setBounds(puzzlePanel_width + 150, 60, puzzlePanel_width, puzzlePanel_height);

        shuffle.setBounds(puzzlePanel_width / 2 , 700, 200, 30);
        shuffle.setForeground(Color.YELLOW);
        shuffle.setBackground(Color.red);
        shuffle.setFocusable(false);
        shuffle.setFont(new Font("Arial", Font.BOLD, 20));





        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pieces[currentImage] = new BufferedImage(subImage_width, subImage_height, bufferedImage.getType());
                Graphics2D imageCreator = pieces[currentImage].createGraphics();

                int src_first_x = subImage_width * j;
                int src_first_y = subImage_height * i;
                int dst_corner_x = subImage_width * j + subImage_width;
                int dst_corner_y = subImage_height * i + subImage_height;

                double scaleX = (double) subImage_width / (double) (dst_corner_x - src_first_x);
                double scaleY = (double) subImage_height / (double) (dst_corner_y - src_first_y);
                AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
                imageCreator.setTransform(scaleTransform);

                imageCreator.drawImage(bufferedImage, 0, 0, subImage_width, subImage_height, src_first_x, src_first_y, dst_corner_x, dst_corner_y, null);

                JLabel label = new JLabel(new ImageIcon(pieces[currentImage]));
                label.setBorder(BorderFactory.createLineBorder(Color.red));
                label.setPreferredSize(new Dimension(subImage_width, subImage_height));

                shuffle.addActionListener(this);
                pictureBox.setBounds(500, 100, 300, 200);
                labelList.add(label);

                puzzlePanel.add(label);
                currentImage++;
                lastLabel = label;
            }
        }

        Shuffle();
        jFrame.add(pictureBox);
        jFrame.add(puzzlePanel);
        jFrame.add(shuffle);
        jFrame.add(originalImage);
        jFrame.add(label);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

    }





    public void Shuffle() {
//        lastLabel = labelList.get(labelList.size() - 1);
//        emptyLabel = labelList.get(labelList.indexOf(null));

        Collections.shuffle(labelList);


        for (int i = 0; i < (labelList.size()); i++) {
            JLabel label = labelList.get(i);


        //label.addKeyListener(this);
            label.requestFocus();

            label.addMouseListener(this);

            puzzlePanel.add(label);
        }

        removeLastSubImage();
        puzzlePanel.revalidate();
        puzzlePanel.repaint();
    }


    public void removeLastSubImage() {
        if (lastLabel != null && puzzlePanel.getComponentZOrder(lastLabel) != -1) {
            int indexToRemove = labelList.indexOf(lastLabel);
            lastLabel = null; // reset the last label added to the panel
            emptyLabel = null;
            for (int i = labelList.size() - 1; i >= 0; i--) {
                JLabel label = labelList.get(i);
                if (puzzlePanel.getComponentZOrder(label) != -1) {
                    emptyLabel = label;
                    emptyLabel.setIcon(null);
                    break;
                }
            }
            puzzlePanel.revalidate();
            puzzlePanel.repaint();
            checkWin(indexToRemove);
        }
    }

    public void checkWin(int removedIndex) {
        boolean solved = true;

        for (int i = 0; i < labelList.size() - 1; i++) {
            if (i == removedIndex) {
                continue;
            }
            JLabel label = labelList.get(i);
            ImageIcon icon = (ImageIcon) label.getIcon();
            if (icon != null) {
                BufferedImage image = (BufferedImage) icon.getImage();
                try {
                    BufferedImage original = this.pieces[i];
                    if (!original.equals(image)) {
                        solved = false;
                        break;
                    }
                }
                catch (Exception abc){
                    System.out.println("error");
                }
            } else {
                solved = false;
                break;
            }
        }

        if (solved) {
            JOptionPane.showMessageDialog(jFrame, "You win!");

            Timer timer = new Timer(2000, (e) -> {
                jFrame.dispose(); // Close the Puzzle frame
                MainMenu.main(new String[]{});
            });
            timer.setRepeats(false); // Only run the timer once
            timer.start();
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel clickedLabel = (JLabel) e.getSource();
        int clickedIndex = labelList.indexOf(clickedLabel);
        int emptyIndex = labelList.indexOf(emptyLabel);

        int clickedRow = clickedIndex / cols;
        int clickedCol = clickedIndex % cols;
        int emptyRow = emptyIndex / cols;
        int emptyCol = emptyIndex % cols;

        if ((clickedRow == emptyRow && Math.abs(clickedCol - emptyCol) == 1) ||
                (clickedCol == emptyCol && Math.abs(clickedRow - emptyRow) == 1)) {
            // Swap the subImages
            ImageIcon tempIcon = (ImageIcon) emptyLabel.getIcon();
            emptyLabel.setIcon(clickedLabel.getIcon());
            clickedLabel.setIcon(tempIcon);
            emptyLabel = clickedLabel;
        }
        checkWin(clickedIndex);
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == shuffle)
        {
            Shuffle();
        }

        if (e.getSource() == upload) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG & GIF Images", "jpg", "png", "gif");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedImage image = ImageIO.read(selectedFile);
                    image = resizeImage(image, 600, 600);
                    imageIcon.setImage(image);
                    originalImage.setIcon(imageIcon);
                    bufferedImage = image;

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }



    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resized;
    }
}




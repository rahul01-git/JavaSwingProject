package MainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import java.io.IOException;

public class MainMenu extends JFrame implements ActionListener {
    private JButton circlePackingButton;
    private JButton puzzleButton;
    private JLabel titleLabel;

    public MainMenu() {
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        setLocationRelativeTo(null);

        // Create the label
        titleLabel = new JLabel("Choose Project");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create buttons
        circlePackingButton = new JButton("Circle Packing");
        puzzleButton = new JButton("Puzzle");

        // Add action listeners
        circlePackingButton.addActionListener(this);
        puzzleButton.addActionListener(this);

        // Create panel and add components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(titleLabel);
        panel.add(circlePackingButton);
        panel.add(puzzleButton);

        // Add panel to frame
        getContentPane().add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == circlePackingButton) {
            runCirclePacking();
        } else if (e.getSource() == puzzleButton) {
            try {
                runPuzzle();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void runCirclePacking() {
        SwingUtilities.invokeLater(() -> {
            this.dispose();
            CirclePacking.Main.main(new String[]{});
        });
    }

    private void runPuzzle() throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                this.dispose();
                PuzzleJava.Main.main(new String[]{});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        });
    }
}

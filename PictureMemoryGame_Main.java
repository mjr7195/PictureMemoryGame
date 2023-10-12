import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class PictureMemoryGame_Main extends JFrame {
    private ArrayList<String> imagePaths;
    private ArrayList<String> cardImages;
    private JButton[] cardButtons;
    private String[] imageNames = {"Photo1.JPG","Photo2.JPG", "Photo3.JPG", "Photo4.JPG", "Photo5.jpg", "Photo6.jpg", "Photo7.jpg", "Photo8.jpg", "Photo9.jpg", "Photo10.jpg", "Photo11.jpg", "Photo12.jpg",
                                   "Photo1.JPG","Photo2.JPG", "Photo3.JPG", "Photo4.JPG", "Photo5.jpg", "Photo6.jpg", "Photo7.jpg", "Photo8.jpg", "Photo9.jpg", "Photo10.jpg", "Photo11.jpg", "Photo12.jpg"};
    private int numberOfMatches;
    private int firstCardIndex = -1;
    private int secondCardIndex;

    public PictureMemoryGame_Main(){
        setTitle("Picture Memory Game");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imagePaths = new ArrayList<>();
        // add file paths to images
        imagePaths.addAll(Arrays.asList(imageNames));

        cardImages = new ArrayList<>();
        for (String imagePath : imagePaths) {
            cardImages.add("");
        }

        Collections.shuffle(imagePaths);
        Collections.shuffle(cardImages);

        JPanel cardPanel = new JPanel(new GridLayout(12,12));
        cardButtons = new JButton[24];

        for (int i = 0; i < cardButtons.length; i++) {
            final int index = i;
            cardButtons[i] = new JButton();
            cardButtons[i].setIcon(new ImageIcon("CardCoverQuestionMark.png"));
            cardButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleCardClick(index);
                }
            });
            cardPanel.add(cardButtons[i]);
        }
        add(cardPanel);
    }

    private void handleCardClick(int index) {
        if (cardButtons[index].getIcon() == null) {
            return;
        }

        if (firstCardIndex == -1) {
            firstCardIndex = index;
            cardButtons[firstCardIndex].setIcon(new ImageIcon(imagePaths.get(index)));
        } else {
            secondCardIndex = index;
            cardButtons[secondCardIndex].setIcon(new ImageIcon(imagePaths.get(index)));

            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (imagePaths.get(firstCardIndex).equals(imagePaths.get(secondCardIndex))) {
                        cardButtons[firstCardIndex].setIcon(null);
                        cardButtons[secondCardIndex].setIcon(null);
                        cardImages.set(firstCardIndex, null);
                        cardImages.set(secondCardIndex, null);
                        numberOfMatches++;

                        if (numberOfMatches == imagePaths.size() / 2) {
                            JOptionPane.showMessageDialog(null, "Congrats! You Won!!!");
                            System.exit(0);
                        }
                    } else {
                        cardButtons[firstCardIndex].setIcon(new ImageIcon("CardCoverQuestionMark.png"));
                        cardButtons[secondCardIndex].setIcon(new ImageIcon("CardCoverQuestionMark.png"));
                    }
                    firstCardIndex = -1;
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PictureMemoryGame_Main().setVisible(true);
            }
        });
    }



}

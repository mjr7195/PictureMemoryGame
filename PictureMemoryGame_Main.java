import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.time.Duration;


public class PictureMemoryGame_Main extends JFrame {
    Instant startTime = Instant.now();
    private ArrayList<String> imagePaths;
    private ArrayList<String> cardImages;
    private JButton[] cardButtons;
    private String[] imageNames = {"Photo1.JPG","Photo2.JPG", "Photo3.JPG", "Photo4.JPG", "Photo5.jpg", "Photo6.jpg", "Photo7.jpg", "Photo8.jpg", "Photo9.jpg", "Photo10.jpg", "Photo11.jpg", "Photo12.jpg",
                                   "Photo1.JPG","Photo2.JPG", "Photo3.JPG", "Photo4.JPG", "Photo5.jpg", "Photo6.jpg", "Photo7.jpg", "Photo8.jpg", "Photo9.jpg", "Photo10.jpg", "Photo11.jpg", "Photo12.jpg"};
    private String[] image6Names = {"Photo1.JPG","Photo2.JPG", "Photo3.JPG", "Photo4.JPG", "Photo5.jpg", "Photo6.jpg",
                                    "Photo1.JPG","Photo2.JPG", "Photo3.JPG", "Photo4.JPG", "Photo5.jpg", "Photo6.jpg"};
    private int numberOfMatches;
    private int firstCardIndex = -1;
    private int secondCardIndex;

    JFrame frame = new JFrame();

    public PictureMemoryGame_Main(){
        setTitle("Picture Memory Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imagePaths = new ArrayList<>();

        String cardNum = JOptionPane.showInputDialog("How many cards do you want to play with?");
        int cardNumber = Integer.parseInt(cardNum);
        while(cardNumber != 24 && cardNumber != 12) {
            cardNum = JOptionPane.showInputDialog("Invalid card amount. Please enter 12 or 24 cards?");
            cardNumber = Integer.parseInt(cardNum);
        }
        if (cardNumber == 24) {
            // add file paths to images
            imagePaths.addAll(Arrays.asList(imageNames));
        }
        if (cardNumber == 12) {
            imagePaths.addAll(Arrays.asList(image6Names));
        }

        cardImages = new ArrayList<>();
        for (String imagePath : imagePaths) {
            cardImages.add("");
        }

        Collections.shuffle(imagePaths);
        Collections.shuffle(cardImages);

        JPanel cardPanel = new JPanel(new GridLayout(5,5));
        cardButtons = new JButton[cardNumber];

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
        Instant startTime = Instant.now();
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
                            JOptionPane.showMessageDialog(null, "You took "+ timeTaken +" seconds to complete game");
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
    Instant endTime = Instant.now();
    Duration timeTaken = Duration.between(startTime,endTime);

}

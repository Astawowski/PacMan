import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TypeInYourScore extends JFrame {

    public TypeInYourScore(int previousScore) {
        super("Pac-Man Record yourself!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setLocation(500, 500);

        JPanel BackGroundpanel = new JPanel();
        BackGroundpanel.setLayout(new BoxLayout(BackGroundpanel, BoxLayout.Y_AXIS));
        this.add(BackGroundpanel);

        FontUIResource font = new FontUIResource("Arial", FontUIResource.PLAIN, 24);

        JLabel Label4score = new JLabel("Your score: "+previousScore+"  Type your nick!");
        Label4score.setFont(font);
        Label4score.setAlignmentX(CENTER_ALIGNMENT);
        BackGroundpanel.add(Label4score);

        JTextField Field4Nick = new JTextField();
        Field4Nick.setFont(font);
        Field4Nick.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, Field4Nick.getPreferredSize().height));
        BackGroundpanel.add(Field4Nick);

        JButton saveButton = new JButton("Save");
        saveButton.setFont(font);
        saveButton.setAlignmentX(CENTER_ALIGNMENT);
        BackGroundpanel.add(saveButton);

        saveButton.addActionListener(e -> {
            String nick = Field4Nick.getText();
            saveScore(previousScore, nick);
            this.dispose();
            SwingUtilities.invokeLater(MainMenuScreen::new);
        });
        this.setVisible(true);
    }

    private void saveScore(int score, String nick) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./Data/Ranking.txt", true))) {
            writer.write("Score: "+score+" Nick: "+nick+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

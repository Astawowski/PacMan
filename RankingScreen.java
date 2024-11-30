import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.event.ActionEvent; // mus to jest
import java.io.*;
import java.util.ArrayList;

public class RankingScreen extends JFrame {

    public RankingScreen() {
        super("Pac-Man Ranking   -ESC to return-");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 400);
        this.setLocation(500, 500);

        ArrayList<String> rankings = LoaderAndRankingAnalyzer("./Data/Ranking.txt");
        String[] rankingScoresArray = rankings.toArray(new String[0]);
        JList<String> rankingList = new JList<>(rankingScoresArray);

        FontUIResource font = new FontUIResource("Arial", FontUIResource.PLAIN, 18);
        rankingList.setFont(font);

        JScrollPane scrolls = new JScrollPane(rankingList);
        this.add(scrolls);

        addEscKeyListener();
        this.setVisible(true);
    }

    private void addEscKeyListener() {
        JRootPane rootPaneElement = this.getRootPane();
        rootPaneElement.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "ESCAPE");
        rootPaneElement.getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(MainMenuScreen::new);
            }
        });
    }

    private ArrayList<String> LoaderAndRankingAnalyzer(String Path) {
        ArrayList<String> ranking = new ArrayList<>();
        try (BufferedReader Filereader = new BufferedReader(new FileReader(Path))) {
            String line;
            while ((line = Filereader.readLine()) != null) {
                ranking.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ranking.sort((r1, r2) -> {
            int score1st = Integer.parseInt(r1.split(" ")[1].trim());
            int score2nd = Integer.parseInt(r2.split(" ")[1].trim());
            return Integer.compare(score2nd, score1st);
        });

        return ranking;
    }
}

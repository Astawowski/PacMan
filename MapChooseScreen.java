import javax.swing.*;
import java.awt.*;

public class MapChooseScreen extends MyFrame {
        // --- Stuff
        private JComboBox<String> mapComboBox;
        private JLabel MainPromptText;
        private JButton backButton;
        private JButton goButton;
        private int MapLayoutChosen;
        private JLabel mapPreviewLabel;
        private ImageIcon mapPreviewIcon;
        // --- Threads
        private FrameCenterPointUpdater centerPointUpdater;
        private MapChooseStuffPositionUpdater stuffPositionsUpdater;
        private Thread centerPointUpdaterThread;
        private Thread stuffPositionsUpdaterThread;

        public MapChooseScreen() {
                super("Pac-Man Choose Map");
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                stopUpdaters();
                                System.exit(0);
                        }
                });
                this.setLocationRelativeTo(null);
                this.setLayout(null);
                this.setSize(1920, 1080);
                this.setMinimumSize(new Dimension(1290, 970));
                // --- Starting the thread setting current frame center point
                this.CurrentCenterX = this.getWidth() / 2;
                this.CurrentCenterY = this.getHeight() / 2;
                startCenterPointUpdater();

                // --- Setting the frame background to black
                JPanel BackGroundPanel = new JPanel();
                BackGroundPanel.setLayout(null);
                BackGroundPanel.setBackground(new java.awt.Color(0, 0, 0));
                this.setContentPane(BackGroundPanel);

                // --- On default MapLayoutChosen = 1
                this.MapLayoutChosen = 1;
                // --- Map Combo Box for map layouts options
                this.mapComboBox = new JComboBox<>(Data.DataMapLayoutsList);
                this.mapComboBox.setBounds(CurrentCenterX - 650, CurrentCenterY - 200, 300, 60);
                this.mapComboBox.setFont(new javax.swing.plaf.FontUIResource("Arial", javax.swing.plaf.FontUIResource.BOLD, 45));
                // --- Switching the preview & updating main variable
                mapComboBox.addActionListener(e -> {
                        this.MapLayoutChosen = this.mapComboBox.getSelectedIndex() + 1;
                        updateMapPreview();
                });
                this.add(mapComboBox);

                // --- Map Preview
                this.mapPreviewIcon = Data.DataMap1Image;
                mapPreviewLabel = new JLabel();
                mapPreviewLabel.setIcon(this.mapPreviewIcon);
                mapPreviewLabel.setBounds(CurrentCenterX - 177, CurrentCenterY - 275, this.mapPreviewIcon.getIconWidth(), this.mapPreviewIcon.getIconHeight());
                this.add(mapPreviewLabel);

                // --- Main Prompt Text ("Choose map ..." etc)
                this.MainPromptText = new JLabel("Choose Map Layout");
                this.MainPromptText.setBounds(CurrentCenterX - 350, CurrentCenterY - 500, 500, 70);
                this.MainPromptText.setFont(new javax.swing.plaf.FontUIResource("Arial", javax.swing.plaf.FontUIResource.BOLD, 45));
                this.MainPromptText.setForeground(java.awt.Color.WHITE);
                this.MainPromptText.setHorizontalAlignment(SwingConstants.CENTER);
                this.add(this.MainPromptText);

                // --- Button to go back to main screen
                backButton = new JButton("BACK");
                backButton.setBounds(CurrentCenterX - 617, CurrentCenterY, 150, 70);
                backButton.setFont(new javax.swing.plaf.FontUIResource("Arial", javax.swing.plaf.FontUIResource.BOLD, 40));
                this.backButton.addActionListener(e -> {
                        int CurrentFrameX = this.getLocation().x;
                        int CurrentFrameY = this.getLocation().y;
                        stopUpdaters();
                        this.dispose();
                        MainMenuScreen mainMenuScreen = new MainMenuScreen();
                        mainMenuScreen.setLocation(CurrentFrameX, CurrentFrameY);
                        mainMenuScreen.setVisible(true);
                });
                this.add(backButton);

                // --- Button to begin the game
                goButton = new JButton("GO");
                goButton.setBounds(CurrentCenterX - 42, CurrentCenterY, 100, 70);
                goButton.setFont(new javax.swing.plaf.FontUIResource("Arial", javax.swing.plaf.FontUIResource.BOLD, 40));
                this.goButton.addActionListener(e -> {
                        int CurrentFrameX = this.getLocation().x;
                        int CurrentFrameY = this.getLocation().y;
                        stopUpdaters();
                        this.dispose();
                        MainGameScreen gameScreen = new MainGameScreen(this.MapLayoutChosen, 0, 4);
                        gameScreen.setLocation(CurrentFrameX, CurrentFrameY);
                        gameScreen.setVisible(true);
                });
                this.add(goButton);

                // --- Displaying the frame
                this.setVisible(true);
                // --- Starting the thread updating stuff positions
                startStuffPositionsUpdater();
        }

        // --- Function to update current map preview
        private void updateMapPreview() {
                switch (MapLayoutChosen) {
                        case 1:
                                this.mapPreviewIcon = Data.DataMap1Image;
                                break;
                        case 2:
                                this.mapPreviewIcon = Data.DataMap2Image;
                                break;
                        case 3:
                                this.mapPreviewIcon = Data.DataMap3Image;
                                break;
                        case 4:
                                this.mapPreviewIcon = Data.DataMap4Image;
                                break;
                        default:
                                this.mapPreviewIcon = Data.DataMap5Image;
                }
                mapPreviewLabel.setIcon(this.mapPreviewIcon);
                mapPreviewLabel.setBounds(CurrentCenterX - 177, CurrentCenterY - 275, this.mapPreviewIcon.getIconWidth(), this.mapPreviewIcon.getIconHeight());
        }

        // --- Functions to start threads
        private void startCenterPointUpdater() {
                centerPointUpdater = new FrameCenterPointUpdater(this, this.CurrentCenterX, this.CurrentCenterY);
                centerPointUpdaterThread = new Thread(centerPointUpdater);
                centerPointUpdaterThread.start();
        }

        private void startStuffPositionsUpdater() {
                stuffPositionsUpdater = new MapChooseStuffPositionUpdater(this);
                stuffPositionsUpdaterThread = new Thread(stuffPositionsUpdater);
                stuffPositionsUpdaterThread.start();
        }

        // --- Function to force threads stop
        private void stopUpdaters() {
                if (centerPointUpdater != null) {
                        centerPointUpdater.stop();
                        try {
                                centerPointUpdaterThread.join();
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                        }
                }
                if (stuffPositionsUpdater != null) {
                        stuffPositionsUpdater.stop();
                        try {
                                stuffPositionsUpdaterThread.join();
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                        }
                }
        }




// --- Getters/Setters
        public JComboBox<String> getMapComboBox() {
                return mapComboBox;
        }
        public void setMapComboBox(JComboBox<String> mapComboBox) {
                this.mapComboBox = mapComboBox;
        }
        public JLabel getMainPromptText() {
                return MainPromptText;
        }
        public void setMainPromptText(JLabel mainPromptText) {
                MainPromptText = mainPromptText;
        }
        public JButton getBackButton() {
                return backButton;
        }
        public void setBackButton(JButton backButton) {
                this.backButton = backButton;
        }
        public JButton getGoButton() {
                return goButton;
        }
        public void setGoButton(JButton goButton) {
                this.goButton = goButton;
        }
        public JLabel getMapPreviewLabel() {
                return mapPreviewLabel;
        }
        public void setMapPreviewLabel(JLabel mapPreviewLabel) {
                this.mapPreviewLabel = mapPreviewLabel;
        }
        public ImageIcon getMapPreviewIcon(){
                return this.mapPreviewIcon;
        }


}

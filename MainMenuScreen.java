import javax.swing.*;

public class MainMenuScreen extends MyFrame {
    // --- Stuff
    private JButton StartNewGameButton;
    private JButton HighScoresButton;
    private JButton ExitButton;
    private JLabel PackManLogoLabel;
    // --- Threads
    private Thread centerPointUpdaterThread;
    private Thread stuffPositionsUpdaterThread;
    private FrameCenterPointUpdater centerPointUpdater;
    private MainMenuStuffPositionUpdater stuffPositionsUpdater;

    public MainMenuScreen() {
        // --- Setting the frame basic settings
        super("Pac-Man - Main Menu");
        this.setSize(1920, 1080);
        this.setMinimumSize(new javax.swing.plaf.DimensionUIResource(1250, 1100));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // --- Stopping threads when exiting
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                stopUpdaters();
                System.exit(0);
            }
        });
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        // --- Constantly updating current frame center point position
        this.CurrentCenterX = this.getWidth() / 2;
        this.CurrentCenterY = this.getHeight() / 2;
        startCenterPointUpdater();

        // --- Setting the frame background to black
        JPanel BackGroundPanel = new JPanel();
        BackGroundPanel.setLayout(null);
        BackGroundPanel.setBackground(new java.awt.Color(0, 0, 0));
        this.setContentPane(BackGroundPanel);


    // ----------- Buttons ---------------
        // --- Creating Main buttons
        this.StartNewGameButton = createButton("Start New Game", Data.DataButtonIcon, CurrentCenterX, CurrentCenterY);
        this.HighScoresButton = createButton("High Scores",Data.DataButtonIcon, CurrentCenterX , CurrentCenterY);
        this.ExitButton = createButton("Exit", Data.DataButtonIcon, CurrentCenterX, CurrentCenterY);

        // --- Adding buttons to the frame
        this.add(this.StartNewGameButton);
        this.add(this.HighScoresButton);
        this.add(this.ExitButton);

        // --- Adding action Listeners
        this.StartNewGameButton.addActionListener(e -> startNewGamePressed());
        this.HighScoresButton.addActionListener(e -> showHighScoresPressed());
        this.ExitButton.addActionListener(e -> exitGamePressed());

        // --- Pack Man Logo ---
        JLabel PacManLogoLabel = new JLabel(Data.DataPacManLogoIcon);
        PacManLogoLabel.setBounds(CurrentCenterX-510, CurrentCenterY-450, Data.DataPacManLogoIcon.getIconWidth(), Data.DataPacManLogoIcon.getIconHeight());
        this.PackManLogoLabel = PacManLogoLabel;
        this.add(this.PackManLogoLabel);

        // --- Starting position Updating
        startStuffPositionsUpdater();
        // --- Turn on the frame
        this.setVisible(true);
        this.setSize(1920, 1080);
    }

    // --- Custom method for creating main menu buttons
    private JButton createButton(String Text, ImageIcon Icon, int x, int y) {
        JButton button = new JButton(Text, Icon);
        button.setContentAreaFilled(false);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setFocusPainted(false);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setBorderPainted(false);
        button.setBounds(x, y, Icon.getIconWidth(), Icon.getIconHeight());
        button.setFont(new javax.swing.plaf.FontUIResource("Arial", javax.swing.plaf.FontUIResource.BOLD, 45));
        return button;
    }

    // --- Function to start threads  ---
    private void startCenterPointUpdater() {
        centerPointUpdater = new FrameCenterPointUpdater(this, this.CurrentCenterX, this.CurrentCenterY);
        centerPointUpdaterThread = new Thread(centerPointUpdater);
        centerPointUpdaterThread.start();
    }
    private void startStuffPositionsUpdater() {
        stuffPositionsUpdater = new MainMenuStuffPositionUpdater(this);
        stuffPositionsUpdaterThread = new Thread(stuffPositionsUpdater);
        stuffPositionsUpdaterThread.start();
    }

    // --- Action Listener for Start New Game Button
    private void startNewGamePressed() {
        int CurrentFrameX = this.getLocation().x;
        int CurrentFrameY = this.getLocation().y;
        stopUpdaters();
        this.dispose();
        MapChooseScreen mapChooseScreen = new MapChooseScreen();
        mapChooseScreen.setLocation(CurrentFrameX, CurrentFrameY);
        mapChooseScreen.setVisible(true);
    }

    // --- Action Listener for High Scores Button
    private void showHighScoresPressed() {
        stopUpdaters();
        this.dispose();
        RankingScreen ranking = new RankingScreen();
        ranking.setVisible(true);
    }

    // --- Action Listener for exit button
    private void exitGamePressed() {
        System.exit(0);
    }

    // --- Function to force stop of the threads
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
    public int getCenterPointX(){
        return this.CurrentCenterX;
    }
    public int getCenterPointY(){
        return this.CurrentCenterY;
    }
    public JButton getStartNewGameButton() {
        return this.StartNewGameButton;
    }
    public JButton getHighScoresButton() {
        return this.HighScoresButton;
    }
    public JButton getExitButton() {
        return this.ExitButton;
    }
    public void setStartNewGameButton(JButton startNewGameButton) {
        this.StartNewGameButton = startNewGameButton;
    }
    public void setHighScoresButton(JButton highScoresButton) {
        this.HighScoresButton = highScoresButton;
    }
    public void setExitButton(JButton exitButton) {
        this.ExitButton = exitButton;
    }
    public void setPackManLogoLabel(JLabel packManLogoLabel) {
        this.PackManLogoLabel = packManLogoLabel;
    }
    public JLabel getPackManLogoLabel() {
        return this.PackManLogoLabel;
    }

}

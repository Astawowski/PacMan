import javax.swing.*;

public class MainGameStuffPositionUpdater implements Runnable, MyGUIThread {
    private boolean IsRunning;
    private final MainGameScreen MainGameScreen;
    private JLabel[][] Map;
    private int OldCenterX;
    private int OldCenterY;
    private JLabel[][] Points2PickUpImages;
    private JLabel PlayerPointsDisplayer;
    private JLabel PlayerPointsDisplayer2;
    private JLabel PlayerLifesDisplayer;
    private JLabel CherryIndicator;
    private JLabel AppleIndicator;
    private JLabel PeachIndicator;
    private JLabel StrawberryIndicator;
    private JLabel MedallionIndicator;

    public MainGameStuffPositionUpdater(MainGameScreen MainGameScreen) {
        this.MainGameScreen = MainGameScreen;
        this.IsRunning = true;
        synchronized (this.MainGameScreen) {
            this.Map = this.MainGameScreen.getMap();
            this.OldCenterX = this.MainGameScreen.CurrentCenterX;
            this.OldCenterY = this.MainGameScreen.CurrentCenterY;
            this.Points2PickUpImages = this.MainGameScreen.getPoints2PickUpImages();
            this.PlayerPointsDisplayer = this.MainGameScreen.getPlayerPointsDisplayer();
            this.PlayerPointsDisplayer2 = this.MainGameScreen.getPlayerPointsDisplayer2();
            this.PlayerLifesDisplayer = this.MainGameScreen.getPlayerLifesDisplayer();
            this.CherryIndicator = this.MainGameScreen.getCherryIndicator();
            this.AppleIndicator = this.MainGameScreen.getAppleIndicator();
            this.PeachIndicator = this.MainGameScreen.getPeachIndicator();
            this.StrawberryIndicator = this.MainGameScreen.getStrawberryIndicator();
            this.MedallionIndicator = this.MainGameScreen.getMedallionIndicator();
        }
    }


    @Override
    public void run() {
        while (IsRunning) {
            // --- Repositioning map elements
            synchronized (this.MainGameScreen) {
                // --- If the middle point of the screen has moved it adjusts things position
                if((this.OldCenterX!=this.MainGameScreen.CurrentCenterX)||(this.OldCenterY!=this.MainGameScreen.CurrentCenterY)) {
                    // --- Map Elements positioning
                    for (int i = 0; i < Map.length; i++) {
                        for (int j = 0; j < Map[i].length; j++) {
                            this.Map[i][j].setBounds(this.MainGameScreen.CurrentCenterX - (Map[0].length * 25) + (50 * j), this.MainGameScreen.CurrentCenterY - (Map.length * 25) + (50 * i), 50, 50);
                        }
                    }
                    this.MainGameScreen.setMap(this.Map);
                    // --- Calculating the delta (distance of which central points have moved)
                    int deltaX = this.OldCenterX - this.MainGameScreen.CurrentCenterX;
                    int deltaY = this.OldCenterY - this.MainGameScreen.CurrentCenterY;
                    // --- Map Points positioning
                    for(int i = 0; i < Points2PickUpImages.length; i++) {
                        for(int j = 0; j < Points2PickUpImages[i].length; j++) {
                            Points2PickUpImages[i][j].setBounds(Points2PickUpImages[i][j].getBounds().x-deltaX, Points2PickUpImages[i][j].getBounds().y-deltaY, 50, 50);
                        }
                    }
                    this.MainGameScreen.setPoints2PickUpImages(this.Points2PickUpImages);

                   // --- pints displayer positioning
                    this.PlayerPointsDisplayer.setBounds(this.PlayerPointsDisplayer.getBounds().x-deltaX, this.PlayerPointsDisplayer.getBounds().y-deltaY, 200, 75);
                    this.MainGameScreen.setPlayerPointsDisplayer(this.PlayerPointsDisplayer);

                    // --- displayer positioning
                    this.PlayerPointsDisplayer2.setBounds(this.PlayerPointsDisplayer2.getBounds().x-deltaX, this.PlayerPointsDisplayer2.getBounds().y-deltaY, 200, 75);
                    this.MainGameScreen.setPlayerPointsDisplayer2(this.PlayerPointsDisplayer2);

                    // --- Player Lifes Displayer positioning
                    this.PlayerLifesDisplayer.setBounds(this.PlayerLifesDisplayer.getBounds().x - deltaX, this.PlayerLifesDisplayer.getBounds().y - deltaY, 250, 100);
                    this.MainGameScreen.setPlayerLifesDisplayer(this.PlayerLifesDisplayer);

                    // --- Cherry Indicator positioning
                    this.CherryIndicator.setBounds(this.CherryIndicator.getBounds().x - deltaX, this.CherryIndicator.getBounds().y - deltaY, 50, 50);
                    this.MainGameScreen.setCherryIndicator(this.CherryIndicator);

                    // --- Apple Indicator positioning
                    this.AppleIndicator.setBounds(this.AppleIndicator.getBounds().x - deltaX, this.AppleIndicator.getBounds().y - deltaY, 50, 50);
                    this.MainGameScreen.setAppleIndicator(this.AppleIndicator);

                    // --- Peach Indicator positioning
                    this.PeachIndicator.setBounds(this.PeachIndicator.getBounds().x - deltaX, this.PeachIndicator.getBounds().y - deltaY, 50, 50);
                    this.MainGameScreen.setPeachIndicator(this.PeachIndicator);

                    // --- Strawberry Indicator positioning
                    this.StrawberryIndicator.setBounds(this.StrawberryIndicator.getBounds().x - deltaX, this.StrawberryIndicator.getBounds().y - deltaY, 50, 50);
                    this.MainGameScreen.setStrawberryIndicator(this.StrawberryIndicator);

                     // --- Medallion Indicator positioning
                    this.MedallionIndicator.setBounds(this.MedallionIndicator.getBounds().x - deltaX, this.MedallionIndicator.getBounds().y - deltaY, 50, 50);
                    this.MainGameScreen.setMedallionIndicator(this.MedallionIndicator);

                    // --- Assigning the new central point
                    this.OldCenterX = this.MainGameScreen.CurrentCenterX;
                    this.OldCenterY = this.MainGameScreen.CurrentCenterY;
                }
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void stop(){
        this.IsRunning = false;
    }
}

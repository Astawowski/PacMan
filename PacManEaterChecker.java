import javax.swing.*;

public class PacManEaterChecker implements Runnable,MyGUIThread {
    private boolean IsRunning;
    private final MainGameScreen MainGameScreen;
    private int[][] Points2PickUpMap;
    private JLabel[][] Points2PickUpImages;
    private JLabel CherryIndicator;
    private JLabel AppleIndicator;
    private JLabel PeachIndicator;
    private JLabel StrawberryIndicator;
    private JLabel MedallionIndicator;

    public PacManEaterChecker(MainGameScreen MainGameScreen) {
        this.MainGameScreen = MainGameScreen;
        this.IsRunning = true;
        synchronized (this.MainGameScreen) {
        this.Points2PickUpImages = this.MainGameScreen.getPoints2PickUpImages();
        this.Points2PickUpMap = this.MainGameScreen.getPoints2PickUpMap();
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
            int CurrentPacManX = 0;
            int CurrentPacManY = 0;
            synchronized (this.MainGameScreen){
                CurrentPacManX = this.MainGameScreen.getPacMan().getBounds().x;
                CurrentPacManY = this.MainGameScreen.getPacMan().getBounds().y;
                int CurrentPacManPointField = this.Points2PickUpMap[this.MainGameScreen.getCurrentRowIndex(CurrentPacManX,CurrentPacManY)]
                        [this.MainGameScreen.getCurrentColumnIndex(CurrentPacManX,CurrentPacManY)];
                // --- Checking if player is on the point field
                if(CurrentPacManPointField == 1) {
                    this.Points2PickUpMap[this.MainGameScreen.getCurrentRowIndex(CurrentPacManX,CurrentPacManY)]
                            [this.MainGameScreen.getCurrentColumnIndex(CurrentPacManX,CurrentPacManY)] = -1;
                    this.Points2PickUpImages[this.MainGameScreen.getCurrentRowIndex(CurrentPacManX,CurrentPacManY)]
                            [this.MainGameScreen.getCurrentColumnIndex(CurrentPacManX,CurrentPacManY)].setIcon(Data.DataTransparentSquareN1);
                    this.MainGameScreen.setPlayerPoints(this.MainGameScreen.getPlayerPoints()+50*this.MainGameScreen.getPointsMultiplier());
                    this.MainGameScreen.setPoints2PickUpImages(this.Points2PickUpImages);
                    this.MainGameScreen.setPoints2PickUpMap(this.Points2PickUpMap);
                    JLabel PointsDisplayer2 = this.MainGameScreen.getPlayerPointsDisplayer2();
                    PointsDisplayer2.setText(""+this.MainGameScreen.getPlayerPoints());
                    this.MainGameScreen.setPlayerPointsDisplayer2(PointsDisplayer2);
                    this.MainGameScreen.AnyPointsLeft();
                }else if(CurrentPacManPointField == 2){
                    ResetPointFieldBack2Null(CurrentPacManX, CurrentPacManY);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (MainGameScreen) {
                                MainGameScreen.setPointsMultiplier(2);
                                CherryIndicator.setIcon(Data.DataCherry);
                                MainGameScreen.setCherryIndicator(CherryIndicator);
                            }
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            synchronized (MainGameScreen) {
                                MainGameScreen.setPointsMultiplier(1);
                                CherryIndicator.setIcon(Data.DataTransparentSquareN1);
                                MainGameScreen.setCherryIndicator(CherryIndicator);
                            }
                        }
                    }).start();
                }else if(CurrentPacManPointField == 3){
                    ResetPointFieldBack2Null(CurrentPacManX, CurrentPacManY);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (MainGameScreen) {
                                MainGameScreen.setPlayerImmortal(true);
                                AppleIndicator.setIcon(Data.DataApple);
                                MainGameScreen.setAppleIndicator(AppleIndicator);
                            }
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            synchronized (MainGameScreen) {
                                MainGameScreen.setPlayerImmortal(false);
                                AppleIndicator.setIcon(Data.DataTransparentSquareN1);
                                MainGameScreen.setAppleIndicator(AppleIndicator);
                            }
                        }
                    }).start();
                }else if(CurrentPacManPointField == 4){
                    ResetPointFieldBack2Null(CurrentPacManX, CurrentPacManY);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (MainGameScreen) {
                                MainGameScreen.setPlayerSpeedMultiplier(2);
                                PeachIndicator.setIcon(Data.DataPeach);
                                MainGameScreen.setPeachIndicator(PeachIndicator);
                            }
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            synchronized (MainGameScreen) {
                                MainGameScreen.setPlayerSpeedMultiplier(1);
                                PeachIndicator.setIcon(Data.DataTransparentSquareN1);
                                MainGameScreen.setPeachIndicator(PeachIndicator);
                            }
                        }
                    }).start();
                }else if(CurrentPacManPointField == 5){
                    ResetPointFieldBack2Null(CurrentPacManX, CurrentPacManY);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (MainGameScreen) {
                                MainGameScreen.setEnemySpeedMultiplier(0.75f);
                                StrawberryIndicator.setIcon(Data.DataStrawberry);
                                MainGameScreen.setStrawberryIndicator(StrawberryIndicator);
                            }
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            synchronized (MainGameScreen) {
                                MainGameScreen.setEnemySpeedMultiplier(1);
                                StrawberryIndicator.setIcon(Data.DataTransparentSquareN1);
                                MainGameScreen.setStrawberryIndicator(StrawberryIndicator);
                            }
                        }
                    }).start();
                }else if(CurrentPacManPointField == 6){
                    ResetPointFieldBack2Null(CurrentPacManX, CurrentPacManY);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (MainGameScreen) {
                                MainGameScreen.setRolesSwitched(true);
                                MedallionIndicator.setIcon(Data.DataMedallion);
                                MainGameScreen.setMedallionIndicator(MedallionIndicator);
                            }
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            synchronized (MainGameScreen) {
                                MainGameScreen.setRolesSwitched(false);
                                MedallionIndicator.setIcon(Data.DataTransparentSquareN1);
                                MainGameScreen.setMedallionIndicator(MedallionIndicator);
                            }
                        }
                    }).start();
                }

            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ResetPointFieldBack2Null(int currentPacManX, int currentPacManY) {
        this.Points2PickUpMap[this.MainGameScreen.getCurrentRowIndex(currentPacManX, currentPacManY)]
                [this.MainGameScreen.getCurrentColumnIndex(currentPacManX, currentPacManY)] = -1;
        this.Points2PickUpImages[this.MainGameScreen.getCurrentRowIndex(currentPacManX, currentPacManY)]
                [this.MainGameScreen.getCurrentColumnIndex(currentPacManX, currentPacManY)].setIcon(Data.DataTransparentSquareN1);
        this.MainGameScreen.setPoints2PickUpImages(this.Points2PickUpImages);
        this.MainGameScreen.setPoints2PickUpMap(this.Points2PickUpMap);
    }

    @Override
    public void stop(){
        this.IsRunning = false;
    }

}

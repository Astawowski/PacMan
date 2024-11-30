public class ConsumablesDropper implements Runnable, MyGUIThread{
    private boolean IsRunning;
    private final MainGameScreen gameScreen;

    public ConsumablesDropper(MainGameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.IsRunning = true;
    }

    @Override
    public void run() {
        while (IsRunning) {
            synchronized (this.gameScreen) {
                int PinkDrop = (int)(Math.random()*20);
                int OrangeDrop = (int)(Math.random()*20);
                int RedDrop = (int)(Math.random()*20);
                int BlueDrop = (int)(Math.random()*20);
                if(PinkDrop<5){
                    int PinkCurrentRow = this.gameScreen.getCurrentRowIndex(this.gameScreen.getPinkGhostEnemy().getX(), this.gameScreen.getPinkGhostEnemy().getY());
                    int PinkCurrentColumn = this.gameScreen.getCurrentColumnIndex(this.gameScreen.getPinkGhostEnemy().getX(), this.gameScreen.getPinkGhostEnemy().getY());
                    switch (PinkDrop){
                        case 0:
                            this.gameScreen.getPoints2PickUpMap()[PinkCurrentRow][PinkCurrentColumn] = 2;
                            this.gameScreen.getPoints2PickUpImages()[PinkCurrentRow][PinkCurrentColumn].setIcon(Data.DataCherry);
                            break;
                        case 1:
                            this.gameScreen.getPoints2PickUpMap()[PinkCurrentRow][PinkCurrentColumn] = 3;
                            this.gameScreen.getPoints2PickUpImages()[PinkCurrentRow][PinkCurrentColumn].setIcon(Data.DataApple);
                            break;
                        case 2:
                            this.gameScreen.getPoints2PickUpMap()[PinkCurrentRow][PinkCurrentColumn] = 4;
                            this.gameScreen.getPoints2PickUpImages()[PinkCurrentRow][PinkCurrentColumn].setIcon(Data.DataPeach);
                            break;
                        case 3:
                            this.gameScreen.getPoints2PickUpMap()[PinkCurrentRow][PinkCurrentColumn] = 5;
                            this.gameScreen.getPoints2PickUpImages()[PinkCurrentRow][PinkCurrentColumn].setIcon(Data.DataStrawberry);
                            break;
                        default:
                            this.gameScreen.getPoints2PickUpMap()[PinkCurrentRow][PinkCurrentColumn] = 6;
                            this.gameScreen.getPoints2PickUpImages()[PinkCurrentRow][PinkCurrentColumn].setIcon(Data.DataMedallion);
                    }
                }if(OrangeDrop<5){
                    int OrangeCurrentRow = this.gameScreen.getCurrentRowIndex(this.gameScreen.getOrangeGhostEnemy().getX(), this.gameScreen.getOrangeGhostEnemy().getY());
                    int OrangeCurrentColumn = this.gameScreen.getCurrentColumnIndex(this.gameScreen.getOrangeGhostEnemy().getX(), this.gameScreen.getOrangeGhostEnemy().getY());
                    switch (OrangeDrop){
                        case 0:
                            this.gameScreen.getPoints2PickUpMap()[OrangeCurrentRow][OrangeCurrentColumn] = 2;
                            this.gameScreen.getPoints2PickUpImages()[OrangeCurrentRow][OrangeCurrentColumn].setIcon(Data.DataCherry);
                            break;
                        case 1:
                            this.gameScreen.getPoints2PickUpMap()[OrangeCurrentRow][OrangeCurrentColumn] = 3;
                            this.gameScreen.getPoints2PickUpImages()[OrangeCurrentRow][OrangeCurrentColumn].setIcon(Data.DataApple);
                            break;
                        case 2:
                            this.gameScreen.getPoints2PickUpMap()[OrangeCurrentRow][OrangeCurrentColumn] = 4;
                            this.gameScreen.getPoints2PickUpImages()[OrangeCurrentRow][OrangeCurrentColumn].setIcon(Data.DataPeach);
                            break;
                        case 3:
                            this.gameScreen.getPoints2PickUpMap()[OrangeCurrentRow][OrangeCurrentColumn] = 5;
                            this.gameScreen.getPoints2PickUpImages()[OrangeCurrentRow][OrangeCurrentColumn].setIcon(Data.DataStrawberry);
                            break;
                        default:
                            this.gameScreen.getPoints2PickUpMap()[OrangeCurrentRow][OrangeCurrentColumn] = 6;
                            this.gameScreen.getPoints2PickUpImages()[OrangeCurrentRow][OrangeCurrentColumn].setIcon(Data.DataMedallion);
                    }
                }
                if(BlueDrop<5){
                    int BlueCurrentRow = this.gameScreen.getCurrentRowIndex(this.gameScreen.getBlueGhostEnemy().getX(), this.gameScreen.getBlueGhostEnemy().getY());
                    int BlueCurrentColumn = this.gameScreen.getCurrentColumnIndex(this.gameScreen.getBlueGhostEnemy().getX(), this.gameScreen.getBlueGhostEnemy().getY());
                    switch (BlueDrop){
                        case 0:
                            this.gameScreen.getPoints2PickUpMap()[BlueCurrentRow][BlueCurrentColumn] = 2;
                            this.gameScreen.getPoints2PickUpImages()[BlueCurrentRow][BlueCurrentColumn].setIcon(Data.DataCherry);
                            break;
                        case 1:
                            this.gameScreen.getPoints2PickUpMap()[BlueCurrentRow][BlueCurrentColumn] = 3;
                            this.gameScreen.getPoints2PickUpImages()[BlueCurrentRow][BlueCurrentColumn].setIcon(Data.DataApple);
                            break;
                        case 2:
                            this.gameScreen.getPoints2PickUpMap()[BlueCurrentRow][BlueCurrentColumn] = 4;
                            this.gameScreen.getPoints2PickUpImages()[BlueCurrentRow][BlueCurrentColumn].setIcon(Data.DataPeach);
                            break;
                        case 3:
                            this.gameScreen.getPoints2PickUpMap()[BlueCurrentRow][BlueCurrentColumn] = 5;
                            this.gameScreen.getPoints2PickUpImages()[BlueCurrentRow][BlueCurrentColumn].setIcon(Data.DataStrawberry);
                            break;
                        default:
                            this.gameScreen.getPoints2PickUpMap()[BlueCurrentRow][BlueCurrentColumn] = 6;
                            this.gameScreen.getPoints2PickUpImages()[BlueCurrentRow][BlueCurrentColumn].setIcon(Data.DataMedallion);
                    }
                }
                if(RedDrop<5){
                    int RedCurrentRow = this.gameScreen.getCurrentRowIndex(this.gameScreen.getRedGhostEnemy().getX(), this.gameScreen.getRedGhostEnemy().getY());
                    int RedCurrentColumn = this.gameScreen.getCurrentColumnIndex(this.gameScreen.getRedGhostEnemy().getX(), this.gameScreen.getRedGhostEnemy().getY());
                    switch (RedDrop){
                        case 0:
                            this.gameScreen.getPoints2PickUpMap()[RedCurrentRow][RedCurrentColumn] = 2;
                            this.gameScreen.getPoints2PickUpImages()[RedCurrentRow][RedCurrentColumn].setIcon(Data.DataCherry);
                            break;
                        case 1:
                            this.gameScreen.getPoints2PickUpMap()[RedCurrentRow][RedCurrentColumn] = 3;
                            this.gameScreen.getPoints2PickUpImages()[RedCurrentRow][RedCurrentColumn].setIcon(Data.DataApple);
                            break;
                        case 2:
                            this.gameScreen.getPoints2PickUpMap()[RedCurrentRow][RedCurrentColumn] = 4;
                            this.gameScreen.getPoints2PickUpImages()[RedCurrentRow][RedCurrentColumn].setIcon(Data.DataPeach);
                            break;
                        case 3:
                            this.gameScreen.getPoints2PickUpMap()[RedCurrentRow][RedCurrentColumn] = 5;
                            this.gameScreen.getPoints2PickUpImages()[RedCurrentRow][RedCurrentColumn].setIcon(Data.DataStrawberry);
                            break;
                        default:
                            this.gameScreen.getPoints2PickUpMap()[RedCurrentRow][RedCurrentColumn] = 6;
                            this.gameScreen.getPoints2PickUpImages()[RedCurrentRow][RedCurrentColumn].setIcon(Data.DataMedallion);
                    }
                }

            }
            try {
                Thread.sleep(5000);
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

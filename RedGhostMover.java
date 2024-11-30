import javax.swing.*;


public class RedGhostMover implements Runnable, MyGUIThread {
    private boolean IsRunning;
    private final MainGameScreen gameScreen;
    private JLabel RedGhost;
    private int RedGhostXSpeed;
    private int RedGhostYSpeed;
    private int trialsCount;
    private Direction GhostDirection;
    private int OldCenterX;
    private int OldCenterY;
    private int MapLayoutChosen;
    private boolean IsChasing;
    private boolean GettingOutOfSpawn;

    public RedGhostMover(MainGameScreen MainGameScreen) {
        this.gameScreen = MainGameScreen;
        this.IsRunning = true;
        this.RedGhostXSpeed = 0;
        this.RedGhostYSpeed = 0;
        this.trialsCount = 0;
        synchronized (this.gameScreen) {
            this.RedGhost = this.gameScreen.getRedGhostEnemy();
            this.GhostDirection = this.gameScreen.getRedGhostDirection();
            this.OldCenterX = this.gameScreen.CurrentCenterX;
            this.OldCenterY = this.gameScreen.CurrentCenterY;
            this.MapLayoutChosen = this.gameScreen.getMapLayoutChosen();
        }
        this.IsChasing = false;
        this.GettingOutOfSpawn = true;
    }

    @Override
    public void run() {
        while (this.IsRunning) {
            // --- At the beginning ghost is picking random speed vector to get out of his spawn
            if (!GhostGotOutOfSpawn(this.RedGhost.getBounds().x,this.RedGhost.getBounds().y) && this.trialsCount%30==0) {
                PickNewRandomVector();
                AssignToField();
            }
            else if(!this.IsChasing&&GhostGotOutOfSpawn(this.RedGhost.getBounds().x,this.RedGhost.getBounds().y)){
                int determinator2go = (int)(Math.random()*10);
                if(determinator2go==0) Go2StandardFieldNearby();
            }

            // --- Wall Collision Checking ---
            synchronized (this.gameScreen) {
                // --- Checking if RedGhost is heading for a wall
                if (this.RedGhostXSpeed > 0) {
                    if ( (this.gameScreen.getCurrentFieldType(this.RedGhost.getX() + 40, this.RedGhost.getY()) != 0)||
                            (this.gameScreen.getCurrentFieldType(this.RedGhost.getX() + 40, this.RedGhost.getY()+30) != 0 )
                    )  {
                        AssignToField();
                        PickNewRandomVector();

                    }
                }
            }
            synchronized (this.gameScreen) {
                if (this.RedGhostXSpeed < 0) {
                    if ((this.gameScreen.getCurrentFieldType(this.RedGhost.getX() - 10, this.RedGhost.getY()) != 0)||
                            (this.gameScreen.getCurrentFieldType(this.RedGhost.getX() - 10, this.RedGhost.getY()+30) != 0))  {
                        AssignToField();
                        PickNewRandomVector();
                    }
                }
            }
            synchronized (this.gameScreen) {
                if (this.RedGhostYSpeed > 0) {
                    if ((this.gameScreen.getCurrentFieldType(this.RedGhost.getX(), this.RedGhost.getY() + 40) != 0)
                            ||(this.gameScreen.getCurrentFieldType(this.RedGhost.getX()+30, this.RedGhost.getY() + 40) != 0)) {
                        AssignToField();
                        PickNewRandomVector();
                    }
                }
            }
            synchronized (this.gameScreen) {
                if (this.RedGhostYSpeed < 0) {
                    if ((this.gameScreen.getCurrentFieldType(this.RedGhost.getX(), this.RedGhost.getY() - 10) != 0)
                            || (this.gameScreen.getCurrentFieldType(this.RedGhost.getX()+30, this.RedGhost.getY() - 10) != 0)) {
                        AssignToField();
                        PickNewRandomVector();
                    }
                }
            }

            // --- Chase player if in range
            CheckAndIfChasePlayer();

            // --- Checking if ghost has stopped for whatever reason
            if(this.RedGhostXSpeed==0&&this.RedGhostYSpeed==0) {
                PickNewRandomVector();
                AssignToField();
            }

            // --- Move Red Ghost & apply possible center frame point changes
            int xOffSet = 0;
            int yOffSet = 0;
            synchronized (this.gameScreen) {
                if (this.gameScreen.CurrentCenterX != this.OldCenterX || this.gameScreen.CurrentCenterY != this.OldCenterY) {
                    xOffSet = this.gameScreen.CurrentCenterX - this.OldCenterX;
                    yOffSet = this.gameScreen.CurrentCenterY - this.OldCenterY;
                    this.OldCenterX = this.gameScreen.CurrentCenterX;
                    this.OldCenterY = this.gameScreen.CurrentCenterY;
                }
                this.RedGhost.setBounds(this.RedGhost.getBounds().x + xOffSet + (int)(this.RedGhostXSpeed*this.gameScreen.getEnemySpeedMultiplier()),
                        this.RedGhost.getBounds().y + yOffSet + (int)(this.RedGhostYSpeed*this.gameScreen.getEnemySpeedMultiplier()),
                        this.RedGhost.getIcon().getIconWidth(), this.RedGhost.getIcon().getIconHeight());
                this.gameScreen.setRedGhostEnemy(this.RedGhost);
                this.gameScreen.setRedGhostDirection(this.GhostDirection);
            }



            this.GettingOutOfSpawn = !GhostGotOutOfSpawn(this.RedGhost.getBounds().x, this.RedGhost.getBounds().y);
            if(this.GettingOutOfSpawn) this.trialsCount++;

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public void PickNewRandomVector(){
        int DirectionPicked = (int) (Math.random() * 4);
        switch (DirectionPicked) {
            case 0: // Right
                this.RedGhostXSpeed = 3;
                this.RedGhostYSpeed = 0;
                this.GhostDirection = Direction.Right;
                break;
            case 1: // Left
                this.RedGhostXSpeed = -3;
                this.RedGhostYSpeed = 0;
                this.GhostDirection = Direction.Left;
                break;
            case 2: // Up
                this.RedGhostXSpeed = 0;
                this.RedGhostYSpeed = -3;
                this.GhostDirection = Direction.Up;
                break;
            default: // Down
                this.RedGhostXSpeed = 0;
                this.RedGhostYSpeed = 3;
                this.GhostDirection = Direction.Down;
        }
    }

    public void AssignToField(){
        int CurrentColumn = this.gameScreen.getCurrentColumnIndex(this.RedGhost.getBounds().x, this.RedGhost.getBounds().y);
        int CurrentRow = this.gameScreen.getCurrentRowIndex(this.RedGhost.getBounds().x, this.RedGhost.getBounds().y);
        this.RedGhost.setBounds(this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().x+10,
                this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().y+10,
                this.RedGhost.getIcon().getIconWidth(), this.RedGhost.getIcon().getIconHeight());
    }


    public boolean GhostGotOutOfSpawn(int x, int y){
        int GhostRow = 0;
        int GhostCol = 0;
        boolean OuOfSpawn = false;
        synchronized (this.gameScreen) {
            GhostRow = this.gameScreen.getCurrentRowIndex(x,y);
            GhostCol = this.gameScreen.getCurrentColumnIndex(x,y);
        }
        if(this.MapLayoutChosen==1){
            if(GhostRow!=9||((GhostCol<8)||(GhostCol>11))) OuOfSpawn=true;
        }
        if(this.MapLayoutChosen==2){
            if(GhostRow!=5||((GhostCol<8)||(GhostCol>11))) OuOfSpawn=true;
        }
        if(this.MapLayoutChosen==3){
            if(GhostRow!=8||((GhostCol<4)||(GhostCol>7))) OuOfSpawn=true;
        }if(this.MapLayoutChosen==4){
            if(GhostRow!=7||((GhostCol<6)||(GhostCol>9))) OuOfSpawn=true;
        }if(this.MapLayoutChosen==5){
            OuOfSpawn=true;
        }
        return OuOfSpawn;
    }

    public void CheckAndIfChasePlayer(){
        int DeltaX=0;
        int DeltaY=0;
        synchronized (this.gameScreen) {
            DeltaX=this.RedGhost.getBounds().x-this.gameScreen.getPacMan().getBounds().x;
            DeltaY=this.RedGhost.getBounds().y-this.gameScreen.getPacMan().getBounds().y;
        }
        if(
                (Math.abs(DeltaY)<=15)&&(Math.abs(DeltaX)<=1000)){
            synchronized (this.gameScreen) {
                int distance = 0;
                while(Math.abs(distance)<Math.abs(DeltaX)){
                    if(this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x+distance, this.RedGhost.getBounds().y)!=0){
                        return;
                    }
                    distance+=(DeltaX<0)?30:-30;
                }
                if (DeltaX > 0&&(this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x-20, this.RedGhost.getBounds().y)==0)
                        &&(this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x-20, this.RedGhost.getBounds().y+30)==0)) {
                    this.GhostDirection = Direction.Left;
                    this.RedGhostXSpeed = -3;
                    this.RedGhostYSpeed = 0;
                    if (!this.IsChasing) AssignToField();
                    this.IsChasing = true;
                } else if((this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x+50, this.RedGhost.getBounds().y)==0)
                        &&(this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x+50, this.RedGhost.getBounds().y+30)==0)){
                    this.GhostDirection = Direction.Right;
                    this.RedGhostXSpeed = 3;
                    this.RedGhostYSpeed = 0;
                    if (!this.IsChasing) AssignToField();
                    this.IsChasing = true;
                }
            }
        }else if(
                (Math.abs(DeltaX)<=15)&&(Math.abs(DeltaY)<=1000)) {
            synchronized (this.gameScreen) {
                int distance = 0;
                while(Math.abs(distance)<Math.abs(DeltaY)){
                    if(this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x, this.RedGhost.getBounds().y+distance)!=0){
                        return;
                    }
                    distance+=(DeltaY<0)?30:-30;
                }
            if (DeltaY > 0 && (this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x, this.RedGhost.getBounds().y - 20) == 0) &&
                    (this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x + 30, this.RedGhost.getBounds().y - 20) == 0)) {
                this.GhostDirection = Direction.Up;
                this.RedGhostXSpeed = 0;
                this.RedGhostYSpeed = -3;
                if (!this.IsChasing) AssignToField();
                this.IsChasing = true;
            } else if ((this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x, this.RedGhost.getBounds().y + 50) == 0) &&
                    (this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x + 30, this.RedGhost.getBounds().y + 50) == 0)) {
                this.GhostDirection = Direction.Down;
                this.RedGhostXSpeed = 0;
                this.RedGhostYSpeed = 3;
                if (!this.IsChasing) AssignToField();
                this.IsChasing = true;
            }
        }
        }else this.IsChasing = false;

    }



    public void Go2StandardFieldNearby() {
        int GhostRow = 0;
        int GhostCol = 0;
        boolean OuOfSpawn = false;
        synchronized (this.gameScreen) {
            GhostRow = this.gameScreen.getCurrentRowIndex(this.RedGhost.getBounds().x, this.RedGhost.getBounds().y);
            GhostCol = this.gameScreen.getCurrentColumnIndex(this.RedGhost.getBounds().x, this.RedGhost.getBounds().y);
            // -- If standard field is above
            if ((this.GhostDirection!=Direction.Up)&&(this.GhostDirection!=Direction.Down)&&(this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x, this.RedGhost.getBounds().y - 12) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x + 30, this.RedGhost.getBounds().y - 12) == 0)) {
                this.GhostDirection = Direction.Up;
                this.RedGhostXSpeed = 0;
                this.RedGhostYSpeed = -3;
                AssignToField();
                // -- If standard field is to the right
            } else if ((this.GhostDirection!=Direction.Left)&&(this.GhostDirection!=Direction.Right)&&(this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x + 42, this.RedGhost.getBounds().y) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x + 42, this.RedGhost.getBounds().y + 30) == 0)) {
                this.GhostDirection = Direction.Right;
                this.RedGhostXSpeed = 3;
                this.RedGhostYSpeed = 0;
                AssignToField();
            }
            // -- If standard field is to the left
            else if ((this.GhostDirection!=Direction.Left)&&(this.GhostDirection!=Direction.Right)&&(this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x - 12, this.RedGhost.getBounds().y) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x - 12, this.RedGhost.getBounds().y + 30) == 0)) {
                this.GhostDirection = Direction.Left;
                this.RedGhostXSpeed = -3;
                this.RedGhostYSpeed = 0;
                AssignToField();
            }
            // -- If standard field is below
            else if (GhostGotOutOfSpawn(this.RedGhost.getBounds().x,this.RedGhost.getBounds().y+100)&&(this.GhostDirection!=Direction.Up)&&(this.GhostDirection!=Direction.Down)&&(this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x, this.RedGhost.getBounds().y + 42) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.RedGhost.getBounds().x + 30, this.RedGhost.getBounds().y + 42) == 0)) {
                this.GhostDirection = Direction.Down;
                this.RedGhostXSpeed = 0;
                this.RedGhostYSpeed = 3;
                AssignToField();
            }
        }
    }



    @Override
    public void stop(){
        this.IsRunning = false;
    }

}

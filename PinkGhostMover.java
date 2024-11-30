import javax.swing.*;


public class PinkGhostMover implements Runnable, MyGUIThread {
    private boolean IsRunning;
    private final MainGameScreen gameScreen;
    private JLabel PinkGhost;
    private int PinkGhostXSpeed;
    private int PinkGhostYSpeed;
    private int trialsCount;
    private Direction GhostDirection;
    private int OldCenterX;
    private int OldCenterY;
    private int MapLayoutChosen;
    private boolean IsChasing;
    private boolean GettingOutOfSpawn;

    public PinkGhostMover(MainGameScreen MainGameScreen) {
        this.gameScreen = MainGameScreen;
        this.IsRunning = true;
        this.PinkGhostXSpeed = 0;
        this.PinkGhostYSpeed = 0;
        this.trialsCount = 0;
        synchronized (this.gameScreen) {
            this.PinkGhost = this.gameScreen.getPinkGhostEnemy();
            this.GhostDirection = this.gameScreen.getPinkGhostDirection();
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
            if (!GhostGotOutOfSpawn(this.PinkGhost.getBounds().x,this.PinkGhost.getBounds().y) && this.trialsCount%30==0) {
                    PickNewRandomVector();
                    AssignToField();
            }
            else if(!this.IsChasing&&GhostGotOutOfSpawn(this.PinkGhost.getBounds().x,this.PinkGhost.getBounds().y)){
                int determinator2go = (int)(Math.random()*10);
                if(determinator2go==0) Go2StandardFieldNearby();
            }


            // --- Wall Collision Checking ---
            synchronized (this.gameScreen) {
                // --- Checking if PinkGhost is heading for a wall
                if (this.PinkGhostXSpeed > 0) {
                    if ( (this.gameScreen.getCurrentFieldType(this.PinkGhost.getX() + 40, this.PinkGhost.getY()) != 0)||
                            (this.gameScreen.getCurrentFieldType(this.PinkGhost.getX() + 40, this.PinkGhost.getY()+30) != 0 )
                    )  {
                        PickNewRandomVector();
                        AssignToField();
                    }
                }
            }
            synchronized (this.gameScreen) {
                if (this.PinkGhostXSpeed < 0) {
                    if ((this.gameScreen.getCurrentFieldType(this.PinkGhost.getX() - 10, this.PinkGhost.getY()) != 0)||
                            (this.gameScreen.getCurrentFieldType(this.PinkGhost.getX() - 10, this.PinkGhost.getY()+30) != 0))  {
                        PickNewRandomVector();
                        AssignToField();
                    }
                }
            }
            synchronized (this.gameScreen) {
                if (this.PinkGhostYSpeed > 0) {
                    if ((this.gameScreen.getCurrentFieldType(this.PinkGhost.getX(), this.PinkGhost.getY() + 40) != 0)
                            ||(this.gameScreen.getCurrentFieldType(this.PinkGhost.getX()+30, this.PinkGhost.getY() + 40) != 0)) {
                        PickNewRandomVector();
                        AssignToField();
                    }
                }
            }
            synchronized (this.gameScreen) {
                if (this.PinkGhostYSpeed < 0) {
                    if ((this.gameScreen.getCurrentFieldType(this.PinkGhost.getX(), this.PinkGhost.getY() - 10) != 0)
                            || (this.gameScreen.getCurrentFieldType(this.PinkGhost.getX()+30, this.PinkGhost.getY() - 10) != 0)) {
                        PickNewRandomVector();
                        AssignToField();
                    }
                }
            }

            // --- Chase player if in range
            CheckAndIfChasePlayer();

            // --- Checking if ghost has stopped for whatever reason
            if(this.PinkGhostXSpeed==0&&this.PinkGhostYSpeed==0) {
                PickNewRandomVector();
                AssignToField();
            }

            // --- Move Pink Ghost & apply possible center frame point changes
            int xOffSet = 0;
            int yOffSet = 0;
            synchronized (this.gameScreen) {
                if (this.gameScreen.CurrentCenterX != this.OldCenterX || this.gameScreen.CurrentCenterY != this.OldCenterY) {
                    xOffSet = this.gameScreen.CurrentCenterX - this.OldCenterX;
                    yOffSet = this.gameScreen.CurrentCenterY - this.OldCenterY;
                    this.OldCenterX = this.gameScreen.CurrentCenterX;
                    this.OldCenterY = this.gameScreen.CurrentCenterY;
                }
                this.PinkGhost.setBounds(this.PinkGhost.getBounds().x + xOffSet + (int)(this.PinkGhostXSpeed*this.gameScreen.getEnemySpeedMultiplier()),
                        this.PinkGhost.getBounds().y + yOffSet + (int)(this.PinkGhostYSpeed*this.gameScreen.getEnemySpeedMultiplier()),
                        this.PinkGhost.getIcon().getIconWidth(), this.PinkGhost.getIcon().getIconHeight());
                this.gameScreen.setPinkGhostEnemy(this.PinkGhost);
                this.gameScreen.setPinkGhostDirection(this.GhostDirection);
            }

            this.GettingOutOfSpawn = !GhostGotOutOfSpawn(this.PinkGhost.getBounds().x, this.PinkGhost.getBounds().y);
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
                this.PinkGhostXSpeed = 3;
                this.PinkGhostYSpeed = 0;
                this.GhostDirection = Direction.Right;
                break;
            case 1: // Left
                this.PinkGhostXSpeed = -3;
                this.PinkGhostYSpeed = 0;
                this.GhostDirection = Direction.Left;
                break;
            case 2: // Up
                this.PinkGhostXSpeed = 0;
                this.PinkGhostYSpeed = -3;
                this.GhostDirection = Direction.Up;
                break;
            default: // Down
                this.PinkGhostXSpeed = 0;
                this.PinkGhostYSpeed = 3;
                this.GhostDirection = Direction.Down;
        }
    }

    public void AssignToField(){
        int CurrentColumn = this.gameScreen.getCurrentColumnIndex(this.PinkGhost.getBounds().x, this.PinkGhost.getBounds().y);
        int CurrentRow = this.gameScreen.getCurrentRowIndex(this.PinkGhost.getBounds().x, this.PinkGhost.getBounds().y);
        this.PinkGhost.setBounds(this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().x+10,
                this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().y+10,
                this.PinkGhost.getIcon().getIconWidth(), this.PinkGhost.getIcon().getIconHeight());
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
            DeltaX=this.PinkGhost.getBounds().x-this.gameScreen.getPacMan().getBounds().x;
            DeltaY=this.PinkGhost.getBounds().y-this.gameScreen.getPacMan().getBounds().y;
        }
        if(
                (Math.abs(DeltaY)<=15)&&(Math.abs(DeltaX)<=1000)){
            synchronized (this.gameScreen) {
                int distance = 0;
                while(Math.abs(distance)<Math.abs(DeltaX)){
                    if(this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x+distance, this.PinkGhost.getBounds().y)!=0){
                        return;
                    }
                    distance+=(DeltaX<0)?30:-30;
                }
                if (DeltaX > 0&&(this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x-20, this.PinkGhost.getBounds().y)==0)
                        &&(this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x-20, this.PinkGhost.getBounds().y+30)==0)) {
                    this.GhostDirection = Direction.Left;
                    this.PinkGhostXSpeed = -3;
                    this.PinkGhostYSpeed = 0;
                    if (!this.IsChasing) AssignToField();
                    this.IsChasing = true;
                } else if((this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x+50, this.PinkGhost.getBounds().y)==0)
                        &&(this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x+50, this.PinkGhost.getBounds().y+30)==0)){
                    this.GhostDirection = Direction.Right;
                    this.PinkGhostXSpeed = 3;
                    this.PinkGhostYSpeed = 0;
                    if (!this.IsChasing) AssignToField();
                    this.IsChasing = true;
                }
            }
        }else if(
                (Math.abs(DeltaX)<=15)&&(Math.abs(DeltaY)<=1000)) {
            synchronized (this.gameScreen) {
                int distance = 0;
                while(Math.abs(distance)<Math.abs(DeltaY)){
                    if(this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x, this.PinkGhost.getBounds().y+distance)!=0){
                        return;
                    }
                    distance+=(DeltaY<0)?30:-30;
                }
                if (DeltaY > 0 && (this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x, this.PinkGhost.getBounds().y - 20) == 0) &&
                        (this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x + 30, this.PinkGhost.getBounds().y - 20) == 0)) {
                    this.GhostDirection = Direction.Up;
                    this.PinkGhostXSpeed = 0;
                    this.PinkGhostYSpeed = -3;
                    if (!this.IsChasing) AssignToField();
                    this.IsChasing = true;
                } else if ((this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x, this.PinkGhost.getBounds().y + 50) == 0) &&
                        (this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x + 30, this.PinkGhost.getBounds().y + 50) == 0)) {
                    this.GhostDirection = Direction.Down;
                    this.PinkGhostXSpeed = 0;
                    this.PinkGhostYSpeed = 3;
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
            GhostRow = this.gameScreen.getCurrentRowIndex(this.PinkGhost.getBounds().x, this.PinkGhost.getBounds().y);
            GhostCol = this.gameScreen.getCurrentColumnIndex(this.PinkGhost.getBounds().x, this.PinkGhost.getBounds().y);
            // -- If standard field is above
            if ((this.GhostDirection!=Direction.Up)&&(this.GhostDirection!=Direction.Down)&&(this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x, this.PinkGhost.getBounds().y - 12) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x + 30, this.PinkGhost.getBounds().y - 12) == 0)) {
                this.GhostDirection = Direction.Up;
                this.PinkGhostXSpeed = 0;
                this.PinkGhostYSpeed = -3;
                AssignToField();
                // -- If standard field is to the right
            } else if ((this.GhostDirection!=Direction.Left)&&(this.GhostDirection!=Direction.Right)&&(this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x + 42, this.PinkGhost.getBounds().y) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x + 42, this.PinkGhost.getBounds().y + 30) == 0)) {
                this.GhostDirection = Direction.Right;
                this.PinkGhostXSpeed = 3;
                this.PinkGhostYSpeed = 0;
                AssignToField();
            }
            // -- If standard field is to the left
            else if ((this.GhostDirection!=Direction.Left)&&(this.GhostDirection!=Direction.Right)&&(this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x - 12, this.PinkGhost.getBounds().y) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x - 12, this.PinkGhost.getBounds().y + 30) == 0)) {
                this.GhostDirection = Direction.Left;
                this.PinkGhostXSpeed = -3;
                this.PinkGhostYSpeed = 0;
                AssignToField();
            }
            // -- If standard field is below
            else if (GhostGotOutOfSpawn(this.PinkGhost.getBounds().x,this.PinkGhost.getBounds().y+100)&&(this.GhostDirection!=Direction.Up)&&(this.GhostDirection!=Direction.Down)&&(this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x, this.PinkGhost.getBounds().y + 42) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.PinkGhost.getBounds().x + 30, this.PinkGhost.getBounds().y + 42) == 0)) {
                this.GhostDirection = Direction.Down;
                this.PinkGhostXSpeed = 0;
                this.PinkGhostYSpeed = 3;
                AssignToField();
            }
        }
    }



    @Override
    public void stop(){
        this.IsRunning = false;
    }

}

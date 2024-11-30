import javax.swing.*;


public class OrangeGhostMover implements Runnable, MyGUIThread {
    private boolean IsRunning;
    private final MainGameScreen gameScreen;
    private JLabel OrangeGhost;
    private int OrangeGhostXSpeed;
    private int OrangeGhostYSpeed;
    private int trialsCount;
    private Direction GhostDirection;
    private int OldCenterX;
    private int OldCenterY;
    private int MapLayoutChosen;
    private boolean IsChasing;
    private boolean GettingOutOfSpawn;

    public OrangeGhostMover(MainGameScreen MainGameScreen) {
        this.gameScreen = MainGameScreen;
        this.IsRunning = true;
        this.OrangeGhostXSpeed = 0;
        this.OrangeGhostYSpeed = 0;
        this.trialsCount = 0;
        synchronized (this.gameScreen) {
            this.OrangeGhost = this.gameScreen.getOrangeGhostEnemy();
            this.GhostDirection = this.gameScreen.getOrangeGhostDirection();
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
            if (!GhostGotOutOfSpawn(this.OrangeGhost.getBounds().x,this.OrangeGhost.getBounds().y) && this.trialsCount%30==0) {
                PickNewRandomVector();
                AssignToField();
            }
            else if(!this.IsChasing&&GhostGotOutOfSpawn(this.OrangeGhost.getBounds().x,this.OrangeGhost.getBounds().y)){
                int determinator2go = (int)(Math.random()*10);
                if(determinator2go==0) Go2StandardFieldNearby();
            }
            // --- Wall Collision Checking ---
            synchronized (this.gameScreen) {
                // --- Checking if OrangeGhost is heading for a wall
                if (this.OrangeGhostXSpeed > 0) {
                    if ( (this.gameScreen.getCurrentFieldType(this.OrangeGhost.getX() + 40, this.OrangeGhost.getY()) != 0)||
                            (this.gameScreen.getCurrentFieldType(this.OrangeGhost.getX() + 40, this.OrangeGhost.getY()+30) != 0 )
                    )  {
                        PickNewRandomVector();
                        AssignToField();
                    }
                }
            }
            synchronized (this.gameScreen) {
                if (this.OrangeGhostXSpeed < 0) {
                    if ((this.gameScreen.getCurrentFieldType(this.OrangeGhost.getX() - 10, this.OrangeGhost.getY()) != 0)||
                            (this.gameScreen.getCurrentFieldType(this.OrangeGhost.getX() - 10, this.OrangeGhost.getY()+30) != 0))  {
                        PickNewRandomVector();
                        AssignToField();
                    }
                }
            }
            synchronized (this.gameScreen) {
                if (this.OrangeGhostYSpeed > 0) {
                    if ((this.gameScreen.getCurrentFieldType(this.OrangeGhost.getX(), this.OrangeGhost.getY() + 40) != 0)
                            ||(this.gameScreen.getCurrentFieldType(this.OrangeGhost.getX()+30, this.OrangeGhost.getY() + 40) != 0)) {
                        PickNewRandomVector();
                        AssignToField();
                    }
                }
            }
            synchronized (this.gameScreen) {
                if (this.OrangeGhostYSpeed < 0) {
                    if ((this.gameScreen.getCurrentFieldType(this.OrangeGhost.getX(), this.OrangeGhost.getY() - 10) != 0)
                            || (this.gameScreen.getCurrentFieldType(this.OrangeGhost.getX()+30, this.OrangeGhost.getY() - 10) != 0)) {
                        PickNewRandomVector();
                        AssignToField();
                    }
                }
            }

            // --- Chase player if in range
            CheckAndIfChasePlayer();

            // --- Checking if ghost has stopped for whatever reason
            if(this.OrangeGhostXSpeed==0&&this.OrangeGhostYSpeed==0) {
                PickNewRandomVector();
                AssignToField();
            }

            // --- Move Orange Ghost & apply possible center frame point changes
            int xOffSet = 0;
            int yOffSet = 0;
            synchronized (this.gameScreen) {
                if (this.gameScreen.CurrentCenterX != this.OldCenterX || this.gameScreen.CurrentCenterY != this.OldCenterY) {
                    xOffSet = this.gameScreen.CurrentCenterX - this.OldCenterX;
                    yOffSet = this.gameScreen.CurrentCenterY - this.OldCenterY;
                    this.OldCenterX = this.gameScreen.CurrentCenterX;
                    this.OldCenterY = this.gameScreen.CurrentCenterY;
                }
                this.OrangeGhost.setBounds(this.OrangeGhost.getBounds().x + xOffSet + (int)(this.OrangeGhostXSpeed*this.gameScreen.getEnemySpeedMultiplier()),
                        this.OrangeGhost.getBounds().y + yOffSet + (int)(this.OrangeGhostYSpeed*this.gameScreen.getEnemySpeedMultiplier()),
                        this.OrangeGhost.getIcon().getIconWidth(), this.OrangeGhost.getIcon().getIconHeight());
                this.gameScreen.setOrangeGhostEnemy(this.OrangeGhost);
                this.gameScreen.setOrangeGhostDirection(this.GhostDirection);
            }

            this.GettingOutOfSpawn = !GhostGotOutOfSpawn(this.OrangeGhost.getBounds().x, this.OrangeGhost.getBounds().y);
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
                this.OrangeGhostXSpeed = 3;
                this.OrangeGhostYSpeed = 0;
                this.GhostDirection = Direction.Right;
                break;
            case 1: // Left
                this.OrangeGhostXSpeed = -3;
                this.OrangeGhostYSpeed = 0;
                this.GhostDirection = Direction.Left;
                break;
            case 2: // Up
                this.OrangeGhostXSpeed = 0;
                this.OrangeGhostYSpeed = -3;
                this.GhostDirection = Direction.Up;
                break;
            default: // Down
                this.OrangeGhostXSpeed = 0;
                this.OrangeGhostYSpeed = 3;
                this.GhostDirection = Direction.Down;
        }
    }

    public void AssignToField(){
        int CurrentColumn = this.gameScreen.getCurrentColumnIndex(this.OrangeGhost.getBounds().x, this.OrangeGhost.getBounds().y);
        int CurrentRow = this.gameScreen.getCurrentRowIndex(this.OrangeGhost.getBounds().x, this.OrangeGhost.getBounds().y);
        this.OrangeGhost.setBounds(this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().x+10,
                this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().y+10,
                this.OrangeGhost.getIcon().getIconWidth(), this.OrangeGhost.getIcon().getIconHeight());
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
            DeltaX=this.OrangeGhost.getBounds().x-this.gameScreen.getPacMan().getBounds().x;
            DeltaY=this.OrangeGhost.getBounds().y-this.gameScreen.getPacMan().getBounds().y;
        }
        if(
                (Math.abs(DeltaY)<=15)&&(Math.abs(DeltaX)<=1000)){
            synchronized (this.gameScreen) {
                int distance = 0;
                while(Math.abs(distance)<Math.abs(DeltaX)){
                    if(this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x+distance, this.OrangeGhost.getBounds().y)!=0){
                        return;
                    }
                    distance+=(DeltaX<0)?30:-30;
                }
                if (DeltaX > 0&&(this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x-20, this.OrangeGhost.getBounds().y)==0)
                        &&(this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x-20, this.OrangeGhost.getBounds().y+30)==0)) {
                    this.GhostDirection = Direction.Left;
                    this.OrangeGhostXSpeed = -3;
                    this.OrangeGhostYSpeed = 0;
                    if (!this.IsChasing) AssignToField();
                    this.IsChasing = true;
                } else if((this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x+50, this.OrangeGhost.getBounds().y)==0)
                        &&(this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x+50, this.OrangeGhost.getBounds().y+30)==0)){
                    this.GhostDirection = Direction.Right;
                    this.OrangeGhostXSpeed = 3;
                    this.OrangeGhostYSpeed = 0;
                    if (!this.IsChasing) AssignToField();
                    this.IsChasing = true;
                }
            }
        }else if(
                (Math.abs(DeltaX)<=15)&&(Math.abs(DeltaY)<=1000)) {
            synchronized (this.gameScreen) {
                int distance = 0;
                while(Math.abs(distance)<Math.abs(DeltaY)){
                    if(this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x, this.OrangeGhost.getBounds().y+distance)!=0){
                        return;
                    }
                    distance+=(DeltaY<0)?30:-30;
                }
                if (DeltaY > 0 && (this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x, this.OrangeGhost.getBounds().y - 20) == 0) &&
                        (this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x + 30, this.OrangeGhost.getBounds().y - 20) == 0)) {
                    this.GhostDirection = Direction.Up;
                    this.OrangeGhostXSpeed = 0;
                    this.OrangeGhostYSpeed = -3;
                    if (!this.IsChasing) AssignToField();
                    this.IsChasing = true;
                } else if ((this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x, this.OrangeGhost.getBounds().y + 50) == 0) &&
                        (this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x + 30, this.OrangeGhost.getBounds().y + 50) == 0)) {
                    this.GhostDirection = Direction.Down;
                    this.OrangeGhostXSpeed = 0;
                    this.OrangeGhostYSpeed = 3;
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
            GhostRow = this.gameScreen.getCurrentRowIndex(this.OrangeGhost.getBounds().x, this.OrangeGhost.getBounds().y);
            GhostCol = this.gameScreen.getCurrentColumnIndex(this.OrangeGhost.getBounds().x, this.OrangeGhost.getBounds().y);
            // -- If standard field is above
            if ((this.GhostDirection!=Direction.Up)&&(this.GhostDirection!=Direction.Down)&&(this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x, this.OrangeGhost.getBounds().y - 12) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x + 30, this.OrangeGhost.getBounds().y - 12) == 0)) {
                this.GhostDirection = Direction.Up;
                this.OrangeGhostXSpeed = 0;
                this.OrangeGhostYSpeed = -3;
                AssignToField();
                // -- If standard field is to the right
            } else if ((this.GhostDirection!=Direction.Left)&&(this.GhostDirection!=Direction.Right)&&(this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x + 42, this.OrangeGhost.getBounds().y) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x + 42, this.OrangeGhost.getBounds().y + 30) == 0)) {
                this.GhostDirection = Direction.Right;
                this.OrangeGhostXSpeed = 3;
                this.OrangeGhostYSpeed = 0;
                AssignToField();
            }
            // -- If standard field is to the left
            else if ((this.GhostDirection!=Direction.Left)&&(this.GhostDirection!=Direction.Right)&&(this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x - 12, this.OrangeGhost.getBounds().y) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x - 12, this.OrangeGhost.getBounds().y + 30) == 0)) {
                this.GhostDirection = Direction.Left;
                this.OrangeGhostXSpeed = -3;
                this.OrangeGhostYSpeed = 0;
                AssignToField();
            }
            // -- If standard field is below
            else if (GhostGotOutOfSpawn(this.OrangeGhost.getBounds().x,this.OrangeGhost.getBounds().y+100)&&(this.GhostDirection!=Direction.Up)&&(this.GhostDirection!=Direction.Down)&&(this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x, this.OrangeGhost.getBounds().y + 42) == 0)
                    && (this.gameScreen.getCurrentFieldType(this.OrangeGhost.getBounds().x + 30, this.OrangeGhost.getBounds().y + 42) == 0)) {
                this.GhostDirection = Direction.Down;
                this.OrangeGhostXSpeed = 0;
                this.OrangeGhostYSpeed = 3;
                AssignToField();
            }
        }
    }

    @Override
    public void stop(){
        this.IsRunning = false;
    }

}

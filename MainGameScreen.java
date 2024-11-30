import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainGameScreen extends MyFrame {
    // --- Stuff
    private int[][] MapLayoutLoaded;
    private final int MapLayoutChosen;
    private JLabel[][] Map;
    private int mapZeroPointX;
    private int mapZeroPointY;
    private JLabel PacMan;
    private int PacManXVelocity;
    private int PacManYVelocity;
    private JLayeredPane MapWithStuff;
    private Direction PacManDirection;
    private int[][] Points2PickUpMap;
    private JLabel[][] Points2PickUpImages;
    private int PlayerPoints;
    private JLabel PlayerPointsDisplayer;
    private JLabel PlayerPointsDisplayer2;
    private JLabel PinkGhostEnemy;
    private Direction PinkGhostDirection;
    private JLabel OrangeGhostEnemy;
    private Direction OrangeGhostDirection;
    private JLabel BlueGhostEnemy;
    private Direction BlueGhostDirection;
    private JLabel RedGhostEnemy;
    private Direction RedGhostDirection;
    private int PlayerLifesLeft;
    private JLabel PlayerLifesDisplayer;
    private boolean PlayerImmortal;
    private boolean GameIsRestarting;
    private int PointsMultiplier;
    private JLabel CherryIndicator;
    private JLabel AppleIndicator;
    private JLabel PeachIndicator;
    private JLabel StrawberryIndicator;
    private JLabel MedallionIndicator;
    private int PlayerSpeedMultiplier;
    private float EnemySpeedMultiplier;
    private boolean RolesSwitched;

    // --- Threads
    private Thread centerPointUpdaterThread;
    private Thread pacManEaterCheckerThread;
    private Thread pacManAnimatorThread;
    private Thread wallCollisionCheckerThread;
    private Thread stuffPositionsUpdaterThread;
    private Thread pacManPositionsUpdaterThread;
    private Thread pinkGhostAnimatorThread;
    private Thread pinkGhostMoverThread;
    private Thread orangeGhostAnimatorThread;
    private Thread orangeGhostMoverThread;
    private Thread blueGhostAnimatorThread;
    private Thread blueGhostMoverThread;
    private Thread redGhostAnimatorThread;
    private Thread redGhostMoverThread;
    private Thread playerAndGhostsCollisionManagerThread;
    private Thread consumablesDropperThread;

    private FrameCenterPointUpdater centerPointUpdater;
    private PacManEaterChecker pacManEaterChecker;
    private PacManAnimator PacManAnimator;
    private PackManWallCollisionChecker packManWallCollisionChecker;
    private MainGameStuffPositionUpdater stuffPositionsUpdater;
    private PacManMover pacManMover;
    private PinkGhostAnimator pinkGhostAnimator;
    private PinkGhostMover pinkGhostMover;
    private OrangeGhostAnimator orangeGhostAnimator;
    private OrangeGhostMover orangeGhostMover;
    private BlueGhostAnimator blueGhostAnimator;
    private BlueGhostMover blueGhostMover;
    private RedGhostAnimator redGhostAnimator;
    private RedGhostMover redGhostMover;
    private PlayerAndGhostsCollisionManager playerAndGhostsCollisionManager;
    private ConsumablesDropper consumablesDropper;


    public MainGameScreen(int MapLayoutChosen, int PlayerPointsTaken, int PlayerLifesGiven) {
        // --- Basic Game Frame settings
        super("Pac-Man Main Game");
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
        switch (MapLayoutChosen){
            case 5:
                this.setMinimumSize(new Dimension(1260, 737));
                break;
            case 1:
                this.setMinimumSize(new Dimension(1722, 988));
                break;
            case 2:
                this.setMinimumSize(new Dimension(1715, 738));
                break;
            case 3:
                this.setMinimumSize(new Dimension(1208, 927));
                break;
            default:
                this.setMinimumSize(new Dimension(1470, 847));
        }
        this.CurrentCenterX = this.getWidth() / 2;
        this.CurrentCenterY = this.getHeight() / 2;
        this.MapLayoutChosen = MapLayoutChosen;

        // --- Player has 0 points on start (on default)
        this.PlayerPoints = PlayerPointsTaken;
        this.PointsMultiplier = 1;

        // --- Player has 4 lifes on start
        this.PlayerLifesLeft = PlayerLifesGiven;

        // --- Player is not immortal on start
        this.PlayerImmortal = false;

        //--- Game's not restarting at the beginning
        this.GameIsRestarting = false;

        // --- Player & enemies have speed multiplier on 1 at the beginning
        this.PlayerSpeedMultiplier = 1;
        this.EnemySpeedMultiplier = 1;

        // --- Roles are not switched at the beginning
        this.RolesSwitched = false;

        // --- Setting the frame background to black
        JPanel BackGroundPanel = new JPanel();
        BackGroundPanel.setLayout(null);
        BackGroundPanel.setBackground(new java.awt.Color(0, 0, 0));
        this.setContentPane(BackGroundPanel);

        // --- Creating Layer Pane for map with stuff on it
        this.MapWithStuff = new JLayeredPane();
        this.MapWithStuff.setBounds(0, 0, 1920, 1080);
        BackGroundPanel.add(this.MapWithStuff);

        // --- Starting the thread setting current frame center point
        startCenterPointUpdater();

        // --- Loading appropriate map layout from data
        switch(MapLayoutChosen){
            case 1:
                this.MapLayoutLoaded = Data.DataMap1Layout;
                break;
            case 2:
                this.MapLayoutLoaded = Data.DataMap2Layout;
                break;
            case 3:
                this.MapLayoutLoaded = Data.DataMap3Layout;
                break;
            case 4:
                this.MapLayoutLoaded = Data.DataMap4Layout;
                break;
            case 5:
                this.MapLayoutLoaded = Data.DataMap5Layout;
                break;
        }

        // --- Determining the start point of the map
        this.mapZeroPointX = this.CurrentCenterX - (MapLayoutLoaded[0].length * 25);
        this.mapZeroPointY = this.CurrentCenterY - (MapLayoutLoaded.length * 25);

        // --- Player Current Points Displaying1
        this.PlayerPointsDisplayer = new JLabel("Points:");
        this.PlayerPointsDisplayer.setBounds(mapZeroPointX-200,mapZeroPointY,200,75);
        this.PlayerPointsDisplayer.setFont(new javax.swing.plaf.FontUIResource("Arial", javax.swing.plaf.FontUIResource.BOLD, 45));
        this.PlayerPointsDisplayer.setForeground(java.awt.Color.WHITE);
        this.PlayerPointsDisplayer.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(this.PlayerPointsDisplayer);

        // --- Player Current Points Displaying2
        this.PlayerPointsDisplayer2 = new JLabel(""+this.PlayerPoints);
        this.PlayerPointsDisplayer2.setBounds(mapZeroPointX-200,mapZeroPointY+50,200,75);
        this.PlayerPointsDisplayer2.setFont(new javax.swing.plaf.FontUIResource("Arial", javax.swing.plaf.FontUIResource.BOLD, 45));
        this.PlayerPointsDisplayer2.setForeground(java.awt.Color.WHITE);
        this.PlayerPointsDisplayer2.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(this.PlayerPointsDisplayer2);


        // --- Generating map & indicating points
        this.Map = new JLabel[MapLayoutLoaded.length][MapLayoutLoaded[0].length];
        this.Points2PickUpMap = new int[MapLayoutLoaded.length][MapLayoutLoaded[0].length];
        GenerateMap();
        this.Points2PickUpImages = new JLabel[MapLayoutLoaded.length][MapLayoutLoaded[0].length];
        setUpPointsImages();

        // --- Player Current Lifes Displaying
        this.PlayerLifesDisplayer = new JLabel("Lifes: "+this.PlayerLifesLeft);
        this.PlayerLifesDisplayer.setBounds(mapZeroPointX-220,mapZeroPointY+100,250,100);
        this.PlayerLifesDisplayer.setFont(new javax.swing.plaf.FontUIResource("Arial", javax.swing.plaf.FontUIResource.BOLD, 45));
        this.PlayerLifesDisplayer.setForeground(java.awt.Color.WHITE);
        this.PlayerLifesDisplayer.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(this.PlayerLifesDisplayer);

        // --- Player Cherry Usage Indicator
        this.CherryIndicator = new JLabel(Data.DataTransparentSquareN1);
        this.CherryIndicator.setBounds(mapZeroPointX-100,mapZeroPointY+310,50,50);
        this.add(this.CherryIndicator);

        // --- Player Apple Usage Indicator
        this.AppleIndicator = new JLabel(Data.DataTransparentSquareN1);
        this.AppleIndicator.setBounds(mapZeroPointX-100,mapZeroPointY+370,50,50);
        this.add(this.AppleIndicator);

        // --- Player Peach Usage Indicator
        this.PeachIndicator = new JLabel(Data.DataTransparentSquareN1);
        this.PeachIndicator.setBounds(mapZeroPointX-100,mapZeroPointY+430,50,50);
        this.add(this.PeachIndicator);

        // --- Player Strawberry Usage Indicator
        this.StrawberryIndicator = new JLabel(Data.DataTransparentSquareN1);
        this.StrawberryIndicator.setBounds(mapZeroPointX-100,mapZeroPointY+490,50,50);
        this.add(this.StrawberryIndicator);

        // --- Player Medallion Usage Indicator
        this.MedallionIndicator = new JLabel(Data.DataTransparentSquareN1);
        this.MedallionIndicator.setBounds(mapZeroPointX-100,mapZeroPointY+250,50,50);
        this.add(this.MedallionIndicator);

        //---- Setting Pac Man Image
        this.PacMan = new JLabel();
        this.PacMan.setIcon(Data.DataPacMan1ImageR);
        // --- PacMan Start position is the first standard field
        int PacManCurrentField = 1;
        int PacManX = mapZeroPointX;
        int PacManY = mapZeroPointY;
        // --- Searching for first standard field
        zew:
        for(int i = 0; i < this.Map.length; i++) {
            for (int j = 0; j < this.Map[0].length; j++) {
                PacManCurrentField = getCurrentFieldType(this.Map[i][j].getBounds().x, this.Map[i][j].getBounds().y);
                if (PacManCurrentField == 0) {
                    PacManX = this.Map[i][j].getBounds().x;
                    PacManY = this.Map[i][j].getBounds().y;
                    break zew;
                }
            }
        }
        this.PacMan.setBounds(PacManX+10, PacManY+10, Data.DataPacMan1ImageR.getIconWidth(), Data.DataPacMan1ImageR.getIconHeight());
        this.MapWithStuff.add(this.PacMan, Integer.valueOf(3));
        // --- PacMan Speed is on default 0,0 and Direction towards right
        this.PacManDirection = Direction.Right;
        this.PacManXVelocity = 0;
        this.PacManYVelocity = 0;

        // --- Creating Pink Ghost ---
        // --- Pink ghost start position is fixed and dependent on map chosen
        this.PinkGhostEnemy = new JLabel(Data.DataGhostPink1R);
        if(this.MapLayoutChosen==1){
            this.PinkGhostEnemy.setBounds(this.Map[9][9].getBounds().x + 10, this.Map[9][9].getBounds().y + 10, 50, 50);
        }
        else if(this.MapLayoutChosen==2) {
            this.PinkGhostEnemy.setBounds(this.Map[5][8].getBounds().x + 10, this.Map[5][8].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==3) {
            this.PinkGhostEnemy.setBounds(this.Map[8][5].getBounds().x + 10, this.Map[8][5].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==4) {
            this.PinkGhostEnemy.setBounds(this.Map[7][6].getBounds().x + 10, this.Map[7][6].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==5) {
            this.PinkGhostEnemy.setBounds(this.Map[5][5].getBounds().x + 10, this.Map[5][5].getBounds().y + 10, 50, 50);
        }
        // --- Adding Pink Ghost
        this.MapWithStuff.add(this.PinkGhostEnemy,Integer.valueOf(3));
        // --- Starting Pink Ghost Animator
        this.PinkGhostDirection = Direction.Right;
        StartPinkGhostAnimator();
        StartPinkGhostMover();

    // --- Creating Orange Ghost ---
    // --- Orange ghost start position is fixed and dependent on map chosen
        this.OrangeGhostEnemy = new JLabel(Data.DataGhostOrange1R);
        if(this.MapLayoutChosen==1){
            this.OrangeGhostEnemy.setBounds(this.Map[9][9].getBounds().x + 10, this.Map[9][9].getBounds().y + 10, 50, 50);
        }
        if(this.MapLayoutChosen==2) {
            this.OrangeGhostEnemy.setBounds(this.Map[5][9].getBounds().x + 10, this.Map[5][9].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==3) {
            this.OrangeGhostEnemy.setBounds(this.Map[8][5].getBounds().x + 10, this.Map[8][5].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==4) {
            this.OrangeGhostEnemy.setBounds(this.Map[7][6].getBounds().x + 10, this.Map[7][6].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==5) {
            this.OrangeGhostEnemy.setBounds(this.Map[1][11].getBounds().x + 10, this.Map[1][11].getBounds().y + 10, 50, 50);
        }
    // --- Adding Orange Ghost
        this.MapWithStuff.add(this.OrangeGhostEnemy, Integer.valueOf(3));
    // --- Starting Orange Ghost Animator
        this.OrangeGhostDirection = Direction.Right;
        StartOrangeGhostAnimator();
        StartOrangeGhostMover();

    // --- Creating Blue Ghost ---
    // --- Blue ghost start position is fixed and dependent on map chosen
        this.BlueGhostEnemy = new JLabel(Data.DataGhostBlue1R);
        if(this.MapLayoutChosen==1){
            this.BlueGhostEnemy.setBounds(this.Map[9][9].getBounds().x + 10, this.Map[9][9].getBounds().y + 10, 50, 50);
        }
        if(this.MapLayoutChosen==2) {
            this.BlueGhostEnemy.setBounds(this.Map[5][10].getBounds().x + 10, this.Map[5][10].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==3) {
            this.BlueGhostEnemy.setBounds(this.Map[8][5].getBounds().x + 10, this.Map[8][5].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==4) {
            this.BlueGhostEnemy.setBounds(this.Map[7][6].getBounds().x + 10, this.Map[7][6].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==5) {
            this.BlueGhostEnemy.setBounds(this.Map[9][1].getBounds().x + 10, this.Map[9][1].getBounds().y + 10, 50, 50);
        }
    // --- Adding Blue Ghost
        this.MapWithStuff.add(this.BlueGhostEnemy, Integer.valueOf(3));
    // --- Starting Blue Ghost Animator
        this.BlueGhostDirection = Direction.Right;
        StartBlueGhostAnimator();
        StartBlueGhostMover();

    // --- Creating Red Ghost ---
    // --- Red ghost start position is fixed and dependent on map chosen
        this.RedGhostEnemy = new JLabel(Data.DataGhostRed1R);
        if(this.MapLayoutChosen==1){
            this.RedGhostEnemy.setBounds(this.Map[9][9].getBounds().x + 10, this.Map[9][9].getBounds().y + 10, 50, 50);
        }
        if(this.MapLayoutChosen==2) {
            this.RedGhostEnemy.setBounds(this.Map[5][11].getBounds().x + 10, this.Map[5][11].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==3) {
            this.RedGhostEnemy.setBounds(this.Map[8][5].getBounds().x + 10, this.Map[8][5].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==4) {
            this.RedGhostEnemy.setBounds(this.Map[7][6].getBounds().x + 10, this.Map[7][6].getBounds().y + 10, 50, 50);
        }else if(this.MapLayoutChosen==5) {
            this.RedGhostEnemy.setBounds(this.Map[9][11].getBounds().x + 10, this.Map[9][11].getBounds().y + 10, 50, 50);
        }
    // --- Adding Red Ghost
        this.MapWithStuff.add(this.RedGhostEnemy, Integer.valueOf(3));
    // --- Starting Red Ghost Animator
        this.RedGhostDirection = Direction.Right;

        this.setSize(1920, 1080);
        StartRedGhostAnimator();
        StartRedGhostMover();

        // --- Starting Objects positioning
        startStuffPositionsUpdater();
        StartPacManMovement();
        StartWallCollisionChecker();
        StartPacManAnimator();
        StartPacManEaterChecker();
        StartPlayerAndGhostCollisionManager();
        StartConsumablesDropper();
        // --- Calling the function to set up PacMan control of movement
        setupKeyBindings();

        // --- Displaying the frame & enable ESC key
        addEscKeyListener();
        this.setVisible(true);
    }

    // --- Functions to start threads
    private void startCenterPointUpdater() {
        centerPointUpdater = new FrameCenterPointUpdater(this, this.CurrentCenterX, this.CurrentCenterY);
        centerPointUpdaterThread = new Thread(centerPointUpdater);
        centerPointUpdaterThread.start();
    }
    private void startStuffPositionsUpdater() {
        stuffPositionsUpdater = new MainGameStuffPositionUpdater(this);
        stuffPositionsUpdaterThread = new Thread(stuffPositionsUpdater);
        stuffPositionsUpdaterThread.start();
    }
    private void StartPacManMovement() {
        pacManMover = new PacManMover(this);
        pacManPositionsUpdaterThread = new Thread(pacManMover);
        pacManPositionsUpdaterThread.start();
    }
    private void StartWallCollisionChecker(){
        packManWallCollisionChecker = new PackManWallCollisionChecker(this);
        wallCollisionCheckerThread = new Thread(packManWallCollisionChecker);
        wallCollisionCheckerThread.start();
    }
    private void StartPacManAnimator(){
        PacManAnimator = new PacManAnimator(this);
        pacManAnimatorThread = new Thread(PacManAnimator);
        pacManAnimatorThread.start();
    }
    private void StartPacManEaterChecker(){
        pacManEaterChecker = new PacManEaterChecker(this);
        pacManEaterCheckerThread = new Thread(pacManEaterChecker);
        pacManEaterCheckerThread.start();
    }
    private void StartPinkGhostAnimator(){
        pinkGhostAnimator = new PinkGhostAnimator(this);
        pinkGhostAnimatorThread = new Thread(pinkGhostAnimator);
        pinkGhostAnimatorThread.start();
    }
    private void StartPinkGhostMover(){
        pinkGhostMover = new PinkGhostMover(this);
        pinkGhostMoverThread = new Thread(pinkGhostMover);
        pinkGhostMoverThread.start();
    }
    private void StartOrangeGhostAnimator(){
        orangeGhostAnimator = new OrangeGhostAnimator(this);
        orangeGhostAnimatorThread = new Thread(orangeGhostAnimator);
        orangeGhostAnimatorThread.start();
    }
    private void StartOrangeGhostMover(){
        orangeGhostMover = new OrangeGhostMover(this);
        orangeGhostMoverThread = new Thread(orangeGhostMover);
        orangeGhostMoverThread.start();
    }
    private void StartBlueGhostAnimator(){
        blueGhostAnimator = new BlueGhostAnimator(this);
        blueGhostAnimatorThread = new Thread(blueGhostAnimator);
        blueGhostAnimatorThread.start();
    }
    private void StartBlueGhostMover(){
        blueGhostMover = new BlueGhostMover(this);
        blueGhostMoverThread = new Thread(blueGhostMover);
        blueGhostMoverThread.start();
    }
    private void StartRedGhostAnimator(){
        redGhostAnimator = new RedGhostAnimator(this);
        redGhostAnimatorThread = new Thread(redGhostAnimator);
        redGhostAnimatorThread.start();
    }
    private void StartRedGhostMover(){
        redGhostMover = new RedGhostMover(this);
        redGhostMoverThread = new Thread(redGhostMover);
        redGhostMoverThread.start();
    }
    private void StartPlayerAndGhostCollisionManager(){
        playerAndGhostsCollisionManager = new PlayerAndGhostsCollisionManager(this);
        playerAndGhostsCollisionManagerThread = new Thread(playerAndGhostsCollisionManager);
        playerAndGhostsCollisionManagerThread.start();
    }
    private void StartConsumablesDropper(){
        consumablesDropper = new ConsumablesDropper(this);
        consumablesDropperThread = new Thread(consumablesDropper);
        consumablesDropperThread.start();
    }

    // --- Function to generate the map&points map ---
    public void GenerateMap() {
        for (int i = 0; i < MapLayoutLoaded.length; i++) {
            for (int j = 0; j < MapLayoutLoaded[i].length; j++) {
                this.Map[i][j] = new JLabel();
                ImageIcon mapField = switch (MapLayoutLoaded[i][j]) {
                    case 0 -> Data.DataFieldSquare0;
                    case 1 -> Data.DataWallRock1;
                    case 2 -> Data.DataWallSideHorizontal2;
                    case 3 -> Data.DataWallSideVertical3;
                    case 4 -> Data.DataWallJoinerLowerLeft4;
                    case 5 -> Data.DataWallJoinerLowerRight5;
                    case 6 -> Data.DataWallJoinerUpperLeft6;
                    default -> Data.DataWallJoinerUpperRight7;
                };
                // --- Generating points indicators
                if(MapLayoutLoaded[i][j]==0){
                    this.Points2PickUpMap[i][j] = 1;
                }else this.Points2PickUpMap[i][j] = -1;
                this.Map[i][j].setIcon(mapField);
                this.Map[i][j].setBounds(this.mapZeroPointX + (50 * j), this.mapZeroPointY + (50 * i), 50, 50);
                this.MapWithStuff.add(this.Map[i][j], Integer.valueOf(1));
            }
        }
    }

    // -1 = Transparent square, 1 = point, 2 = cherry, 3 = apple, 4 = peach, 5 = strawberry, 6 = medallion
    public void setUpPointsImages(){
        for (int i = 0; i < MapLayoutLoaded.length; i++) {
            for (int j = 0; j < MapLayoutLoaded[i].length; j++) {
                if(this.Points2PickUpMap[i][j]==-1){
                    this.Points2PickUpImages[i][j] = new JLabel(Data.DataTransparentSquareN1);
                    this.Points2PickUpImages[i][j].setBounds(this.mapZeroPointX + (50 * j), this.mapZeroPointY + (50 * i), 50, 50);
                    this.MapWithStuff.add(this.Points2PickUpImages[i][j], Integer.valueOf(2));
                }
            else if(this.Points2PickUpMap[i][j]==1){
                this.Points2PickUpImages[i][j] = new JLabel(Data.DataPoint2PickUp);
                this.Points2PickUpImages[i][j].setBounds(this.mapZeroPointX + (50 * j), this.mapZeroPointY + (50 * i), 50, 50);
                this.MapWithStuff.add(this.Points2PickUpImages[i][j], Integer.valueOf(2));
            }else if(this.Points2PickUpMap[i][j]==2){
                    this.Points2PickUpImages[i][j] = new JLabel(Data.DataCherry);
                    this.Points2PickUpImages[i][j].setBounds(this.mapZeroPointX + (50 * j), this.mapZeroPointY + (50 * i), 50, 50);
                    this.MapWithStuff.add(this.Points2PickUpImages[i][j], Integer.valueOf(3));
                }else if(this.Points2PickUpMap[i][j]==3){
                    this.Points2PickUpImages[i][j] = new JLabel(Data.DataApple);
                    this.Points2PickUpImages[i][j].setBounds(this.mapZeroPointX + (50 * j), this.mapZeroPointY + (50 * i), 50, 50);
                    this.MapWithStuff.add(this.Points2PickUpImages[i][j], Integer.valueOf(3));
                }else if(this.Points2PickUpMap[i][j]==4){
                    this.Points2PickUpImages[i][j] = new JLabel(Data.DataPeach);
                    this.Points2PickUpImages[i][j].setBounds(this.mapZeroPointX + (50 * j), this.mapZeroPointY + (50 * i), 50, 50);
                    this.MapWithStuff.add(this.Points2PickUpImages[i][j], Integer.valueOf(3));
                }else if(this.Points2PickUpMap[i][j]==5){
                    this.Points2PickUpImages[i][j] = new JLabel(Data.DataStrawberry);
                    this.Points2PickUpImages[i][j].setBounds(this.mapZeroPointX + (50 * j), this.mapZeroPointY + (50 * i), 50, 50);
                    this.MapWithStuff.add(this.Points2PickUpImages[i][j], Integer.valueOf(3));
                }else if(this.Points2PickUpMap[i][j]==6){
                    this.Points2PickUpImages[i][j] = new JLabel(Data.DataMedallion);
                    this.Points2PickUpImages[i][j].setBounds(this.mapZeroPointX + (50 * j), this.mapZeroPointY + (50 * i), 50, 50);
                    this.MapWithStuff.add(this.Points2PickUpImages[i][j], Integer.valueOf(3));
                }
            }
        }
    }

    public void RestartGame(int Points2Take, int PlayerLifesLeft) {
            int CurrentFrameX = this.getLocation().x;
            int CurrentFrameY = this.getLocation().y;
            stopUpdaters();
            if(PlayerLifesLeft!=0) {
                SwingUtilities.invokeLater(() -> {
                    this.dispose();
                    MainGameScreen gameScreen = new MainGameScreen(this.MapLayoutChosen, Points2Take, PlayerLifesLeft);
                    gameScreen.setLocation(CurrentFrameX, CurrentFrameY);
                    gameScreen.setVisible(true);
                });
            }else{
                this.dispose();
                TypeInYourScore typeInYourScoreScreen = new TypeInYourScore(Points2Take);
                typeInYourScoreScreen.setVisible(true);
            }
    }

    public void AnyPointsLeft() {
        if(!this.GameIsRestarting) {
            boolean AnyPointsLeft = false;
            zew:
            for (int i = 0; i < this.Points2PickUpMap.length; i++) {
                for (int j = 0; j < this.Points2PickUpMap[i].length; j++) {
                    if (this.Points2PickUpMap[i][j] == 1) {
                        AnyPointsLeft = true;
                        break zew;
                    }
                }
            }
            if (!AnyPointsLeft) {
                this.GameIsRestarting = true;
                SwingUtilities.invokeLater(() -> RestartGame(this.PlayerPoints, this.PlayerLifesLeft));
            }
        }
    }

    public void PacManAndGhostCollisionChecker() {
        if(!this.GameIsRestarting&&!this.PlayerImmortal&&!this.RolesSwitched) {
            int deltaX2Pink = Math.abs(this.PinkGhostEnemy.getX() - this.PacMan.getX());
            int deltaY2Pink = Math.abs(this.PinkGhostEnemy.getY() - this.PacMan.getY());
            if (Math.sqrt(deltaX2Pink * deltaX2Pink + deltaY2Pink * deltaY2Pink) <= 30) {
                this.GameIsRestarting = true;
                SwingUtilities.invokeLater(() -> RestartGame(this.PlayerPoints, this.PlayerLifesLeft - 1));
            } else {
                int deltaX2Orange = Math.abs(this.OrangeGhostEnemy.getX() - this.PacMan.getX());
                int deltaY2Orange = Math.abs(this.OrangeGhostEnemy.getY() - this.PacMan.getY());
                if (Math.sqrt(deltaX2Orange * deltaX2Orange + deltaY2Orange * deltaY2Orange) <= 30) {
                    this.GameIsRestarting = true;
                    SwingUtilities.invokeLater(() -> RestartGame(this.PlayerPoints, this.PlayerLifesLeft - 1));
                } else {
                    int deltaX2Blue = Math.abs(this.BlueGhostEnemy.getX() - this.PacMan.getX());
                    int deltaY2Blue = Math.abs(this.BlueGhostEnemy.getY() - this.PacMan.getY());
                    if (Math.sqrt(deltaX2Blue * deltaX2Blue + deltaY2Blue * deltaY2Blue) <= 30) {
                        this.GameIsRestarting = true;
                        SwingUtilities.invokeLater(() -> RestartGame(this.PlayerPoints, this.PlayerLifesLeft - 1));
                    } else {
                        int deltaX2Red = Math.abs(this.RedGhostEnemy.getX() - this.PacMan.getX());
                        int deltaY2Red = Math.abs(this.RedGhostEnemy.getY() - this.PacMan.getY());
                        if (Math.sqrt(deltaX2Red * deltaX2Red + deltaY2Red * deltaY2Red) <= 30) {
                            this.GameIsRestarting = true;
                            SwingUtilities.invokeLater(() -> RestartGame(this.PlayerPoints, this.PlayerLifesLeft - 1));
                        }
                    }
                }
            }
        }else if(this.RolesSwitched){
            int deltaX2Pink = Math.abs(this.PinkGhostEnemy.getX() - this.PacMan.getX());
            int deltaY2Pink = Math.abs(this.PinkGhostEnemy.getY() - this.PacMan.getY());
            if (Math.sqrt(deltaX2Pink * deltaX2Pink + deltaY2Pink * deltaY2Pink) <= 30) {
                if(this.MapLayoutChosen==1){
                    this.PinkGhostEnemy.setBounds(this.Map[9][9].getBounds().x + 10, this.Map[9][9].getBounds().y + 10, 50, 50);
                }
                if(this.MapLayoutChosen==2) {
                    this.PinkGhostEnemy.setBounds(this.Map[5][8].getBounds().x + 10, this.Map[5][8].getBounds().y + 10, 50, 50);
                }else if(this.MapLayoutChosen==3) {
                    this.PinkGhostEnemy.setBounds(this.Map[8][5].getBounds().x + 10, this.Map[8][5].getBounds().y + 10, 50, 50);
                }else if(this.MapLayoutChosen==4) {
                    this.PinkGhostEnemy.setBounds(this.Map[7][6].getBounds().x + 10, this.Map[7][6].getBounds().y + 10, 50, 50);
                }else if(this.MapLayoutChosen==5) {
                    this.PinkGhostEnemy.setBounds(this.Map[9][11].getBounds().x + 10, this.Map[9][11].getBounds().y + 10, 50, 50);
                }
                this.PlayerPoints+=500;
            } else {
                int deltaX2Orange = Math.abs(this.OrangeGhostEnemy.getX() - this.PacMan.getX());
                int deltaY2Orange = Math.abs(this.OrangeGhostEnemy.getY() - this.PacMan.getY());
                if (Math.sqrt(deltaX2Orange * deltaX2Orange + deltaY2Orange * deltaY2Orange) <= 30) {
                    if(this.MapLayoutChosen==1){
                        this.OrangeGhostEnemy.setBounds(this.Map[9][9].getBounds().x + 10, this.Map[9][9].getBounds().y + 10, 50, 50);
                    }
                    if(this.MapLayoutChosen==2) {
                        this.OrangeGhostEnemy.setBounds(this.Map[5][9].getBounds().x + 10, this.Map[5][9].getBounds().y + 10, 50, 50);
                    }else if(this.MapLayoutChosen==3) {
                        this.OrangeGhostEnemy.setBounds(this.Map[8][5].getBounds().x + 10, this.Map[8][5].getBounds().y + 10, 50, 50);
                    }else if(this.MapLayoutChosen==4) {
                        this.OrangeGhostEnemy.setBounds(this.Map[7][6].getBounds().x + 10, this.Map[7][6].getBounds().y + 10, 50, 50);
                    }else if(this.MapLayoutChosen==5) {
                        this.OrangeGhostEnemy.setBounds(this.Map[9][11].getBounds().x + 10, this.Map[9][11].getBounds().y + 10, 50, 50);
                    }
                    this.PlayerPoints+=500;
                } else {
                    int deltaX2Blue = Math.abs(this.BlueGhostEnemy.getX() - this.PacMan.getX());
                    int deltaY2Blue = Math.abs(this.BlueGhostEnemy.getY() - this.PacMan.getY());
                    if (Math.sqrt(deltaX2Blue * deltaX2Blue + deltaY2Blue * deltaY2Blue) <= 30) {
                        if(this.MapLayoutChosen==1){
                            this.BlueGhostEnemy.setBounds(this.Map[9][9].getBounds().x + 10, this.Map[9][9].getBounds().y + 10, 50, 50);
                        }
                        if(this.MapLayoutChosen==2) {
                            this.BlueGhostEnemy.setBounds(this.Map[5][10].getBounds().x + 10, this.Map[5][10].getBounds().y + 10, 50, 50);
                        }else if(this.MapLayoutChosen==3) {
                            this.BlueGhostEnemy.setBounds(this.Map[8][5].getBounds().x + 10, this.Map[8][5].getBounds().y + 10, 50, 50);
                        }else if(this.MapLayoutChosen==4) {
                            this.BlueGhostEnemy.setBounds(this.Map[7][6].getBounds().x + 10, this.Map[7][6].getBounds().y + 10, 50, 50);
                        }else if(this.MapLayoutChosen==5) {
                            this.BlueGhostEnemy.setBounds(this.Map[9][11].getBounds().x + 10, this.Map[9][11].getBounds().y + 10, 50, 50);
                        }
                        this.PlayerPoints+=500;
                    } else {
                        int deltaX2Red = Math.abs(this.RedGhostEnemy.getX() - this.PacMan.getX());
                        int deltaY2Red = Math.abs(this.RedGhostEnemy.getY() - this.PacMan.getY());
                        if (Math.sqrt(deltaX2Red * deltaX2Red + deltaY2Red * deltaY2Red) <= 30) {
                            if(this.MapLayoutChosen==1){
                                this.RedGhostEnemy.setBounds(this.Map[9][9].getBounds().x + 10, this.Map[9][9].getBounds().y + 10, 50, 50);
                            }
                            if(this.MapLayoutChosen==2) {
                                this.RedGhostEnemy.setBounds(this.Map[5][11].getBounds().x + 10, this.Map[5][11].getBounds().y + 10, 50, 50);
                            }else if(this.MapLayoutChosen==3) {
                                this.RedGhostEnemy.setBounds(this.Map[8][5].getBounds().x + 10, this.Map[8][5].getBounds().y + 10, 50, 50);
                            }else if(this.MapLayoutChosen==4) {
                                this.RedGhostEnemy.setBounds(this.Map[7][6].getBounds().x + 10, this.Map[7][6].getBounds().y + 10, 50, 50);
                            }else if(this.MapLayoutChosen==5) {
                                this.RedGhostEnemy.setBounds(this.Map[9][11].getBounds().x + 10, this.Map[9][11].getBounds().y + 10, 50, 50);
                            }
                            this.PlayerPoints+=500;
                        }
                    }
                }
            }
        }
    }

    // --- Function to get current field type ---
    public int getCurrentFieldType(int x, int y) {
        return this.MapLayoutLoaded[getCurrentRowIndex(x,y)][getCurrentColumnIndex(x,y)];
    }
    public int getCurrentColumnIndex(int x, int y){
        int mapZeroPointX = this.CurrentCenterX - (MapLayoutLoaded[0].length * 25);
        return (x - mapZeroPointX) / 50;
    }
    public int getCurrentRowIndex(int x, int y){
        int mapZeroPointY = this.CurrentCenterY - (MapLayoutLoaded.length * 25);
        return (y - mapZeroPointY) / 50;
    }

    // --- Setters/Getters
    public JLabel[][] getMap() {
        return this.Map;
    }
    public int getMapLayoutChosen(){
        return this.MapLayoutChosen;
    }
    public void setMap(JLabel[][] map) {
        this.Map = map;
    }
    public JLabel getPacMan() {
        return this.PacMan;
    }
    public void setPacMan(JLabel pacMan) {
        this.PacMan = pacMan;
    }
    public int getPacManXVelocity() {
        return this.PacManXVelocity;
    }
    public void setPacManXVelocity(int pacManXVelocity) {
        this.PacManXVelocity = pacManXVelocity;
    }
    public int getPacManYVelocity() {
        return this.PacManYVelocity;
    }
    public void setPacManYVelocity(int pacManYVelocity) {
        this.PacManYVelocity = pacManYVelocity;
    }
    public Direction getPacManDirection() {
        return this.PacManDirection;
    }
    public JLabel[][] getPoints2PickUpImages() {
        return this.Points2PickUpImages;
    }
    public void setPoints2PickUpImages(JLabel[][] points2PickUpImages) {
        this.Points2PickUpImages = points2PickUpImages;
    }
    public int[][] getPoints2PickUpMap() {
        return this.Points2PickUpMap;
    }
    public void setPoints2PickUpMap(int[][] points2PickUpMap) {
        this.Points2PickUpMap = points2PickUpMap;
    }
    public int getPlayerPoints(){
        return this.PlayerPoints;
    }
    public void setPlayerPoints(int playerPoints) {
        this.PlayerPoints = playerPoints;
    }
    public JLabel getPlayerPointsDisplayer(){
        return this.PlayerPointsDisplayer;
    }
    public void setPlayerPointsDisplayer(JLabel playerPointsDisplayer) {
        this.PlayerPointsDisplayer = playerPointsDisplayer;
    }
    public JLabel getPinkGhostEnemy(){
        return this.PinkGhostEnemy;
    }
    public void setPinkGhostEnemy(JLabel PinkGhostEnemy){
        this.PinkGhostEnemy = PinkGhostEnemy;
    }
    public Direction getPinkGhostDirection(){
        return this.PinkGhostDirection;
    }
    public void setPinkGhostDirection(Direction pinkGhostDirection) {
        this.PinkGhostDirection = pinkGhostDirection;
    }
    public JLabel getOrangeGhostEnemy(){
        return this.OrangeGhostEnemy;
    }
    public void setOrangeGhostEnemy(JLabel OrangeGhostEnemy){
        this.OrangeGhostEnemy = OrangeGhostEnemy;
    }
    public Direction getOrangeGhostDirection(){
        return this.OrangeGhostDirection;
    }
    public void setOrangeGhostDirection(Direction orangeGhostDirection) {
        this.OrangeGhostDirection = orangeGhostDirection;
    }
    public JLabel getBlueGhostEnemy(){
        return this.BlueGhostEnemy;
    }
    public void setBlueGhostEnemy(JLabel BlueGhostEnemy){
        this.BlueGhostEnemy = BlueGhostEnemy;
    }
    public Direction getBlueGhostDirection(){
        return this.BlueGhostDirection;
    }
    public void setBlueGhostDirection(Direction blueGhostDirection) {
        this.BlueGhostDirection = blueGhostDirection;
    }
    public JLabel getRedGhostEnemy(){
        return this.RedGhostEnemy;
    }
    public void setRedGhostEnemy(JLabel RedGhostEnemy){
        this.RedGhostEnemy = RedGhostEnemy;
    }
    public Direction getRedGhostDirection(){
        return this.RedGhostDirection;
    }
    public void setRedGhostDirection(Direction redGhostDirection) {
        this.RedGhostDirection = redGhostDirection;
    }
    public int getPointsMultiplier(){
        return this.PointsMultiplier;
    }
    public void setPointsMultiplier(int pointsMultiplier) {
        this.PointsMultiplier = pointsMultiplier;
    }
    public JLabel getCherryIndicator(){
        return this.CherryIndicator;
    }
    public void setCherryIndicator(JLabel CherryIndicator) {
        this.CherryIndicator = CherryIndicator;
    }
    public JLabel getAppleIndicator(){
        return this.AppleIndicator;
    }
    public void setAppleIndicator(JLabel AppleIndicator) {
        this.AppleIndicator = AppleIndicator;
    }
    public JLabel getPeachIndicator(){
        return this.PeachIndicator;
    }
    public void setPeachIndicator(JLabel PeachIndicator) {
        this.PeachIndicator = PeachIndicator;
    }
    public JLabel getStrawberryIndicator(){
        return this.StrawberryIndicator;
    }
    public void setStrawberryIndicator(JLabel StrawberryIndicator) {
        this.StrawberryIndicator = StrawberryIndicator;
    }
    public JLabel getMedallionIndicator(){
        return this.MedallionIndicator;
    }
    public void setMedallionIndicator(JLabel MedallionIndicator) {
        this.MedallionIndicator = MedallionIndicator;
    }
    public void setPlayerImmortal(boolean playerImmortal) {
        this.PlayerImmortal = playerImmortal;
    }
    public int getPlayerSpeedMultiplier(){
        return this.PlayerSpeedMultiplier;
    }
    public void setPlayerSpeedMultiplier(int playerSpeedMultiplier) {
        this.PlayerSpeedMultiplier = playerSpeedMultiplier;
    }
    public float getEnemySpeedMultiplier(){
        return this.EnemySpeedMultiplier;
    }
    public void setEnemySpeedMultiplier(float newSpeedMultiplier){
        this.EnemySpeedMultiplier = newSpeedMultiplier;
    }
    public void setRolesSwitched(boolean rolesSwitched) {
        this.RolesSwitched = rolesSwitched;
    }
    public JLabel getPlayerLifesDisplayer(){
        return this.PlayerLifesDisplayer;
    }
    public void setPlayerLifesDisplayer(JLabel PlayerLifesDisplayer) {
        this.PlayerLifesDisplayer = PlayerLifesDisplayer;
    }
    public JLabel getPlayerPointsDisplayer2(){
        return this.PlayerPointsDisplayer2;
    }
    public void setPlayerPointsDisplayer2(JLabel PlayerPointsDisplayer2) {
        this.PlayerPointsDisplayer2 = PlayerPointsDisplayer2;
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
        if (pacManMover != null) {
            pacManMover.stop();
            try {
                pacManPositionsUpdaterThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(packManWallCollisionChecker != null){
            packManWallCollisionChecker.stop();
            try{
                wallCollisionCheckerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(PacManAnimator != null){
            PacManAnimator.stop();
            try{
                pacManAnimatorThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(pacManEaterChecker != null){
            pacManEaterChecker.stop();
            try{
                pacManEaterCheckerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(pinkGhostAnimator != null){
            pinkGhostAnimator.stop();
            try{
                pinkGhostAnimatorThread.join();
            }catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(pinkGhostMover != null){
            pinkGhostMover.stop();
            try{
                pinkGhostAnimatorThread.join();
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(orangeGhostAnimator != null){
            orangeGhostAnimator.stop();
            try{
                orangeGhostAnimatorThread.join();
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(orangeGhostMover != null){
            orangeGhostMover.stop();
            try{
                orangeGhostAnimatorThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(blueGhostAnimator != null){
            blueGhostAnimator.stop();
            try{
                blueGhostAnimatorThread.join();
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(blueGhostMover != null){
            blueGhostMover.stop();
            try{
                blueGhostAnimatorThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(redGhostAnimator != null){
            redGhostAnimator.stop();
            try{
                redGhostAnimatorThread.join();
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(redGhostMover != null){
            redGhostMover.stop();
            try{
                redGhostAnimatorThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(playerAndGhostsCollisionManager != null){
            playerAndGhostsCollisionManager.stop();
            try{
                playerAndGhostsCollisionManagerThread.join();
            }catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if(consumablesDropper != null){
            consumablesDropper.stop();
            try{
                consumablesDropperThread.join();
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // --- Function for setting up movement by W/A/S/D keys ---
    private void setupKeyBindings() {
        ActionMap actionMap4PackManMovement = this.MapWithStuff.getActionMap();
        InputMap inputMap4PackManMovement = this.MapWithStuff.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap4PackManMovement.put(KeyStroke.getKeyStroke("W"), "moveUp");
        inputMap4PackManMovement.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        inputMap4PackManMovement.put(KeyStroke.getKeyStroke("S"), "moveDown");
        inputMap4PackManMovement.put(KeyStroke.getKeyStroke("D"), "moveRight");
        actionMap4PackManMovement.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PacManYVelocity = -6;
                PacManXVelocity = 0;
                PacManDirection = Direction.Up;
                LineUpPacMan();
            }
        });
        actionMap4PackManMovement.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PacManYVelocity = 0;
                PacManXVelocity = -6;
                PacManDirection = Direction.Left;
                LineUpPacMan();
            }
        });
        actionMap4PackManMovement.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PacManYVelocity = 6;
                PacManXVelocity = 0;
                PacManDirection = Direction.Down;
                LineUpPacMan();
            }
        });
        actionMap4PackManMovement.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PacManYVelocity = 0;
                PacManXVelocity = 6;
                PacManDirection = Direction.Right;
                LineUpPacMan();
            }
        });
    }

    public void LineUpPacMan(){
        int CurrentColumn = getCurrentColumnIndex(this.PacMan.getBounds().x, this.PacMan.getBounds().y);
        int CurrentRow = getCurrentRowIndex(this.PacMan.getBounds().x, this.PacMan.getBounds().y);
        this.PacMan.setBounds(getMap()[CurrentRow][CurrentColumn].getBounds().x+10,
                getMap()[CurrentRow][CurrentColumn].getBounds().y+10,
                this.PacMan.getIcon().getIconWidth(), this.PacMan.getIcon().getIconHeight());
    }

    private void addEscKeyListener() {
        JRootPane rootPaneElement = this.getRootPane();
        rootPaneElement.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "ESCAPE");
        rootPaneElement.getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopUpdaters();
                dispose();
                SwingUtilities.invokeLater(MainMenuScreen::new);
            }
        });
    }

}



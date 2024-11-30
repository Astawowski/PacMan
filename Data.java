import javax.swing.*;

public class Data extends JFrame {

    public static ImageIcon DataButtonIcon;
    public static ImageIcon DataPacManLogoIcon;
    public static int[][] DataMap1Layout;
    public static int[][] DataMap2Layout;
    public static int[][] DataMap3Layout;
    public static int[][] DataMap4Layout;
    public static int[][] DataMap5Layout;
    public static String[] DataMapLayoutsList;
    public static ImageIcon DataTransparentSquareN1;
    public static ImageIcon DataFieldSquare0;
    public static ImageIcon DataWallRock1;
    public static ImageIcon DataWallSideHorizontal2;
    public static ImageIcon DataWallSideVertical3;
    public static ImageIcon DataWallJoinerLowerLeft4;
    public static ImageIcon DataWallJoinerLowerRight5;
    public static ImageIcon DataWallJoinerUpperLeft6;
    public static ImageIcon DataWallJoinerUpperRight7;
    public static ImageIcon DataPacMan1ImageR;
    public static ImageIcon DataPacMan1ImageL;
    public static ImageIcon DataPacMan1ImageU;
    public static ImageIcon DataPacMan1ImageD;
    public static ImageIcon DataPacMan2ImageR;
    public static ImageIcon DataPacMan2ImageL;
    public static ImageIcon DataPacMan2ImageU;
    public static ImageIcon DataPacMan2ImageD;
    public static ImageIcon DataPacMan3ImageR;
    public static ImageIcon DataPacMan3ImageL;
    public static ImageIcon DataPacMan3ImageU;
    public static ImageIcon DataPacMan3ImageD;
    public static ImageIcon DataMap1Image;
    public static ImageIcon DataMap2Image;
    public static ImageIcon DataMap3Image;
    public static ImageIcon DataMap4Image;
    public static ImageIcon DataMap5Image;
    public static ImageIcon DataPoint2PickUp;
    public static ImageIcon DataCherry;
    public static ImageIcon DataPeach;
    public static ImageIcon DataApple;
    public static ImageIcon DataStrawberry;
    public static ImageIcon DataMedallion;

    public static ImageIcon DataGhostPink1R;
    public static ImageIcon DataGhostPink1L;
    public static ImageIcon DataGhostPink1U;
    public static ImageIcon DataGhostPink1D;
    public static ImageIcon DataGhostPink2R;
    public static ImageIcon DataGhostPink2L;
    public static ImageIcon DataGhostPink2U;
    public static ImageIcon DataGhostPink2D;

    public static ImageIcon DataGhostOrange1R;
    public static ImageIcon DataGhostOrange1L;
    public static ImageIcon DataGhostOrange1U;
    public static ImageIcon DataGhostOrange1D;
    public static ImageIcon DataGhostOrange2R;
    public static ImageIcon DataGhostOrange2L;
    public static ImageIcon DataGhostOrange2U;
    public static ImageIcon DataGhostOrange2D;

    public static ImageIcon DataGhostBlue1R;
    public static ImageIcon DataGhostBlue1L;
    public static ImageIcon DataGhostBlue1U;
    public static ImageIcon DataGhostBlue1D;
    public static ImageIcon DataGhostBlue2R;
    public static ImageIcon DataGhostBlue2L;
    public static ImageIcon DataGhostBlue2U;
    public static ImageIcon DataGhostBlue2D;

    public static ImageIcon DataGhostRed1R;
    public static ImageIcon DataGhostRed1L;
    public static ImageIcon DataGhostRed1U;
    public static ImageIcon DataGhostRed1D;
    public static ImageIcon DataGhostRed2R;
    public static ImageIcon DataGhostRed2L;
    public static ImageIcon DataGhostRed2U;
    public static ImageIcon DataGhostRed2D;

    public static void LoadGameData() {

            // --------- Loading the textures -------------
            try {
                // ---Loading---
                DataButtonIcon = new ImageIcon("./Data/ButtonImage.png");
                DataPacManLogoIcon = new ImageIcon("./Data/Pac-ManLogo.png");
                DataTransparentSquareN1 = new ImageIcon("./Data/TransparentSquare-1.png");
                DataFieldSquare0 = new ImageIcon("./Data/FieldSquare0.png");
                DataWallRock1 = new ImageIcon("./Data/WallRock1.png");
                DataWallSideHorizontal2 = new ImageIcon("./Data/WallSideHorizontal2.png");
                DataWallSideVertical3 = new ImageIcon("./Data/WallSideVertical3.png");
                DataWallJoinerLowerLeft4 = new ImageIcon("./Data/WallJoinerLowerLeft4.png");
                DataWallJoinerLowerRight5 = new ImageIcon("./Data/WallJoinerLowerRight5.png");
                DataWallJoinerUpperLeft6 = new ImageIcon("./Data/WallJoinerUpperLeft6.png");
                DataWallJoinerUpperRight7 = new ImageIcon("./Data/WallJoinerUpperRight7.png");
                DataPacMan1ImageR = new ImageIcon("./Data/PacMan/PacMan1ImageR.png");
                DataPacMan1ImageL = new ImageIcon("./Data/PacMan/PacMan1ImageL.png");
                DataPacMan1ImageU = new ImageIcon("./Data/PacMan/PacMan1ImageU.png");
                DataPacMan1ImageD = new ImageIcon("./Data/PacMan/PacMan1ImageD.png");
                DataPacMan2ImageR = new ImageIcon("./Data/PacMan/PacMan2ImageR.png");
                DataPacMan2ImageU = new ImageIcon("./Data/PacMan/PacMan2ImageU.png");
                DataPacMan2ImageL = new ImageIcon("./Data/PacMan/PacMan2ImageL.png");
                DataPacMan2ImageD = new ImageIcon("./Data/PacMan/PacMan2ImageD.png");
                DataPacMan3ImageR = new ImageIcon("./Data/PacMan/PacMan3ImageR.png");
                DataPacMan3ImageU = new ImageIcon("./Data/PacMan/PacMan3ImageU.png");
                DataPacMan3ImageL = new ImageIcon("./Data/PacMan/PacMan3ImageL.png");
                DataPacMan3ImageD = new ImageIcon("./Data/PacMan/PacMan3ImageD.png");
                DataMap1Image = new ImageIcon("./Data/Map1.png");
                DataMap2Image = new ImageIcon("./Data/Map2.png");
                DataMap3Image = new ImageIcon("./Data/Map3.png");
                DataMap4Image = new ImageIcon("./Data/Map4.png");
                DataMap5Image = new ImageIcon("./Data/Map5.png");
                DataPoint2PickUp = new ImageIcon("./Data/Point2PickUp.png");
                DataCherry = new ImageIcon("./Data/cherry.png");
                DataPeach = new ImageIcon("./Data/peach.png");
                DataStrawberry = new ImageIcon("./Data/strawberry.png");
                DataMedallion = new ImageIcon("./Data/medallion.png");
                DataApple = new ImageIcon("./Data/apple.png");

                // Pink Ghost
                DataGhostPink1R = new ImageIcon("./Data/PinkGhost/GhostPink1R.png");
                DataGhostPink1L = new ImageIcon("./Data/PinkGhost/GhostPink1L.png");
                DataGhostPink1U = new ImageIcon("./Data/PinkGhost/GhostPink1U.png");
                DataGhostPink1D = new ImageIcon("./Data/PinkGhost/GhostPink1D.png");
                DataGhostPink2R = new ImageIcon("./Data/PinkGhost/GhostPink2R.png");
                DataGhostPink2L = new ImageIcon("./Data/PinkGhost/GhostPink2L.png");
                DataGhostPink2U = new ImageIcon("./Data/PinkGhost/GhostPink2U.png");
                DataGhostPink2D = new ImageIcon("./Data/PinkGhost/GhostPink2D.png");

// Orange Ghost
                DataGhostOrange1R = new ImageIcon("./Data/OrangeGhost/GhostOrange1R.png");
                DataGhostOrange1L = new ImageIcon("./Data/OrangeGhost/GhostOrange1L.png");
                DataGhostOrange1U = new ImageIcon("./Data/OrangeGhost/GhostOrange1U.png");
                DataGhostOrange1D = new ImageIcon("./Data/OrangeGhost/GhostOrange1D.png");
                DataGhostOrange2R = new ImageIcon("./Data/OrangeGhost/GhostOrange2R.png");
                DataGhostOrange2L = new ImageIcon("./Data/OrangeGhost/GhostOrange2L.png");
                DataGhostOrange2U = new ImageIcon("./Data/OrangeGhost/GhostOrange2U.png");
                DataGhostOrange2D = new ImageIcon("./Data/OrangeGhost/GhostOrange2D.png");

// Blue Ghost
                DataGhostBlue1R = new ImageIcon("./Data/BlueGhost/GhostBlue1R.png");
                DataGhostBlue1L = new ImageIcon("./Data/BlueGhost/GhostBlue1L.png");
                DataGhostBlue1U = new ImageIcon("./Data/BlueGhost/GhostBlue1U.png");
                DataGhostBlue1D = new ImageIcon("./Data/BlueGhost/GhostBlue1D.png");
                DataGhostBlue2R = new ImageIcon("./Data/BlueGhost/GhostBlue2R.png");
                DataGhostBlue2L = new ImageIcon("./Data/BlueGhost/GhostBlue2L.png");
                DataGhostBlue2U = new ImageIcon("./Data/BlueGhost/GhostBlue2U.png");
                DataGhostBlue2D = new ImageIcon("./Data/BlueGhost/GhostBlue2D.png");

// Red Ghost
                DataGhostRed1R = new ImageIcon("./Data/RedGhost/GhostRed1R.png");
                DataGhostRed1L = new ImageIcon("./Data/RedGhost/GhostRed1L.png");
                DataGhostRed1U = new ImageIcon("./Data/RedGhost/GhostRed1U.png");
                DataGhostRed1D = new ImageIcon("./Data/RedGhost/GhostRed1D.png");
                DataGhostRed2R = new ImageIcon("./Data/RedGhost/GhostRed2R.png");
                DataGhostRed2L = new ImageIcon("./Data/RedGhost/GhostRed2L.png");
                DataGhostRed2U = new ImageIcon("./Data/RedGhost/GhostRed2U.png");
                DataGhostRed2D = new ImageIcon("./Data/RedGhost/GhostRed2D.png");




                // --- Checking if loading succeeded
                if (DataButtonIcon.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPacManLogoIcon.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataTransparentSquareN1.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataFieldSquare0.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataWallRock1.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataWallSideHorizontal2.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataWallSideVertical3.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataWallJoinerLowerLeft4.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataWallJoinerLowerRight5.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataWallJoinerUpperLeft6.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataWallJoinerUpperRight7.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPacMan1ImageR.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPacMan1ImageL.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPacMan1ImageU.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPacMan1ImageD.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if(DataPacMan2ImageR.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPacMan2ImageL.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPacMan2ImageU.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPacMan2ImageD.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if(DataPacMan3ImageR.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPacMan3ImageL.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPacMan3ImageU.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPacMan3ImageD.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataMap1Image.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataMap2Image.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataMap3Image.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataMap4Image.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataMap5Image.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataPoint2PickUp.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                // Pink Ghost
                if (DataGhostPink1R.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostPink1L.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostPink1U.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostPink1D.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostPink2R.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostPink2L.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostPink2U.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostPink2D.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }

// Orange Ghost
                if (DataGhostOrange1R.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostOrange1L.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostOrange1U.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostOrange1D.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostOrange2R.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostOrange2L.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostOrange2U.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostOrange2D.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }

// Blue Ghost
                if (DataGhostBlue1R.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostBlue1L.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostBlue1U.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostBlue1D.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostBlue2R.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostBlue2L.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostBlue2U.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostBlue2D.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }

// Red Ghost
                if (DataGhostRed1R.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostRed1L.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostRed1U.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostRed1D.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostRed2R.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostRed2L.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostRed2U.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataGhostRed2D.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataCherry.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if (DataApple.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if(DataPeach.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if(DataMedallion.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }
                if(DataStrawberry.getIconWidth() == -1) {
                    throw new Exception("Image loading failed");
                }


            } catch (Exception e) {
                System.exit(3);
            }

            // --- Map Layouts available
            DataMapLayoutsList = new String[]{ "Map 1", "Map 2",  "Map 3" , "Map 4", "Map 5"};

            // --- Setting the map layouts
            DataMap1Layout = new int[][]{
                    {6,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,7},
                    {3,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,3},
                    {3,0,1,1,0,1,1,1,0,4,5,0,1,1,1,0,1,1,0,3},
                    {3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
                    {3,0,2,2,0,3,0,2,2,2,2,2,2,0,3,0,2,2,0,3},
                    {3,0,0,0,0,3,0,0,0,3,3,0,0,0,3,0,0,0,0,3},
                    {3,0,6,7,0,3,2,2,0,4,5,0,2,2,3,0,6,7,0,3},
                    {3,0,3,3,0,3,0,0,0,0,0,0,0,0,3,0,3,3,0,3},
                    {3,0,4,5,0,3,0,6,2,0,0,2,7,0,3,0,4,5,0,3},
                    {3,0,0,0,0,0,0,3,0,0,0,0,3,0,0,0,0,0,0,3},
                    {3,0,6,7,0,3,0,4,2,2,2,2,5,0,3,0,6,7,0,3},
                    {3,0,3,3,0,3,0,0,0,0,0,0,0,0,3,0,3,3,0,3},
                    {3,0,4,5,0,3,0,2,2,7,6,2,2,0,3,0,4,5,0,3},
                    {3,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,3},
                    {4,2,2,2,2,2,2,2,2,5,4,2,2,2,2,2,2,2,2,5},
            };

            DataMap2Layout = new int[][]{
                    {6,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,7},
                    {3,0,0,0,0,3,0,0,0,0,0,0,0,0,3,0,0,0,0,3},
                    {3,0,6,2,0,3,0,2,2,2,2,2,2,0,3,0,2,7,0,3},
                    {3,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,3},
                    {3,0,3,0,2,2,0,6,2,0,0,2,7,0,2,2,0,3,0,3},
                    {3,0,0,0,0,0,0,3,0,0,0,0,3,0,0,0,0,0,0,3},
                    {3,0,3,0,2,2,0,4,2,2,2,2,5,0,2,2,0,3,0,3},
                    {3,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,3},
                    {3,0,4,2,0,3,0,2,2,2,2,2,2,0,3,0,2,5,0,3},
                    {3,0,0,0,0,3,0,0,0,0,0,0,0,0,3,0,0,0,0,3},
                    {4,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,5}
            };

        DataMap3Layout = new int[][]{
                {6,2,2,2,2,2,2,2,2,2,2,7},
                {3,0,0,0,0,0,1,0,0,0,0,3},
                {3,0,6,2,2,0,1,0,2,7,0,3},
                {3,0,3,0,0,0,0,0,0,3,0,3}, //3
                {3,0,3,0,6,2,2,7,0,3,0,3}, //4
                {3,0,3,0,4,2,2,5,0,3,0,3}, //5
                {3,0,0,0,0,0,0,0,0,0,0,3}, //6
                {3,1,0,6,2,0,0,2,7,0,1,3}, //7
                {3,0,0,3,0,0,0,0,3,0,0,3},
                {3,0,0,4,2,7,6,2,5,0,0,3}, //9
                {3,0,0,0,0,3,3,0,0,0,0,3},
                {3,0,2,7,0,4,5,0,6,2,0,3},
                {3,0,0,3,0,0,0,0,3,0,0,3},
                {4,2,2,2,2,2,2,2,2,2,2,5}
        };

        DataMap4Layout = new int[][]{
                {6,2,2,2,2,2,2,2,2,2,2,2,2,2,2,7},
                {3,0,3,0,0,3,0,0,0,0,3,0,0,3,0,3},
                {3,0,3,0,2,5,0,6,7,0,4,2,0,3,0,3},
                {3,0,0,0,0,0,0,3,3,0,0,0,0,0,0,3},
                {4,7,0,6,2,2,0,4,5,0,2,2,7,0,6,5},
                {1,3,0,3,0,0,0,0,0,0,0,0,3,0,3,1},
                {1,3,0,3,0,6,2,0,0,2,7,0,3,0,3,1},
                {1,3,0,0,0,3,0,0,0,0,3,0,0,0,3,1},
                {1,3,0,3,0,4,2,2,2,2,5,0,3,0,3,1},
                {1,3,0,3,0,0,0,0,0,0,0,0,3,0,3,1},
                {6,5,0,4,2,2,2,0,0,2,2,2,5,0,4,7},
                {3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
                {4,2,2,2,2,2,2,2,2,2,2,2,2,2,2,5}
        };

        DataMap5Layout = new int[][]{
                {6,2,2,2,2,2,2,2,2,2,2,2,7},
                {3,0,0,0,0,3,0,0,0,0,3,0,3},
                {3,0,6,2,0,3,0,2,7,0,3,0,3},
                {3,0,3,0,0,0,0,0,3,0,0,0,3},
                {3,0,0,0,6,0,7,0,0,0,3,0,3},
                {3,2,2,0,0,0,0,0,2,2,3,2,3},
                {3,0,0,0,4,0,5,0,0,0,3,0,3},
                {3,0,3,0,0,0,0,0,3,0,0,0,3},
                {3,0,4,2,0,3,0,2,5,0,3,0,3},
                {3,0,0,0,0,3,0,0,0,0,3,0,3},
                {4,2,2,2,2,2,2,2,2,2,2,2,5}
        };
    }






}

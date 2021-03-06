
package BotSunTzu;
import java.lang.Math;
import java.util.ArrayList;

/**
 *
 * @author Ermenildo V. Castro, Jr.
 * -----------------------------------------------------------------------------
 * Class Description: GeneralRulesCorupus
 * The GeneralRulesCorpus analyzes the current state of the board, and determines
 * the optimal move for the designated player.
 * 
 * Optimality is determined by applying general knowledge rules to the game state
 * such as "If the opponent will have a winning sequence in the next move, block
 * the opponent."
 * 
 * The designated player is supplied to the constructor, identified by either 1 or -1.
 * Thereafter, the calculus of the optimal move is conducted with respect to the 
 * identified player.
 * -----------------------------------------------------------------------------
 */
public class GeneralRulesCorpus {
    private int playerID;
    private int opponentID;
    private int[][] gameState;
    private int boardX;
    private int boardY;
    private int[][] optimalMatrix; //the weighted matrix derived from gameState calculus.
    //debugging variables-------------------------------------------------------
    private boolean DEBUGMODE = false; //to display debugging code
    private CellDataWeight[][] infoMatrix;
    //--------------------------------------------------------------------------
    
    //method constants----------------------------------------------------------
    //constant declaration for leftHull and rightHull
    final int MINCONTAINMENT = 24;
    //constant for preservation
    final int WINCONDITION = 4;
    //--------------------------------------------------------------------------
    
    //constants for matrix weight modification----------------------------------
    final private double MAXVAL = 100;
    final private double MINVAL = 0;
    final private double DECVAL = -10;
    final private double RESCIND = -100;
    final private double VALMOD = 10;
    
    //--------------------------------------------------------------------------
    //weights for abstract principles-------------------------------------------
    //entries in array correspond to principle
    //  principleWeightModifiers[0] weight is for Principle of Centrality
    //0) PRINCIPLE OF CENTRALITY : INCREASING WEIGHT
    //1) PRINCIPLE OF INTERDICTION : INCREASING WEIGHT
    //2) PRINCIPLE OF PRESERVATION : ABS WEIGHT
    //3) PRINCIPLE OF UNITY : INCREASING WEIGHT
    //4) PRINCIPLE OF INTERSECTION : WEIGHT INCREASING
    //5) PRINCIPLE OF "COUP DE GRACE" : ABS WEIGHT
    //6) PRINCIPLE OF CONTAINMENT : ABS WEIGHT
    //7) PRINCIPLE OF OPPORTUNITY : INCREASING WEIGHT 
    //8) PRINCIPLE OF CONSERVATION : ABS WEIGHT
    //9) PRINCIPLE OF DRAGON'S TEETH
    //10) PRINCIPLE OF PROGRESS : ABS WEIGHT
    //11) PRINCIPLE OF BREAKOUT : DECREASING WEIGHT
    //12) PRINCIPLE OF DESIRE : INCREASING WEIGHT
    //13) PRINCIPLE OF OBSTRUCTION : INCREASING WEIGHT
    double[] c = {//scalars
        .25, //0
        2.25, //1
        100, //2
        .5, //3
        1, //4
        100, //5
        1, //6
        1, //7 
        100, //8
        2.25, //9
        100, //10
        1, //11
        1, //12
        .75 // 13
    }; 
    private double[] principleWeightModifiers = {
        VALMOD * c[0], //0
        VALMOD * c[1], //1
        MAXVAL * c[2], //2
        VALMOD * c[3], //3
        VALMOD * c[4], //4
        MAXVAL * c[5], //5
        VALMOD * c[6], //6
        VALMOD * c[7], //7
        RESCIND * c[8], //8
        VALMOD * c[9], //9
        RESCIND * c[10], //10
        DECVAL * c[11], //11
        VALMOD * c[12],//12
        VALMOD * c[13] //13
    };
    //--------------------------------------------------------------------------
    /**
     * Constructor
     * @param playerID
     * @param gameState - int[][] representing the current state of the GoMoku board. 
     */
    public GeneralRulesCorpus(int playerID, int[][] gameState){
        this.playerID = playerID;
        this.gameState = gameState.clone();
        
        if(this.playerID == 1){this.opponentID = -1;}else{this.opponentID = 1;}
        boardX = this.gameState.length;
        boardY = this.gameState[0].length;
        optimalMatrix = new int[boardX][boardY];
        infoMatrix = new CellDataWeight[boardX][boardY];
    }
    
    /**
     * Constructs a weighted matrix from the current gameState.
     */
    public void constructWeightedMatrix(){
        for(int j = 0; j < boardY; j++){
            for(int i = 0; i < boardX; i++){
                optimalMatrix[i][j] = weightCell(i, j);
            }
        }
        
    }
    public void displayGameState(){
        System.out.println("Current Game State");
        for(int j = 0; j < boardY; j++){
            for(int i = 0; i < boardX; i++){
                System.out.print(" " + gameState[j][i]);
            }
            System.out.println();
        }
    }
    public void displayWeightedBoard(){
        System.out.println("Weighted Board");
        for(int j = 0; j < boardX; j++){
            for(int i = 0; i < boardY; i++){
                System.out.print(" " + optimalMatrix[j][i]);
            }
            System.out.println();
        }
        
    }
    public void displayCellDataWeight(int x, int y){System.out.println(infoMatrix[x][y].retrieveCellData());}
    public int[][] getWeightedMatrix(){
        //DEBUG CODE
        if(DEBUGMODE){displayWeightedBoard();}
        //END DEBUG CODE
        return optimalMatrix.clone();}
    /**
     * Determines the weight to be applied to the cell.
     * Invokes auxiliary method, weightByRule.
     * @param x - integer, cell coordinate X
     * @param y - integer, cell coordinate Y
     * @return double, the weight of the cell.
     */
    private int weightCell(int x, int y){return (int) weightByRule(x, y);}
    /**
     * weightByRule invokes a collection of private general rules.
     * The method constructs a set of weights from each rule.
     * The average of the set is the determined weight of the cell.
     * @param x
     * @param y
     * @return 
     */
    private double weightByRule(int x, int y){
        double weight = 0;
        infoMatrix[x][y] = new CellDataWeight(x, y);
        weight += poCENTRALITY(x, y);
        if(DEBUGMODE){System.out.println("Calculated poCENTRALITY " + weight);}
        infoMatrix[x][y].addData("Calculated poCENTRALITY " + weight);
        weight += poCONSERVATION(x,y);
        if(DEBUGMODE){System.out.println("Calculated poCONSERVATION "+ weight);}
        infoMatrix[x][y].addData("Calculated poCONSERVATION "+ weight);
        weight += poCONTAINMENT(x,y);
        if(DEBUGMODE){System.out.println("Calculated poCONTAINMENT "+ weight);}
        infoMatrix[x][y].addData("Calculated poCONTAINMENT "+ weight);
        weight += poGRACE(x, y);
        if(DEBUGMODE){System.out.println("Calculated poGRACE "+ weight);}
        infoMatrix[x][y].addData("Calculated poGRACE "+ weight);
        weight += poINTERDICTION(x,y);
        if(DEBUGMODE){System.out.println("Calculated poINTERDICTION "+ weight);}
        infoMatrix[x][y].addData("Calculated poINTERDICTION "+ weight);
        weight += poINTERSECTION(x,y);
        if(DEBUGMODE){System.out.println("Calculated poINTERSECTION "+ weight);}
        infoMatrix[x][y].addData("Calculated poINTERSECTION "+ weight);
        weight += poOPPORUNITY(x,y);
        if(DEBUGMODE){System.out.println("Calculated poOPPORUNITY "+ weight);}
        infoMatrix[x][y].addData("Calculated poOPPORUNITY "+ weight);
        weight += poPRESERVATION(x,y);
        if(DEBUGMODE){System.out.println("Calculated poPRESERVATION "+ weight);}
        infoMatrix[x][y].addData("Calculated poPRESERVATION "+ weight);
        weight += poUNITY(x, y);
        if(DEBUGMODE){System.out.println("Calculated poUNITY "+ weight);}
        infoMatrix[x][y].addData("Calculated poUNITY "+ weight);
        weight += poPROGRESS(x, y);
        if(DEBUGMODE){System.out.println("Calculated poPROGRESS "+ weight);}
        infoMatrix[x][y].addData("Calculated poPROGRESS "+ weight);
        weight += poTEETH(x, y);
        if(DEBUGMODE){System.out.println("Calculated poTEETH " + weight);}
        infoMatrix[x][y].addData("Calculated poTEETH " + weight);
        weight += poBREAKOUT(x, y);
        if(DEBUGMODE){System.out.println("Calculated poBREAKOUT " + weight);}
        infoMatrix[x][y].addData("Calculated poBREAKOUT " + weight);
        weight += poDESIRE(x, y);
        if(DEBUGMODE){System.out.println("Calculated poDESIRE " + weight);}
        infoMatrix[x][y].addData("Calculated poDESIRE " + weight);
        weight += poOBSTRUCTION(x, y);
        if(DEBUGMODE){System.out.println("Calculated poOBSTRUCTION " + weight);}
        infoMatrix[x][y].addData("Calculated poOBSTRUCTION " + weight);
        if(DEBUGMODE){System.out.println("Weight Calculated: " + weight);}
        return weight;
                
    }
    
    public void updateGameState(int[][] gameState){this.gameState = gameState.clone();}
    public void setPlayerID(int id){this.playerID = id;}
    
    //ABSTRACT RULES------------------------------------------------------------
    //Note that each principle has a quantified weight change associated
    //  INCREASING, DECREASING, or ABSOLUTE
    //For INCREASING - the more a cell satisfies the principle, the greater its result weight will be.
    //For DECREASING - the more a cell satisfies the principle, the less weight is derived.
    //      EXAMPLE: poCENTRALITY
    //              1) cells towards the center will have increasing weight
    //For ABSOLUTE (ABS) - based on the satisfaction of the principle, the weight derived is either MAXVAL or MINVAL
    //The cell with the greatest weight (highest) is ideal.
    //0) PRINCIPLE OF CENTRALITY : INCREASING WEIGHT
    //      Cells nearest the center of the board are preferred, because 
    //      central cell affords a greater possibility of achieving an in-sequence
    //      combination in all directions; unrestricted by board dimensions.
    private double poCENTRALITY(int x, int y){
        //calculate center of board
        int midx = boardX / 2;
        int midy = boardY / 2;
        double deviation = (double) (Math.sqrt(Math.pow(midx, 2) + Math.pow(midy, 2)));
        
        //assign weight by deviance from center of board
        //calculate distance from cell -> center of board
        int diffx = x - midx;
        int diffy = y - midy;
        double distance =  (double) (Math.sqrt(Math.pow(diffx, 2) + Math.pow(diffy, 2)));
        // weight affected by deviations away from middle
        // Decreasing weight
        double scalar = 1.0/3.0;
        if(distance != 0){scalar = (deviation / distance);}
        return (double) principleWeightModifiers[0] * scalar;
    }
    
    //1) PRINCIPLE OF INTERDICTION : INCREASING WEIGHT
    //      Cells which impede the opponent's achievement of a winning sequence
    //      are prefered, because anything which is of disservice to the opponent
    //      is advantageous to the self.
    public double poINTERDICTION(int x, int y){
        int hostileCount = 0;
        //Determine quantity of HOSTILE cells
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                //determine if within bounds
                int cX = (x-1)+ j;
                int cY = (y-1) + i;
                if((cX >= 0 && cX < boardX) && (cY >= 0 && cY < boardY)){
                    if(gameState[cX][cY] == opponentID){hostileCount++;}
                }
                
            }
        }
        //weight affected by quantity of HOSTILE cells
        //increasing weight
        return principleWeightModifiers[1] * hostileCount;
    }
    //2) PRINCIPLE OF PRESERVATION : ABS WEIGHT
    //      If the opponent will win within their next move, all other principles
    //      become secondary,and the AI must intercept their winning move; all 
    //      other priorities are rescinded.
    private double poPRESERVATION(int x, int y){
        //absolute weight effect
        if(winSequence(opponentID, x, y, 4)){return principleWeightModifiers[2];}
        else{return MINVAL;}
    }
    //3) PRINCIPLE OF UNITY : INCREASING WEIGHT
    //      Cells which are proximate to other 'friendly' cells are likely
    //      to form a winning sequence. Thus, the AI should favor cells which
    //      are proximate, and may form a winning sequence.
    private double poUNITY(int x, int y){
        int friendlyNeighbors = neighborPlayerCount(playerID, x, y, 4);
        //increasing weight
        return principleWeightModifiers[3] * friendlyNeighbors;
    }
    
    //4) PRINCIPLE OF INTERSECTION : WEIGHT INCREASING
    //      Cells which bridge disjoint groups of cells shsould be favored becuase
    //      of the likelihood of forming a winning combination.
    private double poINTERSECTION(int x, int y){
        //increasing weight
        return principleWeightModifiers[4] * calculateIntersections(playerID, x, y);
    }
    
    //5) PRINCIPLE OF "COUP DE GRACE" : ABS WEIGHT
    //      If it is possible to win with the AI's move, do so; all other priorities
    //      rescinded.
    private double poGRACE(int x, int y){
        //absolute weight
        if(winSequence(playerID, x, y, 4)){return principleWeightModifiers[5];}
        else{return MINVAL;}
    }
    //6) PRINCIPLE OF CONTAINMENT : ABS WEIGHT
    //      The AI should seek to control the maximum size of territory that permits
    //      winning sequences by the AI, but prohibits winning sequences by the 
    //      opposition.
    private double poCONTAINMENT(int x, int y){
        AICell cell = new AICell(x, y);
        cell.setPlayerID(playerID);
        if(hullContain(cell, 10)){return principleWeightModifiers[6];}
        return MINVAL;
    }
    
    //7) PRINCIPLE OF OPPORTUNITY : INCREASING WEIGHT
    //      The AI should seek to maximize the potential of forming a winning sequence.
    //      Thus, the AI should prioritize the formation of of diagonal sequences.
    //      Diagnoal sequences permit eight opportunities to win; contrasting
    //      orthogonal sequences which only permit four opportunities to win.
    private double poOPPORUNITY(int x, int y){
        int diagonalNeighbors = 0;
        //check diagonal neighbors
        if(x - 1 >= 0 && y + 1 < boardY){if(gameState[x-1][y+1] == playerID){diagonalNeighbors++;}}
        if(x - 1 >= 0 && y - 1 >= 0){if(gameState[x-1][y-1] == playerID){diagonalNeighbors++;}}
        if(x + 1 < boardX && y + 1 < boardY){if(gameState[x+1][y+1] == playerID){diagonalNeighbors++;}}
        if(x + 1 < boardX && y - 1 >= 0){if(gameState[x+1][y-1] == playerID){diagonalNeighbors++;}}
        return principleWeightModifiers[7] * diagonalNeighbors;
    }
    //8) PRINCIPLE OF CONSERVATION : ABS WEIGHT
    //      Cells which can NEVER form a winning sequence for BOTH PLAYERS
    //      are relegated to the lowest priority.
    private double poCONSERVATION(int x, int y){
        if(calculatePotentialWinDirections(playerID, x, y, opponentID) == 0 && calculatePotentialWinDirections(opponentID, x, y, playerID) == 0){return principleWeightModifiers[8];}
        else{return MINVAL;}
    }
    //9) PRINCIPLE OF DRAGON'S TEETH : INCREASING WEIGHT
    //      All other principles are irrelevant, should the player engage in 
    //      the construction of diagonal-only sequences; woven in close proximity,
    //      "Dragon's Teeth" weave. Thus, the AI should be programmed to recognize
    //      this pattern, and pre-empt it by "blunting" the teeth - the end point
    //      of the player's diagonal sequences.
    private double poTEETH(int x, int y){
        return neighborPlayerCountDiagonalOnly(opponentID, x, y, 1) * principleWeightModifiers[9];
    }
    //10) PRINCIPLE OF PROGRESS : ABS WEIGHT
    //      If the AI or the player has played on the CELL, relegate cell to lowest priority.
    private double poPROGRESS(int x, int y){
        if(gameState[x][y] == playerID || gameState[x][y] == opponentID){return principleWeightModifiers[10];}
        else{return MINVAL;}
    }
    
    //11) PRINCIPLE OF BREAKOUT : DECREASING WEIGHT
    //      Cells which are encircled by hostile cells are unlikely to form winning combinations.
    //      Therefore, the AI should devalue such cells, and select cells which are exterior
    //      to the opposition encirclement, hence "BREAKOUT."
    private double poBREAKOUT(int x, int y){
        int op4 = 0;
        double weight = 0;
        //cell is completely encircled, set weight to minval
        op4 = neighborPlayerCount(opponentID, x, y, 1);
        if(op4 == 8 ){return Math.pow(principleWeightModifiers[11], 3);}
        op4 = neighborPlayerCountDiagonalOnly(opponentID, x, y, 1);
        if(op4 == 4){return principleWeightModifiers[11];}
        op4 = neighborPlayerCountOrthogonalOnly(opponentID, x, y, 1);
        if(op4 == 4){return principleWeightModifiers[11];}
        return 0;
    }
    
    //12) PRINCIPLE OF DESIRE : INCREASING WEIGHT
    //      The AI should attempt to the prioritize cells which have the potential
    //      to form a winning sequence.
    //      The conditions for prioritizing cells:
    //          Consider all possible directions.
    //          For each direction which is a possible win direction,
    //              mulityple the ultimate PRINCIPLEWEIGHTMODIFIER by
    //              the quantity of possible win directions
    //      A possible WIN DIRECTION is constituted by
    //          a WINCONDITION sequence depth that is UNINTERRUPTED by an opponent AI.
    private double poDESIRE(int x, int y){
        return calculatePotentialWinDirections(playerID, x, y, opponentID) * principleWeightModifiers[12];
    }
    //13) PRINCIPLE OF OP4 OBSTRUCTION : INCREASING WEIGHT
    // The AI should attempt to prioritize cells which obstruct winning sequences
    //      for the opponent.
    private double poOBSTRUCTION(int x, int y){
        double calcPotential = calculatePotentialWinDirections(opponentID, x, y, playerID) * principleWeightModifiers[13];
        if(winSequence(opponentID, x, y, 3)){return Math.pow(calcPotential, 2);}
        else{return calcPotential;}
    }
    
    
    
    //END ABSTRACT RULES--------------------------------------------------------
    
    /**
     * Determine if the cell may yield a win sequence for the given player
     * @param pID - the given player.
     * @param x - coordinate x
     * @param y - coordinate y
     * @param searchDepth - the depth to search for a true return of piD
     * @return TRUE if cell may result in a winning sequence
     */
    private boolean winSequence(int pID, int x, int y, int searchDepth){
        boolean win = false;
        int depth = searchDepth;
        int cX = 0;
        int cY = 0;
        //check diagonal--------------------------------------------------------
        int markUpLeft = 0;
        int markDownRight = 0;
        int markUpRight = 0;
        int markDownLeft = 0;
        cY = y;
        cX = x;
        for(int i = 1; i <= depth; i++){
            //searching up and to the left
            cY = y - i;
            cX = x - i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){markUpLeft++;}}
            
            //searching up and to the right
            cY = y - i;
            cX = x + i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){markUpRight++;}}
            
            //searching down and to the right
            cY = y + i;
            cX = x + i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){markDownRight++;}}
            
            //searching down and to the left
            cY = y + i;
            cX = x - i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){markDownLeft++;}}
            
            
        }
        if((markUpLeft >= depth || markDownRight >= depth) || (markUpRight + markDownLeft >= depth)){win = true; return win;}
        //check vertical--------------------------------------------------------
        int markUp = 0;
        int markDown = 0;
        cX = x;
        for(int i = 1; i <= depth; i++){
            cY = y - i;
            if(cY >= 0 && cY < boardY){if(gameState[cX][cY] == pID){markUp++;}}
            cY = y + i;
            if(cY >= 0 && cY < boardY){if(gameState[cX][cY] == pID){markDown++;}}
        }
        if(markUp >= depth || markDown >= depth){win = true; return win;}
        
        //check horizontal------------------------------------------------------
        int markLeft = 0;
        int markRight = 0;
        cY = y;
        for(int i = 1; i <= depth; i++){
            cX = x - i;
            if(cX >= 0 && cX < boardX){if(gameState[cX][cY] == pID){markLeft++;}}
            cX = x + i;
            if(cX >= 0 && cX < boardX){if(gameState[cX][cY] == pID){markRight++;}}
        }
        if(markLeft >= depth || markRight >= depth){win = true;}
        
        
        return win;
    }
    
    /**
     * Calculate the quantity of neighbors of the given player id up to a prescribed depth.
     * @param pID - the player ID
     * @param x - cell coordinate x
     * @param y - cell coordinate y
     * @param depth - the distance to consider neighboring cells
     * @return integer of the quantity of neighbors of given pID
     */
    private int neighborPlayerCount(int pID, int x, int y, int depth){
        int neighborCount = 0;
        int cX = 0;
        int cY = 0;
        //check diagonal--------------------------------------------------------
        
        cY = y;
        cX = x;
        for(int i = 1; i <= depth; i++){
            //searching up and to the left
            cY = y - i;
            cX = x - i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){neighborCount++;}}
            
            //searching up and to the right
            cY = y - i;
            cX = x + i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){neighborCount++;}}
            
            //searching down and to the right
            cY = y + i;
            cX = x + i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){neighborCount++;}}
            
            //searching down and to the left
            cY = y + i;
            cX = x - i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){neighborCount++;}}
            
            
        }
        
        //check vertical--------------------------------------------------------
        cX = x;
        for(int i = 1; i <= depth; i++){
            cY = y - i;
            if(cY >= 0 && cY < boardY){if(gameState[cX][cY] == pID){neighborCount++;}}
            cY = y + i;
            if(cY >= 0 && cY < boardY){if(gameState[cX][cY] == pID){neighborCount++;}}
        }
        
        
        //check horizontal------------------------------------------------------
        cY = y;
        for(int i = 1; i <= depth; i++){
            cX = x - i;
            if(cX >= 0 && cX < boardX){if(gameState[cX][cY] == pID){neighborCount++;}}
            cX = x + i;
            if(cX >= 0 && cX < boardX){if(gameState[cX][cY] == pID){neighborCount++;}}
        }
        return neighborCount;
    }
    
    private int neighborPlayerCountDiagonalOnly(int pID, int x, int y, int depth){
        int neighborCount = 0;
        int cX = 0;
        int cY = 0;
        //check diagonal--------------------------------------------------------
        
        cY = y;
        cX = x;
        for(int i = 1; i <= depth; i++){
            //searching up and to the left
            cY = y - i;
            cX = x - i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){neighborCount++;}}
            
            //searching up and to the right
            cY = y - i;
            cX = x + i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){neighborCount++;}}
            
            //searching down and to the right
            cY = y + i;
            cX = x + i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){neighborCount++;}}
            
            //searching down and to the left
            cY = y + i;
            cX = x - i;
            if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID){neighborCount++;}}
        }
        return neighborCount;
    }
    
    private int neighborPlayerCountOrthogonalOnly(int pID, int x, int y, int depth){
        int neighborCount = 0;
        int cX = 0;
        int cY = 0;
        //check vertical--------------------------------------------------------
        cX = x;
        for(int i = 1; i <= depth; i++){
            cY = y - i;
            if(cY >= 0 && cY < boardY){if(gameState[cX][cY] == pID){neighborCount++;}}
            cY = y + i;
            if(cY >= 0 && cY < boardY){if(gameState[cX][cY] == pID){neighborCount++;}}
        }
        
        
        //check horizontal------------------------------------------------------
        cY = y;
        for(int i = 1; i <= depth; i++){
            cX = x - i;
            if(cX >= 0 && cX < boardX){if(gameState[cX][cY] == pID){neighborCount++;}}
            cX = x + i;
            if(cX >= 0 && cX < boardX){if(gameState[cX][cY] == pID){neighborCount++;}}
        }
        return neighborCount;
    }
    
    /**
     * Determine the quantity of potential intersecting points for the given player.
     * @param pID - the given player
     * @param x - coordinate x
     * @param y - coordinate y
     * @return number of intersections available for the given player
     */
    private int calculateIntersections(int pID, int x, int y){
        int intersections = 0;
        //check diagonals
        
        int cX0 = x - 1;
        int cY0 = y + 1;
        int cX1 = x + 1;
        int cY1 = y - 1;
            
        if((cX0 >= 0 && cX0 < boardX) && (cY0 >= 0 && cY0 < boardY) && (cX1 >= 0 && cX1 < boardX) && (cY1 >= 0 && cY1 < boardY)){
            if(gameState[cX0][cY0] == pID && gameState[cX1][cY1] == pID){intersections++;}
            if(gameState[cX0][cY1] == pID && gameState[cX1][cY0] == pID){intersections++;}
        }
        
        //check horizontal
        cX0 = x;
        cY0 = y;
        if((cX0 - 1 >= 0 && cX0 + 1 < boardX)){
            if(gameState[cX0 -1][cY0] == pID && gameState[cX0 + 1][cY0] == pID){intersections++;}
        }
        
        //check vertical
        cX0 = x;
        cY0 = y;
        if((cY0 - 1 >= 0 && cY0 + 1 < boardY)){
            if(gameState[cX0][cY0 - 1] == pID && gameState[cX0][cY0 + 1] == pID){intersections++;}
        }
        return intersections;
    }
    
    private boolean hullContain(AICell rMost, int depth){
        
        //NEIGHBOR CELL ARRAY LIST CONSTRUCTION---------------------------------
        //find candidate cells left of rMost
        //establish cX & cY as upper-left search boundary for a given depth
        int cX = rMost.getCoordinates()[0] - depth;
        int cY = rMost.getCoordinates()[1] - depth;
        
        
        //search row by column for candidate cells friendly to rMost (across the row, first)
        //search left quadrants
        //valid friendly cells are added to the arraylist
        ArrayList<AICell> pLCells = new ArrayList();
        for(int j = 0; j < depth * 2; j++){
            for(int i = 0; i < depth; i++){
                //check for valid coordinates
                if(((cX + i) >= 0 && (cX + i) < boardX) && ((cY + j) >= 0 && cY + j < boardY)){
                    //determine if cell is friendly
                    
                    //DEBUG CODE
                    //System.out.println("boardX : boardY " + boardX + " : " + boardY);
                    //int scale0 = cX+i;
                    //int scale1 = cY+j;
                    //System.out.println("cX : cY " + scale0 + " : " + scale1);
                    //END DEBUG CODE
                    if(gameState[cX+i][cY+j] == rMost.getPlayerID()){AICell fCell = new AICell(cX+i, cY+j); fCell.setPlayerID(rMost.getPlayerID()); pLCells.add(fCell);}
                }
            }
        }
        
        //search right quandrants
         ArrayList<AICell> pRCells = new ArrayList();
        for(int j = cY; j < depth * 2; j++){
            for(int i = rMost.getCoordinates()[0]; i < depth; i++){
                //check for valid coordinates
                if((cX + i >= 0 && cX + i < boardX) && (cY + j >= 0 && cY + j< boardY)){
                    //determine if cell is friendly
                    if(gameState[cX+i][cY+j] == rMost.getPlayerID()){AICell fCell = new AICell(cX+i, cY+j); fCell.setPlayerID(rMost.getPlayerID()); pRCells.add(fCell);}
                }
            }
        }
        //END CONSTRUCTION OF NEIGHBOR CELL ARRAYLISTS--------------------------
        
        //find the left most upper and lower quandrants, and left most lower quandrant cells
        int maxXUP = rMost.getCoordinates()[0];
        int maxXLOW = rMost.getCoordinates()[0];
        int maxY = 0;
        int minY = rMost.getCoordinates()[1] + depth;
        AICell tempCell = new AICell(0, 0);
        AICell cLeftMostUpperQuad = tempCell;
        AICell cLeftMostLowerQuad = tempCell;
        for(AICell cell : pLCells){
            //find the cLeftMostUpperQuad
            if(cell.getCoordinates()[1] <= minY){
                minY = cell.getCoordinates()[1];
                if(cell.getCoordinates()[0] <= maxXUP){
                    maxXUP = cell.getCoordinates()[0];
                    cLeftMostUpperQuad = cell;
                }
            }
            else if (cell.getCoordinates()[1] >= maxY){
                maxY = cell.getCoordinates()[1];
                if(cell.getCoordinates()[0] <= maxXLOW){
                    maxXLOW = cell.getCoordinates()[0];
                    cLeftMostLowerQuad = cell;
                }
            }
        }
        //repeat process for rightmost cells
        maxXUP = rMost.getCoordinates()[0];
        maxXLOW = rMost.getCoordinates()[0];
        maxY = 0;
        minY = rMost.getCoordinates()[1] + depth;
        AICell cRightMostUpperQuad = tempCell;
        AICell cRightMostLowerQuad = tempCell;
        for(AICell cell : pRCells){
            //find the cRightMostUpperQuad
            if(cell.getCoordinates()[1] <= minY){
                minY = cell.getCoordinates()[1];
                if(cell.getCoordinates()[0] >= maxXUP){
                    maxXUP = cell.getCoordinates()[0];
                    cRightMostUpperQuad = cell;
                }
            }
            else if (cell.getCoordinates()[1] >= maxY){
                maxY = cell.getCoordinates()[1];
                if(cell.getCoordinates()[0] >= maxXLOW){
                    maxXLOW = cell.getCoordinates()[0];
                    cRightMostLowerQuad = cell;
                }
            }
        }
        
        //determine if the vertices form a minimal containment
        //minimal containment is that no winning sequence can be formed
        //by the opposition within the contained area, id est, 
        //the area must be less than or equal to the MINCONTAINMENT value
        double a = Math.sqrt(Math.pow(rMost.getCoordinates()[0] - cLeftMostUpperQuad.getCoordinates()[0], 2) + Math.pow(rMost.getCoordinates()[1] - cLeftMostUpperQuad.getCoordinates()[1], 2));
        double c = Math.sqrt(Math.pow(rMost.getCoordinates()[0] - cLeftMostLowerQuad.getCoordinates()[0], 2) + Math.pow(rMost.getCoordinates()[1] - cLeftMostLowerQuad.getCoordinates()[1], 2));
        double b = Math.sqrt(Math.pow(cLeftMostUpperQuad.getCoordinates()[0] - cLeftMostLowerQuad.getCoordinates()[0], 2) + Math.pow(cLeftMostUpperQuad.getCoordinates()[1] - cLeftMostLowerQuad.getCoordinates()[1], 2));
        double p = (a + b + c) / 2.0;
        //apply Heron's formula to solve for A
        double area = Math.sqrt(p * (p-a) * (p-b) * (p-c));
        
        double a0 = Math.sqrt(Math.pow(rMost.getCoordinates()[0] - cRightMostUpperQuad.getCoordinates()[0], 2) + Math.pow(rMost.getCoordinates()[1] - cRightMostUpperQuad.getCoordinates()[1], 2));
        double c0 = Math.sqrt(Math.pow(rMost.getCoordinates()[0] - cRightMostLowerQuad.getCoordinates()[0], 2) + Math.pow(rMost.getCoordinates()[1] - cRightMostLowerQuad.getCoordinates()[1], 2));
        double b0 = Math.sqrt(Math.pow(cRightMostUpperQuad.getCoordinates()[0] - cRightMostLowerQuad.getCoordinates()[0], 2) + Math.pow(cRightMostUpperQuad.getCoordinates()[1] - cRightMostLowerQuad.getCoordinates()[1], 2));
        double p0 = (a + b + c) / 2.0;
        //apply Heron's formula to solve for A
        double area0 = Math.sqrt(p0 * (p0-a0) * (p0-b0) * (p0-c0));
        
        if(area <= MINCONTAINMENT || area0 <= MINCONTAINMENT){return true;}
        else{return false;}
    }
    
    private int calculatePotentialWinDirections(int pID, int x, int y, int op4){
        int potentialWinSequence = 0;
        int depth = WINCONDITION;
        int cX = 0;
        int cY = 0;
        //check diagonal--------------------------------------------------------
        boolean foundUpLeftOp4 = false;
        boolean foundUpRightOp4 = false;
        boolean foundDownLeftOp4 = false;
        boolean foundDownRightOp4 = false;
        int markUpLeft = 0;
        int markDownRight = 0;
        int markUpRight = 0;
        int markDownLeft = 0;
        cY = y;
        cX = x;
        for(int i = 1; i <= depth; i++){
            //searching up and to the left
            cY = y - i;
            cX = x - i;
            if(!foundUpLeftOp4){
                if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID || gameState[cX][cY] != op4){markUpLeft++;}else{foundUpLeftOp4 = true;}}
            }
            //searching up and to the right
            cY = y - i;
            cX = x + i;
            if(!foundUpRightOp4){
                if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID || gameState[cX][cY] != op4){markUpRight++;}else{foundUpRightOp4 = false;}}
            }
            //searching down and to the right
            cY = y + i;
            cX = x + i;
            if(!foundDownRightOp4){
                if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID || gameState[cX][cY] != op4){markDownRight++;}else{foundDownRightOp4 = true;}}
            }
            //searching down and to the left
            cY = y + i;
            cX = x - i;
            if(!foundDownLeftOp4){
                if((cY >= 0 && cY < boardY) && (cX >= 0 && cX < boardX)){if(gameState[cX][cY] == pID || gameState[cX][cY] != op4){markDownLeft++;}else{foundDownLeftOp4 = true;}}
            }
            
        }
        if((markUpLeft + markDownRight >= depth)){potentialWinSequence++;}
        if((markUpRight + markDownLeft >= depth)){potentialWinSequence++;}
        //check vertical--------------------------------------------------------
        boolean foundUpOp4 = false;
        boolean foundDownOp4 = false;
        int markUp = 0;
        int markDown = 0;
        cX = x;
        for(int i = 1; i <= depth; i++){
            cY = y - i;
            if(!foundUpOp4){
                if(cY >= 0 && cY < boardY){if(gameState[cX][cY] == pID || gameState[cX][cY] != op4){markUp++;}else{foundUpOp4 = true;}}
            }
            cY = y + i;
            if(!foundDownOp4){
                if(cY >= 0 && cY < boardY){if(gameState[cX][cY] == pID || gameState[cX][cY] != op4){markDown++;}else{foundDownOp4 = true;}}
            }
        }
        if(markUp + markDown >= depth){potentialWinSequence++;}
        
        //check horizontal------------------------------------------------------
        boolean foundLeftOp4 = false;
        boolean foundRightOp4 = false;
        int markLeft = 0;
        int markRight = 0;
        cY = y;
        for(int i = 1; i <= depth; i++){
            cX = x - i;
            if(!foundLeftOp4){
                if(cX >= 0 && cX < boardX){if(gameState[cX][cY] == pID || gameState[cX][cY] != op4){markLeft++;}else{foundLeftOp4 = true;}}
            }
            cX = x + i;
            if(!foundRightOp4){
                if(cX >= 0 && cX < boardX){if(gameState[cX][cY] == pID || gameState[cX][cY] != op4){markRight++;}else{foundRightOp4 = true;}}
            }
        }
        if(markLeft + markRight >= depth){potentialWinSequence++;}
        
        
        return potentialWinSequence;
    }
}

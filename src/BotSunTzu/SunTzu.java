
package BotSunTzu;

import AISimulationEnvironment.BotInterface;
import java.util.ArrayList;

/**
 *
 * @author Ermenildo V. Castro, Jr.
 * GoMoKu AI: SunTzu
 * Version 1.0.0
 * 
 * Description:
 * You cannot hide. 
 * You cannot win. 
 * By choosing to compete against this AI, 
 *      you have already lost.
 * 
 * "The general who wins the battle makes many calculations in his temple before 
 * the battle is fought. The general who loses makes but few calculations beforehand."
 *          - Sun Tzu, "The Art of War"
 */
public class SunTzu implements BotInterface{
    int[][] gameState;
    GeneralRulesCorpus grc;
    int playerID;
    int opponentID;
    int difficulty; //0: easy; 1: meadium; 2: hard
    private boolean DEBUGMODE = true;
    final int MANDATORYWEIGHT = 800;
    //constructor
    public SunTzu(boolean playerID, int[][] gameBoard, int difficulty){
        gameState = gameBoard;
        this.difficulty = difficulty;
        
        if(playerID){this.playerID = 1; this.opponentID = -1;}
        else{this.playerID = -1; this.opponentID = 1;}
        
        grc = new GeneralRulesCorpus(this.playerID, gameBoard);
    }
    
    public void setDifficulty(int difficulty){this.difficulty = difficulty;}
    
    @Override
    public int[] fetchMove(int[][] boardState) {
        gameState = boardState;
        //perform calculus
        //1) calculus by GeneralRulesCorpus
        int[][] grcMatrix = calcGeneralRulesCorpus();
        //2) calculus by ExperienceCorupus : TODO
        //3) calculus by StrategyCorpus : TODO
        
        AICell[] solutions = findOptimalMoves(grcMatrix);
        if(DEBUGMODE){
        System.out.println("SunTzu SOLUTIONS: ");
        System.out.println("Easy: X : Y ---" +solutions[0].getCoordinates()[0] + " : "+solutions[0].getCoordinates()[1]);
        System.out.println("Normal: X : Y---" +solutions[1].getCoordinates()[0] + " : "+solutions[0].getCoordinates()[1]);
        System.out.println("Hard: X : Y ---" +solutions[2].getCoordinates()[0] + " : "+solutions[0].getCoordinates()[1]);
        }
        switch (difficulty){
            case 0: return solutions[0].getCoordinates();
            case 1: return solutions[1].getCoordinates();
            case 2: return solutions[2].getCoordinates();
        }
        if(DEBUGMODE){System.out.println("SunTzu: Failed to find optimal solution");}
        int[] fail = {-1 , -1};
        return fail;
    }
    
    private int[][] calcGeneralRulesCorpus(){
        grc.updateGameState(gameState);
        grc.constructWeightedMatrix();
        int[][] grcMatrix = grc.getWeightedMatrix();
        if(DEBUGMODE){System.out.println("SunTzu: grc matrix returned.");}
        return grcMatrix;
    }
    
    /**
     * 
     * @param weightedBoard
     * @return a set of optimal moves from least optimal -> most optimal; size 3
     */
    private AICell[] findOptimalMoves(int[][] weightedBoard){
        int sizeX = weightedBoard.length;
        int sizeY = weightedBoard[0].length;
        int maxWeight = 0;
        int weight = 0;
        int weightSum = 0;
        int mean = 0;
        double meanDif = 0;
        double stdDeviation = 0;
        ArrayList<AICell> weightCellList = new ArrayList();
        int maxXC = 0;
        int maxYC = 0;
        for(int j = 0; j < sizeY; j++){
            for(int i = 0; i < sizeX; i++){
                if(gameState[i][j] != opponentID && gameState[i][j] != playerID){ //cell does not belong to opposition or to self
                    weight = weightedBoard[i][j];
                    if(weight > 0){ //only consider non-negative and nonzero weights
                        AICell tempCell = new AICell(playerID, i, j, weight);
                        weightSum += weight;
                        weightCellList.add(tempCell);
                    }
                    if(weight >= maxWeight){
                        maxWeight = weight;
                        maxXC = i;
                        maxYC = j;
                    }
                }
                
            }
        }
        
        
        //declare cell for optimal move
        AICell optimalCell = new AICell(playerID, maxXC, maxYC, maxWeight);
        ArrayList<AICell> aicells = this.sortAICells(weightCellList);
        if(DEBUGMODE){
            //print values of sorted cell
            for(AICell cell : aicells){System.out.print(cell.getWeight() + " ");}
        }
        AICell avgCell = aicells.get(1);
        AICell subAvgCell = aicells.get(2);
        //find average move, and min move
        /*
        //calculate average
        if(weightCellList.size() != 0){mean = weightSum / weightCellList.size();}
        //calculate standard deviation
        for(AICell cell : weightCellList){
            meanDif += (Math.pow((cell.getWeight() - mean), 2));
        }
        //stdDeviation = Math.sqrt((meanDif / weightCellList.size()));
        */
        
        //find a cell that is within at-least one standard deviation away
        //AICell avgCell = new AICell(0, 0);
        //AICell subAvgCell = new AICell(0, 0); //incorporate standard deviation
        /*
        boolean avgSearchCont = true;
        int counter = 0;
        boolean foundAvg = false;
        boolean foundMin = false;
        if(weightCellList.isEmpty()){avgSearchCont = false;}
        */
        //DEBUG CODE
        if(DEBUGMODE){
        System.out.println("-------------------------");
        System.out.println("SunTzu: Finding Solution, Statistics");
        System.out.println("mean: " + mean);
        System.out.println("stdDeviation: " + stdDeviation);
        System.out.println("maxWeight: " + maxWeight);
        }
        //END DEBUG CODE
        //if optimal solution is MANDATORY - id est, the AI MUST PLAY THE OPTIMAL SOLUTION
        if(maxWeight >= MANDATORYWEIGHT){avgCell = optimalCell; subAvgCell = optimalCell;}
        /*
        while(avgSearchCont){
            if(counter == weightCellList.size() - 1){avgSearchCont = false;}
            int cWeight = weightCellList.get(counter).getWeight();
            //must be within one standard deviation, down, from the optimal solution
            if(!foundAvg && cWeight >= maxWeight - stdDeviation && cWeight < maxWeight && cWeight >= 0){
                avgCell = weightCellList.get(counter);
                foundAvg = true;
            }
            // two standard deviations, down - minimal optimal solution
            if(!foundMin && (cWeight >= maxWeight - (stdDeviation * 2)) && (cWeight < maxWeight - stdDeviation) && cWeight >= 0){
                subAvgCell = weightCellList.get(counter);
                foundMin = true;
            }
            if(foundAvg && foundMin){avgSearchCont = false;}
            counter++;
        }
        */
        //if(!foundMin){if(DEBUGMODE){System.out.println("SunTzu: Unable to find subAvgCell");}subAvgCell = avgCell;}
        AICell[] solutions = {subAvgCell, avgCell, optimalCell};
        if(DEBUGMODE){grc.displayCellDataWeight(optimalCell.getCoordinates()[0], optimalCell.getCoordinates()[1]); grc.displayWeightedBoard(); grc.displayGameState();}
        return solutions;
    }
    
    private ArrayList<AICell> sortAICells(ArrayList<AICell> aicells){
        //sort list from largest to smallest
        for(int i = 0; i < aicells.size() - 1; i++){
            AICell currentCell = aicells.get(i);
            for(int j = i + 1; j < aicells.size(); j ++){
                AICell nextCell = aicells.get(j);
                if(currentCell.getWeight() < nextCell.getWeight()){
                    //swap the currentCell with nextCell
                    AICell tempcell = currentCell;
                    currentCell = nextCell;
                    aicells.set(i, nextCell);
                    aicells.set(j, tempcell);
                }
            }
        }
        return aicells;}
    
}

/*
 * The MIT License
 *
 * Copyright 2015 Vivo.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package AISimulationEnvironment;

import java.util.Random;

/**
 *
 * @author Vivo
 */
public class randBot implements BotInterface{
    private int boardSize;
    private Random rand;
    private int[][] gameState;
    
    public randBot() {
        boardSize = 30;
        rand = new Random();
    }

    @Override
    public int[] fetchMove(int[][] boardState) {
        gameState = boardState;
        return calculateMove();
    }
    
    private int[] calculateMove(){
        int x = rand.nextInt(boardSize);
        int y = rand.nextInt(boardSize);
        System.out.println("randBot, calculateMove(): X Y" + x + " " + y);
        while(gameState[x][y] != 0){
            x = rand.nextInt(boardSize);
            y = rand.nextInt(boardSize);
        }
        int[] coordinates = {x, y};
        return coordinates;
    }
}

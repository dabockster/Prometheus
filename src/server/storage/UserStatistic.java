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
package server.storage;

import server.storage.UserProfile;


/**
 *
 * @author Vivo
 * UserStatistic:
 *  Record for all pertinent previous game information collected by the user
 */
public class UserStatistic {
    private UserProfile user;
    private int victories;
    private int losses;
    private int draws;
    private int totalGames;
    private int streak;
    private int rating;
    private double timePerMove;
    private double totalTimePlayed;
    //constructor
    public UserStatistic(UserProfile user){this.user = user; 
        victories = 0; 
        losses = 0; 
        draws = 0; 
        totalGames = 0; 
        streak = 0; 
        rating = 0;
        timePerMove = 0;
        totalTimePlayed = 0;
    }

    //define getters and setters------------------------------------------------
    /*
    getTuple()
    @return an Object array containing all private user statistic information
    */
    public Object[] getTuple(){
        Object[] tuple = {
        user, victories, losses, draws, totalGames, streak, rating, timePerMove, totalTimePlayed
        }; 
        return tuple;}
    
    public boolean setVictories(int vic){
        if(vic >=0){
            victories = vic; 
            return true;
        } 
        return false;
    }
    
    public boolean setLosses(int loss){
        if(loss >=0){
            losses = loss; 
            return true;
        } 
        return false;
    }
    
    public boolean setDraws(int draw){
        if(draw >=0){
            draws = draw; 
            return true;
        } 
        return false;
    }
    
    public boolean setStreak(int streak){
        this.streak = streak;
        return true;
    }
    
    public boolean setRating(int rating){
        if(rating >=0){
            this.rating = rating; 
            return true;
        } 
        return false;
    }
    
    public boolean setTimePerMove(double time){
        if(time >=0){
            timePerMove = time; 
            return true;
        } 
        return false;
    }
    
    public boolean setTotalTimePlayed(double time){
        if(time >=0){
            totalTimePlayed = time; 
            return true;
        } return false;
    }
    //--------------------------------------------------------------------------
}

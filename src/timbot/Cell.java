/*
 * The MIT License
 *
 * Copyright 2015 Timothy Ernst.
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
package timbot;

/**
 *
 * @author Timothy Ernst
 */
public class Cell {
    
    final int OPEN = 0;
    final int HUMAN = 1;
    final int TIMBOT = 2;
    
    public boolean isOpen;
    public boolean isHuman;
    public boolean isTimbot;
    
    private int presentState;
    
    private Cell n;  //north
    private Cell ne; //northeast
    private Cell e;  //east
    private Cell se; //southeast
    private Cell s;  //south
    private Cell sw; //southwest
    private Cell w;  //west
    private Cell nw; //nortwest
   
    /**
     * This holds true for the following four methods
     * @return 0 : no sequence
     *         1 : is first of sequence
     *         2 : is middle of sequence
     *         3 : is end of sequence
     */
    private int diagIncreasing(int state){
        if(ne.hasState(state)){
            if(sw.hasState(state)){
                return 2;
            }else{
                return 1;
            }
        }else if(sw.hasState(state)){
            return 3;
        }else
            return 0;
    }
    private int diagDecreasing(int state){
        if(nw.hasState(state)){
            if(se.hasState(state)){
                return 2;
            }else{
                return 3;
            }
        }else if(se.hasState(state)){
            return 1;
        }else
            return 0;
    }
    private int horizontal(int state){
        if(e.hasState(state)){
            if(w.hasState(state)){
                return 2;
            }else{
                return 1;
            }
        }else if(w.hasState(state)){
            return 3;
        }else{
            return 0;
        }
    }
    private int vertical(int state){
        if(n.hasState(state)){
            if(s.hasState(state)){
                return 2;
            }else{
                return 1;
            }
        }else if(s.hasState(state)){
            return 3;
        }else{
            return 0;
        }
    }
    
    public boolean hasState(int otherState){
        return this.presentState == otherState;
    }
}
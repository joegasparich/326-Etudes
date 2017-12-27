/**
 * FitPiece contains a list of possible pieces, with relative coordinates.
 * Places piece into the window and checks for out of bounds placement.
 * Team: Alfred Pardoe, Joe Gasparich, Tyler Baker, Will Shaw
 *
 */

import java.util.*;

public class FitPiece {



    public static final int numPieces = 19; // number of pieces, for reference
    public  static final int[][] pieces = {
    
    //square
    {0,0,1,0,0,1,1,1},
    
    //Z vertical 1
    {0,0,1,0,0,1,1,-1},
    //Z vertical 2
    {0,0,0,1,1,1,1,2},
    //Z horizontal 1
    {0,0,1,0,1,1,2,1},
    //Z horizontal 2
    {0,0,1,0,1,-1,2,-1},
    
    //long horizontal
    {0,0,1,0,2,0,3,0},
    //long vertical
    {0,0,0,1,0,2,0,3},
    
    //T shape vertical 1
    {0,0,0,1,0,2,1,1},
    //T shape vertical 2
    {0,0,1,0,1,1,1,-1},
    //T shape hor 1
    {0,0,1,0,2,0,1,-1},
    //T shape hor 2
    {0,0,1,0,2,0,1,1},
    
    //L vert 1
    {0,0,0,1,0,2,1,2},
    //L vert 2
    {0,0,1,0,1,1,1,2},
    //L hor 1
    {0,0,1,0,2,0,0,1},
    //L hor 2
    {0,0,1,0,2,0,2,-1},
    
    //J hor 1
    {0,0,1,0,2,0,2,1},
    //J hor 2
    {0,0,0,1,1,1,2,1},
    //J vert 1
    {0,0,1,0,1,-1,1,-2},
    //J vert 2
    {0,0,1,0,0,1,0,2},
    
    };


    //attempts to fit a peice into a the states window by iterating through the four squares of
    //the tetris piece. returns the new state if successful or null if any of the rules are broken
    public static State placePiece(State state,int startRow, int index){
        
        State newState = new State(state.length, state.width);
        newState.window = state.window;
        
        for(int i = 0; i < 7; i+=2) {
            int x = 0+pieces[index][i];
            int y = startRow+pieces[index][i+1];
            //if the piece goes out of bounds, return a null state
            if(x < 0 || y < 0 || x > newState.length-1 || y > newState.width-1) {
                return null;
            }
            //if the piece overlaps another already placed, return null
            if(!newState.getCellState(x,y)){
                newState.window[x][y] = true;
            } else {
                return null;
            }
        }
        newState.clearColumns();
        //newState.display();
        return newState;
    }
}



















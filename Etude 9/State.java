/**
 * State class to store the current state of a window.
 * Team: Alfred Pardoe, Joe Gasparich, Tyler Baker, Will Shaw
 *
 */

import java.util.*;

/*
 This class is a state that has neighbours (states that can be reached from this state
 and also does the logic for adding a new piece into the window.
 */
public class State {

    boolean [][] window; //The sliding window
    int number; //The index of the state
    ArrayList<State> neighbours; //States that can be reached by placing one piece from this state
    int length;
    int width;
    boolean isValid = true; //Whether the window is valid

    //generate a fresh empty state from the cmd args for its new width/length
    public State(int length , int width) {
        
        this.length = length;
        this.width = width;
        window = new boolean[length][width];
        neighbours = new ArrayList<State>();
        
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                window[i][j] = false;
            }
        }
        number = 0;
    }

    //Create a new statew based off an old one and place a piece in it
    public State(State prevState, int piece, int number) {
        
        this.length = prevState.length;
        this.width = prevState.width;

        this.window = new boolean[length][width];
        for(int i=0; i<length; i++) {
            for(int j=0; j<width; j++) {
                this.window[i][j] = prevState.window[i][j];
            }
        }
        this.number = number;
        neighbours = new ArrayList<State>();
        
        State newState = FitPiece.placePiece(this, startRow(), piece);
        if(newState != null) {
            this.window = newState.window;
            isValid = true;
        } else {
            isValid = false;
        }
    }
    
    //finds the location on the carpet where we will next try to place a piece
    public int startRow() {
        
        for (int i = 0; i < width; i++) {
            if (!window[0][i]) {
                return i;
            }
        }
        return -1;
    }

    //accessor for the state of a cell in the window
    public boolean getCellState(int x, int y) {
        return window[x][y];
    }
   
    //clears the columns that are full - shifts all values to the left
    public void clearColumns(){
        boolean done = false;
        while(!done){
            done = false;
            for (int i = 0; i < width; i++) {
                if(!this.window[0][i]){
                    done = true;
                }
            }
            if(!done){
                for(int i = 0;i<length-1;i++) {
                    for(int j = 0; j < width;j++) {
                        window[i][j] = window[i+1][j];
                    }
                }
                for (int i = 0; i < width;i++) {
                    window[length-1][i] = false;
                }
            }
        }
    }
    
    //Creates a hash key for keeping track of each state
    public String makeHashKey() {
        String key = "";
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(window[j][i]){
                    key += "1";
                }else {
                    key += "0";
                }
            }
        }
        return key;
    }
    
    //adds a new neightbour to this State
    public void addNeighbour (State neighbour) {
        if (!neighbours.contains(neighbour)) {
            this.neighbours.add(neighbour);
        }
    }
}























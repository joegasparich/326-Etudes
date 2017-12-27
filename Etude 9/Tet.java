/**
 * Tet main. Reads from an input file and solves for possible carpet placements.
 * Team: Alfred Pardoe, Joe Gasparich, Tyler Baker, Will Shaw
 *
 */

import java.util.*;
import java.math.BigInteger;

public class Tet {

    private static ArrayList<State> stateQueue; //Queue of unexpanded states
    private static HashMap<String, State> stateMap; //Hash map of discovered states
    private static int numStates;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String line;

        while (sc.hasNextLine()) {

            line = sc.nextLine();

            if (!line.isEmpty() && !line.matches("^#.*")) {

                if (line.split(" ").length != 2) {
                    System.out.println("Bad input");
                    System.exit(1);
                }

                int width = Integer.parseInt(line.split(" ")[0]);
                int length = Integer.parseInt(line.split(" ")[1]);

                if ((length >= 1 && length <= 100) && (width >= 1 && width <= 6) && ((length * width) % 4 == 0)) {
                    // The input is ok. Solve for solutions.
                    System.out.println(line);
                    System.out.println("# " + solve(length, width));
                    System.out.println();
                } else {
                    System.err.println("Invalid input");
                } 

                //System.out.println(width);
                //System.out.println(length);
            }
        }
        //System.out.println(solve(100, 6));
        
    }
    
    //Finds the number of iterations of carpets that will fit in a given width and length
    public static BigInteger solve(int length, int width) {
        
        //--Section One--//
        // Creates a graph of all possible states and their connections to one another.
        // This continues to loop through the queue until there are no more undiscovered states
        
        //Initialise variables
        stateQueue = new ArrayList<State>();
        stateMap = new HashMap<String, State>();
        numStates = 0;

        //Create the empty state (which is the initial and solution state)
        // and adds it to the hash map and queue
        State emptyState = new State(length, width);
        stateMap.put(emptyState.makeHashKey(), emptyState);
        numStates++;
        stateQueue.add(emptyState);
        
        //While there are states that haven't been expanded
        while(stateQueue.size() > 0) {
            State currentState = stateQueue.remove(0);//Pop the first state
            //For each piece
            for(int piece=0; piece<FitPiece.numPieces; piece++) {
                //Place piece in new state
                State newState = new State(currentState, piece, numStates);
                //If new state is valid (i.e. piece fits)
                if(newState.isValid) {
                    String newKey = newState.makeHashKey();
                    if(stateMap.containsKey(newKey)) {
                        //State already exists in graph so add connection
                        State existingState = stateMap.get(newKey);
                        currentState.addNeighbour(existingState);
                    } else {
                        //State doesn't exist in graph
                        //so create and add connection
                        numStates++;
                        stateQueue.add(newState);
                        stateMap.put(newKey, newState);
                        currentState.addNeighbour(newState);
                    }
                }
            }
        }

        
        //--Section Two--//
        // Loops through the amount of pieces to place, using the graph to figure out the
        // total amount of ways to get to a given state.
        // Does this by creating a table that stores these values per piece placed
        
        int pieceCount = (width*length)/4; //Number of pieces to be placed
        
        //Initialise table and fill it with 0s
        BigInteger[][] table = new BigInteger[numStates + 1][pieceCount + 1];
        for(int i = 0; i < numStates + 1; i++) {
            for (int j = 0; j < pieceCount + 1; j++) {
                table[i][j] = BigInteger.ZERO;
            }
        }
        
        //Get sorted array of all states
        Collection<State> stateCollection = stateMap.values();
        List<State> stateArray = new ArrayList<State>(stateCollection);
        Collections.sort(stateArray, new Comparator<State>() {
            @Override
            public int compare(State s1, State s2) {
                if(s1.number > s2.number) return 1;
                if(s1.number < s2.number) return -1;
                else return 0;
            }
        });
        
        //System.out.println("Number of pieces: " + pieceCount);
        //System.out.println("Number of states: " + numStates);

        //Empty state is the only state when 0 pieces have been placed
        table[0][0] = BigInteger.ONE;

        //Move down the table, adding current state's value to its neighbours
        for(int row=0; row<pieceCount; row++) {
            for(State current : stateArray) {
                for(State neighbour : current.neighbours) {
                    table[neighbour.number][row+1] = table[neighbour.number][row+1].add(table[current.number][row]);
                }
            }
        }
        //The value in the bottom left cell will be the result
        return table[0][pieceCount];
        
    }

}

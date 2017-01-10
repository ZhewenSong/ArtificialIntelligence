/****************************************************************
 * studPlayer.java
 * Implements MiniMax search with A-B pruning and iterative deepening search (IDS). The static board
 * evaluator (SBE) function is simple: the # of stones in studPlayer's
 * mancala minue the # in opponent's mancala.
 * -----------------------------------------------------------------------------------------------------------------
 * Licensing Information: You are free to use or extend these projects for educational purposes provided that
 * (1) you do not distribute or publish solutions, (2) you retain the notice, and (3) you provide clear attribution to UW-Madison
 *
 * Attribute Information: The Mancala Game was developed at UW-Madison.
 *
 * The initial project was developed by Chuck Dyer(dyer@cs.wisc.edu) and his TAs.
 *
 * Current Version with GUI was developed by Fengan Li(fengan@cs.wisc.edu).
 * Some GUI components are from Mancala Project in Google code.
 */




//################################################################
// studPlayer class
//################################################################
import java.util.*;
public class zsong39Player extends Player {


    /*Use IDS search to find the best move. The step starts from 1 and keeps incrementing by step 1 until
	 * interrupted by the time limit. The best move found in each step should be stored in the
	 * protected variable move of class Player.
     */
    public void move(GameState state)
    {
        for (int depth=1; ; depth++) {
        	this.move = maxAction(state, depth);
        	//System.out.println(depth);
        }
    	
    }
    

    // Return best move for max player. Note that this is a wrapper function created for ease to use.
	// In this function, you may do one step of search. Thus you can decide the best move by comparing the 
	// sbe values returned by maxSBE. This function should call minAction with 5 parameters.
    public int maxAction(GameState state, int maxDepth)
    {	
    	int bestMove = 0;
    	
    	int bestVal = Integer.MIN_VALUE;
    	for (int s=0; s<6; s++) {
    		if (state.illegalMove(s)) continue;
    		GameState successor = new GameState(state);

    		boolean freeTurn = successor.applyMove(s);
    		int val;
    		if (freeTurn)
    			val = maxAction(successor, 0, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE); 
    		else
    			val = minAction(successor, 1, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE); 

    		if (bestVal < val) {
    			bestVal = val;
    			bestMove = s;
    		}


    	}
		
    	return bestMove;
    }
    
	//return sbe value related to the best move for max player
    public int maxAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta)
    {
    	if (maxDepth == currentDepth || state.gameOver()) {
    		return sbe(state);
    	}
    	int bestVal = Integer.MIN_VALUE;
    	for (int s=0; s<6; s++) {
    		if (state.illegalMove(s)) continue;
    		GameState successor = new GameState(state);

    		boolean freeTurn = successor.applyMove(s);
    		int val;
    		if (freeTurn) // if free turn, go deeper in maxAction
    			val = maxAction(successor, currentDepth, maxDepth, alpha, beta); 
    		else // else step into minAction
    			val = minAction(successor, currentDepth + 1, maxDepth, alpha, beta); 
    		if (bestVal < val) bestVal = val;
    		if (bestVal >= beta) return bestVal;
    		alpha = Math.max(alpha, bestVal);

    	}
    	return bestVal;
    }
    //return sbe value related to the best move for min player
    public int minAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta)
    {
    	if (currentDepth == maxDepth || state.gameOver()) {
    		return sbe(state);
    	}

    	int bestVal = Integer.MAX_VALUE;
    	for (int s=7; s<13; s++) {
    		if (state.illegalMove(s)) continue;
    		GameState successor = new GameState(state);
    		boolean freeTurn = successor.applyMove(s);
    		int val;
    		if (freeTurn) // if free turn, go deeper in minAction
    			val = minAction(successor, currentDepth, maxDepth, alpha, beta); 
    		else // else step into maxAction
    			val = maxAction(successor, currentDepth + 1, maxDepth, alpha, beta); 
    		if (bestVal > val) bestVal = val;
			if (bestVal <= alpha) return bestVal;
    		beta = Math.min(beta, bestVal);
    		
    		
    	}
    	return bestVal;
    }

    //the sbe function for game state. Note that in the game state, the bins for current player are always in the bottom row.
    private int sbe(GameState state)
    {

    	return state.stoneCount(6) - state.stoneCount(13);
    	
    }
}
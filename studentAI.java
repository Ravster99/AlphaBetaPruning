public class studentAI extends Player {
    private int maxDepth;
   
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void move(BoardState state) {
    	move = alphabetaSearch(state, maxDepth);
    }
    
    public int alphabetaSearch(BoardState state, int maxDepth) {
    	/*Initializing some preliminary variables.*/
    	int moves_selection = 0;
    	int v = -1000;
    	int v_max = -5000;
    	int alpha = -1000;
    	/*For checking of minimum value.*/
    	/*int x = minValue(state, maxDepth, 1, -1000, 1000);*/
    	/*For checking of maximum value, set currentDepth from here.*/
    	int currentDepth = maxDepth;
    	/*Dealing with the first node to keep track of the best move.*/
    	for (int i = 0; i <= 5; i++){
    		if (state.isLegalMove(1, i) == true){
    			BoardState explored_moves_main;
    			explored_moves_main = state.applyMove(1, i);
    			/*Explore further nodes if the present node is not a leaf.*/
    			if (currentDepth >= 1){
    				v = maxValue(explored_moves_main, maxDepth, currentDepth - 1, alpha, 1000);
    			}
    			/*Do not explore further nodes if the present node is a leaf.*/
    			else{
    				v = sbe(state);
    			}
		    	alpha = Math.max(alpha, v);
		    	/*Determining the best v values and the corresponding move.*/
		    	if (v > v_max){
		    		v_max = v;
		    		moves_selection = i;
		    	}
    		}
    	}
    	return moves_selection;
    }

    public int maxValue(BoardState state, int maxDepth, int currentDepth, int alpha, int beta) {    	
    	/*Evaluating the score at the final move.*/
    	if (currentDepth == 0 || (state.status(2) == 0 || state.status(2) == 1 || state.status(2) == 2)){
    		return sbe(state);    		
    	}
    	/*Updating the depth.*/
    	currentDepth-=1;
    	/*Search for the minimax value associated with the best move for the MAX player.*/
    	int v = -2000;
    	for (int i = 0; i <= 5; i++){
    		if (state.isLegalMove(2, i) == true){
    			BoardState explored_moves;
    			explored_moves = state.applyMove(2, i);
	    		v = Math.max(v, minValue(explored_moves, maxDepth, currentDepth, alpha, beta));
	    		if (v >= beta){
	    			return v;
	    		}
	    		alpha = Math.max(alpha, v);
    		}
    	}
    	return v;
    }
    
    public int minValue(BoardState state, int maxDepth, int currentDepth, int alpha, int beta) {
    	/*Evaluating the score at the final move.*/
    	if (currentDepth <= 0 || (state.status(1) == 0 || state.status(1) == 1 || state.status(1) == 2)){
    		return sbe(state);
    	}
    	/*Updating the depth.*/
    	currentDepth-=1;
    	/*Search for the minimax value associated with the best move for the MIN player.*/
    	int v = 2000;
    	for (int i = 0; i <= 5; i++){
    		if (state.isLegalMove(1, i) == true){
    			BoardState explored_moves;
    			explored_moves = state.applyMove(1, i);
	    		v = Math.min(v, maxValue(explored_moves, maxDepth, currentDepth, alpha, beta));
	    		if (v <= alpha){
	    			return v;
	    		}
	    		beta = Math.min(beta, v);
    		}
    	}
    	return v;    	
    }
    
    public int sbe(BoardState state){
    	int score = 0;
    	/*Evaluating the score at the final move.*/
    	score = state.score[0] - state.score[1];
    	return score;
    }
}
package contestants;

import java.util.concurrent.TimeUnit;

import connectFour.Grid;
import connectFour.GridUtilities;
import connectFour.Player;

public class KulakovskyA_Epic implements Player
{
	private int myPlayerNumber;
	private int myMoveCount;
	private int[] directions = {Grid.RIGHT, Grid.UP, Grid.UPLEFT, Grid.UPRIGHT};
	
	public KulakovskyA_Epic()
	{
		myPlayerNumber = -1;
		myMoveCount = 0;
	}
	
	public String getPlayerName() 
	{
		return "*default dance noises*";
	}
	
	public int getMoveColumn(Grid g) 
	{
		if (myPlayerNumber == -1)
		{
			myPlayerNumber = g.getNextPlayer();
		}
		
		myMoveCount++;
		if (myMoveCount == 1)
		{
			return g.getCols()/2;
		}
		else if (myMoveCount == 2 && myPlayerNumber == Grid.PLAYER1)
		{
			return g.getCols()/2 + 1;
		} 
		
		int[] results = minimaxGetScore(g, 5, myPlayerNumber);
		myMoveCount++;
		return results[1];
	}

    // Returns an array of two integers: [0] is the score for this grid, and
    // [1] is the recommended column to move in for this grid. 
    private int[] minimaxGetScore(Grid g, int remainingDepth, int myPlayer)
    {
        // Did this move end the game?  If so, score it now based on whether we won.
        if (g.getWinningPlayer() == myPlayer)
        {
            // We won!
            return new int[] { 1000 * (remainingDepth + 1), -1 };
        }
        else if (g.getWinningPlayer() == (3 - myPlayer))
        {
            // They won
            return new int[] { -1000 * (remainingDepth + 1), -1 };
        }
        else if (g.getWinningPlayer() == -1)
        {
            // Game ends in a draw.
            return new int[] { 0, -1 };
        }

        int nextPlayer = g.getNextPlayer();

        // We don't want to go any deeper, so just return the immediate heuristic score
        // for this board
        if (remainingDepth <= 0)
        {
            // TODO: FOR YOU TO DO!  WRITE THIS getHeuristicScore METHOD
            // TO EXAMINE THE GRID AND COME UP WITH A NUMERIC SCORE FOR IT.
            // THE SCORE SHOULD BE FROM THE POINT OF VIEW OF YOUR PLAYER
            // (HIGH VALUES MEANS GOOD FOR YOU, LOW VALUES MEAN BAD FOR YOU).
            // THEN REPLACE '= 1' WITH '= getHeuristicScore(g)'
            int score = getHeuristicScore(g);

            return new int[] { score, -1 };
        }

        // Call self recursively for next player's moves' scores
        
        // Is this nextPlayer trying to minimize or maximize the score?  If it's us,
        // maximize.  If opponent, minimize.
        boolean isMax = (nextPlayer == myPlayer);
        int bestMove = -1;
        int bestScore;
        if (isMax)
        {
            bestScore = Integer.MIN_VALUE;
        }
        else
        {
            bestScore = Integer.MAX_VALUE;
        }        

        for (int nextCol = 0; nextCol < g.getCols(); nextCol++)
        {
            if (!g.isColumnFull(nextCol))
            {
                // Apply the move (temporarily) to the grid...
                g.makeMove(nextCol);
                
                // ... and then call ourselves recursively to move down the decision tree
                // and come up with a score                
                int scoreCur = minimaxGetScore(g, remainingDepth - 1, myPlayer)[0];
                
                // ... and we must remember to UNDO that move now that the call is done.
                g.undo();
                
                // Update bestScore with what the recursive call returned
                if (isMax)
                {
                    if (scoreCur > bestScore)
                    {
                        bestScore = scoreCur;
                        bestMove = nextCol;
                    }
                }
                else
                {
                    // minimizing!
                    if (scoreCur < bestScore)
                    {
                        bestScore = scoreCur;
                        bestMove = nextCol;
                    }
                }
            }
        }

        // Return the best score (and the recommended move)
        return new int[] { bestScore, bestMove };        
    }

	private int getHeuristicScore(Grid g)
	{
		GridUtilities gu = new GridUtilities(g);
		int score = 0;
		
		for(int i = 0; i < g.getRows(); i++)
		{
			for(int x = 0; x < g.getCols(); x++)
			{
				int m;
				if(g.getPlayerAt(i, x) == myPlayerNumber) m = 1;
				else m = -1;
				
				for(int direction : directions)
				{
					int[] n = gu.getLengthAndSpaces(i, x, direction);
					if(n[0]>=3)
					{
						
					}
					score += 10*n[0]*n[1]*m;
				}
			}
		}
		
		return score;
	}

}

package connectFour;

import contestants.KulakovskyA_Epic;
import contestants.SimplePlayer;
import graphics.GamePanel;
import sample.InteractivePlayer;

public class Runner
{
	// Note: You can change these to modify the size of the board
    private static final int ROWS = 6;
    private static final int COLS = 7;
    
    private static Board board;
    private static GamePanel gamePanel = new GamePanel(ROWS, COLS);

    public static void main(String[] args)
    {
        board = new Board(ROWS, COLS);
        gamePanel.drawGrid(board);


        /* ----------------------------------------------------------------------------------
         PLAY!
        
         For step 2 of the ConnectFour doc, you are required to play and HAVE FUN.
        
         Do the following:
         
         1) Run this file as is.  main will pit two InteractivePlayer
            instances against each other, meaning two humans can play against each other
            to get a feel for the game.  Play with your partner!
         
         2) Modify the playGame call below to replace one of the InteractivePlayer
            objects with one of the AI players instead.  Now play against the AI
            player and see how good it is.  These are your choices:
            
            bromanda.SampleRandomPlayer
            Yes, totally random.  I hope you can beat this one.
            
            watsona.OneAheadPlayer
            Only looks ahead one move, but still pretty smart.
            
            bromanda.AIPlayerAlphaBeta
            Looks ahead 9 moves with alpha-beta pruning and a basic heuristic function.
            (Another constructor allows you to change how many moves to look ahead.)
            
            watsona.LookaheadPlayer
            Extremely fast, but very smart AI that uses a very clever "statistical sampling" approach
            
            All of these Players have a constructor that takes zero parameters, so you can instantiate
            them directly.  Example:
            
                    playGame(new watsona.OneAheadPlayer(), new InteractivePlayer());
                    
         3) Modify the playGame call below to replace BOTH of the players with AI
            players above.  Pit different AI players against each other, or the same one
            against itself.  Observe the results.

           ---------------------------------------------------------------------------------- */
        
        playGame(new KulakovskyA_Epic(), new InteractivePlayer());

        // This example plays bromanda.AIPlayerAlphaBeta and lets you see the
        // hypothetical game plays it makes during look-ahead
        //playGame(new bromanda.AIPlayerAlphaBeta(8, true), new bromanda.AIPlayerAlphaBeta(8, true));
    }
    
    public static void playGame(Player player1, Player player2)
    {
        int winningPlayer = 0;
        while (winningPlayer == 0)
        {
            board.makeMove(player1.getMoveColumn(board));
            gamePanel.drawGrid(board);
            winningPlayer = board.getWinningPlayer();
            if (winningPlayer == 0)
            {
                board.makeMove(player2.getMoveColumn(board));
                gamePanel.drawGrid(board);
                winningPlayer = board.getWinningPlayer();
            }
        }

        if (winningPlayer == -1)
        {
            System.out.println("Game over. It was a draw.");
            return;
        }
        String winningPlayerName = 
                (winningPlayer == 1) ? player1.getPlayerName() : player2.getPlayerName();
        System.out.print("Game over.  Player " + winningPlayer + ": " + winningPlayerName + " won.");
    }

    public static GamePanel getGamePanel()
    {
        return gamePanel;
    }
}
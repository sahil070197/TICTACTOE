
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
class ScoreBoard{
    int games;
    int player1Score;
    int player2Score;
    int computerScore;
    int draws;
    int bestWin;
    ScoreBoard()
    {
        
        games=0;
        player1Score=0;
        player2Score=0;
        computerScore=0;
        draws=0;
        bestWin=0;
    }
    void setBestWin(int thisWin)
    {
        bestWin=Math.max(bestWin, thisWin);
    }
    void newGame()
    {
        games++;
    }
    void draw()
    {
        draws++;
    }
    void player1Win(int symbols)
    {
        player1Score++;
        setBestWin(symbols);
    }
    void player2Win(int symbols)
    {
        player2Score++;
        setBestWin(symbols);
    }
    void computerWin(int symbols)
    {
        computerScore++;
        setBestWin(symbols);
    }
}
class Move{
    int row;
    int column;
    Move(int row, int column)
    {
        this.row=row;
        this.column=column;
    }
}
public class TICTACTOE{
  /** symbol for X */
  public static final Character ex = 'X';

  /** symbol for O */
  public static final Character oh = 'O';

  /** symbol for empty grid element */
  public static final Character empty = ' ';

  /** board is the grid that the game is played on; 
    * each element must be one of ex, oh or empty*/
  public static Character[][] board;


  /** rows is the number of rows (N) in the game board */
  public static int rows;

  /** columns is the number of columns (M) in the game board */
  public static int columns;

  /** win_condition is the value of K, the winning condition of the game */
  public static int win_condition; 

  /** specifies if the game is 1p (human-computer) or 2p (human-human) */
  public static boolean human_human_game;


  public static ScoreBoard score_board;
  /** Checks for a win based on the last symbol played in the game
   * 
   * It is assumed that the position specified by the last_row_played
   * and last_column_played is valid and that the symbol in the board
   * at that position is not empty. (It must be <em>ex</em> or <em>oh</em>)
   * 
   * @param m
   * @param c
   * @return 
   * 
   */
  public static int win(Move m, Character c){
    
      int count=0;
      count=Math.max(0,getConsecutiveinCurrentRow(m, c));
      count=Math.max(count, getConsecutiveinCurrentColumn(m, c));
      count=Math.max(count, getConsecutiveinDiagonal1(m, c));
      count=Math.max(count, getConsecutiveinDiagonal2(m, c));
      /*
        Include current position
      */
      count++;
      if(count>=win_condition)
      {
          return count;
      }
    return -1;
  }

  /** main method to run the game 
    * 
    * @paramargs optional command line arguments to 
    * specify if the game is 1p (human-computer) or
    * 2p (human-human). Expect the string "2p" if any
    * command line argument is given.
    */
  public static void main(String[] args){
   /*------------------------------------------
     * handle command line arguments if any     
     * to determine if the game is human-human  
     * or human-computer                        
     *------------------------------------------*/
if( args.length> 0){
      /* there are commend line arguments present */
    if(args[0].compareTo("2p")==0)
    human_human_game=true;
    else
    {
        System.exit(0);
    }
      // add your code here

}else{
      /* there are no command line argument present */

        human_human_game=false;
      // add your code here

    }

    /*------------------------------------
     * read N-M-K data from init file   
     * N = rows                         
     * M = columns                      
     * K = win_condition
     *------------------------------------*/

    /*-------------------------------------
     *---------------------------------^----
     * BEGIN : Do NOT change the code here
     *-------------------------------------
    */
BufferedReader file_input;
FileReader     file;
    String         file_name = "init.txt"; 
    String         line;
    
try{
      file = new FileReader(file_name);
      file_input = new BufferedReader(file);

      line = file_input.readLine();
      rows = Integer.parseInt(line);

      line = file_input.readLine();
      columns = Integer.parseInt(line);

      line = file_input.readLine();
      win_condition = Integer.parseInt(line);

      /* always close your files you are done with them! */
file_input.close();

}catch(Exception e){
      /* somethine went wrong! */
System.err.println("Failure to read data from init file properly");
System.err.println(e);
System.err.println("Program ending");
      return;
    }


    /*-------------------------------------
     * END : Do NOT change the code here
     *------------------------------------- 
     *-------------------------------------*/


    /* create and initialize the game board */

    /* allocate memory for the board array */
    board = new Character[rows][columns];
    score_board=new ScoreBoard();
    
    Scanner in=new Scanner(System.in);
    while(true)
    {
        
        initBoard();
        score_board.newGame();
        
        playGame();
        if(human_human_game)
        {
            displayStatusHumanHuman();
        }
        else
        {
            displayStatusHumanComputer();
        }
        System.out.println("Would you like to play again? ");
        String choice=in.nextLine();
        if(choice.compareToIgnoreCase("no") == 0)
        {
            System.exit(0);
        }
        else if(choice.compareToIgnoreCase("yes") != 0)
        {
            System.exit(0);
        }
    }
    /* code to drive the game */
  }
  public static void playGame()
  {
      if(human_human_game)
      {
          String name1="player1";
          String name2="player2";
          
          boolean wonP1, wonP2;
          while(true)
          {
              wonP1=playerMove(name1);
              if(wonP1 || isGameDraw())
              {
                  break;
              }
              
              wonP2=playerMove(name2);

              if(wonP2 || isGameDraw())
              {
                  break;
              }
          }
      }
      else
      {
          String name1="player1";
          boolean wonP, wonC;
          while(true)
          {
              wonP=playerMove(name1);
              if( wonP  || isGameDraw())
              {
                  break;
              }
              wonC=computerMove();
              if(wonC || isGameDraw())
              {
                  break;
              }
          }
      }
  }

    public static void initBoard() {
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++)
            {
                board[i][j]=null;
            }
        }
    }

    public static boolean isGameDraw() {
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++)
            {
                if(board[i][j]==null)
                {
                    return false;
                }
            }
        }
        score_board.draw();
        return true;
    }
    public static Move getValidPlayerMove(String name)
    {
        Scanner in=new Scanner(System.in);
        System.out.println(name+" input: ");
        String playerInput=in.nextLine();
        
        String ar[]=playerInput.split(" ");
        
        if(ar.length==4 && ar[0].compareTo("r")==0 && ar[2].compareTo("c")==0)
        {
            try
            {
                int r=Integer.parseInt(ar[1]);
                int c=Integer.parseInt(ar[3]);
                if(r<rows && r>=0 && c<columns && c>=0 && board[r][c]==null)
                {
                    return new Move(r, c);
                }
            }
            catch(Exception e)
            {
                
            }
        }
        System.out.println("That move is not allowed. Try again.");
        return getValidPlayerMove(name);
    }
    public static Move getValidComputerMove()
    {
        Move m;
        while(true)
        {
            int x=(int) (Math.random() * (rows));
            int y=(int) (Math.random() * (columns));
            if(board[x][y]==null)
            {
                m=new Move(x,y);
                break;
            }
        }
        int count=0;
        count=Math.max(0,getConsecutiveinCurrentRow(m, oh));
        count=Math.max(count, getConsecutiveinCurrentColumn(m, oh));
        count=Math.max(count, getConsecutiveinDiagonal1(m, oh));
        count=Math.max(count, getConsecutiveinDiagonal2(m, oh));
        Move m2=new Move(0,0);
        
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++)
            {
                if(board[i][j]==null)
                {
                    m2.row=i;
                    m2.column=j;
                    int count2=Math.max(0,getConsecutiveinCurrentRow(m2, oh));
                    count2=Math.max(count2, getConsecutiveinCurrentColumn(m2, oh));
                    count2=Math.max(count2, getConsecutiveinDiagonal1(m2, oh));
                    count2=Math.max(count2, getConsecutiveinDiagonal2(m2, oh));
                    count2=Math.max(count2,getConsecutiveinCurrentRow(m2, ex));
                    count2=Math.max(count2, getConsecutiveinCurrentColumn(m2, ex));
                    count2=Math.max(count2, getConsecutiveinDiagonal1(m2, ex));
                    count2=Math.max(count2, getConsecutiveinDiagonal2(m2, ex));
                    if(count2>count)
                    {
                        count=count2;
                        m=new Move(m2.row, m2.column);
                    }
                }
            }
        }
        return m;
    }
    public static int getConsecutiveinCurrentRow(Move m, Character symbol)
    {
        int c=m.column;
        int count=0;
        for(int i=c-1;i>=0;i--)
        {
            if(board[m.row][i]!=null && board[m.row][i]==symbol)
            {
                count++;
            }
            else
            {
                break;
            }
        }
        
        for(int i=c+1;i<columns;i++)
        {
            if(board[m.row][i]!=null && board[m.row][i]==symbol)
            {
                count++;
            }
            else
            {
                break;
            }
        }
        return count;
    }
    public static int getConsecutiveinCurrentColumn(Move m, Character symbol)
    {
        int r=m.row;
        int count=0;
        for(int i=r-1;i>=0;i--)
        {
            if(board[i][m.column]!=null && board[i][m.column]==symbol)
            {
                count++;
            }
            else
            {
                break;
            }
        }
        
        for(int i=r+1;i<rows;i++)
        {
            if(board[i][m.column]!=null && board[i][m.column]==symbol)
            {
                count++;
            }
            else
            {
                break;
            }
        }
        return count;
    }
    public static int getConsecutiveinDiagonal1(Move m, Character symbol)
    {
        int r=m.row;
        int c=m.column;
        int count=0;
        for(int i=r-1, j=c-1;j>=0 && i>=0;j--,i--)
        {
            if(board[i][j]!=null && board[i][j]==symbol)
            {
                count++;
            }
            else
            {
                break;
            }
        }
        
        for(int i=r+1, j=c+1;j<columns && i<rows;j++,i++)
        {
            if(board[i][j]!=null && board[i][j]==symbol)
            {
                count++;
            }
            else
            {
                break;
            }
        }
        return count;
    }
    public static int getConsecutiveinDiagonal2(Move m, Character symbol)
    {
        int r=m.row;
        int c=m.column;
        int count=0;
        for(int i=r-1, j=c+1;j<columns && i>=0;j++,i--)
        {
            if(board[i][j]!=null && board[i][j]==symbol)
            {
                count++;
            }
            else
            {
                break;
            }
        }
        
        for(int i=r+1, j=c-1;j>=0 && i<rows;j--,i++)
        {
            if(board[i][j]!=null && board[i][j]==symbol)
            {
                count++;
            }
            else
            {
                break;
            }
        }
        return count;
    }

    public static boolean playerMove(String name) {
        if(name.compareTo("player1")==0)
        {
            Move move=getValidPlayerMove(name);
            board[move.row][move.column]=ex;
            int result=win(move ,ex);
            printBoard();
            if(result<0)
            {
                return false;
            }
            else
            {
                System.out.println("Player 1 wins with "+result+" symbols");
                score_board.player1Win(result);
                return true;
            }
        }
        else 
        {
            Move move=getValidPlayerMove(name);
            board[move.row][move.column]=oh;
            int result=win(move, oh);
            printBoard();
            if(result<0)
            {
                return false;
            }
            else
            {
                System.out.println("Player 2 wins with "+result+" symbols");
                score_board.player2Win(result);
                return true;
            }
        }
    }

    public static boolean computerMove() {
        Move move=getValidComputerMove();
        System.out.println("Computer plays: r "+move.row+" c "+move.column);
        board[move.row][move.column]=oh;
        int result=win(move ,oh);
        printBoard();;
        if(result<0)
        {
            return false;
        }
        else
        {
            System.out.println("Computer wins with "+result+" symbols");
            score_board.computerWin(result);
            return true;
        }
    }
    public static void printBoard()
    {
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++)
            {
                if(board[i][j]==null)
                {
                    if(j<columns-1)
                    System.out.print("   |");
                    else
                    {
                       System.out.print("   \n"); 
                    }
                }
                else
                {
                    if(j<columns-1)
                    System.out.print(" "+board[i][j]+" |");
                    else
                    System.out.print(" "+board[i][j]+" \n");
                }
            }
            if(i<rows-1)
            for(int j=0;j<columns;j++)
            {
                if(j<columns-1)
                System.out.print("---+");
                else
                System.out.println("---");
            }
        }
    }

    public static void displayStatusHumanComputer() {
        System.out.println("Thanks for playing tic-tac-toe++\n" +
        "\ntotal games   :"+score_board.games  +
        "\nplayer 1 wins :"+score_board.player1Score+
        "\ncomputer wins :"+score_board.computerScore  +
        "\ndraws         :"+score_board.draws +
        "\nbest win      :"+score_board.bestWin);
    }
    public static void displayStatusHumanHuman() {
        System.out.println("Thanks for playing tic-tac-toe++\n" +
        "\ntotal games   :"+score_board.games  +
        "\nplayer 1 wins :"+score_board.player1Score+
        "\nplayer 2 wins :"+score_board.player2Score  +
        "\ndraws         :"+score_board.draws +
        "\nbest win      :"+score_board.bestWin);
    }
    
}
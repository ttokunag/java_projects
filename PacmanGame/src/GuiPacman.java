import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.io.*;
import java.lang.*;

/**
 * @author      Tomoya Tokunaga <ttokunag@ucsd.edu>
 */

public class GuiPacman extends Application
{
  private String outputBoard; // The filename for where to save the Board
  private Board board; // The Game Board
  private GridPane gridPane;
  private GridPane titleScore;
  private GridPane pane = new GridPane();
  private Text titleText;
  private Text gameOver;
  private Scene scene;
  private StackPane stack;
  private double imageSize;
  private int[] currentPacman = new int[2];
  private int[] previousPacman = new int[2];
  private int[][] currentGhosts = new int[4][2];
  private int[][] previousGhosts = new int[4][2];


  // Fill colors to choose
  private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);
  private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242);
  private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);


  /*
   * Name:      start
   * Purpose:   Start and keep the game running.
   * Parameter: Stage
   * Return:    void
   */
  @Override
  public void start(Stage primaryStage)
  {
    // Process Arguments and Initialize the Game Board
    processArgs(getParameters().getRaw().toArray(new String[0]));

    imageSize = ((660 - 5*(board.getGrid().length + 1)) / board.getGrid().length);
    currentGhosts = board.getGhosts();
    currentPacman = board.getPacman();
    titleText = new Text("Pac-Man");
    titleText.setFill(Color.WHITE);
    titleText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));

    StackPane stack = new StackPane();
    stack.setStyle("-fx-background-color: #000000");

    pane.setAlignment(Pos.CENTER);

    VBox root = new VBox();
    gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(5);
    gridPane.setVgap(5);

    titleScore = new GridPane();
    titleScore.setHgap(80);
    titleScore.setPadding(new Insets(0,0,0,40));
    root.setPadding(new Insets(10));
    root.setSpacing(10);
    root.getChildren().addAll(titleScore, gridPane);

    stack.getChildren().addAll(root, pane);

    titleScore.getChildren().clear();
    Text scoreText = new Text("Score: " + board.getScore());
    scoreText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
    scoreText.setFill(Color.WHITE);
    titleScore.add(titleText,0,0);
    titleScore.add(scoreText,1,0);
    for (int i = 0; i < board.getGrid().length; i++) {
      for (int j = 0; j < board.getGrid().length; j++) {
        Tile tile = new Tile(board.getGrid()[i][j]);
        gridPane.add(tile.getNode(),j,i);
      }
    }

    //reflesher(Direction.RIGHT);

    scene = new Scene(stack, 660, 730);
    scene.setOnKeyPressed(new myKeyHandler());
    primaryStage.setTitle("GuiPacman");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  public void reflesher(Direction direction) {
      previousGhosts = currentGhosts;
      currentGhosts = board.getGhosts();
      previousPacman = currentPacman;
      currentPacman = board.getPacman();
      Image blackImage = new Image("/image/black.png");
      ImageView blackIV = new ImageView(blackImage);
      blackIV.setFitHeight(imageSize);
      blackIV.setFitWidth(imageSize);
      gridPane.add(blackIV, previousPacman[1], previousPacman[0]);
      Tile ti = new Tile(board.getGrid()[previousPacman[0]][previousPacman[1]]);
      gridPane.add(ti.getNode(),previousPacman[1],previousPacman[0]);

      ImageView blackIV_ = new ImageView(blackImage);
      blackIV_.setFitHeight(imageSize);
      blackIV_.setFitWidth(imageSize);
      gridPane.add(blackIV_, currentPacman[1], currentPacman[0]);
      Tile ti_ = new Tile(board.getGrid()[currentPacman[0]][currentPacman[1]]);

      if (direction.equals(Direction.UP)) {
        ti_.getNode().setRotate(ti_.getNode().getRotate() + 270);
      } else if (direction.equals(Direction.DOWN)) {
        ti_.getNode().setRotate(ti_.getNode().getRotate() + 90);
      } else if (direction.equals(Direction.LEFT)) {
        ti_.getNode().setRotate(ti_.getNode().getRotate() + 180);
      } else {}

      gridPane.add(ti_.getNode(),currentPacman[1],currentPacman[0]);

      for (int i = 0; i < 4; i++) {
          ImageView blackImageView = new ImageView(blackImage);
          blackImageView.setFitHeight(imageSize);
          blackImageView.setFitWidth(imageSize);
          gridPane.add(blackImageView, previousGhosts[i][1], previousGhosts[i][0]);
          Tile t = new Tile(board.getGrid()[previousGhosts[i][0]][previousGhosts[i][1]]);
          gridPane.add(t.getNode(),previousGhosts[i][1],previousGhosts[i][0]);

          ImageView blackImageView_ = new ImageView(blackImage);
          blackImageView_.setFitHeight(imageSize);
          blackImageView_.setFitWidth(imageSize);
          gridPane.add(blackImageView_, currentGhosts[i][1], currentGhosts[i][0]);
          Tile t_ = new Tile(board.getGrid()[currentGhosts[i][0]][currentGhosts[i][1]]);
          gridPane.add(t_.getNode(),currentGhosts[i][1],currentGhosts[i][0]);
      }


      // the following is for title & score
      titleScore.getChildren().clear();
      Text scoreText = new Text("Score: " + board.getScore());
      scoreText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
      scoreText.setFill(Color.WHITE);
      titleScore.add(titleText,0,0);
      titleScore.add(scoreText,1,0);

      if (board.isGameOver()) {
        return;
      }

  }

  /** Add your own Instance Methods Here */

  /*
   * Name:       myKeyHandler
   *
   * Purpose:
   *
   *
   */
  private class myKeyHandler implements EventHandler<KeyEvent> {

   /*
    * Name:      handle
    * Purpose:   handle the KeyEvent of user's input.
    * Parameter:
    * Return:
    */
    @Override
    public void handle(KeyEvent e) {
      if (!board.isGameOver()) {
        // when UP arrow is pressed
        if (e.getCode() == KeyCode.UP) {
          if (board.canMove(Direction.UP)) {
            board.move(Direction.UP);
            reflesher(Direction.UP);
            System.out.println("Moving UP");
          } else {
            board.move(Direction.STAY);
            reflesher(Direction.STAY);
            System.out.println("Cannot Move in this direction");
          }
          if (board.isGameOver()) {
            gameIsOver();
          }
        // when DOWN arrow is pressed
        } else if (e.getCode() == KeyCode.DOWN) {
          if (board.canMove(Direction.DOWN)) {
            board.move(Direction.DOWN);
            reflesher(Direction.DOWN);
            System.out.println("Moving DOWN");
          } else {
            board.move(Direction.STAY);
            reflesher(Direction.STAY);
            System.out.println("Cannot Move in this direction");
          }
          if (board.isGameOver()) {
            gameIsOver();
          }
        // when LEFT arrow is pressed
        } else if (e.getCode() == KeyCode.LEFT) {
          if (board.canMove(Direction.LEFT)) {
            board.move(Direction.LEFT);
            reflesher(Direction.LEFT);
            System.out.println("Moving LEFT");
          } else {
            board.move(Direction.STAY);
            reflesher(Direction.STAY);
            System.out.println("Cannot Move in this direction");
          }
          if (board.isGameOver()) {
            gameIsOver();
          }
        // when RIGHT arrow is pressed
        } else if (e.getCode() == KeyCode.RIGHT) {
          if (board.canMove(Direction.RIGHT)) {
            board.move(Direction.RIGHT);
            reflesher(Direction.RIGHT);
            System.out.println("Moving RIGHT");
          } else {
            board.move(Direction.STAY);
            reflesher(Direction.STAY);
            System.out.println("Cannot Move in this direction");
          }
          if (board.isGameOver()) {
            gameIsOver();
          }
        // when 's' key is pressed
        } else if (e.getCode() == KeyCode.S) {
            try {
              board.saveBoard(outputBoard);
              System.out.println("Saving Board to " + outputBoard);
            } catch(IOException ioe) {
              System.out.println("saveBoard threw an Exception");
            }
        } else {}
      }
      else {
        // nullify any inputs if a game is over
        scene.setOnKeyPressed(null);
      }
    }

    /*
     * Name:      gameIsOver
     * Purpose:   Check if the game is over and show the gameover board.
     * Parameter:
     * Return:
     */
    private void gameIsOver() {
      gameOver = new Text("Game Over!");
      gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 60));
      gameOver.setFill(COLOR_VALUE_DARK);
      pane.setStyle("-fx-background-color: rgba(238, 228, 218, 0.73)");
      pane.add(gameOver, 0, 0);
    }
  } // End of Inner Class myKeyHandler.



  /*
   * Name:        Tile
   *
   * Purpose:     This class tile helps to make the tiles in the board
   *              presented using JavaFX. Whenever a tile is needed,
   *              the constructor taking one char parameter is called
   *              and create certain ImageView fit to the char representation
   *              of the tile.
   *
   *
   */
  private class Tile {

    private ImageView repr;   // This field is for the Rectangle of tile.

    /*
     * Constructor
     *
     * Purpose:   display corresponding images to text representations
     * Parameter: char, a text representation
     *
     */
    public Tile(char tileAppearance) {
      Image eatenImage = new Image("/image/dot_eaten.png");
      Image uneatenImage = new Image("/image/dot_uneaten.png");
      Image redImage = new Image("/image/blinky_left.png");
      Image pinkImage = new Image("/image/pinky_left.png");
      Image blueImage = new Image("/image/inky_down.png");
      Image orangeImage = new Image("/image/clyde_up.png");
      Image pacmanImage = new Image("/image/pacman_right.png");
      Image gameoverImage = new Image("/image/pacman_dead.png");
      Image cherryImage = new Image("/image/cherry.png");

      switch (tileAppearance) {
        // for a pacman image
        case 'P':
          repr = new ImageView(pacmanImage);
          repr.setFitHeight(imageSize);
          repr.setFitWidth(imageSize); break;
        // for a red ghost image
        case 'R':
          repr = new ImageView(redImage);
          repr.setFitHeight(imageSize);
          repr.setFitWidth(imageSize); break;
        // for an orange ghost image
        case 'O':
          repr = new ImageView(orangeImage);
          repr.setFitHeight(imageSize);
          repr.setFitWidth(imageSize); break;
        // for a blue ghost image
        case 'B':
          repr = new ImageView(blueImage);
          repr.setFitHeight(imageSize);
          repr.setFitWidth(imageSize); break;
        // for a pink ghost image
        case 'p':
          repr = new ImageView(pinkImage);
          repr.setFitHeight(imageSize);
          repr.setFitWidth(imageSize); break;
        // for a dead pacman image
        case 'X':
          repr = new ImageView(gameoverImage);
          repr.setFitHeight(imageSize);
          repr.setFitWidth(imageSize); break;
        // for an uneaten image
        case '*':
          repr = new ImageView(uneatenImage);
          repr.setFitHeight(imageSize);
          repr.setFitWidth(imageSize); break;
        // for an eaten image
        case ' ':
          repr = new ImageView(eatenImage);
          repr.setFitHeight(imageSize);
          repr.setFitWidth(imageSize); break;
        // for a cherry image
        case 'C':
          repr = new ImageView(cherryImage);
          repr.setFitHeight(imageSize);
          repr.setFitWidth(imageSize); break;
        default:
          repr = new ImageView(redImage);
          repr.setFitHeight(imageSize);
          repr.setFitWidth(imageSize); break;
      }
    }


    public ImageView getNode() {
      return repr;
    }

  }  // End of Inner class Tile




  /** DO NOT EDIT BELOW */

  // The method used to process the command line arguments
  private void processArgs(String[] args)
  {
    String inputBoard = null;   // The filename for where to load the Board
    int boardSize = 0;          // The Size of the Board

    // Arguments must come in pairs
    if((args.length % 2) != 0)
    {
      printUsage();
      System.exit(-1);
    }

    // Process all the arguments
    for(int i = 0; i < args.length; i += 2)
    {
      if(args[i].equals("-i"))
      {   // We are processing the argument that specifies
        // the input file to be used to set the board
        inputBoard = args[i + 1];
      }
      else if(args[i].equals("-o"))
      {   // We are processing the argument that specifies
        // the output file to be used to save the board
        outputBoard = args[i + 1];
      }
      else if(args[i].equals("-s"))
      {   // We are processing the argument that specifies
        // the size of the Board
        boardSize = Integer.parseInt(args[i + 1]);
      }
      else
      {   // Incorrect Argument
        printUsage();
        System.exit(-1);
      }
    }

    // Set the default output file if none specified
    if(outputBoard == null)
      outputBoard = "Pac-Man.board";
    // Set the default Board size if none specified or less than 2
    if(boardSize < 3)
      boardSize = 13;

    // Initialize the Game Board
    try{
      if(inputBoard != null)
        board = new Board(inputBoard);
      else
        board = new Board(boardSize);
    }
    catch (Exception e)
    {
      System.out.println(e.getClass().getName() + " was thrown while creating a " +
          "Board from file " + inputBoard);
      System.out.println("Either your Board(String, Random) " +
          "Constructor is broken or the file isn't " +
          "formated correctly");
      System.exit(-1);
    }
  }

  // Print the Usage Message
  private static void printUsage()
  {
    System.out.println("GuiPacman");
    System.out.println("Usage:  GuiPacman [-i|o file ...]");
    System.out.println();
    System.out.println("  Command line arguments come in pairs of the form: <command> <argument>");
    System.out.println();
    System.out.println("  -i [file]  -> Specifies a Pacman board that should be loaded");
    System.out.println();
    System.out.println("  -o [file]  -> Specifies a file that should be used to save the Pac-Man board");
    System.out.println("                If none specified then the default \"Pac-Man.board\" file will be used");
    System.out.println("  -s [size]  -> Specifies the size of the Pac-Man board if an input file hasn't been");
    System.out.println("                specified.  If both -s and -i are used, then the size of the board");
    System.out.println("                will be determined by the input file. The default size is 10.");
  }
}
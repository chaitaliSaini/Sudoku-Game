/* SudokuGame.java
Purpose:sudoku game solver

@author Chaitali Saini
@version 1.1 18/5/16
*/
package application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class SudokuGame extends Application {

Stage window; //declare game window
static int row,col; //store coordinates of player's last modified cell

static int[][] arr=new int[9][9]; //stores copy of the sudoku puzzle
static int[][] brr=new int[9][9]; //stores copy of the sudoku puzzle

public static void main(String[] args){

    int[][] puzzle={{0,0,0,0,0,0,1,0,0}  //initializing the puzzle
            		,{0,0,8,7,6,4,5,3,9}
            		,{0,9,3,0,0,0,0,6,0}
            		,{9,8,0,5,0,0,0,0,7}
            		,{0,2,7,0,8,0,9,5,0}
            		,{4,0,0,0,0,6,0,2,8}
            		,{0,4,0,0,0,0,6,9,0}
            		,{8,3,9,6,5,2,7,0,0}
            		,{0,0,5,0,0,0,0,0,0}};

             for(int i=0;i<9;i++){   //storing the original puzzle in arr and brr
            	 for(int j=0;j<9;j++){
            		 arr[i][j]=puzzle[i][j];
            		 brr[i][j]=puzzle[i][j];
            	 }
             }
        launch(args); //calls the method start(Stage)
}





/* Checks whether the puzzle is correctly solved

@param {int array} sudoku - puzzle
@return {boolean}

Description : takes a puzzle(2D array) as input and returns True if it has been correctly solved; False otherwise
*/
static boolean IsSolved(int sudoku[][]){

    //check if all rows satisfy the rules of sudoku
    for(int i=0;i<9;i++){
        int[] freq=new int[10];
        for(int j=0;j<9;j++){
            if(sudoku[i][j]==0){
                return false;}
            else{
                freq[sudoku[i][j]]++;
                if(freq[sudoku[i][j]]>1){
                    return false;}
                if(j==8){
                    for(int k=1;k>10;k++){
                        if(freq[k]!=1){
                            return false;}
                        }
                     }
                }
             }
         }

    //check if all columns satisfy the rules of sudoku
    for(int j=0;j<9;j++){
        int[] freq=new int[10];
        for(int i=0;i<9;i++){
            if(sudoku[i][j]==0){
                return false;}
            else{
                freq[sudoku[i][j]]++;
                if(freq[sudoku[i][j]]>1){
                    return false;}
                if(i==8){
                         for(int k=1;k>10;k++){
                             if(freq[k]!=1){
                                 return false;
                             }
                         }
                     }
                }
             }
         }

    //check if all boxes satisfy the rules of sudoku
         for(int a=0;a<9;a=a+3){
             for(int b=0;b<9;b=b+3){
                 int[] freq=new int[10];
             for(int i=a;i<a+3;i++){
                 for(int j=b;j<b+3;j++){
                     freq[sudoku[i][j]]++;
                 }
             }
             for(int k=1;k<10;k++){
                 if(freq[k]!=1){
                     return false;
                 }
             }
             }
         }

         return true;
}



/*
@param {int array} sudoku - puzzle
@param {int} r - cell row
@param {int} c - cell column
@param {int array} arr - stores all possible values of sudoku[r][c]
@return {int} j - number of possible values that the cell sudoku[r][c] can take

Description: Given any cell,it finds out what possible value(s) that cell can satisfying the rules of the game
*/
static int findPossibleValues(int sudoku[][],int r,int c,int arr[]){

    int[] b=new int[10];
    for(int i=0;i<9;i++){
        b[sudoku[r][i]]=1;}

    for(int i=0;i<9;i++){
        b[sudoku[i][c]]=1;}

    int startr=(r/3)*3;
    int startc=(c/3)*3;
    for(int i=startr;i<startr+3;i++){
        for(int j=startc;j<startc+3;j++){
                 b[sudoku[i][j]]=1;}
         }
    int j=0;
    for(int i=1;i<10;i++){
        if(b[i]!=1){
            arr[j]=i;
            j++;}
         }
         return j;
}




/*
@param {int array} sudoku - puzzle
@return {boolean}

Description: Given a sudoku puzzle,it solves the puzzle and returns True if it is solvable and False otherwise
*/
static boolean SudokuSolve(int sudoku[][]){
    int[] arr=new int[9];
    if(IsSolved(sudoku)){
             return true;}
    else{
        int i=0,j=0,p=0;
        for( i=0;i<9;i++){
            for(j=0;j<9;j++){
                if(sudoku[i][j]==0){
                         p=1;
                         break;
                     }
                 }
            if(p==1){
                break;}
             }
        if(i!=9&&j!=9){
        int n= findPossibleValues(sudoku,i,j,arr);
        if(n==0){
            return false;
        }
       for(int k=0;k<n;k++){
           sudoku[i][j]=arr[k];
          if( SudokuSolve(sudoku)){
              return true;
          }
       }
       sudoku[i][j]=0;
         }
    }

    return false;
}


/*
@param {Stage} primaryStage -

Description -

*/
@Override
public void start(Stage primaryStage) throws Exception{

    window=primaryStage;
    TextField score=new TextField("0");  //create a non editable text field to display the score
    GridPane.setConstraints(score, 8, 0 );
    score.setEditable(false);
    TextField moves=new TextField("0");
    GridPane.setConstraints(moves, 8,0);
    moves.setEditable(false);
    TextField cmoves=new TextField("0");
    GridPane.setConstraints(cmoves, 8, 0);
    moves.setEditable(false);
    window.setTitle("SUDOKU");

    GridPane grid= new GridPane(); // grid has 81 text fields and represents the sudoku puzzle

    grid.setPadding(new Insets(10,20,10,30));
    Button rules=new Button("Rules");
    rules.setMaxHeight(30);
    rules.setMinHeight(30);
    rules.setMaxWidth(70);
    rules.setMaxWidth(70);
    GridPane.setConstraints(rules,4,0);
    rules.setOnAction(e->{
        Stage hintWindow=new Stage();
        hintWindow.setTitle("RULES OF SUDOKU");
        Label label=new Label();
        label.setText("1.All the rows,columns and sub-squares can only have numbers from 1-9\n"
                + "Any other input would not be taken into account\n2.All the rows,columns and "
                + "sub-squares can have each digit from 1-9 only once\n3.You can only have only"
                + " one red square at a time");
        label.setAlignment(Pos.CENTER);
        Scene rulescene=new Scene(label,500,100);
        hintWindow.setScene(rulescene);
        hintWindow.show();
    });
    Label nameLabel=new Label("Accuracy:");
    GridPane.setConstraints(nameLabel, 6, 0);

    for(int i=0;i<9;i++){
        for(int j=0;j<9;j++){
    if(IsSolved(arr)){

    //if the puzzle has been solved,a new window is created and a 'you have solved the puzzzle' message is printed with the accuracy
    Stage window2=new Stage();
    Label label=new Label("Congratulations!!!!"
            + " You have solved the puzzle\n"+"Your accuracy is : "+score.getText()+"%");
    StackPane layout=new StackPane();
    layout.getChildren().add(label);
    Scene newscene=new Scene(layout,300,300);
    window2.setScene(newscene);
    window2.show();
    }
    if(arr[i][j]!=0){
        String a=Integer.toString(arr[i][j]);
        TextField passInput=new TextField(a);
        passInput.resize(30, 30);
        passInput.setPadding(new Insets(10,10,20,15));
        if((i==3||i==6)&&(j==3||j==6)){
            GridPane.setMargin(passInput, new Insets(10, 0, 0,10));
            }
        else{
            if(i==3||i==6){
            GridPane.setMargin(passInput, new Insets(10, 0, 0,0));
                }
            if(j==3||j==6){
            GridPane.setMargin(passInput, new Insets(0, 0, 0,10));
                }
            }

            GridPane.setConstraints(passInput, j, i+1 );
            passInput.setEditable(false);

            grid.getChildren().add(passInput);
            }
    else{
        TextField passInput=new TextField();
        passInput.resize(30, 30);
        passInput.setPadding(new Insets(10,10,20,15));

        if((i==3||i==6)&&(j==3||j==6)){
            GridPane.setMargin(passInput, new Insets(10, 0, 0,10));
            }
        else{
            if(i==3||i==6){
            GridPane.setMargin(passInput, new Insets(10, 0, 0,0));
            }
            if(j==3||j==6){
            GridPane.setMargin(passInput, new Insets(0, 0, 0,10));
            }
            }

            GridPane.setConstraints(passInput, j, i+1 );
            grid.getChildren().add(passInput);
            int a=i,b=j;

            passInput.setOnAction(e->{
                try{
                    arr[a][b]=Integer.parseInt(passInput.getText());
                    brr[a][b]=arr[a][b];
                    if(arr[a][b]==0){
                    	passInput.setStyle("-fx-background-color: #AB4642");
                    }
                    else  if(SudokuSolve(brr)){
                    passInput.setStyle("-fx-background-color: #A1B56C");
                String c=cmoves.getText();
                String m=moves.getText();
                int correct=Integer.parseInt(c);

                int totalMoves=Integer.parseInt(m);
                correct++;
                totalMoves++;
                final String cm=Integer.toString(correct);
                final String mm=Integer.toString(totalMoves);
                moves.setText(mm);
                cmoves.setText(cm);

                float scoreAcc=(float)((correct*100)/totalMoves);
                    final String p=Float.toString(scoreAcc);

                    score.setText(p);
        }

        else{
            passInput.setStyle("-fx-background-color: #AB4642");
            String c=cmoves.getText();
            String m=moves.getText();
            int correct=Integer.parseInt(c);

            int totalMoves=Integer.parseInt(m);

            totalMoves++;

            final String mm=Integer.toString(totalMoves);
            moves.setText(mm);
            float scoreAcc=(float)((correct*100)/totalMoves);
                final String p=Float.toString(scoreAcc);

                score.setText(p);

                }
                    if(IsSolved(arr)){
                        Stage window2=new Stage();
                        Label label=new Label("Congratulations!!!!"
                                + " You have solved the puzzle\n"+"Your accuracy is : "+score.getText()+"%");
                        StackPane layout=new StackPane();
                        layout.getChildren().add(label);
                        Scene newscene=new Scene(layout,300,300);
                        window2.setScene(newscene);
                        window2.show();
                    }
                }catch(NumberFormatException t ){

                }
            });
        }
        }
    }


    grid.getChildren().addAll(nameLabel,score,rules);


    Scene scene=new Scene(grid,500,500);

    window.setScene(scene);
    window.show();
}
}

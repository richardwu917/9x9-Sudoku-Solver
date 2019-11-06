import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;
import javafx.animation.FadeTransition;
import javafx.scene.input.KeyCode;

public class SudokuBoard extends Application {
	private TextField[][] sudokuUI = new TextField[9][9]; //2D Array to display to the UI
	private Sudoku sudoku = new Sudoku();
	public boolean isDigit(String text) {
		if(text.length() != 1) {
			return false;
		}
		else {
			return Character.isDigit(text.charAt(0));
		}
	}
	
	//Take the value in sudoku and transfer it to the UI board
	public void printSudoku() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudokuUI[i][j].setText(String.valueOf(sudoku.value(j,i)));
			}
		}
	}
	//Clear the sudoku and sudokuUI when the clear button is clicked
	private void clearSudoku() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				sudokuUI[i][j].setText("");
				sudoku.removeNum(j, i, sudoku.value(j, i));
			}
		}
	}
	/*
	Display a message at the bottom if there is an error with an input
	@param	FadeTransition ft to create the fade effect
		Label msg the Label object to display the text
		String text the text to input in the label
		TextField box the Textfield object to remove the invalid input
	*/
	public void setMsg(FadeTransition ft, Label msg, String text, TextField box) {
		//Remove the value associated with the invalid sudoku coordinate if there is any and clear the text field
		int x = GridPane.getColumnIndex(box);
		int y = GridPane.getRowIndex(box);
		sudoku.removeNum(x, y, sudoku.value(x, y));
		box.clear();
		msg.setText(text);
		//Create a fade transition for the text 
		//Set the opacity range from 1 to 0 and repeat 1 time
		ft.setFromValue(1.0);;
		ft.setToValue(0.0);
		ft.setCycleCount(1);
		ft.play();
	}
	public void start(Stage stage) throws Exception {
		stage.setTitle("Sudoku Solver");
		Label msg = new Label();
		//Set duration of the fade transition for 2 seconds (2000 milliseconds)
		FadeTransition ft = new FadeTransition(Duration.millis(2000), msg);
		
		GridPane gridLayout = new GridPane();
		//Spacing between pixel
		gridLayout.setHgap(0);
		gridLayout.setVgap(0);
		gridLayout.setPadding(new Insets(5,5,5,5)); //Spacing around the entire grid TRBL
		//Initializing the TextField array
		for (int i = 0; i < sudokuUI.length; i++) {
			for (int j = 0; j < sudokuUI[i].length; j++) {
				sudokuUI[i][j] = new TextField();
				TextField temp = sudokuUI[i][j];
				//KeyEventPressed to handle backspace need to get the value and index to remove from the board
				temp.setOnKeyPressed(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						if(e.getCode().equals(KeyCode.BACK_SPACE)) {
							int x = GridPane.getColumnIndex(temp);
							int y = GridPane.getRowIndex(temp);
							sudoku.removeNum(x,y,sudoku.value(x,y));
						}
					}
				});
				temp.setOnKeyReleased(new EventHandler<KeyEvent> () {
					public void handle(KeyEvent e) {
						if(!e.getCode().equals(KeyCode.BACK_SPACE)) {
							int x = GridPane.getColumnIndex(temp);
							int y = GridPane.getRowIndex(temp);
							//Check if the input is a digit between 1 and 9
							if(isDigit(text) && !text.contentEquals("0")) {
								int num = Integer.valueOf(temp.getText()); //convert to integer
								if(sudoku.isValid(x,y,num)) { //check if it is valid 
									sudoku.addNum(x,y,num);
								}
								else {
									//error message if number is contained in row, column, or 3x3 box
									setMsg(ft, msg, "Number already contained", temp);  
								}
							}
							else {
								//error message if the input number is greater then 9
								setMsg(ft, msg, "Invalid: not a number between 1-9", temp);
							}
						}
					}
				});
				//Adding the TextField to the gridpane
				GridPane.setConstraints(sudokuUI[i][j], j, i);
				gridLayout.add(sudokuUI[i][j], j, i);
			}
		}
		//Setting up the UI scene
		Button solveButton = new Button("Solve");
		Button clearButton = new Button("Clear");
		
		VBox vBox = new VBox(0);
		VBox vBox2 = new VBox();
		HBox hBox = new HBox();
		solveButton.setPadding(new Insets(10,0,0,0));
		clearButton.setPadding(new Insets(10,0,0,0));
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(solveButton, clearButton);
		vBox2.setAlignment(Pos.CENTER);
		vBox2.getChildren().addAll(hBox, msg);
		vBox.getChildren().addAll(gridLayout, vBox2);
		
		solveButton.setOnAction(e -> {
			if(sudoku.solveSudoku())
				printSudoku();
			else
				msg.setText("NO SOLUTIONS!");
		});
		
		clearButton.setOnAction(e -> {
			clearSudoku();
		});
		
		stage.setScene(new Scene(vBox, 250, 300));
		stage.show();
	}
	
	public static void main(String [] args) {
		launch(args);		
	}
}
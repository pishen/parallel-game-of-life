package info.pishen.gameoflife;

public class CellGrid {
	boolean[][] grid;
	
	public CellGrid(int rowNum, int colNum){
		grid = new boolean[rowNum][colNum];
	}
	
	public int getRowNum(){
		return grid.length;
	}
	
	public int getColNum(){
		return grid[0].length;
	}
}

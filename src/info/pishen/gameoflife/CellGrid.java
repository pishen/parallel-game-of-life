package info.pishen.gameoflife;

public class CellGrid {
	private boolean[][] grid;
	
	public CellGrid(int rowNum, int colNum){
		grid = new boolean[rowNum][colNum];
		for(int i = 0; i < rowNum; i++){
			for(int j = 0; j < colNum; j++){
				if(Math.random() > 0.6){
					grid[i][j] = true;
				}
			}
		}
	}
	
	public int getRowNum(){
		return grid.length;
	}
	
	public int getColNum(){
		return grid[0].length;
	}
	
	public synchronized void setNewGrid(boolean[][] newGrid){
		grid = newGrid;
	}
	
	public synchronized boolean[][] getPartialGrid(int iStart, int jStart, int iEnd, int jEnd){
		boolean[][] partialGrid = new boolean[iEnd - iStart + 1][jEnd - jStart + 1];
		for(int iOut = 0, iGrid = iStart; iGrid <= iEnd; iOut++, iGrid++){
			for(int jOut = 0, jGrid = jStart; jGrid <= jEnd; jOut++, jGrid++){
				partialGrid[iOut][jOut] = grid[iGrid][jGrid];
			}
		}
		return partialGrid;
	}
}

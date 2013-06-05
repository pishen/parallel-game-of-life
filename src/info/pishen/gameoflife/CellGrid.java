package info.pishen.gameoflife;

import java.util.logging.Logger;

public class CellGrid {
	private static Logger log = Logger.getLogger(CellGrid.class.getName());
	private MainGUI gui;
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
	
	public void setMainGUI(MainGUI gui){
		this.gui = gui;
	}
	
	public int getRowNum(){
		return grid.length;
	}
	
	public int getColNum(){
		return grid[0].length;
	}
	
	public boolean[][] getDuplicateGrid(){
		boolean[][] duplicateGrid = new boolean[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++){
			System.arraycopy(grid[i], 0, duplicateGrid[i], 0, grid[i].length);
		}
		return duplicateGrid;
	}
	
	public void setNewGrid(boolean[][] newGrid){
		replaceGrid(newGrid);
		gui.repaintGrid();
	}
	
	private synchronized void replaceGrid(boolean[][] newGrid){
		grid = newGrid;
	}
	
	public synchronized boolean[][] getPartialGrid(int iStart, int jStart, int iEnd, int jEnd){
		boolean[][] partialGrid = new boolean[iEnd - iStart + 1][jEnd - jStart + 1];
		for(int iOut = 0, iGrid = iStart; iGrid <= iEnd; iOut++, iGrid++){
			System.arraycopy(grid[iGrid], jStart, partialGrid[iOut], 0, partialGrid[iOut].length);
			/*for(int jOut = 0, jGrid = jStart; jGrid <= jEnd; jOut++, jGrid++){
				partialGrid[iOut][jOut] = grid[iGrid][jGrid];
			}*/
		}
		return partialGrid;
	}
}

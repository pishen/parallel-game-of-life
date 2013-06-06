package info.pishen.gameoflife;

import java.util.logging.Logger;

public class CellGrid {
	@SuppressWarnings("unused")
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
	
	public synchronized void updateGrid(int i, int j, boolean value){
		grid[i][j] = value;
	}
	
	public void setNewGrid(boolean[][] newGrid){
		replaceGrid(newGrid);
		gui.repaintGrid();
	}
	
	private synchronized void replaceGrid(boolean[][] newGrid){
		grid = newGrid;
	}
	
	public synchronized boolean getValue(int i, int j){
		return grid[i][j];
	}
	
	public synchronized boolean[][] getPartialGrid(int iTop, int jLeft, int iBottom, int jRight){
		//iBottom and jRight are included
		boolean[][] partialGrid = new boolean[iBottom - iTop + 1][jRight - jLeft + 1];
		for(int iOut = 0, iGrid = iTop; iGrid <= iBottom; iOut++, iGrid++){
			System.arraycopy(grid[iGrid], jLeft, partialGrid[iOut], 0, partialGrid[iOut].length);
		}
		return partialGrid;
	}
}

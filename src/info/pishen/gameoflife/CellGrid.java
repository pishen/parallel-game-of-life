package info.pishen.gameoflife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.logging.Logger;

public class CellGrid {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(CellGrid.class.getName());
	private boolean[][] grid;
	
	public CellGrid(String patternName) throws NumberFormatException, IOException, URISyntaxException{
		if(patternName.equals("clear")){
			grid = new boolean[2000][2000];
			return;
		}else if(patternName.equals("random")){
			grid = new boolean[2000][2000];
			for(int i = 0; i < 2000; i++){
				for(int j = 0; j < 2000; j++){
					if(Math.random() > 0.6){
						grid[i][j] = true;
					}
				}
			}
			return;
		}else if(patternName.startsWith("pseudo-")){
			Random random = new Random(2013);
			int size = Integer.parseInt(patternName.substring(7));
			grid = new boolean[size][size];
			for(int i = 0; i < size; i++){
				for(int j = 0; j < size; j++){
					if(random.nextDouble() > 0.6){
						grid[i][j] = true;
					}
				}
			}
			return;
		}
		
		File pattern = new File("pattern/" + patternName);
		
		BufferedReader in = new BufferedReader(new FileReader(pattern));
		int width = 1000, height = 1000, iStart = 0, jStart = 0;
		String content = "";
		String line = null;
		while((line = in.readLine()) != null){
			if(line.startsWith("#")){
				continue;
			}else if(line.startsWith("x")){
				String[] infos = line.split(", ");
				for(String info: infos){
					if(info.startsWith("w")){
						width = Integer.parseInt(info.split(" = ")[1]);
					}else if(info.startsWith("h")){
						height = Integer.parseInt(info.split(" = ")[1]);
					}else if(info.startsWith("i")){
						iStart = Integer.parseInt(info.split(" = ")[1]);
					}else if(info.startsWith("j")){
						jStart = Integer.parseInt(info.split(" = ")[1]);
					}
				}
			}else{
				content += line;
			}
		}
		in.close();
		
		grid = new boolean[height][width];
		int iCurrent = iStart, jCurrent = jStart;
		String countStr = "";
		for(int i = 0; i < content.length(); i++){
			if(content.charAt(i) == 'b'){
				jCurrent += (countStr.equals("") ? 1 : Integer.parseInt(countStr));
				countStr = "";
			}else if(content.charAt(i) == 'o'){
				int count = (countStr.equals("") ? 1 : Integer.parseInt(countStr));
				for(int j = 0; j < count; j++){
					if(iCurrent < height && jCurrent < width){
						grid[iCurrent][jCurrent] = true;
					}
					jCurrent++;
				}
				countStr = "";
			}else if(content.charAt(i) == '$'){
				jCurrent = jStart;
				iCurrent += (countStr.equals("") ? 1 : Integer.parseInt(countStr));
				countStr = "";
			}else if(content.charAt(i) == '!'){
				break;
			}else{
				countStr += Integer.parseInt(content.substring(i, i + 1));
			}
		}
	}
	
	public int getRowNum(){
		return grid.length;
	}
	
	public int getColNum(){
		return grid[0].length;
	}
	
	/*public boolean[][] getDuplicateGrid(){
		boolean[][] duplicateGrid = new boolean[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++){
			System.arraycopy(grid[i], 0, duplicateGrid[i], 0, grid[i].length);
		}
		return duplicateGrid;
	}*/
	
	public boolean[][] getGrid(){
		return grid;
	}
	
	public synchronized void updateGrid(int i, int j, boolean value){
		grid[i][j] = value;
	}
	
	public synchronized void replaceGrid(boolean[][] newGrid){
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

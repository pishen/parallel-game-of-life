package info.pishen.gameoflife;

import java.util.logging.Logger;

public class ParallelGenerator {
	private static Logger log = Logger.getLogger(ParallelGenerator.class.getName());
	
	private boolean[][] oldGrid, newGrid;
	private CellGrid cellGrid;
	private volatile UpdateThread updateThread;
	
	public ParallelGenerator(CellGrid cellGrid){
		this.cellGrid = cellGrid;
	}
	
	public void run(){
		updateThread = new UpdateThread();
		updateThread.start();
	}
	
	public void pause(MainGUI mainGUI){
		updateThread.mainGUI = mainGUI;
		updateThread.toPause = true;
	}
	
	private class UpdateThread extends Thread{
		volatile MainGUI mainGUI;
		volatile boolean toPause = false;
		
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(500);
					//log.info("test");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				oldGrid = cellGrid.getDuplicateGrid();
				newGrid = new boolean[oldGrid.length][oldGrid[0].length];
				for(int i = 0; i < oldGrid.length; i++){
					for(int j = 0; j < oldGrid[0].length; j++){
						int count = 0;
						for(int iSub = Math.max(0, i - 1); iSub < Math.min(i + 2, oldGrid.length); iSub++){
							for(int jSub = Math.max(0, j - 1); jSub < Math.min(j + 2, oldGrid[i].length); jSub++){
								if(iSub == i && jSub == j){
									continue;
								}
								if(oldGrid[iSub][jSub]){
									count++;
								}
							}
						}
						if(count < 2 || count > 3){
							newGrid[i][j] = false;
						}else if(count == 3){
							newGrid[i][j] = true;
						}else{
							newGrid[i][j] = oldGrid[i][j];
						}
					}
				}
				
				if(toPause){
					mainGUI.enableRun();
					break;
				}
				
				cellGrid.setNewGrid(newGrid);
			}
		}
	}
}

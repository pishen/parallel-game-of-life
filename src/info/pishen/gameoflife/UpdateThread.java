package info.pishen.gameoflife;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

public class UpdateThread extends Thread{
	//@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(UpdateThread.class.getName());
	
	private boolean[][] oldGrid, newGrid;
	private CellGrid cellGrid;
	//private int parallelLevel = 1;
	private int blockSize = 0;
	private boolean toStop = false;
	private int evalIter = 0;
	
	public UpdateThread(CellGrid cellGrid, int evalIter){
		this.cellGrid = cellGrid;
		this.evalIter = evalIter;
		blockSize = cellGrid.getRowNum() / Runtime.getRuntime().availableProcessors();
	}
	
	@Override
	public void run(){
		int count = 0;
		double accuTime = 0.0;
		
		ForkJoinPool pool = new ForkJoinPool();
		
		while(toStop == false){
			oldGrid = cellGrid.getGrid();
			newGrid = new boolean[oldGrid.length][oldGrid[0].length];
			
			//parallel update//\\//\\//\\\//\\//\\//\\//
			long startTime = System.currentTimeMillis();
			
			SubUpdateTask rootTask = new SubUpdateTask(0, cellGrid.getRowNum());
			pool.invoke(rootTask);
			
			long endTime = System.currentTimeMillis();
			//\\//\\//\\\//\\//\\//\\//\\//\\\//\\//\\//
			
			if(count < evalIter){
				count++;
				double updateTime = (endTime - startTime) / 1000.0;
				//log.info("Iter: " + count + " Time: " + updateTime);
				accuTime += updateTime;
				if(count == evalIter){
					log.info("BlockSize: " + blockSize + " Avg: " + (accuTime / (double)evalIter));
					MainFrame.instance.evalNext(blockSize * 2);
				}
			}
			
			synchronized(this){
				if(toStop == false){
					cellGrid.replaceGrid(newGrid);
				}
			}
			
			MainFrame.instance.showUpdateTime((endTime - startTime) / 1000.0);
			MainFrame.instance.repaintGrid();
		}
		
		pool.shutdown();
	}
	
	public synchronized void lagStop(){
		toStop = true;
	}
	
	public void setBlockSize(int value){
		blockSize = value;
	}
	
	private class SubUpdateTask extends RecursiveAction{
		private static final long serialVersionUID = 1L;
		private int iStart, iEnd;
		
		public SubUpdateTask(int iStart, int iEnd){
			this.iStart = iStart;
			this.iEnd = iEnd;
		}
		
		private void computeDirectly() {
			//iEnd is excluded
			for(int i = iStart; i < iEnd; i++){
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
		}

		@Override
		protected void compute() {
			if(iEnd - iStart <= blockSize){
				computeDirectly();
				return;
			}
			
			int iMiddle = (iStart + iEnd) / 2;
			
			invokeAll(new SubUpdateTask(iStart, iMiddle), new SubUpdateTask(iMiddle, iEnd));
		}
	}
}

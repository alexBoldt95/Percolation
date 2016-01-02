import java.util.Arrays;

import princeton.*;

/**
 * Simulate percolation thresholds for a grid-base system using depth-first-search,
 * aka 'flood-fill' techniques for determining if the top of a grid is connected
 * to the bottom of a grid.
 * <P>
 * Modified from the COS 226 Princeton code for use at Duke. The modifications
 * consist of supporting the <code>IPercolate</code> interface, renaming methods
 * and fields to be more consistent with Java/Duke standards and rewriting code
 * to reflect the DFS/flood-fill techniques used in discussion at Duke.
 * <P>
 * @author Kevin Wayne, wayne@cs.princeton.edu
 * @author Owen Astrachan, ola@cs.duke.edu
 * @author Jeff Forbes, forbes@cs.duke.edu
 */


public class PercolationDFS implements IPercolate {
	// possible instance variable for storing grid state
	public int[][] myGrid;

	/**
	 * Initialize a grid so that all cells are blocked.
	 * 
	 * @param n
	 *            is the size of the simulated (square) grid
	 */
	public PercolationDFS(int n) {
		// TODO complete constructor and add necessary instance variables
		myGrid = new int[n][n];
		for(int r=0; r<n; r++){
			Arrays.fill(myGrid[r], BLOCKED);
		}
	}

	public void open(int i, int j) {
		myGrid[i][j]=OPEN;
		flush();
		for(int col=0; col<myGrid[0].length; col++){
			dfs(0, col);
		}
	}

	public boolean isOpen(int i, int j) {
		return myGrid[i][j]==OPEN;
	}

	public boolean isFull(int i, int j) {
		return myGrid[i][j]==FULL;
	}
	/**
	 * Flush out all full cells by marking them as open
	 */
	public void flush(){
		for(int y=0; y<myGrid.length; y++){
			for(int x=0; x<myGrid.length; x++){
				if(isFull(y, x)){
					myGrid[y][x]=OPEN;
				}
			}
		}
	}

	public boolean percolates() {
		for(int col=0; col<myGrid[0].length; col++){
			if(isFull(0, col)){
				for(int j=0; j<myGrid.length; j++){
					if(isFull(myGrid.length-1, j)){
						return true;
					}
				}
			}		
		}
		return false;
	}

	/**
	 * Private helper method to mark all cells that are open and reachable from
	 * (row,col).
	 * 
	 * @param row
	 *            is the row coordinate of the cell being checked/marked
	 * @param col
	 *            is the col coordinate of the cell being checked/marked
	 */
	private void dfs(int row, int col) {
		if(row < 0 || row >= myGrid.length || col < 0 || col >= myGrid.length){
			return;
		}
		if(!isOpen(row, col) || isFull(row, col)){
			return;
		}
		myGrid[row][col]=FULL;
		dfs(row+1, col);
		dfs(row-1, col);
		dfs(row, col+1);
		dfs(row, col-1);		
	}

}

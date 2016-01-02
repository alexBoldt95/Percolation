import java.util.Arrays;

/**
 * Simulate a system to see its Percolation Threshold, but use a UnionFind
 * implementation to determine whether simulation occurs. The main idea is that
 * initially all cells of a simulated grid are each part of their own set so
 * that there will be n^2 sets in an nxn simulated grid. Finding an open cell
 * will connect the cell being marked to its neighbors --- this means that the
 * set in which the open cell is 'found' will be unioned with the sets of each
 * neighboring cell. The union/find implementation supports the 'find' and
 * 'union' typical of UF algorithms.
 * <P>
 * 
 * @author Owen Astrachan
 * @author Jeff Forbes
 *
 */

public class PercolationUF implements IPercolate {
	private final int OUT_BOUNDS = -1;
	public int[][] myGrid;
	public IUnionFind myUniter;
	public int mySource;
	public int mySink;
	/**
	 * Constructs a Percolation object for a nxn grid that uses unionThing to
	 * store sets representing the cells and the top/source and bottom/sink
	 * virtual cells
	 */
	public PercolationUF(int n, IUnionFind unionThing) {
		myGrid = new int[n][n];
		for(int r=0; r<n; r++){
			Arrays.fill(myGrid[r], BLOCKED);
		}
		unionThing.initialize(n*n+2);
		myUniter = unionThing;
		mySource = (int) Math.pow(n, 2);
		mySink = ((int) Math.pow(n, 2)) + 1;
		
	}

	/**
	 * Return an index that uniquely identifies (row,col), typically an index
	 * based on row-major ordering of cells in a two-dimensional grid. However,
	 * if (row,col) is out-of-bounds, return OUT_BOUNDS.
	 * 
	 * 0 1 2 3
	 * 4 5 6 7
	 * 8 9 . .
	 *       15
	 * 16 - TOP
	 * 17- BOTTOM
	 */
	public int getIndex(int row, int col) {
		if(row < 0 || row >= myGrid.length || col < 0 || col >= myGrid.length){
			return OUT_BOUNDS;
		}
		return row*myGrid.length+col;
	}

	public void open(int i, int j) {
		 connect(i, j);
		 myGrid[i][j]=OPEN;
	}

	public boolean isOpen(int i, int j) {
		return myGrid[i][j]==OPEN || isFull(i, j);
	}

	public boolean isFull(int i, int j) {
		return myUniter.connected(getIndex(i ,j), mySource);
	}

	public boolean percolates() {
		return myUniter.connected(mySource, mySink);
	}

	/**
	 * Connect new site (row, col) to all adjacent open sites
	 */
	private void connect(int row, int col) {
		if(row==0){			
			myUniter.union(getIndex(row, col), mySource);			
		}
		if(row==myGrid.length-1){
			myUniter.union(getIndex(row, col), mySink);
		}
		if(getIndex(row+1, col)!=OUT_BOUNDS && isOpen(row+1, col)){
			myUniter.union(getIndex(row, col), getIndex(row+1, col));
		}
		if(getIndex(row-1, col)!=OUT_BOUNDS && isOpen(row-1, col)){
			myUniter.union(getIndex(row, col), getIndex(row-1, col));
		}
		if(getIndex(row, col+1)!=OUT_BOUNDS && isOpen(row, col+1)){
			myUniter.union(getIndex(row, col), getIndex(row, col+1));
		}
		if(getIndex(row, col-1)!=OUT_BOUNDS && isOpen(row, col-1)){
			myUniter.union(getIndex(row, col), getIndex(row, col-1));
		}
	}

}

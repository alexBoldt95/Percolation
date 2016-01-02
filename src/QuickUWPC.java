
public class QuickUWPC implements IUnionFind{
	
	 private int[] parent;   // parent[i] = parent of i
	    private int[] size;     // size[i] = number of sites in subtree rooted at i
	    private int count;      // number of components

	    /**
	     * Initializes an empty union-find data structure with <tt>N</tt> sites
	     * <tt>0</tt> through <tt>N-1</tt>. Each site is initially in its own 
	     * component.
	     *
	     * @param  N the number of sites
	     * @throws IllegalArgumentException if <tt>N &lt; 0</tt>
	     */
	    public QuickUWPC(int n) {
	        initialize(n);
	    }
	    public void initialize(int n){
	    	count = n;
	        parent = new int[n];
	        size = new int[n];
	        for (int i = 0; i < n; i++) {
	            parent[i] = i;
	            size[i] = 1;
	        }
	    }

	    /**
	     * Returns the number of components.
	     *
	     * @return the number of components (between <tt>1</tt> and <tt>N</tt>)
	     */
	    public int components() {
	        return count;
	    }
	  
	    /**
	     * Returns the component identifier for the component containing site <tt>p</tt>.
	     *
	     * @param  p the integer representing one object
	     * @return the component identifier for the component containing site <tt>p</tt>
	     * @throws IndexOutOfBoundsException unless <tt>0 &le; p &lt; N</tt>
	     */
	    public int find(int p) {
	    	//find now compresses the path of every element in the set to point straight to the root
	    	//creating a flat tree
	        validate(p);
	        int root = p;
	        //after this loop finished, root is equal to the root of the entire tree set (the very top)
	        while (root != parent[root])
	            root = parent[root];	        
	        while (p != root){
	        	int temp = parent[p];
	        	parent[p] =root;
	        	p= temp;
	        }
	        return root;
	    }

	    // validate that p is a valid index
	    private void validate(int p) {
	        int N = parent.length;
	        if (p < 0 || p >= N) {
	            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (N-1));  
	        }
	    }

	    /**
	     * Returns true if the the two sites are in the same component.
	     *
	     * @param  p the integer representing one site
	     * @param  q the integer representing the other site
	     * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt> are in the same component;
	     *         <tt>false</tt> otherwise
	     * @throws IndexOutOfBoundsException unless
	     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
	     */
	    public boolean connected(int p, int q) {
	        return find(p) == find(q);
	    }

	    /**
	     * Merges the component containing site <tt>p</tt> with the 
	     * the component containing site <tt>q</tt>.
	     *
	     * @param  p the integer representing one site
	     * @param  q the integer representing the other site
	     * @throws IndexOutOfBoundsException unless
	     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
	     */
	    public void union(int p, int q) {
	        int rootP = find(p);
	        int rootQ = find(q);
	        if (rootP == rootQ) return;

	        // make smaller root point to larger one
	        //includes setting sets to the grandparent set of the other element (path compression). 
	        if (size[rootP] < size[rootQ]) {
	            parent[rootP] = rootQ;
	            size[rootQ] += size[rootP];
	        }
	        else {
	            parent[rootQ] = rootP;
	            size[rootP] += size[rootQ];
	        }
	        count--;
	    }


	    /**
	     * Reads in a sequence of pairs of integers (between 0 and N-1) from standard input, 
	     * where each integer represents some object;
	     * if the sites are in different components, merge the two components
	     * and print the pair to standard output.
	     */
//	    public static void main(String[] args) {
//	        int N = StdIn.readInt();
//	        QuickUWPC uf = new QuickUWPC(N);
//	        while (!StdIn.isEmpty()) {
//	            int p = StdIn.readInt();
//	            int q = StdIn.readInt();
//	            if (uf.connected(p, q)) continue;
//	            uf.union(p, q);
//	            StdOut.println(p + " " + q);
//	        }
//	        StdOut.println(uf.count() + " components");
//	    }

	}


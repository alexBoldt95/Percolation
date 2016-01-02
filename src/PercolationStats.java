import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

import princeton.*;

/**
 * Print statistics on Percolation: prompts the user for N and T, performs T
 * independent experiments on an N-by-N grid, prints out the 95% confidence
 * interval for the percolation threshold, and prints mean and std. deviation
 * of timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 */

public class PercolationStats {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);

	public static void main(String[] args) {
		int N, T;
		if (args.length == 2) { // use command-line arguments for
								// testing/grading
			N = Integer.parseInt(args[0]);
			T = Integer.parseInt(args[1]);
		} else {
			String input = JOptionPane.showInputDialog("Enter N and T", "20 100");
			String[] inputArr= input.split(" ");
			N = Integer.parseInt(inputArr[0]);
			T = Integer.parseInt(inputArr[1]);			
		}
		
		double[] threshes = new double[T];
		long[] times = new long[T];
		float sumThresh = 0;
		long totalTime = 0;
		int area = N*N;
		
		for(int exp=0; exp<T; exp++){
			double opened = 0;
			//System.out.println(exp);
		//TODO: Comment in the type of percolation structure you want to find statistics for
			//IPercolate perc = new PercolationDFS(N);
			//IPercolate perc = new PercolationUF(N, new QuickFind());
			IPercolate perc = new PercolationUF(N, new QuickUWPC(N));
			ArrayList<Point> cellsToOpen = new ArrayList<Point>();
			for(int i=0; i<N; i++){
				for(int j=0; j<N; j++){
					cellsToOpen.add(new Point(i, j));
				}
			}
			Collections.shuffle(cellsToOpen, ourRandom);
			long start = System.currentTimeMillis();
			for(Point cell:cellsToOpen){
				perc.open(cell.y, cell.x);
				opened++;
				boolean done = perc.percolates();
				if(done){
					break;
				}			
			}
			long end = System.currentTimeMillis();
			long time= end-start;
			double thresh = opened/area;
			threshes[exp]=thresh;
			times[exp]=time;
			//System.out.println(thresh);
			sumThresh += thresh;
			totalTime += time;
		}
		double dubTime = (double) totalTime;
		dubTime =dubTime/1000;
		double pStar = sumThresh/T;
		double meanTime = dubTime/T;
		double percVarSum = 0;
		double timeVarSum = 0;
		for(int exp=0; exp<T; exp++){
			percVarSum += Math.pow(threshes[exp]-pStar, 2);
			timeVarSum += Math.pow(times[exp]-meanTime, 2);
		}
		double percVariance;
		double timeVariance;
		if(T<=1){
			percVariance = Double.NaN;
			timeVariance = Double.NaN;
		}
		else{
			percVariance = percVarSum/(T-1);
			timeVariance = timeVarSum/(T-1);			
		}
		double percStd = Math.sqrt(percVariance);
		double timeStd = Math.sqrt(timeVariance);
		timeStd = timeStd/1000;
		double lower = pStar - (1.96*percStd/Math.sqrt(T));
		double upper = pStar + (1.96*percStd/Math.sqrt(T));
		//printing information
		System.out.printf("mean percolation threshold: %.3f%n", pStar );
		System.out.printf("stdDev: %.3f%n", percStd );
		System.out.print("95% confidence interval: [");
		System.out.printf("%.3f", lower);
		System.out.print(", ");
		System.out.printf("%.3f", upper);
		System.out.println("]");
		System.out.printf("total time: %.3f", dubTime);
		System.out.println(" seconds");
		System.out.printf("mean time per experiment: %.4f", meanTime);
		System.out.println(" seconds");
		System.out.printf("stdDev: %.3f%n", timeStd );
		
		// TODO: print statistics and confidence interval

	}
}

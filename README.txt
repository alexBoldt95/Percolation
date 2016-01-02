This program simulates a liquid percolating (dripping) through a porous material, modeled as a 2D grid with open
and closed cells. Water runs through the open cells from the top, and random cells are opened until it can 
reach the bottom. PercolationVisualizer uses a GUI to visualize the process. PercolationStats runs an arbitrary
amount of experiments to find a mean percolation threshold, or the proportion of cells needed to open on average
to let water through.

***INSTRUCTIONS***
Run PercolationVisualizer.java
Input an N value. This the length of one side of the grid. So an N of 20 creates a 20 x 20 = 400 area size grid.

Run PercolationStats.java

Input an N and T separated by a space. N is the same argument as above, T is the amount of experiments to run,
so T = 100 is 100 experiments run, whose values are then summed and averaged and statistically analyzed in a
console printout. 
***


Name: Alex Boldt
NetID: apb34
Hours Spent: 9
Consulted With: Ademola Olayinka, David Brenes
Resources Used: Austin Lu's Walkthrough Video 
Impressions: A cool application of programming to an engineering/natural scientific problem. The coding wasn't too 
difficult, and it was very satisfying to see the percolation actually work, especially so quickly with union, weighting,
and path compression.
----------------------------------------------------------------------
Problem 1: How does doubling N affect the running time?
DFS: Doubling N for DFS will take about 8 times as much (N^3) because there are N^2 calls to connect the open sites, and checking for percolation
requires iterating over the entire top and bottom row in the worst case, so that will add a factor of about 2N. In most cases, it will be around N
O(N) either way. So O(NN^2) = O(N^3).

QuickFind: Doubling N would take about 16 times as long (N^4) because in the worst case, because for each union, there is a loop over about N^2 elements for
each N^2 elements, because the flat trees are expensive to maintain. 

QuickUWPC: Doubling N will double running time because QuickUWPC is nearly linear in N.

Problem 2: How does doubling T affect the running time?

Doubling T will double running time for all of the algorithms because increasing T simply increases how many times each algorithm is run, so total time
are all linear with T. 

Mean time for each experiment is independent of T.

Problem 3: Measure running time (using calls to System.currentTimeMillis)
of the three versions of your program (DFS, Quick Find, and weighted quick
union with path compression).

Constant T
----------------------
T = 100
N =               50 (1x)          100 (2x)                                  150 (3x)
Mean time per 
experiment (s)                                  factor difference(from N=50)            factor difference(from N=50)
DFS:              0.0087,          0.0762       x8.72                        0.7503     x86.24                       
QuickFind :       0.0107,          0.1717       x15.99                       0.7644     x71.43
QuickUWPC :       0.0031,          0.0045       x1.42                        0.0072     x2.32

Constant N
---------------------
N = 50 
T =               50                 100
Mean time per 
experiment (s)                                     factor difference
DFS:              0.0158,            0.0141        x0.89
QuickFind :       0.0133,            0.0110        x0.83
QuickUWPC :       0.0027,            0.0025        x0.93


The mean time for experiments is not affected by T, but total time is linear with T.

Problem 4: Give a formula (using Big-Oh notation) of the running time on your computer (in seconds) as a function of both N and T.
DFS:            meanTime per experiment: O(N^3)       total Time of all experiments O(TN^3)
QuickFind :     meanTime per experiment:  O(N^4)      total Time of all experiments O(TN^4)
QuickUWPC :     meanTime per experiment:  O(N)        total Time of all experiments O(TN)

Problem 5: Estimate the largest experiment you could  perform in one
[minute, day, year] assuming your computer has enough memory.
Using QuickUWPC: where T is 1, assuming QuickUWPC is O(N)
5A 1 minute: N = 1324503
5B 1 day:    N = 1986206897
5C 1 year:   N = 6.96*10^11

Problem 6: Give a formula (using Big-Oh notation) that describes the amount
of memory (in bytes) that your IPercolate implementation consumes as a function of N.
6A DFS: O(N^2)
6B Quick Find: O(N^2)
6C Weighted quick union with path compression: O(N^2)

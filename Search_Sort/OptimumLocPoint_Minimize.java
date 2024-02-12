class OptimumLocPoint_Minimize {
	static double EPS = 1e-3; //loop will run until the high-low== 1e-3, 
	//which means on substracting high-low will give 0.0001
	static int N = 5;

	// structure defining a point, we need static keyword as we are using it within a class
	static class point {
		int x, y;
		public point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	// structure defining a line of ax + by + c = 0 form, we don't need static keyword if we are declaring it outside this class
	static class line {
		int a, b, c;
		public line(int a, int b, int c){
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}

	//get distance from a point (x, y) on the line to a 'point p'
	static double dist(double x, double y, point p){
		return Math.sqrt(Math.pow((x - p.x),2) + Math.pow((y - p.y),2));
	}

	/* Utility method to compute total distance all points
	from a chosen point(x,y) on the line. double X represents x-coord*/
	static double compute(point p[], int n, line l,double X){
		double res = 0;

		// calculating Y of chosen point by line equation
		double Y = -1 * (l.c + l.a * X) / l.b;
		for (int i = 0; i < n; i++)
			res += dist(X, Y, p[i]);

		return res;
	}

	// Utility method to find minimum total distance
	static double findOptimumCostUtil(point p[], int n,line l){
		double low = -1e6;  
		double high = 1e6;
		// loop untill difference between low and high
		// become less than EPS
		while ((high - low) > EPS) {
		// mid1 and mid2 are representative x co-ordiantes of search space
			double mid1 = low + (high - low) / 3;
			double mid2 = high - (high - low) / 3;

			double dist1 = compute(p, n, l, mid1);
			double dist2 = compute(p, n, l, mid2);

			// if mid2 point gives more total distance,
			// skip third part
			if (dist1 < dist2)
				high = mid2;

			// if mid1 point gives more total distance,
			// skip first part
			else
				low = mid1;
		}
		System.out.println("low:"+low+" high:"+high);
		double X= (low + high)/2;
		double Y=  -1 * (l.c + l.a * X) / l.b;
		System.out.println("X="+X+", Y="+ Y);
		System.out.println("X="+Math.round(X)+", Y="+ Math.round(Y));
		// compute optimum distance cost by sending average of low and high as X
		return compute(p, n, l, (low+high)/2);
	}

	static double findOptimumCost(int points[][], line l){
		point[] p = new point[N];

		// converting 2D array input to "point" object array
		for (int i = 0; i < N; i++)
			p[i] = new point(points[i][0], points[i][1]);

		return findOptimumCostUtil(p, N, l);
	}
	public static void main(String[] args){
		//ax+by+c=0
		line l = new line(1, -1, -3);

		int points[][] ={{ -3, -2 },
						 { -1, 0 },
						 { -1, 2 },
						 { 1, 2 },
						 { 3, 4 }};
		System.out.println("Minimum distance: "+findOptimumCost(points, l));
	}
}

import java.io.EOFException;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.lang.Math;

public class GridMonitor implements GridMonitorInterface {

	private String filename;
	// public enum Dimensions {NUMBER_OF_ROWS,NUMBER_OF_COLUMNS}
	private int NUMBER_OF_ROWS;
	private int NUMBER_OF_COLUMNS;
	private double[][] baseGrid;
	private double[][] gridOfAdjacentPositions;
	private double[][] gridOfAverageAdjacentPositions;
	private double[][] gridOfMaximumDeltaOfAverage;
	private boolean[][] booleanDangerGrid;

	/*
	 * 
	 */
	public GridMonitor(String filename) {
		// reads in a filename from the GridMonitorTester
		this.filename = filename;
		try {

			File gridFile = new File(filename);
			Scanner fileReader = new Scanner(gridFile);
			/*
			 * fileContents creates a empty String that will be populated with the contents
			 * of the read-in file. A space is placed in between each read in line to avoid
			 * numbers being in- properly concatenated together.
			 */
			String fileContents = "";

			while (fileReader.hasNextLine()) {
				fileContents += fileReader.nextLine();
				fileContents += " ";
			}
			/*
			 * FileContents is then split apart at each space each element of the
			 * StringGridValAndDim array is then converted to an integer and stored in a new
			 * 1D array. The purpose of this is to convert all at once all elements in the
			 * array at once including the first two values which represent the *Rows* and
			 * *Columns* of the grid.
			 */
			String[] strGridValAndDim = fileContents.split(" ");
			double[] doubleGridValAndDim = new double[strGridValAndDim.length];
			// covert the string array of values to an int array
			for (int i = 0; i != strGridValAndDim.length; i++) {
				doubleGridValAndDim[i] = Math.abs(Double.parseDouble(strGridValAndDim[i]));

			}
			/*
			 * Taking the first two values from the array intGridValAndDim I create the
			 * dimensions of the 2D array I will populate with the rest of the values. The
			 * 2D array can be read as: Row----->
			 * 
			 * Column ^ | | __________ |1 2 3 |4 5 6 |7 8 9
			 * 
			 * where 1 is at grid position (R=0,C=0) where 4 is at grid position (R=1,C=0)
			 * where 7 is at grid position (R=2,C=0) where 2 is at grid position (R=0,C=1)
			 * where 3 is at grid position (R=0,C=2)
			 */

			this.NUMBER_OF_ROWS = (int) doubleGridValAndDim[0];
			this.NUMBER_OF_COLUMNS = (int) doubleGridValAndDim[1];

			this.baseGrid = new double[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
			/*
			 * NOTE counter adds the grid value from the intGridValAndDim array to the
			 * baseGrid array. NOTE Counter begins at 2 because the first two index's
			 * represent the dimensions and should not be included in the grid.
			 */
			int counter = 2;

			for (int baseRow = 0; baseRow < NUMBER_OF_ROWS; baseRow++) {

				for (int baseCol = 0; baseCol < NUMBER_OF_COLUMNS; baseCol++) {
					this.baseGrid[baseRow][baseCol] = doubleGridValAndDim[counter];
					counter += 1;

				}
			}
			this.gridOfAdjacentPositions = new double[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
			this.gridOfAverageAdjacentPositions = new double[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
			this.gridOfMaximumDeltaOfAverage = new double[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
			for (int row = 0; row < NUMBER_OF_ROWS; row++) {

				for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {

					double topVal;
					double leftVal;
					double rightVal;
					double bottomVal;
					try {
						topVal = baseGrid[row - 1][col];
					} catch (Exception IndexOutOfBoundsException) {
						topVal = baseGrid[row][col];
					}
					try {
						leftVal = baseGrid[row][col - 1];
					} catch (Exception IndexOutOfBoundsException) {
						leftVal = baseGrid[row][col];
					}
					try {
						rightVal = baseGrid[row][col + 1];
					} catch (Exception IndexOutOfBoundsException) {
						rightVal = baseGrid[row][col];
					}
					try {
						bottomVal = baseGrid[row + 1][col];
					} catch (Exception IndexOutOfBoundsException) {
						bottomVal = baseGrid[row][col];
					}

					double sumVal = (double) (topVal + leftVal) + (rightVal + bottomVal);
					double averageOfSum = (sumVal / 4.0);
					double MaxDeltaOfAverage = (averageOfSum / 2.0);

					this.gridOfAdjacentPositions[row][col] = sumVal;
					this.gridOfAverageAdjacentPositions[row][col] = averageOfSum;
					this.gridOfMaximumDeltaOfAverage[row][col] = MaxDeltaOfAverage;

				}

			}

		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
		

	}

	/**
	 * Returns a grid with the same dimensions as the base grid, but each element is
	 * the sum of the 4 surrounding base elements. For elements on a grid border,
	 * the base element's own value is used when looking out of bounds, as if there
	 * is a mirror surrounding the grid. This method is exposed for testing.
	 * 
	 * @return grid containing the sum of adjacent positions
	 */
	public double[][] getBaseGrid() {
		
		return baseGrid;

	}

	public double[][] getSurroundingSumGrid() {
		
		return gridOfAverageAdjacentPositions;
	}

	/**
	 * Returns a grid with the same dimensions as the base grid, but each element is
	 * the average of the 4 surrounding base elements. This method is exposed for
	 * testing.
	 * 
	 * @return grid containing the average of adjacent positions
	 */
	public double[][] getSurroundingAvgGrid() {
		return gridOfAverageAdjacentPositions;
	}

	/**
	 * Returns a grid with the same dimensions as the base grid, but each element is
	 * the maximum delta from the average. For example, if the surrounding average
	 * at some coordinate is 2.0 and the maximum delta is 50% of the average, the
	 * delta value at the same coordinate in this grid will be 1.0. This method is
	 * exposed for testing.
	 * 
	 * @return grid containing the maximum delta from average of adjacent positions
	 */
	public double[][] getDeltaGrid() {

		return gridOfMaximumDeltaOfAverage;
	}

	/**
	 * Returns a grid with the same dimensions as the base grid, but each element is
	 * a boolean value indicating if the cell is at risk. For example, if the cell
	 * at a coordinate is less than the surrounding average minus its maximum delta
	 * or greater than the surrounding average plus its maximum delta, the
	 * corresponding coordinate in this grid will be true. If the base cell value is
	 * within the safe range, however, the corresponding value in this grid will be
	 * false.
	 * 
	 * @return grid containing boolean values indicating whether the cell at this
	 *         location is in danger of exploding
	 */
	public boolean[][] getDangerGrid() {
		this.booleanDangerGrid = new boolean[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {

			for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
				double avgVal = gridOfAverageAdjacentPositions[row][col];
				double deltaVal = gridOfMaximumDeltaOfAverage[row][col];
				double safeRangeHigh = avgVal + deltaVal;
				double safeRangeLow = avgVal - deltaVal;

				if (baseGrid[row][col] < safeRangeLow) {
					booleanDangerGrid[row][col] = true;
				}
				if (baseGrid[row][col] > safeRangeHigh) {
					booleanDangerGrid[row][col] = true;
				} else {
					booleanDangerGrid[row][col] = false;
				}
				

			}
		}
		// loop through and
		return booleanDangerGrid;

	}

}
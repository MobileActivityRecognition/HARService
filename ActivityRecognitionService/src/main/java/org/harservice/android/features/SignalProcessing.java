package org.harservice.android.features;

import android.util.Log;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.Iterator;

import static org.harservice.android.server.Constants.X;
import static org.harservice.android.server.Constants.Y;
import static org.harservice.android.server.Constants.Z;

/**
 * Utility class for signal processing calculations
 */
public class SignalProcessing {
    public static final String TAG = SignalProcessing.class.getSimpleName();

    /**
     * Roll an array appending a new value
     * @param window window matrix array
     * @param dimension dimention to roll
     * @param value value to append
     * @return rolled matrix array at given dimension
     */
    public static float[][] roll(float[][] window, int dimension, float value) {
        int rollSize = window.length - 1;
        for (int i = 0; i < rollSize; i++) {
            window[i][dimension] = window[i+1][dimension];
        }
        window[rollSize][dimension] = value;
        return window;
    }

    /**
     * Calculates an average of a matrix array in 3 dimensions
     * @param sampleWindow matrix array Nx3
     * @return average calculation on each X, Y and Z dimensions
     */
    public static float[] average(float[][] sampleWindow) {
        float[] result = new float[3];
        int windowSize = sampleWindow.length;
        for (int i = 0; i < windowSize; i++) {
            result[X] += sampleWindow[i][X] / windowSize;
            result[Y] += sampleWindow[i][Y] / windowSize;
            result[Z] += sampleWindow[i][Z] / windowSize;
        }
        return result;
    }

    /**
     * Calculates FFT for a given array
     * @param values input array of values
     * @return result array
     */
    public static double[] transform(double[] values) {
        double[] tempConversion = new double[values.length];

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] complx = transformer.transform(values, TransformType.FORWARD);

        for (int i = 0; i < complx.length; i++) {
            double rr = (complx[i].getReal());
            double ri = (complx[i].getImaginary());

            tempConversion[i] = Math.sqrt((rr * rr) + (ri * ri));
        }

        return tempConversion;
    }


    /**
     * Calculates Shannon Entropy
     * @param values array of FFT calculations
     * @return entropy magnitude
     */
    public static double calculateShannonEntropy(double[] values) {
        Frequency freq = new Frequency();
        for (double value : values) {
            freq.addValue(value);
        }

        double result = 0.0;
        Iterator it = freq.valuesIterator();
        double log2 = Math.log(2);
        while(it.hasNext()) {
            Double val = (Double) it.next();
            double frequency = (double) freq.getCount(val) / values.length; // DEBE SER <> 0
            result -= frequency * (Math.log(frequency) / log2);
        }

        return result;
    }

    /**
     * Calculates Autoregression coefficient one dimension matrix.
     * @param values sample values
     * @param mean mean value
     * @param order regression order
     * @param removeMean minus mean flag   @return coefficient matrix
     */
    public static double[] calculateARCoefficients(double[] values, double mean, int order,
                                                   boolean removeMean) {
        if(removeMean){
            for (int i = 0; i < values.length; i++) {
                values[0] -= mean;
            }
        }


        int length = values.length;

        double[] result = new double[order];
        double[] coef = new double[order];
        double[][] mat = new double[order][order];

        //create a symetric matrix of covariance values for the past timeseries elements
        //and a vector with covariances between the past timeseries elements and the timeseries element to estimate.
        //start at "degree"-th sampel and repeat this for the length of the timeseries
        for(int i=order-1;i<length-1;i++) {
            for (int j=0;j<order;j++) {
                coef[j] += values[i+1]*values[i-j];
                for (int k=j;k<order;k++){ //start with k=j due to symmetry of the matrix...
                    mat[j][k] += values[i-j]*values[i-k];
                }
            }
        }

        //calculate the mean values for the matrix and the coefficients vector according to the length of the timeseries
        for (int i=0;i<order;i++) {
            coef[i] /= (length - order);
            for (int j=i;j<order;j++) {
                mat[i][j] /= (length - order);
                mat[j][i] = mat[i][j]; //use the symmetry of the matrix
            }
        }

        RealMatrix matrix = MatrixUtils.createRealMatrix(mat);
        RealVector coefficients = MatrixUtils.createRealVector(coef);

        //solve the equation "matrix * X = coefficients", where x is the solution vector with the AR-coeffcients
        DecompositionSolver solver = new LUDecomposition(matrix).getSolver();
        RealVector solution = null;
        try {
            solution = solver.solve(coefficients);
            result = solution.toArray();
        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Calculates the mean frequency of a FFT calculation
     * @param fftValues array of FFT calculation
     * @param values sample values
     * @return mean frequency magnitude
     */
    public static double meanFreq(double[] fftValues, double[] values) {
        int size = fftValues.length;
        double result = 0.0d;
        double numerador = 0.0;
        double denominador = 0.0;

        for (int i = 0; i < size; i++) {
            numerador = numerador + fftValues[i] * values[i];
            denominador = denominador + fftValues[i];
        }
        result = numerador / denominador;

        return result;
    }
}

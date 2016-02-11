package org.hardroid.features;

import android.os.SystemClock;
import android.util.Log;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import static org.hardroid.server.Constants.X;
import static org.hardroid.server.Constants.Y;
import static org.hardroid.server.Constants.Z;

import org.hardroid.server.Constants.FeatureType;
import org.hardroid.server.Constants.VariableType;


/**
 * Calculate sampling feature for a given window size
 */
public class FeatureProcessing {

    public static final int NUM_FEATURES = FeatureType.values().length;
    private static final String TAG = FeatureProcessing.class.getSimpleName();

    public static double[] calculateSample(int sliceStart, int sliceEnd, int windowSize, float[][] sample,
                                          VariableType variableType) {

        if (variableType == null) {
            variableType = VariableType.MAG;
        }

        Log.d(TAG, String.format("Calculating Sample %s %d to %d = %d",
                variableType, sliceStart, sliceEnd, windowSize));

        // Check invalid window
        if (sliceStart >= sliceEnd ||
                (sliceEnd - sliceStart) > windowSize ||
                sample == null) {
            throw new IllegalArgumentException("Invalid slice and window");
        }



        DescriptiveStatistics stats = new DescriptiveStatistics();

        for (int i = sliceStart; i < sliceEnd && i < sample.length; i++) {
            double calc = 0;
            float[] row = sample[i];
            if (variableType == VariableType.X) {
                calc = row[X];

            } else if (variableType == VariableType.Y) {
                calc = row[Y];

            } else if (variableType == VariableType.Z) {
                calc = row[Z];

            } else if (variableType == VariableType.MAG) {
                double sum = row[X]*row[X] +
                             row[Y]*row[Y] +
                             row[Z]*row[Z];
                calc = Math.sqrt(sum);
            }
            stats.addValue(calc);
        }

        return calculateFeatures(stats);
    }

    private static double[] calculateFeatures(DescriptiveStatistics stats) {
        long now = SystemClock.elapsedRealtime();
        double[] result = new double[NUM_FEATURES];

        Log.d(TAG, String.format("%d: Calculating %d features of size %d", now, NUM_FEATURES,
                stats.getN()));

        //********* FFT *********
        double[] fft  = SignalProcessing.transform(stats.getValues());

        //******************* Feature *******************//
        //mean(s) - Arithmetic mean
        result[FeatureType.MEAN.ordinal()] = stats.getMean();
        //std(s) - Standard deviation
        result[FeatureType.STD.ordinal()] = stats.getStandardDeviation();
        //max(s) - Largest values in array
        result[FeatureType.MAX.ordinal()] = stats.getMax();
        //min(s) - Smallest value in array
        result[FeatureType.MIN.ordinal()] = stats.getMin();
        //skewness(s) - Frequency signal Skewness
        result[FeatureType.SKEWNESS.ordinal()] = stats.getSkewness();
        //kurtosis(s) - Frequency signal Kurtosis
        result[FeatureType.KURTOSIS.ordinal()] = stats.getKurtosis();
        //energy(s) - Average sum of the squares
        result[FeatureType.ENERGY.ordinal()] = stats.getSumsq() / stats.getN();
        //entropy(s) - Signal Entropy
        result[FeatureType.ENTROPY.ordinal()] = SignalProcessing.calculateShannonEntropy(fft);
        //iqr (s) Interquartile range
        result[FeatureType.IRQ.ordinal()] = stats.getPercentile(75) - stats.getPercentile(25);
        //autoregression (s) -4th order Burg Autoregression coefficients
        double[] ar4 = SignalProcessing.calculateARCoefficients(stats.getValues(), stats.getMean(), 4, true);
        result[FeatureType.AR_COEF1.ordinal()] = ar4[0];
        result[FeatureType.AR_COEF2.ordinal()] = ar4[1];
        result[FeatureType.AR_COEF3.ordinal()] = ar4[2];
        result[FeatureType.AR_COEF4.ordinal()] = ar4[3];
        //meanFreq(s) - Frequency signal weighted average
        result[FeatureType.MEAN_FREQ.ordinal()] = SignalProcessing.meanFreq(fft, stats.getValues());
        long finished = SystemClock.elapsedRealtime();

        Log.d(TAG, String.format("%d: Returning %d results in %.3f seconds", finished, NUM_FEATURES,
                (finished - now) / 1000d));

        return result;
    }
}

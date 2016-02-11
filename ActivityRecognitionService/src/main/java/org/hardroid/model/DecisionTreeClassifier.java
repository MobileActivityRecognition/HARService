package org.hardroid.model;

import android.util.Log;

import org.hardroid.common.HumanActivity.Type;
import org.hardroid.model.WekaClassifier;

/**
 * Implements a decision tree classifier
 */
public class DecisionTreeClassifier implements ActivityClassifier {
    private Type[] detectedActivity = new Type[]{
            Type.ON_BICYCLE,
            Type.WALKING,
            Type.RUNNING,
            Type.STILL,
            Type.TILTING,
            Type.ON_BICYCLE,
    };
    public static final String TAG = DecisionTreeClassifier.class.getSimpleName();

    @Override
    public Type classify(double[] featureData) {
        int result = -1;
        Double[] sendData = new Double[featureData.length];
        try {
            int i = 0;
            for (double feature : featureData) {
                sendData[i++] = feature;
            }
            result = (int) WekaClassifier.classify(sendData);
            if (result > 0 && result < detectedActivity.length) {
                return detectedActivity[result];
            }
        } catch (Exception e) {
            Log.e(TAG, "Error clasifying activity " + result + "");
        }
        return Type.UNKNOWN;
    }
}

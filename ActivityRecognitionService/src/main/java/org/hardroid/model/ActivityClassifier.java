package org.hardroid.model;

import org.hardroid.common.HumanActivity;

/**
 * Defines an activity classifier
 */
public interface ActivityClassifier {
    public HumanActivity.Type classify(double[] featureData);
}

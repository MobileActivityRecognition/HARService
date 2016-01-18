package org.harservice.android.model;

import org.harservice.android.common.HumanActivity;

/**
 * Defines an activity classifier
 */
public interface ActivityClassifier {
    public HumanActivity.Type classify(double[] featureData);
}

package org.hardroid.classifier;

import org.hardroid.common.HumanActivity;

/**
 * Defines an activity classifier
 */
public abstract class ActivityClassifier {
    public abstract int version();
    public abstract HumanActivity.Type classify(double[] featureData);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{version = " + version() + "}";
    }
}

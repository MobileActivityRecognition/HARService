package org.hardroid.classifier;

import org.hardroid.common.HumanActivityType;

/**
 * Defines an activity classifier
 */
public abstract class ActivityClassifier {
    public abstract int version();
    public abstract HumanActivityType classify(double[] featureData);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{version = " + version() + "}";
    }
}

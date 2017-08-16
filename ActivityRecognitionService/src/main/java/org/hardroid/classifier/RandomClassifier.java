package org.hardroid.classifier;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.hardroid.common.HumanActivityType;

import java.util.Random;

/**
 * Implements a random classifier
 */
public class RandomClassifier extends ActivityClassifier {
    private final Random generator = new Random();
    private static final HumanActivityType[] activityList = HumanActivityType.values();

    @Override
    public int version() {
        return 1;
    }

    @Override
    public HumanActivityType classify(double[] featureData) {
        DescriptiveStatistics stats = new DescriptiveStatistics(featureData);
        generator.setSeed((long) stats.getSum());
        return activityList[generator.nextInt(activityList.length)];
    }
}

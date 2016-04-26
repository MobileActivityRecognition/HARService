package org.hardroid.classifier;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.hardroid.common.HumanActivity;

import java.util.Random;

/**
 * Implements a random classifier
 */
public class RandomClassifier extends ActivityClassifier {
    private final Random generator = new Random();
    private static final HumanActivity.Type[] activityList = HumanActivity.Type.values();

    @Override
    public int version() {
        return 1;
    }

    @Override
    public HumanActivity.Type classify(double[] featureData) {
        DescriptiveStatistics stats = new DescriptiveStatistics(featureData);
        generator.setSeed((long) stats.getSum());
        return activityList[generator.nextInt(activityList.length)];
    }
}

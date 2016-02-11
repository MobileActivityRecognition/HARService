// IActivityRecognitionManager.aidl
package org.hardroid.common;

import org.hardroid.common.ActivityRecognitionResult;
import org.hardroid.common.IActivityRecognitionResponseListener;

interface IActivityRecognitionManager {
    void requestActivityUpdates(in long detectionIntervalMillis,
                                in IActivityRecognitionResponseListener listener);
    void requestSingleUpdates(in IActivityRecognitionResponseListener listener);
    void removeActivityUpdates(in IActivityRecognitionResponseListener listener);
    ActivityRecognitionResult getDetectedActivities();
}

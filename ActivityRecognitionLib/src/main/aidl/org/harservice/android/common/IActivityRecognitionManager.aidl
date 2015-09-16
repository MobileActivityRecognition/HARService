// IActivityRecognitionManager.aidl
package org.harservice.android.common;

import org.harservice.android.common.ActivityRecognitionResult;
import org.harservice.android.common.IActivityRecognitionResponseListener;

interface IActivityRecognitionManager {
    void requestActivityUpdates(in long detectionIntervalMillis,
                                in IActivityRecognitionResponseListener listener);
    void requestSingleUpdates(in IActivityRecognitionResponseListener listener);
    void removeActivityUpdates(in IActivityRecognitionResponseListener listener);
    ActivityRecognitionResult getDetectedActivities();
}

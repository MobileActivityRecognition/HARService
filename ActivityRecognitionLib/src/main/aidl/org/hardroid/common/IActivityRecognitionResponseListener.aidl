// IActivityRecognitionResponseListener.aidl
package org.hardroid.common;

import org.hardroid.common.ActivityRecognitionResult;

oneway interface IActivityRecognitionResponseListener {
    void onResponse(in ActivityRecognitionResult result);
}

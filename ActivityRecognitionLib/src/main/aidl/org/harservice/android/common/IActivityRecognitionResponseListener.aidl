// IActivityRecognitionResponseListener.aidl
package org.harservice.android.common;

import org.harservice.android.common.ActivityRecognitionResult;

oneway interface IActivityRecognitionResponseListener {
    void onResponse(in ActivityRecognitionResult result);
}

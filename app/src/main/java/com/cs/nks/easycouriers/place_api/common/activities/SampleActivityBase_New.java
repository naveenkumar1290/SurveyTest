/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.cs.nks.easycouriers.place_api.common.activities;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.cs.nks.easycouriers.place_api.common.logger.Log;
import com.cs.nks.easycouriers.place_api.common.logger.LogWrapper;


/**
 * Base launcher activity, to handle most of the common plumbing for samples.
 */
public class SampleActivityBase_New extends Fragment {

    public static final String TAG = "SampleActivityBase";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public   void onStart() {
        super.onStart();
        initializeLogging();
    }

    /** Set up targets to receive log data */
    public void initializeLogging() {
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        // Wraps Android's native log framework
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);

        Log.i(TAG, "Ready");
    }
}

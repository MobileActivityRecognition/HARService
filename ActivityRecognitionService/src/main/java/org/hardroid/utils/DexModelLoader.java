/*
 * HARService: Activity Recognition Service
 * Copyright (C) 2015 agimenez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.hardroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import org.hardroid.model.WekaClassifier;
import org.hardroid.server.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import it.necst.grabnrun.SecureDexClassLoader;
import it.necst.grabnrun.SecureLoaderFactory;

/**
 * Dynamic Model Loader
 */
public class DexModelLoader {
    public static final String TAG = DexModelLoader.class.getSimpleName();
    private final SharedPreferences sharedPrefs;
    private final ConnectivityManager connMgr;
    private Context context;
    private Map<String, URL> packageNamesToCertMap;

    public DexModelLoader(Context context) {
        this.context = context;
        packageNamesToCertMap = new HashMap<>();
        // 1st Entry: valid remote certificate location
        try {
            packageNamesToCertMap.put("org.hardroid.model",
                    new URL(context.getString(R.string.cert_url)));
        } catch (MalformedURLException e1) {
            // A malformed URL was used for remote certificate location
            Log.e(TAG, "A malformed URL was used for remote certificate location!");
        }
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public WekaClassifier retrieveModel(String className) {
        Class<?> loadedClass;
        WekaClassifier model = null;
        boolean saveDate = false;

        if (shouldRetrieve()) {
            loadedClass = loadRemoteModel(className);
            saveDate = true;
        }
        else {
            loadedClass = loadLocalModel(className);
        }

        if (loadedClass != null) {
            try {
                model = (WekaClassifier) loadedClass.newInstance();
                if (saveDate) {
                    saveDate(System.currentTimeMillis());
                }
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem in the instantiation of the target class!");
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Problem in the access to the target class!");
            }
        }
        return model;
    }

    private Class<?> loadLocalModel(String className) {
        Class<?> loadedClass = null;
        try {
            loadedClass = getClass().getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Error: Class not found! " + className);
        }
        return loadedClass;
    }

    private Class<?> loadRemoteModel(String className) {

        Log.d(TAG, "Setting up SecureDexClassLoader...");

        SecureLoaderFactory secureLoaderFactory = new SecureLoaderFactory(context, 1);

        SecureDexClassLoader secureDexClassLoader;

        if (packageNamesToCertMap.size() == 0) {
            return null;
        }

        secureDexClassLoader = secureLoaderFactory.createDexClassLoader(context.getString(R.string.jar_url),
                null,
                getClass().getClassLoader(),
                packageNamesToCertMap);

        Class<?> loadedClass = null;

        try {

            loadedClass = secureDexClassLoader.loadClass(className);

        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Error: Class not found! " + className);
        }

        //secureDexClassLoader.wipeOutPrivateAppCachedData(true, true);

        return loadedClass;
    }

    public boolean shouldRetrieve() {
        if (!isConnected()) {
            return false;
        }
        long result = sharedPrefs.getLong(context.getString(R.string.pref_model_date), -1);
        if (result > 0) {
            long now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
            long lastHour = TimeUnit.MILLISECONDS.toHours(result);
            return (now - lastHour) > 24;
        }
        return true;
    }

    public void saveDate(long time) {
        sharedPrefs.edit().putLong(context.getString(R.string.model_name), time).apply();
    }

    private boolean isConnected() {
        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}

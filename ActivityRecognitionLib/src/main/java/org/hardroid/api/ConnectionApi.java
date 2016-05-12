/*
 * HARService: Activity Recognition Service
 * Copyright (C) 2015 agimenez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.hardroid.api;

import org.hardroid.client.OnClientConnectionListener;

/**
 * This interface holds the state of the connection between Client and Server processes
 */
public interface ConnectionApi {
    /**
     * Request a connection to be made
     */
    void connect();

    /**
     * Request a disconnection to be made
     */
    void disconnect();

    /**
     * Request a reconnection
     */
    void reconnect();

    /**
     * Query the connection status
     *
     * @return true if the service is connected
     */
    boolean isConnected();

    /**
     * Query if the connection is been established
     *
     * @return true if the service is about to be connected
     */
    boolean isConnecting();

    /**
     * Request an update when the service finally is connected
     *
     * @param listener
     *        required object that listents to connection changes
     */
    void addOnConnectionListener(OnClientConnectionListener listener);
}

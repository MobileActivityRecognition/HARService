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

package org.harservice.android.api;

/**
 * Template interface for activity recognition service integration
 */
public interface ConnectionApi {
    /**
     *
     */
    public void connect();

    /**
     *
     */
    public void disconnect();

    /**
     *
     */
    public void reconnect();

    /**
     * @return
     */
    public boolean isConnected();

    /**
     * @return
     */
    public boolean isConnecting();

    // TODO: 31/08/2015 Implementar Connection Calback y Connection Listener
}

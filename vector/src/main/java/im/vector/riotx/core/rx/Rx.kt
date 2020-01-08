/*
 * Copyright 2019 New Vector Ltd
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

package im.vector.riotx.core.rx

import im.vector.riotx.BuildConfig
import im.vector.riotx.features.settings.VectorPreferences
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject

class RxConfig @Inject constructor(
        private val vectorPreferences: VectorPreferences
) {

    /**
     * Make sure unhandled Rx error does not crash the app in production
     */
    fun setupRxPlugin() {
        RxJavaPlugins.setErrorHandler { throwable ->
            Timber.e(throwable, "RxError")

            // Avoid crash in production
            if (BuildConfig.DEBUG || vectorPreferences.failFast()) {
                throw throwable
            }
        }
    }
}

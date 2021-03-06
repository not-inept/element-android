/*
 * Copyright (c) 2020 New Vector Ltd
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

package im.vector.matrix.android.internal.session.room.tags

import im.vector.matrix.android.internal.di.UserId
import im.vector.matrix.android.internal.network.executeRequest
import im.vector.matrix.android.internal.session.room.RoomAPI
import im.vector.matrix.android.internal.task.Task
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

internal interface DeleteTagFromRoomTask : Task<DeleteTagFromRoomTask.Params, Unit> {

    data class Params(
            val roomId: String,
            val tag: String
    )
}

internal class DefaultDeleteTagFromRoomTask @Inject constructor(
        private val roomAPI: RoomAPI,
        @UserId private val userId: String,
        private val eventBus: EventBus
) : DeleteTagFromRoomTask {

    override suspend fun execute(params: DeleteTagFromRoomTask.Params) {
        executeRequest<Unit>(eventBus) {
            apiCall = roomAPI.deleteTag(
                    userId = userId,
                    roomId = params.roomId,
                    tag = params.tag
            )
        }
    }
}

package com.vraj.cityconnect.response

import com.vraj.cityconnect.model.Event
import com.vraj.cityconnect.response.ResponseBody.ResultType.SUCCESS
import com.vraj.cityconnect.util.Constants.GET_EVENTS_SUCCESSFUL

data class GetEventsResponse(
    override val resultType: ResponseBody.ResultType,
    override val data: Data,
    override val message: String
) : ResponseBody<GetEventsResponse.Data> {

    data class Data(
        val events: List<Event>,
    )

    companion object {
        fun success(events: List<Event>): GetEventsResponse =
            GetEventsResponse(SUCCESS, Data(events), GET_EVENTS_SUCCESSFUL)
    }
}

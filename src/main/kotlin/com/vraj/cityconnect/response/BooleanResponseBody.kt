package com.vraj.cityconnect.response

import com.vraj.cityconnect.response.ResponseBody.ResultType.SUCCESS

data class BooleanResponseBody(
    override val resultType: ResponseBody.ResultType,
    override val data: Boolean,
    override val message: String
): ResponseBody<Boolean> {

    companion object {
        fun success(message: String) =
            BooleanResponseBody(SUCCESS, true, message)
    }
}

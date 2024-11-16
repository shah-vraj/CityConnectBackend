package com.vraj.cityconnect.response

import com.vraj.cityconnect.response.ResponseBody.ResultType
import com.vraj.cityconnect.response.ResponseBody.ResultType.FAILURE

data class ErrorResponseBody(
    override val resultType: ResultType,
    override val data: Boolean,
    override val message: String
) : ResponseBody<Boolean> {

    companion object {
        fun error(message: String): ErrorResponseBody =
            ErrorResponseBody(FAILURE, false, message)
    }
}

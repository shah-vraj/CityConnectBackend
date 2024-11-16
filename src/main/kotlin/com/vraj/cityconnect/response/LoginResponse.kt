package com.vraj.cityconnect.response

import com.vraj.cityconnect.response.ResponseBody.ResultType
import com.vraj.cityconnect.response.ResponseBody.ResultType.SUCCESS
import com.vraj.cityconnect.util.Constants.LOGIN_REQUEST_SUCCESS

data class LoginResponse(
    override val resultType: ResultType,
    override val data: Boolean,
    override val message: String
) : ResponseBody<Boolean> {

    companion object {
        fun success(): LoginResponse =
            LoginResponse(SUCCESS, true, LOGIN_REQUEST_SUCCESS)
    }
}

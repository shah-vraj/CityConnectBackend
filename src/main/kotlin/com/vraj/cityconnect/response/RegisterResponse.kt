package com.vraj.cityconnect.response

import com.vraj.cityconnect.response.ResponseBody.ResultType
import com.vraj.cityconnect.response.ResponseBody.ResultType.SUCCESS
import com.vraj.cityconnect.util.Constants.REGISTER_REQUEST_SUCCESS

data class RegisterResponse(
    override val resultType: ResultType,
    override val data: Boolean,
    override val message: String
) : ResponseBody<Boolean> {

    companion object {
        fun success(): RegisterResponse =
            RegisterResponse(SUCCESS, true, REGISTER_REQUEST_SUCCESS)
    }
}
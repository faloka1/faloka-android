package com.example.faloka_mobile.Register;

import com.example.faloka_mobile.API.ApiConfig;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class RegisterErrorUtils {
    public static RegisterErrorResponse parseError(retrofit2.Response<?> response) {
        Converter<ResponseBody, RegisterErrorResponse> converter =
                ApiConfig.retrofit.responseBodyConverter(RegisterErrorResponse.class, new Annotation[0]);

        RegisterErrorResponse error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new RegisterErrorResponse();
        }
        return error;
    }
}

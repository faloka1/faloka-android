package com.example.faloka_mobile.Checkout;

import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.PaymentUploadResponse;

public interface UploadFileListener {
    public void onUpload(PaymentUploadResponse paymentUploadResponse);
}

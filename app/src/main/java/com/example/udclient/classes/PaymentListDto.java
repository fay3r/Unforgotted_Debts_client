package com.example.udclient.classes;

import java.io.Serializable;
import java.util.List;

public class PaymentListDto implements Serializable {
    private List<PaymentGetDto> paymentDtoList;

    public PaymentListDto() {
    }

    public PaymentListDto(List<PaymentGetDto> paymentDtoList) {
        this.paymentDtoList = paymentDtoList;
    }

    public List<PaymentGetDto> getPaymentDtoList() {
        return paymentDtoList;
    }

    public void setPaymentDtoList(List<PaymentGetDto> paymentDtoList) {
        this.paymentDtoList = paymentDtoList;
    }
}

package com.example.udclient.classes;

import java.io.Serializable;
import java.util.List;

public class PaymentListDto implements Serializable {
    private List<PaymentGetDto> paymentListDto;

    public PaymentListDto() {
    }

    public PaymentListDto(List<PaymentGetDto> paymentListDto) {
        this.paymentListDto = paymentListDto;
    }

    public List<PaymentGetDto> getPaymentDtoList() {
        return paymentListDto;
    }

    public void setPaymentDtoList(List<PaymentGetDto> paymentListDto) {
        this.paymentListDto = paymentListDto;
    }

    @Override
    public String toString() {
        return "PaymentListDto{" +
                "paymentDtoList=" + paymentListDto.toString() +
                '}';
    }
}

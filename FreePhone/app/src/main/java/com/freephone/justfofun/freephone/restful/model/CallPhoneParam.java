package com.freephone.justfofun.freephone.restful.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lijianying on 2016/1/7.
 */
public class CallPhoneParam {

    @Expose
    private int businessId;

    @Expose
    private int productId;

    @Expose
    @SerializedName("from")
    private String phoneCaller;

    @Expose
    @SerializedName("to")
    private String phoneReceiver;

    @Expose
    @SerializedName("fromSerNum")
    private String callerShowPhoneNumber;

    @Expose
    @SerializedName("toSerNum")
    private String receiverShowPhoneNumber;

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getPhoneCaller() {
        return phoneCaller;
    }

    public void setPhoneCaller(String phoneCaller) {
        this.phoneCaller = phoneCaller;
    }

    public String getPhoneReceiver() {
        return phoneReceiver;
    }

    public void setPhoneReceiver(String phoneReceiver) {
        this.phoneReceiver = phoneReceiver;
    }

    public String getCallerShowPhoneNumber() {
        return callerShowPhoneNumber;
    }

    public void setCallerShowPhoneNumber(String callerShowPhoneNumber) {
        this.callerShowPhoneNumber = callerShowPhoneNumber;
    }

    public String getReceiverShowPhoneNumber() {
        return receiverShowPhoneNumber;
    }

    public void setReceiverShowPhoneNumber(String receiverShowPhoneNumber) {
        this.receiverShowPhoneNumber = receiverShowPhoneNumber;
    }

    @Override
    public String toString() {
        return "CallPhoneParam{" +
                "businessId=" + businessId +
                ", productId=" + productId +
                ", phoneCaller='" + phoneCaller + '\'' +
                ", phoneReceiver='" + phoneReceiver + '\'' +
                ", callerShowPhoneNumber='" + callerShowPhoneNumber + '\'' +
                ", receiverShowPhoneNumber='" + receiverShowPhoneNumber + '\'' +
                '}';
    }
}

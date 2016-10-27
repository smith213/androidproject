package com.freephone.justfofun.freephone.model;

/**
 * Created by Shenh on 2016/10/27.
 * 联系人信息
 */

public class ContactInfo {
    /**
     * 联系人id
     */
    long contactId;

    /**
     * 联系人姓名
     */
    String contactName;

    /**
     * 联系人手机号码
     */
    String contactNum;

    /**
     * 联系人头像id
     */
    long photoId;

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }
}

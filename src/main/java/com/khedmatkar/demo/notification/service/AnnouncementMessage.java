package com.khedmatkar.demo.notification.service;

import lombok.Getter;

@Getter
public enum AnnouncementMessage {
    ADMIN_CREATION_ANNOUNCEMENT("با سلام %s گرامی. حساب کاربری شما توسط مدیر فناوری اطلاعات ایجاد شده است.", "ساخت حساب کاربری ادمین"),
    ADMIN_PERMISSIONS_UPDATE_ANNOUNCEMENT("دسترسی های شما توسط مدیر فناوری اطلاعات به روز شده است.", ""), //todo complete
    ADMIN_CANCELS_SERVICE_ANNOUNCEMENT("کاربر گرامی، درخواست به شماره %s توسط مدیران سامانه لغو شد.", ""),
    SPECIALIST_IS_CHOSEN_FOR_SERVICE_ANNOUNCEMENT("شما به عنوان متخصص برای انجام درخواست %s انتخاب شده اید.", ""),
    CUSTOMER_CANCELS_SERVICE_ANNOUNCEMENT("کاربر گرامی، درخواست به شماره %s توسط متشری لغو شد.", ""),
    SPECIALIST_REJECTS_SERVICE_ANNOUNCEMENT("کاربر گرامی، همکاری برای درخواست به شماره %s توسط متخصص رد شد.", ""),
    SPECIALIST_ACCEPTS_SERVICE_ANNOUNCEMENT("کاربر گرامی، متخصص همکاری برای درخواست به شماره %s را پذیرفت.", ""),
    CUSTOMER_REJECTS_SPECIALIST_ANNOUNCEMENT("کاربر گرامی، همکاری برای درخواست به شماره %s توسط مشتری رد شد.", ""),
    CUSTOMER_ACCEPTS_SPECIALIST_ANNOUNCEMENT("کاربر گرامی، مشتری همکاری برای درخواست به شماره %s با شما را تایید کرد.", ""),
    CUSTOMER_CREATES_SERVICE_REQUEST("کاربر گرامی، سفارش شما با موفقیت در سیستم ثبت شد.", "ثبت سفارش");


    private final String message;
    private final String subject;

    AnnouncementMessage(String message, String subject) {
        this.message = message;
        this.subject = subject;
    }
}

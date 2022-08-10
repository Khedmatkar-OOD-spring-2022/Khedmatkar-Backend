package com.khedmatkar.demo.notification.service;

import lombok.Getter;

@Getter
public enum AnnouncementMessage {

    TEST_ANNOUNCEMENT("این پیام به منظور تست زیر سامانه آگهی ارسال شده است.", "پیام تستی"),
    ADMIN_CREATION_ANNOUNCEMENT("با سلام %s گرامی. حساب کاربری شما توسط مدیر فناوری اطلاعات ایجاد شده است.\nرمز عبور حساب کاربری شما %s می‌باشد.", "ساخت حساب کاربری ادمین"),
    ADMIN_PERMISSIONS_UPDATE_ANNOUNCEMENT("دسترسی های شما توسط مدیر فناوری اطلاعات به روز شده است.", "به روز رسانی دسترسی مدیر"),
    ADMIN_CANCELS_SERVICE_ANNOUNCEMENT("کاربر گرامی، درخواست به شماره %s توسط مدیران سامانه لغو شد.", "لغو درخواست خدمت"),
    SPECIALIST_IS_CHOSEN_FOR_SERVICE_ANNOUNCEMENT("شما به عنوان متخصص برای انجام درخواست %s انتخاب شده اید.", "پیشنهاد انجام درخواست خدمت"),
    CUSTOMER_CANCELS_SERVICE_ANNOUNCEMENT("کاربر گرامی، درخواست به شماره %s توسط متشری لغو شد.", "لغو درخواست حدمت"),
    SPECIALIST_REJECTS_SERVICE_ANNOUNCEMENT("کاربر گرامی، همکاری برای درخواست به شماره %s توسط متخصص رد شد.", "رد انجام درخواست خدمت"),
    SPECIALIST_ACCEPTS_SERVICE_ANNOUNCEMENT("کاربر گرامی، متخصص همکاری برای درخواست به شماره %s را پذیرفت.", "پذیرش انجام درخواست خدمت"),
    CUSTOMER_REJECTS_SPECIALIST_ANNOUNCEMENT("کاربر گرامی، همکاری برای درخواست به شماره %s توسط مشتری رد شد.", "رد انجام درخواست خدمت"),
    CUSTOMER_ACCEPTS_SPECIALIST_ANNOUNCEMENT("کاربر گرامی، مشتری همکاری برای درخواست به شماره %s با شما را تایید کرد.", "پذیرش انجام درخواست خدمت"),
    CUSTOMER_CREATES_SERVICE_REQUEST("کاربر گرامی، سفارش شما با موفقیت در سیستم ثبت شد.", "ثبت سفارش"),
    SPECIALIST_FINISHES_SERVICE_REQUEST("کاربر گرامی، درخواست به شماره %s به پایان رسید.\nلطفا با پر کردن فرم نظرسنجی به ما در سرویس دهی بهتر کمک کنید.", "اتمام سفارش"),
    ADMIN_ANSWERS_TECHNICAL_ISSUE("کاربر گرامی، به تیکت شماره %s پاسخ داده شد.", "پاسخ به تیکت");


    private final String message;
    private final String subject;

    AnnouncementMessage(String message, String subject) {
        this.message = message;
        this.subject = subject;
    }
}

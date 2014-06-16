package com.android.storemanage.utils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {

  public static Intent sendMMS(String phone, String body, File img) {
    Intent intent = new Intent(Intent.ACTION_SENDTO);
    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(img));// uri为你的附件的uri
    intent.setDataAndType(Uri.parse("smsto:" + toSafeString(phone)), "image/*");
    return intent;
  }

  public static Intent sendSMS(String phone, String msg) {
    Intent intent = new Intent(Intent.ACTION_SENDTO);
    intent.putExtra("sms_body", msg);
    intent.setData(Uri.parse("smsto:" + toSafeString(phone)));
    return intent;
  }

  public static Intent getCallIntent(String number) {
    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
    return intent;
  }

  public static Intent getDialIntent(String number) {
    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
    return intent;
  }

  // android获取一个用于打开HTML文件的intent
  public static Intent getHtmlFileIntent(File file) {
    Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content")
        .encodedPath(file.toString()).build();
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setDataAndType(uri, "text/html");
    return intent;
  }

  // android获取一个用于打开图片文件的intent
  public static Intent getImageFileIntent(File file) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(file);
    intent.setDataAndType(uri, "image/*");
    return intent;
  }

  // android获取一个用于打开PDF文件的intent
  public static Intent getPdfFileIntent(File file) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(file);
    intent.setDataAndType(uri, "application/pdf");
    return intent;
  }

  // android获取一个用于打开文本文件的intent
  public static Intent getTextFileIntent(File file) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(file);
    intent.setDataAndType(uri, "text/plain");
    return intent;
  }

  // android获取一个用于打开音频文件的intent
  public static Intent getAudioFileIntent(File file) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("oneshot", 0);
    Uri uri = Uri.fromFile(file);
    intent.setDataAndType(uri, "audio/*");
    return intent;
  }

  // android获取一个用于打开视频文件的intent
  public static Intent getVideoFileIntent(File file) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("oneshot", 0);
    intent.putExtra("configchange", 0);
    Uri uri = Uri.fromFile(file);
    intent.setDataAndType(uri, "video/*");
    return intent;
  }

  // android获取一个用于打开CHM文件的intent
  public static Intent getChmFileIntent(File file) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(file);
    intent.setDataAndType(uri, "application/x-chm");
    return intent;
  }

  // android获取一个用于打开Word文件的intent
  public static Intent getWordFileIntent(File file) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(file);
    intent.setDataAndType(uri, "application/msword");
    return intent;
  }

  // android获取一个用于打开Excel文件的intent
  public static Intent getExcelFileIntent(File file) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(file);
    intent.setDataAndType(uri, "application/vnd.ms-excel");
    return intent;
  }

  // android获取一个用于打开PPT文件的intent
  public static Intent getPPTFileIntent(File file) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(file);
    intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
    return intent;
  }

  // android获取一个用于打开apk文件的intent
  public static Intent getApkFileIntent(File file) {
    Intent intent = new Intent();
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setAction(android.content.Intent.ACTION_VIEW);
    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
    return intent;
  }

  private static String toSafeString(Object obj) {
    if (null == obj) { return ""; }
    return obj.toString();
  }

  public static Intent getBtnClickOpenIntent(Context con, Class<? extends Activity> cls) {
    Intent i = new Intent(con, cls);
    //i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    return i;
  }

}

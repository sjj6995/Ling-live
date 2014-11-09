package com.android.storemanage.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class LogUtil {
  final static String TAG = LogUtil.class.getSimpleName();

  final static int LOGCAT_LEVEL = 2;// logcat level
  static int FILE_LOG_LEVEL = 18;// log file level, must >= LOGCAT_LEVEL

  final static int LOG_LEVEL_ERROR = 16;
  final static int LOG_LEVEL_WARN = 8;
  final static int LOG_LEVEL_INFO = 4;
  final static int LOG_LEVEL_DEBUG = 2;

  private final static boolean DEBUG = (LOGCAT_LEVEL <= LOG_LEVEL_DEBUG);
  private final static boolean INFO = (LOGCAT_LEVEL <= LOG_LEVEL_INFO);
  private final static boolean WARN = (LOGCAT_LEVEL <= LOG_LEVEL_WARN);
  private final static boolean ERROR = (LOGCAT_LEVEL <= LOG_LEVEL_ERROR);

  final static String LOG_FILE_NAME = "log.log";
  final static String LOG_ENTRY_FORMAT = "[%tF %tT][%s][%s]%s"; // [2010-01-22
                                                                // 13:39:1][D][com.bbt.sm]error
  public final static String PRO_NAME = "k-";
  static PrintStream logStream;

  static boolean initialized = false;

  private LogUtil() {
  }

  public static void d(String msg) {
    d(getTag(), msg);
  }

  public static void d(String tag, String msg) {
    if (DEBUG) {
      Log.d(tag, msg);
      if (FILE_LOG_LEVEL <= LOG_LEVEL_DEBUG) write("D", tag, msg, null);
    }
  }

  public static void d(String tag, String msg, Throwable error) {
    d(tag, msg + "\n" + getStackTraceString(error));
  }

  public static void i(String msg) {
    i(getTag(), msg);
  }

  public static void i(String tag, String msg) {
    if (INFO) {
      Log.i(tag, msg);
      if (FILE_LOG_LEVEL <= LOG_LEVEL_INFO) write("I", tag, msg, null);
    }
  }

  public static void i(String tag, String msg, Throwable error) {
    i(tag, msg + "\n" + getStackTraceString(error));
  }

  public static void w(String msg) {
    w(getTag(), msg);
  }

  public static void w(Throwable msg) {
    w(getTag(), getStackTraceString(msg));
  }

  public static void w(String tag, String msg) {
    if (WARN) {
      Log.w(tag, msg);
      if (FILE_LOG_LEVEL <= LOG_LEVEL_WARN) write("W", tag, msg, null);
    }
  }

  public static void w(String tag, String msg, Throwable error) {
    w(tag, msg + "\n" + getStackTraceString(error));
  }

  public static void e(String msg) {
    e(getTag(), msg);
  }

  public static void e(Throwable th) {
    e(getTag(), getStackTraceString(th));
  }

  public static void e(String tag, String msg) {
    if (ERROR) {
      Log.e(tag, msg);

      if (FILE_LOG_LEVEL <= LOG_LEVEL_ERROR) write("E", tag, msg, null);
    }
  }

  public static void e(String tag, String msg, Throwable error) {
    e(tag, msg + "\n" + getStackTraceString(error));
  }

  private static String getStackTraceString(Throwable error) {
    StringBuffer sb = new StringBuffer();
    Throwable er = error;
    while (er != null) {
      StackTraceElement[] stack = er.getStackTrace();
      sb.append(er.toString() + "\n");
      for (int i = 0; i < stack.length; i++) {
        sb.append("\tat " + stack[i].toString() + "\n");
      }
      er = er.getCause();
    }
    return sb.toString();
  }

  private static void write(String level, String tag, String msg, Throwable error) {
    init();
    if (logStream == null || logStream.checkError()) {
      initialized = false;
      return;
    }
    Date now = new Date();
    logStream.printf(LOG_ENTRY_FORMAT, now, now, level, tag, msg);
    logStream.println();

    if (error != null) {
      logStream.print(getStackTraceString(error));
      logStream.println();
    }
  }

  public static synchronized void init() {
    if (initialized) return;
    try {
      File sdRoot = getSDRootFile();
      if (sdRoot != null) {
        File logFile = new File(sdRoot, LOG_FILE_NAME);
        logFile.createNewFile();
        Log.d(TAG, " : Log to file : " + logFile);
        if (logStream != null) {
          logStream.close();
        }
        logStream = new PrintStream(new FileOutputStream(logFile, true), true);
        initialized = true;
      }
    } catch (Exception e) {
      Log.e(TAG, "init log stream failed", e);
    }
  }

  public static boolean isSdCardAvailable() {
    return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
  }

  public static File getSDRootFile() {
    if (isSdCardAvailable()) {
      return Environment.getExternalStorageDirectory();
    } else {
      return null;
    }
  }

  public static String getTag() {
    StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
    return PRO_NAME + "_" + stacks[4].getFileName().split("\\.")[0] + ":[" + stacks[4].getLineNumber() + "]";
  }

}

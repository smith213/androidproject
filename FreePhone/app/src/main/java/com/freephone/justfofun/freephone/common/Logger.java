package com.freephone.justfofun.freephone.common;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by jiajing.mo on 14-10-20.
 * 自定义的log工具
 */
public class Logger {

    private static String logPath;

    private boolean isLogToFile;

    private boolean isLogToConsole;

    private static final Executor THREAD_EXECUTOR = Executors.newSingleThreadExecutor();

    private String mLogFile;

    private static final Object LOCK = new Object();

    private volatile static Logger defaultLog = null;


    public static Logger getDefaultLog() {
        if (defaultLog == null) {
            synchronized (Logger.class) {
                if (defaultLog == null) {
                    defaultLog = new Logger("freephone");
                }
            }
        }

        return defaultLog;
    }

    public static Logger getLog(String logFile) {
        return new Logger(logFile);
    }

    public Logger(String logFile) {
        mLogFile = logFile;
        isLogToFile = true;
        isLogToConsole = true;
        logPath = "freephone/log";
    }

    public void i(String tag, String msg) {
        if (isLogToConsole) {
            Log.i(tag, msg);
        }
        if (isLogToFile) {
            printToFile(tag, msg);
        }
    }

    public void w(String tag, String msg) {
        if (isLogToConsole) {
            Log.w(tag, msg);
        }
        if (isLogToFile) {
            printToFile(tag, msg);
        }
    }

    public void d(String tag, String msg) {
        if (isLogToConsole) {
            Log.e(tag, msg);
        }
        if (isLogToFile) {
            printToFile(tag, msg);
        }
    }

    public void e(String tag, String msg) {
        if (isLogToConsole) {
            Log.e(tag, msg);
        }
        if (isLogToFile) {
            printToFile(tag, msg);
        }
    }

    public void e(String tag, String msg, Throwable tr) {
        if (isLogToConsole) {
            Log.e(tag, msg, tr);
        }
        if (isLogToFile) {
            printToFile(tag, msg + '\n' + Log.getStackTraceString(tr));
        }
    }

    public void writeImmediately(String tag, String msg, Throwable tr) {
        if (isLogToConsole) {
            Log.e(tag, msg, tr);
        }
        if (isLogToFile) {
            synchronized (LOCK) {
                PrintRunnable runnable = new PrintRunnable(tag, msg + '\n' + Log.getStackTraceString(tr));
                runnable.run();
            }
        }
    }

    private void printToFile(String tag, String msg) {
        if (mLogFile != null) {
            synchronized (LOCK) {
                PrintRunnable runnable = new PrintRunnable(tag, msg);
                THREAD_EXECUTOR.execute(runnable);
            }
        }
    }

    private class PrintRunnable implements Runnable {

        private String text;

        public PrintRunnable(String tag, String msg) {
            text = getTime() + " " + tag + ":" + msg + "\n";
        }


        @Override
        public void run() {
            FileLock flock = null;
            BufferedWriter buffer = null;
            try {
                final String logFile = mLogFile;
                if (logFile != null && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    File path = new File(Environment.getExternalStorageDirectory(), logPath);
                    boolean file_flag = path.exists();
                    if (!file_flag) {
                        file_flag = path.mkdirs();
                    }
                    if (file_flag) {
                        File file = new File(path, logFile + "_" + getDate() + ".txt");
                        FileOutputStream out = new FileOutputStream(file, true);
                        FileChannel fc = out.getChannel();
                        flock = fc.lock();
                        OutputStreamWriter writer = new OutputStreamWriter(out);
                        buffer = new BufferedWriter(writer);
                        buffer.write(text);
                        buffer.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (flock != null) {
                    try {
                        flock.release();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (buffer != null) {
                    try {
                        buffer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return sdf.format(new Date());
    }

    private static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd", Locale.CHINA);
        return sdf.format(new Date());
    }
}
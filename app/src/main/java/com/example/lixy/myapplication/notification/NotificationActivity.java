package com.example.lixy.myapplication.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lixy.myapplication.R;
import com.example.lixy.myapplication.Utils;


public class NotificationActivity extends Activity {

    private static final String TAG = "SevenNLS";
    private static final String TAG_PRE = "[" + NotificationActivity.class.getSimpleName() + "] ";
    private static final int EVENT_SHOW_CREATE_NOS = 0;
    private static final int EVENT_LIST_CURRENT_NOS = 1;
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private boolean isEnabledNLS = false;
    private TextView mTextView;
    private boolean isFirst = true;
    private Handler mHandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_SHOW_CREATE_NOS:
                    showCreateNotification();
                    break;
                case EVENT_LIST_CURRENT_NOS:
                    listCurrentNotification();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        mTextView = (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isEnabledNLS = isEnabled();
        logNLS("isEnabledNLS = " + isEnabledNLS);
        if (!isEnabledNLS) {
            showConfirmDialog();
        } else {
            if (isFirst) {
                isFirst = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    NotificationMonitor.toggleNotificationListenerService(this);
                }
            }
        }
    }

    public void buttonOnClicked(View view) {
        mTextView.setTextColor(Color.BLACK);
        switch (view.getId()) {
            case R.id.btnCreateNotify:
                logNLS("Create notifications...");
                createNotification(this);
                mHandler.sendMessageDelayed(mHandler.obtainMessage(EVENT_SHOW_CREATE_NOS), 50);
                break;
            case R.id.btnClearLastNotify:
                logNLS("Clear Last notification...");
                clearLastNotification();
                mHandler.sendMessageDelayed(mHandler.obtainMessage(EVENT_LIST_CURRENT_NOS), 50);
                break;
            case R.id.btnClearAllNotify:
                logNLS("Clear All notifications...");
                clearAllNotifications();
                mHandler.sendMessageDelayed(mHandler.obtainMessage(EVENT_LIST_CURRENT_NOS), 50);
                break;
            case R.id.btnListNotify:
                logNLS("List notifications...");
                listCurrentNotification();
                break;
            case R.id.btnEnableUnEnableNotify:
                logNLS("Enable/UnEnable notification...");
                openNotificationAccess();
                break;
            case R.id.btnOrientation:
                if (!Utils.getOrientationLocked(this)) {
                    Toast.makeText(this, "请开启屏幕旋转功能", Toast.LENGTH_SHORT).show();
                } else {
                    if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    } else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    } else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                }


                break;
            default:
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("=============", "onConfigurationChanged:" + newConfig.orientation);
    }

    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void createNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder ncBuilder = new NotificationCompat.Builder(context);
        ncBuilder.setContentTitle("My Notification");
        ncBuilder.setContentText("Notification Listener Service Example");
        ncBuilder.setTicker("Notification Listener Service Example");
        ncBuilder.setSmallIcon(R.mipmap.ic_launcher);
        ncBuilder.setAutoCancel(true);
        manager.notify((int) System.currentTimeMillis(), ncBuilder.build());
    }

    private void cancelNotification(Context context, boolean isCancelAll) {
        Intent intent = new Intent();
        intent.setAction(NotificationMonitor.ACTION_NLS_CONTROL);
        if (isCancelAll) {
            intent.putExtra("command", "cancel_all");
        } else {
            intent.putExtra("command", "cancel_last");
        }
        context.sendBroadcast(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private String getCurrentNotificationString() {
        String listNos = "";
        StatusBarNotification[] currentNos = NotificationMonitor.getCurrentNotifications();
        if (currentNos != null) {
            for (int i = 0; i < currentNos.length; i++) {
                listNos = i + " " + currentNos[i].getPackageName() + "\n" + listNos;
            }
        }
        return listNos;
    }

    private void listCurrentNotification() {
        String result = "";
        if (isEnabledNLS) {
            if (NotificationMonitor.getCurrentNotifications() == null) {
                logNLS("mCurrentNotifications.get(0) is null");
                return;
            }
            int n = NotificationMonitor.mCurrentNotificationsCounts;
            if (n == 0) {
                result = getResources().getString(R.string.active_notification_count_zero);
            } else {
                result = String.format(getResources().getQuantityString(R.plurals.active_notification_count_nonzero, n, n));
            }
            result = result + "\n" + getCurrentNotificationString();
            mTextView.setText(result);
        } else {
            mTextView.setTextColor(Color.RED);
            mTextView.setText("Please Enable Notification Access");
        }
    }

    private void clearLastNotification() {
        if (isEnabledNLS) {
            cancelNotification(this, false);
        } else {
            mTextView.setTextColor(Color.RED);
            mTextView.setText("Please Enable Notification Access");
        }
    }

    private void clearAllNotifications() {
        if (isEnabledNLS) {
            cancelNotification(this, true);
        } else {
            mTextView.setTextColor(Color.RED);
            mTextView.setText("Please Enable Notification Access");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void showCreateNotification() {
        if (NotificationMonitor.mPostedNotification != null) {
            String result = NotificationMonitor.mPostedNotification.getPackageName() + "\n"
                    + NotificationMonitor.mPostedNotification.getTag() + "\n"
                    + NotificationMonitor.mPostedNotification.getId() + "\n" + "\n"
                    + mTextView.getText();
            result = "Create notification:" + "\n" + result;
            mTextView.setText(result);
        }
    }

    private void openNotificationAccess() {
        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    private void showConfirmDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Please enable NotificationMonitor access")
                .setTitle("Notification Access")
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                openNotificationAccess();
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // do nothing
                            }
                        })
                .create().show();
    }

    private void logNLS(Object object) {
        Log.i(TAG, TAG_PRE + object);
    }
}


package com.example.lixy.myapplication;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;


public class ToastUtil {
    /**
     * @方法名: toastL
     * @描述: TODO(这里用一句话描述这个方法的作用)
     * @设定: @param context
     * @设定: @param message 吐司内容，为string里面的内容id
     * @返回: void 返回类型
     * @日期: 2014-5-28 上午10:03:46
     * @throws
     */
    public static void toastL(Context context, int message) {
        if (context != null) {
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
    }
    
    /**
     * 显示吐司信息（较长时间）
     * 
     * @param context
     * @param text
     */
    public static void toastL(Context context, String text) {
        if (context != null) {
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
		}
    }
    
    /**
     * 显示吐司信息（较短时间）
     * 
     * @param context
     * @param text
     */
    public static void toasts(Context context, String text) {
        if (context != null) {
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		}
    }
    
    /**
     * 显示吐司信息交给handler处理（较长时间）
     * 
     * @param context
     * @param text
     * @param handler
     */
    public static void toastLInThread(final Context context, final String text,
                                      Handler handler) {
        
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.toastL(context, text);
            }
        });
    }
    
    /**
     * 显示吐司信息交给handler处理（较短时间）
     * 
     * @param context
     * @param text
     * @param handler
     */
    public static void toastsInThread(final Context context, final String text,
                                      Handler handler) {
        
        handler.post(new Runnable() {
            
            @Override
            public void run() {
                ToastUtil.toasts(context, text);
            }
        });
    }
    
    /**
     * @方法名: toasts
     * @描述: TODO(短时间吐司)
     * @设定: @param context
     * @设定: @param message 吐司内容，为string里面的内容id
     * @返回: void 返回类型
     * @日期: 2014-5-26 上午9:55:09
     * @throws
     */
    public static void toasts(Context context, int message) {
        if (context != null) {
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
    }
}

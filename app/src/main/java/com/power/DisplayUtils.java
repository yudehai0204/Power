package com.power;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.WindowManager;

/**
 * Created by 于德海 on 2017/10/12.
 * 因变量命名较为直白，相关注释就省略了。
 *
 * @description dp sp px 转换
 */

public class DisplayUtils {

    /***
     *  px转dip
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context,float pxValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale+0.5f);
    }

    /***
     * dip转px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context,float dipValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue*scale+0.5f);
    }

    /***
     * px转 sp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context,float pxValue){
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }

    /***
     * sp 转 px
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context,float spValue){
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }

    public static int getStatubarHeight(Context context){
        Rect frame = new Rect();
        ((Activity)context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }
    public static  int getWindow_Width(Activity activity) {
        WindowManager wm = activity.getWindowManager();
//        ConfigYibaisong.window_x = ;
//        ConfigYibaisong.window_y = wm.getDefaultDisplay().getHeight();
        return wm.getDefaultDisplay().getWidth();
    }
}

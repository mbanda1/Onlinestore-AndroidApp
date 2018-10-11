package com.cyphertech.biashara.cart;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.view.MenuItem;

public class NotificationCountSetClass extends Activity {

    private static LayerDrawable icon;
    public NotificationCountSetClass() {
        //constructor
    }

    public static void setAddToCart(Context context, MenuItem item, int numMessages) {
        //BitmapDrawable iconBitmap = (BitmapDrawable) item.getIcon();
        // icon = new LayerDrawable(new Drawable[] { iconBitmap });
       // SetNotificationCount.setBadgeCount(context, icon, NotificationCountSetClass.setNotifyCount(numMessages));


         icon = (LayerDrawable) item.getIcon();
       SetNotificationCount.setBadgeCount(context, icon, NotificationCountSetClass.setNotifyCount(numMessages));


    }

    public static int setNotifyCount(int numMessages) {
        return numMessages;

    }

}

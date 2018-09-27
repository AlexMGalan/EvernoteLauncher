package com.google.android.apps.nexuslauncher;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Process;

import com.acacias.enlauncher.FastBitmapDrawable;
import com.acacias.enlauncher.ItemInfo;
import com.acacias.enlauncher.LauncherSettings;
import com.acacias.enlauncher.Utilities;
import com.acacias.enlauncher.graphics.DrawableFactory;
import com.google.android.apps.nexuslauncher.clock.DynamicClock;

public class DynamicDrawableFactory extends DrawableFactory {
    private final DynamicClock mDynamicClockDrawer;

    public DynamicDrawableFactory(Context context) {
        mDynamicClockDrawer = new DynamicClock(context);
    }

    @Override
    public FastBitmapDrawable newIcon(Bitmap icon, ItemInfo info) {
        if (info != null &&
                Utilities.ATLEAST_OREO &&
                info.itemType == LauncherSettings.Favorites.ITEM_TYPE_APPLICATION &&
                DynamicClock.DESK_CLOCK.equals(info.getTargetComponent()) &&
                info.user.equals(Process.myUserHandle())) {
            return mDynamicClockDrawer.drawIcon(icon);
        }
        return super.newIcon(icon, info);
    }
}

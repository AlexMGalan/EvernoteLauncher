package com.acacias.enlauncher.popup;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.acacias.enlauncher.AbstractFloatingView;
import com.acacias.enlauncher.InfoDropTarget;
import com.acacias.enlauncher.ItemInfo;
import com.acacias.enlauncher.Launcher;
import com.acacias.enlauncher.R;
import com.acacias.enlauncher.model.WidgetItem;
import com.acacias.enlauncher.util.PackageUserKey;
import com.acacias.enlauncher.widget.WidgetsBottomSheet;

import java.util.List;

import static com.acacias.enlauncher.userevent.nano.LauncherLogProto.Action;
import static com.acacias.enlauncher.userevent.nano.LauncherLogProto.ControlType;

/**
 * Represents a system shortcut for a given app. The shortcut should have a static label and
 * icon, and an onClickListener that depends on the item that the shortcut services.
 *
 * Example system shortcuts, defined as inner classes, include Widgets and AppInfo.
 */
public abstract class SystemShortcut extends ItemInfo {
    private final int mIconResId;
    private final int mLabelResId;

    public SystemShortcut(int iconResId, int labelResId) {
        mIconResId = iconResId;
        mLabelResId = labelResId;
    }

    public Drawable getIcon(Context context) {
        return context.getResources().getDrawable(mIconResId, context.getTheme());
    }

    public String getLabel(Context context) {
        return context.getString(mLabelResId);
    }

    public abstract View.OnClickListener getOnClickListener(final Launcher launcher,
            final ItemInfo itemInfo);

    public static class Custom extends SystemShortcut {

        public Custom() {
            super(R.drawable.ic_edit_no_shadow, R.string.action_preferences);
        }

        @Override
        public View.OnClickListener getOnClickListener(Launcher launcher, ItemInfo itemInfo) {
            return null;
        }
    }

    public static class Widgets extends SystemShortcut {

        public Widgets() {
            super(R.drawable.ic_widget, R.string.widget_button_text);
        }

        @Override
        public View.OnClickListener getOnClickListener(final Launcher launcher,
                final ItemInfo itemInfo) {
            final List<WidgetItem> widgets = launcher.getWidgetsForPackageUser(new PackageUserKey(
                    itemInfo.getTargetComponent().getPackageName(), itemInfo.user));
            if (widgets == null) {
                return null;
            }
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AbstractFloatingView.closeAllOpenViews(launcher);
                    WidgetsBottomSheet widgetsBottomSheet =
                            (WidgetsBottomSheet) launcher.getLayoutInflater().inflate(
                                    R.layout.widgets_bottom_sheet, launcher.getDragLayer(), false);
                    widgetsBottomSheet.populateAndShow(itemInfo);
                    launcher.getUserEventDispatcher().logActionOnControl(Action.Touch.TAP,
                            ControlType.WIDGETS_BUTTON, view);
                }
            };
        }
    }

    public static class AppInfo extends SystemShortcut {
        public AppInfo() {
            super(R.drawable.ic_info_no_shadow, R.string.app_info_drop_target_label);
        }

        @Override
        public View.OnClickListener getOnClickListener(final Launcher launcher,
                final ItemInfo itemInfo) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Rect sourceBounds = launcher.getViewBounds(view);
                    Bundle opts = launcher.getActivityLaunchOptions(view);
                    InfoDropTarget.startDetailsActivityForInfo(itemInfo, launcher, null, sourceBounds, opts);
                    launcher.getUserEventDispatcher().logActionOnControl(Action.Touch.TAP,
                            ControlType.APPINFO_TARGET, view);
                }
            };
        }
    }
}

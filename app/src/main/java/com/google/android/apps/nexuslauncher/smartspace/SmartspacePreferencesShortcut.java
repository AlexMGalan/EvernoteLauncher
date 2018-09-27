package com.google.android.apps.nexuslauncher.smartspace;

import android.view.View;

import com.acacias.enlauncher.AbstractFloatingView;
import com.acacias.enlauncher.ItemInfo;
import com.acacias.enlauncher.Launcher;
import com.acacias.enlauncher.R;
import com.acacias.enlauncher.popup.SystemShortcut;

class SmartspacePreferencesShortcut extends SystemShortcut {
    SmartspacePreferencesShortcut() {
        super(R.drawable.ic_smartspace_preferences, R.string.smartspace_preferences);
    }

    public View.OnClickListener getOnClickListener(final Launcher launcher, ItemInfo itemInfo) {
        return new View.OnClickListener() {
            public void onClick(final View view) {
                SmartspaceController.get(view.getContext()).cZ();
                AbstractFloatingView.closeAllOpenViews(launcher);
            }
        };
    }
}

package com.google.android.apps.nexuslauncher;

import android.content.Context;
import android.view.View;

import com.acacias.enlauncher.AbstractFloatingView;
import com.acacias.enlauncher.ItemInfo;
import com.acacias.enlauncher.Launcher;
import com.acacias.enlauncher.R;
import com.acacias.enlauncher.graphics.DrawableFactory;
import com.acacias.enlauncher.popup.SystemShortcut;

public class CustomEditShortcut extends SystemShortcut.Custom {
    public CustomEditShortcut(Context context) {
        super();
    }

    @Override
    public View.OnClickListener getOnClickListener(final Launcher launcher, final ItemInfo itemInfo) {
        if (CustomIconUtils.usingValidPack(launcher)) {
            CustomDrawableFactory factory = (CustomDrawableFactory) DrawableFactory.get(launcher);
            factory.ensureInitialLoadComplete();

            return new View.OnClickListener() {
                private boolean mOpened = false;

                @Override
                public void onClick(View view) {
                    if (!mOpened) {
                        mOpened = true;
                        AbstractFloatingView.closeAllOpenViews(launcher);
                        CustomBottomSheet cbs = (CustomBottomSheet) launcher.getLayoutInflater()
                                .inflate(R.layout.app_edit_bottom_sheet, launcher.getDragLayer(), false);
                        cbs.populateAndShow(itemInfo);
                    }
                }
            };
        }

        return null;
    }
}

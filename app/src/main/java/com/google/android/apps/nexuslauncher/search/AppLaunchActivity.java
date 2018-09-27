package com.google.android.apps.nexuslauncher.search;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.acacias.enlauncher.BaseActivity;
import com.acacias.enlauncher.ItemInfo;
import com.acacias.enlauncher.Launcher;
import com.acacias.enlauncher.LauncherAppState;
import com.acacias.enlauncher.LauncherModel;
import com.acacias.enlauncher.R;
import com.acacias.enlauncher.Utilities;
import com.acacias.enlauncher.compat.LauncherAppsCompat;
import com.acacias.enlauncher.util.ComponentKey;
import com.acacias.enlauncher.util.PackageManagerHelper;

public class AppLaunchActivity extends BaseActivity {
    private void startUri(Uri uri) {
        try {
            ComponentKey dl = AppSearchProvider.uriToComponent(uri, this);
            ItemInfo dVar = new AppItemInfoWithIcon(dl);
            if (!getPackageManager().isSafeMode() || Utilities.isSystemApp(this, dVar.getIntent())) {
                if (dl.user.equals(android.os.Process.myUserHandle())) {
                    startActivity(dVar.getIntent());
                } else {
                    LauncherAppsCompat.getInstance(this).startActivityForProfile(dl.componentName, dl.user, getIntent().getSourceBounds(), null);
                }
                View view = new View(this);
                view.setTag(dVar);
                int i = 2;
                LauncherModel.Callbacks callback = LauncherAppState.getInstance(this).getModel().getCallback();
                if (callback instanceof Launcher) {
                    i = ((Launcher) callback).getWorkspace().getState().containerType;
                }
                String predictionRank = uri.getQueryParameter("predictionRank");
                new LogContainerProvider(this, TextUtils.isEmpty(predictionRank) ? -1 : Integer.parseInt(predictionRank)).addView(view);
                return;
            }
            Toast.makeText(this, R.string.safemode_shortcut_error, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
        }

    }

    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        mDeviceProfile = LauncherAppState.getIDP(this).getDeviceProfile(this);
        final Uri data = getIntent().getData();
        if (data == null) {
            String query = getIntent().getStringExtra("query");
            if (!TextUtils.isEmpty(query)) {
                startActivity(PackageManagerHelper.getMarketSearchIntent(this, query));
            }
        } else {
            startUri(data);
        }
        finish();
    }
}

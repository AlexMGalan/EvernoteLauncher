package src.com.google.android.apps.nexuslauncher.search;

import android.content.SharedPreferences;

import com.acacias.enlauncher.AppInfo;
import com.acacias.enlauncher.IconCache;
import com.acacias.enlauncher.ItemInfoWithIcon;
import com.acacias.enlauncher.Launcher;
import com.acacias.enlauncher.LauncherAppState;
import com.acacias.enlauncher.LauncherCallbacks;
import com.acacias.enlauncher.R;
import com.acacias.enlauncher.allapps.AllAppsRecyclerView;
import com.acacias.enlauncher.allapps.AlphabeticalAppsList;
import com.acacias.enlauncher.util.ComponentKeyMapper;

import java.util.Iterator;

public class ItemInfoUpdateReceiver implements IconCache.ItemInfoUpdateReceiver, SharedPreferences.OnSharedPreferenceChangeListener {
    private final LauncherCallbacks mCallbacks;
    private final int eD;
    private final Launcher mLauncher;
    
    public ItemInfoUpdateReceiver(final Launcher launcher, final LauncherCallbacks callbacks) {
        this.mLauncher = launcher;
        this.mCallbacks = callbacks;
        this.eD = launcher.getDeviceProfile().allAppsNumCols;
    }
    
    public void di() {
        final AlphabeticalAppsList apps = ((AllAppsRecyclerView)this.mLauncher.findViewById(R.id.apps_list_view)).getApps();
        final IconCache iconCache = LauncherAppState.getInstance(this.mLauncher).getIconCache();
        final Iterator<ComponentKeyMapper<AppInfo>> iterator = this.mCallbacks.getPredictedApps().iterator();
        int n = 0;
        while (iterator.hasNext()) {
            final AppInfo app = apps.findApp(iterator.next());
            int n2;
            if (app != null) {
                if (app.usingLowResIcon) {
                    iconCache.updateIconInBackground(this, app);
                }
                n2 = n + 1;
                if (n2 >= this.eD) {
                    break;
                }
            }
            else {
                n2 = n;
            }
            n = n2;
        }
    }
    
    public void onCreate() {
        mLauncher.getSharedPrefs().registerOnSharedPreferenceChangeListener(this);
    }
    
    public void onDestroy() {
        mLauncher.getSharedPrefs().unregisterOnSharedPreferenceChangeListener(this);
    }
    
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String s) {
        if ("reflection_last_predictions".equals(s) || "pref_show_predictions".equals(s)) {
            this.di();
        }
    }
    
    public void reapplyItemInfo(final ItemInfoWithIcon itemInfoWithIcon) {
    }
}

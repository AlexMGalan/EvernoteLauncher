package src.com.google.android.apps.nexuslauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.UserHandle;

import com.acacias.enlauncher.BuildConfig;
import com.acacias.enlauncher.Launcher;
import com.acacias.enlauncher.LauncherModel;
import com.acacias.enlauncher.Utilities;
import com.acacias.enlauncher.compat.UserManagerCompat;
import com.acacias.enlauncher.util.ComponentKey;

import java.util.HashSet;
import java.util.Set;

public class CustomAppFilter extends NexusAppFilter {
    public final static String HIDE_APPS_PREF = "all_apps_hide";
    private final Context mContext;

    public CustomAppFilter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public boolean shouldShowApp(ComponentName componentName, UserHandle user) {
        if (componentName.getPackageName().equals(BuildConfig.APPLICATION_ID)) {
            return false;
        }
        if (CustomIconUtils.usingValidPack(mContext)) {
            return !isHiddenApp(mContext, new ComponentKey(componentName, user));
        }
        return super.shouldShowApp(componentName, user);
    }

    static void resetAppFilter(Context context) {
        SharedPreferences.Editor editor = Utilities.getPrefs(context).edit();
        editor.putStringSet(HIDE_APPS_PREF, new HashSet<String>());
        editor.apply();
    }

    static void setComponentNameState(Context context, ComponentKey key, boolean hidden) {
        String comp = key.toString();
        Set<String> hiddenApps = getHiddenApps(context);
        while (hiddenApps.contains(comp)) {
            hiddenApps.remove(comp);
        }
        if (hidden != CustomIconUtils.isPackProvider(context, key.componentName.getPackageName())) {
            hiddenApps.add(comp);
        }
        setHiddenApps(context, hiddenApps);

        LauncherModel model = Launcher.getLauncher(context).getModel();
        for (UserHandle user : UserManagerCompat.getInstance(context).getUserProfiles()) {
            model.onPackagesReload(user);
        }
    }

    static boolean isHiddenApp(Context context, ComponentKey key) {
        return getHiddenApps(context).contains(key.toString()) != CustomIconUtils.isPackProvider(context, key.componentName.getPackageName());
    }

    private static Set<String> getHiddenApps(Context context) {
        return new HashSet<>(Utilities.getPrefs(context).getStringSet(HIDE_APPS_PREF, new HashSet<String>()));
    }

    private static void setHiddenApps(Context context, Set<String> hiddenApps) {
        SharedPreferences.Editor editor = Utilities.getPrefs(context).edit();
        editor.putStringSet(HIDE_APPS_PREF, hiddenApps);
        editor.apply();
    }
}

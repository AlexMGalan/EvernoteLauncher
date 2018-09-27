package src.com.google.android.apps.nexuslauncher.qsb;

import com.acacias.enlauncher.Utilities;
import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;

public class OPAStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Utilities.getDevicePrefs(context).edit().putBoolean("opa_enabled", intent.getBooleanExtra("OPA_ENABLED", false)).apply();
    }
}

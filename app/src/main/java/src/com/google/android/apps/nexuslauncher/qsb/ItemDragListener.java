package src.com.google.android.apps.nexuslauncher.qsb;

import android.content.pm.LauncherActivityInfo;
import android.graphics.Rect;
import android.view.View;

import com.acacias.enlauncher.InstallShortcutReceiver;
import com.acacias.enlauncher.ItemInfo;
import com.acacias.enlauncher.ShortcutInfo;
import com.acacias.enlauncher.compat.ShortcutConfigActivityInfo;
import com.acacias.enlauncher.dragndrop.BaseItemDragListener;
import com.acacias.enlauncher.userevent.nano.LauncherLogProto;
import com.acacias.enlauncher.widget.PendingAddShortcutInfo;
import com.acacias.enlauncher.widget.PendingItemDragHelper;

public class ItemDragListener extends BaseItemDragListener {
    private final LauncherActivityInfo mActivityInfo;

    public ItemDragListener(LauncherActivityInfo activityInfo, Rect rect) {
        super(rect, rect.width(), rect.width());
        mActivityInfo = activityInfo;
    }

    protected PendingItemDragHelper createDragHelper() {
        PendingAddShortcutInfo tag = new PendingAddShortcutInfo(new ShortcutConfigActivityInfo.ShortcutConfigActivityInfoVO(mActivityInfo) {
            @Override
            public ShortcutInfo createShortcutInfo() {
                return InstallShortcutReceiver.fromActivityInfo(mActivityInfo, mLauncher);
            }
        });
        View view = new View(mLauncher);
        view.setTag(tag);
        return new PendingItemDragHelper(view);
    }

    @Override
    public void fillInLogContainerData(View v, ItemInfo info, LauncherLogProto.Target target, LauncherLogProto.Target targetParent) {
    }
}

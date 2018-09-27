package com.google.android.apps.nexuslauncher.search;

import com.acacias.enlauncher.allapps.search.AllAppsSearchBarController;
import com.acacias.enlauncher.util.ComponentKey;

import java.util.ArrayList;

class SearchResult {
    final AllAppsSearchBarController.Callbacks mCallbacks;
    final String mQuery;
    final ArrayList<ComponentKey> mApps;

    SearchResult(String query, AllAppsSearchBarController.Callbacks callbacks) {
        mApps = new ArrayList<>();
        mQuery = query;
        mCallbacks = callbacks;
    }
}

package acacias.aidlbridge;

import acacias.aidlbridge.BridgeCallback;

interface Bridge {
    oneway void setCallback(in int index, in BridgeCallback cb);
}

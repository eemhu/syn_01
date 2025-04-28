package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;

public final class ContinuationPointStub implements ContinuationPoint {
    @Override
    public Claim claim() {
        throw new UnsupportedOperationException("Stub object does not provide this method");
    }

    @Override
    public boolean isStub() {
        return true;
    }
}

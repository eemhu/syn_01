package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;

public final class ContinuationPointImpl implements ContinuationPoint {
    private final Claim claim;

    public ContinuationPointImpl(final Claim claim) {
        this.claim = claim;
    }

    @Override
    public Claim claim() {
        return claim;
    }

    @Override
    public boolean isStub() {
        return false;
    }
}

package com.teragrep.syn_01.claims.results;

import com.teragrep.syn_01.ContinuationPoint;
import com.teragrep.syn_01.ContinuationPointStub;

import java.nio.ByteBuffer;
import java.util.List;

public final class EmptyClaimResult implements ClaimResult {
    @Override
    public List<ByteBuffer> remainingBuffers() {
        return List.of();
    }

    @Override
    public Class<?> valueType() {
        return String.class;
    }

    @Override
    public Status status() {
        return Status.SUCCESS;
    }

    @Override
    public String id() {
        return "";
    }

    @Override
    public ContinuationPoint continuationPoint() {
        return new ContinuationPointStub();
    }
}

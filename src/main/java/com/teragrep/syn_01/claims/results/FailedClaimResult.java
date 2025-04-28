package com.teragrep.syn_01.claims.results;

import com.teragrep.syn_01.ContinuationPoint;

import java.nio.ByteBuffer;
import java.util.List;

public final class FailedClaimResult implements ClaimResult {
    @Override
    public List<ByteBuffer> remainingBuffers() {
        throw new UnsupportedOperationException("Failed result does not provide buffers");
    }

    @Override
    public Class<?> valueType() {
        throw new UnsupportedOperationException("Failed result does not provide valueType");
    }

    @Override
    public Status status() {
        return Status.FAILED;
    }

    @Override
    public String id() {
        throw new UnsupportedOperationException("Failed result does not provide id");
    }

    @Override
    public ContinuationPoint continuationPoint() {
        throw new UnsupportedOperationException("Failed result does not provide continuationPoint");
    }
}

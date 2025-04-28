package com.teragrep.syn_01.claims.results;

import com.teragrep.syn_01.ContinuationPoint;

import java.nio.ByteBuffer;
import java.util.List;

public final class ClaimResultImpl implements ClaimResult {
    private final List<ByteBuffer> remainingBuffers;
    private final Class<?> valueType;
    private final Status status;
    private final String id;
    private final ContinuationPoint continuationPoint;

    public ClaimResultImpl(final List<ByteBuffer> remainingBuffers, final Class<?> valueType, final Status status, final String id
    , final ContinuationPoint continuationPoint) {
        this.remainingBuffers = remainingBuffers;
        this.valueType = valueType;
        this.status = status;
        this.id = id;
        this.continuationPoint = continuationPoint;
    }

    @Override
    public List<ByteBuffer> remainingBuffers() {
        return remainingBuffers;
    }

    @Override
    public Class<?> valueType() {
        return valueType;
    }

    @Override
    public Status status() {
        return status;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public ContinuationPoint continuationPoint() {
        return continuationPoint;
    }
}

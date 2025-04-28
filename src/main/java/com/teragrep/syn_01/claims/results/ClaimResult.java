package com.teragrep.syn_01.claims.results;

import com.teragrep.syn_01.ContinuationPoint;

import java.nio.ByteBuffer;
import java.util.List;

public interface ClaimResult {
    enum Status {
        SUCCESS, IN_PROGRESS, FAILED
    }

    List<ByteBuffer> remainingBuffers();
    Class<?> valueType();
    Status status();
    String id();
    ContinuationPoint continuationPoint();
}

package com.teragrep.syn_01.claims;

import java.nio.ByteBuffer;
import java.util.List;

public final class EmptyClaim implements Claim{
    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        return this;
    }

    @Override
    public Status status() {
        return Status.SUCCESSFUL;
    }

    @Override
    public List<ByteBuffer> buffers() {
        return List.of();
    }
}

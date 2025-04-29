package com.teragrep.syn_01.claims.repeats;

import com.teragrep.syn_01.claims.Claim;

import java.nio.ByteBuffer;
import java.util.List;

public class RepeatableClaim implements Claim {
    private final Claim claim;
    private final int minCount;

    public RepeatableClaim(final Claim claim, final int minCount) {
        this.claim = claim;
        this.minCount = minCount;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Status status() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<ByteBuffer> buffers() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

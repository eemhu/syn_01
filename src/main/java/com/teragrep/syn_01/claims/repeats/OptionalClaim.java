package com.teragrep.syn_01.claims.repeats;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;

import java.nio.ByteBuffer;
import java.util.List;

public final class OptionalClaim implements Claim {
    private final Claim claim;
    public OptionalClaim(final Claim claim) {
        this.claim = claim;
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

    @Override
    public int length() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

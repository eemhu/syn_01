package com.teragrep.syn_01.claims.repeats;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;

import java.nio.ByteBuffer;

public final class OptionalClaim implements Claim {
    private final Claim claim;
    public OptionalClaim(final Claim claim) {
        this.claim = claim;
    }
    @Override
    public ClaimResult claim(final ClaimResult previous, final ByteBuffer input) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

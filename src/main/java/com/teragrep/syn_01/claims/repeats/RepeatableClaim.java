package com.teragrep.syn_01.claims.repeats;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;

import java.nio.ByteBuffer;

public class RepeatableClaim implements Claim {
    private final Claim claim;
    private final int minCount;

    public RepeatableClaim(final Claim claim, final int minCount) {
        this.claim = claim;
        this.minCount = minCount;
    }

    @Override
    public ClaimResult claim(final ClaimResult previous, final ByteBuffer input) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

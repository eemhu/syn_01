package com.teragrep.syn_01.claims.bools;

import com.teragrep.syn_01.ContinuationPointImpl;
import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.ClaimResultImpl;

import java.nio.ByteBuffer;

public final class AndClaim implements Claim {
    private final Claim first;
    private final Claim second;

    public AndClaim(final Claim first, final Claim second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ClaimResult claim(final ClaimResult previous, final ByteBuffer input) {

        final ClaimResult firstResult = first.claim(previous, input);
        if (!firstResult.status().equals(ClaimResult.Status.SUCCESS)) {
            return new ClaimResultImpl(
                    firstResult.remainingBuffers(),
                    String.class,
                    firstResult.status(),
                    "AND",
                    new ContinuationPointImpl(this)
            );
        }
        else {
            final ClaimResult secondResult = second.claim(firstResult, ByteBuffer.wrap(new byte[0]));
            return secondResult;
        }
    }
}

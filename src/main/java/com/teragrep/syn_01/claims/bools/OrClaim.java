package com.teragrep.syn_01.claims.bools;

import com.teragrep.syn_01.ContinuationPointImpl;
import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.ClaimResultImpl;

import java.nio.ByteBuffer;

public class OrClaim implements Claim {
    private final Claim first;
    private final Claim second;

    public OrClaim(final Claim first, final Claim second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ClaimResult claim(final ClaimResult previous, final ByteBuffer input) {
        // OR uses same input for both
        final ClaimResult firstResult = first.claim(previous, input);
        final ClaimResult secondResult = second.claim(previous, input);

        if (firstResult.status().equals(ClaimResult.Status.IN_PROGRESS)) {
            return new ClaimResultImpl(
                    firstResult.remainingBuffers(),
                    String.class,
                    firstResult.status(),
                    "OR",
                    new ContinuationPointImpl(this)
            );
        }
        else if (firstResult.status().equals(ClaimResult.Status.SUCCESS)) {
            
        }
        else {
            return secondResult;
        }

        throw new UnsupportedOperationException("Not yet implemented");
    }
}

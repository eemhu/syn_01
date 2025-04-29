package com.teragrep.syn_01.claims.bools;

import com.teragrep.syn_01.ContinuationPointImpl;
import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.ClaimResultImpl;

import java.nio.ByteBuffer;
import java.util.List;

public class OrClaim implements Claim {
    private final Claim first;
    private final Claim second;

    public OrClaim(final Claim first, final Claim second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        // OR uses same input for both
        final Claim firstResult = first.claim(previous, input);
        final Claim secondResult = second.claim(previous, input);

        if (firstResult.status().equals(Claim.Status.IN_PROGRESS)) {
          /*  return new ClaimImpl(
                    firstResult.buffers(),
                    String.class,
                    firstResult.status(),
                    "OR",
                    new ContinuationPointImpl(this)
            ); */
        }
        else if (firstResult.status().equals(Status.SUCCESSFUL)) {
            
        }
        else {
            return secondResult;
        }

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Status status() {
        return null;
    }

    @Override
    public List<ByteBuffer> buffers() {
        return List.of();
    }
}

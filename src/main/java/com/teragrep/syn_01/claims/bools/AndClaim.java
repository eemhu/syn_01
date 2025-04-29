package com.teragrep.syn_01.claims.bools;

import com.teragrep.syn_01.claims.Claim;

import java.nio.ByteBuffer;
import java.util.List;

public final class AndClaim implements Claim {
    private final Claim first;
    private final Claim second;
    private final Claim.Status status;
    private final List<ByteBuffer> buffers;


    public AndClaim(final Claim first, final Claim second) {
        this(first, second, Status.INITIAL, List.of());
    }

    public AndClaim(final Claim first, final Claim second, final Status status, final List<ByteBuffer> buffers) {
        this.first = first;
        this.second = second;
        this.status = status;
        this.buffers = buffers;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        Claim firstClaim = first;
        Claim secondClaim = second;


        if (firstClaim.status().equals(Status.IN_PROGRESS) || firstClaim.status().equals(Status.INITIAL)) {
            // continue from first
            firstClaim = firstClaim.claim(previous, input);
            return new AndClaim(firstClaim, secondClaim, Status.IN_PROGRESS, firstClaim.buffers());
        }
        else if (firstClaim.status().equals(Status.SUCCESSFUL)) {
            // first success, go to second
            if (secondClaim.status().equals(Status.IN_PROGRESS) || secondClaim.status().equals(Status.INITIAL)) {
                secondClaim = secondClaim.claim(previous, input);
                return new AndClaim(firstClaim, secondClaim, secondClaim.status(), secondClaim.buffers());
            }
            else if (secondClaim.status().equals(Status.SUCCESSFUL)) {
                return new AndClaim(firstClaim, secondClaim, Status.SUCCESSFUL, secondClaim.buffers());
            }
        }

        return new AndClaim(firstClaim, secondClaim, Status.FAILED, buffers);
    }

    @Override
    public Status status() {
        return status;
    }

    @Override
    public List<ByteBuffer> buffers() {
        return buffers;
    }
}

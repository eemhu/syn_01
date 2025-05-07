package com.teragrep.syn_01.claims.bools;

import com.teragrep.syn_01.claims.Claim;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class OrClaim implements Claim {
    private final Claim first;
    private final Claim second;
    private final Claim.Status status;
    private final List<ByteBuffer> buffers;

    public OrClaim(final Claim first, final Claim second) {
        this(first, second, Status.INITIAL, List.of());
    }

    public OrClaim(final Claim first, final Claim second, final Status status, final List<ByteBuffer> buffers) {
        this.first = first;
        this.second = second;
        this.status = status;
        this.buffers = buffers;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        List<ByteBuffer> newBuffers = new ArrayList<>(previous.buffers());
        newBuffers.add(input);

        // OR uses same input for both
        Claim firstClaim = first;
        Claim secondClaim = second;

        // C1 In-Progress
        if (firstClaim.status().equals(Status.INITIAL) || firstClaim.status().equals(Status.IN_PROGRESS)) {
            firstClaim = firstClaim.claim(previous, input);
            return new OrClaim(firstClaim, secondClaim, Status.IN_PROGRESS, newBuffers);
        }

        // C2 In-Progress
        if (secondClaim.status().equals(Status.INITIAL) || secondClaim.status().equals(Status.IN_PROGRESS)) {
            secondClaim = secondClaim.claim(previous, input);
            return new OrClaim(firstClaim, secondClaim, Status.IN_PROGRESS, newBuffers);
        }

        // C1&C2 Successful
        if (firstClaim.status().equals(Status.SUCCESSFUL) && secondClaim.status().equals(Status.SUCCESSFUL)) {
            // check length
            throw new UnsupportedOperationException("Length comparison not impl yet");
        }

        // C1 Only Successful
        if (firstClaim.status().equals(Status.SUCCESSFUL)) {
            return new OrClaim(firstClaim, secondClaim, Status.SUCCESSFUL, firstClaim.buffers());
        }

        // C2 Only Successful
        if (secondClaim.status().equals(Status.SUCCESSFUL)) {
            return new OrClaim(firstClaim, secondClaim, Status.SUCCESSFUL, secondClaim.buffers());
        }

        // Both failed
        return new OrClaim(firstClaim, secondClaim, Status.FAILED, List.of());
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

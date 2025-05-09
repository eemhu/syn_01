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
    private final int length;

    public OrClaim(final Claim first, final Claim second) {
        this(first, second, Status.INITIAL, List.of(), 0);
    }

    public OrClaim(final Claim first, final Claim second, final Status status, final List<ByteBuffer> buffers, final int length) {
        this.first = first;
        this.second = second;
        this.status = status;
        this.buffers = buffers;
        this.length = length;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        List<ByteBuffer> newBuffers = new ArrayList<>(this.buffers);
        newBuffers.add(input);

        // OR uses same input for both
        Claim firstClaim = first;
        Claim secondClaim = second;
        System.out.println("1st:"+firstClaim.status());
        System.out.println("2nd:"+secondClaim.status());

        // C1 In-Progress
        if (firstClaim.status().equals(Status.INITIAL) || firstClaim.status().equals(Status.IN_PROGRESS)) {
            firstClaim = firstClaim.claim(previous, input);
            return new OrClaim(firstClaim, secondClaim, Status.IN_PROGRESS, newBuffers, firstClaim.length());
        }

        // C2 In-Progress
        if (secondClaim.status().equals(Status.INITIAL) || secondClaim.status().equals(Status.IN_PROGRESS)) {
            secondClaim = secondClaim.claim(previous, input);
            return new OrClaim(firstClaim, secondClaim, Status.IN_PROGRESS, newBuffers, secondClaim.length());
        }

        // C1&C2 Successful
        if (firstClaim.status().equals(Status.SUCCESSFUL) && secondClaim.status().equals(Status.SUCCESSFUL)) {
            // check length
            System.out.println("1st claim:"+firstClaim.length());
            System.out.println("2nd claim:"+secondClaim.length());
            if (firstClaim.length() >= secondClaim.length()) {
                System.out.println("Choose 1st claim");
                return new OrClaim(firstClaim, secondClaim, Status.SUCCESSFUL, firstClaim.buffers(), firstClaim.length());
            }
            else {
                System.out.println("Choose 2nd claim");
                return new OrClaim(firstClaim, secondClaim, Status.SUCCESSFUL, secondClaim.buffers(), secondClaim.length());
            }
        }

        // C1 Only Successful
        if (firstClaim.status().equals(Status.SUCCESSFUL)) {
            return new OrClaim(firstClaim, secondClaim, Status.SUCCESSFUL, firstClaim.buffers(), firstClaim.length());
        }

        // C2 Only Successful
        if (secondClaim.status().equals(Status.SUCCESSFUL)) {
            return new OrClaim(firstClaim, secondClaim, Status.SUCCESSFUL, secondClaim.buffers(), secondClaim.length());
        }

        // Both failed
        return new OrClaim(firstClaim, secondClaim, Status.FAILED, List.of(), 0);
    }

    @Override
    public Status status() {
        return status;
    }

    @Override
    public List<ByteBuffer> buffers() {
        return buffers;
    }

    @Override
    public int length() {
        return length;
    }
}

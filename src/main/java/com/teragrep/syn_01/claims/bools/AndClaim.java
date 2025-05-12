package com.teragrep.syn_01.claims.bools;

import com.teragrep.syn_01.claims.Claim;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class AndClaim implements Claim {
    private final Claim first;
    private final Claim second;
    private final Claim.Status status;
    private final List<ByteBuffer> buffers;
    private final int length;

    public AndClaim(final Claim first, final Claim second) {
        this(first, second, Status.INITIAL, List.of(), 0);
    }

    public AndClaim(final Claim first, final Claim second, final Status status, final List<ByteBuffer> buffers, final int length) {
        this.first = first;
        this.second = second;
        this.status = status;
        this.buffers = buffers;
        this.length = length;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        List<ByteBuffer> buffers = new ArrayList<>(this.buffers);
        buffers.add(input);
        Claim firstClaim = first;
        Claim secondClaim = second;


        if (firstClaim.status().equals(Status.IN_PROGRESS) || firstClaim.status().equals(Status.INITIAL)) {
            // continue from first
            firstClaim = firstClaim.claim(previous, input);
            return new AndClaim(firstClaim, secondClaim, Status.IN_PROGRESS, firstClaim.buffers(), firstClaim.length());
        }
        else if (firstClaim.status().equals(Status.SUCCESSFUL)) {
            // first success, go to second
            if (secondClaim.status().equals(Status.IN_PROGRESS) || secondClaim.status().equals(Status.INITIAL)) {
                secondClaim = secondClaim.claim(firstClaim, input);

                return new AndClaim(firstClaim, secondClaim, secondClaim.status(), secondClaim.buffers(), firstClaim.length() + secondClaim.length());
            }
            else if (secondClaim.status().equals(Status.SUCCESSFUL)) {
                return new AndClaim(firstClaim, secondClaim, Status.SUCCESSFUL, secondClaim.buffers(), firstClaim.length() + secondClaim.length());
            }
        }


        return new AndClaim(firstClaim, secondClaim, Status.FAILED, buffers, 0);
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

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AndClaim andClaim = (AndClaim) o;
        return length == andClaim.length && Objects.equals(first, andClaim.first) && Objects.equals(second, andClaim.second) && status == andClaim.status && Objects.equals(buffers, andClaim.buffers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, status, buffers, length);
    }
}

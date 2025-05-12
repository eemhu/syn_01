package com.teragrep.syn_01.claims.repeats;

import com.teragrep.syn_01.claims.Claim;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RepeatableClaim implements Claim {
    private final Claim claim;
    private final int minCount;
    private final Claim.Status status;
    private final List<ByteBuffer> buffers;
    private final int length;
    private final int currentCount;

    public RepeatableClaim(final Claim claim, final int minCount) {
        this(claim, minCount, 0,  Status.INITIAL, List.of(), 0);
    }

    public RepeatableClaim(final Claim claim, final int minCount, final int currentCount, final Claim.Status status, final List<ByteBuffer> buffers, final int length) {
        this.claim = claim;
        this.minCount = minCount;
        this.currentCount = currentCount;
        this.status = status;
        this.buffers = buffers;
        this.length = length;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        List<ByteBuffer> newBuffers = new ArrayList<>(buffers);
        if (newBuffers.isEmpty()) {
            newBuffers.addAll(previous.buffers());
        }
        newBuffers.add(input);

        Claim c = claim;
        int newCurrentCount = currentCount;

        if (c.status().equals(Status.INITIAL) || c.status().equals(Status.IN_PROGRESS)) {
            c = c.claim(previous, input);
            return new RepeatableClaim(c, minCount, currentCount, Status.IN_PROGRESS, newBuffers, c.length());
        }
        else if (c.status().equals(Status.SUCCESSFUL)) {
            newCurrentCount++;
            c = claim.claim(previous, input);
            return new RepeatableClaim(c, minCount, newCurrentCount, Status.IN_PROGRESS, newBuffers, c.length());
        }

        // Failed
        if (newCurrentCount < minCount) {
            return new RepeatableClaim(c, minCount, newCurrentCount, Status.FAILED, newBuffers, c.length());
        }

        return new RepeatableClaim(c, minCount, newCurrentCount, Status.SUCCESSFUL, newBuffers, c.length());

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
        final RepeatableClaim that = (RepeatableClaim) o;
        return minCount == that.minCount && length == that.length && currentCount == that.currentCount && Objects.equals(claim, that.claim) && status == that.status && Objects.equals(buffers, that.buffers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(claim, minCount, status, buffers, length, currentCount);
    }
}

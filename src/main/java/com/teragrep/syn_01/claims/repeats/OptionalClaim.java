package com.teragrep.syn_01.claims.repeats;

import com.teragrep.syn_01.claims.Claim;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class OptionalClaim implements Claim {
    private final Claim claim;
    private final Claim.Status status;
    private final List<ByteBuffer> buffers;
    private final int length;

    public OptionalClaim(final Claim claim) {
        this(claim, Status.INITIAL, List.of(), 0);
    }

    public OptionalClaim(final Claim claim, final Claim.Status status, final List<ByteBuffer> buffers, final int length) {
        this.claim = claim;
        this.status = status;
        this.buffers = buffers;
        this.length = length;
    }


    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        List<ByteBuffer> newBuffers = new ArrayList<>(buffers);
        newBuffers.add(input);
        Claim c = claim;

        if (c.status() == Status.INITIAL || c.status() == Status.IN_PROGRESS) {
            c = c.claim(previous, input);
            return new OptionalClaim(c, Status.IN_PROGRESS, newBuffers, c.length());
        } else if (c.status() == Status.FAILED) {
            return new OptionalClaim(c, Status.SUCCESSFUL, newBuffers, 0);
        } else {
            return new OptionalClaim(c, Status.SUCCESSFUL, c.buffers(), c.length());
        }
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

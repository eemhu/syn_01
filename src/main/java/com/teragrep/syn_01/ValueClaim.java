package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.ClaimResultImpl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class ValueClaim implements Claim {
    private final Status status;
    private final List<ByteBuffer> buffers;
    private final int length;

    public ValueClaim() {
        this(Status.INITIAL, List.of(), 0);
    }

    public ValueClaim(final Status status, final List<ByteBuffer> buffers, final int length) {
        this.status = status;
        this.buffers = buffers;
        this.length = length;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        final List<ByteBuffer> buffers = new ArrayList<>();

        if (previous.status().equals(Status.IN_PROGRESS)) {

            buffers.addAll(previous.buffers());
        }
        buffers.add(input);

        boolean complete = false;
        boolean open = false;
        List<ByteBuffer> rv = new ArrayList<>();

        for (final ByteBuffer buffer : buffers) {
            ByteBuffer slice = buffer.slice();
            int read = 0;

            while (slice.hasRemaining()) {
                final byte b = slice.get();
                read++;

                if (open && b == '"') {
                    slice = slice.limit(read);
                    complete = true;
                    break;
                } else if (b == '"') {
                    open = true;
                }
                //TODO: Check for non-allowed characters also
                //  and fail the parsing if exists
            }

            if (complete) {
                rv.add(slice);
                break;
            }
        }


        // OK key
        if (complete) {
            return new ValueClaim(
                    Status.SUCCESSFUL, rv, -1
            );
        } else {
            // ran out of buffer, need to try again?
            return new ValueClaim(
                    Status.IN_PROGRESS, buffers, -1
            );
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

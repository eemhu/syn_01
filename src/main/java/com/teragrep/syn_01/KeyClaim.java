package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class KeyClaim implements Claim {
    private final Claim.Status status;
    private final List<ByteBuffer> buffers;
    private final int length;

    public KeyClaim() {
        this(Status.INITIAL, List.of(), 0);
    }

    public KeyClaim(final Status status, final List<ByteBuffer> buffers, final int length) {
        this.status = status;
        this.buffers = buffers;
        this.length = length;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        final List<ByteBuffer> buffers = new ArrayList<>(this.buffers);
        if (buffers.isEmpty()) {
            buffers.addAll(previous.buffers());
        }
        buffers.add(input);

        boolean complete = false;
        boolean open = false;
        List<ByteBuffer> rv = new ArrayList<>();

        for (final ByteBuffer buffer : buffers) {
            ByteBuffer slice = buffer.slice();

            while (slice.hasRemaining()) {
                final byte b = slice.get();

                if (open && b == '"') {
                    complete = true;
                    break;
                } else if (b == '"') {
                    open = true;
                } else if (!open) {
                    return new KeyClaim(
                            Status.FAILED,
                            List.of(),
                            0
                    );
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
            return new KeyClaim(Status.SUCCESSFUL, rv, -1);
        } else {
            // ran out of buffer, need to try again?
            return new KeyClaim(Status.IN_PROGRESS, buffers, -1);
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

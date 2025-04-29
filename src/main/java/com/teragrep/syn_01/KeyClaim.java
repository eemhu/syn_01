package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.ClaimResultImpl;
import com.teragrep.syn_01.claims.results.FailedClaimResult;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class KeyClaim implements Claim {
    private final Claim.Status status;
    private final List<ByteBuffer> buffers;

    public KeyClaim() {
        this(Status.INITIAL, List.of());
    }

    public KeyClaim(final Status status, final List<ByteBuffer> buffers) {
        this.status = status;
        this.buffers = buffers;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        final List<ByteBuffer> buffers = new ArrayList<>(previous.buffers());
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
                } else if (!open) {
                    return new KeyClaim(
                            Status.FAILED,
                            List.of()
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
            return new KeyClaim(Status.SUCCESSFUL, rv);
        } else {
            // ran out of buffer, need to try again?
            return new KeyClaim(Status.IN_PROGRESS, buffers);
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
}

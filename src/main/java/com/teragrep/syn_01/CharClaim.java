package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.ClaimResultImpl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class CharClaim implements Claim {
    private final char c;
    private final List<ByteBuffer> buffers;
    private final Claim.Status status;


    public CharClaim(char c) {
        this(c, List.of(), Status.INITIAL);
    }

    public CharClaim(final char c, final List<ByteBuffer> buffers, final Status status) {
        this.c = c;
        this.buffers = buffers;
        this.status = status;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        final List<ByteBuffer> buffers = new ArrayList<>();

        if (previous.status().equals(Claim.Status.IN_PROGRESS)) {
            System.out.println("Previous attempt in progress, add buffers");
            buffers.addAll(previous.buffers());
        }
        buffers.add(input);

        boolean complete = false;
        List<ByteBuffer> rv = new ArrayList<>();

        for (final ByteBuffer buffer : buffers) {
            ByteBuffer slice = buffer.slice();
            int read = 0;

            while (slice.hasRemaining()) {
                final byte b = slice.get();
                read++;

                if (b == (byte)c) {
                    slice = slice.limit(read);
                    complete = true;
                    break;
                }
                else {
                    return new CharClaim(
                            c,
                            List.of(),
                            Status.FAILED
                    );
                }
            }

            if (complete) {
                rv.add(slice);
                break;
            }
        }


        // OK key
        if (complete) {
            return new CharClaim(c, rv, Status.SUCCESSFUL);
        } else {
            // ran out of buffer, need to try again?
            return new CharClaim(c, buffers, Status.IN_PROGRESS);
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

package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.ClaimResultImpl;
import com.teragrep.syn_01.claims.results.FailedClaimResult;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class CharClaim implements Claim {
    private final char c;
    public CharClaim(char c) {
        this.c=c;
    }

    @Override
    public ClaimResult claim(final ClaimResult previous, final ByteBuffer input) {
        final List<ByteBuffer> buffers = new ArrayList<>();

        if (previous.status().equals(ClaimResult.Status.IN_PROGRESS)) {
            System.out.println("Previous attempt in progress, add buffers");
            buffers.addAll(previous.remainingBuffers());
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
                    return new FailedClaimResult();
                }
            }

            if (complete) {
                rv.add(slice);
                break;
            }
        }


        // OK key
        if (complete) {
            return new ClaimResultImpl(
                    rv,
                    char.class,
                    ClaimResult.Status.SUCCESS,
                    this.getClass().getSimpleName(),
                    new ContinuationPointImpl(this)
            );
        } else {
            // ran out of buffer, need to try again?
            return new ClaimResultImpl(
                    buffers,
                    char.class,
                    ClaimResult.Status.IN_PROGRESS,
                    this.getClass().getSimpleName(),
                    new ContinuationPointImpl(this)
            );
        }
    }
}

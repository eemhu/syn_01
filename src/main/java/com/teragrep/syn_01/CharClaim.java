package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CharClaim implements Claim {
    private final char c;
    private final List<ByteBuffer> buffers;
    private final Claim.Status status;
    private final int length;


    public CharClaim(char c) {
        this(c, List.of(), Status.INITIAL, 0);
    }

    public CharClaim(final char c, final List<ByteBuffer> buffers, final Status status, final int length) {
        this.c = c;
        this.buffers = buffers;
        this.status = status;
        this.length = length;
    }

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {

      /*  if (previous.status().equals(Claim.Status.IN_PROGRESS)) {
            System.out.println("Previous attempt in progress, add buffers");

        }*/
        final List<ByteBuffer> buffers = new ArrayList<>(this.buffers);
        if (buffers.isEmpty()) {
            buffers.addAll(previous.buffers());
        }
        buffers.add(input);

        boolean complete = false;
        List<ByteBuffer> rv = new ArrayList<>();

        for (final ByteBuffer buffer : buffers) {
            ByteBuffer slice = buffer.slice();

            while (slice.hasRemaining()) {
                final byte b = slice.get();
                //System.out.println("Read char " + (char) b);

                if (b == (byte)c) {
                    complete = true;
                    break;
                }
                else {
                    return new CharClaim(
                            c,
                            buffers,
                            Status.FAILED,
                            0
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
            return new CharClaim(c, rv, Status.SUCCESSFUL, 1);
        } else {
            // ran out of buffer, need to try again?
            return new CharClaim(c, buffers, Status.IN_PROGRESS, 0);
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

package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;

import java.nio.ByteBuffer;
import java.util.List;

public final class KeyValuePairClaim implements Claim {

    public KeyValuePairClaim() {}

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Status status() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ByteBuffer> buffers() {
        throw new UnsupportedOperationException();
    }
}

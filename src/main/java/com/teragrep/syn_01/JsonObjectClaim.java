package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.ClaimResultImpl;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class JsonObjectClaim implements Claim {

    @Override
    public Claim claim(final Claim previous, final ByteBuffer input) {
        return null;
    }

    @Override
    public Status status() {
        return null;
    }

    @Override
    public List<ByteBuffer> buffers() {
        return List.of();
    }

    @Override
    public int length() {
        return 0;
    }
}

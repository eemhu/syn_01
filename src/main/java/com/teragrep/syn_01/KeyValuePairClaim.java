package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;

import java.nio.ByteBuffer;

public final class KeyValuePairClaim implements Claim {

    public KeyValuePairClaim() {}

    @Override
    public ClaimResult claim(final ClaimResult previous, final ByteBuffer input) {

        KeyClaim keyClaim = new KeyClaim();

        throw new RuntimeException();
    }
}

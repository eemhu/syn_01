package com.teragrep.syn_01.claims;

import com.teragrep.syn_01.claims.results.ClaimResult;

import java.nio.ByteBuffer;

public interface Claim {
    ClaimResult claim(ClaimResult previous, ByteBuffer input);
}

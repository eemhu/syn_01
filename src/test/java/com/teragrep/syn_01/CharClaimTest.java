package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.EmptyClaimResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class CharClaimTest {
    @Test
    void testCharClaim() {
        CharClaim charClaim = new CharClaim('a');
        ClaimResult cr = charClaim.claim(new EmptyClaimResult(), ByteBuffer.wrap("a".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(ClaimResult.Status.SUCCESS, cr.status());
    }

    @Test
    void testCharClaimFailed() {
        CharClaim charClaim = new CharClaim('a');
        ClaimResult cr = charClaim.claim(new EmptyClaimResult(), ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(ClaimResult.Status.FAILED, cr.status());
    }
}

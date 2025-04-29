package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.EmptyClaim;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class CharClaimTest {
    @Test
    void testCharClaim() {
        CharClaim charClaim = new CharClaim('a');
        Claim cr = charClaim.claim(new EmptyClaim(), ByteBuffer.wrap("a".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(Claim.Status.SUCCESSFUL, cr.status());
    }

    @Test
    void testCharClaimFailed() {
        CharClaim charClaim = new CharClaim('a');
        Claim cr = charClaim.claim(new EmptyClaim(), ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(Claim.Status.FAILED, cr.status());
    }
}

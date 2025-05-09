package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.EmptyClaim;
import com.teragrep.syn_01.claims.bools.AndClaim;
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

    @Test
    void testTwoCharClaims() {
        CharClaim charClaim = new CharClaim('a');
        CharClaim charClaim2 = new CharClaim('b');
        Claim cr = new AndClaim(charClaim, charClaim2);
        cr = cr.claim(new EmptyClaim(), ByteBuffer.wrap("ab".getBytes(StandardCharsets.UTF_8)));
        cr = cr.claim(new EmptyClaim(), ByteBuffer.wrap("ab".getBytes(StandardCharsets.UTF_8)));
        cr = cr.claim(cr, ByteBuffer.wrap("c".getBytes(StandardCharsets.UTF_8)));
        // 'b' gets skipped
        Assertions.assertEquals(Claim.Status.SUCCESSFUL, cr.status());



    }
}

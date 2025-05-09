package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.EmptyClaim;
import com.teragrep.syn_01.claims.repeats.OptionalClaim;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class OptionalClaimTest {
    @Test
    void testSuccess() {
        Claim c1 = new CharClaim('a');
        Claim optionalClaim = new OptionalClaim(c1);
        ByteBuffer buf = ByteBuffer.wrap("a".getBytes(StandardCharsets.UTF_8));

        optionalClaim = optionalClaim.claim(new EmptyClaim(), buf);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, optionalClaim.status());
        optionalClaim = optionalClaim.claim(optionalClaim, buf);
        Assertions.assertEquals(Claim.Status.SUCCESSFUL, optionalClaim.status());
        // successful optional claim returns with position=1 (past 'a')
        Assertions.assertEquals(1, optionalClaim.buffers().size());
        Assertions.assertEquals(1, optionalClaim.buffers().get(0).position());
    }

    @Test
    void testFailure() {
        Claim c1 = new CharClaim('a');
        Claim optionalClaim = new OptionalClaim(c1);
        ByteBuffer buf = ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8));
        optionalClaim = optionalClaim.claim(new EmptyClaim(), buf);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, optionalClaim.status());
        optionalClaim = optionalClaim.claim(optionalClaim, buf);
        Assertions.assertEquals(Claim.Status.SUCCESSFUL, optionalClaim.status());
        // failed optional claim returns with position=0 (at beginning)
        Assertions.assertEquals(2, optionalClaim.buffers().size());
        Assertions.assertEquals(0, optionalClaim.buffers().get(0).position());
    }

    @Test
    void testLeftInProgress() {
        Claim c1 = new CharClaim('a');
        Claim optionalClaim = new OptionalClaim(c1);
        ByteBuffer buf = ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8));
        optionalClaim = optionalClaim.claim(new EmptyClaim(), buf);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, optionalClaim.status());
        optionalClaim = optionalClaim.claim(optionalClaim, buf);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, optionalClaim.status());
        // failed optional claim returns with position=0 (at beginning)
        Assertions.assertEquals(2, optionalClaim.buffers().size());
        Assertions.assertEquals(0, optionalClaim.buffers().get(0).position());
    }
}

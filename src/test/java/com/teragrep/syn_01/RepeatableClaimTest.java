package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.EmptyClaim;
import com.teragrep.syn_01.claims.repeats.RepeatableClaim;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class RepeatableClaimTest {
    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(RepeatableClaim.class).verify();
    }
    @Test
    public void testIdealCase() {
        Claim claim = new CharClaim('a');
        Claim repeatClaim = new RepeatableClaim(claim, 3);
        ByteBuffer buf = ByteBuffer.wrap("aaa".getBytes(StandardCharsets.UTF_8));
        repeatClaim = repeatClaim.claim(new EmptyClaim(), buf);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap(new byte[0]));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap(new byte[0]));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap(new byte[0]));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(Claim.Status.SUCCESSFUL, repeatClaim.status());
    }

    @Test
    public void testMoreThanMinCount() {
        Claim claim = new CharClaim('a');
        Claim repeatClaim = new RepeatableClaim(claim, 2);
        ByteBuffer buf = ByteBuffer.wrap("aaa".getBytes(StandardCharsets.UTF_8));
        repeatClaim = repeatClaim.claim(new EmptyClaim(), buf);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap(new byte[0]));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap(new byte[0]));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap(new byte[0]));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(Claim.Status.SUCCESSFUL, repeatClaim.status());
    }

    @Test
    public void testLessThanMinCount() {
        Claim claim = new CharClaim('a');
        Claim repeatClaim = new RepeatableClaim(claim, 2);
        ByteBuffer buf = ByteBuffer.wrap("a".getBytes(StandardCharsets.UTF_8));
        repeatClaim = repeatClaim.claim(new EmptyClaim(), buf);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap(new byte[0]));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap(new byte[0]));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap(new byte[0]));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(Claim.Status.FAILED, repeatClaim.status());
    }

    @Test
    public void testUnexpectedInput() {
        Claim claim = new CharClaim('a');
        Claim repeatClaim = new RepeatableClaim(claim, 2);
        ByteBuffer buf = ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8));
        repeatClaim = repeatClaim.claim(new EmptyClaim(), buf);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, repeatClaim.status());
        repeatClaim = repeatClaim.claim(repeatClaim, ByteBuffer.wrap(new byte[0]));
        Assertions.assertEquals(Claim.Status.FAILED, repeatClaim.status());
    }
}

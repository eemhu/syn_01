package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.EmptyClaim;
import com.teragrep.syn_01.claims.bools.AndClaim;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class AndClaimTest {

    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(AndClaim.class).verify();
    }

    @Test
    void testAndClaim() {
        ByteBuffer b0 = ByteBuffer.wrap("\"key\"".getBytes(StandardCharsets.UTF_8));
        ByteBuffer b1 = ByteBuffer.wrap("\"key2\"".getBytes(StandardCharsets.UTF_8));

        Claim andClaim = new AndClaim(new KeyClaim(), new KeyClaim());
        Claim cr1 = andClaim.claim(new EmptyClaim(), b0);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, cr1.status());
        Claim cr2 = cr1.claim(cr1, b1);
        Assertions.assertEquals(Claim.Status.SUCCESSFUL, cr2.status());
    }

    @Test
    void testAndClaimFailed() {
        ByteBuffer b0 = ByteBuffer.wrap("\"key\"".getBytes(StandardCharsets.UTF_8));
        ByteBuffer b1 = ByteBuffer.wrap("FAIL".getBytes(StandardCharsets.UTF_8));

        Claim andClaim = new AndClaim(new KeyClaim(), new KeyClaim());
        Claim cr1 = andClaim.claim(new EmptyClaim(), b0);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, cr1.status());
        Claim cr2 = cr1.claim(cr1, b1);
        Assertions.assertEquals(Claim.Status.FAILED, cr2.status());
    }

    @Test
    void testAndClaimSuccessWithTwoBuffers() {
        ByteBuffer b0 = ByteBuffer.wrap("\"ke".getBytes(StandardCharsets.UTF_8));
        ByteBuffer b1 = ByteBuffer.wrap("y\"".getBytes(StandardCharsets.UTF_8));
        ByteBuffer b2 = ByteBuffer.wrap("\"key2\"".getBytes(StandardCharsets.UTF_8));

        Claim andClaim = new AndClaim(new KeyClaim(), new KeyClaim());
        Claim cr1 = andClaim.claim(new EmptyClaim(), b0);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, cr1.status());
        Claim cr2 = cr1.claim(cr1, b1);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, cr2.status());
        Claim cr3 = cr2.claim(cr2, b2);
        Assertions.assertEquals(Claim.Status.SUCCESSFUL, cr3.status());
    }

    @Test
    void testAndClaimSuccessWithTwoBuffersForBoth() {
        ByteBuffer b0 = ByteBuffer.wrap("\"ke".getBytes(StandardCharsets.UTF_8));
        ByteBuffer b1 = ByteBuffer.wrap("y\"".getBytes(StandardCharsets.UTF_8));
        ByteBuffer b2 = ByteBuffer.wrap("\"key".getBytes(StandardCharsets.UTF_8));
        ByteBuffer b3 = ByteBuffer.wrap("2\"".getBytes(StandardCharsets.UTF_8));

        Claim andClaim = new AndClaim(new KeyClaim(), new KeyClaim());
        Claim cr1 = andClaim.claim(new EmptyClaim(), b0);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, cr1.status());
        Claim cr2 = cr1.claim(cr1, b1);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, cr2.status());
        Claim cr3 = cr2.claim(cr2, b2);
        Assertions.assertEquals(Claim.Status.IN_PROGRESS, cr3.status());
        Claim cr4 = cr3.claim(cr3, b3);
        Assertions.assertEquals(Claim.Status.SUCCESSFUL, cr4.status());
    }
}

package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.bools.AndClaim;
import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.EmptyClaimResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class AndClaimTest {
    @Test
    void testAndClaim() {
        ByteBuffer b0 = ByteBuffer.wrap("\"key\"".getBytes(StandardCharsets.UTF_8));
        ByteBuffer b1 = ByteBuffer.wrap("\"key2\"".getBytes(StandardCharsets.UTF_8));

        Claim andClaim = new AndClaim(new KeyClaim(), new KeyClaim());
        ClaimResult cr1 = andClaim.claim(new EmptyClaimResult(), b0);
        Assertions.assertEquals(ClaimResult.Status.IN_PROGRESS, cr1.status());
        ClaimResult cr2 = cr1.continuationPoint().claim().claim(cr1, b1);
        Assertions.assertEquals(ClaimResult.Status.SUCCESS, cr2.status());
    }

    @Test
    void testAndClaimFailed() {
        ByteBuffer b0 = ByteBuffer.wrap("\"key\"".getBytes(StandardCharsets.UTF_8));
        ByteBuffer b1 = ByteBuffer.wrap("FAIL".getBytes(StandardCharsets.UTF_8));

        Claim andClaim = new AndClaim(new KeyClaim(), new KeyClaim());
        ClaimResult cr1 = andClaim.claim(new EmptyClaimResult(), b0);
        Assertions.assertEquals(ClaimResult.Status.IN_PROGRESS, cr1.status());
        ClaimResult cr2 = cr1.continuationPoint().claim().claim(cr1, b1);
        Assertions.assertEquals(ClaimResult.Status.FAILED, cr2.status());
    }
}

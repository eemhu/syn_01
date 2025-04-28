package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.ClaimResultImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public final class KeyClaimTest {

    @Test
    void testKeyClaim() {
        KeyClaim keyClaim = new KeyClaim();
        ClaimResult prev = new ClaimResultImpl(
                Collections.emptyList(),
                String.class,
                ClaimResult.Status.SUCCESS,
                "prev",
                new ContinuationPointImpl(keyClaim)
        );
        ClaimResult keyClaimResult = keyClaim.claim(prev, ByteBuffer.wrap("\"key\"".getBytes(StandardCharsets.UTF_8)));


        Assertions.assertEquals(ClaimResult.Status.SUCCESS, keyClaimResult.status());
        Assertions.assertEquals(1, keyClaimResult.remainingBuffers().size());
        Assertions.assertEquals(5, keyClaimResult.remainingBuffers().get(0).limit());
    }

    @Test
    void testKeyClaimNotComplete() {
        KeyClaim keyClaim = new KeyClaim();
        ClaimResult prev = new ClaimResultImpl(
                Collections.emptyList(),
                String.class,
                ClaimResult.Status.SUCCESS,
                "prev",
                new ContinuationPointImpl(keyClaim)
        );
        ClaimResult keyClaimResult = keyClaim.claim(prev, ByteBuffer.wrap("\"key".getBytes(StandardCharsets.UTF_8)));

        Assertions.assertEquals(ClaimResult.Status.IN_PROGRESS, keyClaimResult.status());
        Assertions.assertEquals(1, keyClaimResult.remainingBuffers().size());
        Assertions.assertEquals(4, keyClaimResult.remainingBuffers().get(0).limit());

        keyClaimResult = keyClaim.claim(keyClaimResult, ByteBuffer.wrap("\"".getBytes(StandardCharsets.UTF_8)));

        Assertions.assertEquals(ClaimResult.Status.SUCCESS, keyClaimResult.status());
        Assertions.assertEquals(1, keyClaimResult.remainingBuffers().size());
        Assertions.assertEquals(1, keyClaimResult.remainingBuffers().get(0).limit());
    }
}

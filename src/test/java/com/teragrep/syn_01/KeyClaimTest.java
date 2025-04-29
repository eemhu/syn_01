package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.EmptyClaim;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class KeyClaimTest {

    @Test
    void testKeyClaim() {
        KeyClaim keyClaim = new KeyClaim();
        Claim keyClaimResult = keyClaim.claim(new EmptyClaim(), ByteBuffer.wrap("\"key\"".getBytes(StandardCharsets.UTF_8)));

        Assertions.assertEquals(Claim.Status.SUCCESSFUL, keyClaimResult.status());
        Assertions.assertEquals(1, keyClaimResult.buffers().size());
        Assertions.assertEquals(5, keyClaimResult.buffers().get(0).limit());
    }

    @Test
    void testKeyClaimNotComplete() {
        KeyClaim keyClaim = new KeyClaim();

        Claim keyClaimResult = keyClaim.claim(new EmptyClaim(), ByteBuffer.wrap("\"key".getBytes(StandardCharsets.UTF_8)));

        Assertions.assertEquals(Claim.Status.IN_PROGRESS, keyClaimResult.status());
        Assertions.assertEquals(1, keyClaimResult.buffers().size());
        Assertions.assertEquals(4, keyClaimResult.buffers().get(0).limit());

        keyClaimResult = keyClaim.claim(keyClaimResult, ByteBuffer.wrap("\"".getBytes(StandardCharsets.UTF_8)));

        Assertions.assertEquals(Claim.Status.SUCCESSFUL, keyClaimResult.status());
        Assertions.assertEquals(1, keyClaimResult.buffers().size());
        Assertions.assertEquals(1, keyClaimResult.buffers().get(0).limit());
    }
}

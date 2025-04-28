package com.teragrep.syn_01;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class JsonObjectClaimTest {

    @Test
    void testJsonObjectClaim() {
        ByteBuffer buffer = ByteBuffer.wrap("{}".getBytes(StandardCharsets.UTF_8));
        JsonObjectClaim claim = new JsonObjectClaim();
       // ClaimResult cr = claim.claim(buffer);

    }

}

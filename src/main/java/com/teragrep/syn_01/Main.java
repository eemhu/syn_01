package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.EmptyClaimResult;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.wrap("\"".getBytes(StandardCharsets.UTF_8));
        KeyClaim claim = new KeyClaim();
        ClaimResult keyClaimResult = claim.claim(new EmptyClaimResult(), buffer);


    }
}

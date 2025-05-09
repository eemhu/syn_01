package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.EmptyClaim;
import com.teragrep.syn_01.claims.bools.AndClaim;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class PerformanceTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000})
    void test(int iterations) {
        ByteBuffer buf = ByteBuffer.wrap("\"".concat("a".repeat(100_000)).concat("\"").getBytes(StandardCharsets.UTF_8));

        long total = 0L;
        Claim c = new EmptyClaim();
        for (int i = 0; i < iterations; i++) {
            c = new AndClaim(c, new KeyClaim());
            long start = System.nanoTime();
            c = c.claim(c, buf);
            long end = System.nanoTime();
            long diff = end - start;
            total += diff;
        }

        System.out.println("Iterations: " + iterations);
        System.out.println("Claims took total of " + total/1_000_000L + " ms");
        System.out.println("Buffer total size " + buf.capacity()*iterations + " bytes");
        System.out.println();

    }
}

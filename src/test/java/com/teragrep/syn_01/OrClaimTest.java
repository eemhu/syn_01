package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.EmptyClaim;
import com.teragrep.syn_01.claims.bools.AndClaim;
import com.teragrep.syn_01.claims.bools.OrClaim;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class OrClaimTest {

    @Test
    void testFirstSuccess() {
        Claim c1 = new CharClaim('a');
        Claim c2 = new CharClaim('b');
        ByteBuffer b0 = ByteBuffer.wrap("a".getBytes(StandardCharsets.UTF_8));

        Claim orClaim = new OrClaim(c1, c2);
        // Testing C1
        Claim result = orClaim.claim(new EmptyClaim(), b0);
        // C1 success
        result = result.claim(result, ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8)));
        // Testing C2, fails. C1 wins.
        result = result.claim(result, ByteBuffer.wrap(new byte[0]));

        Assertions.assertEquals(Claim.Status.SUCCESSFUL, result.status());
    }

    @Test
    void testSecondSuccess() {
        Claim c1 = new CharClaim('a');
        Claim c2 = new CharClaim('b');
        ByteBuffer b0 = ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8));
        Claim orClaim = new OrClaim(c1, c2);
        Claim result = orClaim.claim(new EmptyClaim(), b0);
        result = result.claim(result, ByteBuffer.wrap("a".getBytes(StandardCharsets.UTF_8)));
        result = result.claim(result, ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(Claim.Status.SUCCESSFUL, result.status());
    }

    @Test
    void testBothSuccess() {
        Claim c1 = new CharClaim('a');
        Claim c2 = new CharClaim('a');
        ByteBuffer b0 = ByteBuffer.wrap("a".getBytes(StandardCharsets.UTF_8));
        Claim orClaim = new OrClaim(c1, c2);
        Claim result = orClaim.claim(new EmptyClaim(), b0);
        result = result.claim(result, ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8)));
        result = result.claim(result, ByteBuffer.wrap("a".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(Claim.Status.SUCCESSFUL, result.status());
    }

    @Test
    void testBothFailure() {
        Claim c1 = new CharClaim('a');
        Claim c2 = new CharClaim('b');
        ByteBuffer b0 = ByteBuffer.wrap("c".getBytes(StandardCharsets.UTF_8));
        Claim orClaim = new OrClaim(c1, c2);
        Claim result = orClaim.claim(new EmptyClaim(), b0);
        result = result.claim(result, ByteBuffer.wrap("a".getBytes(StandardCharsets.UTF_8)));
        result = result.claim(result, ByteBuffer.wrap("b".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertEquals(Claim.Status.FAILED, result.status());
    }

    @Test
    void testBothSuccessButSecondLongerLength() {

        Claim c1 = new CharClaim('a'); // "a"
        Claim c2 = new OrClaim(new CharClaim('a'), new AndClaim(new CharClaim('a'), new CharClaim('b'))); // "a" OR ("a" AND "b")

        ByteBuffer b0 = ByteBuffer.wrap("ab".getBytes(StandardCharsets.UTF_8)); // input= "ab"

        Claim orClaim = new OrClaim(c1, c2); // "a" OR ("a" OR ("a" AND "b"))
        Claim result = orClaim.claim(new EmptyClaim(), b0); // test "a" should be OK
        result = result.claim(result, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8))); // test OR
        result = result.claim(result, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8))); // test "a" OK
        result = result.claim(result, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8))); // test "a" OK
        result = result.claim(result, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8))); // test "b" OK
        result = result.claim(result, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8))); // second OR OK?

        Assertions.assertEquals(Claim.Status.SUCCESSFUL, result.status());
    }

    @Test
    void testBothSuccessButFirstLongerLength() {
        Claim c1 = new OrClaim(new CharClaim('a'), new AndClaim(new CharClaim('a'), new CharClaim('b'))); // "a" OR ("a" AND "b")
        Claim c2 = new CharClaim('a'); // "a"

        ByteBuffer b0 = ByteBuffer.wrap("ab".getBytes(StandardCharsets.UTF_8)); // input= "ab"

        Claim orClaim = new OrClaim(c1, c2); // ("a" OR ("a" AND "b") OR "a")
        Claim result = orClaim.claim(new EmptyClaim(), b0); // test "a" should be OK
        result = result.claim(result, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8))); // test OR
        result = result.claim(result, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8))); // test "a" OK
        result = result.claim(result, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8))); // test "a" OK
        result = result.claim(result, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8))); // test "b" OK
        result = result.claim(result, ByteBuffer.wrap("".getBytes(StandardCharsets.UTF_8))); // second OR OK?

        Assertions.assertEquals(Claim.Status.SUCCESSFUL, result.status());
    }
}

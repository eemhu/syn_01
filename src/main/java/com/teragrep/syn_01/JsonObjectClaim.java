package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;
import com.teragrep.syn_01.claims.results.ClaimResult;
import com.teragrep.syn_01.claims.results.ClaimResultImpl;

import java.nio.ByteBuffer;
import java.util.Collections;

public final class JsonObjectClaim implements Claim {


    @Override
    public ClaimResult claim(final ClaimResult previous, final ByteBuffer input) {
        // { KeyValuePair(s) }

        byte b = input.get();
        if (b != '{') {
            return new ClaimResultImpl(
                    Collections.emptyList(),
                    null,
                    ClaimResult.Status.FAILED,
                    "LBRACE",
                    new ContinuationPointImpl(this)
            );
        }



        return null;
    }
}

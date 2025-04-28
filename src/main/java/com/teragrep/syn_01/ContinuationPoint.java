package com.teragrep.syn_01;

import com.teragrep.syn_01.claims.Claim;

public interface ContinuationPoint extends Stubable {
    Claim claim();
}

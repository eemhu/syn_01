package com.teragrep.syn_01.claims;

import java.nio.ByteBuffer;
import java.util.List;

public interface Claim {
    enum Status {
        INITIAL,
        IN_PROGRESS,
        SUCCESSFUL,
        FAILED
    }
    Claim claim(Claim previous, ByteBuffer input);
    Status status();
    List<ByteBuffer> buffers();
    int length();
}

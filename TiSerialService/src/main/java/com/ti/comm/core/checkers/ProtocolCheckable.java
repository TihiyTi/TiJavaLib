package com.ti.comm.core.checkers;

import java.util.concurrent.ConcurrentLinkedDeque;

public interface ProtocolCheckable {
    boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque);
}

package com.ti.checkers;

import java.util.concurrent.ConcurrentLinkedDeque;

public interface ProtocolCheckable {
    boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque);
}

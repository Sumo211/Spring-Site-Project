package com.leon.infrastructure.jpa;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

public class SnowFlakeGenerator implements UniqueIdGenerator<Long> {

    private static final AtomicInteger counter = new AtomicInteger(new SecureRandom().nextInt());

    private static final int TOTAL_BITS = 64;

    private static final int EPOCH_BITS = 42;

    private static final int MACHINE_ID_BITS = 10;

    private static final int MACHINE_ID;

    private static final int LOWER_ORDER_TEN_BITS = 0x3FF;

    private static final int LOWER_ORDER_TWELVE_BITS = 0xFFF;

    static {
        MACHINE_ID = createMachineId();
    }

    @Override
    public Long getNextUniqueId() {
        long currentMs = Instant.now().toEpochMilli();
        long id = currentMs << (TOTAL_BITS - EPOCH_BITS);
        id |= (MACHINE_ID << (TOTAL_BITS - EPOCH_BITS - MACHINE_ID_BITS));
        id |= (getNextCounter() & LOWER_ORDER_TWELVE_BITS);
        return id;
    }

    private long getNextCounter() {
        return counter.incrementAndGet();
    }

    private static int createMachineId() {
        int machineId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for (byte element : mac) {
                        sb.append(String.format("%02X", element));
                    }
                }
            }

            machineId = sb.toString().hashCode();
        } catch (SocketException ex) {
            machineId = (new SecureRandom().nextInt());
        }

        machineId = machineId & LOWER_ORDER_TEN_BITS;
        return machineId;
    }

}

package com.ti;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileServiceTest {
    private static final String TEST_FILE = "SerialService 2017-Oct-03 18-30-24.bin";
    @Test
    public void readBytesTest() throws Exception {
        Path file = Paths.get(this.getClass().getResource(TEST_FILE).toURI());
        FileService service = new FileService(file.toString());
        ByteBuffer buffer = service.readBytes();
        byte[] expect = new byte[10];
        byte[] actual = new byte[]{-107, 3, 0, 0, -75, -12, 2, 0, -101, 12};
        buffer.rewind();
        buffer.get(expect);

        Assert.assertArrayEquals(expect, actual);
    }

}
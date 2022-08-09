package com.ti;

import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class FileReadLineServiceTest {
    private static final String TEST_FILE = "Wave.csv";

    @Test
    public void fileReadLineServiceTest() throws URISyntaxException {

        Path file = Paths.get(this.getClass().getResource(TEST_FILE).toURI());
        FileReadLineService service = new FileReadLineService();
        service.setSkipHead(true);
        service.read(file);

        List<List<Integer>> ints = service.getDataAsInteger();
        ints.forEach(x-> System.out.println(x.toString()));
        System.out.println("test");
    }

}
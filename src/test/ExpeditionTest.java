package test;
import main.Expedition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExpeditionTest {
    private Expedition expedition;
    private List<String[]> expectedReadResult;

    @BeforeEach
    void setUp() { // Create object before compilation
        expedition = new Expedition();
        expectedReadResult = new ArrayList<>();
        expectedReadResult.add(new String[]{"Mineral", "Quantity"});
        expectedReadResult.add(new String[]{"Chromium", "12"});
        expectedReadResult.add(new String[]{"Gold", "3"});
        expectedReadResult.add(new String[]{"Titanium", "9"});

    }

    @Test
    void testReadMineralDataWithoutException() throws IOException {

        List<String[]> actualReadResult = expedition.readMineralData("../expeditions.csv");
        for(int i = 0; i < actualReadResult.size(); i++) {
            Assertions.assertEquals(expectedReadResult.get(i)[0], actualReadResult.get(i)[0]);
            Assertions.assertEquals(expectedReadResult.get(i)[1], actualReadResult.get(i)[1]);
        }
    }

    @Test
    void testReadMineralDataWithException() {
        Exception exception = Assertions.assertThrows(FileNotFoundException.class, () -> {
            List<String[]> actualReadResult = expedition.readMineralData("wrong file path");
        });

        String expectedMessage = "wrong file path";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testWriteDataWithoutException() throws IOException {
        boolean writeData = expedition.writeData("../testWriteData.csv", expectedReadResult);
        Assertions.assertTrue(writeData);
    }

    @Test
    void testWriteMineralDataWithException() {
        Exception exception = Assertions.assertThrows(IOException.class, () -> {
            expedition.writeData("/no/such/path", expectedReadResult);
        });

        String expectedMessage = "/no/such/path";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}

package org.starmap.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.starmap.model.Constellation;
import org.starmap.model.Star;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertLinesMatch;

public class DataWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void testSaveDataToFile() throws IOException {
        // Prepare test data
        Path testFilePath = tempDir.resolve("test_output.json");
        List<Star> stars = new ArrayList<>();
        stars.add(new Star("Sirius", 100, 200, 1.46));
        stars.add(new Star("Canopus", 150, 250, 0.72));

        List<Constellation> constellations = new ArrayList<>();
        Constellation taurus = new Constellation("Taurus", new ArrayList<>());
        taurus.addStar(new Star("Aldebaran", 50, 400, 0.85));
        taurus.addStar(new Star("Elnath", 100, 450, 1.65));
        constellations.add(taurus);

        // Save data to file
        DataWriter.saveDataToFile(testFilePath.toString(), stars, constellations);

        // Read the content of the file
        List<String> fileContent = Files.readAllLines(testFilePath);

        // Verify the content
        List<String> expectedLines = List.of(
                "Star,Sirius,100.00,200.00,1.46",
                "Star,Canopus,150.00,250.00,0.72",
                "Constellation,Taurus,Aldebaran,Elnath"
        );

        assertLinesMatch(expectedLines, fileContent);
    }
}

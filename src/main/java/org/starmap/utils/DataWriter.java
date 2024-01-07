package org.starmap.utils;

import org.starmap.model.Constellation;
import org.starmap.model.Star;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DataWriter {

    public static void saveDataToFile(String filePath, List<Star> stars, List<Constellation> constellations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Zapisz gwiazdy
            for (Star star : stars) {
                writer.write(String.format(Locale.US, "Star,%s,%.2f,%.2f,%.2f%n",
                        star.getName(), star.getXPosition(), star.getYPosition(), star.getBrightness()));
            }

            // Zapisz gwiazdozbiory
            for (Constellation constellation : constellations) {
                writer.write("Constellation," + constellation.getName() + ",");
                List<Star> starsInConstellation = constellation.getStars();
                for (int i = 0; i < starsInConstellation.size(); i++) {
                    writer.write(starsInConstellation.get(i).getName());
                    if (i < starsInConstellation.size() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

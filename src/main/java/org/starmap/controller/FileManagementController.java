package org.starmap.controller;

import org.starmap.utils.DataLoader;
import org.starmap.utils.DataWriter;

public class FileManagementController {

    private final StarMapController starMapController;

    public FileManagementController(StarMapController starMapController) {
        this.starMapController = starMapController;
    }

    public void loadFromFile(String filePath) {
        starMapController.setStars(DataLoader.loadStars(filePath));
        starMapController.setConstellations(DataLoader.loadConstellations(filePath, starMapController.getStars()));
    }

    public void saveToFile(String filePath) {
        DataWriter.saveDataToFile(filePath, starMapController.getStars(), starMapController.getConstellations());
    }
}

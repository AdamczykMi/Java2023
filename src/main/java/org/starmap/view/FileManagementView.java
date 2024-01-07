package org.starmap.view;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.starmap.controller.FileManagementController;

import java.io.File;

public class FileManagementView {

    private final FileManagementController controller;

    public FileManagementView(FileManagementController controller) {
        this.controller = controller;
    }

    public void openFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz plik");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki JSON", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            controller.loadFromFile(selectedFile.getPath());
        }
    }

    public void saveToFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz plik");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki JSON", "*.json"));
        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile != null) {
            controller.saveToFile(selectedFile.getPath());
        }
    }
}

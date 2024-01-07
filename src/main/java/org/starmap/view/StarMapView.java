package org.starmap.view;

import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.starmap.controller.StarMapController;
import org.starmap.model.Constellation;
import org.starmap.model.Star;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Button;
import java.util.*;
import java.util.stream.Collectors;

public class StarMapView extends Canvas {
    private final StarMapController controller;
    private PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
    private Star currentHoveredStar = null;
    private Map<String, Color> constellationColors = new HashMap<>();

    private boolean showCoordinates = true;

    public StarMapView(StarMapController controller) {
        this.controller = controller;
        this.setWidth(1024); // Set canvas width
        this.setHeight(768); // Set canvas height
        drawMap();
        initializeConstellationColors();
        addMouseMotionListener();
        setMouseHandlersForStarMove();
    }

    public void setShowCoordinates(boolean showCoordinates) {
        this.showCoordinates = showCoordinates;
        drawMap(); // Przerysuj mapę po zmianie opcji
    }
    private void initializeConstellationColors() {
        List<Constellation> constellations = controller.getConstellations();
        for (Constellation constellation : constellations) {
            int hash = constellation.getName().hashCode();
            Random rand = new Random(hash); // Use hash as a seed for random generator
            Color color = new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1);
            constellationColors.put(constellation.getName(), color);
        }
    }

    public void drawMap() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight()); // Set background to black
        drawCoordinates();
        drawStars();
        drawConstellations();
    }

    private void drawStars() {
        GraphicsContext gc = getGraphicsContext2D();
        List<Star> stars = controller.getStars();
        for (Star star : stars) {
            double brightnessScale = star.getBrightness() / 2.0; // Scale brightness
            double starSize = 2 + (5 - brightnessScale); // Calculate star size
            Color starColor = Color.hsb(60, 0.5, 1 - 0.2 * brightnessScale); // Color based on brightness
            drawStar(gc, star.getXPosition(), star.getYPosition(), starSize, starColor);
        }
    }

    private void drawStar(GraphicsContext gc, double x, double y, double size, Color color) {
        double[] xPoints = new double[10];
        double[] yPoints = new double[10];
        for (int i = 0; i < 10; i++) {
            double angle = Math.PI / 5 * i;
            double radius = i % 2 == 0 ? size : size / 2;
            xPoints[i] = x + radius * Math.sin(angle);
            yPoints[i] = y - radius * Math.cos(angle);
        }
        gc.setStroke(color);
        gc.strokePolyline(xPoints, yPoints, 10);
    }

    private void drawConstellations() {
        GraphicsContext gc = getGraphicsContext2D();
        List<Constellation> constellations = controller.getConstellations();

        for (Constellation constellation : constellations) {
            Color lineColor = constellationColors.getOrDefault(constellation.getName(), Color.BLUE);
            gc.setStroke(lineColor);
            gc.setLineWidth(1);
            gc.setFill(lineColor);
            gc.setFont(new Font("Arial", 14));

            List<Star> starsInConstellation = constellation.getStars();
            for (int i = 0; i < starsInConstellation.size() - 1; i++) {
                Star start = starsInConstellation.get(i);
                Star end = starsInConstellation.get(i + 1);
                gc.strokeLine(start.getXPosition(), start.getYPosition(), end.getXPosition(), end.getYPosition());
            }

            // Rysuj nazwę konstelacji obok pierwszej gwiazdy
            if (!starsInConstellation.isEmpty()) {
                Star firstStar = starsInConstellation.get(0);
                gc.fillText(constellation.getName(), firstStar.getXPosition(), firstStar.getYPosition() - 15);
            }
        }
    }
    private void drawCoordinates() {
        if (showCoordinates) {
            GraphicsContext gc = getGraphicsContext2D();
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(1);

            // Oś X
            gc.strokeLine(0, getHeight() / 2, getWidth(), getHeight() / 2);

            // Oś Y
            gc.strokeLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        }
    }

    private void addMouseMotionListener() {
        this.setOnMouseMoved(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            Star foundStar = null;

            List<Star> stars = controller.getStars();
            for (Star star : stars) {
                if (Math.abs(mouseX - star.getXPosition()) < 10 && Math.abs(mouseY - star.getYPosition()) < 10) {
                    foundStar = star;
                    break;
                }
            }

            if (foundStar != null && foundStar != currentHoveredStar) {
                currentHoveredStar = foundStar;
                pause.stop(); // Zatrzymaj poprzednie opóźnienie
                drawStarName(foundStar);
            } else if (foundStar == null && currentHoveredStar != null) {
                pause.setOnFinished(e -> {
                    hideStarName();
                    currentHoveredStar = null;
                });
                pause.playFromStart();
            }
        });
    }

    private void drawStarName(Star star) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillText(star.getName(), star.getXPosition() + 10, star.getYPosition() - 10);
    }

    private void hideStarName() {
        if (currentHoveredStar != null) {
            pause.setOnFinished(e -> {
                clearCanvas();
                drawMap(); // Rysuj wszystko od nowa
            });
            pause.playFromStart();
        }
    }

    private void clearCanvas() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
    }

    private Star findStarAtPosition(double x, double y) {
        List<Star> stars = controller.getStars();
        for (Star star : stars) {
            double starX = star.getXPosition();
            double starY = star.getYPosition();
            double distance = Math.sqrt(Math.pow(x - starX, 2) + Math.pow(y - starY, 2));
            if (distance < 10) {
                return star;
            }
        }
        return null;
    }


    private void setMouseHandlersForStarMove() {
        setOnMousePressed(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            Star clickedStar = findStarAtPosition(mouseX, mouseY);

            if (clickedStar != null) {
                MouseButton button = event.getButton();

                if (button == MouseButton.PRIMARY) {
                    // Przesuwanie gwiazdy (Lewy przycisk myszy)
                    controller.startMovingStar(clickedStar);
                } else if (button == MouseButton.SECONDARY) {
                    // Edytowanie gwiazdy (prawy przycisk myszy)
                    openStarEditor(clickedStar);
                } else if (button == MouseButton.MIDDLE) {
                    // Dodawanie nowej gwiazdy (środkowy przycisk myszy)
                    addNewStarOnCanvas(mouseX, mouseY);
                }
            }
        });

        setOnMouseDragged(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            controller.moveStar(mouseX, mouseY);
            drawMap();
        });

        setOnMouseReleased(event -> {
            controller.stopMovingStar();
        });


    }

    private void openStarEditor(Star star) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edycja Gwiazdy");
        dialog.setHeaderText("Edytuj informacje o gwiazdzie");

        TextField nameField = new TextField(star.getName());
        TextField brightnessField = new TextField(Double.toString(star.getBrightness()));

        ChoiceBox<String> constellationChoiceBox = new ChoiceBox<>();
        List<String> constellationNames = controller.getConstellations().stream()
                .map(Constellation::getName)
                .collect(Collectors.toList());
        constellationChoiceBox.getItems().addAll(constellationNames);
        constellationChoiceBox.setValue(getConstellationNameContainingStar(star));

        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Nazwa:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Jasność:"), 0, 1);
        gridPane.add(brightnessField, 1, 1);
        gridPane.add(new Label("Gwiazdozbiór:"), 0, 2);
        gridPane.add(constellationChoiceBox, 1, 2);

        dialog.getDialogPane().setContent(gridPane);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType("Usuń", ButtonBar.ButtonData.APPLY);
        ButtonType cancelButtonType = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, deleteButtonType, cancelButtonType);

        Node okButtonNode = dialog.getDialogPane().lookupButton(okButtonType);
        if (okButtonNode instanceof Button) {
            ((Button) okButtonNode).setDefaultButton(true);
        }

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                String newName = nameField.getText();
                double newBrightness = Double.parseDouble(brightnessField.getText());
                String newConstellationName = constellationChoiceBox.getValue();

                String previousConstellationName = getConstellationNameContainingStar(star);
                if (previousConstellationName != null && !previousConstellationName.equals(newConstellationName)) {
                    Constellation previousConstellation = controller.getConstellationByName(previousConstellationName).orElse(null);
                    if (previousConstellation != null) {
                        previousConstellation.removeStar(star);
                    }
                }

                Constellation newConstellation = controller.getConstellationByName(newConstellationName).orElse(null);
                if (newConstellation != null) {
                    newConstellation.addStar(star);
                }

                controller.editStar(star, newName, newBrightness);
                drawMap();
            } else if (dialogButton == deleteButtonType) {
                Constellation previousConstellation = controller.getConstellationByName(getConstellationNameContainingStar(star)).orElse(null);
                if (previousConstellation != null) {
                    previousConstellation.removeStar(star);
                }
                controller.removeStar(star.getName());
                drawMap();
            }
            return null;
        });

        dialog.showAndWait();
    }

    private String getConstellationNameContainingStar(Star star) {
        return controller.getConstellations().stream()
                .filter(constellation -> constellation.getStars().contains(star))
                .map(Constellation::getName)
                .findFirst()
                .orElse(null);
    }

    private void addNewStarOnCanvas(double x, double y) {
        // Pobierz nazwę i inne dane dla nowej gwiazdy, np., poprzez okno dialogowe
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nowa Gwiazda");
        dialog.setHeaderText("Podaj nazwę nowej gwiazdy:");

        // Dodaj pole wyboru dla dodawania do gwiazdozbioru
        CheckBox addToConstellationCheckBox = new CheckBox("Dodaj do gwiazdozbioru");

        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Nazwa:"), 0, 0);
        gridPane.add(dialog.getEditor(), 1, 0);
        gridPane.add(addToConstellationCheckBox, 0, 1, 2, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Obsługa przycisku OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Pobierz wartości z pól edycji
                String newStarName = dialog.getEditor().getText();
                boolean addToConstellation = addToConstellationCheckBox.isSelected();

                // Dodaj nową gwiazdę do kontrolera i narysuj mapę
                controller.addNewStar(newStarName, x, y);

                // Dodaj nową gwiazdę do gwiazdozbioru, jeśli użytkownik wybrał tę opcję
                if (addToConstellation) {
                    addToConstellation(newStarName);
                }

                drawMap(); // Odśwież widok
            }
            return null;
        });

        // Wyświetl dialog
        dialog.showAndWait();
    }


    private void addToConstellation(String starName) {
        // Pobierz listę gwiazdozbiorów
        List<Constellation> constellations = controller.getConstellations();

        // Stwórz okno dialogowe do wprowadzenia nazwy nowego gwiazdozbioru
        TextInputDialog constellationNameDialog = new TextInputDialog();
        constellationNameDialog.setTitle("Dodaj do gwiazdozbioru");
        constellationNameDialog.setHeaderText("Podaj nazwę nowego gwiazdozbioru:");

        // Wyświetl okno dialogowe do wprowadzenia nazwy
        Optional<String> result = constellationNameDialog.showAndWait();

        // Jeśli użytkownik wprowadził nazwę, dodaj nową gwiazdę do wybranego lub nowo utworzonego gwiazdozbioru
        result.ifPresent(constellationName -> {
            // Sprawdź, czy taki gwiazdozbiór już istnieje
            Optional<Constellation> selectedConstellation = constellations.stream()
                    .filter(constellation -> constellation.getName().equals(constellationName))
                    .findFirst();

            // Jeśli nie istnieje, utwórz nowy gwiazdozbiór
            Constellation constellation = selectedConstellation.orElse(new Constellation(constellationName, new ArrayList<>()));

            // Dodaj nową gwiazdę do gwiazdozbioru
            Optional<Star> newStar = controller.getStarByName(starName);
            newStar.ifPresent(constellation::addStar);

            // Dodaj nowy gwiazdozbiór do listy, jeśli został utworzony nowy
            if (!selectedConstellation.isPresent()) {
                constellations.add(constellation);
            }
        });
    }


}

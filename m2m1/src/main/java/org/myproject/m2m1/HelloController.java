package org.myproject.m2m1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.PerspectiveCamera;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.Random;

public class HelloController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Circle circle;

    @FXML
    private Rectangle cub;

    @FXML
    private Line line;

    @FXML
    private SplitMenuButton form;

    @FXML
    private SplitMenuButton color;

    @FXML
    private Slider slider;

    @FXML
    private MenuItem red, blue, black;

    @FXML
    private TabPane TabPane;

    @FXML
    private AnchorPane pane3;

    // Добавляем фасад
    private DrawingFabric drawingFabric;

    private Random random = new Random();
    private Color color1 = Color.RED;

    // Переменные для рисования
    private double startMouseX, startMouseY;
    private double endMouseX, endMouseY;
    private Line line1;
    private Circle circle1;
    private Rectangle cub1;

    // Переменные для 3D
    private Box3DFabric box3DFabric;

    @FXML
    void initialize() {
        // Инициализация фасада
        drawingFabric = new DrawingFabric(pane);
        box3DFabric = new Box3DFabric(pane3);

        assert circle != null : "fx:id=\"circle\" was not injected";
        assert cub != null : "fx:id=\"cub\" was not injected";
        assert line != null : "fx:id=\"line\" was not injected";

        box3DFabric.initialize3DScene();
    }

    // Демонстрация работы фасада
    @FXML
    void demoFacade(ActionEvent event) {
        // Автоматически создаем 3 разные фигуры через фасад
        drawingFabric.createCircle(100, 100, 30, Color.RED, 2);
        drawingFabric.createLine(200, 200, 300, 250, Color.BLUE, 3);
        drawingFabric.createRectangle(400, 300, 80, 60, Color.GREEN, 2);

        // Показываем сообщение
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Фасад");
        alert.setHeaderText("Паттерн Фасад");
        alert.setContentText("Создано 3 фигуры через фасад!\nВсего фигур на панели: " + drawingFabric.getShapesCount());
        alert.showAndWait();
    }

    @FXML
    void panepressed(MouseEvent event) {
        // Получаем выбранный цвет через фасад (упрощенная логика)
        if (color.getText().equals("Красный")) {
            color1 = Color.RED;
        } else if (color.getText().equals("Синий")) {
            color1 = Color.BLUE;
        } else if (color.getText().equals("Черный")) {
            color1 = Color.BLACK;
        }

        startMouseX = event.getX();
        startMouseY = event.getY();

        switch (form.getText()) {
            case "круг":
                circle1 = drawingFabric.createCircle(startMouseX, startMouseY, 0, color1, slider.getValue());
                break;
            case "линия":
                line1 = drawingFabric.createLine(startMouseX, startMouseY, startMouseX + 1, startMouseY + 1, color1, slider.getValue());
                break;
            case "прямоугольник":
                cub1 = drawingFabric.createRectangle(startMouseX, startMouseY, 20, 20, color1, slider.getValue());
                break;
        }
    }

    @FXML
    void panedragged(MouseEvent event) {
        endMouseX = event.getX();
        endMouseY = event.getY();

        switch (form.getText()) {
            case "линия":
                if (line1 != null) {
                    line1.setEndX(endMouseX);
                    line1.setEndY(endMouseY);
                }
                break;
            case "круг":
                if (circle1 != null) {
                    double radius = Math.sqrt(Math.pow((startMouseX - endMouseX), 2) + Math.pow((startMouseY - endMouseY), 2));
                    circle1.setRadius(radius);
                }
                break;
            case "прямоугольник":
                if (cub1 != null) {
                    cub1.setWidth(Math.abs(startMouseX - endMouseX));
                    cub1.setHeight(Math.abs(startMouseY - endMouseY));
                    cub1.setX(Math.min(startMouseX, endMouseX));
                    cub1.setY(Math.min(startMouseY, endMouseY));
                }
                break;
        }
    }

    @FXML
    void OnMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            drawingFabric.changeCircleColor(circle, drawingFabric.generateRandomColor());
        }
    }

    @FXML
    void OnScroll(ScrollEvent event) {
        Object object = event.getTarget();
        if (object instanceof Circle) {
            drawingFabric.resizeCircle((Circle) object, event.getDeltaY() > 0 ? 5 : -5);
        } else if (object instanceof Line) {
            drawingFabric.resizeLine((Line) object, event.getDeltaY() > 0 ? 1.05 : 1/1.05);
        }
    }

    @FXML
    void LineMousePressed(MouseEvent event) {
        // Сохраняем начальные координаты для поворота
    }

    @FXML
    void LineMouseDragged(MouseEvent event) {
        // Логика поворота линии
    }

    @FXML
    void OnMouseMoved(MouseEvent event) {
        // Используем фасад для изменения цвета
        drawingFabric.changeCircleColor(circle, drawingFabric.generateRandomColor());
    }

    @FXML
    void OnMouseExited(MouseEvent event) {
        // Используем фасад для изменения цвета прямоугольника
        if (cub != null) {
            cub.setFill(Color.web("0xaaea9cff"));
        }
    }

    // Методы для выбора фигур
    @FXML
    public void actionmenucricle(ActionEvent actionEvent) {
        form.setText("круг");
    }

    @FXML
    public void actionmenurectangle(ActionEvent actionEvent) {
        form.setText("прямоугольник");
    }

    @FXML
    public void actionmenuline(ActionEvent actionEvent) {
        form.setText("линия");
    }

    // Методы для выбора цвета
    @FXML
    public void ActionRed(ActionEvent actionEvent) {
        color.setText("Красный");
    }

    @FXML
    public void ActionBlack(ActionEvent actionEvent) {
        color.setText("Черный");
    }

    @FXML
    public void ActionBlue(ActionEvent actionEvent) {
        color.setText("Синий");
    }

    // Методы для 3D (упрощенные)
    @FXML
    void actionPressedPane3(MouseEvent event) {
        box3DFabric.onMousePressed(event);
    }

    @FXML
    void pane3Dragged(MouseEvent event) {
        box3DFabric.onMouseDragged(event);
    }

    @FXML
    void ScrollPane3(ScrollEvent event) {
        box3DFabric.onScroll(event);
    }

    public void paneclick(MouseEvent mouseEvent) {
    }

    public void actionmenycricle(ActionEvent event) {
    }
}
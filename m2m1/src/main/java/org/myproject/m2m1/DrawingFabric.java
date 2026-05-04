package org.myproject.m2m1;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingFabric {
    private AnchorPane pane;
    private List<Object> shapes;
    private Random random;

    public DrawingFabric(AnchorPane pane) {
        this.pane = pane;
        this.shapes = new ArrayList<>();
        this.random = new Random();
    }

    // Создание круга
    public Circle createCircle(double x, double y, double radius, Color color, double strokeWidth) {
        Circle circle = new Circle(x, y, radius);
        circle.setFill(color);
        circle.setStroke(Color.RED);
        circle.setStrokeWidth(strokeWidth);
        pane.getChildren().add(circle);
        shapes.add(circle);
        return circle;
    }

    // Создание линии
    public Line createLine(double startX, double startY, double endX, double endY, Color color, double strokeWidth) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(color);
        line.setStrokeWidth(strokeWidth);
        pane.getChildren().add(line);
        shapes.add(line);
        return line;
    }

    // Создание прямоугольника
    public Rectangle createRectangle(double x, double y, double width, double height, Color color, double strokeWidth) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(strokeWidth);
        pane.getChildren().add(rect);
        shapes.add(rect);
        return rect;
    }

    // Изменение цвета круга
    public void changeCircleColor(Circle circle, Color color) {
        if (circle != null) {
            circle.setFill(color);
        }
    }

    // Изменение размера круга
    public void resizeCircle(Circle circle, double delta) {
        if (circle != null) {
            double newRadius = circle.getRadius() + delta;
            if (newRadius > 5) {
                circle.setRadius(newRadius);
            }
        }
    }

    // Изменение толщины линии
    public void resizeLine(Line line, double factor) {
        if (line != null) {
            line.setStrokeWidth(line.getStrokeWidth() * factor);
        }
    }

    // Поворот линии
    public void rotateLine(Line line, double angle) {
        if (line != null) {
            line.setRotate(angle);
        }
    }

    // Генерация случайного цвета
    public Color generateRandomColor() {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    // Очистка всех фигур
    public void clearAllShapes() {
        pane.getChildren().removeAll(shapes);
        shapes.clear();
    }

    // Получение количества фигур
    public int getShapesCount() {
        return shapes.size();
    }
}
package org.myproject.m2m1;

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Box3DFabric {
    private AnchorPane pane3;
    private Box box;
    private Rotate rotateXAxis;
    private Rotate rotateYAxis;
    private Translate translate;
    private PhongMaterial material;
    private PerspectiveCamera camera;
    private Group modelGroup;

    private double mouse0ldX, mouse0ldY;
    private final double mouseSensitivity = 0.1;
    private final double movementSpeed = 10.0;

    public Box3DFabric(AnchorPane pane3) {
        this.pane3 = pane3;
        this.box = new Box();
        this.material = new PhongMaterial();
        this.modelGroup = new Group();
    }

    public void initialize3DScene() {
        // Настройка материала
        material.setDiffuseColor(Color.OLIVE);
        box.setMaterial(material);
        box.setWidth(100);
        box.setHeight(100);
        box.setDepth(100);

        // Настройка трансформаций
        rotateXAxis = new Rotate(0, Rotate.X_AXIS);
        rotateYAxis = new Rotate(0, Rotate.Y_AXIS);
        translate = new Translate(400, 250, 0);

        modelGroup.getTransforms().addAll(translate, rotateXAxis, rotateYAxis);
        modelGroup.getChildren().add(box);

        // Освещение
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateX(800);
        pointLight.setTranslateY(-700);
        pointLight.setTranslateZ(-300);

        // Камера
        camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-500);

        pane3.getChildren().addAll(modelGroup, ambientLight, pointLight);
        pane3.setDepthTest(javafx.scene.DepthTest.ENABLE);
    }

    public void onMousePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            mouse0ldX = event.getSceneX();
            mouse0ldY = event.getSceneY();
        }
    }

    public void onMouseDragged(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            double mouseDeltaX = event.getSceneX() - mouse0ldX;
            double mouseDeltaY = event.getSceneY() - mouse0ldY;
            rotateXAxis.setAngle(rotateXAxis.getAngle() - mouseDeltaY * mouseSensitivity);
            rotateYAxis.setAngle(rotateYAxis.getAngle() + mouseDeltaX * mouseSensitivity);
            mouse0ldX = event.getSceneX();
            mouse0ldY = event.getSceneY();
        }
    }

    public void onScroll(ScrollEvent event) {
        double delta = event.getDeltaY();
        if (delta > 0) {
            translate.setZ(translate.getZ() + movementSpeed);
        } else {
            translate.setZ(translate.getZ() - movementSpeed);
        }
    }

    public void changeBoxColor(Color color) {
        material.setDiffuseColor(color);
    }
}
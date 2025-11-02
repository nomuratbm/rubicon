package com.gabriel.draw.component;

import com.gabriel.draw.controller.PropertyEventListener;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.property.PropertyOptions;
import com.gabriel.property.PropertyPanel;
import com.gabriel.property.cell.SelectionCellComponent;
import com.gabriel.property.property.*;
import com.gabriel.property.property.selection.Item;
import com.gabriel.property.property.selection.SelectionProperty;
import com.gabriel.property.validator.CompoundValidator;
import com.gabriel.property.validator.StringValidator;
import com.gabriel.property.validator.doubleNumber.DoubleRangeValidator;
import com.gabriel.property.validator.doubleNumber.DoubleValidator;
import com.gabriel.property.validator.doubleNumber.DoubleZeroPolicyValidator;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PropertySheet extends PropertyPanel {
    PropertyPanel propertyTable;
    private SelectionProperty shapeProp;
    Item RectangleItem;
    Item EllipseItem;
    Item LineItem;
    Item TextItem;
    Item SelectItem;


    public void setShapeProp(ShapeMode shapeMode ){
        SelectionCellComponent  selectionComponent =  propertyTable.getSelectionCellComponent();
        if (shapeMode ==ShapeMode.Rectangle) {
            selectionComponent.setCellEditorValue(RectangleItem);
        } else if (shapeMode == ShapeMode.Ellipse) {
            selectionComponent.setCellEditorValue(EllipseItem);
        } else if (shapeMode == ShapeMode.Line) {
            selectionComponent.setCellEditorValue(LineItem);
        } else if (shapeMode == ShapeMode.Select) {
            selectionComponent.setCellEditorValue(SelectItem);
        }
    }

    public PropertySheet(PropertyOptions options){
        super(options);
        shapeProp = new SelectionProperty<>(
                "Current Shape",
                new ArrayList<>(Arrays.asList(
                        new Item<>(ShapeMode.Rectangle, "Rectangle"),
                        new Item<>(ShapeMode.Ellipse, "Ellipse"),
                        new Item<>(ShapeMode.Line, "Line"),
                        new Item<>(ShapeMode.Text, "Text"),
                        new Item<>(ShapeMode.Select, "Select")
                ))
        );
    }

    public void populateTable(AppService appService) {
        Drawing drawing = appService.getDrawing();
        propertyTable = this;
        propertyTable.addEventListener(new PropertyEventListener(appService));

        propertyTable.clear();
        Shape shape  = appService.getSelectedShape();
        String objectType;
        if ( shape == null) {
            objectType = "Drawing";
        }
        else {
            objectType = "Shape";
        }

        StringProperty targetProp = new StringProperty("Object Type", objectType);
        propertyTable.addProperty(targetProp);

        RectangleItem = new Item<ShapeMode>(ShapeMode.Rectangle, "Rectangle");
        EllipseItem = new Item<ShapeMode>(ShapeMode.Ellipse, "Ellipse");
        LineItem =    new Item<ShapeMode>(ShapeMode.Line, "Line");
        SelectItem =    new Item<ShapeMode>(ShapeMode.Select, "Select");
        TextItem =    new Item<ShapeMode>(ShapeMode.Text, "Text");
        shapeProp = new SelectionProperty<>(
                "Current Shape",
                new ArrayList<>(Arrays.asList(
                        RectangleItem,
                        EllipseItem,
                        LineItem,
                        SelectItem,
                        TextItem
                ))
        );

        propertyTable.addProperty(shapeProp);

        SelectionCellComponent  selectionComponent =  propertyTable.getSelectionCellComponent();
        ShapeMode shapeMode = appService.getShapeMode();
        if(shapeMode == ShapeMode.Rectangle) {
            selectionComponent.setCellEditorValue(RectangleItem);
        }
        else if(shapeMode == ShapeMode.Ellipse) {
            selectionComponent.setCellEditorValue(EllipseItem);
        }
        else if(shapeMode == ShapeMode.Line) {
            selectionComponent.setCellEditorValue(LineItem);
        }
        else if(shapeMode == ShapeMode.Select) {
            selectionComponent.setCellEditorValue(SelectItem);
        }
        shapeProp.setValue(shape);


        ColorProperty currentColorProp = new ColorProperty("Fore color", appService.getColor());
        propertyTable.addProperty(currentColorProp);

        ColorProperty currentFillProp = new ColorProperty("Fill color",  appService.getFill());
        propertyTable.addProperty(currentFillProp);

        ColorProperty currentStartColorProp = new ColorProperty("Start color",  appService.getStartColor());
        propertyTable.addProperty(currentStartColorProp);

        ColorProperty currentEndColorProp = new ColorProperty("End color",  appService.getEndColor());
        propertyTable.addProperty(currentEndColorProp);

        IntegerProperty startx = new IntegerProperty("Start x", appService.getXLocation());
        propertyTable.addProperty(startx );

        IntegerProperty starty = new IntegerProperty("Start y", appService.getXLocation());
        propertyTable.addProperty(starty );

        IntegerProperty endx = new IntegerProperty("End x", appService.getXLocation());
        propertyTable.addProperty(endx );

        IntegerProperty endy = new IntegerProperty("End y", appService.getXLocation());
        propertyTable.addProperty(endy );

        BooleanProperty isGradientProp = new BooleanProperty("IsGradient",  appService.isGradient() );
        propertyTable.addProperty(isGradientProp);

        BooleanProperty isVisibleProp = new BooleanProperty("IsVisible",  appService.isGradient() );
        propertyTable.addProperty(isVisibleProp);

        IntegerProperty lineThicknessProp = new IntegerProperty("Line Thickness", appService.getThickness());
        propertyTable.addProperty(lineThicknessProp);

        IntegerProperty xlocProp = new IntegerProperty("X Location", appService.getXLocation());
        propertyTable.addProperty(xlocProp);

        IntegerProperty ylocProp = new IntegerProperty("Y Location", appService.getYLocation());
        propertyTable.addProperty(ylocProp);

        IntegerProperty width = new IntegerProperty("Width", appService.getXLocation());
        propertyTable.addProperty(width );

        IntegerProperty height = new IntegerProperty("Height", appService.getXLocation());
        propertyTable.addProperty(height);

        BooleanProperty prop3 = new BooleanProperty("Boolean", true);
        propertyTable.addProperty(prop3);

        if(shape!=null) {
            BooleanProperty selectedProp = new BooleanProperty("is Selected", shape.isSelected());
            propertyTable.addProperty(selectedProp);
        }

        FloatProperty prop4 = new FloatProperty("Float", 1.2f);
        propertyTable.addProperty(prop4);

        StringProperty stringProp = new StringProperty("Text", appService.getText());
        propertyTable.addProperty(stringProp);

        stringProp = new StringProperty("Image", appService.getImageFilename());
        propertyTable.addProperty(stringProp);

        Font font = appService.getFont();

        stringProp = new StringProperty("Font family", font.getFamily());
        propertyTable.addProperty(stringProp);

        IntegerProperty intProp = new IntegerProperty("Font style", font.getStyle());
        propertyTable.addProperty(intProp);

        intProp = new IntegerProperty("Font size", font.getSize());
        propertyTable.addProperty(intProp);

        StringProperty prop6 = new StringProperty("String 2", "test", new StringValidator(
                new String[]{"test", "test 2", "foo"}
        ));
        propertyTable.addProperty(prop6);

        DoubleProperty prop8 = new DoubleProperty("Double", 2.34,
                new CompoundValidator(
                        new DoubleValidator(),
                        new DoubleRangeValidator(-1.2, 45.33, true, false),
                        new DoubleZeroPolicyValidator(false)
                )
        );
    }
}

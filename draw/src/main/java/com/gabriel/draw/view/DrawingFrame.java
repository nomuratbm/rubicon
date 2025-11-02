package com.gabriel.draw.view;

import com.gabriel.draw.command.*;
import com.gabriel.draw.component.PropertySheet;
import com.gabriel.draw.controller.ActionController;
import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.controller.DrawingWindowController;
import com.gabriel.draw.service.DrawingAppService;
import com.gabriel.draw.service.DrawingCommandAppService;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.property.PropertyOptions;
import com.gabriel.property.event.PropertyEventAdapter;
import com.gabriel.property.property.Property;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DrawingFrame extends JFrame {

    Drawing drawing;
    DrawingAppService drawingAppService;
    AppService appService;
    DrawingFrame drawingFrame;
    Container pane;
    private PropertySheet propertySheet;
    ActionController actionListener;
    DrawingMenuBar drawingMenuBar;
    DrawingToolBar drawingToolBar;
    DrawingView drawingView;
    DrawingController drawingController;
    JScrollPane jScrollPane;
    DrawingStatusPanel drawingStatusPanel;
    DrawingWindowController drawingWindowController;
    public DrawingFrame() {

        drawing = new Drawing();
        drawingAppService = new DrawingAppService();
        appService = DrawingCommandAppService.getInstance(drawingAppService);

        pane = getContentPane();
        setLayout(new BorderLayout());

        actionListener = new ActionController(appService);
        actionListener.setFrame(this);
        drawingMenuBar = new DrawingMenuBar( actionListener);

        setJMenuBar(drawingMenuBar);

        drawingMenuBar.setVisible(true);


        drawingToolBar = new DrawingToolBar(actionListener);
        drawingToolBar.setVisible(true);

        drawingView = new DrawingView(appService);
        actionListener.setComponent(drawingView);


        drawingController = new DrawingController(appService, drawingView);
        drawingController.setDrawingView(drawingView);

        drawingView.addMouseMotionListener(drawingController);
        drawingView.addMouseListener(drawingController);
        drawingView.setPreferredSize(new Dimension(4095, 8192));

        jScrollPane = new JScrollPane(drawingView);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        drawingStatusPanel = new DrawingStatusPanel();
        drawingController.setDrawingStatusPanel(drawingStatusPanel);

        pane.add(drawingToolBar, BorderLayout.PAGE_START);
        pane.add(jScrollPane, BorderLayout.CENTER );
        pane.add(drawingStatusPanel, BorderLayout.PAGE_END);

        drawingAppService.setDrawingView(drawingView);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);

        drawingWindowController = new DrawingWindowController(appService);
        this.addWindowListener(drawingWindowController);
        this.addWindowFocusListener(drawingWindowController);
        this.addWindowStateListener(drawingWindowController);
        buildGUI(pane);
        drawingController.setPropertySheet(propertySheet);
    }

    public void buildGUI(Container pane){
        buildPropertyTable(pane);
        JScrollPane scrollPane = new JScrollPane(propertySheet);
        pane.add(scrollPane, BorderLayout.LINE_END);
        pack();
    }


    void buildPropertyTable(Container pane) {
        String[] headers = new String[]{"Property", "value"};
        Color backgroundColor = new Color(255, 255, 255);
        Color invalidColor = new Color(255, 179, 176);
        int rowHeight = 30;
        PropertyOptions options = new PropertyOptions(headers, backgroundColor, invalidColor, rowHeight);

        propertySheet = new PropertySheet(new PropertyOptions.Builder().build());
        propertySheet.addEventListener(new EventListener());
        propertySheet.populateTable(appService);

        repaint();
    }

    class EventListener extends PropertyEventAdapter {
        @Override
        public void onPropertyUpdated(Property property) {
            Shape shape  = appService.getSelectedShape();
            if(property.getName().equals("Current Shape")){
                if(shape ==null) {
                    appService.setShapeMode((ShapeMode) property.getValue());
                }
            }
            else if(property.getName().equals("Fore color")){
                if(shape ==null) {
                    appService.setColor((Color) property.getValue());
                } else {
                    Command command = new SetColorCommand(appService, (Color) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Fill color")){
                if(shape ==null) {
                    appService.setFill((Color)property.getValue());
                } else {
                    Command command = new SetFillCommand(appService, (Color) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Line Thickness")){
                if(shape ==null) {
                    appService.setThickness((int)property.getValue());
                } else {
                    Command command = new SetThicknessCommand(appService, (int) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("X Location")){
                if(shape != null) {
                    Command command = SetLocationCommand.forX(appService, (int) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Y Location")){
                if(shape != null) {
                    Command command = SetLocationCommand.forY(appService, (int) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Width")){
                if(shape != null) {
                    Command command = SetDimensionsCommand.forWidth(appService, (int) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Height")){
                if(shape != null) {
                    Command command = SetDimensionsCommand.forHeight(appService, (int) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Text")){
                if(shape != null) {
                    Command command = new SetTextCommand(appService, (String) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Font size")){
                if(shape != null) {
                    Font font = shape.getFont();
                    if(font != null) {
                        Font newFont = new Font(font.getFamily(), font.getStyle(), (int) property.getValue());
                        Command command = new SetFontCommand(appService, newFont);
                        CommandService.ExecuteCommand(command);
                    }
                }
            }
            else if(property.getName().equals("Font family")){
                if(shape != null) {
                    Font font = shape.getFont();
                    if(font != null) {
                        Font newFont = new Font((String) property.getValue(), font.getStyle(), font.getSize());
                        Command command = new SetFontCommand(appService, newFont);
                        CommandService.ExecuteCommand(command);
                    }
                }
            }
            else if(property.getName().equals("Font style")){
                if(shape != null) {
                    Font font = shape.getFont();
                    if(font != null) {
                        Font newFont = new Font(font.getFamily(), (int) property.getValue(), font.getSize());
                        Command command = new SetFontCommand(appService, newFont);
                        CommandService.ExecuteCommand(command);
                    }
                }
            }
            else if(property.getName().equals("Start color")){
                if(shape != null) {
                    Command command = new SetGradientCommand(appService, shape.isGradient(),
                            (Color) property.getValue(), shape.getEndColor());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("End color")){
                if(shape != null) {
                    Command command = new SetGradientCommand(appService, shape.isGradient(),
                            shape.getStartColor(), (Color) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("IsGradient")){
                if(shape != null) {
                    Command command = new SetGradientCommand(appService, (Boolean) property.getValue(),
                            shape.getStartColor(), shape.getEndColor());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("IsVisible")){
                if(shape != null) {
                    shape.setVisible((Boolean) property.getValue());
                }
            }

            drawingView.repaint();
        }
    }
}


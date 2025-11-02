package com.gabriel.draw.controller;

import com.gabriel.property.event.PropertyEventAdapter;
import com.gabriel.property.property.Property;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class PropertyEventListener extends PropertyEventAdapter {
    private AppService appService;

    public PropertyEventListener(AppService appService) {
        this.appService = appService;
    }

    @Override
    public void onPropertyUpdated(Property property) {
        if (property.getName().equals("Fill color")) {
            appService.setFill((Color) property.getValue());
        } else if (property.getName().equals("Fore color")) {
            appService.setColor ((Color) property.getValue());
        } else if (property.getName().equals("X Location")) {
            appService.setXLocation((int) property.getValue());
        } else if (property.getName().equals("Y Location")) {
            appService.setYLocation((int) property.getValue());
        } else if (property.getName().equals("Width")) {
            appService.setWidth((int) property.getValue());
        } else if (property.getName().equals("Height")) {
            appService.setHeight((int) property.getValue());
        } else if (property.getName().equals("Line Thickness")) {
            appService.setThickness((int) property.getValue());
        } else if (property.getName().equals("Text")) {
            appService.setText((String)property.getValue());
        } else if (property.getName().equals("Font size")) {
            appService.setFontSize((int)property.getValue());
        } else if (property.getName().equals("Font Family")) {
            appService.setFontSize((int) property.getValue());
        } else if (property.getName().equals("Start color")) {
            appService.setStartColor((Color) property.getValue());
        } else if (property.getName().equals("End color")) {
            appService.setEndColor((Color) property.getValue());
        } else if (property.getName().equals("IsGradient")) {
            appService.setIsGradient((Boolean) property.getValue());
        } else if (property.getName().equals("IsVisible")) {
            appService.setIsVisible((Boolean) property.getValue());
        } else if (property.getName().equals("Start x")) {
            appService.setStartX((int) property.getValue());
        } else if (property.getName().equals("Start y")) {
            appService.setStarty((int) property.getValue());
        } else if (property.getName().equals("End x")) {
            appService.setEndx((int) property.getValue());
        } else if (property.getName().equals("End y")) {
            appService.setEndy((int) property.getValue());
        } else if (property.getName().equals("Font family")) {
            Font font = appService.getFont();
            Font newFont = new Font((String) property.getValue(), font.getStyle(), font.getSize());
            appService.setFont(newFont);
        } else if (property.getName().equals("Font style")) {
            Font font = appService.getFont();
            Font newFont = new Font( font.getFamily(), (int)property.getValue(), font.getSize());
            appService.setFont(newFont);
        } else if (property.getName().equals("Font size")) {
            Font font = appService.getFont();
            Font newFont = new Font(font.getFamily(),font.getStyle(), (int) property.getValue());
            appService.setFont(newFont);
        }
    }
}
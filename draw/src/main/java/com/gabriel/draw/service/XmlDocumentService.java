package com.gabriel.draw.service;

import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Line;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.SelectionMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.service.DocumentService;
import org.w3c.dom.*;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.draw.model.Text;
import com.gabriel.draw.model.Image;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.awt.*;
import java.io.File;

public class XmlDocumentService implements DocumentService {

    Drawing drawing;

    public XmlDocumentService(Drawing drawing){
        this.drawing = drawing;
    }

    @Override
    public void save(){
        Font font;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("Drawing");

            Attr attr = document.createAttribute("color");
            attr.setValue(String.valueOf(drawing.getColor()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("filename");
            attr.setValue(drawing.getFilename());
            root.setAttributeNode(attr);

            attr =  document.createAttribute("mode");
            attr.setValue(String.valueOf(drawing.getShapeMode()));
            root.setAttributeNode(attr);

            attr =  document.createAttribute("mode");
            attr.setValue(String.valueOf(drawing.getShapeMode()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("id");
            attr.setValue(String.valueOf(drawing.getId()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("visible");
            attr.setValue(String.valueOf(drawing.isVisible()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("color");
            attr.setValue(String.valueOf(drawing.getColor()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("fill");
            attr.setValue(String.valueOf(drawing.getFill()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("text");
            attr.setValue(String.valueOf(drawing.getText()));
            root.setAttributeNode(attr);


            attr = document.createAttribute("r");
            attr.setValue(String.valueOf(drawing.getR()));
            root.setAttributeNode(attr);


            attr = document.createAttribute("x");
            attr.setValue(String.valueOf(drawing.getLocation().x));
            root.setAttributeNode(attr);

            attr = document.createAttribute("y");
            attr.setValue(String.valueOf(drawing.getLocation().y));
            root.setAttributeNode(attr);

            attr = document.createAttribute("width");
            attr.setValue(String.valueOf(drawing.getWidth()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("height");
            attr.setValue(String.valueOf(drawing.getHeight()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("startx");
            attr.setValue(String.valueOf(drawing.getStart().x));
            root.setAttributeNode(attr);

            attr = document.createAttribute("starty");
            attr.setValue(String.valueOf(drawing.getStart().y));
            root.setAttributeNode(attr);

            attr = document.createAttribute("endx");
            attr.setValue(String.valueOf(drawing.getEnd().x));
            root.setAttributeNode(attr);

            attr = document.createAttribute("endy");
            attr.setValue(String.valueOf(drawing.getEnd().y));
            root.setAttributeNode(attr);

            attr = document.createAttribute("startColor");
            attr.setValue(String.valueOf(drawing.getStartColor()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("endColor");
            attr.setValue(String.valueOf(drawing.getEndColor()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("gradient");
            attr.setValue(String.valueOf(drawing.isGradient()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("thickness");
            attr.setValue(String.valueOf(drawing.getThickness()));
            root.setAttributeNode(attr);

            font = drawing.getFont();

            attr = document.createAttribute("font-family");
            attr.setValue(font.getFamily());
            root.setAttributeNode(attr);

            attr = document.createAttribute("font-style");
            attr.setValue(String.valueOf(font.getStyle()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("font-size");
            attr.setValue(String.valueOf(font.getSize()));
            root.setAttributeNode(attr);

            attr = document.createAttribute("image");
            attr.setValue(String.valueOf(drawing.getImageFilename()));
            root.setAttributeNode(attr);

            document.appendChild(root);

            for (Shape shape : drawing.getShapes()) {

                Element element = document.createElement("Shape");
                attr = document.createAttribute("type");
                if (shape.getClass() == Line.class) {
                    attr.setValue("Line");
                } else if (shape.getClass() == Ellipse.class) {
                    attr.setValue("Ellipse");
                } else if (shape.getClass() == Rectangle.class) {
                    attr.setValue("Rectangle");
                } else if (shape.getClass() == Text.class) {
                    attr.setValue("Text");
                } else if (shape.getClass() == Image.class) {
                    attr.setValue("Image");
                }
                element.setAttributeNode(attr);

                attr = document.createAttribute("id");
                attr.setValue(String.valueOf(shape.getId()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("selected");
                attr.setValue(String.valueOf(shape.isSelected()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("visible");
                attr.setValue(String.valueOf(shape.isVisible()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("color");
                attr.setValue(String.valueOf(shape.getColor()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("alpha");
                attr.setValue(String.valueOf(shape.getColor().getAlpha()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("fill");
                attr.setValue(String.valueOf(shape.getFill()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("fill-alpha");
                attr.setValue(String.valueOf(shape.getFill().getAlpha()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("start-color");
                attr.setValue(String.valueOf(shape.getStartColor()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("start-color-alpha");
                attr.setValue(String.valueOf(shape.getStartColor().getAlpha()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("end-color");
                attr.setValue(String.valueOf(shape.getEndColor()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("end-color-alpha");
                attr.setValue(String.valueOf(shape.getEndColor().getAlpha()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("text");
                attr.setValue(String.valueOf(shape.getText()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("selection-mode");
                attr.setValue(String.valueOf(shape.getSelectionMode()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("r");
                attr.setValue(String.valueOf(shape.getR()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("x");
                attr.setValue(String.valueOf(shape.getLocation().x));
                element.setAttributeNode(attr);

                attr = document.createAttribute("y");
                attr.setValue(String.valueOf(shape.getLocation().y));
                element.setAttributeNode(attr);

                attr = document.createAttribute("width");
                attr.setValue(String.valueOf(shape.getWidth()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("height");
                attr.setValue(String.valueOf(shape.getHeight()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("startx");
                attr.setValue(String.valueOf(shape.getStart().x));
                element.setAttributeNode(attr);

                attr = document.createAttribute("starty");
                attr.setValue(String.valueOf(shape.getStart().y));
                element.setAttributeNode(attr);

                attr = document.createAttribute("endx");
                attr.setValue(String.valueOf(shape.getEnd().x));
                element.setAttributeNode(attr);

                attr = document.createAttribute("endy");
                attr.setValue(String.valueOf(shape.getEnd().y));
                element.setAttributeNode(attr);


                attr = document.createAttribute("gradient");
                attr.setValue(String.valueOf(shape.isGradient()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("thickness");
                attr.setValue(String.valueOf(shape.getThickness()));
                element.setAttributeNode(attr);

                font = shape.getFont();

                attr = document.createAttribute("font-family");
                attr.setValue(font.getFamily());
                element.setAttributeNode(attr);

                attr = document.createAttribute("font-style");
                attr.setValue(String.valueOf(font.getStyle()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("font-size");
                attr.setValue(String.valueOf(font.getSize()));
                element.setAttributeNode(attr);

                attr = document.createAttribute("image");
                attr.setValue(String.valueOf(shape.getImageFilename()));
                element.setAttributeNode(attr);

                root.appendChild(element);
            }

            // Write to XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            // Specify your local file path
            String filename = drawing.getFilename();
            StreamResult result = new StreamResult(filename);
            if(!filename.toUpperCase().endsWith(".XML") ){
                result = new StreamResult(filename + "xml");
            }
            transformer.transform(source, result);

            System.out.println("XML file created successfully!");
        }
        catch(Exception e){
            System.out.println("Error occurred " + e.getMessage());
        }
    }
    @Override
    public void open() {
        drawing.getShapes().clear();
        Shape  shape = null;
        File xmlFile = new File(drawing.getFilename());
        int id;
        int x;
        int y;
        int width;
        int height;
        int startx;
        int starty;
        int endx;
        int endy;
        boolean isvisible;
        boolean isgradient;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            NodeList nodeList = document.getElementsByTagName("Drawing");
            Node root = nodeList.item(0);
            NamedNodeMap map =  root.getAttributes();

            drawing.setColor(convertColor(map.getNamedItem("color").getNodeValue()));
            drawing.setFill(convertColor(map.getNamedItem("fill").getNodeValue()));
            drawing.setStartColor(convertColor(map.getNamedItem("startColor").getNodeValue()));
            drawing.setColor(convertColor(map.getNamedItem("endColor").getNodeValue()));
            x = Integer.parseInt(map.getNamedItem("startx").getNodeValue());
            y = Integer.parseInt(map.getNamedItem("starty").getNodeValue());
            drawing.setStart(new Point(x,y));
            x = Integer.parseInt(map.getNamedItem("endx").getNodeValue());
            y = Integer.parseInt(map.getNamedItem("endy").getNodeValue());
            drawing.setEnd(new Point(x,y));

            drawing.setVisible(Boolean.parseBoolean(map.getNamedItem("visible").getNodeValue()));
            drawing.setVisible(Boolean.parseBoolean(map.getNamedItem("gradient").getNodeValue()));

            drawing.setWidth(Integer.parseInt(map.getNamedItem("width").getNodeValue()));
            drawing.setHeight(Integer.parseInt(map.getNamedItem("height").getNodeValue()));
            drawing.setThickness(Integer.parseInt(map.getNamedItem("thickness").getNodeValue()));

            drawing.setText(map.getNamedItem("text").getNodeValue());
            drawing.setImageFilename(map.getNamedItem("image").getNodeValue());

            String fontFamily = map.getNamedItem("font-family").getNodeValue();
            int fontStyle  =  Integer.parseInt(map.getNamedItem("font-style").getNodeValue());
            int fontSize =   Integer.parseInt(map.getNamedItem("font-size").getNodeValue());
            Font font = new Font(fontFamily, fontStyle, fontSize);
            drawing.setFont(font);

            String mode = map.getNamedItem("mode").getNodeValue();
            if(mode.equals("Select")){
                drawing.setShapeMode(ShapeMode.Select);
            } else if(mode.equals("Rectangle")){
                drawing.setShapeMode(ShapeMode.Rectangle);
            } else if(mode.equals("Ellipse")){
                drawing.setShapeMode(ShapeMode.Ellipse);
            } else if(mode.equals("Line")){
                drawing.setShapeMode(ShapeMode.Line);
            } else if(mode.equals("Image")){
                drawing.setShapeMode(ShapeMode.Image);
            } else if(mode.equals("Text")) {
                drawing.setShapeMode(ShapeMode.Text);
            }




            nodeList = document.getElementsByTagName("Shape");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                map = node.getAttributes();

                x = Integer.parseInt(map.getNamedItem("x").getNodeValue());
                y = Integer.parseInt(map.getNamedItem("y").getNodeValue());
                width = Integer.parseInt(map.getNamedItem("width").getNodeValue());
                height = Integer.parseInt(map.getNamedItem("height").getNodeValue());

                Point p = new Point(x,y);
                String type = map.getNamedItem("type").getNodeValue();

                if (type.equals("Rectangle")) {
                    shape = new Rectangle(p, width, height);
                } else if (type.equals("Ellipse")) {
                    shape = new Ellipse(p, width, height);
                }else if (type.equals("Line")) {
                    shape = new Line(p, width, height);
                }else if (type.equals("Text")) {
                    shape = new Text(p, width, height);
                }else if (type.equals("Image")) {
                    shape = new Image(p, width, height);
                }
                shape.setId(Integer.parseInt(map.getNamedItem("id").getNodeValue()));
                shape.setR(Integer.parseInt(map.getNamedItem("r").getNodeValue()));
                shape.setThickness(Integer.parseInt(map.getNamedItem("thickness").getNodeValue()));

                shape.setVisible(Boolean.parseBoolean(map.getNamedItem("visible").getNodeValue()));
                shape.setGradient(Boolean.parseBoolean(map.getNamedItem("gradient").getNodeValue()));
                shape.setSelected(Boolean.parseBoolean(map.getNamedItem("selected").getNodeValue()));

                String selectionMode = map.getNamedItem("selection-mode").getNodeValue();
                if(selectionMode.equals("None")){
                    shape.setSelectionMode(SelectionMode.None);
                } else if(selectionMode.equals("UpperLeft")){
                    shape.setSelectionMode(SelectionMode.UpperLeft);
                } else if(selectionMode.equals("UpperRight")){
                    shape.setSelectionMode(SelectionMode.UpperRight);
                } else if(selectionMode.equals("LowerLeft")){
                    shape.setSelectionMode(SelectionMode.LowerLeft);
                } else if(selectionMode.equals("LowerRight")){
                    shape.setSelectionMode(SelectionMode.LowerRight);
                } else if(selectionMode.equals("MiddleTop")){
                    shape.setSelectionMode(SelectionMode.MiddleTop);
                } else if(selectionMode.equals("MiddleRight")){
                    shape.setSelectionMode(SelectionMode.MiddleRight);
                } else if(selectionMode.equals("MiddleLeft")){
                    shape.setSelectionMode(SelectionMode.MiddleLeft);
                } else if(selectionMode.equals("MiddleBottom")) {
                    shape.setSelectionMode(SelectionMode.MiddleBottom);
                } else {
                    shape.setSelectionMode(SelectionMode.None);
                }
                Color color = convertColor(map.getNamedItem("color").getNodeValue());
                int alpha = Integer.parseInt(map.getNamedItem("alpha").getNodeValue());
                Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
                shape.setColor(newColor);

                color = convertColor(map.getNamedItem("start-color").getNodeValue());
                alpha = Integer.parseInt(map.getNamedItem("start-color-alpha").getNodeValue());
                newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
                shape.setStartColor(newColor);

                color = convertColor(map.getNamedItem("end-color").getNodeValue());
                alpha = Integer.parseInt(map.getNamedItem("end-color-alpha").getNodeValue());
                newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
                shape.setEndColor(newColor);

                color = convertColor(map.getNamedItem("fill").getNodeValue());
                alpha = Integer.parseInt(map.getNamedItem("fill-alpha").getNodeValue());
                newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
                shape.setFill(newColor);

                shape.setR(Integer.parseInt(map.getNamedItem("r").getNodeValue()));

                x =Integer.parseInt(map.getNamedItem("startx").getNodeValue());
                y =Integer.parseInt(map.getNamedItem("starty").getNodeValue());
                p = new Point(x,y);
                shape.setStart(p);

                x =Integer.parseInt(map.getNamedItem("endx").getNodeValue());
                y =Integer.parseInt(map.getNamedItem("endy").getNodeValue());
                p = new Point(x,y);
                shape.setEnd(p);

                shape.setText(map.getNamedItem("text").getNodeValue());
                shape.setImageFilename(map.getNamedItem("image").getNodeValue());

                fontFamily = map.getNamedItem("font-family").getNodeValue();
                fontStyle =  Integer.parseInt(map.getNamedItem("font-style").getNodeValue());
                fontSize =   Integer.parseInt(map.getNamedItem("font-size").getNodeValue());

                font = new Font(fontFamily, fontStyle, fontSize);
                shape.setFont(font);
                drawing.getShapes().add(shape);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Color convertColor(String colorStr) {
        Color color;
        boolean hasAlpha = false;
        int rIndex = colorStr.indexOf("r=");
        int alpha;
        String rStr;
        String gStr;
        String bStr;
        String aStr;
        int rcolor;
        int gcolor;
        int bcolor;

        colorStr = colorStr.substring(rIndex + 2);
        int nextIndex = colorStr.indexOf(",");
        rStr = colorStr.substring(0, nextIndex);
        colorStr = colorStr.substring(nextIndex + 3);
        nextIndex = colorStr.indexOf(",");
        gStr = colorStr.substring(0, nextIndex);
        colorStr = colorStr.substring(nextIndex + 3);
        nextIndex = colorStr.indexOf(",");

         rcolor = Integer.parseInt(rStr);
         gcolor = Integer.parseInt(gStr);

        if(nextIndex>0) {   // Found a comma therefore there is alpha
            hasAlpha = true;
            bStr = colorStr.substring(0, nextIndex);
            bcolor= Integer.parseInt(bStr);
            nextIndex = colorStr.indexOf("]");
            aStr = colorStr.substring(0, nextIndex);
            int acode =  Integer.parseInt(aStr);
            color = new Color(rcolor, gcolor, bcolor, acode);
        }
        else {
            nextIndex = colorStr.indexOf("]");
            bStr = colorStr.substring(0, nextIndex);
            bcolor= Integer.parseInt(bStr);
            color = new Color(rcolor, gcolor, bcolor);
        }
       return color;
    }
 }

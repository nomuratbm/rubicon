package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class DrawingToolBar extends JToolBar {

    protected JTextArea textArea;
    ActionListener actionListener;

  public DrawingToolBar( ActionListener actionListener){
        setFloatable(false);
        setRollover(true);
        this.actionListener = actionListener;
        addButtons();

        setPreferredSize(new Dimension(200, 40));
        setBackground(Color.WHITE);
    }

    protected void addButtons() {
        JButton button = null;

        button = makeNavigationButton("undo", ActionCommand.UNDO, "Undo",ActionCommand.UNDO);
        add(button);

        button = makeNavigationButton("redo", ActionCommand.REDO, "Redo",ActionCommand.REDO);
        add(button);

        addSeparator();

        button = makeNavigationButton("rect", ActionCommand.RECT, "Draw a rectangle",ActionCommand.RECT);
        add(button);

        button = makeNavigationButton("line", ActionCommand.LINE, "Draw a line",ActionCommand.LINE);
        add(button);

        button = makeNavigationButton("ellipse", ActionCommand.ELLIPSE,"Draw an ellipse",ActionCommand.ELLIPSE);
        add(button);

        button = makeNavigationButton("text",ActionCommand.TEXT,"Add a text",ActionCommand.TEXT);
        add(button);

        button = makeNavigationButton("image",ActionCommand.IMAGE,"Add an  image",ActionCommand.IMAGE);
        add(button);

        //separator
        addSeparator();

        button = makeNavigationButton("select",ActionCommand.SELECT,"Switch to select",ActionCommand.SELECT);
        add(button);

        button = makeNavigationButton("imagefile",ActionCommand.IMAGEFILE,"Select another image ",ActionCommand.IMAGEFILE);
        add(button);

        button = makeNavigationButton("font",ActionCommand.FONT,"Select another font ",ActionCommand.FONT);
        add(button);

        //fourth button
        button = makeNavigationButton("delete", ActionCommand.DELETE, "Delete selected shape", ActionCommand.DELETE);
        add(button);

        //fifth component is NOT a button!
        JTextField textField = new JTextField("Ryan Reimann Layno - AM2");
        textField.setColumns(10);
        textField.addActionListener(actionListener);
        textField.setActionCommand("TEXT_ENTERED");
        add(textField);
    }

    protected JButton makeNavigationButton(String imageName,
        String actionCommand,
        String toolTipText,
        String altText) {
        //Look for the image.
        String imgLocation = "images/"
                + imageName
                + ".png";
        URL imageURL = DrawingToolBar.class.getResource(imgLocation);

        //Create and initialize the button.
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(actionListener);
        button.setOpaque(false);
        button.setBorderPainted(false);

        if (imageURL != null) {                      //image found
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {                                     //no image found
            button.setText(altText);
            System.err.println("Resource not found: "
                    + imgLocation);
        }
        return button;
    }

}

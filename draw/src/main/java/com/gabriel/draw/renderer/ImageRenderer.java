package com.gabriel.draw.renderer;

import com.gabriel.draw.model.Image;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.renderer.ShapeRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageRenderer extends ShapeRenderer {

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        if(!shape.isVisible()){
            return;
        }

        Point start = shape.getLocation();
        int x = start.x;
        int y = start.y;
        int width = shape.getWidth();
        int height = shape.getHeight();

        String imageFilename = ((Image) shape).getImageFilename();
        String imgLocation = "./images/"
                + "google48"
                + ".png";
        File input = new File(imageFilename);
        try {
            java.awt.Image bImage = ImageIO.read(input);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(shape.getThickness()));
            if(xor) {
                g2.setXORMode(shape.getColor());
                g2.drawRect(shape.getLocation().x, shape.getLocation().y, shape.getWidth(), shape.getHeight());
            }
            else {
                BufferedImage img =  resizeImage((BufferedImage) bImage, width, height);
                g2.drawImage(img, x, y, null);
                g2.setColor(shape.getColor());
                super.render(g, shape, xor);
            }

        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    public static BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }


    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }
}

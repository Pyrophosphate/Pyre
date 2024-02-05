package com.brandonoium.bithorse;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author oiumb
 */
public class BitDisplay extends JPanel {
    private BufferedImage image;
    private int pxScale = 2;

    private int tColor = new Color(255, 255, 255).getRGB();
    private int fColor = new Color(0, 0, 0).getRGB();

    private boolean[][] buffer;
    private int w = 128, h = 64;

    private long lastDrawTime = 0;


    public BitDisplay() {
        setupImage();
    }

    public BitDisplay(int width, int height) {
        w = width;
        h = height;
        setupImage();
    }

    public BitDisplay(int width, int height, int scale) {
        w = width;
        h = height;
        pxScale = scale;
        setupImage();
    }

    private void setupImage() {
        //System.out.println("Pixel Scale = " + pxScale);
        buffer = new boolean[h][w];
        image = new BufferedImage(w * pxScale, h * pxScale, BufferedImage.TYPE_INT_ARGB);
        lastDrawTime = System.nanoTime();
    }


    public void setPixel(int x, int y, boolean bit) {
        buffer[y][x] = bit;
    }

    public boolean getPixel(int x, int y) {
        return buffer[y][x];
    }

    public void flipPixel(int x, int y) {
        buffer[y][x] = !buffer[y][x];
    }

    public int getDisplayWidth() {
        return w;
    }

    public int getDisplayHeight() {
        return h;
    }

    public int getImageWidth() {
        return w * pxScale;
    }

    public int getImageHeight() {
        return h * pxScale;
    }


    public int gettColor() {
        return tColor;
    }

    public void settColor(int tColor) {
        this.tColor = tColor;
    }

    public int getfColor() {
        return fColor;
    }

    public void setfColor(int fColor) {
        this.fColor = fColor;
    }


    public void paintScaled() {
        for(int y = 0; y < image.getHeight(); y++) {
            for(int x = 0; x < image.getWidth(); x++) {
                image.setRGB(x, y, (buffer[y / pxScale][x / pxScale] ? tColor : fColor));
            }
        }
    }

    public void paintScaledSection(int xPos, int yPos, int w, int h) {
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                image.setRGB(x + xPos, y + yPos, (buffer[y / pxScale][x / pxScale] ? tColor : fColor));
            }
        }
    }


    public void awaitRefresh() {

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}

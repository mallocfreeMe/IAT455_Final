import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.lang.String;

import javax.imageio.ImageIO;

class UI extends Frame {
    int width;
    int height;

    BufferedImage appleImage;
    BufferedImage randomBlock;
    BufferedImage randomImage;

    public UI() {
        try {
            appleImage = ImageIO.read(new File("apple.png"));

        } catch (Exception e) {
            System.out.println("Cannot load the provided image");
        }
        this.setTitle("IAT455_Final");
        this.setVisible(true);

        width = appleImage.getWidth() * 4;
        height = appleImage.getHeight() * 4;

        randomBlock = ImageQuilting.randomBlock(appleImage, 50);
        randomImage = ImageQuilting.randomImage(appleImage, width / 5, height / 5, 50);

        //Anonymous inner-class listener to terminate program
        this.addWindowListener(
                new WindowAdapter() {//anonymous class definition
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }//end windowClosing()
                }
        );
    }

    public void paint(Graphics g) {
        int w = width / 5;
        int h = height / 5;


        this.setSize(w * 5 + 100, h + 80);

        g.drawImage(randomBlock, 25, 50, 50, 50, this);
        g.drawImage(randomImage, 100 + w, 50, w, h, this);
        g.drawImage(appleImage, 200 + w * 2, 50, w, h, this);
        g.drawImage(appleImage, 300 + w * 3, 50, w, h, this);

        g.setColor(Color.BLACK);
        Font f1 = new Font("Verdana", Font.PLAIN, 13);
        g.setFont(f1);

        g.drawString("1.Original CG image", 25, 40);
        g.drawString("2.Ambient occlusion mask", 125 + w, 40);
        g.drawString("3.Painted areas mask", 225 + w * 2, 40);
        g.drawString("4.Reflections layer", 325 + w * 3, 40);
    }

    public static void main(String[] args) {
        UI ui = new UI();
        ui.repaint();
    }
}

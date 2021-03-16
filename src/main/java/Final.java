//IAT455 - Workshop week 9

//**********************************************************/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.lang.String;

import javax.imageio.ImageIO;

class Final extends Frame {  //controlling class
    BufferedImage carImage;  //reference to an Image object
    BufferedImage paint_mask;  //mask for the painted areas
    BufferedImage reflection_layer;
    BufferedImage occlusion;

    int width; //width of the resized image
    int height; //height of the resized image

    BufferedImage color_corrected;
    BufferedImage paint_changed_mask;
    BufferedImage newColor_image;
    BufferedImage paint_mask_refl;
    BufferedImage shadows;
    BufferedImage refl;
    BufferedImage shiny;
    BufferedImage comp;

    public Final() {
        // constructor
        // Get an image from the specified file in the current directory on the
        // local hard disk.
        try {
            carImage = ImageIO.read(new File("car_cg.jpg")); //1
            paint_mask = ImageIO.read(new File("painted_areas_mask.jpg")); //2
            reflection_layer = ImageIO.read(new File("reflections_layer.jpg")); //3
            occlusion = ImageIO.read(new File("ambient_occlusion_layer.jpg")); //4

        } catch (Exception e) {
            System.out.println("Cannot load the provided image");
        }
        this.setTitle("Week 8 workshop");
        this.setVisible(true);

        width = carImage.getWidth();//
        height = carImage.getHeight();//

        //Anonymous inner-class listener to terminate program
        this.addWindowListener(
                new WindowAdapter() {//anonymous class definition
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);//terminate the program
                    }//end windowClosing()
                }//end WindowAdapter
        );//end addWindowListener
    }// end constructor

    public void paint(Graphics g) {
        int w = width / 5;
        int h = height / 5;


        this.setSize(w * 5 + 100, h + 80);

        g.drawImage(carImage, 25, 50, w, h, this);
        g.drawImage(occlusion, 100 + w, 50, w, h, this);
        g.drawImage(paint_mask, 200 + w * 2, 50, w, h, this);
        g.drawImage(reflection_layer, 300 + w * 3, 50, w, h, this);

        g.setColor(Color.BLACK);
        Font f1 = new Font("Verdana", Font.PLAIN, 13);
        g.setFont(f1);

        g.drawString("1.Original CG image", 25, 40);
        g.drawString("2.Ambient occlusion mask", 125 + w, 40);
        g.drawString("3.Painted areas mask", 225 + w * 2, 40);
        g.drawString("4.Reflections layer", 325 + w * 3, 40);
    }

    public static void main(String[] args) {

        Final img = new Final();//instantiate this object
        img.repaint();//render the image

    }//end main
}

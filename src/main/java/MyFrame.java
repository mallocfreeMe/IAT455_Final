import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.lang.String;

import javax.imageio.ImageIO;

class MyFrame extends Frame {
    public BufferedImage appleImage;
    public BufferedImage randomBlock;
    public BufferedImage randomImage;

    private int width;
    private int height;
    private Button selectFileButton;
    private Button generateButton;

    public MyFrame() {
        try {
            //appleImage = ImageIO.read(new File("apple.png"));
            appleImage = ImageIO.read(new File("tomato.png"));
            //appleImage = ImageIO.read(new File("paragraph.png"));
            //appleImage = ImageIO.read(new File("bucket.png"));
            //appleImage = ImageIO.read(new File("chocolate.png"));
//            appleImage = ImageIO.read(new File("flower.png"));
        } catch (Exception e) {
            System.out.println("Cannot load the provided image");
        }
        this.setTitle("IAT455_Final");
        this.setVisible(true);

        width = appleImage.getWidth() * 4;
        height = appleImage.getHeight() * 4;

        randomBlock = ImageQuilting.randomBlock(appleImage, 10);
        randomImage = ImageQuilting.randomImage(appleImage, width / 10, height / 10, 10);

        this.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );

        selectFileButton = new Button("Select");
        generateButton = new Button("Generate");
        this.add(selectFileButton);
        this.add(generateButton);

    }

    public void paint(Graphics g) {
        int w = 500;
        int h = 500;

        this.setSize(w * 4, h + 100);

        g.drawImage(appleImage, 25, 50, w, h, this);
        g.drawImage(randomBlock, 100 + w, 50, 50, 50, this);
        g.drawImage(randomImage, 200 + w * 2, 50, w, h, this);

        g.setColor(Color.BLACK);
        Font f1 = new Font("Verdana", Font.PLAIN, 13);
        g.setFont(f1);

        g.drawString("1.Original Texture image", 25, 40);
        g.drawString("2.Select a random block from the source image", 100 + w, 40);
        g.drawString("3.Generate a synthesis image by place random blocks", 205 + w * 2, 40);

        selectFileButton.setBounds(15, 550, 80, 30);
        generateButton.setBounds(90, 550, 80, 30);
    }

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        frame.setLayout(null);
        frame.repaint();
//        App app = new App();
//        app.setVisible(true);
    }
}

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.lang.String;
import java.io.*;
import javax.swing.*;


import javax.imageio.ImageIO;

class MyFrame extends Frame {
    public BufferedImage inputImage;

    public BufferedImage randomBlock;
    public BufferedImage randomImage;
    public BufferedImage neighboringBlockImage;

    private int width;
    private int height;
    public Button selectFileButton;
    public TextField blockSizeSelection;
    public String imgName, input;
    public int blockSize = 45;

    //private Button generateButton;

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        frame.setLayout(null);
        frame.repaint();
        //System.out.println("pass");

    }

    public MyFrame() {
        this.setTitle("IAT455_Final");
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        selectFileButton = new Button("Select");
        this.add(selectFileButton);

        blockSizeSelection = new TextField("45");
        this.add(blockSizeSelection);
        blockSizeSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = blockSizeSelection.getText();
                blockSize = Integer.parseInt(input);
            }
        });


        //=======User select file=========
        selectFileButton.addActionListener(new ActionListener() {
            File image;
            int response;
            JFileChooser chooser = new JFileChooser(".");

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == selectFileButton) {
                    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    response = chooser.showOpenDialog(null);

                    if (response == JFileChooser.APPROVE_OPTION) {
                        image = chooser.getSelectedFile();
                        imgName = image.getName();

                        try {
                            inputImage = ImageIO.read(new File(imgName));
                            randomBlock = ImageQuilting.randomBlock(inputImage, blockSize);
                            randomImage = ImageQuilting.randomImage(inputImage, width/5, height/5, blockSize);
                            neighboringBlockImage = ImageQuilting.neighboringBlockPlacement(inputImage,width/5, height/5, blockSize);
                        } catch (Exception error) {
                            System.out.println(error);
                        }

                        repaint();
                    }

                }

            }
        });

        if (imgName == null) {
            imgName = "BlankImage.png";
            try {
                inputImage = ImageIO.read(new File(imgName));


            } catch (Exception e) {
                System.out.println("Cannot load the provided image");
            }
        }
        width = inputImage.getWidth() * 4;
        height = inputImage.getHeight() * 4;
    }

    public void paint(Graphics g) {
        int w = 1600;
        int h = 950;
        this.setSize(w, h);


        g.setColor(Color.BLACK);
        Font f1 = new Font("Verdana", Font.BOLD, 32);
        g.setFont(f1);
        g.drawString("Texture Synthesis", w / 2 - 200, 60);


        Font f2 = new Font("Verdana", Font.BOLD, 20);
        g.setFont(f2);
        g.drawString("Instructions:", w / 2 - 750, 110);

        //=====TextField=====
        g.drawString("BlockSize:", w / 2, 140);
        blockSizeSelection.setBounds(w / 2 + 285, 120, 150, 30);
        //=====Button======
        g.drawString("Select texture Image:", w / 2, 190);
        selectFileButton.setBounds(w / 2 + 300, 170, 120, 30);

        Font f3 = new Font("Verdana", Font.PLAIN, 18);
        g.setFont(f3);
        //===instructions====
        g.drawString("1. Enter the desired block size (a value) and press enter to confirm", w / 2 - 750, 140);
        g.drawString("2. Select the desired image", w / 2 - 750, 170);

        //System.out.println(inputted);
        //==========Original Image===========
        g.drawString("Original Texture image", w / 2 + 5, 240);
        g.drawImage(inputImage, w / 2, 260, 200, 200, this);

        //==========Block Size Display===========
        g.drawString("Block Size", w / 2 + 250, 360);
        g.drawImage(randomBlock, w / 2 + 250, 370, blockSize, blockSize, this);

        //==========Random Block Method Image===========
        g.drawString("Random Placement Method", w / 2 - 750, 550);
        g.drawImage(randomImage, w / 2 - 750, 570, 300, 300, this);

        //==========Neighboring Method Image===========
        g.drawString("Neighboring Block Method", w / 2 - 175, 550);
        g.drawImage(neighboringBlockImage, w / 2 - 175, 570, 300, 300, this);

        //==========Min Cut Method Image===========
        g.drawString("Minimum Error Boundary Cut Method", w / 2 + 400, 550);
        g.drawImage(inputImage, w / 2 + 400, 570, 300, 300, this);
    }


}

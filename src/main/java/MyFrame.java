import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.lang.String;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;

class MyFrame extends Frame {
    private BufferedImage inputImage;
    private BufferedImage randomBlock;
    private BufferedImage randomImage;
    private BufferedImage neighboringBlockImage;
    private Button selectFileButton;
    private Button enterButton;
    private TextField blockSizeSelection;

    private String imgName, input;
    private int blockSize = 45;
    private int width;
    private int height;
    private int newWidth;
    private int newHeight;

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        frame.setLayout(null);
        frame.repaint();
    }

    public MyFrame() {
        this.setTitle("IAT455_Final");
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //======= User enters different block sizes =========
        blockSizeSelection = new TextField("45");
        this.add(blockSizeSelection);

        enterButton = new Button("Enter");
        this.add(enterButton);
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == enterButton) {
                    input = blockSizeSelection.getText();
                    blockSize = Integer.parseInt(input);
                    if (imgName != "BlankImage.png") {
                        randomBlock = ImageQuilting.randomBlock(inputImage, blockSize);
                        randomImage = ImageQuilting.randomPlacementImage(inputImage, newWidth, newHeight, blockSize);
                        neighboringBlockImage = ImageQuilting.neighboringBlockPlacementImage(inputImage, newWidth, newHeight, blockSize);
                        repaint();
                    }
                }
            }
        });

        //======= User selects different images =========
        selectFileButton = new Button("Select");
        this.add(selectFileButton);
        selectFileButton.addActionListener(new ActionListener() {
            File image;
            int response;
            JFileChooser chooser = new JFileChooser("./img");

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == selectFileButton) {
                    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    response = chooser.showOpenDialog(null);

                    if (response == JFileChooser.APPROVE_OPTION) {
                        image = chooser.getSelectedFile();
                        imgName = image.getName();

                        try {
                            inputImage = ImageIO.read(image);
                            randomBlock = ImageQuilting.randomBlock(inputImage, blockSize);
                            newWidth = width * 2;
                            newHeight = height;
                            randomImage = ImageQuilting.randomPlacementImage(inputImage, newWidth, newHeight, blockSize);
                            neighboringBlockImage = ImageQuilting.neighboringBlockPlacementImage(inputImage, newWidth, newHeight, blockSize);
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

        if (imgName == "BlankImage.png") {
            width = 200;
            height = 200;
        } else {
            width = inputImage.getWidth();
            height = inputImage.getHeight();
        }
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
        if (blockSizeSelection != null) {
            blockSizeSelection.setBounds(w / 2 + 285, 120, 50, 30);
        }

        //===== Enter Button =====
        if (enterButton != null) {
            enterButton.setBounds(w / 2 + 350, 120, 150, 30);
        }

        //===== Select Images Button======
        g.drawString("Select texture Image:", w / 2, 190);
        if (selectFileButton != null) {
            selectFileButton.setBounds(w / 2 + 280, 170, 120, 30);
        }

        Font f3 = new Font("Verdana", Font.PLAIN, 18);
        g.setFont(f3);

        //===instructions====
        g.drawString("1. Enter the desired block size (a value) and press enter to confirm", w / 2 - 750, 140);
        g.drawString("2. Select the desired image", w / 2 - 750, 170);

        //==========Original Image===========
        g.drawString("Original Texture image", w / 2, 240);
        g.drawImage(inputImage, w / 2, 260, width, height, this);

        //==========Block Size Display===========
        g.drawString("Block Size", w / 2 + 250, 360);
        g.drawImage(randomBlock, w / 2 + 250, 370, blockSize, blockSize, this);

        //==========Random Block Method Image===========
        g.drawString("Random Placement Method", w / 2 - 750, 550);
        g.drawImage(randomImage, w / 2 - 750, 570, newWidth, newHeight, this);

        //==========Neighboring Method Image===========
        g.drawString("Neighboring Block Method", w / 2 - 175, 550);
        g.drawImage(neighboringBlockImage, w / 2 - 175, 570, newWidth, newHeight, this);

        //==========Min Cut Method Image===========
        g.drawString("Minimum Error Boundary Cut Method", w / 2 + 400, 550);
        g.drawImage(inputImage, w / 2 + 400, 570, width, height, this);
    }
}

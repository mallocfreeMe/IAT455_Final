import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class App extends JFrame
{


    private JPanel rootMainScreen;
    private JButton selectImageButton;
    private JLabel srcImageDispaly;
    private JLabel randomizedDisplay;
    private JLabel overlapDisplay;
    private JLabel synthesizedDisplay;


    public App()
    {
        add(rootMainScreen);
        setTitle("IAT 455 Final Project");
        setSize(1100, 1200);
    }





    private void createUIComponents()
    {
        // TODO: place custom component creation code here
        srcImageDispaly = new JLabel(new ImageIcon("apple.png"));
        randomizedDisplay= new JLabel(new ImageIcon("apple.png"));
        overlapDisplay = new JLabel(new ImageIcon("apple.png"));
        synthesizedDisplay= new JLabel(new ImageIcon("apple.png"));
    }













}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleimageresizer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author MWIGO JON MARK
 */
public class SimpleImageResizer extends JFrame
{

    File image;
    BufferedImage bImage;
    
    private SimpleImageResizer()
    {
        initComponents();
    }
    
    private void initComponents()
    {
        JPanel panel1 = this.getPanel();
        Container mainPane = this.getContentPane();
        
        mainPane.add(panel1, BorderLayout.NORTH);
        
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    private JPanel getPanel()
    {
        JPanel panel1 = new JPanel();
        Box vBox = Box.createVerticalBox();
        JButton btnImage = new JButton("Choose Image File");
        JButton btnResize = new JButton("Start Resize");
        JLabel size1 = new JLabel("Image Percentage Size");
        JTextPane fileDetails = new JTextPane();
        JTextField resizedFile = new JTextField(300);
        int min = 0;
        int max = 100;
        int current = 1;
        int steps = 1;
        SpinnerNumberModel model1 = new SpinnerNumberModel(current, min, max, steps);
        JSpinner imageSize1 = new JSpinner(model1);
        JSpinner.NumberEditor sne1 = new JSpinner.NumberEditor(imageSize1, "00");
        
        imageSize1.setEditor(sne1);
        size1.setLabelFor(imageSize1);
        
        vBox.add(btnImage);
        vBox.add(new JPanel());
        vBox.add(size1);
        vBox.add(imageSize1);
        
        panel1.add(vBox, BorderLayout.WEST);
        this.add(fileDetails, BorderLayout.CENTER);
        this.add(resizedFile, BorderLayout.SOUTH);
        this.add(btnResize, BorderLayout.EAST);
        
        btnImage.addActionListener(e->{
            JFileChooser fileOpener = new JFileChooser();
            int selected = fileOpener.showOpenDialog(this);
            
            if(selected == JFileChooser.APPROVE_OPTION)
            {
                image = new File(fileOpener.getSelectedFile().getPath());
                try
                {
                    bImage = ImageIO.read(image);
                    fileDetails.setText("Original Image Details"
                            + "\n\tImage Name: "
                            +image.getName()+"\n\tFile Path: "
                            +image.getAbsolutePath()+"\n\tImage Type: "
                            +bImage.getType()+"\n\tImage Resolution: "
                            +bImage.getWidth()+" X "+bImage.getHeight());  
                }
                catch (IOException ex)
                {
                    Logger.getLogger(SimpleImageResizer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnResize.addActionListener(e->{
            
            JFileChooser fileSaver = new JFileChooser();
            int selected = fileSaver.showSaveDialog(this);
            
            if(selected == JFileChooser.APPROVE_OPTION)
            {
                int size11 = (int) imageSize1.getValue();
                
                int resizedHeight = (int) (bImage.getHeight() * (size11*0.01));
                int resizedWidth = (int) (bImage.getHeight() * (size11*0.01));
                
                System.out.println( resizedHeight+", "+resizedWidth);
                BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, bImage.getType());

                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(bImage, 0, 0, resizedWidth, resizedHeight, null);
                g2d.dispose();
                resizedImage.flush();

                try
                {
                    ImageIO.write(resizedImage, "jpg", fileSaver.getSelectedFile());
                }
                catch (IOException ex)
                {
                    Logger.getLogger(SimpleImageResizer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                resizedFile.setText(fileSaver.getSelectedFile().getPath());
            }
        });
        return panel1;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
//        EventQueue.invokeLater(new Runnable()
//        {
//            public void run()
//            {
//                new NewJFrame().setVisible(true);
//            }
//        });
        SimpleImageResizer f = new SimpleImageResizer();
    }

    
}

package gameClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//A class that creates an opening screen for the game
public class openScreen extends JFrame implements ActionListener {
    private long id;
    private int level;
    JLabel idLabel = new JLabel("ID");
    JLabel levelLabel = new JLabel("LEVEL");
    JTextField idField = new JTextField();//get ID from the user
    JTextField LevelField = new JTextField();//get num of level
    JLabel entar = new JLabel("Enter your ID and num of level:" );
    JButton loginButton = new JButton("START GAME");//Start of game

    public openScreen() throws IOException {
        super();
        setSize(700, 1000);
        setLayout(null);
        ImageIcon backdround_image = new ImageIcon("data/pokemon-go.jpeg");//Background image for the home screen
        ImageIcon resizedImage = scaleImage(backdround_image,2000,2000);//Image resizing
        setLocationAndSize();
        addActionEvent();
        //Add to the frame
        add(idLabel);
        add(levelLabel);
        add(idField);
        add(LevelField);
        add(loginButton);
        add(entar);
        //Adjust the background to the size of the opening screen
        Image img = resizedImage.getImage();
        Image temp = img.getScaledInstance(getWidth(),getHeight(),Image.SCALE_SMOOTH);
        resizedImage = new ImageIcon(temp);
        JLabel back = new JLabel("",resizedImage , JLabel.CENTER);
        back.setBounds(0,0,700,1000);
        add(back);
        setVisible(true);

    }
    //Image resizing
    public ImageIcon scaleImage(ImageIcon icon, int w, int h)
    {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();

        if(icon.getIconWidth() > w)
        {
            nw = w;
            nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }

        if(nh > h)
        {
            nh = h;
            nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
    }


    public long getId(){return id;}

    public int getLevel(){return level;}

    private void addActionEvent() {
        loginButton.addActionListener(this);
    }
    //Place sizes for each field
    private void setLocationAndSize() {
        Font font = new Font("", Font.BOLD, 20);
        entar.setFont(font);
        entar.setBounds(15,70,1000,80);
        idLabel.setBounds(120, 150, 100, 30);
        levelLabel.setBounds(100, 220, 100, 30);
        idField.setBounds(150, 150, 150, 30);
        LevelField.setBounds(150, 220, 150, 30);
        loginButton.setBounds(150, 300, 150, 30);
    }
    //Create a corresponding frame according to the data obtained
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = idField.getText();
            pwdText = LevelField.getText();
            id = Long.parseLong(userText);
            level = Integer.parseInt(pwdText);
            if(userText.length() != 9 || id < 0){
                JOptionPane.showMessageDialog(this, "Invalid ID");
            }
            else {

                Thread client = new Thread((new Ex2()));
                client.start();
                dispose();
            }

        }
    }

}
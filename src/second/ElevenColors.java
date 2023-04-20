package second;
import java.awt.*;
import java.awt.event.*;



public class ElevenColors extends Frame implements ActionListener{

    Button blue = new Button ("Blau");
    Button red = new Button ("Rot");
    Button black = new Button ("Schwarz");
    Button cyan= new Button ("Cyan");
    Button gray = new Button ("Grau");
    Button green = new Button ("Grün");
    Button magenta = new Button ("Magenta");
    Button orange = new Button ("Orange");
    Button pink = new Button ("Pink");
    Button white = new Button ("Weiß");
    Button yellow = new Button ("Gelb");



    ElevenColors(){

        red.addActionListener( this );
        blue.addActionListener( this );
        cyan.addActionListener( this );
        gray.addActionListener( this );
        green.addActionListener( this );
        magenta.addActionListener( this );
        orange.addActionListener( this );
        pink.addActionListener( this );
        white.addActionListener( this );
        yellow.addActionListener( this );
        black.addActionListener( this );



        red.setActionCommand( "red" );
        black.setActionCommand( "black" );
        cyan.setActionCommand( "cyan" );
        gray.setActionCommand( "gray" );
        green.setActionCommand( "green" );
        magenta.setActionCommand( "magenta" );
        orange.setActionCommand( "orange" );
        pink.setActionCommand( "pink" );
        white.setActionCommand( "white" );
        yellow.setActionCommand( "yellow" );
        black.setActionCommand( "black" );
        blue.setActionCommand("blue");



        setLayout(new FlowLayout());
        add(blue);
        add(red);
        add(cyan);
        add(gray);
        add(green);
        add(magenta);
        add(orange);
        add(pink);
        add(white);
        add(yellow);
        add(black);
    }

    public static void main(String[] args){
        ElevenColors frm = new ElevenColors();
        WindowQuitter wquit= new WindowQuitter();
        frm.addWindowListener( wquit );
        frm.setSize( 1000, 500);
        frm.setVisible( true );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "black":
                setBackground(Color.BLACK);
                break;
            case "red":
                setBackground(Color.RED);
                break;
            case "blue":
                setBackground(Color.BLUE);
                break;
            case "cyan":
                setBackground(Color.CYAN);
                break;
            case "gray":
                setBackground(Color.GRAY);
                break;
            case "magenta":
                setBackground(Color.MAGENTA);
                break;
            case "orange":
                setBackground(Color.ORANGE);
                break;
            case "pink":
                setBackground(Color.PINK);
                break;
            case "white":
                setBackground(Color.WHITE);
                break;
            case "yellow":
                setBackground(Color.YELLOW);
                break;
            case "green":
                setBackground(Color.GREEN);
                break;

            default:
                break;


        }

    }


}




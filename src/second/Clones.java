package second;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class  Clones extends Frame implements ActionListener {
    Button fw = new Button ("Farbwechsel");
    Button cl = new Button ("Clone");

    Clones(){
        fw.addActionListener( this );
        cl.addActionListener( this );

        fw.setActionCommand( "Farbwechsel" );
        cl.setActionCommand( "Clone" );

        setLayout(new FlowLayout());
        add(fw);
        add(cl);





    }

    public static void main(String[] args){
        Clones frm = new Clones();
        WindowQuitter wquit= new WindowQuitter();
        frm.addWindowListener( wquit );
        frm.setSize( 1000, 500);
        frm.setVisible( true );

    }
    int index = 1;
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Farbwechsel")){
        switch (index) {
            case 1:
                setBackground(Color.BLACK);
                break;
            case 2:
                setBackground(Color.RED);
                break;
            case 3:
                setBackground(Color.BLUE);
                break;
            case 4:
                setBackground(Color.CYAN);
                break;
            case 5:
                setBackground(Color.GRAY);
                break;
            case 6:
                setBackground(Color.MAGENTA);
                break;
            case 7:
                setBackground(Color.ORANGE);
                break;
            case 8:
                setBackground(Color.PINK);
                break;
            case 9:
                setBackground(Color.WHITE);
                break;
            case 10:
                setBackground(Color.YELLOW);
                break;
            case 11:
                setBackground(Color.GREEN);
                break;

        }

        index++;


        } else if (e.getActionCommand().equals("Clone")){
                Clones frm = new Clones();
                WindowQuitter wquit= new WindowQuitter();
                frm.addWindowListener( wquit );
                frm.setSize( 1000, 500);
                frm.setVisible( true );

        }

    }
}

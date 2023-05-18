import java.io.IOException;
import java.awt.EventQueue;
import view.SettingsFrame;

public class Main { 

    public static void main(String[] args)  {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                SettingsFrame tela;
                try {
                    tela = new SettingsFrame();
                    tela.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

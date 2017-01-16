import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame {

    Map jpMap;

    public MyWindow() {
        setSize(505, 587);
        setResizable(false);
        setLocation(800, 200);
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jpMap = new Map();
        jpMap.startNewGame(3,3);
        add(jpMap, BorderLayout.CENTER);

        // Основная нижняя панель
        JPanel jpBottom = new JPanel(new CardLayout());
        jpBottom.setPreferredSize(new Dimension(1, 60));
        add(jpBottom, BorderLayout.SOUTH);

        // Панель с кнопками Старт/Выход
        JPanel jpStartExit = new JPanel(new GridLayout());
        JButton jbStart = new JButton("Start New Game");
        jbStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpSelectPlayers");
            }
        });
        jpStartExit.add(jbStart);
        JButton jbExit = new JButton("Exit Game");
        jbExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jpStartExit.add(jbExit);
        jpBottom.add(jpStartExit, "jpStartExit");

        // Создание панели выбора игроков
        JPanel jpSelectPlayers = new JPanel(new GridLayout());
        JButton jbHumanVsHuman = new JButton("Человек vs. Человек");
        jbHumanVsHuman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpSelectMap");
                createP(false);
            }
        });
        JButton jbHumanVsAI = new JButton("Человек vs. AI");
        jbHumanVsAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpSelectMap");
                createP(true);
            }
        });
        jpSelectPlayers.add(jbHumanVsHuman);
        jpSelectPlayers.add(jbHumanVsAI);
        jpBottom.add(jpSelectPlayers, "jpSelectPlayers");



        // Создание панели выбора поля
        JPanel jpSelectMap = new JPanel(new GridLayout());
        JButton jbSM3x3l3 = new JButton("Поле: 3x3 Серия: 3");
        JButton jbSM5x5l4 = new JButton("Поле: 5x5 Серия: 4");
        JButton jbSM10x10l5 = new JButton("Поле: 10x10 Серия: 5");

        jpSelectMap.add(jbSM3x3l3);
        jbSM3x3l3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpStartExit");
                createMap(3,3);

            }
        });
        jpSelectMap.add(jbSM5x5l4);
        jbSM5x5l4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpStartExit");
                createMap(5,4);
            }
        });
        jpSelectMap.add(jbSM10x10l5);
        jbSM10x10l5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpStartExit");
                createMap(10,5);
            }
        });
        jpBottom.add(jpSelectMap, "jpSelectMap");

        ((CardLayout) jpBottom.getLayout()).show(jpBottom, "jpStartExit");

        setVisible(true);
    }

    public void createMap(int size, int dotwin) {
        jpMap.startNewGame(size,dotwin);
    }

    public void createP (boolean w) {
        jpMap.setW(w);
    }

}

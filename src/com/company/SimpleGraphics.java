package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Scanner;


public enum SimpleGraphics {
    INSTANCE;

    JFrame frame;
    JPanel panel;
    //public Palette palette0;
    Integer scale = 3;
    int width = 1000;
    int height = 1000;

    DotStruct[] dotMas;
    DotStruct[] examples;
    Integer curExampleVector = 0;
    Double radius = 600.0;
    Double effect0 = 0.3;
    double currentTimeMillis = 0.0;
    Integer veha =0;


    public Color[] visibleBuffer = new Color[256 * 240];


    SimpleGraphics() {


        for (int j = 0; j < 256 * 240; j++) {
            visibleBuffer[j] = Color.BLACK;
        }


        frame = new JFrame("Emul");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setTitle("Emul");
        frame.setResizable(false);
        //setUndecorated(true);

        panel = new GPanel();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        initDots(width, height, 30);


    }

    public void initDots(Integer width, Integer height, Integer masSize) {
        dotMas = new DotStruct[masSize * masSize];

        for (int i = 0; i < masSize * masSize; i++) {
            dotMas[i] = new DotStruct(width, height, true);
        }

        examples = new DotStruct[5];

        examples[0] = new DotStruct(300, 100);
        examples[1] = new DotStruct(700, 200);
        examples[2] = new DotStruct(800, 400);
        examples[3] = new DotStruct(100, 600);
        examples[4] = new DotStruct(900, 50);

        examples[0].color = Color.pink;
        examples[1].color = Color.red;
        examples[2].color = Color.green;
        examples[3].color = Color.yellow;
        examples[4].color = Color.cyan;

      /*  for(DotStruct elem: examples){
            elem.color = Color.;
        }*/

     /*   examples[4] = new DotStruct(100, 100);
        examples[5] = new DotStruct(200, 200);
        examples[6] = new DotStruct(500, 500);*/

///////////////////////////////////////////////////////////////////////////////////////////switch mas
        /*DotStruct[] temp = dotMas;
        dotMas = examples;
        examples = temp;*/
    }

    private void step() {
        Double min = 99999999.0;
        Integer indexOfMin = 0;

        //////////////////////////////////////////////////////////////////////////////////select type
        curExampleVector ++;
        //curExampleVector = (int)(Math.random()*100);
        curExampleVector %= examples.length;

        currentTimeMillis += 1;//0.00001;
        double lambda = 1000 / Math.log(radius);
        //System.out.println(lambda);
        //System.out.println(lambda);
        Double sigma = radius * Math.exp(-currentTimeMillis / lambda);
        radius = sigma;
        System.out.println(radius);
        Double effect = effect0 * Math.exp(-currentTimeMillis / lambda);

        //System.out.println(effect);


        for (int i = 0; i < dotMas.length; i++) {
            DotStruct ex = examples[curExampleVector];
            DotStruct elem = dotMas[i];

            Double delta = Math.sqrt(
                    (ex.vecInteger[0] - elem.vecInteger[0]) * (ex.vecInteger[0] - elem.vecInteger[0]) +
                            (ex.vecInteger[1] - elem.vecInteger[1]) * (ex.vecInteger[1] - elem.vecInteger[1]));

            if (Math.abs(delta) < min) {
                min = Math.abs(delta);
                indexOfMin = i;
            }
        }

        Integer newX = dotMas[indexOfMin].vecInteger[0];
        Integer newY = dotMas[indexOfMin].vecInteger[1];


        for (int j = 0; j < dotMas.length; j++) {
            //DotStruct el = dotMas[new];
            DotStruct e = dotMas[j];

            Double curRad = Math.sqrt(
                    (newX - e.vecInteger[0]) * (newX - e.vecInteger[0]) +
                            (newY - e.vecInteger[1]) * (newY - e.vecInteger[1]));

            if (Math.abs(curRad) <= radius) {
                Double d = Math.sqrt(
                        ((examples[curExampleVector].vecInteger[0]) - e.vecInteger[0]) * ((examples[curExampleVector].vecInteger[0]) - e.vecInteger[0]) +
                                ((examples[curExampleVector].vecInteger[1]) - e.vecInteger[1]) * ((examples[curExampleVector].vecInteger[1]) - e.vecInteger[1]));

                Double speed = Math.exp(-d * d / (1.5 * sigma * sigma));

                //System.out.println("------------------" + currentTimeMillis);
                //System.out.println("speed " +speed);
                //System.out.println("effect " +effect);

                /////////////////////////////////////////////////////////////////////////////////////////switch mas
                e.setColor(examples[curExampleVector].color);
                //examples[curExampleVector].setColor(e.color);

                double ddx = (examples[curExampleVector].vecInteger[0]) - e.vecInteger[0];
                double ddy = (examples[curExampleVector].vecInteger[1]) - e.vecInteger[1];
                double dx = (speed ) * effect * ddx;
                double dy = (speed ) * effect * ddy;

                /*if (dy < 0.001){
                    System.out.println("STOP");
                    return;
                }*/
                //System.out.println(dx + "  " + dy);
                e.vecInteger[0] += (int) dx;
                e.vecInteger[1] += (int) dy;
            }
        }
    }



    /*public void addPixel(Integer curPixel, Integer curLine, Integer color, Palette curPalette) {
        visibleBuffer[(256 * curLine) + curPixel] = curPalette.getColor(color);

    }*/


    class GPanel extends JPanel implements ActionListener {
        Timer timer = new Timer(10, this);

        public GPanel() {
            super();

            setDoubleBuffered(true);

            setBackground(Color.white);
            setPreferredSize(new Dimension(width, height));


            timer.start();
        }

        public void paint(Graphics g) {
            super.paint(g);


            for (DotStruct ds : examples) {
                g.setColor(ds.color);
                g.fillOval(ds.vecInteger[0], ds.vecInteger[1], 6, 6);
            }


            for (int i = 0; i < dotMas.length; i++) {
                g.setColor(dotMas[i].color);
               /* g.fillRect(dotMas[i].vecInteger[0], dotMas[i].vecInteger[1],
                        1, 1);*/

                g.fillOval(dotMas[i].vecInteger[0], dotMas[i].vecInteger[1], 7, 7);
            }

            //PPU.INSTANCE.drawScreen();

           /* for (int i = 0; i < 256 * 240; i++) {
                g.setColor(visibleBuffer[i]);


                int x = i % 256;
                int y = i / 256;
                x *= scale;
                y *= scale;
                g.fillRect(x, y, scale, scale);
                //g.drawLine(x, y, x+scale-1, y+scale-1);
                //g.setColor(Color.BLACK);
            }*/

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            step();
            veha++;
            if (veha == 30){
                System.out.println("STOP VEHA");
                Scanner in = new Scanner(System.in);
                in.nextLine();
            }
            if(radius == 1){
                System.out.println("STOP");
                Scanner in = new Scanner(System.in);
                in.nextLine();
            }

            repaint();

           /* for (int j = 0; j < 256 * 240; j++) {
                visibleBuffer[j] = Color.BLACK;
            }*/


        }


    }

    public void init() {

    }


}
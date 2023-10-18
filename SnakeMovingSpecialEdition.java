package lesson10;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SnakeMovingSpecialEdition extends JPanel implements Runnable{
    Image P = Toolkit.getDefaultToolkit().getImage("HWGD.jpg");
    Image A = Toolkit.getDefaultToolkit().getImage("a.jpg");
    Image B = Toolkit.getDefaultToolkit().getImage("bomb.png");
    ArrayList<Point> snake;
    int width=20;
    int [] bombx = new int [5];
    int [] bomby = new int [5];
    int CURRENTDIRECTION = 0;
    int LEFT = 1;
    int RIGHT = 2;
    int UP = 3;
    int DOWN = 4;
    int [] Ax=new int [5];
    int  [] Ay = new int [5];
    int GAMEOVER=1;
    int gameStatis;
    int Tx;
    int Ty;
    int Tx2;
    int Ty2;
    void generateBomb(){
        int gridCount = 500/width;
        for(int i = 0; i<bombx.length;i++){
            int bomb_grid_x = (int)(Math.random()*gridCount);
            bombx[i]  = bomb_grid_x * width; //pixel
            int bomb_grid_y = (int)(Math.random()*gridCount);
            bomby[i] = bomb_grid_y * width;}
    }
    void generateApple(){
        int gridCount = 500/width;
        for(int i = 0; i<Ax.length;i++){
            int bomb_grid_x = (int)(Math.random()*gridCount);
            Ax[i]  = bomb_grid_x * width; //pixel
            int bomb_grid_y = (int)(Math.random()*gridCount);
            Ay[i] = bomb_grid_y * width;}
    }
    void generateTP(){
        int gridCount = 500/width;
        int bomb_grid_x = (int)(Math.random()*gridCount);
        Tx = bomb_grid_x * width; //pixel
        int bomb_grid_y = (int)(Math.random()*gridCount);
        Ty = bomb_grid_y * width;
        bomb_grid_x = (int)(Math.random()*gridCount);
        Tx2 = bomb_grid_x * width; //pixel
        bomb_grid_y = (int)(Math.random()*gridCount);
        Ty2 = bomb_grid_y * width;
    }

    SnakeMovingSpecialEdition(){
        snake = new ArrayList<>();
        for (int i = 0; i<4; i++){
            int x = 240 - i * width;
            int y = 240;
            Point P = new Point(x,y);
            snake.add(P);
        }
        setPreferredSize(new Dimension(500, 500));
        setFocusable(true);
        generateBomb();
        generateApple();
        generateTP();
        System.out.println(Tx2+" "+Ty2);
        for(int i = 0; i<bombx.length; i++){
            if(bombx[i]==Ax[i]&&Ax[i]==Tx&&Ax[i]==Tx2&&Tx==Tx2&&Tx==bombx[i]){repaint();}
        }
        KeyAdapter ka = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println("I detected a key pressed :"
                        + e.getKeyCode() + " " + e.getKeyChar());
                int kc = e.getKeyCode();
                Point head = snake.get(0);


                if (kc == KeyEvent.VK_W&&CURRENTDIRECTION!=DOWN) {
                        CURRENTDIRECTION = UP;
                    }

                else if (kc == KeyEvent.VK_S&&CURRENTDIRECTION!=UP) {
                        CURRENTDIRECTION = DOWN;
                    }
                else if (kc == KeyEvent.VK_A&&CURRENTDIRECTION!=RIGHT) {
                        CURRENTDIRECTION = LEFT;

                    }
                else if (kc == KeyEvent.VK_D&&CURRENTDIRECTION!=LEFT) {
                        CURRENTDIRECTION = RIGHT;
                    }

                    repaint();


            }
        };
        addKeyListener(ka);
        new Thread(this).start();
    }
    public void run() {
        Point oldSnakeH = snake.get(0);
        while (true) {
            try{
                Thread.sleep(100
                );
            }catch(InterruptedException e){

            }
            repaint();
            Point newHead = null;
            int newHeadX;
            int len = snake.size();
            int newHeadY;
//            Color ramdom = (Color.getColor())
            if (CURRENTDIRECTION == RIGHT) {
                newHeadX = snake.get(0).x + width;
                newHead = new Point(newHeadX, snake.get(0).y);
                if(newHeadX>=500){gameStatis=GAMEOVER; break;}
            } else if (CURRENTDIRECTION == LEFT) {

                newHeadX = snake.get(0).x - width;
                newHead = new Point(newHeadX, snake.get(0).y);
                if(newHeadX<0){gameStatis=GAMEOVER; break;}


            } else if (CURRENTDIRECTION == UP) {

                newHeadY = snake.get(0).y - width;
                newHead = new Point(snake.get(0).x, newHeadY);
                if(newHeadY<0){gameStatis=GAMEOVER; break;}

            } else if (CURRENTDIRECTION == DOWN) {

                newHeadY = snake.get(0).y + width;
                newHead = new Point(snake.get(0).x, newHeadY);
                if(newHeadY>=500){gameStatis=GAMEOVER; break;}
            }
            if (newHead != null) {
                if (snake.contains(newHead)) {
                    gameStatis = GAMEOVER;
                    break;
                }

                for (int i = 0; i < bombx.length; i++) {
                    if(snake.size()>0) {
                        if (snake.get(0).x == bombx[i] && snake.get(0).y == bomby[i]) {
                            snake.remove(len - 1);
                            bombx[i]=width*(int)(Math.random()*500/width);
                            bomby[i]=width*(int)(Math.random()*500/width);
//                            generateBomb();
                        }
                    }
                }

                if(snake.get(0).equals(new Point(Tx2,Ty2))&&!oldSnakeH.equals(new Point(Tx,Ty))){
                    System.out.println("this is the 2nd point");
                    oldSnakeH = newHead;
                    System.out.println(Tx2+" "+Ty2);
                    oldSnakeH = snake.get(0);
                    newHead.x=Tx;
                    newHead.y=Ty;
                    System.out.println(newHead.x+" "+newHead.y);

                }
                else if(snake.get(0).equals(new Point(Tx,Ty))&&!oldSnakeH.equals(new Point(Tx2,Ty2))){
                    System.out.println("this is the 1st point");
                    System.out.println(Tx + " " + Ty);
                    oldSnakeH = newHead;
                    newHead.x=Tx2;
                    newHead.y=Ty2;
                    System.out.println(newHead.x + " " + newHead.y);

                }
                boolean eat = false;
                for (int lol = 0; lol<Ax.length; lol++) {

                    if (newHead.equals(new Point(Ax[lol], Ay[lol]))) {
                        int gridCount = 500/width;
                        int bomb_grid_x = (int)(Math.random()*gridCount);
                        Ax[lol]  = bomb_grid_x * width; //pixel
                        int bomb_grid_y = (int)(Math.random()*gridCount);
                        Ay[lol] = bomb_grid_y * width;
                        eat=true;
                        break;
                    }
                }
                snake.add(0,newHead);
                if(!eat){

                    snake.remove(snake.size()-1);
                }
                if (snake.size() == 0) {
                    gameStatis = GAMEOVER;
                    break;
                }

            }


        }
    }
    public void paintComponent (Graphics g){

        super.paintComponent(g);
        //entire background/field
        g.setColor(Color.green);
        g.fillRect(0, 0, getWidth(), getHeight());
        //snake head
        g.setColor(Color.BLUE);
        g.drawImage(P,Tx,Ty,width,width,this);
        g.drawImage(P,Tx2,Ty2,width,width,this);
        g.setColor(Color.blue);
        for (int i = 1; i<snake.size(); i++){
            g.fillOval(snake.get(i).x,snake.get(i).y,width,width);
        }
        g.setColor(Color.black);
        if(snake.size()>0){
            g.fillOval(snake.get(0).x,snake.get(0).y,width,width);
        }
        g.setColor(Color.RED);
        for(int k = 0; k<Ax.length; k++){
            g.drawImage(A,Ax[k],Ay[k],width,width,this);
        }
        //draw bombs
        g.setColor(Color.black);
        for(int i = 0; i<bombx.length; i++)
            g.drawImage(B,bombx[i],bomby[i],width,width,this);
        //collision

        if(gameStatis == GAMEOVER){

            g.setColor(Color.black);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));  
            g.drawString("Gameover",100, getHeight()/2);
        }

    }



    public static void main (String argv[]) {
        JFrame myFrame = new JFrame("Moving Snake game thats boring.");
        SnakeMovingSpecialEdition myPanel = new SnakeMovingSpecialEdition();
        myFrame.add(myPanel);
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }
}


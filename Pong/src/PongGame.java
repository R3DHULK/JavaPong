import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 20;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BALL_DIAMETER = 20;
    private static final int BALL_SPEED = 3;

    private int paddle1Y = HEIGHT / 2;
    private int paddle2Y = HEIGHT / 2;
    private int ballX = WIDTH / 2;
    private int ballY = HEIGHT / 2;
    private int ballXSpeed = BALL_SPEED;
    private int ballYSpeed = BALL_SPEED;
    private int player1Score = 0;
    private int player2Score = 0;

    public PongGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });

        Timer timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        timer.start();
    }

    private void update() {
        // Update ball position
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Check for collision with paddles
        if (ballX <= PADDLE_WIDTH && ballY + BALL_DIAMETER >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            ballXSpeed = BALL_SPEED;
        } else if (ballX >= WIDTH - PADDLE_WIDTH - BALL_DIAMETER && ballY + BALL_DIAMETER >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            ballXSpeed = -BALL_SPEED;
        }

        // Check for collision with top and bottom walls
        if (ballY <= 0 || ballY >= HEIGHT - BALL_DIAMETER) {
            ballYSpeed = -ballYSpeed;
        }

        // Check for score
        if (ballX <= 0) {
            player2Score++;
            reset();
        } else if (ballX >= WIDTH - BALL_DIAMETER) {
            player1Score++;
            reset();
        }
    }

    private void reset() {
        ballX = WIDTH / 2;
        ballY = HEIGHT / 2;
        ballXSpeed = BALL_SPEED;
        ballYSpeed = BALL_SPEED;
    }

    private void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP && paddle2Y > 0) {
            paddle2Y -= 5;
        } else if (keyCode == KeyEvent.VK_DOWN && paddle2Y < HEIGHT - PADDLE_HEIGHT) {
            paddle2Y += 5;
        } else if (keyCode == KeyEvent.VK_W && paddle1Y > 0) {
            paddle1Y -= 5;
        } else if (keyCode == KeyEvent.VK_S && paddle1Y < HEIGHT - PADDLE_HEIGHT) {
            paddle1Y += 5;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw paddles
        g.setColor(Color.WHITE);
        g.fillRect(0, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Draw ball
        g.fillOval(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);

        // Draw score
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Player 1: " + player1Score, 20, 30);
        g.drawString("Player 2: " + player2Score, WIDTH - 150, 30);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        PongGame game = new PongGame();
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

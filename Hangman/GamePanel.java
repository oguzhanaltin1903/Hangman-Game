package Hangman;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    private int WIDTH;
    private int HEIGHT;
    private Question question;
    private boolean gameOver;

    private StringBuilder guessedAnswer;
    private char guessedLetter;
    private int lives;
    private int questionIndex;
    private Player player;
    private JTextField playerNameTextField;
    private Set<Character> guessedLetters;
    private JLabel feedbackLabel;
    private JButton restartButton;

    GamePanel() {
        WIDTH = 1600;
        HEIGHT = 900;
        question = new Question();
        gameOver = false;

        playerNameTextField = new JTextField();
        playerNameTextField.setBounds(160, 250, 200, 30);

        player = new Player();

        guessedAnswer = new StringBuilder();
        guessedLetter = ' ';
        lives = 8;
        questionIndex = 0;

        JLabel playerNameLabel = new JLabel("Player Name:");
        playerNameLabel.setBounds(50, 250, 100, 30);

        feedbackLabel = new JLabel();
        feedbackLabel.setPreferredSize(new Dimension(500, 50));
        feedbackLabel.setHorizontalAlignment(JLabel.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 28));
        feedbackLabel.setForeground(Color.WHITE);
        add(feedbackLabel);

        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        Timer timer = new Timer(1000 / 60, this);
        timer.start();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        guessedLetters = new HashSet<>();

        restartButton = new JButton();
		restartButton.setText("Yeniden Başla");
		restartButton.setSize(300,300);
		restartButton.setLocation(500,200);

		restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameOver) {
					if(e.getSource()==restartButton){
						restart(); 
					}
        
                }
            }
        });
        restartButton.setVisible(false); // Hide initially
        add(restartButton);

        newGame(); // Start a new game
    }

    public void newGame() {
        String playerName = JOptionPane.showInputDialog(null, " Lütfen oyuncu adını giriniz:" , "Hangman Giriş Ekranı" , JOptionPane.INFORMATION_MESSAGE );
        if (playerName != null && !playerName.isEmpty()) {
            player.setName(playerName);
            System.out.println("Player Name: " + player.getName());
            getQuestion();
        } 
		else {
            JOptionPane.showMessageDialog(null, "Oyunu başlatmak için bir isim girmelisiniz!");
			System.exit(0);
        }
    }

    private void getQuestion() {
        String playerName = playerNameTextField.getText();
	        player.setName(playerName);
	        System.out.println("Player Name: " + player.getName());
	        
	        HashMap<String, String> questionAnswer = question.getQuestionAnswer();
	        questionIndex = 0;
	
	        String currentQuestion = question.getQuestion(questionIndex);
	        String answer = question.getAnswer(currentQuestion);
	        guessedAnswer = createGuessedAnswer(answer);
    }

	private void restart(){

		remove(restartButton);
        Window window = SwingUtilities.windowForComponent(GamePanel.this);
        window.dispose();
        GameFrame game = new GameFrame();
		add(game); // Start a new game
        gameOver = false; 
	}

    private boolean isLetterAlreadyGuessed(char letter) {
        return guessedLetters.contains(Character.toLowerCase(letter));
    }

    public StringBuilder createGuessedAnswer(String answer) {
        StringBuilder guessedAnswer = new StringBuilder();
        for (int i = 0; i < answer.length(); i++) {
            char currentChar = answer.charAt(i);
            if (Character.isLetter(currentChar)) {
                guessedAnswer.append("_");
            } else {
                guessedAnswer.append(currentChar);
            }
            guessedAnswer.append(" ");
        }
        return guessedAnswer;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        String currentQuestion = question.getQuestion(questionIndex);

        if (lives != 8) {
            g.drawString("Skor: " + player.getScore(), 700, 750);
        }

        g.drawString("Oyuncu Adı: " + player.getName(), 25, 25);

        if (lives == 8) {
            g.drawString("HOŞ GELDİNİZ!", 700, 400);
            g.drawString("JAVA ÖĞRENMEYE BAŞLAMAK İÇİN BİR DÜĞMEYE BASIN!", 500, 450);
            restartButton.setVisible(false); // Hide restart button initially
        } else {
            g.drawString("Soru:", 50, 50);
            drawWrappedString(g, currentQuestion, 50, 80, 20, WIDTH - 100);
            g.drawString("Kalan Canlar " + lives, 50, 150);
            g.drawString("Tahmin Edilen Cevap: " + guessedAnswer.toString(), 50, 180);
            restartButton.setVisible(gameOver); // Show restart button when game is over
        }

        drawHangman(g);

        if (guessedLetter != ' ' && lives != 8) {
            g.drawString("Tahmin Edilen Harf: " + guessedLetter, 50, 230);
        }

        if (lives == 0) {
            gameOver(g);
        }
    }
	
	private void drawWrappedString(Graphics g, String text, int x, int y, int lineHeight, int maxWidth) {
		String[] words = text.split(" ");
		StringBuilder currentLine = new StringBuilder(words[0]);

		for (int i = 1; i < words.length; i++) {
			String word = words[i];
			String testLine = currentLine + " " + word;
			int lineWidth = g.getFontMetrics().stringWidth(testLine);

			if (lineWidth < maxWidth) {
				currentLine.append(" ").append(word);
			} else {
				g.drawString(currentLine.toString(), x, y);
				y += lineHeight;
				currentLine = new StringBuilder(word);
			}
		}

		g.drawString(currentLine.toString(), x, y);
	}

	public void drawHangman(Graphics g) {
		int x = 200;
		int y = 300;
		int width = 100;
		int height = 100;

		if (lives <= 7) {
			g.drawLine(x-100, y + height + 300, x+100, y + height + 300);
			g.drawLine(x, y, x, y + height + 300);
			g.drawLine(x, y, x+x, y);
		}

		if (lives <= 6) {
			g.drawLine((2*x)-50, y, (2*x)-50, y+50);
		}
		if (lives <= 5) {
			g.drawOval(x + width, y+50, width, height);
		}
		if (lives <= 4) {
			g.drawLine(x+150, y + height+50 , x+150, y+250); 
		}
		if (lives <= 3) {
			g.drawLine(x+150, y + height+50 , (x - width / 2)+150, (y + height +100 / 2)+50);  // Head
		}
		if (lives <= 2) {
			g.drawLine(x+150, y + height+50 , (x + width / 2)+150, (y + height +100/ 2)+50);
		}
		if (lives <= 1) {
			g.drawLine(x+150, y + height+150, (x + width-50)+150, (y + height * 3+50/ 2)+50); 
		}
		if (lives <= 0) {
			g.drawLine(x+150, y + height+150, (x - width+50)+150, (y + height * 3+50/ 2)+50);
		   

		}
	}

	public void gameOver(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("Oyuncu Adı: " + player.getName(), WIDTH / 2 -150, HEIGHT / 2 -50);
		g.drawString("Öldün... Java hakkında daha çok şey öğrenmelisin.", WIDTH / 2 -300, HEIGHT / 2);
		g.drawString("Skorunuz:" + player.getScore(), WIDTH / 2 -125, HEIGHT / 2 +50);

	  
		gameOver = true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (!gameOver) {
				char keyChar = Character.toLowerCase(e.getKeyChar());
				
				if (!isLetterAlreadyGuessed(keyChar)) {
					guessedLetter = keyChar;
					boolean found = false;

					String currentQuestion = question.getQuestion(questionIndex);
					String answer = question.getAnswer(currentQuestion);

					StringBuilder updatedAnswer = new StringBuilder(guessedAnswer);
					for (int i = 0; i < answer.length(); i++) {
						char currentChar = Character.toLowerCase(answer.charAt(i));
						if (Character.isLetter(currentChar) && currentChar == guessedLetter && updatedAnswer.charAt(i * 2) == '_') {
							updatedAnswer.setCharAt(i * 2, answer.charAt(i));
							found = true;
						}
					}

					if (!found) {
						lives--;
						feedbackLabel.setText("Yanlış Tahmin");
						player.decrementScore();
						
						guessedLetters.add(guessedLetter);
					} 
					else {
						feedbackLabel.setText("Tebrikler! Doğru Tahmin");
						player.incrementScore();
					}

					guessedAnswer = updatedAnswer;

					if (!guessedAnswer.toString().contains("_")) {
						questionIndex++;
						if (questionIndex >= question.getQuestionAnswer().size()) {
							gameOver = true;
						} 
						else {
							String nextQuestion = question.getQuestion(questionIndex);
							String nextAnswer = question.getAnswer(nextQuestion);
							guessedAnswer = createGuessedAnswer(nextAnswer);
						}
					}
				}
			}
		}
	}
}
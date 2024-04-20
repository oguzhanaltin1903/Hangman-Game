package Hangman;

public class Player {
    private String name;
    private int score=10;
    
    public Player() {
        this.name = name;
        this.score=score;
    }

    public void incrementScore() {
    	score += 10;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void decrementScore() {
    	
    	if(score==0) {
    		score = 0;
    	}
    	else {
    		score -= 10;
    	}
   	 
    }
    
    public void setScore(int score) {
        this.score=score;
    }
}

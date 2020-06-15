package alexsimi.com.github;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import alexsimi.com.github.model.Card;
import alexsimi.com.github.model.Rank;
import alexsimi.com.github.model.Suit;

public class MainActivity extends AppCompatActivity {

    //fields other
    private ArrayList<Card> deck;
    private Card humanCard;
    private Card computerCard;
    private int round;
    private int humanScore;
    private int computerScore;

    //fields
    private TextView round_tv;
    private ImageView leftCard;
    private ImageView rightCard;
    private TextView humanScoreTv;
    private TextView computerScoreTv;
    private TextView winnerText;
    private Button dealButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        round_tv = findViewById(R.id.round_text_view);
        leftCard = findViewById(R.id.left_card);
        rightCard = findViewById(R.id.right_card);
        humanScoreTv = findViewById(R.id.human_score);
        computerScoreTv = findViewById(R.id.computer_score);
        winnerText = findViewById(R.id.winner_text_view);
        dealButton = findViewById(R.id.deal_button);
        createDeck();

   }

    public void createDeck()
    {
        int firstCardId = R.drawable.ac02 ;

        deck = new ArrayList<>(52);

        for (Suit suit : Suit.values())
        {
            for (Rank rank : Rank.values())
            {
                deck.add(new Card(suit, rank, firstCardId));
                firstCardId += 1;
            }
        }

        Collections.shuffle(deck);
    }

    public void onDeal(View v)
    {
        round++;
        humanCard = deck.remove(0);
        computerCard = deck.remove(0);

        if (humanCard.getRank().ordinal() > computerCard.getRank().ordinal())
            humanScore++;
        else if (humanCard.getRank().ordinal() < computerCard.getRank().ordinal())
            computerScore++;

        updateUI();

        if(deck.size() == 0)
        {
            checkWinner();
            dealButton.setEnabled(false);
            return;
        }

    }

    public void onReset(View v)
    {
        //reset deck
        deck.clear();
        int firstCardId = R.drawable.ac02 ;
        for (Suit suit : Suit.values())
        {
            for (Rank rank : Rank.values())
            {
                deck.add(new Card(suit, rank, firstCardId));
                firstCardId += 1;
            }
        }

        Collections.shuffle(deck);
        round = 0;
        humanScore = 0;
        computerScore = 0;
        resetUI();
    }

    public void updateUI()
    {
        leftCard.setImageResource(humanCard.getResourceId());
        rightCard.setImageResource(computerCard.getResourceId());
        round_tv.setText(String.valueOf(round));
        humanScoreTv.setText(String.valueOf(humanScore));
        computerScoreTv.setText(String.valueOf(computerScore));
    }

    public void checkWinner()
    {
        winnerText.setVisibility(View.VISIBLE);
        if(humanScore > computerScore)
        {
            winnerText.setText("You are the winner!");
        }
        else if(humanScore < computerScore)
        {
            winnerText.setText("Computer is the winner!");
        }
        else
        {
            winnerText.setText("It's a tie!");
        }
    }

    public void resetUI()
    {
        round_tv.setText("-");
        leftCard.setImageResource(R.drawable.az);
        rightCard.setImageResource(R.drawable.az);
        humanScoreTv.setText(String.valueOf(humanScore));
        computerScoreTv.setText(String.valueOf(computerScore));
        winnerText.setVisibility(View.GONE);
        dealButton.setEnabled(true);
    }
}
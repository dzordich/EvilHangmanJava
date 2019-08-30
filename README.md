This is a Java remake of one of my favorite Python projects from Momentum Learning [(original repo)](https://github.com/momentum-cohort-2019-05/w2-mystery-word-dzordich). 

## Evil Hangman

From the [Sanford Computer Science Evil Hangman exercize](http://nifty.stanford.edu/2011/schwarz-evil-hangman/). 

Evil Hangman is an adaptation of the classic hangman guessing game. In the original game, one player will pick a word while the other player, with only the knowledge of the word's length, must guess the word one letter at a time. The player loses if they run out of guesses before they guess the word.

As the name implies, Evil Hangman is a twisted version of the game where your opponent (the computer) never actually picks a word. Instead, it will give you a set length of the word to guess, but keep a running list of all the words of this length. As you guess letters, the computer attempts to keep the word list as long as possible. For example, if the player were to guess the letter A, the computer would group all the words with A in the same position and all the words that do not contain the letter A. If the group that does not contain A is the largest of these groups, the computer will tell you that you've guessed incorrectly. However, say there are more words with A in the second position than words with A in any other position and words that do not contain A (e.g., -A----); the computer will award you with a correct guess and reveal A in the second position. Now the word list will only contain words with A in the second letter position. The game is nearly impossible to beat.

=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D arrays - This Wordle implementation uses 2 2D arrays to store the state of the game board. One of the arrays
  stores integers for each cell that indicate the color that the cell should be colored. 0 represents that the letter
  has no guess (i.e. it's in the 3rd row, but the player has only inputted 2 guesses). 1 represents that the guessed
  letter is not in the correct word at all. 2 represents that the guessed letter is in the word, but in the wrong
  position, so it will be displayed as yellow. 3 represents that the guessed letter is in the correct place in the
  word and it will be displayed as green.

  I have another array to store the characters that are gonna be displayed in each cell. The advantage of using these
  2D arrays is the easy of iterating through the rows columns first and then the rows which reflects the
  turns of the game. The use of 2 different arrays allows for the colors and the letters to be updated at different
  times. The colors would only be updated at the end of the round while the letters are updated within the round.


  2. Collections - This Wordle implementation uses a collection, specifically an ArrayList to store the words in the
  dictionary that represent the possible answer options and input possibilities. The ArrayList makes it easy to select
  a random word by first selecting a random index. It also make it easy to check if an inputted guess is in the
  internal dictionary of words.

  A LinkedList was used to store the player's submitted guesses which was advantageous because the LinkedList preserved
  the order of the guesses. It also made accessing the last guess simple.

  TreeMaps were generated for the guessed word and answer word when checking the answers to update the colors. These
  maps mapped letters to the counts of the letters so that it can handle cases where there are multiple duplicates.

  3.File I/O - this implementation uses File I/O to save the game state in a text file that can later be loaded to
  resume the game. It stores all of the pertinent fields in the Wordle object.

  4.My game has an internal state stored in a 2D array that represents the letters of all of the guesses.
   I tested the state after various method calls such as addLetter and delLetter. I also wrote helper methods in Wordle
   class that allowed me to access the fields of the internal state.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

    The Wordle class makes up the model part of the MVC framework. It stores all of the game state, including
    the guessed characters for each cell, the colors of each cell, the round number, the column number, the guessed
    answers, and the dictionary.

    The LetterBox and Instructions classes helped modularize the paintComponent part of the GameBoard. The GameBoard
    class handles most of the view and controller functionality of the MVC framework, while Run Wordle initializes the
    top level frame and the initial view.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

    The function to checkLetters in a guess was complex because of many edge cases.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

    I think overall, my design did a good job of separating functionality and encapsulating private state of the model.
    If given the chance, I would streamline some of my functions such as the checkLetters function.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

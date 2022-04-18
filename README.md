# Word-Puzzle-Game

## Table of Contents

* [About the Project](#about-the-project)
* [General Information](#general-information)
* [Game Steps](#game-steps)
* [Console View](#console-view)
* [Built With](#built-with)
* [References](#references)
* [Contact](#contact)

## About The Project
The aim of this project is to develop a two-player game that guesses correctly the specific words in the puzzle table. 
When all spaces are properly filled with the words, the puzzle will be completed.

## General Information
Word puzzle game consists of a puzzle matrix (15 x 15) and a word list (dictionary, max. 100 words), given as text files, with the names puzzle.txt and word_list.txt respectively. The puzzle.txt file includes binary values where the 1s symbolize the letters and 0s indicate empty spaces. After these files are read, the screen is designed and the game starts

## Game Steps
Each player guesses a letter from the word list and try to find a word. When a player places a letter in a position, 
if the placement is correct, the player gets point and continues to insert letters, if it is inappropriate, the turn goes to the second player. 
The second player tries to guess another word by placing a letter. The player gets one point for each correct action. A word is completed when its last letter 
is guessed. The player who completes a word gets 10 points. When all the words are completed on the board, the game ends and the player who has the highest score
wins the game. 

In the project, lots of data structures used. Single Link List (SLL) and Multi Linked List (MLL) for sorting the words alphabetically. And Double Linked List (DLL) for sorting and storing the high score table.

## Console View
![alt text](https://github.com/enes-sahinn/Word-Puzzle-Game/blob/master/console_view.PNG)

To get the poster of the project: [Poster Link](https://github.com/enes-sahinn/Word-Puzzle-Game/blob/master/poster.png)

## Built With
* Java

## References
* https://www.rapidtables.com/web/color/RGB_Color.html
* https://stackoverflow.com/questions/22185683/read-txt-file-into-2d-array

## Contact
Mail: enessah200@gmail.com\
LinkedIn: [linkedin.com/in/enes-sahinn](https://www.linkedin.com/in/enes-sahinn/)\
Project Link: [Word Puzzle Game](https://github.com/enes-sahinn/Word-Puzzle-Game)

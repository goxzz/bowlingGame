# Bowling Game

This is a short guide to explain how the game was made and how to run it.

The game was made using the following technologies:
  
  - Java 8
  - Maven
  - Git (to versionate the code)
  - Junit
  - Java libraries
  
The project was made using interfaces to ensure code reusability, easy unit testing and quick refactoring.
  
The game logic is stored in the Bowling implementation for Playable, wich obtains the game data from an input file,
wich is handled by the GameLoader implementation of loadable, wich reads the file and and validates the players data
is valid and that all players have completed their turns. This is handled in the GameLoader class, to ensure that the 
loaded data is consistent and optimal, instead of just reading any data and validating it during the match.
  
Bonus:
 
  Extended use of:
  
   - Java 8 lambdas
   - Java 8 streams
 
How to run the game

You have two ways of running the game:

  - Importing the project into your chosen ide
  - Running the executable generated jar file into the terminal
  
Import the project into your chosen ide

  - You have to clone the following git reposiroty:
  
    https://github.com/goxzz/bowlingGame.git
    
    User: gonzalo.aranda951@yahoo.com.ar
    
    You can do it in the terminal using git clone https://github.com/goxzz/bowlingGame.git
    
    Also you can import it in you ide using Git import
    
    You will have yo switch to the master branch to acces the code.
    
    - git checkout master
    
    - git pull
    
  - Import the project into your ide using file -> import
  
  - Import maven existing project
  
  - Select the directory of your cloned repository
  
  - Import
  
  Now you project is configured to run from your ide (you may need to do a maven update if needed).
  
  To run the project execute the Application class, stored in com.bowling.app
  
  The project is provided with an input file called "bowling.txt" in the folder Gamedata.
  
  By default the game will search the file in the directory "Gamedata/bowling.txt"
  
 Run the game in the terminal
 
  - You can generate an execuatble jar file of the project or used the one provided. It has to be in this folder structure:
  
    - A folder with any name wich will contain the Bowling.jar executable and a Gamedata folder on the same level.
    
    - Inside the Gamedata folder you will encounter the provided bowling.txt file wich contains a test match.
    
    As long as you keep the file name as "bowling.txt" you can replace the content however you want.
  
  - To run the executable:
  
    - Open a new terminal
    
    - Go to the directory where the game folder is
    
    - type: Java -jar BowlingGame.jar
    
    Take in count the executable name may change depending on how you imported it.
    
    DISCLAIMER: You may want to use a full screen terminal since the game display is large and if you run it minimized,
    
    the display may show imcompleted, altought the game generates the display perfectly.
    
    Just see the terminal on full screen and it will show just fine.
  

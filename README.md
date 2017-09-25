# Simple Spaceship Game
This is a simple spaceship/asteroid shooting game developed in Java.
___
# Requirements
You will need Java's Development Kit (JDK) to compile this. You can download it [here.](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html) This is the most updated link at the date of this README, which is Sep. 24, 2017.
___
# How to Compile
1. Open a command prompt.
2. Make sure you cd into right outside "javaFiles." i.e. You will need to be in the "spaceshipGame" folder.
3. Type into the command line
<br />`javac javaFiles/*`
<br />NOTE: If you are on Windows and this does not work, you will need to add the path of "javac.exe" into your PATH variable. This could be VERY disastrous. I recommend going on YouTube and looking up a tutorial so you do not accidentally destroy your computer.
<br />Nevertheless, here are my instructions. The path is either `C:\Program Files\Java\jdk_YOUR_VERSION\bin` if you are on a 64-bit machine, or `C:\Program Files (x86)\Java\jdk_YOUR_VERSION\bin` if you are on a 32-bit machine. To change your PATH variable, go into `Control Panel -> System -> Advanced system settings (this is on the left side) -> Environment Variables`. Once here, you can set the javac path in either the user PATH variable or the system PATH variable. If the user PATH variable does not work, try setting it in your PATH variable.
4. Once the project finishes compiling, run the game by typing into the command line
<br />`java javaFiles.SpaceshipGo`
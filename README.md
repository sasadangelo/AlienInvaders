# Alien Invaders

Alien Invaders is a simplified clone of Space Invaders. A spacecraft must defend the earth from an alien attack. The army alien consists of 3 categories of aliens: ugly, bad and good. The spacecraft must destroy the alien army without getting hit and keep her from arriving on Earth. The only defense for the spacecraft are 4 blocks behind which it can shelter from the aliens bullets. For each alien kill the player will get a score. The aliens move in block from left to right and vice versa, and every time they touch a margin, they advance. The player advances to level when he kills all the aliens. At each new level the speed of the aliens increases, thus increasing the difficulty of the game.

This game has been created only for educational purpose, it has no claim to be a complete game distributable through the Android market. It's my belief that you can get inspiration from this source code to implement your own video games.

# Screenshots

![Main Menu](https://raw.githubusercontent.com/wiki/sasadangelo/AlienInvaders/img/Screenshot_Alien_Invaders_Home.png) ![Game](https://raw.githubusercontent.com/wiki/sasadangelo/AlienInvaders/img/Screenshot_Alien_Invaders.png)

# Video Demo
[![Video Demo](https://raw.githubusercontent.com/wiki/sasadangelo/AlienInvaders/img/AlienInvaders_Video.png)](https://www.youtube.com/watch?v=C8KQJOkNzsU "Video Demo")

# Limitations

In the game there is no the upper spacecraft to hit as a bonus as in the original game. The game also has an infinite theoretical life.

# Credits

The original author of the framework code, later modified by me, is [Mario Zachner](https://github.com/badlogic) (@badlogic) that released the code with GPL3 license as a resource of the Beginning Android Games book. The framework is a very simplified version of the open source library Libgdx, released by Mario Zachner with GPL3 license. Graphic assets and some simplifications comes from the open source project [rubyinvaders](https://github.com/llexileon/rubyinvaders) written in Ruby by [Robert Alexander Leon](https://github.com/llexileon) (@llexileon).

# License
[GPL3](https://www.gnu.org/licenses/gpl-3.0.en.html)

# Related Projects

[Droids](https://github.com/sasadangelo/Droids), [Mr Snake](https://github.com/sasadangelo/MrSnake)

# Installation & Run

[Download and install Android Studio](http://code4projects.altervista.org/how-to-install-android-studio/). If you already have Android Studio installed, make sure it is at the latest level. Once Android Studio is up and running make sure all projects are closed (if a project is open do File->Close Project), the "Welcome to Android Studio" Panel appears. Select the option "Check out project from version control" and then GitHub. 

Fill the following fields:

    Git Repository URL: https://github.com/sasadangelo/AlienInvaders.git
    Parent Directory: <an empty directory previously created>
    Directory Name: AlienInvaders

The source code will be downloaded and the AlienInvaders project will be created. Now you can run the code doing Run->Run. You can execute the code on Physical or Virtual device. For more details you can read the last section of the following [article](http://code4projects.altervista.org/how-to-create-an-android-application/). 

# Troubleshooting

Sometime could happen that there is incompatibility between the level of gradle declared in the source code with the one installed in the development environment. When this occurs Android Studio will show also a link to fix it. Click the link to solve the issue.

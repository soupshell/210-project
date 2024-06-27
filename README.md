# Rhythm Game Project

## Application Description (current)

This application allows users to create their own rhythm game levels, as well as edit two preset levels.
They are able to create new notes to add to their levels, view the list of notes in their level
as well as further information on the notes, and read instructions on future gameplay and level creation.

CURRENT FEATURES (for users who want...):
- customizable gameplay (making your own levels)
- simple and easy to use mechanics
- adjustable settings (speed) (not implemented yet)
- rhythm game

User Instructions:
- Notes will appear in four columns. In order to click on these notes, press and/or hold keys 1 2 3 4
  (corresponding to keys in the first, second, third, and fourth columns from the left)
- TBA

## User Stories (CONSOLE)
As a user, I want to be able to:
- create songs that can be played (with arbitrarily many notes)
- view the full list of notes in the songs that I have created and those of the two preset songs
- add songs I made to the song selection (that holds an arbitrary number of songs)
- view the full list of songs in the song selection
- view information about a song (length in units of 0.5 seconds, highest score, etc)
- save my songs to file
- load previously made songs from file
- 
## User Stories (GUI)
As a user, I want to be able to:
- create songs
- add songs I made to the song selection (that holds an arbitrary number of songs)
- view the full list of songs in the song selection
- save my songs to file
- load previously made songs from file

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple songs to a repertoire" by clicking start, 
and then select a song. Then, hit any of the X buttons to the right of a button that has a title. This deletes that song
from the repertoire.
- You can generate the second required action related to the user story "adding multiple songs to a repertoire" by clicking start,
and then select a song, and then add. You will then be prompted to use the generated keyboard
to type a string. Click enter after the string is to your liking, and then
you will see your new song. Please note that the two tiny rectangular black buttons
are made to type Y and Z. 
- You can locate my visual component by clicking the frog button on the starter menu.
When you have clicked the frog button, you cannot return to the starter menu.
- You can save the state of my application by clicking the "save" button wherever it shows up.
- You can reload the state of my application by clicking the "load" button on the starter menu.

# Phase 4: Task 3

I would change how data is loaded into the application
and make it so that it would be possible to choose between
save files for both saving and loading.

I would also implement the composite pattern to be able to group
certain songs together in the repertoire, and find a way to clean up
the GUI/GraphicsGame class, which currently has several unused methods
because I wanted to use WindowListener methods, and a lot of other methods
aren't really organized (for example, the ones that do button events and 
turn numbers into corresponding letters of the alphabet, which should be cleaned up)

I could also refactor my GUI to use more built-in methods rather than just
relying on JButton, like making keyboard input available instead of just pressing buttons.

Finally, I would use the iterator pattern. I currently have getter methods in both
Song and Repertoire to get their lists of NoteBlock and Song for use in for loops.
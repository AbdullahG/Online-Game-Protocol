# Online-Game-Protocol
Online Game Protocol, consisting of Server and Client side implementation

###Architecture

   - Multi-Threaded Server - Thread per Connection model<br>
   - The server hosts all players in a single universe<br>
   - Stores usernames and their states in database -named as game in persistence.xml-<br>
   - Uses JPA to make CRUD operations<br>
   - Uses EclipseLink to auto-create schema tables<br>

	
###Basics:

   - Every sent/received messages contain a username.
   - Each player has a (X,Y) location
   - Players has 10 health points when first logged in.
   - Protocol works through a TCP socket connection.
   - All commands end with <CR> (newline), are in uppercase.
   - All responses are single-line, end with <CR>.
   - Connected username is stored in only server-side for security issues

###Commands:
    
   - **HELLO username** - Logins as 'username' - Server responds with OK X,Y,H if username is unique and accepted, disconnects the client otherwise.
   - **LIST** - Lists positions of the users in the universe - Server responds with USER1 X,Y,H - USER2 X,Y,H - USER3 X,Y,H... where H is the health value of listed user.
   - **MOVE X,Y** - Moves to specified X,Y position - Server responds with OK if the move is possible (NO otherwise)
   - **FIRE X,Y** - Fires a bullet towards specified X,Y - Server responds with OK if the fire is possible (NO otherwise)
   - **STATUS** - Checks status - Server responds with current position, health point and a list of hits taken fired from users since the beginning of the game (example: X,Y,H - USER1,1 USER2,2 means current user is hit by 1 bullets from USER1 and 2 bullets from USER2)
   - **QUIT** - Quit game - Server removes the player from universe and disconnects the socket.
	
###Other Matters of the Game:
 
   - Move command moves a user's position 1 unit (on X and/or Y axis) per second.
   - Fired bullet moves 3 units per second.
   - A bullet removes 1 health point from the user hit by the bullet.
   - A user can fire 1 bullet or make 1 movement each second.
   - Users with zero health score "dies", ie, get disconnected.
   - The universe is always a 100x100 square.
   - Users can be hit while they are on the move.

####Server-Client-Test
![alt text](https://github.com/AbdullahG/Online-Game-Protocol/client/src/main/resources/server-client-test.png "Server-Client-Test")

	





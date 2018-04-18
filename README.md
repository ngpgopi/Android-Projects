There are three projects in this repository.

A simple song playlist Application(Adapters)
Chicago guide(Fragments)
A simple no-user Game(Threads)
Project 1:

The main activity in the app displays a list of songs from various artists. When the user clicks on a list item, the main activity is stopped, and a new activity containing a WebView is displayed instead. The web view shows a page with a publicly available video segment, such as a YouTube video, playing the song. By clicking a play button on the view, the device user can watch the video and listen to the song. A user would return to the main activity by pressing the device’s “back” soft key, e.g., when the video clip is complete.

In addition, the main activity displays an options menu. The options menu supports 3 kinds of functionality. First, the options menu will allow a user to add a song to the list displayed in the main activity. When the user selects this option, a dialog window pops up that allows the user to enter (1) a song title, (2) the artist or band singing the song, (3) the URL of the wikipedia page describing the song and (4) the URL of the public video segment playing the song. Second, the options menu will allow a user to remove a song from the list. When this item is selected, a submenu is displayed that shows the current song titles. The user will select one of the current songs and remove it from the list. The activity’s display will be immediately updated. The third item in the options menu allows a user to exit your app.

Finally, each list item supports “long click” functionality. A long click on any list item will bring up a “context menu” showing the following three options for the song under consideration: (1) View the video clip (similar to a simple click); (2) View the song’s wikipedia page in the second activity; and (3) View the artist (or band) wikipedia page in the second activity.

Project 2:

Two new Android apps meant to work together on an Android phone or tablet running version 7.1, Nougat. The first app helps visitors in Chicago decide on points of interest in the city. The second app has specific information about the points of interest.

Application A1 defines a dangerous level permission. In addition, A1 defines an activity containing two read-only text views and two buttons. The buttons, when selected, will first show a short toast message, then broadcast two different intents (e.g., attractions and restaurants) depending on the button pressed. The text views describe the meaning of the buttons to the device user.
Application A2 receives the intents; however, this app will respond to the intents only if the sender owns permission defined in A1. Depending on the kind of intent that was received, A2 will launch one of two activities. The first activity (attractions) displays information about 10 points of interest in the city of Chicago, Illinois (e.g., the Lincoln Park Zoo, Navy Pier, the Museum of Science and Indutry, the Art Institute, the TILT!, etc.) The second activity shows at least 6 restaurants located within Chicago’s city limits. Both activities contain two fragments, whose behavior is described below. In addition, application A2 maintains an options menu and an action bar. The action bar shows the name of the application (your choice) and the overflow area. The options menu allows a device user to switch between attractions and restaurants. This menu should be clearly accessible from the overflow area.
Each of the two activities in A2 contains two fragments. The first fragment displays a list (either the attractions or the restaurants, depending on the activity). The device user may select any item from either list; the currently selected item will stay highlighted until another item is selected. The second fragment shows the official web site of the highlighted item using a web view.

When the device is in portrait mode the two fragments are displayed on different screens. First, the device will show only the first fragment. When the user selects an item, the the first fragment disappears and the second fragment is shown. Pressing the “back” soft button on the device, will return the device to the original configuration (first fragment only), thereby allowing the user to select a different point of interest. When the device is in landscape mode, application A2 initially shows only the first fragment across the entire width of the screen. As soon as a user selects an item, the first fragment is “shrunk” to about 1/3 of the screen’s width. This fragment will appear in the left-hand side of the screen, with the second fragment taking up the remaining 2/3 of the display on the right. Again, pressing the “back” button will return the application to its initial configuration. The action bar should be displayed at all times regardless of whether the device is in portrait or landscape mode.

Finally, the state of application A2 would be retained across device reconfigurations, e.g., when the device is switched from landscape to portrait mode and vice versa. This means that the selected list item (in the first fragment) and the page displayed in the second fragment will be kept during configuration changes.

Project 3:

The layout of Microgolf consists of 50 “holes” arranged in a vertical line. One of the holes is randomly designated by the UI thread as the winning hole. Two worker threads take turns shooting a virtual ball into a hole of their choosing. The first thread to shoot its ball into the winning hole wins the game. Here are additional details on this game. The 50 holes are partitioned into 5 “hole groups”. Each group contains 10 adjacent holes. Whenever one of the two player threads shoots a ball into a hole, the game system provides one of four possible responses:

Jackpot—This happens if the thread shot the ball into the winning hole.
Near miss—This happens if the thread missed the winning hole, but shot into hole in the same group as the winning hole.
Near group—This happens if the thread shot a ball into a hole whose group is adjacent to the group containing the winning hole.
Big miss—This happens if ball misses by more than one group.
Catastrophe—This happens if the ball falls into a hole already currently occupied by the other player’s ball. In this case, the player is immediately disqualified and the other player wins the game.
Threads have can choose one of three possible shots.

Random—This shot will end in any of the fifty possible holes. This is the only option available when a thread makes its first move.
Close group—This shot will end in a random hole either in the same group as the previous shot by the same player, or an adjacent group.
Same group—This shot will end in a random hole in the same group as the previous shot by the same player.
Target hole—This shot will end in a hole specified by a player thread. An additional constraint is that a thread will never shoot in the same hole twice during an entire game, no matter what kind of shot the thread chose
The implementation has two Java worker threads play against each other. The UI thread is responsible for creating and starting the two worker threads, for maintaining and updating the display, and for notifying the worker threads the outcomes of their moves. Each worker thread will take turns with the other thread taking the following actions:

Waiting for a short time (1-2 seconds) in order for a human viewer to take note of the previous move on the display
Figuring out the next shot of this thread.
Communicating this shot to the UI thread.
Waiting for a response from the UI thread.
The UI thread is specifically responsible for the following functionality:

Showing the current hole display. The display should include a vertical array of the holes, highlight the winning hole, as well as the most recent shots by the two player threads. Use, for instance, different colors to distinguish the holes shot by the two players and the winning hole. The displaying of the holes should be scrollable if the screen is too small to display the entire array.
Notifying player threads of the outcome of their shots.
Updating the display after each shot.
Determining whether one player has won the game.
Signaling the two worker threads that the game is over; the two threads should stop their execution as a result of this action.
Displaying the outcome of the game in the UI.

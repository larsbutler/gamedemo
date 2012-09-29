## Building and running

The following steps are required to build and run the jar.
(An Eclipse project has been included for convenience, but the
project config may need to be adjusted to suit your environment.)

1. Build the jar: $ ant
   (ant is of course required)
2. Symlink or copy the appropriate native lib for LWJGL to this dir.
   Example:

    $ ln -s lib/lwjgl/native/macosx/liblwjgl.jnilib
    or
    $ cp lib/lwjgl/native/macosx/liblwjgl.jnilib ./
3. Run the jar:

    $ java -jar GameDemo.jar


PLEASE NOTE:

I hacked this all together really fast and so things may not work
smoothly, depending on your environment and platform. I would love
to have your feedback so I can improve this.

## Game controls

Spacebar: Jump
Left arrow: Move left
Right arrow: Move right
P: Pause/unpause

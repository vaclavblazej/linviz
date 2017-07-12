#Lin vizualizer

Java based visualization tool for basic geometric shapes with standard input.

## How to install and run
To download and build the project:

    git clone git@github.com:vaclavblazej/linviz.git
    cd linviz
    mvn package

To run the program:

    java -jar target/linviz-jar-with-dependencies.jar

## Controls

    mouse wheel (or +/-) - zoom
    left mouse drag (or w/a/s/d) - move
    right and left arrow - move to next/previous frame
    c - reset to the initial zoom
    ~ - run terminal (only beta)
    

## Commandline input
The possible commands are as follows:

    exit - terminate the input
    rt <x> <y> <size> - adds rectangle
    pg <count> [<x1> <y1> ...] - adds polygon
    pgp <count> [<a1> <b1> <c1> ...] - adds polygon in half-plane format
    el <x> <y> <rot> <width> <height> - adds ellipse
    ln <x1> <y1> <x2> <y2> - adds line
    dl <x1> <y1> <x2> <y2> - adds dashed line
    frame - creates new frame
    pause - adds subframe to frame
    background <adding command> - added shape will be put into background (will be shown on all frames)
    zoom <scale> - moves camera to the given distance

## Contribution
Feel free to add issues and issue merge/pull requests.

## Examples
We used this project to show how ellipsoid method functions, therefore the example is a run of this method.

#### Ellipsoid method

    background pgp 3 3 4 12 2 -6 12 -4 -2 -9
    zoom 1024
    el 0 0 0 64 64
    pause
    dl -4 -2 -0
    pause
    el 9.54056 4.77028 0.463648 42.6667 73.9008
    frame
    el 9.54056 4.77028 0.463648 42.6667 73.9008
    pause
    dl 3 4 47.7028
    pause
    el 8.33856 -4.8457 -0.785398 75.2569 32.253
    frame
    el 8.33856 -4.8457 -0.785398 75.2569 32.253
    pause
    dl 2 -6 45.7513
    pause
    el 0.462749 4.62297 -0.502442 53.5416 34.8982
    frame
    el 0.462749 4.62297 -0.502442 53.5416 34.8982
    pause
    dl 3 4 19.8801
    pause
    el -3.93882 0.557057 -0.611525 61.1934 23.5054
    frame
    el -3.93882 0.557057 -0.611525 61.1934 23.5054
    pause
    dl -4 -2 14.6412
    pause
    el 4.1712 -2.35839 0.70917 20.4381 54.1765
    frame
    el 4.1712 -2.35839 0.70917 20.4381 54.1765
    pause
    dl 2 -6 22.4927
    pause
    el -1.24704 4.75177 -0.687736 37.2944 22.8552
    frame
    el -1.24704 4.75177 -0.687736 37.2944 22.8552
    pause
    dl 3 4 15.266
    pause
    el -3.3128 1.53186 -0.654047 43.0106 15.2557
    frame
    el -3.3128 1.53186 -0.654047 43.0106 15.2557
    pause
    dl -4 -2 10.1875
    pause
    el 2.19635 -0.805065 0.692504 13.2392 38.1524
    frame
    el 2.19635 -0.805065 0.692504 13.2392 38.1524
    pause
    dl -4 -2 -7.17527
    pause
    el 5.86911 -2.36301 0.549455 9.81695 39.6083
    frame
    el 5.86911 -2.36301 0.549455 9.81695 39.6083
    pause
    dl 2 -6 25.9163
    pause
    el 2.5074 3.30644 0.583892 11.2931 26.5051
    frame
    el 2.5074 3.30644 0.583892 11.2931 26.5051
    pause
    dl 3 4 20.748
    pause
    el 2.87001 0.142355 -0.773145 26.5909 8.66536
    frame
    el 2.87001 0.142355 -0.773145 26.5909 8.66536

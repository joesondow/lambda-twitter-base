# lambda-twitter-base
Template Java project for building a Twitter bot that can be run by [AWS Lambda](https://aws.amazon.com/lambda/).

Some Twitter bots built from lambda-twitter-base:
- https://github.com/joesondow/fishies which powers [@EmojiAquarium](https://twitter.com/EmojiAquarium)
- https://github.com/joesondow/meadow which powers [@EmojiMeadow](https://twitter.com/EmojiMeadow)
- https://github.com/joesondow/markov which powers [@PicardEbooks](https://twitter.com/PicardEbooks), [@RikerEbooks](https://twitter.com/RikerEbooks), [@JoeEbooks](https://twitter.com/JoeEbooks)


# Build

This project uses [Gradle](https://gradle.org/) for building, in order to make it easier to include support for [Spock](http://spockframework.org/) testing. The AWS Lambda documentation for [Creating a .jar Deployment Package Using Maven and Eclipse IDE (Java)](https://docs.aws.amazon.com/lambda/latest/dg/java-create-jar-pkg-maven-and-eclipse.html) only covers how to use the Shade plugin for Maven to create an executable "fat jar" (containing all dependencies) suitable for use with Lambda. The Gradle equivalent is the Shadow plugin. To build the project:

## on the command line

gradle clean shadowJar

## in Eclipse

Run menu > Run Configurations
Select "Gradle Project"
Click "New launch configuration" button
Click "Workspace" button
Select the project you're building
In the "Gradle Tasks" text area, type "clean shadowJar"
Click Apply
Click Run

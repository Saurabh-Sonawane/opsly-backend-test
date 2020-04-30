## Your mission:

A client needs to know what is happening on the social networks. All of them. Right now.

The three social networks the client is interested in are:

https://takehome.io/twitter

https://takehome.io/facebook

https://takehome.io/instagram

Because these social networks are so webscale, they don't always respond predictably. The delay in their response almost appears like someone waited for a random integer of seconds before responding!

Also, sometimes they will respond with an error. This error will not be valid JSON. Life's hard sometimes.

The client needs to be able to run your thing, then issue the command:

```
curl localhost:3000
```

And get back a JSON response of the output from the three social networks in the format:

```
{ twitter: [tweets], facebook: [statuses], instagram: [photos] }
```

Order isn't important.

This should be a quick little task, but the client is paying us A Billion dollars for it so make sure your implementation is as robust as it is beautiful.

Don't forget to `git push` regularly.

Have fun!



## Pre-requisite for apllication
JDK 8

Gradle 6 or above (If you don't have gradle installed its okay we have gradlew utility for you)


## How to build and deploy application
I have used gradle and added gradlew package in repo, You can use following command to build application

```
./gradlew clean build
```

## How to run application

```
java -jar build/libs/opsly-backend-test-1.0-SNAPSHOT.jar
```

## How to verify 
Just need to run following command

```
curl localhost:3000
```
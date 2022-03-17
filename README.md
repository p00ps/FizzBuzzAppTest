# FizzBuzzAppTest

Hello !

As I wasn’t sure about what you were expecting, I chose to put my fizzbuzz transformation 
into a datasource, because it is the right place for me.

It was complicated to decide if I wanted a very light application 
or something that would fit into a big one.

I think I’ve mix both choice while I was coding, really not the best I guess, but now it is way too late :p

Currently there’s only one first fetch of a thousand results, 
the next pull of result is not implemented.

What would I improve if I had more time (and code during daylight) ?

A talk with the team to be sure about what’s better ? 
No, I think I would do that with Paging 3 and a Flow and build my UI with Compose
Not tested yet but why not

There's also the question about who have to take responsibility 
of the coroutine dispatchers's nature : UseCase of DataSource
I have hesitate a lot, I've put my context into the usecase because a lot of people do that
but I think it's better into the datasource
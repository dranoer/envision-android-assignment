# Interview Test App
<br />

# Architecture
The project is implemented with MVVM and it's written fully with Kotlin.

# Technologies And Decisions
In this section, I'll try to mention some of the important things I used within this project and I'll explain the reason behind some of my decisions.

## DI
I used hilt because of fewer boilerplate codes. Also generally, I think it's easier for integration tests as well.

## Coroutine and Flow
For the threading and observing, I could use technologies like Livedata with Threadpools, Rx, But I used Coroutine and Flow in this project which is my preferred way.
I can explain Why? It's hard to handle things like backpressure or debounce with Livedatas and ThreadPools.
On the other hand, Coroutine is lighter than Rx. It's native and somehow it's easier to test because Google has created some libraries for that.
*PS: I also used some codes from Google samples.*

## Data Persistence
I made a local database with the help of the Room library for storing captured photos' data in a table. I haven't faced any special issue with this object.

## Navigation and UI
I do like the Navigation component but as I searched around there's an open issue for this component when it gets used by viewpager. They are not compatible together yet and you can not just simply use a Navigation Controller to be able to navigate between screens :(
So I came up with a messy solution by using interfaces so on. This was my biggest challenge in this project. I would like to improve this part in the feature.

## UX
I kept UI minimal and tried to just show I know how to use things like constraintlayout, etc, but I guess from the user side it's not good at all :)

## Test
Today is the deadline of this project and due to the pressure of my interviews I hadn't enough time to write tests for this project. Writing tests for this task was optional, but I have this missing part in my mind and I will write some tests for the people who may like to use my codes.

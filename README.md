# railroad-kitomba

This project is a technical test for Kitomba. It answers to the "Train" problem.

It has been developed in Java 8.

To launch the project
 -

- import the project as a maven project
- launch the main class: RailroadMain.java
- you can also launch the Junit test: TripServiceTest.java


Architecture of the application
 -

All the trip services and algorithms are in TripService. The towns are reprensented by the Node object.
Each node contains its children. A child is a node and it is registered in a Map<Node, Integer> where the Integer is weight.

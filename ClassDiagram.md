```plantuml
@startuml
!pragma layout smetana

class Node {
    -Node parent
    -Node[] children
    -int visits
    -double player1Wins
    -final Board board
    
    +Node(Node parent, Board board)
    +int incrementVisits()
    +double incrementPlayer1Wins(double result)

}

class ConnectBruh {
    -Node root
    -final int width
    -{static} final double EXPLORATION = Math.sqrt(2)
    -long givenTime
    
    +ConnectBruh(Board board, long givenTime)
    
    +void update(int move)
    +int getOptimalMove()
    -Node select()
    -Node select(Node parent)
    -Node expand(Node selectedNode)
    -double simulate(Node expandedNode)
    -void backpropagate(Node expandedNode, double SimulationResult)
}

@enduml
```
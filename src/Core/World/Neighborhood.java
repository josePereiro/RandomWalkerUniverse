package Core.World;

public class Neighborhood extends Rectangle {

    WalkersBuffer neighboors;

    Neighborhood(SpacePoint origin, int width, int height, int bufferCapacity) {
        super(origin, width, height);
        neighboors = new WalkersBuffer(bufferCapacity);
    }

    public WalkersBuffer getNeighboors() {
        return neighboors;
    }
}

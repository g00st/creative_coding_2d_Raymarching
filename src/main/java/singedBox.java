import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class singedBox implements IsingedDf{
    private PVector position;
    private float width;
    private float height;
    @Override
    public PVector Position() {
        return position;
    }

    singedBox(PVector position, float width, float height){
        this.position = position;
        this.width = width;
        this.height = height;
    }

    @Override
    public void Draw(PGraphics g) {
        g.rect(position.x - width / 2, position.y - height / 2, width, height);
    }


    public float getDistance2(PVector p) {
        float dx = PApplet.abs(p.x - position.x) - width / 2;
        float dy = PApplet.abs(p.y - position.y) - height / 2;

        // Calculate the distance to the nearest edge
        float distanceX = PApplet.max(dx, 0);
        float distanceY = PApplet.max(dy, 0);

        // If point is inside the rectangle, return negative signed distance
        if (dx <= 0 && dy <= 0) {
            return -PApplet.min(PApplet.max(dx, dy), 0);
        }

        // Otherwise, return positive signed distance
        return PApplet.sqrt(PApplet.sq(distanceX) + PApplet.sq(distanceY));
    }

    public float getDistance(PVector p) {
        float dx = PApplet.abs(p.x - position.x )-width/2;
        float dy = PApplet.abs(p.y - position.y )-height/2;

        // Calculate the distance to the nearest edge
        float distanceX = PApplet.max(dx, 0);
        float distanceY = PApplet.max(dy, 0);

        // If point is inside the rectangle, return negative signed distance

            return (new PVector(distanceX,distanceY).mag())  + PApplet.min(PApplet.max(dx, dy), 0);




    }


    @Override
    public PVector getnormal(PVector p) {
        var pp = p.copy().sub(position);
        float dx = PApplet.abs(pp.x);
        float dy = PApplet.abs(pp.y);

        // Calculate half-width and half-height
        float halfWidth = width / 2;
        float halfHeight = height / 2;

        // Calculate the distance to each face
        float distanceToLeft = dx + halfWidth;
        float distanceToRight = dx - halfWidth;
        float distanceToTop = dy + halfHeight;
        float distanceToBottom = dy - halfHeight;

        // Determine the smallest distance
        float minDistance = PApplet.min(PApplet.min(distanceToLeft, distanceToRight), PApplet.min(distanceToTop, distanceToBottom));

        // Return the normal of the face with the smallest distance
        if (minDistance == distanceToLeft) {
            return new PVector(-1, 0);
        } else if (minDistance == distanceToRight) {
            return new PVector(1, 0);
        } else if (minDistance == distanceToTop) {
            return new PVector(0, -1);
        } else {
            return new PVector(0, 1);
        }
    }



}


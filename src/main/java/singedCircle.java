import processing.core.PGraphics;
import processing.core.PVector;

public class singedCircle implements IsingedDf{
    private PVector position;
    private float radius;
    singedCircle(PVector position,float  radius){
        this.position = position;
        this.radius = radius;
    }
    @Override
    public PVector Position() {
        return position;
    }

    @Override
    public void Draw(PGraphics g) {
        g.ellipse(position.x, position.y, radius*2, radius*2);
    }

    @Override
    public float getDistance(PVector p) {
        return  (p.dist(position) - radius);
    }

    @Override
    public PVector getnormal(PVector p) {
        return p.copy().sub(position).normalize();
    }
}

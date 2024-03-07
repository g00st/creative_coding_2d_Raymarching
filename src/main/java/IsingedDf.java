import processing.core.PGraphics;
import processing.core.PVector;

public interface IsingedDf {
    public PVector Position();
    public  void Draw (PGraphics g);
    public float getDistance(PVector p);

    public PVector getnormal(PVector p);
}

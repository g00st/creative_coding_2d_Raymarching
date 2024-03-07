import com.sun.jna.platform.win32.WinBase;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

import java.util.ArrayList;

public class Camera {
    public PVector position;
    private float angle;
    private int renderResolution;
    PGraphics render;
    PGraphics debugView;
    private int surfacewidth;
    private int surfaceheight;
    PApplet applet;
    private float verticalfov;
    PVector light =  PVector.fromAngle(applet.PI);

    private ArrayList<IsingedDf> scene;


    PVector currentnormal;
   private  float fov; // field of view in radians

   Camera(PVector position, float angle, int resolution, float focallength, PApplet applet){
       this.position = position;
       this.angle = angle;
       this.renderResolution = resolution;
       debugView = applet.createGraphics(applet.width/2, applet.height);
         render = applet.createGraphics(applet.width/2, applet.height);
            this.applet = applet;
            verticalfov = applet.PI;
            scene = new ArrayList<>();
   }


    public void setRenderResolution(int resolution){
         this.renderResolution = resolution;
    }

    public void setFov(float fov){
        this.fov = fov;
    }

    public void setVertFov(float fov){
        this.verticalfov = fov;
    }

    public void setLight(PVector light){
        this.light = light;
    }

    public void move (PVector direction){
       var pos = position.copy();
       pos.add(direction.rotate(angle));
       var d = getMindistance(pos, scene);
       if (d> 1){
           System.out.println(position);
           position = pos;
           System.out.println(pos);

       }
       System.out.println(d);

    }

    public void turn(float angle){
        this.angle += angle;
        System.out.println(this.angle);
    }


    public void Render(ArrayList<IsingedDf> scene){
       this.scene = scene;
       debugView.beginDraw();
        debugView.pushMatrix();
        debugView.translate(-position.x+ debugView.width/2, -position.y+debugView.height/2);
        debugView.background(0);
        debugView.stroke(255);
        debugView.fill(255);
        debugView.stroke(255, 0, 0);
        debugView.strokeWeight(3);
        var p = PVector.fromAngle(angle).mult(100);
        debugView.strokeWeight(1);
        debugView.line(position.x, position.y, position.x + p.x, position.y + p.y);
        debugView.stroke(255);
        for(var obj : scene) {
            obj.Draw(debugView);
        }


        render.beginDraw();
        render.background(30,0, 0);
        render.fill(30);
        render.rect(0, render.height/2, render.width, render.height/2);
        render.fill(200);


        for (int x = -renderResolution/2 ; x <= renderResolution/2; x++){

            float distance = 0;



            PVector ray =  PVector.fromAngle(angle + x* fov/renderResolution);
            distance = marchRay(ray, scene);
            //fix fishey effect
            //distance *= PApplet.cos( (x*fov/renderResolution) );
            debugView.fill(0,255,0);
            debugView.ellipse(position.x, position.y, 10, 10);
            debugView.line(position.x, position.y, position.x + ray.x*distance, position.y + ray.y*distance);




            render.beginDraw();
            render.translate(render.width/2.0f,0);
            render.stroke(255);



            int rectwidth = render.width/renderResolution;
            float rectheight =  PApplet.max( render.height*2/  (distance*0.2f) , 0);
            render.fill(0);
            if (currentnormal != null){
                var normnorm = PVector.angleBetween(light, currentnormal);
                var normnorm2 = PVector.angleBetween(ray, currentnormal);
                normnorm = applet.map(normnorm, 0, applet.TWO_PI, 10, 255);
                normnorm2 = applet.sin( applet.map(normnorm2, 0, applet.TWO_PI, 0.2f, PApplet.PI));
                render.fill(normnorm*normnorm2- distance*0.01f );
            }
            render.noStroke();
            render.rect(x*rectwidth, render.height/2 - rectheight/2, rectwidth , rectheight );

        }

        debugView.popMatrix();
        debugView.endDraw();
    }

    private float marchRay(PVector ray, ArrayList<IsingedDf> scene){
        float distance = 0;
        float mindistance = getMindistance(position, scene);
        float itterations = 0;

        debugView.noFill();
        debugView.stroke(155, 0, 0);
        debugView.ellipse(position.x, position.y, mindistance*2,mindistance*2 );


        while (distance < 1000 && itterations < 100){
            itterations++;
            distance += mindistance;
            PVector currentPoint = PVector.mult(ray, distance).add(position);
            mindistance = getMindistance(currentPoint, scene);
            debugView.ellipse(currentPoint.x, currentPoint.y, mindistance*2,mindistance*2 );
            if (mindistance < 0.01){

                return distance;
            }
        }
        currentnormal = null;
        return 1000;
    }

    private float getMindistance(PVector point, ArrayList<IsingedDf> scene){
        float minDistance = 10000;
        currentnormal = null;
        for (IsingedDf object : scene){
            float distance = object.getDistance(point);
            if (distance < minDistance){
                minDistance = distance;
                currentnormal = object.getnormal(point);
            }
        }
        return minDistance;
    }






}

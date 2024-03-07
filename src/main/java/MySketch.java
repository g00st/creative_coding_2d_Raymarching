import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PShader;
import controlP5.*;

import java.util.ArrayList;


public class MySketch extends PApplet {
	private int rresolution = 5;
	private float fov = PI;
	private float verticalfov = PI;
	private float size = 20;
	private Camera cam;
	private ArrayList<IsingedDf> scene;

	private boolean type = false;

	ControlP5 cp5;
	public void setup() {
		cam = new Camera( new PVector(100,100,0), 0, rresolution, 1, this);
		this.getSurface().setResizable(false);
		cp5 = new ControlP5(this);
		cp5.addSlider("rresolution")
				.setPosition(10, 10)
				.setRange(1, 200)
				.setValue(5)
				.setSize(100, 20).addListener(theEvent -> cam.setRenderResolution((int) theEvent.getController().getValue()));
		cp5.addSlider("fov")
				.setPosition(10, 40)
				.setRange(0.0f, 2*PI)
				.setValue(5)
				.setSize(100, 20).addListener(theEvent -> cam.setFov(theEvent.getController().getValue()));
		cp5.addSlider("vertfov")
				.setPosition(10, 80)
				.setRange(0.0f, 2*PI)
				.setValue(5)
				.setSize(100, 20).addListener(theEvent -> cam.setVertFov(theEvent.getController().getValue()));
		cp5.addToggle("type")
				.setPosition(10, 130)
				.setSize(100, 20);
		cp5. addSlider("light")
				.setPosition(10, 160)
				.setRange(0, 2*PI)
				.setValue(PI)
				.setSize(100, 20).addListener(theEvent -> cam.setLight(PVector.fromAngle(theEvent.getController().getValue())));
		cp5.addSlider("size")
				.setPosition(10, 190)
				.setRange(1, 100)
				.setValue(20)
				.setSize(100, 20);;


		scene = new ArrayList<>();
	}

	public void settings() {
		size(1000, 500);
	}



	public void frameResized(int w, int h) {

	}

	public void onKeyEvent(){
		if (keyPressed) {
		if (key == 'w'){
			cam.move( new PVector(+1, 0, 0));
		}
		if (key == 's'){
			cam.move( new PVector(-1, 0, 0));
		}
		if (key == 'a'){
			cam.move( new PVector(0, -1, 0));
		}
		if (key == 'd'){
			cam.move( new PVector(0, +1, 0));
		}


		if ( keyCode == LEFT){
			cam.turn(-0.02f);
		}
		if ( keyCode == RIGHT){
			cam.turn(0.02f);
		}}
	}

	public void mouseReleased (){

		if (mouseButton == RIGHT ){

			if (!type) {
				scene.add(new singedCircle(new PVector((float) mouseX + cam.position.x - cam.debugView.height / 2, (float) mouseY + cam.position.y - cam.debugView.height / 2, 0), size / 2));
			}else{
				scene.add(new singedBox(new PVector((float) mouseX + cam.position.x - cam.debugView.height / 2, (float) mouseY + cam.position.y - cam.debugView.height / 2, 0), size, size));
			}
		}
	}

	public void draw(){
		onKeyEvent();
		cam.Render(scene);
		background(20);
		image(cam.debugView.get(), 0, 0);
		image(cam.render.get(), width/2.0f, 0);
		stroke(255);
		noFill();
		ellipse(mouseX, mouseY, size, size);
	}
}

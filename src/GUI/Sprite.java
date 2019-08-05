package GUI;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

public class Sprite {

	private ArrayList<Image> spriteSet;
	private HashMap<String, Integer[]> landmarks;
	private String currentLandmark;
	private int positionOnStop = 0;
	private int landMarksEnd = 0;
	private int currentSprite = 0;
	private Integer[] animationRange;
	private long currentTimeStep = 0;
	private long lastTime = 0;
	private boolean animating = false;
	private boolean resetOnStop = false;
	
	public Sprite(String packageName, String...imageNames) {
		this.spriteSet = new ArrayList<Image>();
		for(String imageName: imageNames) {
			String fullName = "/" + packageName + "/" + imageName;
			this.spriteSet.add(new ImageIcon(getClass().getResource(fullName)).getImage());
		}
		
		this.landmarks = new HashMap<String, Integer[]>();
	}
	
	//IMPORTANT: PLEASE ADD LANDMARKS IN ORDER FROM LEAST TO GREATEST
	public void addLandmark(String name, int length) {
		Integer[] range = {landMarksEnd, landMarksEnd+length};
		this.landmarks.put(name, range);
	}
	
	public List<Image> getLandmarkImages(String name) {
		Integer[] range = this.landmarks.get(name);
		return this.spriteSet.subList(range[0], range[1]);
	}

	public void stop() {
		this.animating = false;
		if(this.resetOnStop) {
			this.jumpToLandmark(this.currentLandmark);
			this.currentSprite += this.positionOnStop;
		}
	}
	
	public Image updateSprite() {
		if(this.animating) {
			if(System.currentTimeMillis() - lastTime >= this.currentTimeStep) {
				this.nextSprite();
				lastTime = System.currentTimeMillis();
			}
		}
		return this.getImage();
	}
	
	public Image getImage() {
		return this.spriteSet.get(this.currentSprite);
	}
	
	public void nextSprite() {
		this.currentSprite ++;
		if(this.currentSprite == this.animationRange[1]) {
			this.currentSprite = this.animationRange[0];
		}
	}
	
	public void animate(String name, long timeStepMillis, int positionOnStop) { 
		if(!animating) {
			this.currentTimeStep = timeStepMillis;
			this.lastTime = System.currentTimeMillis();
			this.animating = true;
			this.animationRange = this.landmarks.get(name);
			this.currentSprite = animationRange[0];
			this.resetOnStop = positionOnStop != -1;
			this.currentLandmark = name;
			this.positionOnStop = positionOnStop;
		}
	}
	
	public void jumpToLandmark(String name) {
		this.currentSprite = this.landmarks.get(name)[0];
	}
}

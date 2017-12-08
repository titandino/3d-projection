package com.proj;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.*;
import javax.swing.*;

import com.proj.game.Camera;
import com.proj.game.Cube;
import com.proj.math.Matrix3D;
import com.proj.math.Vector3D;

/**
 * The main class containing the game window and game logic.
 * 
 * @author Trenton
 */
public class Game extends Canvas {

	private static final long serialVersionUID = -1550772778564315222L;

	public static BufferStrategy strategy;

	public static int FPS = 80;

	public static int dimensionX = 800;
	public static int dimensionY = 600;

	public static boolean gameRunning = true;

	public static boolean debug = true;

	public static Graphics2D graphics;

	public static void main(String args[]) {
		new Game();
		gameLoop();
	}

	/**
	 * Game constructor
	 * 
	 * Sets up the window and sets the resolution to 800x600
	 */
	public Game() {
		// Set the level to the chosen starting level.

		// Create the JFrame to contain the game.
		JFrame container = new JFrame("Projection");

		// Facepalm there.. Wasn't ending the process on closing..
		container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Get the content of the frame and set up the resolution.
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(dimensionX - 10, dimensionY - 10));
		panel.setLayout(null);

		// Set up the canvas size and put it into the container's contentPane.
		setBounds(0, 0, dimensionX, dimensionY);
		panel.add(this);

		// Disable repaint to enable the game to do it itself because
		// we are using accelerated graphics.
		setIgnoreRepaint(true);

		// Make the window visible
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		this.setFocusable(true);

		// Create the buffering strategy which will allow AWT to manage
		// the game's accelerated graphics. Buffers used to manage the screen:
		// 2.
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		graphics = (Graphics2D) strategy.getDrawGraphics();

		// Add key listener
		addKeyListener(new Keyboard());
		// Add mouse listener
		addMouseListener(new Mouse());

	}
	
	private static Vector3D position = new Vector3D(50.0f, 50.0f, 50.0f, 0.0f);
	private static Vector3D camPosition = new Vector3D(-0.5f, -0.5f, -0.5f, 0.0f);
	
	private static Camera camera = new Camera(camPosition, camPosition);
	
	private static Cube cube = createCube(position, 50.0f);
	
	private static Cube createCube(Vector3D pos, float scale) {
		Cube cube = new Cube(pos, new Vector3D(0.0f, 0.0f, 0.0f, 0.0f), scale);
		
		Vector3D[] vertices = {
			new Vector3D(-0.5f,  0.5f,  -0.5f, 2.0f),
			new Vector3D(-0.5f, -0.5f,  -0.5f, 2.0f),
			new Vector3D(0.5f, -0.5f,  -0.5f, 2.0f),
			new Vector3D(0.5f,  0.5f,  -0.5f, 2.0f),
			new Vector3D(-0.5f,  0.5f,   0.5f, 2.0f),
			new Vector3D(-0.5f, -0.5f,   0.5f, 2.0f),
			new Vector3D(0.5f, -0.5f,   0.5f, 2.0f),
			new Vector3D(0.5f,  0.5f,   0.5f, 2.0f),
		};

		cube.pos = pos;
		cube.scale = scale;
		for(int i = 0;i < 8;i++) {
			cube.vtxs[i] = vertices[i];
		}
		cube.rotation = new Vector3D(20.0f, 0.0f, 0.0f, 0.0f);
		return cube;
	}
	
	private static void drawCube(Cube cube, Color color) {
		Matrix3D trans = new Matrix3D();
		Matrix3D scale = new Matrix3D();
		Matrix3D rotX = new Matrix3D();
		Matrix3D rotY = new Matrix3D();
		Matrix3D rotZ = new Matrix3D();
		Matrix3D view = new Matrix3D();
		Matrix3D camTrans = new Matrix3D();
		Matrix3D camRot = new Matrix3D();
		Matrix3D last = new Matrix3D();

		Vector3D[] newVtx = new Vector3D[8];

		for(int i = 0;i < 8;++i) {
			last.buildIdentityMtx();

			camTrans.buildTranslationMtx(-camera.position.x, -camera.position.y, -camera.position.z);
			camRot.buildRotationXMtx(camera.rotation.x);
			camRot.concat(new Matrix3D().buildRotationYMtx(camera.rotation.y));
			camRot.concat(new Matrix3D().buildRotationZMtx(camera.rotation.z));


			trans.buildTranslationMtx(cube.pos.x, cube.pos.y, cube.pos.z);
			scale.buildScaleMtx(cube.scale, cube.scale, cube.scale);
			rotX.buildRotationXMtx(cube.rotation.x);
			rotY.buildRotationYMtx(cube.rotation.y);
			rotZ.buildRotationZMtx(cube.rotation.z);
			view.buildProjectionMtx(camera.fieldOfView, camera.aspect, camera.near, camera.far);

			last.concat(view).concat(camTrans).concat(camRot).concat(trans).concat(scale).concat(rotX).concat(rotY).concat(rotZ);
			
			newVtx[i] = Matrix3D.concatMtxWithVec(last, cube.vtxs[i]);
		}

		
		drawLineVtx(newVtx[0], newVtx[1], color);
		drawLineVtx(newVtx[1], newVtx[2], color);
		drawLineVtx(newVtx[2], newVtx[3], color);
		drawLineVtx(newVtx[3], newVtx[0], color);
		drawLineVtx(newVtx[4], newVtx[5], color);
		drawLineVtx(newVtx[5], newVtx[6], color);
		drawLineVtx(newVtx[6], newVtx[7], color);
		drawLineVtx(newVtx[7], newVtx[4], color);
		drawLineVtx(newVtx[4], newVtx[0], color);
		drawLineVtx(newVtx[5], newVtx[1], color);
		drawLineVtx(newVtx[6], newVtx[2], color);
		drawLineVtx(newVtx[7], newVtx[3], color);
	}
		
	private static void drawLineVtx(Vector3D vtx1, Vector3D vtx2, Color color) {
		graphics.setColor(color);
		graphics.draw(new Line2D.Float(vtx1.x, vtx1.y, vtx2.x, vtx2.y));
	}

	/**
	 * Perform's the game's current level's logic
	 */
	public static void gameLoop() {

		long lastLoopTime = System.currentTimeMillis();

		// Frames per second variables
		long nextSecond = System.currentTimeMillis() + 1000;
		int framesInLastSecond = 0;
		int framesInCurrentSecond = 0;

		while (gameRunning) {

			graphics = (Graphics2D) strategy.getDrawGraphics();

			// Get how long it has been since the last update.
			long deltaLong = System.currentTimeMillis() - lastLoopTime;

			lastLoopTime = System.currentTimeMillis();

			graphics.setColor(Color.black);
			graphics.fillRect(0, 0, dimensionX, dimensionY);

			// Frames per second calculation
			long currentTime = System.currentTimeMillis();
			if (currentTime > nextSecond) {
				nextSecond += 1000;
				framesInLastSecond = framesInCurrentSecond;
				framesInCurrentSecond = 0;
			}
			framesInCurrentSecond++;
			if (debug) {
				graphics.setColor(Color.cyan);
				graphics.drawString("FPS: " + framesInLastSecond, 720, 20);
				graphics.drawString("Angular Velocity: " + cube.angularVelocity.toString(), 10, 20);
			}
			
			/*
			 * Update and draw
			 */
			cube.update(deltaLong);
			drawCube(cube, Color.GREEN);
			
			/*
			 * End update and draw
			 */

			// Clear up the graphics and flip the buffer.
			graphics.dispose();
			strategy.show();

			// Pause for a bit (FrameRateController)
//			while ((System.currentTimeMillis() - lastLoopTime) < 1000 / FPS) {
//
//			}
		}
	}
	
	class Keyboard extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_Q) {
				cube.angularVelocity.x += 0.1f;
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				cube.angularVelocity.x -= 0.1f;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_A) {
				cube.angularVelocity.y += 0.1f;
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				cube.angularVelocity.y -= 0.1f;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_Z) {
				cube.angularVelocity.z += 0.1f;
			}
			if (e.getKeyCode() == KeyEvent.VK_X) {
				cube.angularVelocity.z -= 0.1f;
			}
		}

		public void keyReleased(KeyEvent e) {
			
		}

		public void keyTyped(KeyEvent e) {

		}
	}

	class Mouse extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}
	}
}

package nl.sogyo.robots;

import java.util.*;

public class Robot {
	private int direction; // 0 = north, 1 = east, 2 = south, 3 = west
	private int x;
	private int y;
	private List<QueuedMethod> queuedMethods = new ArrayList<>();
	
	// interface to store methods to be executed as lambdas
	private interface QueuedMethod {
		public void execute();
	}
	
	/////////////////
	// Constructors /
	/////////////////
	public Robot() {
		this(0, 0, 0);
	}
	
	public Robot(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public Robot(int x, int y, String direction) {
		this(0, 0, Arrays.asList("north", "east", "south", "west").indexOf(direction.toLowerCase()));
	}

	////////////////////
	// 'Regular' moves /
	////////////////////
	public void turnLeft() {
		turn(3);
	}
	
	public void turnRight() {
		turn(1);
	}
	
	private void turn(int i) {
		// input of 1 results in a turn to the right
		// input of 3 results in a turn to the left
		direction = (direction + i) % 4;
	}
	
	public void forward() {
		forward(1);
	}
	
	public void forward(int amount) {
		// limit the move to be between 1 and 3 (inclusive)
		amount = Math.min(Math.max(1, amount), 3);
		move(-1, amount);
	}
	
	public void backward() {
		move(1, 1);
	}
	
	private void move(int i, int amount) {
		// i of -1 results in a move forwards
		// i of 1 results in a move backwards
		if (direction % 2 == 0)
			x += i * amount * (direction - 1);
		else
			y += i * amount * (direction - 2);
	}

	//////////////////
	// Queue methods /
	//////////////////
	public void addLeft() {
		queuedMethods.add(() -> turnLeft());
	}
	
	public void addRight() {
		queuedMethods.add(() -> turnRight());
	}
	
	public void addForward() {
		addForward(1);
	}
	
	public void addForward(int amount) {
		queuedMethods.add(() -> forward(amount));
	}
	
	public void addBackward() {
		queuedMethods.add(() -> backward());
	}
	
	// Execute the queue and empty the queue
	public void execute() {
		for (QueuedMethod m : queuedMethods)
			m.execute();
		queuedMethods.clear();
	}
	
	/////////////////////////////
	// Represent me as a string /
	/////////////////////////////
	public String toString() {
		return "( " + x + ", " + y + "): " + direction;
	}
}

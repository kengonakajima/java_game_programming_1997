public class player {
	public final int TOSOKU = 0, KANSEI = 1, SHUTAN = 2, ZENKIN = 3;
	final double HR2 = Math.sqrt(2) / 2;
	final double dir2X[] = {0, HR2, 1, HR2, 0, -HR2, -1, -HR2, 0};
	final double dir2Y[] = {-1, -HR2, 0, HR2, 1, HR2, 0, -HR2, 0};

	double  x, y, dx, dy;
	int     width, height, size;
	double  speed, accel, resist, rate;

	player(int width, int height, int size, 
		double speed, double accel, double rate) {
		this.width = width;
		this.height = height;
		this.size = size;
		this.speed = speed;
		this.accel = accel;
		this.rate = rate;

		x = this.width / 2;
		y = this.height / 2;
		resist = this.accel / this.speed;
		dx = dy = 0.0F;
	}

	public double getX() {return x;}
	public double getY() {return y;}

	public void move(boolean cursor[], int mode) {
		int direction = calcDirection(cursor);

		switch (mode) {
			case TOSOKU:
			calcTosoku(direction);
			break;
			case KANSEI:
			calcKansei(direction);
			break;
			case SHUTAN:
			calcShutan(direction);
			break;
			case ZENKIN:
			calcZenkin(direction);
			break;
		}

		x += dx;
		y += dy;

		if (x < 0) {
			x = 0;
		} else if (x > width - size) {
			x = width - size;
		}
		if (y < 0) {
			y = 0;
		} else if (y > height - size) {
			y = height - size;
		}
	}

	int calcDirection(boolean cursor[]) {
		int input_x = 0, input_y = 0;

		if (cursor[3]) {
			if (!cursor[1]) {
				input_x = -1;
			}
		} else {
			if (cursor[1]) {
				input_x = 1;
			}
		}

		if (cursor[0]) {
			if (!cursor[2]) {
				input_y = -1;
			}
		} else {
			if (cursor[2]) {
				input_y = 1;
			}
		}

		// 真上が0で右回りに7まで。入力無しは8
		if (input_x > 0) {
			if (input_y > 0) {
				return 3;
			} else if (input_y < 0) {
				return 1;
			} else {
				return 2;
			}
		} else if (input_x < 0) {
			if (input_y > 0) {
				return 5;
			} else if (input_y < 0) {
				return 7;
			} else {
				return 6;
			}
		} else {
			if (input_y > 0) {
				return 4;
			} else if (input_y < 0) {
				return 0;
			} else {
				return 8;
			}
		}
	}

	void calcTosoku(int direction) {
		dx = dir2X[direction] * speed;
		dy = dir2Y[direction] * speed;
	}

	void calcKansei(int direction) {
		double sp;

		dx += dir2X[direction] * accel;
		dy += dir2Y[direction] * accel;
		sp = Math.sqrt(dx * dx + dy * dy);
		if (sp > speed) {
			dx = dx * speed / sp;
			dy = dy * speed / sp;
		}
	}

	void calcShutan(int direction) {
		dx += dir2X[direction] * accel - dx * resist;
		dy += dir2Y[direction] * accel - dy * resist;
	}

	void calcZenkin(int direction) {
		dx += (dir2X[direction] * speed - dx) * rate;
		dy += (dir2Y[direction] * speed - dy) * rate;
	}
}

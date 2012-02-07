class moshin {
    final double MAXSPEED = 12.0, ACCEL = 1.0, MUKU = Math.PI / 15.0;
    final int WAIT = 15;
    final int SEARCH = 0, ATTACK = 1;
    
    double x, y, r, theta;
    int width, height, size, phase, count;

    moshin(int width, int height, int size) {
		this.width = width;
		this.height = height;
		this.size = size;
		x = this.width / 2.0;
		y = this.width / 2.0;
		theta = r = 0.0;
		count = phase = 0;
    }

    public double getX() {return x;}
    public double getY() {return y;}
    public double getTheta() {return theta;}

    void LimitTheta() { /* thetaの値を0から2πの間に保つ */
		if (theta < 0.0) {
			theta += 2 * Math.PI;
		} else if (theta >= 2 * Math.PI) {
			theta -= 2 * Math.PI;
		}
    }

    double   Delta2Theta(double dx, double dy)
    {
        if (dx > 0) {
            if (dy >= 0) {
                return Math.atan(dy / dx);
            } else {
                return Math.atan(dy / dx) + 2 * Math.PI;
            }
        } else if (dx < 0) {
            return -Math.atan(dy / -dx) + Math.PI; 
        } else {
			return 0;
		}
    }

    int NextStep() {
		x += r * Math.cos(theta);
		y += r * Math.sin(theta);

		if (x < 0) {
			x = 0;
			return 1;
		} else if (x > width - size) {
			x = width - size;
			return 1;
		}
		if (y < 0) {
			y = 0;
			return 1;
		} else if (y > height - size) {
			y = height - size;
			return 1;
		}
		return 0;
    }

    int InRange(double me, double you, double range)
    {
		double   dtheta = Math.abs(me - you);

		if (dtheta <= range) return 1;
		else return 0;
    }

    int RightLeft(double now, double target)
    {
		if (now < Math.PI) {
			if (now < target && target <= now + Math.PI) return 1;
			else return -1;
		} else {
			if (now - Math.PI < target && target <= now) return -1;
			else return 1;
		}
    }

    int ForBack(double me, double you)
    {
		me -= Math.PI / 2.0;
		if (me < 0.0) me += 2.0 * Math.PI;
		return RightLeft(me, you);
    }

    public void Move(double cx, double cy) {
		double	target = Delta2Theta(cx - x, cy - y);

		switch (phase) {
			case	SEARCH: /* 回転してマウスカーソルの方を向く */
			++count;
			if (0 != InRange(theta, target, MUKU)) {
				if (WAIT < count) {
					phase = ATTACK;
					count = 0;
				}
			} else {
				theta += MUKU * RightLeft(theta, target);
				LimitTheta();
			}
			break;
			case	ATTACK: /* 真っ直に猛進していく */
			if (0 < ForBack(theta, target)) { /* 前方にいれば加速 */
				r += ACCEL;
				if (r > MAXSPEED) r = MAXSPEED;
			} else { /* 後方にいれば減速停止 */
				if (r > ACCEL) {
					r -= ACCEL;
				} else {
					r = 0;
					phase = SEARCH;
				}
			}
			if (0 != NextStep()) { /* 外枠に衝突したときは停止 */
				r = 0;
				phase = SEARCH;
			}
			break;
		}
    }
}


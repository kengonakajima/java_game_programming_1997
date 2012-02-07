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

    void LimitTheta() { /* theta$B$NCM$r(B0$B$+$i(B2$B&P$N4V$KJ]$D(B */
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
			case	SEARCH: /* $B2sE>$7$F%^%&%9%+!<%=%k$NJ}$r8~$/(B */
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
			case	ATTACK: /* $B??$CD>$KLT?J$7$F$$$/(B */
			if (0 < ForBack(theta, target)) { /* $BA0J}$K$$$l$P2CB.(B */
				r += ACCEL;
				if (r > MAXSPEED) r = MAXSPEED;
			} else { /* $B8eJ}$K$$$l$P8:B.Dd;_(B */
				if (r > ACCEL) {
					r -= ACCEL;
				} else {
					r = 0;
					phase = SEARCH;
				}
			}
			if (0 != NextStep()) { /* $B30OH$K>WFM$7$?$H$-$ODd;_(B */
				r = 0;
				phase = SEARCH;
			}
			break;
		}
    }
}


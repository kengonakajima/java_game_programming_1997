/*
   $B"#6K:BI87O$OJ*BN$N0\F07W;;$KJXMx!*(B


   $B%2!<%`Cf$G%-%c%i%/%?$dCO7A$J$I$NJ*BN$N0\F0$rI=8=$9$k$H$-$O!"D>8r$9$k:B(B
   $BI8<4$NCM(B(dx, dy)$B$GB.EY$rI=$9J}K!(B($BD>8r:BI87O(B)$B$h$j!"5wN%$H3QEY(B(r, theta)
   $B$GB.EY$rI=$9J}K!(B($B6K:BI87O(B)$B$rMQ$$$?J}$,JXMx$G$9!#$I$&$7$F$+$H$$$&$H%2!<(B
   $B%`Cf$NJ*BN$N0\F0$O!"$?$$$F$$<!$NFs$D$NMWAG$+$i@.$jN)$C$F$$$k$+$i$G$9!#(B

   $B!&B.$5$rJQ$($k(B $B!]!d(B $B5wN%(B(r)$B$NJQ2=(B
   $B!&8~$-$rJQ$($k(B $B!]!d(B $B3QEY(B(theta)$B$NJQ2=(B

   $B$3$N$h$&$JM}M3$+$i!"%2!<%`%W%m%0%i%_%s%0$G$OJ*BN$NB.EY$O6K:BI87O$GI=8=(B
   $B$7!"0LCV$N7W;;$O$3$l$rD>8r:BI87O$KJQ49$7$F$+$i9T$&$3$H$,$h$/$"$j$^$9!#(B
   $BD>8r:BI87O$KJQ49$7$?8e$N7W;;$K$D$$$F$OJL$N%3%i%`!V%W%l%$%d%-%c%i%/%?0\(B
   $BF0%"%k%4%j%:%`$N?'!9!W$G2r@b$7$?$N$G!"$3$3$G$O6K:BI87O$K$h$k0\F0$NI=8=(B
   $BJ}K!$K$D$$$F2r@b$7$^$9!#(B


   $B?^(B1: $BD>8r:BI87O(B
   (x$B<4$r1&8~$-!"(By$B<4$r2<8~$-$K$7$FD>8r$5$;$??^(B)

   $B?^(B2: $B6K:BI87O(B
   (x$B<4@5$N8~$-$+$i1&2sE>J}8~$KLp0u$N1_$rIA$$$??^(B)


   $B0J2<$N2r@b$G6&DL$7$F;HMQ$9$kJQ?t$N0UL#$O<!$NDL$j$G$9!#(B

   dx, dy    : $BB.EY(B($BD>8r:BI87O(B)
   r, theta  : $BB.EY(B($B6K:BI87O(B)

   $B$?$@$7!"(Btheta$B$O<!$N>r7o$rK~$9$b$N$H$7$^$9!#(B

   0 <= theta && theta < 2.0 * Math.PI


   1. $B3F:BI87O$N4V$G$NJQ49J}K!(B


   $B!&D>8r:BI87O(B(dx, dy) $B!]!d(B $B6K:BI87O(B(r, theta)

   (dx, dy)$B$+$i(Btheta$B$r7W;;$9$k4X?t$r0J2<$N$h$&$KDj5A$7$^$9!#(B
   
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

   $B$9$k$H!"JQ49$O<!$N(B2$B<0$GI=$5$l$^$9!#(B

   r = Math.sqrt(dx * dx + dy * dy);
   theta = Delta2Theta(dx, dy);


   $B!&6K:BI87O(B(r, theta) $B!]!d(B $BD>8r:BI87O(B(dx, dy)

   dx = r * Math.cos(theta);
   dy = r * Math.sin(theta);


   $BE($,<+J,$K8~$+$C$FCF$rH/<M$7$F967b$7$F$/$k>l9g$r9M$($F$_$^$7$g$&!#$3(B
   $B$l$OCF$rH/<M$9$k=hM}$H!"$=$N8eCF$,Ht$V=hM}$KJ,$+$l$^$9!#(B

   $B$3$3$G;HMQ$9$k3FJQ?t$N0UL#$O<!$NDL$j$G$9!#(B

   $B<+J,$N:BI8(B           : mx, my
   $BE($N:BI8(B             : ex, ey
   $BCF$N:BI8(B             : sx, sy
   $BCF$NB.EY(B($BD>8r:BI87O(B) : dx, dy
   $BCF$NB.EY(B($B6K:BI87O(B)   : s_r($BB.$5(B), s_theta($B3QEY(B)

   $B$^$:$OCF$rH/<M$9$k=hM}$G$9$,!"CF$OE($+$iH/<M$5$l$^$9$+$i!"CF$N:BI8$O(B
   $BE($N:BI8$K=i4|2=$5$l$^$9!#(B

   sx = ex;
   sy = ey;

   $BCF$,<+J,$NJ}8~$KH/<M$5$l$k$h$&$KD>8r:BI87O$+$i6K:BI87O$X$NJQ49$rMQ$$(B
   $B$F3QEY$r7W;;$7$^$9!#<+J,$HE($N:BI8$,J,$+$C$F$$$k$H$9$k$H!"CF$rH/<M$9(B
   $B$k3QEY$O<!$N$h$&$K7W;;$7$^$9!#(B

   s_theta = Delta2Theta(mx - ex, my - ey);

   $BCF$NB.$5$ODj?t(BSHOT_SPEED$B$H$7$FDj5A$5$l$F$$$k$b$N$H$7$^$9!#(B

   s_r = SHOT_SPEED;

   $B8e$NCF$rHt$V=hM}$N$?$a$K6K:BI87O$+$iD>8r:BI87O$X$NJQ49$rMQ$$$F!":BI8(B
   $BA}J,$r7W;;$7$^$9!#(B

   dx = s_r * Math.cos(theta);
   dy = s_r * Math.sin(theta);


   $B<!$KCF$,Ht$V=hM}$G$9$,!"CF$,EyB.D>@~1?F0$9$k>l9g$O<!$N7W;;$r9T$&$@$1(B
   $B$G$9!#(B

   sx += dx;
   sy += dy;

   $B$b$7!"CF$,ESCf$G6J$,$k>l9g$d2C8:B.$9$k>l9g$O!"(Bs_theta$B$d(Bs_r$B$r99?7$7!"(B
   dx, dy$B$r7W;;$7D>$7$?8e!">e$N7W;;$r$9$k$3$H$K$J$j$^$9!#Nc$($P!"CF$,<+(B
   $BJ,$rDI$$$+$1$F$/$k$h$&$K$7$?$$>l9g$O!"Dj4|E*$K<!$N7W;;$K$h$C$F(B
   s_theta$B$r99?7$7$F$d$j$^$9!#(B

   s_theta = Delta2Theta(mx - sx, my - sy);


   2. $B3QEY$r07$&$?$a$NH=Dj4X?t72(B


   $BB.$5(B(r)$B$KHf$Y$F!"3QEY(B(theta)$B$r07$&$K$O$A$g$C$H$7$?%3%D$,I,MW$G$9!#3QEY(B
   (theta)$B$r07$&$H$-$KCN$C$F$*$/$HJXMx$J4X?t$r(B3$B$D$[$I>e$2$^$9!#(B


   InRange: $BAj<j$,<+J,$NA0J}$N$"$k0lDjHO0O$N3QEY$K$$$k$+$I$&$+H=Dj$9$k(B

   $BJV$jCM(B: $BHO0OFb$K$$$k>l9g$O(B1$B!"$=$&$G$J$$>l9g$O(B0

   $B0z?t(B: me - $B<+J,$N3QEY!"(Byou - $BAj<j$N3QEY!"(Brange - $B3QEY$NHO0O(B

   int InRange(double me, double you, double range)
   {
       double   dtheta = Math.abs(me - you);

       if (dtheta <= range) return 1;
       else return 0;
   }


   RightLeft: $BL\I8$N3QEY$r8~$/$K$O:81&$N$I$A$i$K2sE>$9$k$Y$-$+H=Dj$9$k(B

   $BJV$jCM(B: $B1&2sE>(B($B@5$NJ}8~(B)$B$N>l9g$O(B1$B!":82sE>(B($BIi$NJ}8~(B)$B$N>l9g$O(B-1

   $B0z?t(B: now - $B:#8=:_$N3QEY!"(Btarget - $BL\I8$N3QEY(B

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


   ForBack: $BAj<j$,<+J,$NA0J}$K$$$k$N$+8eJ}$K$$$k$N$+H=Dj$9$k(B

   $BJV$jCM(B: $BA0J}$K$$$k>l9g$O(B1$B!"8eJ}$K$$$k>l9g$O(B-1

   $B0z?t(B: me - $B<+J,$N3QEY!"(Byou - $BAj<j$N3QEY(B

   int ForBack(double me, double you)
   {
       me -= Math.PI / 2.0;
       if (me < 0.0) me += 2.0 * Math.PI;
       return RightLeft(me, you);
   }


   3. $B6K:BI87O$rMQ$$$?0\F0%"%k%4%j%:%`$NNc!'CvFMLT?J%-%c%i(B


   $B$3$3$G$O<B:]$K6K:BI87O$rMQ$$$?0\F0%"%k%4%j%:%`$NNc$H$7$F!"%^%&%9%+!<%=(B
   $B%k$K8~$+$C$FCvFMLT?J$7$F$/$k%-%c%i%/%?$N%W%m%0%i%`$r<($7$^$9!#@h$K>e$2(B
   $B$?(B3$B$D$N4X?t$r$=$l$>$lMQ$$$F$$$^$9$N$G;29M$K$7$F$/$@$5$$!#(B

   $B%/%i%9(Bmoshin$B$O!"0\F0$N%"%k%4%j%:%`$@$1$rCj=P$7$?$b$N$J$N$G!"$=$l$r(B
   $BI=<($K7k$S$D$1$k$b$N$,I,MW$G$9!#$=$N$?$a$K!"Bh(B4$B>O$G>R2p$7$?(B
   $B%2!<%`$N%9%1%k%H%s%W%m%0%i%`$r;H$$$^$9!#$3$l$i$N%U%!%$%k$OJL!9$K$J$C$F(B
   $B$$$^$9!#JL!9$K%3%s%Q%$%k$7$F!"<B9T$9$k;~$K(Bjava$B$,<+F0E*$KFI$_$3$_$^$9!#(B

*/

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

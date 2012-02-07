/*
$B"#%W%l%$%d%-%c%i%/%?0\F0%"%k%4%j%:%`$N?'!9(B

$B%W%l%$%d%-%c%i%/%?!"$9$J$o$A<g?M8x!"<+5!$J$I$,<+J,$N;W$C$?DL$j$K0\F0$7(B
$B$J$/$F%$%i%$%i$7$?7P83$OL5$$$@$m$&$+!#%W%l%$%d%-%c%i%/%?$N0\F0$N=PMh$N(B
$BNI$70-$7$O%2!<%`$N=PMh$NNI$70-$7$K?<$/1F6A$rM?$($k!#%2!<%`%W%m%0%i%_%s(B
$B%0$K$*$$$F!"%W%l%$%d%-%c%i%/%?$N0\F0%"%k%4%j%:%`$OHs>o$K=EMW$G$"$k!#(B

$B%W%l%$%d%-%c%i%/%?$N0\F0$H$O!"%W%l%$%d$+$i$N%8%g%$%9%F%#%C%/$NF~NO$K$h$C(B
$B$F!"%-%c%i%/%?$N:BI8$rJQ2=$5$;$k$3$H$G$"$k!#$3$N:BI8$NJQ2=$N%"%k%4%j%:(B
$B%`$O%2!<%`$4$H$K$h$C$F@i:9K|JL$G$"$k$,!"4pK\$H$J$kItJ,$O6&DL$G$"$k!#$3(B
$B$3$G$O$=$l$i$N%"%k%4%j%:%`$r(B4$B$D$KJ,N`$7$F2r@b$9$k!#2r@b$K$O(B2$B<!85$N:BI8(B
$B7O$rMQ$$$k$,!"$=$NB>$N:BI87O$K$b4JC1$K3HD%2DG=$G$"$k!#(B

$B0J2<$N2r@b$G6&DL$7$F;HMQ$9$kJQ?t$N0UL#$O<!$NDL$j$G$"$k!#(B

 x, y             : $B:BI8(B
 dx, dy           : $BB.EY%Y%/%H%k(B
 ddx, ddy         : $B2CB.EY%Y%/%H%k(B
 input_x, input_y : $B%W%l%$%d$+$i$NF~NO(B

$B%-%c%i%/%?$N0\F0$O<!$N<0$GI=$5$l$k!#(B

 x = x + dx;
 y = y + dy;

$B%-%c%i%/%?$N0\F0%"%k%4%j%:%`$O!"(Bdx, dy$B$NJQ2=%"%k%4%j%:%`$K$h$C$F!"<!$N(B
4$B$D$KJ,N`$G$-$k!#(B


1. $BEyB.%"%k%4%j%:%`(B

  dx = input_x;
  dy = input_y;

 $B%-%c%i%/%?$OEyB.D>@~0\F0$9$k!#:G$b4pK\E*$GC1=c$J%"%k%4%j%:%`$G$"$j!"F1(B
 $B;~$K:G$b$h$/MQ$$$i$l$k%"%k%4%j%:%`$G$"$k!#%W%l%$%d$NM=B,DL$j$K%-%c%i%/(B
 $B%?$,@53N$K0\F0$7$J$1$l$P$J$i$J$$$h$&$J%2!<%`$G$OFC$K$h$/MQ$$$i$l$k!#(B


2. $B47@-%"%k%4%j%:%`(B

  ddx = input_x;
  ddy = input_y;

  dx += ddx;
  dy += ddy;

 $B%W%l%$%d$,2CB.EY$rF~NO$7$J$$8B$j!"B.EY$OJQ2=$;$:%-%c%i%/%?$OEyB.D>@~0\(B
 $BF0$7B3$1$k!#1'Ch6u4V$d6uCf$G$N!"$U$o$U$o$7$?0\F0$r:F8=$G$-$k!#(B

 $B%8%c%s%W$9$k$H$-$N(By$B:BI8$K$bMQ$$$i$l!"$=$N$H$-(Bddy$B$O=ENO2CB.EY$H$J$k!#(B

 $B$3$N<0$@$1$G$O!"%W%l%$%d$,F1$8J}8~$KF~NO$7B3$1$k$H!"(Bdx, dy$B$O:]8BL5$/A}(B
 $B2C$^$?$O8:>/$7$F$7$^$&!#$=$3$G0J2<$K!"B.EY$r@)8B$9$kJ}K!$r(B2$B$D>R2p$9$k!#(B

 (1) dx, dy$B$r$=$l$>$l0lDjHO0OFb$KJ]$DJ}K!(B

  XMIN : dx$B$N:G>.CM(B
  XMAX : dx$B$N:GBgCM(B
  YMIN : dy$B$N:G>.CM(B
  YMAX : dy$B$N:GBgCM(B

  if (dx < XMIN) {
      dx = XMIN;
  } else if (dx > XMAX) {
      dx = XMAX;
  }
  if (dy < YMIN) {
      dy = YMIN;
  } else if (dy > YMAX) {
      dy = YMAX;
  }


 (2) $BB.$5(B($BB.EY%Y%/%H%kD9(B)$B$r0lDjHO0OFb$KJ]$DJ}K!(B

  speed    : $BB.$5(B
  MAXSPEED : $BB.$5$N:GBgCM(B
  sqrt     : $BJ?J}:,$r5a$a$k%i%$%V%i%j4X?t(B

  speed = Math.sqrt(dx * dx + dy * dy);

  if (MAXSPEED < speed) {
      dx = dx * MAXSPEED / speed;
      dy = dy * MAXSPEED / speed;
  }


3. $B=*C<%"%k%4%j%:%`(B

  RESIST : $BDq9378?t(B

  ddx = input_x;
  ddy = input_y;

  dx = dx + ddx - dx * RESIST;
  dy = dy + ddy - dy * RESIST;

 ddx $B$H(B dx * RESIST $B$,Ey$7$/$J$k$H(Bdx$B$OJQ2=$7$J$/$J$k!#(B
 ddx $B$H(B dx * RESIST $B$,Ey$7$J$j!"$+$D(B ddy $B$H(B dy * RESIST$BEy$7$/$J$k$H(B
 $BB.EY$,JQ2=$7$J$/$J$k!"$9$J$o$A=*C<B.EY$KC#$C$7$?$3$H$K$J$k!#(B

 $B47@-%"%k%4%j%:%`$N3HD%HG$H$b$$$(!"MQ$$$i$l$k>lLL$b47@-%"%k%4%j%:%`$H;w(B
 $BDL$C$F$$$k$,!"FC$K6u5$Cf$d?eCf$J$IDq93$N$"$k>lLL$G$N0\F0$KMQ$$$k!#(B

 dx, dy$B$O$=$l$>$l0lDjHO0OFb$KJ]$?$l!"HO0O$N9-$5$ODq9378?t(BRESIST$B$NCM$K$h$C(B
 $B$F7h$^$k!#HO0O$N7A>u$O(Bddx, ddy$B$N7A>u$HF10l$K$J$k!#$h$C$F!"B.$5(B($BB.EY%Y(B
 $B%/%H%kD9(B)$B$r0lDjHO0OFb$KJ]$A$?$$$H$-$O!"(Bddx, ddy($B$9$J$o$A!"(Binput_x,
 input_y)$B$N%Y%/%H%kD9$,0lDj$K$J$k$h$&$K$9$l$P$h$$!#(B


4. $BA26a%"%k%4%j%:%`(B

  RATE : dx, dy$B$NJQ2=N($G!"(B1$BL$K~$N@5$NCM(B

  dx = dx + (input_x - dx) * RATE;
  dy = dy + (input_y - dy) * RATE;

 dx, dy$B$NJQ2=NL$O$=$l$>$l(Binput_x, input_y$B$H$N:9$,Bg$-$$$[$IBg$-$/!"6a$E(B
 $B$/$K$D$l$F$@$s$@$s$H>.$5$/$J$k!#%-%c%i%/%?$N0\F0B.EY$O=y!9$K%W%l%$%d$N(B
 $BF~NOCM$K<}B+$7$F$$$/$h$&$KJQ2=$9$k!#(B

 $B%8%g%$%9%F%#%C%/$NF~NO$=$N$^$^$K%-%c%i%/%?$,0\F0$9$k$h$j$b!"%W%l%$%d$N(B
 $BF~NO$K3j$i$+$K=>$C$F$$$/$h$&$J0\F0$r$5$;$?$$$H$-$KMQ$$$i$l$k!#(B


$B%2!<%`FbMF$K9g$o$;$F!"$3$l$i$N%"%k%4%j%:%`$rE,@Z$K;H$$J,$1$k$3$H$G!"%W(B
$B%l%$%d%-%c%i%/%?$N0\F0$rL%NOE*$J$b$N$K$7!"%W%l%$%d$r%2!<%`$K0z$-9~$`$3(B
$B$H$,$G$-$k$@$m$&!#(B

$B0J2<$K!"$3$l$i$N%"%k%4%j%:%`$r4^$`%W%l%$%d%-%c%i$N%/%i%9$r<($9!#(B

*/



public class player {
	public final int TOSOKU = 0, KANSEI = 1, SHUTAN = 2, ZENKIN = 3;
	final double HR2 = Math.sqrt(2) / 2;
	final double dir2X[] = {0, HR2, 1, HR2, 0, -HR2, -1, -HR2, 0};
	final double dir2Y[] = {-1, -HR2, 0, HR2, 1, HR2, 0, -HR2, 0};

	double	x, y, dx, dy;
	int	width, height, size;
	double	speed, accel, resist, rate;
	
	player(int width, int height, int size, double speed, double accel, double rate) {
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
		
		// $B??>e$,(B0$B$G1&2s$j$K(B7$B$^$G!#F~NOL5$7$O(B8
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




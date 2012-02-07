/*
■プレイヤキャラクタ移動アルゴリズムの色々

プレイヤキャラクタ、すなわち主人公、自機などが自分の思った通りに移動し
なくてイライラした経験は無いだろうか。プレイヤキャラクタの移動の出来の
良し悪しはゲームの出来の良し悪しに深く影響を与える。ゲームプログラミン
グにおいて、プレイヤキャラクタの移動アルゴリズムは非常に重要である。

プレイヤキャラクタの移動とは、プレイヤからのジョイスティックの入力によっ
て、キャラクタの座標を変化させることである。この座標の変化のアルゴリズ
ムはゲームごとによって千差万別であるが、基本となる部分は共通である。こ
こではそれらのアルゴリズムを4つに分類して解説する。解説には2次元の座標
系を用いるが、その他の座標系にも簡単に拡張可能である。

以下の解説で共通して使用する変数の意味は次の通りである。

 x, y             : 座標
 dx, dy           : 速度ベクトル
 ddx, ddy         : 加速度ベクトル
 input_x, input_y : プレイヤからの入力

キャラクタの移動は次の式で表される。

 x = x + dx;
 y = y + dy;

キャラクタの移動アルゴリズムは、dx, dyの変化アルゴリズムによって、次の
4つに分類できる。


1. 等速アルゴリズム

  dx = input_x;
  dy = input_y;

 キャラクタは等速直線移動する。最も基本的で単純なアルゴリズムであり、同
 時に最もよく用いられるアルゴリズムである。プレイヤの予測通りにキャラク
 タが正確に移動しなければならないようなゲームでは特によく用いられる。


2. 慣性アルゴリズム

  ddx = input_x;
  ddy = input_y;

  dx += ddx;
  dy += ddy;

 プレイヤが加速度を入力しない限り、速度は変化せずキャラクタは等速直線移
 動し続ける。宇宙空間や空中での、ふわふわした移動を再現できる。

 ジャンプするときのy座標にも用いられ、そのときddyは重力加速度となる。

 この式だけでは、プレイヤが同じ方向に入力し続けると、dx, dyは際限無く増
 加または減少してしまう。そこで以下に、速度を制限する方法を2つ紹介する。

 (1) dx, dyをそれぞれ一定範囲内に保つ方法

  XMIN : dxの最小値
  XMAX : dxの最大値
  YMIN : dyの最小値
  YMAX : dyの最大値

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


 (2) 速さ(速度ベクトル長)を一定範囲内に保つ方法

  speed    : 速さ
  MAXSPEED : 速さの最大値
  sqrt     : 平方根を求めるライブラリ関数

  speed = Math.sqrt(dx * dx + dy * dy);

  if (MAXSPEED < speed) {
      dx = dx * MAXSPEED / speed;
      dy = dy * MAXSPEED / speed;
  }


3. 終端アルゴリズム

  RESIST : 抵抗係数

  ddx = input_x;
  ddy = input_y;

  dx = dx + ddx - dx * RESIST;
  dy = dy + ddy - dy * RESIST;

 ddx と dx * RESIST が等しくなるとdxは変化しなくなる。
 ddx と dx * RESIST が等しなり、かつ ddy と dy * RESIST等しくなると
 速度が変化しなくなる、すなわち終端速度に達っしたことになる。

 慣性アルゴリズムの拡張版ともいえ、用いられる場面も慣性アルゴリズムと似
 通っているが、特に空気中や水中など抵抗のある場面での移動に用いる。

 dx, dyはそれぞれ一定範囲内に保たれ、範囲の広さは抵抗係数RESISTの値によっ
 て決まる。範囲の形状はddx, ddyの形状と同一になる。よって、速さ(速度ベ
 クトル長)を一定範囲内に保ちたいときは、ddx, ddy(すなわち、input_x,
 input_y)のベクトル長が一定になるようにすればよい。


4. 漸近アルゴリズム

  RATE : dx, dyの変化率で、1未満の正の値

  dx = dx + (input_x - dx) * RATE;
  dy = dy + (input_y - dy) * RATE;

 dx, dyの変化量はそれぞれinput_x, input_yとの差が大きいほど大きく、近づ
 くにつれてだんだんと小さくなる。キャラクタの移動速度は徐々にプレイヤの
 入力値に収束していくように変化する。

 ジョイスティックの入力そのままにキャラクタが移動するよりも、プレイヤの
 入力に滑らかに従っていくような移動をさせたいときに用いられる。


ゲーム内容に合わせて、これらのアルゴリズムを適切に使い分けることで、プ
レイヤキャラクタの移動を魅力的なものにし、プレイヤをゲームに引き込むこ
とができるだろう。

以下に、これらのアルゴリズムを含むプレイヤキャラのクラスを示す。

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




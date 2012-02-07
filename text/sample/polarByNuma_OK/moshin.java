/*
   ■極座標系はゲームプログラミングに必須のアイテム


   ゲーム中でキャラクタや地形などの物体の移動を表現するときは、直交する座
   標軸の値(dx, dy)で速度を表す方法(直交座標系)より、距離と角度(r, theta)
   で速度を表す方法(極座標系)を用いた方が便利です。どうしてかというとゲー
   ム中の物体の移動は、たいてい次の二つの要素から成り立っているからです。

   ・速さを変える −＞ 距離(r)の変化
   ・向きを変える −＞ 角度(theta)の変化

   このような理由から、ゲームプログラミングでは物体の速度は極座標系で表現
   し、位置の計算はこれを直交座標系に変換してから行うことがよくあります。
   直交座標系に変換した後の計算については別のコラム「プレイヤキャラクタ移
   動アルゴリズムの色々」で解説したので、ここでは極座標系による移動の表現
   方法について解説します。


   図1: 直交座標系
   (x軸を右向き、y軸を下向きにして直交させた図)

   図2: 極座標系
   (x軸正の向きから右回転方向に矢印の円を描いた図)


   以下の解説で共通して使用する変数の意味は次の通りです。

   dx, dy    : 速度(直交座標系)
   r, theta  : 速度(極座標系)

   ただし、thetaは次の条件を満すものとします。

   0 <= theta && theta < 2 * Math.PI


   1. 各座標系の間での変換方法


   ・直交座標系(dx, dy) −＞ 極座標系(r, theta)

   (dx, dy)からthetaを計算する関数を以下のように定義します。
   
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

   すると、変換は次の2式で表されます。

   r = Math.sqrt(dx * dx + dy * dy);
   theta = Delta2Theta(dx, dy);


   ・極座標系(r, theta) −＞ 直交座標系(dx, dy)

   dx = r * Math.cos(theta);
   dy = r * Math.sin(theta);


   敵が自分に向かって弾を発射して攻撃してくる場合を考えてみましょう。こ
   れは弾を発射する処理と、その後弾が飛ぶ処理に分かれます。

   ここで使用する各変数の意味は次の通りです。

   自分の座標           : mx, my
   敵の座標             : ex, ey
   弾の座標             : sx, sy
   弾の速度(直交座標系) : dx, dy
   弾の速度(極座標系)   : s_r(速さ), s_theta(角度)

   まずは弾を発射する処理ですが、弾は敵から発射されますから、弾の座標は
   敵の座標に初期化されます。

   sx = ex;
   sy = ey;

   弾が自分の方向に発射されるように直交座標系から極座標系への変換を用い
   て角度を計算します。自分と敵の座標が分かっているとすると、弾を発射す
   る角度は次のように計算します。

   s_theta = Delta2Theta(mx - ex, my - ey);

   弾の速さは定数SHOT_SPEEDとして定義されているものとします。

   s_r = SHOT_SPEED;

   後の弾を飛ぶ処理のために極座標系から直交座標系への変換を用いて、座標
   増分を計算します。

   dx = s_r * Math.cos(theta);
   dy = s_r * Math.sin(theta);


   次に弾が飛ぶ処理ですが、弾が等速直線運動する場合は次の計算を行うだけ
   です。

   sx += dx;
   sy += dy;

   もし、弾が途中で曲がる場合や加減速する場合は、s_thetaやs_rを更新し、
   dx, dyを計算し直した後、上の計算をすることになります。例えば、弾が自
   分を追いかけてくるようにしたい場合は、定期的に次の計算によって
   s_thetaを更新してやります。

   s_theta = Delta2Theta(mx - sx, my - sy);


   2. 角度を扱うための判定関数群


   速さ(r)に比べて、角度(theta)を扱うにはちょっとしたコツが必要です。角度
   (theta)を扱うときに知っておくと便利な関数を3つほど上げます。


   InRange: 相手が自分の前方のある一定範囲の角度にいるかどうか判定する

   返り値: 範囲内にいる場合は1、そうでない場合は0

   引数: me - 自分の角度、you - 相手の角度、range - 角度の範囲

   int InRange(double me, double you, double range)
   {
   double   dtheta = Math.abs(me - you);

   if (dtheta <= range) return 1;
   else return 0;
   }


   RightLeft: 目標の角度を向くには左右のどちらに回転するべきか判定する

   返り値: 右回転(正の方向)の場合は1、左回転(負の方向)の場合は-1

   引数: now - 今現在の角度、target - 目標の角度

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


   ForBack: 相手が自分の前方にいるのか後方にいるのか判定する

   返り値: 前方にいる場合は1、後方にいる場合は-1

   引数: me - 自分の角度、you - 相手の角度

   int ForBack(double me, double you)
   {
   me -= Math.PI / 2;
   if (me < 0) me += 2 * Math.PI;
   return RightLeft(me, you);
   }


   3. 極座標系を用いた移動アルゴリズムの例：猪突猛進キャラ


   ここでは実際に極座標系を用いた移動アルゴリズムの例として、マウスカーソ
   ルに向かって猪突猛進してくるキャラクタのプログラムを示します。

--------------------------------------------------

  クラスmoshinは、移動のアルゴリズムだけを抽出したものなので、それを
表示に結びつけるものが必要です。そのために、第4章で紹介した
ゲームのスケルトンプログラムを使います。これらのファイルは別々になって
います。別々にコンパイルして、実行する時にjavaが自動的に読みこみます。



   */


class moshin {
    final double MAXSPEED = 12.0, ACCEL = 1.0, MUKU = Math.PI / 15.0;
    final int WAIT = 15;
    final int SEARCH = 0, ATTACK = 1;
    
    double x, y, dx, dy, r, theta;
    int width, height, size, phase, count;

    moshin(int width, int height, int size) {
		this.width = width;
		this.height = height;
		this.size = size;
		x = this.width / 2.0;
		y = this.width / 2.0;
		dx = dy = theta = r = 0.0;
		count = phase = 0;
    }

    public double getX() {return x;}
    public double getY() {return y;}
    public double getDX() {return dx;}
    public double getDY() {return dy;}
	public double getTheta() { return theta; }

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
		dx = r * Math.cos(theta);
		dy = r * Math.sin(theta);
		x += dx;
		y += dy;

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



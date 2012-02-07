public class player {
    public final int TOSOKU = 0, KANSEI = 1, SHUTAN = 2, ZENKIN = 3; // ���[�h�Ԍ�
    final double HR2 = Math.sqrt(2) / 2;  // 8��������̂��߂̃f�[�^
    final double dir2X[] = {0, HR2, 1, HR2, 0, -HR2, -1, -HR2, 0};
    final double dir2Y[] = {-1, -HR2, 0, HR2, 1, HR2, 0, -HR2, 0};

    double  x, y, dx, dy;                // �ʒu�A���x
    int     width, height, size;         // �A�v���b�g�̃T�C�Y�ƕ��̂̃T�C�Y
    double  speed, accel, resist, rate;  // ���ꂼ��̃A���S���Y���Ŏg���l

    // �R���X�g���N�^
    player(int width, int height, int size, 
        double speed, double accel, double rate) {
        this.width = width;    // �ϐ��Q��������
        this.height = height;
        this.size = size;
        this.speed = speed;
        this.accel = accel;
        this.rate = rate;

        x = this.width / 2;    // ���@��\�����̂́A�^�񒆂ɐݒ�B
        y = this.height / 2;
        resist = this.accel / this.speed;
        dx = dy = 0.0F;        // �ŏ��͎~�܂��Ă���B
    }

    public double getX() {return x;}  // ���̃N���X����̃A�N�Z�X�p���\�b�h
    public double getY() {return y;}

    public void move(boolean cursor[], int mode) {
        int direction = calcDirection(cursor);

        // ���[�h�ɂ���ĕ�����B
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

        // �ȉ��́A���ׂẴA���S���Y���̋��ʕ��������o�������́B
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

    // �L�[���͂̏�񂩂���������߂�B
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

        // �^�オ0�ŉE����7�܂ŁB���͂Ȃ���8�B
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

    // �����A���S���Y��
    void calcTosoku(int direction) {
        dx = dir2X[direction] * speed;
        dy = dir2Y[direction] * speed;
    }

    // �����A���S���Y��
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

    // �I�[�A���S���Y��
    void calcShutan(int direction) {
        dx += dir2X[direction] * accel - dx * resist;
        dy += dir2Y[direction] * accel - dy * resist;
    }

    // �Q�߃A���S���Y��
    void calcZenkin(int direction) {
        dx += (dir2X[direction] * speed - dx) * rate;
        dy += (dir2Y[direction] * speed - dy) * rate;
    }
}

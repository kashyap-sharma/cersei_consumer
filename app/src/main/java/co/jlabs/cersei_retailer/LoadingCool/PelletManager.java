package co.jlabs.cersei_retailer.LoadingCool;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import co.jlabs.cersei_retailer.LoadingCool.letter.ALetter;
import co.jlabs.cersei_retailer.LoadingCool.letter.DLetter;
import co.jlabs.cersei_retailer.LoadingCool.letter.GLetter;
import co.jlabs.cersei_retailer.LoadingCool.letter.ILetter;
import co.jlabs.cersei_retailer.LoadingCool.letter.LLetter;
import co.jlabs.cersei_retailer.LoadingCool.letter.Letter;
import co.jlabs.cersei_retailer.LoadingCool.letter.NLetter;
import co.jlabs.cersei_retailer.LoadingCool.letter.OLetter;
import co.jlabs.cersei_retailer.LoadingCool.pellet.FirstPellet;
import co.jlabs.cersei_retailer.LoadingCool.pellet.ForthPellet;
import co.jlabs.cersei_retailer.LoadingCool.pellet.Pellet;
import co.jlabs.cersei_retailer.LoadingCool.pellet.SecondPellet;
import co.jlabs.cersei_retailer.LoadingCool.pellet.ThirdPellet;

/**
 * Created by Kashyap on 02/19/16.
 */
public class PelletManager implements Pellet.AnimatorStateListen {

    private List<Pellet> mPellets;
    private List<Letter> mLetters;
    private int mEndNum = 0;
    private boolean isEnding = false;
    private CoolAnimView mView;

    public PelletManager(CoolAnimView view, int centerX, int centerY) {
        mView = view;
        mPellets = new ArrayList<>();
        mLetters = new ArrayList<>();
        initComponents(centerX, centerY);
    }

    /**
     * 初始化各个组件,包括球和字
     */
    public void initComponents(int x, int y) {
        // 加入小球
        this.addPellet(new FirstPellet(x - 180, y));
        this.addPellet(new ForthPellet(x + 180, y));
        this.addPellet(new ThirdPellet(x + 60, y));
        this.addPellet(new SecondPellet(x - 60, y));
        // 为小球添加结束动画监听
        for (Pellet p : mPellets) {
            p.setAnimatorStateListen(this);
        }
        // 加入字母
        this.addLetter(new LLetter(x - 325, y));
        this.addLetter(new OLetter(x - 205, y));
        this.addLetter(new ALetter(x - 85, y));
        this.addLetter(new DLetter(x + 35, y));
        this.addLetter(new ILetter(x + 125, y));
        this.addLetter(new NLetter(x + 205, y));
        this.addLetter(new GLetter(x + 325, y));

        startPelletsAnim();
    }

    /**
     * 开始小球的动画
     */
    public void startPelletsAnim() {
        isEnding = false;
        mEndNum = 0;
        for (Pellet p : mPellets) {
            p.startAnim();
        }
    }

    /**
     * 先进行小球的移动,同时小球进行结尾动画展示,当完成以为,小球结尾动画继续,同时开始文字的展示
     */
    public void showText() {
        isEnding = true;
        mEndNum = 0;
        for (Pellet p : mPellets) {
            p.endAnim();
        }
    }

    public void addPellet(Pellet pellet) {
        if (pellet != null) {
            mPellets.add(pellet);
            pellet.prepareAnim();
        }
    }

    public void addLetter(Letter letter) {
        if (letter != null) {
            mLetters.add(letter);
        }
    }

    public void drawTheWorld(Canvas canvas) {
        for (Pellet p : mPellets) {
            p.drawSelf(canvas);
        }

        for (Letter l : mLetters) {
            l.drawSelf(canvas);
        }
    }

    public void endAnim() {
        isEnding = true;
    }

    // 纪录动画结束的小球个数,当动画结束可以执行循环任务
    @Override
    public void onAnimatorEnd() {
        mEndNum++;
        if (mEndNum == mPellets.size()) {
            if (isEnding) {
                showText();
            } else {
                startPelletsAnim();
            }
        }
    }

    // 在小球移动到适合的位置后,字母出现
    @Override
    public void onMoveEnd() {
        mEndNum++;
        if (mEndNum == mPellets.size()) {
            for (Letter l : mLetters) {
                l.startAnim();
            }
        }
    }

    /**
     * 由第一个小球传达结束动画消息到manager中
     */
    @Override
    public void onAllAnimatorEnd() {
        mView.onAnimEnd();
    }
}

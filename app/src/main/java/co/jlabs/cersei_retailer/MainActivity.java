package co.jlabs.cersei_retailer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;


import com.gigamole.library.ArcProgressStackView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public final static int MODEL_COUNT = 1;

    // APSV
    private ArcProgressStackView mArcProgressStackView;

    // Buttons
    private Button mBtnShadowColor;
    private Button mBtnTextColor;

    // Wrappers
    private View mWrapperShadow;
    private View mWrapperAnimation;

    // Parsed colors
    private int[] mStartColors = new int[MODEL_COUNT];
    private int[] mEndColors = new int[MODEL_COUNT];

    // First full size of APSV
    private int mFullSize = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        // Get APSV
        mArcProgressStackView = (ArcProgressStackView) findViewById(R.id.apsv);

        // Get colors
        final String[] startColors = getResources().getStringArray(R.array.polluted_waves);
        final String[] endColors = getResources().getStringArray(R.array.default_preview);
        final String[] bgColors = getResources().getStringArray(R.array.medical_express);

        // Parse colors
        for (int i = 0; i < MODEL_COUNT; i++) {
            mStartColors[i] = Color.parseColor(startColors[i]);
            mEndColors[i] = Color.parseColor(endColors[i]);
        }

        // Set models
        final ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        models.add(new ArcProgressStackView.Model("Circle", 0, Color.parseColor(bgColors[0]), mStartColors[0]));
//        models.add(new ArcProgressStackView.Model("Progress", 0, Color.parseColor(bgColors[1]), mStartColors[1]));
//        models.add(new ArcProgressStackView.Model("Stack", 0, Color.parseColor(bgColors[2]), mStartColors[2]));
//        models.add(new ArcProgressStackView.Model("View", 0, Color.parseColor(bgColors[3]), mStartColors[3]));
        mArcProgressStackView.setModels(models);
        mArcProgressStackView.setSweepAngle(74);

        // Get wrappers
        mWrapperShadow = findViewById(R.id.wrapper_shadow);
        mWrapperAnimation = findViewById(R.id.wrapper_animation);


//        // Get checkboxes
//        final CheckBox cbAnimating = (CheckBox) findViewById(R.id.cb_animating);
//        final CheckBox cbDragging = (CheckBox) findViewById(R.id.cb_dragging);
//        final CheckBox cbShadowing = (CheckBox) findViewById(R.id.cb_shadowing);
//        final CheckBox cbRounding = (CheckBox) findViewById(R.id.cb_rounding);
//        final CheckBox cbShowModelBg = (CheckBox) findViewById(R.id.cb_show_model_bg);
//        final CheckBox cbUseCustomTypeface = (CheckBox) findViewById(R.id.cb_use_custom_typeface);
//        final CheckBox cbUseOvershootInterpolator = (CheckBox) findViewById(R.id.cb_use_overshoot_interpolator);
//        final CheckBox cbUseVerticalOrientation = (CheckBox) findViewById(R.id.cb_use_vertical_orientation);
//        final CheckBox cbUseGradient = (CheckBox) findViewById(R.id.cb_use_gradient);
//
//        // Set checkboxes
//        cbAnimating.setOnCheckedChangeListener(this);
//        cbDragging.setOnCheckedChangeListener(this);
//        cbShadowing.setOnCheckedChangeListener(this);
//        cbRounding.setOnCheckedChangeListener(this);
//        cbShowModelBg.setOnCheckedChangeListener(this);
//        cbUseCustomTypeface.setOnCheckedChangeListener(this);
//        cbUseOvershootInterpolator.setOnCheckedChangeListener(this);
//        cbUseVerticalOrientation.setOnCheckedChangeListener(this);
//        cbUseGradient.setOnCheckedChangeListener(this);
//
//        onCheckedChanged(cbAnimating, cbAnimating.isChecked());
//        onCheckedChanged(cbDragging, cbDragging.isChecked());
//        onCheckedChanged(cbShadowing, cbShadowing.isChecked());
//        onCheckedChanged(cbRounding, cbRounding.isChecked());
//        onCheckedChanged(cbShowModelBg, cbShowModelBg.isChecked());
//        onCheckedChanged(cbUseCustomTypeface, cbUseCustomTypeface.isChecked());
//        onCheckedChanged(cbUseOvershootInterpolator, cbUseOvershootInterpolator.isChecked());
//        onCheckedChanged(cbUseVerticalOrientation, cbUseVerticalOrientation.isChecked());
//        onCheckedChanged(cbUseGradient, cbUseGradient.isChecked());
//
//        // Get buttons
//        mBtnTextColor = (Button) findViewById(R.id.btn_text_color);
//        mBtnShadowColor = (Button) findViewById(R.id.btn_shadow_color);
//        final Button btnAnimate = (Button) findViewById(R.id.btn_animate);
//        final Button btnReset = (Button) findViewById(R.id.btn_reset);
//
//        // Set buttons
//        mBtnTextColor.setOnClickListener(this);
//        mBtnShadowColor.setOnClickListener(this);
//        btnAnimate.setOnClickListener(this);
//        btnReset.setOnClickListener(this);
//
//        // Set default colors
//        handleSelectedColor(true, Color.DKGRAY);
//        handleSelectedColor(false, Color.WHITE);
//
//        // Get seekers
//        final SeekBar sbViewSize = (SeekBar) findViewById(R.id.pb_view_size);
//        final SeekBar sbShadowDistance = (SeekBar) findViewById(R.id.pb_shadow_distance);
//        final SeekBar sbShadowAngle = (SeekBar) findViewById(R.id.pb_shadow_angle);
//        final SeekBar sbShadowRadius = (SeekBar) findViewById(R.id.pb_shadow_radius);
//        final SeekBar sbAnimationDuration = (SeekBar) findViewById(R.id.pb_animation_duration);
//        final SeekBar sbDrawWidth = (SeekBar) findViewById(R.id.pb_draw_width);
//        final SeekBar sbModelOffset = (SeekBar) findViewById(R.id.pb_model_offset);
//        final SeekBar sbStartAngle = (SeekBar) findViewById(R.id.pb_start_angle);
//        final SeekBar sbSweepAngle = (SeekBar) findViewById(R.id.pb_sweep_angle);
//
//        // Set seekers
//        sbViewSize.setOnSeekBarChangeListener(this);
//        sbShadowDistance.setOnSeekBarChangeListener(this);
//        sbShadowAngle.setOnSeekBarChangeListener(this);
//        sbShadowRadius.setOnSeekBarChangeListener(this);
//        sbAnimationDuration.setOnSeekBarChangeListener(this);
//        sbDrawWidth.setOnSeekBarChangeListener(this);
//        sbModelOffset.setOnSeekBarChangeListener(this);
//        sbStartAngle.setOnSeekBarChangeListener(this);
//        sbSweepAngle.setOnSeekBarChangeListener(this);
//
//        // Set animator listener
//        mArcProgressStackView.setAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(final ValueAnimator animation) {
//                // Update goes here
//                Log.d("onAnimationUpdate: ", String.valueOf(animation.getAnimatedValue()));
//            }
//        });
//        mArcProgressStackView.setAnimatorListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(final Animator animation) {
//                Toast.makeText(MainActivity.this, "ANIMATION", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Start apsv animation on start
//        mArcProgressStackView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                btnAnimate.performClick();
//            }
//        }, 333);
    }

//    @Override
//    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
//        switch (buttonView.getId()) {
//            case R.id.cb_animating:
//                mArcProgressStackView.setIsAnimated(isChecked);
//                mWrapperAnimation.setVisibility(isChecked ? View.VISIBLE : View.GONE);
//                break;
//            case R.id.cb_dragging:
//                mArcProgressStackView.setIsDragged(isChecked);
//                break;
//            case R.id.cb_shadowing:
//                mArcProgressStackView.setIsShadowed(isChecked);
//                mArcProgressStackView.postInvalidate();
//                mWrapperShadow.setVisibility(isChecked ? View.VISIBLE : View.GONE);
//                break;
//            case R.id.cb_rounding:
//                mArcProgressStackView.setIsRounded(isChecked);
//                mArcProgressStackView.postInvalidate();
//                break;
//            case R.id.cb_show_model_bg:
//                mArcProgressStackView.setModelBgEnabled(isChecked);
//                break;
//            case R.id.cb_use_custom_typeface:
//                mArcProgressStackView.setTypeface(isChecked ? "fonts/agency.ttf" : "");
//                break;
//            case R.id.cb_use_overshoot_interpolator:
//                mArcProgressStackView.setInterpolator(isChecked ? new OvershootInterpolator() : null);
//                break;
//            case R.id.cb_use_vertical_orientation:
//                mArcProgressStackView.setIndicatorOrientation(isChecked ?
//                        ArcProgressStackView.IndicatorOrientation.VERTICAL :
//                        ArcProgressStackView.IndicatorOrientation.HORIZONTAL
//                );
//                break;
//            case R.id.cb_use_gradient:
//                for (int i = 0; i < mArcProgressStackView.getModels().size(); i++) {
//                    final ArcProgressStackView.Model model = mArcProgressStackView.getModels().get(i);
//                    model.setColor(mStartColors[i]);
//                    model.setColors(isChecked ? new int[]{mStartColors[i], mEndColors[i]} : null);
//                }
//                mArcProgressStackView.requestLayout();
//                mArcProgressStackView.postInvalidate();
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onClick(final View v) {
//        switch (v.getId()) {
//            case R.id.btn_text_color:
////                showColorPicker(false);
//                break;
//            case R.id.btn_shadow_color:
////                showColorPicker(true);
//                break;
//            case R.id.btn_animate:
//                final Random random = new Random();
//                for (ArcProgressStackView.Model model : mArcProgressStackView.getModels())
//                    model.setProgress(random.nextInt(101));
//                mArcProgressStackView.animateProgress();
//                break;
//            case R.id.btn_reset:
//                finish();
//                startActivity(getIntent());
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
//        switch (seekBar.getId()) {
//            case R.id.pb_view_size:
//                if (mFullSize == -1)
//                    mFullSize = mArcProgressStackView.getSize();
//                mArcProgressStackView.getLayoutParams().height = (int) ((mFullSize * 0.5f) +
//                        (int) ((float) mFullSize * 0.5f * ((float) progress / 100.0f)));
//                mArcProgressStackView.requestLayout();
//                break;
//            case R.id.pb_shadow_distance:
//                mArcProgressStackView.setShadowDistance(progress);
//                mArcProgressStackView.postInvalidate();
//                break;
//            case R.id.pb_shadow_angle:
//                mArcProgressStackView.setShadowAngle(progress);
//                mArcProgressStackView.postInvalidate();
//                break;
//            case R.id.pb_shadow_radius:
//                mArcProgressStackView.setShadowRadius(progress);
//                mArcProgressStackView.postInvalidate();
//                break;
//            case R.id.pb_animation_duration:
//                mArcProgressStackView.setAnimationDuration(progress);
//                break;
//            case R.id.pb_draw_width:
//                mArcProgressStackView.setDrawWidthFraction((float) progress / 100.0f);
//                break;
//            case R.id.pb_model_offset:
//                mArcProgressStackView.setProgressModelOffset(progress - 50);
//                break;
//            case R.id.pb_start_angle:
//                mArcProgressStackView.setStartAngle(progress);
//                break;
//            case R.id.pb_sweep_angle:
//                mArcProgressStackView.setSweepAngle(progress);
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onStartTrackingTouch(final SeekBar seekBar) {
//        // Empty cause of implement
//    }
//
//    @Override
//    public void onStopTrackingTouch(final SeekBar seekBar) {
//        // Empty cause of implement
//    }

//    private void showColorPicker(final boolean isShadowColor) {
//        new ColorMixerDialog(
//                this,
//                isShadowColor ? mBtnShadowColor.getCurrentTextColor() : mBtnTextColor.getCurrentTextColor(),
//                new ColorMixer.OnColorChangedListener() {
//                    @Override
//                    public void onColorChange(final int i) {
//                        handleSelectedColor(isShadowColor, i);
//                    }
//                }).show();
//    }

    private void handleSelectedColor(final boolean isShadowColor, final int selectedColor) {
        if (isShadowColor) {
            mArcProgressStackView.setShadowColor(selectedColor);
            mBtnShadowColor.setTextColor(selectedColor);
        } else {
            mArcProgressStackView.setTextColor(selectedColor);
            mBtnTextColor.setTextColor(selectedColor);
        }
    }
}

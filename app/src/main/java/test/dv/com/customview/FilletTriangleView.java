package test.dv.com.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by davi on 2017/8/12.
 */

public class FilletTriangleView extends View {
    private float mRadius = 0;
    private int mBackgroundColor = Color.RED;

    private String mText = null;
    private int mTextColor = Color.BLACK;
    private float mTextSize = 36;

    private Paint mBgPaint = null;
    private Paint mTextPaint = null;

    public FilletTriangleView(Context context) {
        this(context, null);
    }

    public FilletTriangleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilletTriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FilletTriangleView);
        mRadius = a.getDimension(R.styleable.FilletTriangleView_radius, mRadius);
        mBackgroundColor = a.getColor(R.styleable.FilletTriangleView_bg_color, mBackgroundColor);
        mText = a.getString(R.styleable.FilletTriangleView_text);
        mTextColor = a.getColor(R.styleable.FilletTriangleView_text_color, mTextColor);
        mTextSize = a.getDimension(R.styleable.FilletTriangleView_text_size, getResources().getDimension(R.dimen.default_text_size));

        init();
    }

    private void init() {
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(mBackgroundColor);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
//        mTextPaint.setStrokeWidth(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measuredHeight = measureHeight(heightMeasureSpec);

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // Default size if no limits are specified.
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
            if (TextUtils.isEmpty(mText)) {
                result = 0;
            } else if (mTextSize != 0 && !TextUtils.isEmpty(mText)) {
                int wrapSize = (int) (mText.length() * mTextSize * 2.5);
                if (wrapSize < specSize) {
                    result = wrapSize;
                }
            }
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }

        return result;
    }


    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // Default size if no limits are specified.
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
            if (TextUtils.isEmpty(mText)) {
                result = 0;
            } else if (mTextSize != 0 && !TextUtils.isEmpty(mText)) {
                int wrapSize = (int) (mText.length() * mTextSize * 2.6);
                if (wrapSize < specSize) {
                    result = wrapSize;
                }
            }
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        int maxRadius = Math.min(width, height) / 2;
        float radius = mRadius;
        if (radius > maxRadius) {
            radius = maxRadius;
        }

        Path sectorPath = new Path();
        //sectorPath.addArc(0, 0, radius * 2, radius * 2, 180, 90);
        sectorPath.addArc(new RectF(0, 0, radius * 2, radius * 2), 180, 90);
        sectorPath.moveTo(0, radius);
        sectorPath.lineTo(radius, radius);
        sectorPath.lineTo(radius, 0);

        Path trianglePath = new Path();
        trianglePath.moveTo(radius, 0);
        trianglePath.lineTo(0, radius);
        trianglePath.lineTo(0, height);
        trianglePath.lineTo(width, 0);

        canvas.drawPath(sectorPath, mBgPaint);
        canvas.drawPath(trianglePath, mBgPaint);

        // 居中画一个文字
        // 计算Baseline绘制的起点X轴坐标 ，计算方式：画布宽度的一半 - 文字宽度的一半
        int baseX = (int) (width / 4 - mTextPaint.measureText(mText) / 2);

        // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
        int baseY = (int) ((height / 3) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2));

        // 居中画一个文字
        canvas.drawText(mText, baseX, baseY, mTextPaint);
    }
}

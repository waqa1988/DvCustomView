package test.dv.com.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by davi on 2017/8/12.
 */

public class PanelView extends android.support.v7.widget.AppCompatButton {
    public static final int SHOW_LEFT = 0;
    public static final int SHOW_RIGHT = 1;

    private float mBorderWidth = 6;
    private float mPanelHeaderMarginHorizontal = 20;
    private float mPanelHeaderHeight = 120;
    private float mPanelHeaderRadius = 20;

    private int mContentColor = Color.WHITE;
    private int mBorderColor = Color.BLACK;

    private Paint mBorderPaint = null;
    private Paint mContentPaint = null;

    private int mShow = SHOW_LEFT;

    public PanelView(Context context) {
        this(context, null);
    }

    public PanelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PanelView);
        mBorderWidth = a.getDimension(R.styleable.PanelView_panel_border_width, mBorderWidth);
        mPanelHeaderMarginHorizontal = a.getDimension(R.styleable.PanelView_panel_header_margin_horizontal, mPanelHeaderMarginHorizontal);
        mPanelHeaderHeight = a.getDimension(R.styleable.PanelView_panel_header_height, mPanelHeaderHeight);
        mPanelHeaderRadius = a.getDimension(R.styleable.PanelView_panel_header_radius, mPanelHeaderRadius);
        mContentColor = a.getColor(R.styleable.PanelView_panel_content_color, mContentColor);
        mBorderColor = a.getColor(R.styleable.PanelView_panel_border_color, mBorderColor);
        mShow = a.getInt(R.styleable.PanelView_panel_show, SHOW_LEFT);

        init();
    }

    private void init() {
        mBorderPaint = new Paint();
        mBorderPaint.setColor(mBorderColor);  //设置画笔颜色
        mBorderPaint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        mBorderPaint.setStrokeWidth(mBorderWidth);//设置画笔宽度

        mContentPaint = new Paint();
        mContentPaint.setColor(mContentColor);  //设置画笔颜色
        mContentPaint.setStyle(Paint.Style.FILL);
    }

    public void setShowAction(int leftOrRight) {
        mShow = leftOrRight;
        invalidate();
    }

    public int getShowAction() {
        return mShow;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int measuredWidth = measureWidth(widthMeasureSpec);
//        int measuredHeight = measureHeight(heightMeasureSpec);
//
//        setMeasuredDimension(measuredWidth, measuredHeight);
//    }
//
//    private int measureHeight(int measureSpec) {
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//
//        // Default size if no limits are specified.
//        int result = 500;
//        if (specMode == MeasureSpec.AT_MOST) {
//            result = specSize;
//            if (TextUtils.isEmpty(mText)) {
//                result = 0;
//            } else if (mTextSize != 0 && !TextUtils.isEmpty(mText)) {
//                int wrapSize = (int) (mText.length() * mTextSize * 2.5);
//                if (wrapSize < specSize) {
//                    result = wrapSize;
//                }
//            }
//        } else if (specMode == MeasureSpec.EXACTLY) {
//            result = specSize;
//        }
//
//        return result;
//    }
//
//
//    private int measureWidth(int measureSpec) {
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//
//        // Default size if no limits are specified.
//        int result = 500;
//        if (specMode == MeasureSpec.AT_MOST) {
//            result = specSize;
//            if (TextUtils.isEmpty(mText)) {
//                result = 0;
//            } else if (mTextSize != 0 && !TextUtils.isEmpty(mText)) {
//                int wrapSize = (int) (mText.length() * mTextSize * 2.6);
//                if (wrapSize < specSize) {
//                    result = wrapSize;
//                }
//            }
//        } else if (specMode == MeasureSpec.EXACTLY) {
//            result = specSize;
//        }
//
//        return result;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        Path borderPath = new Path();
        RectF rect = null;
        if (mShow == SHOW_LEFT) {
            rect = new RectF(mPanelHeaderMarginHorizontal, mBorderWidth / 2, width / 2 - mPanelHeaderMarginHorizontal, mPanelHeaderHeight);
        } else {
            rect = new RectF(width / 2 + mPanelHeaderMarginHorizontal, mBorderWidth / 2, width - mPanelHeaderMarginHorizontal, mPanelHeaderHeight);
        }
        float radii[] ={mPanelHeaderRadius, mPanelHeaderRadius, mPanelHeaderRadius, mPanelHeaderRadius, 0, 0, 0, 0};
        borderPath.addRoundRect(rect, radii, Path.Direction.CCW);

        // 通过Xfermode去掉roundRect底部边框
        int sc = canvas.saveLayer(0, 0, width, height, mBorderPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawPath(borderPath, mBorderPaint);
        mBorderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
//        int linePosY = mPanelHeaderHeight;
        if (mShow == SHOW_LEFT) {
            canvas.drawLine(mPanelHeaderMarginHorizontal - mBorderWidth / 2, mPanelHeaderHeight, width / 2 - mPanelHeaderMarginHorizontal + mBorderWidth / 2, mPanelHeaderHeight, mBorderPaint);
        } else {
            canvas.drawLine(width / 2 + mPanelHeaderMarginHorizontal - mBorderWidth / 2, mPanelHeaderHeight, width - mPanelHeaderMarginHorizontal - mBorderWidth / 2, mPanelHeaderHeight, mBorderPaint);
        }
        mBorderPaint.setXfermode(null);
        canvas.restoreToCount(sc);

        if (mShow == SHOW_LEFT) {
            // 去掉底部边框后，填补roundRect以外的左右两条横线
            // 绘制左边横线
            borderPath.moveTo(0, mPanelHeaderHeight - mBorderWidth);
            borderPath.lineTo(mPanelHeaderHeight - mBorderWidth / 2, mPanelHeaderHeight - mBorderWidth);
            canvas.drawPath(borderPath, mBorderPaint);
            // 绘制右边横线
            borderPath.moveTo(width / 2 + mBorderWidth / 2 - mPanelHeaderMarginHorizontal, mPanelHeaderHeight - mBorderWidth);
            borderPath.lineTo(width, mPanelHeaderHeight - mBorderWidth);
            canvas.drawPath(borderPath, mBorderPaint);
        } else {
            // 去掉底部边框后，填补roundRect以外的左右两条横线
            // 绘制左边横线
            borderPath.moveTo(0, mPanelHeaderHeight - mBorderWidth);
            borderPath.lineTo(width / 2 + mBorderWidth / 2 + mPanelHeaderMarginHorizontal, mPanelHeaderHeight - mBorderWidth);
            canvas.drawPath(borderPath, mBorderPaint);
            // 绘制右边横线
            borderPath.moveTo(width + mBorderWidth / 2 - mPanelHeaderMarginHorizontal, mPanelHeaderHeight - mBorderWidth);
            borderPath.lineTo(width, mPanelHeaderHeight - mBorderWidth);
            canvas.drawPath(borderPath, mBorderPaint);
        }

        // 绘制带圆角的标签内圆角填充块
        Path contentPath = new Path();
        RectF contentRect = null;
        if (mShow == SHOW_LEFT) {
            contentRect = new RectF(mPanelHeaderMarginHorizontal + mBorderWidth / 2, mBorderWidth, width / 2 - mPanelHeaderMarginHorizontal - mBorderWidth / 2, mPanelHeaderHeight);
        } else {
            contentRect = new RectF(width / 2 + mPanelHeaderMarginHorizontal + mBorderWidth / 2, mBorderWidth, width - mPanelHeaderMarginHorizontal - mBorderWidth / 2, mPanelHeaderHeight);
        }
        float contentRadius = mPanelHeaderRadius / mBorderWidth * 4.5f;
        float radArray[] = {contentRadius, contentRadius, contentRadius, contentRadius, 0, 0, 0, 0};
        contentPath.addRoundRect(contentRect, radArray, Path.Direction.CCW);
        canvas.drawPath(contentPath, mContentPaint);
        // 绘制panel标签以下的矩形填充块
        RectF contentRect2 =  new RectF(0, mPanelHeaderHeight - mBorderWidth / 2, width, height);
        contentPath.addRect(contentRect2, Path.Direction.CCW);
        canvas.drawPath(contentPath, mContentPaint);

    }
}

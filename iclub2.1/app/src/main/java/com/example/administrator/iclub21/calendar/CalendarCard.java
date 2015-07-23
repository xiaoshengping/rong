package com.example.administrator.iclub21.calendar;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.List;

public class CalendarCard extends View {  
  
    private static final int TOTAL_COL = 7; // 7列  
    private static final int TOTAL_ROW = 6; // 6行  
  
    private Paint mCirclePaint; // 绘制圆形的画笔  
    private Paint mTextPaint; // 绘制文本的画笔  
    private int mViewWidth; // 视图的宽度  
    private int mViewHeight; // 视图的高度  
    private int mCellSpace; // 单元格间距  
    private Row rows[] = new Row[TOTAL_ROW]; // 行数组，每个元素代表一行  
    private static CustomDate mShowDate; // 自定义的日期，包括year,month,day
    private OnCellClickListener mCellClickListener; // 单元格点击回调事件  
    private int touchSlop; //  
    private boolean callBackCellSpace;  
  
    private Cell mClickCell;  
    private float mDownX;  
    private float mDownY;  
    
    
    private Context mContext;
    private Day user;
    private List<Day> list = null;
    private CalendarActivity ca = new CalendarActivity();
    private String colos;
    
    
    
    
    
  
    /** 
     * 单元格点击的回调接口 
     *  
     * @author wuwenjie 
     *  
     */  
    public interface OnCellClickListener {  
        void clickDate(CustomDate date); // 回调点击的日期
  
        void changeDate(CustomDate date); // 回调滑动ViewPager改变的日期
        
    }  
  
    public CalendarCard(Context context, AttributeSet attrs, int defStyleAttr) {  
        super(context, attrs, defStyleAttr);  
        init(context);  
    }  
  
    public CalendarCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);  
    }  
  
    public CalendarCard(Context context) {  
        super(context);  
        init(context);  
    }  
  
    public CalendarCard(Context context, OnCellClickListener listener) {  
        super(context);  
        mContext = context;
        this.mCellClickListener = listener;  
        init(context);  
    }  
  
    private void init(Context context) {  
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);  


//        mCirclePaint.setColor(Color.parseColor("#F24949")); // 红色圆形
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();  
  
        initDate();  
    }  
  
    private void initDate() {  
        mShowDate = new CustomDate();
        fillDate();//  
    }
  
    private void fillDate() {  
        int monthDay = DateUtil.getCurrentMonthDay(); // 今天
        int lastMonthDays = DateUtil.getMonthDays(mShowDate.year,
                mShowDate.month - 1); // 上个月的天数  
        int currentMonthDays = DateUtil.getMonthDays(mShowDate.year,
                mShowDate.month); // 当前月的天数  
        int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year,
                mShowDate.month);  
        boolean isCurrentMonth = false;  
        if (DateUtil.isCurrentMonth(mShowDate)) {
            isCurrentMonth = true;  
        }  
        int day = 0;  
        for (int j = 0; j < TOTAL_ROW; j++) {  
            rows[j] = new Row(j);  
            for (int i = 0; i < TOTAL_COL; i++) {  
                int position = i + j * TOTAL_COL; // 单元格位置



                // 这个月的
                if (position >= firstDayWeek  
                        && position < firstDayWeek + currentMonthDays) {  
                    day++;

//                    if(day==currentMonthDays){
//                        adsf
//                    }
//                    CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
//                    rows[j].cells[i] = new Cell(date, State.CURRENT_MONTH_DAY, i, j);


                    String str = mShowDate.year+"-"+(mShowDate.month > 9 ? mShowDate.month : ("0" + mShowDate.month))+"-"+(day > 9 ? day : ("0" + day));

                    if(isCurrentMonth && day == monthDay ){
                        colos = "#3485FF";
                        CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                        rows[j].cells[i] = new Cell(date, State.TODAY, i, j,colos);

                    }else if(isCurrentMonth && day < monthDay){
                        colos = "#626466";
                        rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                mShowDate, day), State.CURRENT_MONTH_DAY, i, j,colos);
                    }else if(isCurrentMonth && day > monthDay){
                        colos = "#C4C7CC";
                        rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                mShowDate, day), State.CURRENT_MONTH_DAY, i, j,colos);
                    }else {
                        colos = "#C4C7CC";
                        rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                                mShowDate, day), State.CURRENT_MONTH_DAY, i, j,colos);
                    }

//                    if(ca.userType==1&&ca.dayBeanslist.size()<27){
//                        colos = "#E3E4E6";
//                        rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
//                                mShowDate, day), State.CURRENT_MONTH_DAY, i, j,colos);
//                    }

                    if(isCurrentMonth && day < monthDay) {}else {

                        if (ca.userType == 1) {

                            if (ca.dayBeanslist != null &&ca.dayBeanslist.size()>=27&& ca.dayBeanslist.get(day - 1).getStatus().equals("1") && ca.dayBeanslist.get(day - 1).getDay().equals(str)) {
                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                rows[j].cells[i] = new Cell(date, State.SETUP, i, j, colos);
                            }

                            if (ca.i.equals(mShowDate.year + "-" + (mShowDate.month > 9 ? mShowDate.month : ("0" + mShowDate.month)) + "-" + (day > 9 ? day : ("0" + day)))) {
                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                rows[j].cells[i] = new Cell(date, State.ORDER, i, j, colos);
                            }

                        } else if (ca.userType == 2) {

                            if (ca.dayBeanslist != null &&ca.dayBeanslist.size()>=27&& ca.dayBeanslist.get(day - 1).getStatus().equals("1") && ca.dayBeanslist.get(day - 1).getDay().equals(str)) {
                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                                rows[j].cells[i] = new Cell(date, State.SETUP, i, j, colos);
                            }

//                            DbUtils dbu = DbUtils.create(mContext);
//                            try {
//                                list = dbu.findAll(Selector.from(Day.class).where(WhereBuilder.b("id", "!=", -1).and("day", "=", mShowDate.year + "-" + (mShowDate.month > 9 ? mShowDate.month : ("0" + mShowDate.month)) + "-" + (day > 9 ? day : ("0" + day)))).orderBy("id").limit(10));
////                            list = dbu.findAll(Selector.from(Day.class).where(WhereBuilder.b("id", "！=", -1).and("day","=",mShowDate.year+"-"+(mShowDate.month > 9 ? mShowDate.month : ("0" + mShowDate.month))+"-"+(day > 9 ? day : ("0" + day)))).orderBy("id").limit(10));
//                            } catch (DbException e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//
//                            if (list != null && list.size() != 0) {
//                                mTextPaint.setColor(Color.parseColor("#3485FF"));
//                                CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
//                                rows[j].cells[i] = new Cell(date, State.SETUP, i, j, colos);
//                            }
                        }
                    }

                    // 过去一个月
                } else if (position < firstDayWeek) {
                    rows[j].cells[i] = new Cell(new CustomDate(mShowDate.year,
                            mShowDate.month - 1, lastMonthDays
                                    - (firstDayWeek - position - 1)),
                            State.PAST_MONTH_DAY, i, j,colos);
                    // 下个月
                } else if (position >= firstDayWeek + currentMonthDays) {
                    rows[j].cells[i] = new Cell((new CustomDate(mShowDate.year,
                            mShowDate.month + 1, position - firstDayWeek
                                    - currentMonthDays + 1)),
                            State.NEXT_MONTH_DAY, i, j,colos);
                }  
            }  
        }  
        mCellClickListener.changeDate(mShowDate);
    } 
    
    
    
    
  
    @Override  
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);  
        for (int i = 0; i < TOTAL_ROW; i++) {  
            if (rows[i] != null) {  
                rows[i].drawCells(canvas);  
            }  
        }  
    }  
  
    @Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
        mViewWidth = w;  
        mViewHeight = h;
        mCellSpace = Math.min(mViewHeight / TOTAL_ROW, mViewWidth / TOTAL_COL);
        if (!callBackCellSpace) {  
            callBackCellSpace = true;  
        }
        mTextPaint.setTextSize(mCellSpace / 3);  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        switch (event.getAction()) {  
        case MotionEvent.ACTION_DOWN:  
            mDownX = event.getX();  
            mDownY = event.getY();  
            break;  
        case MotionEvent.ACTION_UP:  
            float disX = event.getX() - mDownX;  
            float disY = event.getY() - mDownY;  
            if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {  
                int col = (int) (mDownX / mCellSpace);  
                int row = (int) (mDownY / mCellSpace);  
                measureClickCell(col, row);  
            }  
            break;  
        default:  
            break;  
        }  
  
        return true;  
    }  
  
    /** 
     * 计算点击的单元格 
     * @param col 
     * @param row 
     */  
    private void measureClickCell(int col, int row) {  
        if (col >= TOTAL_COL || row >= TOTAL_ROW)  
            return;  
        if (mClickCell != null) {  
            rows[mClickCell.j].cells[mClickCell.i] = mClickCell;  
        }  
        if (rows[row] != null) {  
            mClickCell = new Cell(rows[row].cells[col].date,  
                    rows[row].cells[col].state, rows[row].cells[col].i,  
                    rows[row].cells[col].j,colos);
  
            CustomDate date = rows[row].cells[col].date;
            date.week = col;  
            mCellClickListener.clickDate(date);  
  
            // 刷新界面  
            update();  
        }  
    }  
  
    /** 
     * 组元素 
     *  
     * @author wuwenjie 
     *  
     */  
    class Row {  
        public int j;  
  
        Row(int j) {  
            this.j = j;  
        }  
  
        public Cell[] cells = new Cell[TOTAL_COL];  
  
        // 绘制单元格  
        public void drawCells(Canvas canvas) {  
            for (int i = 0; i < cells.length; i++) {  
                if (cells[i] != null) {  
                    cells[i].drawSelf(canvas);  
                }  
            }  
        }  
  
    }  
  
    /** 
     * 单元格元素 
     *  
     * @author wuwenjie 
     *  
     */  
    class Cell {  
        public CustomDate date;
        public State state;  
        public int i;  
        public int j;
        public String textColor ;
  
        public Cell(CustomDate date, State state, int i, int j,String textColor) {
            super();  
            this.date = date;  
            this.state = state;  
            this.i = i;  
            this.j = j;
            this.textColor = textColor;
        }  
  
        public void drawSelf(Canvas canvas) {
            switch (state) {
            case TODAY: // 今天
                mCirclePaint.setStyle(Paint.Style.STROKE);
                mCirclePaint.setStrokeWidth(2);
                mCirclePaint.setColor(Color.parseColor(colos));
                mTextPaint.setColor(Color.parseColor(textColor));
                canvas.drawCircle((float) (mCellSpace * (i + 0.5)),  
                        (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,  
                        mCirclePaint);  
                break;
                case SETUP://设置
                    mCirclePaint.setStyle(Paint.Style.FILL);
                    mCirclePaint.setColor(Color.parseColor("#5DC890"));
                    mTextPaint.setColor(Color.parseColor(textColor));
                    canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                            (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                            mCirclePaint);

                    break;
                case ORDER: // 预约
                    mCirclePaint.setStyle(Paint.Style.FILL);
                    mCirclePaint.setColor(Color.parseColor("#FFFF00"));
                    mTextPaint.setColor(Color.parseColor(textColor));
                    canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                            (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                            mCirclePaint);
                    break;
                case CURRENT_MONTH_DAY: // 当前月日期
                mTextPaint.setColor(Color.parseColor(textColor));
                break;  
            case PAST_MONTH_DAY: // 过去一个月
            case NEXT_MONTH_DAY: // 下一个月  
                mTextPaint.setColor(Color.parseColor("#fffffe"));
                break;  
            case UNREACH_DAY: // 还未到的天  
                mTextPaint.setColor(Color.GRAY);
                break;
            default:  
                break;  
            }



            // 绘制文字  
            String content = date.day + "";  
            canvas.drawText(content,  
                    (float) ((i + 0.5) * mCellSpace - mTextPaint  
                            .measureText(content) / 2), (float) ((j + 0.7)  
                            * mCellSpace - mTextPaint  
                            .measureText(content, 0, 1) / 2), mTextPaint);  
        }  
    }  
  
    /** 
     *  
     * @author wuwenjie 单元格的状态 当前月日期，过去的月的日期，下个月的日期 
     */  
    enum State {  
        TODAY,SETUP,ORDER,CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, UNREACH_DAY;
    }  
  
    // 从左往右划，上一个月  
    public void leftSlide() {  
        if (mShowDate.month == 1) {  
            mShowDate.month = 12;  
            mShowDate.year -= 1;  
        } else {  
            mShowDate.month -= 1;  
        }  
        update();  
    }  
  
    // 从右往左划，下一个月  
    public void rightSlide() {  
        if (mShowDate.month == 12) {  
            mShowDate.month = 1;  
            mShowDate.year += 1;  
        } else {  
            mShowDate.month += 1;  
        }  
        update();  
    }  
  
    public void update() {  
        fillDate();  
        invalidate();  
    }  
  
}  
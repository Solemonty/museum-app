package gr.novidea.museumapp.staff.data

import android.R
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText

// ref: https://gist.github.com/alphamu/0d3055e0233c5749b8d6

@SuppressLint("AppCompatCustomView")
class PinEntryEditText : EditText {
    private var mSpace = 24f //24 dp by default, space between the lines
    private var mNumChars = 4f
    private var mLineSpacing = 8f //8dp by default, height of the text from our lines
    private var mClickListener: OnClickListener? = null
    private var mLineStroke = 1f //1dp by default
    private var mLineStrokeSelected = 2f //2dp by default
    private var mLinesPaint: Paint? = null
    private val textColor = "#000000"
    var mStates = arrayOf(
        intArrayOf(R.attr.state_selected),
        intArrayOf(R.attr.state_focused),
        intArrayOf(-R.attr.state_focused)
    )
    var mColors = intArrayOf(
        Color.GRAY,
        Color.WHITE,
        Color.GRAY
    )
    var mColorStates = ColorStateList(mStates, mColors)

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val multi = context.resources.displayMetrics.density
        mLineStroke = multi * mLineStroke
        mLineStrokeSelected = multi * mLineStrokeSelected
        mLinesPaint = Paint(paint)
        mLinesPaint!!.strokeWidth = mLineStroke
        this.setTextColor(Color.WHITE)

        /* if (!isInEditMode()) {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorControlActivated,
                    outValue, true);
            final int colorActivated = outValue.data;
            mColors[0] = colorActivated;

            context.getTheme().resolveAttribute(R.attr.colorPrimary,
                    outValue, true);
            final int colorDark = outValue.data;
            mColors[1] = colorDark;

            context.getTheme().resolveAttribute(R.attr.colorControlHighlight,
                    outValue, true);

            final int colorHighlight = outValue.data;
            mColors[2] = colorHighlight;
        }*/setBackgroundResource(0)
        mSpace = multi * mSpace //convert to pixels for our density
        mLineSpacing = multi * mLineSpacing //convert to pixels for our density
        mNumChars = attrs.getAttributeIntValue(XML_NAMESPACE_ANDROID, "maxLength", 4).toFloat()

        //Disable copy paste
        super.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {}
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return false
            }
        })
        // When tapped, move cursor to end of text.
        super.setOnClickListener { v: View? ->
            setSelection(text.length)
            if (mClickListener != null) {
                mClickListener!!.onClick(v)
            }
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        mClickListener = l
    }

    override fun setCustomSelectionActionModeCallback(actionModeCallback: ActionMode.Callback) {
        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.")
    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas);
        val text = text
        val textLength = text.length
        val availableWidth = width - paddingRight - paddingLeft
        val mCharSize: Float
        mCharSize = if (mSpace < 0) {
            availableWidth / (mNumChars * 2 - 1)
        } else {
            (availableWidth - mSpace * (mNumChars - 1)) / mNumChars
        }
        var startX = paddingLeft
        val bottom = height - paddingBottom

        //Text Width
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(getText(), 0, textLength, textWidths)
        paint.setColor(Color.parseColor(textColor))

        var i = 0
        while (i < mNumChars) {
            updateColorForLines(i == textLength)
            canvas.drawLine(
                startX.toFloat(), bottom.toFloat(), startX + mCharSize, bottom.toFloat(),
                mLinesPaint!!
            )
            if (getText().length > i) {
                val middle = startX + mCharSize / 2
                canvas.drawText(
                    text, i, i + 1, middle - textWidths[0] / 2, bottom - mLineSpacing,
                    paint
                )
            }
            startX += if (mSpace < 0) {
                (mCharSize * 2).toInt()
            } else {
                (mCharSize + mSpace).toInt()
            }
            i++
        }
    }

    private fun getColorForState(vararg states: Int): Int {
        return mColorStates.getColorForState(states, Color.GRAY)
    }

    /**
     * @param next Is the current char the next character to be input?
     */
    private fun updateColorForLines(next: Boolean) {
        if (isFocused) {
            mLinesPaint!!.strokeWidth = mLineStrokeSelected
            mLinesPaint!!.color = getColorForState(R.attr.state_focused)
            if (next) {
                mLinesPaint!!.color = getColorForState(R.attr.state_selected)
            }
        } else {
            mLinesPaint!!.strokeWidth = mLineStroke
            mLinesPaint!!.color = getColorForState(-R.attr.state_focused)
        }
    }

    fun setMaxNumbers(number: Int) {
        mNumChars = number.toFloat()
        var filters = arrayOfNulls<InputFilter>(1)
        filters[0] = LengthFilter(number)
        filters = filters
    }

    companion object {
        const val XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"
    }
}
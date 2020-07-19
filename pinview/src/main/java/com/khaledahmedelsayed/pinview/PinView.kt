package com.khaledahmedelsayed.pinview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_number.view.*
import kotlinx.android.synthetic.main.pin_view.view.*
import kotlin.properties.Delegates


class PinView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    // callback function that is invoked when pin code is completed
    private var onCompletedListener : OnCompletedListener? = null
    // callback function that is invoked when a pin key is clicked
    private var onPinKeyClickListener : OnPinKeyClickedListener? = null

    // variable that holds xml pin view attributes
    private lateinit var attributes: TypedArray

    //Delegate initialization to listen to currentPinCode changes
    private var currentPinCode by Delegates.observable("") { _, _, newValue ->
        val drawable = DrawableCompat.wrap(resources.getDrawable(R.drawable.oval, null))
        DrawableCompat.setTint(
            drawable, attributes.getColor(
                R.styleable.PinView_dotProgressColor,
                Color.BLACK
            )
        )
        if (newValue.length >= 0) initPinCodeProgress()
        if (newValue.length >= 1) pinOneProgress.background = drawable
        if (newValue.length >= 2) pinTwoProgress.background = drawable
        if (newValue.length >= 3) pinThreeProgress.background = drawable
        if (newValue.length >= 4) pinFourProgress.background = drawable

    }

    init {
        inflate(context, R.layout.pin_view, this)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.PinView)

        titleTextView.text = attributes.getString(R.styleable.PinView_titleName)

        //TypedValue.COMPLEX_UNIT_PX is the correct way to display actual dimension programmatically
        titleTextView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            attributes.getDimensionPixelSize(R.styleable.PinView_titleTextSize, 32).toFloat()
        )

        titleTextView.setTextColor(
            attributes.getColor(
                R.styleable.PinView_titleTextColor,
                Color.BLACK
            )
        )

        initPinCodeProgress()

        pinOneProgress.layoutParams.width =
            attributes.getDimensionPixelSize(R.styleable.PinView_dotRadius, 30)
        pinOneProgress.layoutParams.height =
            attributes.getDimensionPixelSize(R.styleable.PinView_dotRadius, 30)

        pinTwoProgress.layoutParams.width =
            attributes.getDimensionPixelSize(R.styleable.PinView_dotRadius, 30)
        pinTwoProgress.layoutParams.height =
            attributes.getDimensionPixelSize(R.styleable.PinView_dotRadius, 30)

        pinThreeProgress.layoutParams.width =
            attributes.getDimensionPixelSize(R.styleable.PinView_dotRadius, 30)
        pinThreeProgress.layoutParams.height =
            attributes.getDimensionPixelSize(R.styleable.PinView_dotRadius, 30)

        pinFourProgress.layoutParams.width =
            attributes.getDimensionPixelSize(R.styleable.PinView_dotRadius, 30)
        pinFourProgress.layoutParams.height =
            attributes.getDimensionPixelSize(R.styleable.PinView_dotRadius, 30)

        numbersGridView.adapter = NumbersAdapter(attributes)

        errorTextView.text = attributes.getString(R.styleable.PinView_errorMessageText)
        errorTextView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            attributes.getDimensionPixelSize(R.styleable.PinView_errorMessageTextSize, 32).toFloat()
        )

        errorTextView.setTextColor(
            attributes.getColor(
                R.styleable.PinView_errorMessageColor,
                Color.RED
            )
        )
    }

    private fun initPinCodeProgress() {

        val drawable = DrawableCompat.wrap(resources.getDrawable(R.drawable.oval, null))
        DrawableCompat.setTint(
            drawable, attributes.getColor(
                R.styleable.PinView_dotUnProgressColor,
                Color.LTGRAY
            )
        )

        pinOneProgress.background = drawable
        pinTwoProgress.background = drawable
        pinThreeProgress.background = drawable
        pinFourProgress.background = drawable
    }

    fun setOnCompletedListener(listener: OnCompletedListener){
        onCompletedListener = listener
    }

    fun setOnPinKeyClickListener(listener: OnPinKeyClickedListener){
        onPinKeyClickListener = listener
    }

    fun deletePin() {
        if (currentPinCode.isNotEmpty())
            currentPinCode = currentPinCode.dropLast(1)
    }

    fun clearPin() {
        currentPinCode = ""
    }

    fun showError(isEnabled : Boolean){
        if(isEnabled)
        {
            errorTextView.visibility = View.VISIBLE
        }
        else
        {
            errorTextView.visibility = View.GONE
        }
    }

    fun isErrorVisible (): Boolean {
        return errorTextView.isVisible
    }

    private fun appendNumber(number: Int) {
        if (currentPinCode.length < 3)
        {
            currentPinCode = currentPinCode.plus(number)
            onPinKeyClickListener?.onPinKeyClickedListener(number.toString())
        }
        else if (currentPinCode.length == 3) {
            currentPinCode = currentPinCode.plus(number)
            onCompletedListener?.onCompletedListener(currentPinCode)
        }

    }

    private inner class NumbersAdapter(private val attributes: TypedArray) :
        RecyclerView.Adapter<NumbersAdapter.ViewHolder>() {

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_number, parent, false)
            return ViewHolder(view, attributes)
        }

        override fun getItemCount(): Int {
            return 12
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(holder, position)
        }

        inner class ViewHolder(itemView: View, attributes: TypedArray) :
            RecyclerView.ViewHolder(itemView) {
            private val numberTextView: TextView = itemView.numberTextView
            private val deleteImageView: ImageView = itemView.deleteImageView

            init {
                val fontSize =
                    attributes.getDimensionPixelSize(R.styleable.PinView_numbersTextSize, 64)
                itemView.numberTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize.toFloat())

                deleteImageView.layoutParams.width = fontSize
                deleteImageView.layoutParams.height = fontSize

                if (adapterPosition < 9)
                    itemView.numberTextView.setTextColor(
                        attributes.getColor(
                            R.styleable.PinView_numbersTextColor,
                            Color.BLACK
                        )
                    )
                else
                    itemView.numberTextView.setTextColor(
                        attributes.getColor(
                            R.styleable.PinView_clearButtonColor,
                            Color.BLACK
                        )
                    )

                itemView.deleteImageView.setColorFilter(
                    attributes.getColor(
                        R.styleable.PinView_deleteButtonColor,
                        Color.BLACK
                    )
                )
            }

            fun bind(viewHolder: ViewHolder, position: Int) {
                val number = position + 1
                when {
                    position <= 8 -> {
                        viewHolder.apply {
                            numberTextView.text = number.toString()
                            itemView.setOnClickListener {
                                appendNumber(number)
                            }
                        }

                    }
                    position == 9 -> {
                        viewHolder.apply {
                            numberTextView.visibility = View.GONE
                            deleteImageView.visibility = View.VISIBLE
                            itemView.setOnClickListener {
                                deletePin()
                                onPinKeyClickListener?.onPinKeyClickedListener("delete")
                            }
                        }
                    }
                    position == 10 -> viewHolder.apply {
                        numberTextView.text = "0"
                        itemView.setOnClickListener {
                            appendNumber(0)
                            onPinKeyClickListener?.onPinKeyClickedListener("0")
                        }
                    }
                    position == 11 -> viewHolder.apply {
                        numberTextView.text = "C"
                        itemView.setOnClickListener {
                            clearPin()
                            onPinKeyClickListener?.onPinKeyClickedListener("clear")
                        }
                    }
                }

            }
        }
    }

}

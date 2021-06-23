package diplom.dev.aidhealth.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.activity.SetTimeCourseActivity
import diplom.dev.aidhealth.activity.TuningCourseActivity
import kotlinx.android.synthetic.main.activity_add_pills.view.*
import java.security.AccessController.getContext

class CourseDateAdapter(private var dates: MutableList<String>) :
    RecyclerView.Adapter<CourseDateAdapter.MyViewHolderCourseDate>() {


    class MyViewHolderCourseDate(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView? = null
        var choise = false
        init {
            textView = itemView.findViewById(R.id.textCourseDate)
            itemView.setOnClickListener(){
                val pos: Int = adapterPosition
                val text: String = (textView)?.getText().toString()
               // itemView.setBackgroundColor(Color.RED)
              //  Toast.makeText(itemView.context, "Нажали $pos, ${text}", Toast.LENGTH_SHORT).show()
            }


        }



    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseDateAdapter.MyViewHolderCourseDate {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_course_date_layout, parent, false)
       // notifyDataSetChanged()

        return CourseDateAdapter.MyViewHolderCourseDate(itemView)
    }

    override fun getItemCount() =  dates.size

    override fun onBindViewHolder(holder: CourseDateAdapter.MyViewHolderCourseDate, position: Int) {
        holder.textView?.text = dates[position]
        var date = dates
        holder.itemView.setOnLongClickListener(){
         //   holder.itemView.setBackgroundColor(Color.GREEN)
            delete(position, dates)
         //   holder.itemView.setBackgroundColor(Color.WHITE)

            //    val pos: Int = position
          //  dates.removeAt(position)
           // CourseDateAdapter(dates)


        //   TuningCourseActivity().deleteItem(position, holder.date)
            return@setOnLongClickListener true
        }

    }
    fun delete(pos: Int, dates: MutableList<String>){
       dates.removeAt(pos)
        notifyItemRemoved(pos)
        notifyDataSetChanged()
    }




}





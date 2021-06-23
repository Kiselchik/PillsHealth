package diplom.dev.aidhealth.recycler

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.Doctor
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.activity.SetTimeCourseActivity
import diplom.dev.aidhealth.db.handler.DbHandler

class DoctorsRecyclerAdapter( private val names: ArrayList<String>, private val context: Context) :
    RecyclerView.Adapter<DoctorsRecyclerAdapter.MyViewHolderDoctor>() {

    class MyViewHolderDoctor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView? = null
        init {
            textView = itemView.findViewById(R.id.textViewDoctors)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderDoctor {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_doctor_layout, parent, false)
        return MyViewHolderDoctor(itemView)
    }

    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: MyViewHolderDoctor, position: Int) {
        holder.textView?.text = names[position]
        var date = names
        holder.itemView.setOnLongClickListener(){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Тайтл")
            builder.setMessage("Удалить?")
            builder.setPositiveButton("Да"){dialog, which ->
                var dbDoctor = DbHandler(context = context)
                dbDoctor.deleteDoctor(Doctor.doctorsListID.get(position))

                // SetTimeCourseActivity().setDates(dates)

            }
            builder.setNegativeButton("Нет"){dialog, which ->

            }




            //   holder.itemView.setBackgroundColor(Color.GREEN)

            //delete(position, dates)
            //   holder.itemView.setBackgroundColor(Color.WHITE)

            //    val pos: Int = position
            //  dates.removeAt(position)
            // CourseDateAdapter(dates)


            //   TuningCourseActivity().deleteItem(position, holder.date)
            return@setOnLongClickListener true
        }
    }
}
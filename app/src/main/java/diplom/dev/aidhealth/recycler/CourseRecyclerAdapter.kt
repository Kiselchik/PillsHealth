package diplom.dev.aidhealth.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.Dates.dates
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.activity.AddCourseActivity
import diplom.dev.aidhealth.activity.CourseHistoryActivity
import diplom.dev.aidhealth.activity.UserSetCourseActivity
import diplom.dev.aidhealth.db.handler.DbCourseHandler
import diplom.dev.aidhealth.db.handler.DbCourseHistoryHandler
import diplom.dev.aidhealth.db.handler.DbCoursePointHandler
import diplom.dev.aidhealth.db.model.CourseHistory

class CourseRecyclerAdapter ( private val names: ArrayList<String>) :
    RecyclerView.Adapter<CourseRecyclerAdapter.MyViewHolderCourse>() {

    class MyViewHolderCourse(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView? = null
        init {
            textView = itemView.findViewById(R.id.textViewCourse)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderCourse {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_course_layout, parent, false)
        return MyViewHolderCourse(itemView)
    }

    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: MyViewHolderCourse, position: Int) {
        holder.textView?.text = names[position]
        holder.itemView.setOnClickListener(){
            var chooseCourseId = DataRecyclerCourse.dataRecycler[position]
          //  Toast.makeText(holder.itemView.context, "${chooseCourseId}", Toast.LENGTH_SHORT)

            val intent = Intent(holder.itemView.context, CourseHistoryActivity::class.java)
            intent.putExtra("chooseCourseId", chooseCourseId)
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener(){
            val builder = android.app.AlertDialog.Builder(holder.itemView.context)

            builder.setTitle("Удаление")
            builder.setMessage("Удалить курс?")
            builder.setPositiveButton("Да") { dialog, which ->
                var dbCoursePoint = DbCoursePointHandler(context = holder.itemView.context)
                dbCoursePoint.deleteRow(DataRecyclerCourse.dataRecycler[position])
                var dbCourseHistory = DbCourseHistoryHandler(context = holder.itemView.context)
                dbCourseHistory.deleteRow(DataRecyclerCourse.dataRecycler[position])
                var dbCourse = DbCourseHandler(context = holder.itemView.context)
                dbCourse.deleteRow(DataRecyclerCourse.dataRecycler[position])
                notifyItemRemoved(position)
                notifyDataSetChanged()
                Toast.makeText(holder.itemView.context,"Курс удален", Toast.LENGTH_SHORT).show()
              /*  val intent = Intent(holder.itemView.context, AddCourseActivity::class.java)
                holder.itemView.context. startActivity(intent)*/
                delete(position, names)


            }
            builder.setNegativeButton("Нет") { _,_ ->

            }

            val dialog: android.app.AlertDialog = builder.create()
            dialog.show()

            return@setOnLongClickListener true
        }

    }
    fun delete(pos: Int, dates: MutableList<String>){
        dates.removeAt(pos)
        notifyItemRemoved(pos)
        notifyDataSetChanged()
    }
}

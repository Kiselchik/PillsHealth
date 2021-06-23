package diplom.dev.aidhealth.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.PointCourse
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbCourseHandler
import diplom.dev.aidhealth.db.handler.DbCourseHistoryHandler
import diplom.dev.aidhealth.db.handler.DbCoursePointHandler
import diplom.dev.aidhealth.db.handler.DbStatusPointHandler
import java.nio.file.Files.delete
import java.text.SimpleDateFormat
import java.util.*

class PointsRecyclerAdapter (private var dates: MutableList<String>) :
    RecyclerView.Adapter<PointsRecyclerAdapter.MyViewHolderPoint>() {


    class MyViewHolderPoint(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView? = null
        var choise = false
        init {
            textView = itemView.findViewById(R.id.timePointText)
            itemView.setOnClickListener(){
                val pos: Int = adapterPosition
                val text: String = (textView)?.getText().toString()
                itemView.setBackgroundColor(Color.RED)
                //  Toast.makeText(itemView.context, "Нажали $pos, ${text}", Toast.LENGTH_SHORT).show()
            }


        }



    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PointsRecyclerAdapter.MyViewHolderPoint {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_point_course_layout, parent, false)
        // notifyDataSetChanged()

        return PointsRecyclerAdapter.MyViewHolderPoint(itemView)
    }

    override fun getItemCount() =  dates.size

    override fun onBindViewHolder(holder: PointsRecyclerAdapter.MyViewHolderPoint, position: Int) {
        holder.textView?.text = dates[position]
        var date = dates
        holder.itemView.setOnLongClickListener(){
            val builder = android.app.AlertDialog.Builder(holder.itemView.context)

            builder.setTitle("Изменение статуса")
            builder.setMessage("")
            builder.setPositiveButton("Выполнить") { dialog, which ->
                var dbStatusPoint = DbStatusPointHandler(context = holder.itemView.context)
               var statusIdDone = dbStatusPoint.getIdDone().get(0).id
                var dbCoursePoint = DbCoursePointHandler(context = holder.itemView.context)
                val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
                val currentDate = sdf.format(Date())
                dbCoursePoint.setCoursePointDone(statusIdDone, PointCourse.dataRecyclerId[position], currentDate)
                //Toast.makeText(holder.itemView.context,"Отложить", Toast.LENGTH_SHORT).show()
                /*  val intent = Intent(holder.itemView.context, AddCourseActivity::class.java)
                  holder.itemView.context. startActivity(intent)*/

            }
            builder.setNegativeButton("Удалить") { dialog, which ->
                var dbCoursePoint = DbCoursePointHandler(context = holder.itemView.context)
                dbCoursePoint.deleteCoursePoint(PointCourse.dataRecyclerId[position])
                delete(position, dates)
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
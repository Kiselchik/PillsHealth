package diplom.dev.aidhealth.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.Item
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.activity.CourseHistoryActivity
import diplom.dev.aidhealth.activity.PillInfoActivity
import diplom.dev.aidhealth.db.handler.DbCourseHandler
import diplom.dev.aidhealth.db.handler.DbCourseHistoryHandler
import diplom.dev.aidhealth.db.handler.DbCoursePointHandler
import diplom.dev.aidhealth.db.handler.DbPillsHandler

class CustomRecyclerAdapter(private val names: ArrayList<String>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView? = null
        init {
            textView = itemView.findViewById(R.id.textViewDiagnosis)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView?.text = names[position]
        holder.itemView.setOnClickListener(){
            DataRecyclerCourse.valueID =  Item.dataRecyclerItem[position]
            val intent = Intent(holder.itemView.context, PillInfoActivity::class.java)
         //   intent.putExtra("chooseCourseId", chooseCourseId)
            holder.itemView.context.startActivity(intent)
        }



        holder.itemView.setOnLongClickListener(){
            val builder = android.app.AlertDialog.Builder(holder.itemView.context)

            builder.setTitle("Удаление")
            builder.setMessage("Удалить Препарат?" +
                    "\n (при удалении препарата удалится и его курс)")
            builder.setPositiveButton("Да") { dialog, which ->
                var dbPill = DbPillsHandler(context =  holder.itemView.context)

                //получить айди удаляемых курсов
                var dbCourse = DbCourseHandler(context = holder.itemView.context)
               var coursesId =  dbCourse.getCourseId(Item.dataRecyclerItem[position])
                //удаление поинов, истории, курса
                for (i in 0..coursesId.size-1){
                    var dbCoursePoint = DbCoursePointHandler(context = holder.itemView.context)
                    dbCoursePoint.deleteRow(coursesId.get(i).id)
                    var dbCourseHistory = DbCourseHistoryHandler(context = holder.itemView.context)
                    dbCourseHistory.deleteRow(coursesId.get(i).id)
                    dbCourse.deleteRow(coursesId.get(i).id)
                }
                dbPill.deletePill(Item.dataRecyclerItem[position])
                notifyItemRemoved(position)
                notifyDataSetChanged()
                Toast.makeText(holder.itemView.context,"Препарат и курс удален", Toast.LENGTH_SHORT).show()
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
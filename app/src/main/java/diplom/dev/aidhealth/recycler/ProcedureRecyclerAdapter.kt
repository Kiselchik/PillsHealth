package diplom.dev.aidhealth.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.Item
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.*

class ProcedureRecyclerAdapter(private var dates: MutableList<String>) :
    RecyclerView.Adapter<ProcedureRecyclerAdapter.MyViewHolderProcedure>() {


    class MyViewHolderProcedure(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView? = null
        var choise = false
        init {
            textView = itemView.findViewById(R.id.textViewDiagnosis)
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
    ): ProcedureRecyclerAdapter.MyViewHolderProcedure {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)
        // notifyDataSetChanged()

        return ProcedureRecyclerAdapter.MyViewHolderProcedure(itemView)
    }

    override fun getItemCount() =  dates.size

    override fun onBindViewHolder(holder: ProcedureRecyclerAdapter.MyViewHolderProcedure, position: Int) {
        holder.textView?.text = dates[position]
        var date = dates
        holder.itemView.setOnLongClickListener(){
            val builder = android.app.AlertDialog.Builder(holder.itemView.context)

            builder.setTitle("Удаление")
            builder.setMessage("Удалить Процедуру?" +
                    "\n (при удалении процедуры удалится и ее курс)")
            builder.setPositiveButton("Да") { dialog, which ->
                var dbProcedure = DbProcedureHandler(context =  holder.itemView.context)
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
                dbProcedure.deleteProcedure(Item.dataRecyclerItem[position])

                notifyItemRemoved(position)
                notifyDataSetChanged()
                Toast.makeText(holder.itemView.context,"Процедура и курс удален", Toast.LENGTH_SHORT).show()
                /*  val intent = Intent(holder.itemView.context, AddCourseActivity::class.java)
                  holder.itemView.context. startActivity(intent)*/

                delete(position, dates)

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
package diplom.dev.aidhealth.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.*
import diplom.dev.aidhealth.activity.CourseHistoryActivity
import diplom.dev.aidhealth.activity.SymptomInfoActivity
import diplom.dev.aidhealth.db.handler.*

class SymptomRecyclerAdapter ( private val names: ArrayList<String>) :
    RecyclerView.Adapter<SymptomRecyclerAdapter.MyViewHolderSymptom>() {

    class MyViewHolderSymptom(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView? = null
        init {
            textView = itemView.findViewById(R.id.textViewCourse)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderSymptom {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_course_layout, parent, false)
        return MyViewHolderSymptom(itemView)
    }

    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: MyViewHolderSymptom, position: Int) {
        holder.textView?.text = names[position]
        holder.itemView.setOnClickListener(){
           // DataRecyclerDiagnosis.symptomID = DataRecyclerSymptoms.dataRecycler[position]

           // var chooseCourseId = DataRecyclerCourse.dataRecycler[position]
            //  Toast.makeText(holder.itemView.context, "${chooseCourseId}", Toast.LENGTH_SHORT)

            val intent = Intent(holder.itemView.context, SymptomInfoActivity::class.java)
            Symptom.symptomPointId = DataRecyclerSymptoms.dataRecycler[position]
           // intent.putExtra("chooseSymptomId", DataRecyclerSymptoms.dataRecycler[position])
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener(){
            val builder = android.app.AlertDialog.Builder(holder.itemView.context)

            builder.setTitle("Удаление")
            builder.setMessage("Удалить симптом?")
            builder.setPositiveButton("Да") { dialog, which ->
              /*  var dbDiagnosisSymptom = DbDiagnosisSymptomHandler(context = holder.itemView.context)
                dbDiagnosisSymptom.deleteRowSymptom(DataRecyclerSymptoms.dataRecycler[position])*/
                var dbSymptom = DbSymptomHandler(context = holder.itemView.context)
                dbSymptom.deleteRow(DataRecyclerSymptoms.dataRecycler[position])
                notifyItemRemoved(position)
                Toast.makeText(holder.itemView.context,"Cимптом удален", Toast.LENGTH_SHORT).show()
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
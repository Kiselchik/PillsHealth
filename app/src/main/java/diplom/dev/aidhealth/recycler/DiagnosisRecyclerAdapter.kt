package diplom.dev.aidhealth.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.DataRecyclerDiagnosis
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.activity.AddDiagnosisActivity
import diplom.dev.aidhealth.db.handler.*

class DiagnosisRecyclerAdapter(private val names: ArrayList<String>) :
    RecyclerView.Adapter<DiagnosisRecyclerAdapter.MyViewHolderDiagnosis>() {

    class MyViewHolderDiagnosis(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView? = null
        init {
            textView = itemView.findViewById(R.id.textViewDiagnosis)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderDiagnosis {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_diagnosis_layout, parent, false)
        return MyViewHolderDiagnosis(itemView)
    }

    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: MyViewHolderDiagnosis, position: Int) {
        holder.textView?.text = names[position]
        holder.itemView.setOnClickListener(){
           // var chooseCourseId = DataRecyclerCourse.dataRecycler[position]
            //  Toast.makeText(holder.itemView.context, "${chooseCourseId}", Toast.LENGTH_SHORT)

            val intent = Intent(holder.itemView.context, AddDiagnosisActivity::class.java)
          //  intent.putExtra("chooseCourseId", chooseCourseId)
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener(){
            val builder = android.app.AlertDialog.Builder(holder.itemView.context)

            builder.setTitle("Удаление")
            builder.setMessage("Удалить диагноз?")
            builder.setPositiveButton("Да") { dialog, which ->
                var dbDiagnosisSymptom = DbDiagnosisSymptomHandler(context = holder.itemView.context)
                dbDiagnosisSymptom.deleteRow(DataRecyclerDiagnosis.dataRecycler[position])
                var dbDiagnosis= DbDiagnosisHandler(context = holder.itemView.context)
                dbDiagnosis.deleteRow(DataRecyclerDiagnosis.dataRecycler[position])
                notifyItemRemoved(position)

                Toast.makeText(holder.itemView.context,"Диагноз удален", Toast.LENGTH_SHORT).show()
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

package diplom.dev.aidhealth.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerSymptoms
import diplom.dev.aidhealth.R

class AddDiagnosisRecyclerAdapter ( private val names: ArrayList<String>) :
    RecyclerView.Adapter<AddDiagnosisRecyclerAdapter.MyViewHolderAddDiagnosis>() {

    class MyViewHolderAddDiagnosis(itemView: View) : RecyclerView.ViewHolder(itemView){
        var checkBox: CheckBox? = null
        init {
            checkBox = itemView.findViewById(R.id.chooseSymptomChBox)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderAddDiagnosis {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_add_symptoms_layout, parent, false)
        return MyViewHolderAddDiagnosis(itemView)
    }

    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: MyViewHolderAddDiagnosis, position: Int) {
        holder.checkBox?.text = names[position]
        holder.checkBox?.setOnCheckedChangeListener(){buttonView, isChecked ->
         //holder.checkBox?.toggle()
            if(isChecked){
            if(DataRecyclerSymptoms.chooseSymptomId.size!=0){
            for(i in 0..DataRecyclerSymptoms.chooseSymptomId.size-1){
                if(DataRecyclerSymptoms.chooseSymptomId.contains(position)){
                    DataRecyclerSymptoms.chooseSymptomId.remove(position)
                }
                else{
                    DataRecyclerSymptoms.chooseSymptomId.add(position)
                    //holder.checkBox?.text = position.toString()
                }
            }}
            else{
                DataRecyclerSymptoms.chooseSymptomId.add(position)

            }}

              Toast.makeText(holder.itemView.context, "${DataRecyclerSymptoms.chooseSymptomId.size}", Toast.LENGTH_SHORT)


          /*  val intent = Intent(holder.itemView.context, CourseHistoryActivity::class.java)
            intent.putExtra("chooseCourseId", chooseCourseId)
            holder.itemView.context.startActivity(intent)*/
        }

    }
}
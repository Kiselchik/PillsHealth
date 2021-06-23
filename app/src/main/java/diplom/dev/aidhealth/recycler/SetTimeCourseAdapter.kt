package diplom.dev.aidhealth.recycler

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.Items
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.activity.MainActivity
import diplom.dev.aidhealth.activity.SetTimeCourseActivity
import java.util.ArrayList

class SetTimeCourseAdapter(val names: ArrayList<String>, checkButton: Boolean) :
    RecyclerView.Adapter<SetTimeCourseAdapter.MyViewHolderSetTime>() {
  //  var chooseItem = arrayListOf<String>()
    var toActivityCheck = checkButton

    class MyViewHolderSetTime(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null

        init {
            textView = itemView.findViewById(R.id.textCourseDate)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderSetTime {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_course_date_layout, parent, false)
        return MyViewHolderSetTime(itemView)
    }

    override fun getItemCount() = names!!.size

    override fun onBindViewHolder(holder: MyViewHolderSetTime, position: Int) {
        holder.textView?.text = names!![position]
        holder.itemView.setOnClickListener() {
            //val pos: Int = adapterPosition

            //если еще не выделено,то выделить

            if (!Items.chooseItem.contains(holder.textView?.text.toString())) {
               Items.chooseItem.add(holder.textView?.text.toString())
               // intent.putExtra("CHOOSEITEM", Items.chooseItem)
                // val text: String = (textView)?.getText().toString()
                holder.itemView.setBackgroundColor(Color.BLUE)
            } else {
                Items.chooseItem.remove("${holder.textView?.text.toString()}")
                holder.itemView.setBackgroundColor(Color.WHITE)


            }

         /*   if (Items.chooseItem.size > 0) {
                    if(toActivityCheck){
                        val intent = Intent(holder.itemView.context, MainActivity::class.java)

                        intent.putExtra("CHOOSEITEM", Items.chooseItem)
                        holder.itemView.context.startActivity(intent)
                    }
                else{
                        Toast.makeText(holder.itemView.context, "передает фолс", Toast.LENGTH_SHORT).show()
                    }

                //   SetTimeCourseActivity().itemsForSet = chooseItem
                //  SetTimeCourseActivity().setChooseitem(chooseItem)
                /* val intent = Intent(holder.itemView.context, SetTimeCourseActivity::class.java)
                intent.putExtra("CHOOSEITEM", chooseItem)
                holder.itemView.context.startActivity(intent)
*/

                //   Toast.makeText(holder.itemView.context, "Нажали $position ${chooseItem[position]}", Toast.LENGTH_SHORT).show()

            }*/
            //    var text = hol

            //  Toast.makeText(holder.itemView.context, "Нажали $position ${chooseItem[position]}", Toast.LENGTH_SHORT).show()
        }


    }



}
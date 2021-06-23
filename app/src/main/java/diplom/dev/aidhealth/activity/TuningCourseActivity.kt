package diplom.dev.aidhealth.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.icu.text.AlphabeticIndex
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.Items
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.recycler.CourseDateAdapter
import diplom.dev.aidhealth.recycler.DoctorsRecyclerAdapter
import diplom.dev.aidhealth.recycler.SetTimeCourseAdapter

class TuningCourseActivity : AppCompatActivity() {
    private lateinit var calendar: CalendarView
    private lateinit var buttonSetTime: Button

    var dates = mutableListOf<String>()
    var dateschoise = mutableMapOf<String,Boolean>()
   lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tuning_course)
        Items.chooseItem.clear()
        //var dates = mutableListOf<String>()
        recyclerView = findViewById(R.id.recyclerViewDate)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CourseDateAdapter(dates)

        initialize()
        setListener()
        calendar()

    }

    fun initialize(){
        calendar=findViewById(R.id.calendarView)
        buttonSetTime = findViewById(R.id.buttonSetTime)
        buttonSetTime.visibility = View.INVISIBLE
    }

    fun calendar() {

        calendar.setOnDateChangeListener { calendar, i, i2, i3 ->
            dates.add("$i3/${i2+1}/$i")
           dateschoise.put("$i3/$i2/$i", false)

            Toast.makeText(this, "${dates.size}", Toast.LENGTH_SHORT).show()
                (recyclerView.adapter as CourseDateAdapter).notifyItemInserted(dates.size-1)
                (recyclerView.adapter as CourseDateAdapter).notifyDataSetChanged()
            buttonSetTime.visibility = View.VISIBLE

        }
    }

        fun setListener(){
        buttonSetTime.setOnClickListener() {
            val intent = Intent(this@TuningCourseActivity, SetTimeCourseActivity::class.java)
            val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val editor = shPref.edit()
            val check = true
            editor.apply{
                putBoolean("CHECK", check)
            }.apply()
            var d = arrayListOf<String>()
            //  dates.add("hhh from dates")
            for(i in 0..dates.size-1){
                d.add(dates[i])
            }
            intent.putExtra("DATES", d)
            intent.putExtra("MODE", true)

            startActivity(intent)
            /*        val builder = AlertDialog.Builder(this@TuningCourseActivity)
            builder.setTitle("Тайтл")
            builder.setMessage("Выберети тип настройки времени")
            builder.setPositiveButton("Быстрый"){dialog, which ->

                val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val editor = shPref.edit()
                val check = true
                editor.apply{
                    putBoolean("CHECK", check)
                }.apply()

                val intent = Intent(this@TuningCourseActivity, SetTimeCourseActivity::class.java)
                var d = arrayListOf<String>()
             //  dates.add("hhh from dates")
                for(i in 0..dates.size-1){
                  d.add(dates[i])
               }
               intent.putExtra("DATES", d)
                intent.putExtra("MODE", true)

                 startActivity(intent)
               // SetTimeCourseActivity().setDates(dates)

            }
            builder.setNegativeButton("Ручной"){dialog, which ->

                val intent = Intent(this@TuningCourseActivity, SetTimeCourseActivity::class.java)
                intent.putExtra("MODE", false)
                var d = arrayListOf<String>()
                //  dates.add("hhh from dates")
                for(i in 0..dates.size-1){
                    d.add(dates[i])
                }
                intent.putExtra("DATES", d)
                startActivity(intent)
            }
            builder.setNeutralButton("Отмена"){_,_->
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            //dialog.setTitle("Диалог")
         //   calendar.visibility = View.INVISIBLE
        //    val intent = Intent(this@TuningCourseActivity, SetTimeCourseActivity::class.java)
        //    startActivity(intent)
        }*/
        }
    }



}
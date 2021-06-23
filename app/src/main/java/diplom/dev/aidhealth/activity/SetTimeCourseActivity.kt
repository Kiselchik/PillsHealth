package diplom.dev.aidhealth.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.Dates
import diplom.dev.aidhealth.Items
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.recycler.SetTimeCourseAdapter

class SetTimeCourseActivity : AppCompatActivity() {
    lateinit var setTimeButton: Button
    lateinit var recyclerView: RecyclerView
    var itemsForSet = arrayListOf<String>()
    lateinit var textView: TextView

    // var dates = mutableListOf<String>()
    var check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_time_course)
        // Toast.makeText(this, "СОВСЕМ ничего не  выполяется", Toast.LENGTH_SHORT)
        Items.numInDay = 0
        Items.timeFrom = ""
        Items.timeTo = ""
        Items.dose = ""
        Items.measuring = ""
        initialize()
        val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        // val savedCheck = shPref.getBoolean("CHECK", true)

        // textView.text =
//if(savedCheck){

        if(Dates.dates.size==0) {
            var Array = intent.getStringArrayListExtra("DATES")
            if (Array != null) {
                Dates.dates = Array
            }


            recyclerView = findViewById(R.id.recyclerViewDatesSetTime)
            recyclerView.layoutManager = LinearLayoutManager(this)
            //   recyclerView.adapter = SetTimeCourseAdapter(Array)
            recyclerView.adapter = Array?.let { SetTimeCourseAdapter(it, false) }
            val editor = shPref.edit()
            val check = false
            editor.apply {
                putBoolean("CHECK", check)
            }.apply()
        } else{
            recyclerView = findViewById(R.id.recyclerViewDatesSetTime)
            recyclerView.layoutManager = LinearLayoutManager(this)
            //   recyclerView.adapter = SetTimeCourseAdapter(Array)
            recyclerView.adapter = Dates.dates?.let { SetTimeCourseAdapter(it, false) }
            val editor = shPref.edit()
            val check = false
            editor.apply {
                putBoolean("CHECK", check)
            }.apply()

        }
        /*if(Array?.let { SetTimeCourseAdapter(it).chooseItem.size }!! >0){
        itemsForSet = SetTimeCourseAdapter(Array).chooseItem
        check = true
    }*/

    /*      else{
    var Array = intent.getStringArrayListExtra("CHOOSEITEM")

    recyclerView = findViewById(R.id.recyclerViewDatesSetTime)
    recyclerView.layoutManager = LinearLayoutManager(this)
    //   recyclerView.adapter = SetTimeCourseAdapter(Array)
    recyclerView.adapter = Array?.let { SetTimeCourseAdapter(it, false) }

}*/


        /*
            recyclerView = findViewById(R.id.recyclerViewDatesSetTime)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = SetTimeCourseAdapter(intent.getIntegerArrayListExtra("CHOOSEITEM"))*/


        setListener()


    }

    fun initialize(){
        setTimeButton = findViewById(R.id.SetTimePointCourseButton)
        textView = findViewById(R.id.textViewChooseDate)
       // setTimeButton.visibility = View.INVISIBLE
    }


    fun setChooseitem(chooseItem: ArrayList<Int>) {

       // itemsForSet = chooseItem
        //textView.text = itemsForSet[0].toString()


        }
        /*  if(itemsForSet.size>0){
              setTimeButton.visibility = View.VISIBLE
          }*/


   private fun setListener(){
        setTimeButton.setOnClickListener() {

           // val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

             //   var Array = intent.getStringArrayListExtra("DATES")

               // recyclerView = findViewById(R.id.recyclerViewDatesSetTime)
              //  recyclerView.layoutManager = LinearLayoutManager(this)
                //   recyclerView.adapter = SetTimeCourseAdapter(Array)
              //  recyclerView.adapter = Array?.let { SetTimeCourseAdapter(it, false) }
                if (Items.chooseItem.size>0) {
                  //  recyclerView.adapter =  SetTimeCourseAdapter(Array, true)
                    var dataIntent = intent.getBooleanExtra("MODE", false)
                    val intent = Intent(this@SetTimeCourseActivity, ManualSetingCoursePointActivity::class.java)
                    startActivity(intent)
                 /*   if(dataIntent){
                    val intent = Intent(this@SetTimeCourseActivity, SetingCoursePointActivity::class.java)
                   startActivity(intent)}
                    else{
                        val intent = Intent(this@SetTimeCourseActivity, ManualSetingCoursePointActivity::class.java)
                        startActivity(intent)
                    }*/
                }
            else{
                    Toast.makeText(this, "пустой", Toast.LENGTH_SHORT).show()

                }


                /*if(Array?.let { SetTimeCourseAdapter(it).chooseItem.size }!! >0){
                    itemsForSet = SetTimeCourseAdapter(Array).chooseItem
                    check = true
                }*/}


            /* val intent = Intent(this, TuningCourseActivity::class.java)
             startActivity(intent)*/

    }
    //TODO: Cколько раз в день? С какого по какое время- вывести в эдти текст для возможной корректироваки, дозы
    //

}
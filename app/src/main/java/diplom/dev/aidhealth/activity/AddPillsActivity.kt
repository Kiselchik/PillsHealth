package diplom.dev.aidhealth.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbPillsHandler
import diplom.dev.aidhealth.db.model.Pill
import java.text.SimpleDateFormat
import java.util.*


class AddPillsActivity : AppCompatActivity() {
    private lateinit var pillName: EditText
    private lateinit var pillSizePackEdText: EditText
    private lateinit var pillNumPackEdText: EditText
    private lateinit var textView: TextView
    private lateinit var unitSpinner: Spinner
    private lateinit var measurement : String
    private lateinit var insertPillButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pills)
        initializeView()
        listener()
        spinner()
    }

    private fun initializeView() {
        pillName = findViewById(R.id.pillNameEdText)
        pillSizePackEdText = findViewById(R.id.pillSizePackEdText)
        pillNumPackEdText = findViewById(R.id.pillNumPackEdText)
        textView = findViewById(R.id.textView)
        unitSpinner = findViewById(R.id.pillsSpinner)
        insertPillButton = findViewById(R.id.insert_pill_button)
    }

    fun spinner() {

        var units = arrayOf<String>("мл", "мг", "шт")
        /*  var adapter =
        ArrayAdapter.createFromResource(this, R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Вызываем адаптер
        unitSpinner.setAdapter(adapter)*/

        unitSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, units)
        unitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
               // TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    applicationContext,
                    "${units.get(position)}",
                    Toast.LENGTH_SHORT
                ).show()

                measurement = units.get(position)
            }
        }

    }

    fun listener() {
        insertPillButton.setOnClickListener() {
            val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val savedEmail = shPref.getString("EMAIL_KEY", "").toString()
            val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
            val currentDate = sdf.format(Date())
            var db =
                DbPillsHandler(context = this)
            var pack = Integer.parseInt(pillSizePackEdText.text.toString()).toDouble()
            var unity = Integer.parseInt(pillNumPackEdText.text.toString()).toDouble()
            var pill = Pill(
                pillName.text.toString(),
                pack*unity,
                measurement,
                savedEmail,
                currentDate)
            //  var db = DbHandler(context = this)
            db.insertPill(pill)
            /*    textView.text = ""
        myDbManager.openDb()
        myDbManager.insertToDb(pillName.text.toString())
        val dataList = myDbManager.readDbData()
        for (item in dataList){
            textView.append(item)
            textView.append("\n")
        }*/
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        /*    super.onDestroy()
      myDbManager.closeDb()*/
    }


}

package diplom.dev.aidhealth.activity

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbCourseHandler
import diplom.dev.aidhealth.db.handler.DbCoursePointHandler
import diplom.dev.aidhealth.db.handler.DbPillsHandler
import diplom.dev.aidhealth.db.handler.DbStatusPointHandler
import diplom.dev.aidhealth.db.model.Course
import java.text.SimpleDateFormat
import java.util.*


class PillInfoActivity : AppCompatActivity() {

    private lateinit var namePill: TextView
    private lateinit var residuePill: TextView
    private lateinit var plusNumPillIb: ImageButton
    private lateinit var minusNumPillIb: ImageButton
    private lateinit var packEdTxt: EditText
    private lateinit var unityEdTxt: EditText
    private lateinit var saveNumBtn: Button
    var plus = false
    var numNow = 0.0
    var doseSum = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_info)
        initialise()
        setText()
        setListener()
    }

    fun initialise(){
        namePill = findViewById(R.id.namePill)
        residuePill = findViewById(R.id.residuePill)
        plusNumPillIb = findViewById(R.id.plus_num_pill_ib)
        minusNumPillIb = findViewById(R.id.minus_num_pill_ib)
        packEdTxt = findViewById(R.id.pack_pill_edTxt)
        unityEdTxt = findViewById(R.id.unity_pill_edTxt)
        saveNumBtn = findViewById(R.id.saveNumBtn)
    }

    fun setText(){
        var dbPill = DbPillsHandler(context = this)
        var dataPill = dbPill.getOnePill()
        namePill.text = dataPill.get(0).title
        numNow = dataPill.get(0).num //всего упаковок
     //   var dose = arrayListOf<Int>() //принятые дозы
        //дай мне дату последнего пополнения ДЛЯ ПЛЬЗОВАТЕЛЯ
        var dateLast = SimpleDateFormat("dd/mm/yyyy HH:mm:ss").parse(dataPill.get(0).datetime) //дата последнего пополнения

        //ПОЛУЧИТЬ КУРСЫ С ЭТИМ ПРЕПАРАТОМ
        var dbCourse = DbCourseHandler(context = this)
        var courses = mutableListOf<Int>()
        var coursesPill = mutableListOf<Course>()
        for(i in 0..dataPill.size-1) {
            //получаем курсы, содержащие эту таблетку
                 coursesPill = dbCourse.getCourseId(dataPill.get(i).id)
            for(j in 0..coursesPill.size-1){
                courses.add(coursesPill.get(j).id)

            }
            // var dbCoursePoint = DbCoursePointHandler(context = this)
           // var dateDoses = dbCoursePoint.getDoses(dateLast, dataPill.get(i).id)
        }
        //вывести принятые  ДЛЯ КУРСА
        //вытащить их дозы и даты
        //оставить только нужные дозы и даты (больше последнего добавления)
        var dbCoursePoint = DbCoursePointHandler(context = this)
        var dataDoses = arrayListOf<String>()
        var dataDays = arrayListOf<String?>()
        //получить статус проинта ПРИНЯТ
        var dbStatusPoint = DbStatusPointHandler(context = this)
        var statusId = dbStatusPoint.getIdStatusPointConfirmed().get(0).id
        for(i in 0..courses.size-1) {
            DataRecyclerCourse.courseID = courses.get(i) //выбираем айди курса
            var dateCoursePoint = dbCoursePoint.getOneCourse() //получаем поинты для этого курса
            for(j in 0..dateCoursePoint.size-1){
                if(dateCoursePoint.get(j).statusPointID ==statusId){ //если статус поинта ПОДТВЕРЖДЕН
                    dataDoses.add(dateCoursePoint.get(j).dose) //доза каждого поинта
                    dataDays.add(dateCoursePoint.get(j).datetimeStatus) //доза каждого  поинта
                }

        }}

        //поиск поинтов после последнего пополнения
        // doseSum = 0 //сумма принятых доз
        for(i in 0..dataDays.size-1){
            var datePoint = SimpleDateFormat("dd/mm/yyyy HH:mm:ss").parse(dataDays.get(i)) //дата последнего пополнения

            if(dateLast<=datePoint){
                doseSum = doseSum.toDouble() + dataDoses.get(i).toDouble()
            }
        }



        residuePill.text =
                "Принято с последнего пополнения $doseSum \n" +
                "Осталось ${numNow-doseSum.toDouble()}"
    }

   private fun setListener(){
        plusNumPillIb.setOnClickListener(){
            plus = true
            packEdTxt.visibility = View.VISIBLE
            unityEdTxt.visibility = View.VISIBLE
            saveNumBtn.visibility = View.VISIBLE
        }
        minusNumPillIb.setOnClickListener(){
            plus = false
            packEdTxt.visibility = View.VISIBLE
            unityEdTxt.visibility = View.VISIBLE
            saveNumBtn.visibility = View.VISIBLE
        }

       saveNumBtn.setOnClickListener(){
           //получить текущее количество таблеток
           //получить введенное количество
           var packText = 0.toDouble()
           var unityText = unityEdTxt.text.toString().toDouble()
           if(!packEdTxt.text.toString().equals("")){
               packText = packEdTxt.text.toString().toDouble()
           }

           //+-
           var newNumPill: Double
           //изменить текущую дату и количество а бд
           if(packText==0.toDouble()){
               packText=1.toDouble()
           }
           if(plus){

                newNumPill = (numNow-doseSum)+(unityText*packText)
           }
           else{
               newNumPill = (numNow-doseSum)-(unityText*packText)
           }
           val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
           val currentDate = sdf.format(Date())
           var dbPill = DbPillsHandler(context = this)
           dbPill.updateNum(newNumPill, currentDate)
           packEdTxt.visibility = View.INVISIBLE
           unityEdTxt.visibility = View.INVISIBLE
           saveNumBtn.visibility = View.INVISIBLE
           Toast.makeText(this, "Количество препарата изменено", Toast.LENGTH_SHORT).show()
           numNow = 0.0
           doseSum = 0.0
           setText()
       }
    }

}
package diplom.dev.aidhealth.activity

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbHealthHandler
import kotlinx.android.synthetic.main.activity_set_time_course.*


class DynamicHealthActivity  : AppCompatActivity() {
    private lateinit var anyChart: AnyChartView
    var health1 = "Спокойное и уравновешенное"
    var health2 = "Светлое и приятное"
    var health3 = "Безразличное"
    var health4 = "Скучное"
    var health5 = "Радостное"
    var health6 = "Неудовлетворительное"
    var health7 = "Дремотное"
    var health8 = "Восторженное"
    var health9 = "Пресыщенное"
    var numHealth1 = 0
    var numHealth2 = 0
    var numHealth3 = 0
    var numHealth4 = 0
    var numHealth5 = 0
    var numHealth6 = 0
    var numHealth7 = 0
    var numHealth8 = 0
    var numHealth9 = 0
    private lateinit var datesHealthTxt: TextView
    var chooseTitle = ""
     var arrayDatesHealth1 = arrayListOf<String>()
    var arrayDatesHealth2 = arrayListOf<String>()
    var arrayDatesHealth3 = arrayListOf<String>()
    var arrayDatesHealth4 = arrayListOf<String>()
    var arrayDatesHealth5 = arrayListOf<String>()
    var arrayDatesHealth6 = arrayListOf<String>()
    var arrayDatesHealth7 = arrayListOf<String>()
    var arrayDatesHealth8 = arrayListOf<String>()
    var arrayDatesHealth9 = arrayListOf<String>()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynaimc_health)
        initialise()
        getData()
        healthPie()
    }
    private fun initialise(){
        //    graph = findViewById(R.id.graph)
        anyChart = findViewById(R.id.any_chart_dynamic_health)
        datesHealthTxt = findViewById(R.id.datesHealthTxt)
    }

    fun getData(){
        var dbHealth = DbHealthHandler(context = this)
        var arrayHealth = dbHealth.readHealth()

        for(i in 0..arrayHealth.size-1){
            if(arrayHealth.get(i).rating.equals(health1)){
                numHealth1++
                arrayDatesHealth1.add(arrayHealth.get(i).datetime)
            }
            if(arrayHealth.get(i).rating.equals(health2)){
                numHealth2++
                arrayDatesHealth2.add(arrayHealth.get(i).datetime)

            }
            if(arrayHealth.get(i).rating.equals(health3)){
                numHealth3++
                arrayDatesHealth3.add(arrayHealth.get(i).datetime)

            }
            if(arrayHealth.get(i).rating.equals(health4)){
                numHealth4++
                arrayDatesHealth4.add(arrayHealth.get(i).datetime)

            }
            if(arrayHealth.get(i).rating.equals(health5)){
                numHealth5++
                arrayDatesHealth5.add(arrayHealth.get(i).datetime)

            }
            if(arrayHealth.get(i).rating.equals(health6)){
                numHealth6++
                arrayDatesHealth6.add(arrayHealth.get(i).datetime)

            }
            if(arrayHealth.get(i).rating.equals(health7)){
                numHealth7++
                arrayDatesHealth7.add(arrayHealth.get(i).datetime)

            }
            if(arrayHealth.get(i).rating.equals(health8)){
                numHealth8++
                arrayDatesHealth8.add(arrayHealth.get(i).datetime)

            }
            if(arrayHealth.get(i).rating.equals(health9)){
                numHealth9++
                arrayDatesHealth9.add(arrayHealth.get(i).datetime)

            }

        }


        Toast.makeText(this@DynamicHealthActivity, "${arrayDatesHealth1.size}", Toast.LENGTH_SHORT).show()



    }

    private fun healthPie(){
        var pie = AnyChart.pie()
        pie.setOnClickListener(object : ListenersInterface.OnClickListener(arrayOf("x", "value")) {
            override fun onClick(event: Event) {
                Toast.makeText(
                    this@DynamicHealthActivity,
                    event.getData().get("x").toString() + ":" + event.getData().get("value"),
                    Toast.LENGTH_SHORT
                ).show()
                //datesHealthTxt.text = event.getData().get("x").toString()
                var title = event.getData().get("x").toString()
                var text = ""
    //            datesHealthTxt.text = ""
                if(title.equals(health1)){
                    for(i in 0..arrayDatesHealth1.size-1){
                        text = text + "\n "+arrayDatesHealth1.get(i).toString()
                        Toast.makeText(this@DynamicHealthActivity, "${arrayDatesHealth1.size}", Toast.LENGTH_SHORT).show()
                     //   datesHealthTxt.text = event.getData().get("x").toString()
                    }
                }
                else if(title.equals(health2)){
                    for(i in 0..arrayDatesHealth2.size-1){
                        text = text + "\n "+arrayDatesHealth2.get(i).toString()
                      //  datesHealthTxt.text = event.getData().get("x").toString()
                    }
                }
                else if(title.equals(health3)){
                    for(i in 0..arrayDatesHealth3.size-1){
                        text = text + "\n "+arrayDatesHealth3.get(i).toString()
                       // datesHealthTxt.text = event.getData().get("x").toString()
                    }
                }else if(title.equals(health4)){
                    for(i in 0..arrayDatesHealth4.size-1){
                        text = text + "\n "+arrayDatesHealth4.get(i).toString()
                        //datesHealthTxt.text = event.getData().get("x").toString()
                    }
                }else if(title.equals(health5)){
                    for(i in 0..arrayDatesHealth5.size-1){
                        text = text + "\n "+arrayDatesHealth5.get(i).toString()
                       // datesHealthTxt.text = event.getData().get("x").toString()
                    }
                }else if(title.equals(health6)){
                    for(i in 0..arrayDatesHealth6.size-1){
                        text = text + "\n "+arrayDatesHealth6.get(i).toString()
                       // datesHealthTxt.text = event.getData().get("x").toString()
                    }
                }else if(title.equals(health7)){
                    for(i in 0..arrayDatesHealth7.size-1){
                        text = text + "\n "+arrayDatesHealth7.get(i).toString()
                        Toast.makeText(this@DynamicHealthActivity, "fdgf", Toast.LENGTH_SHORT).show()
                    }
                }else if(title.equals(health8)){
                    for(i in 0..arrayDatesHealth8.size-1){
                        text = text + "\n "+arrayDatesHealth8.get(i).toString()
                       // datesHealthTxt.text = event.getData().get("x").toString()
                    }
                }else if(title.equals (health9)){
                    for(i in 0..arrayDatesHealth9.size-1){
                        text = text + "\n "+arrayDatesHealth9.get(i).toString()
                       // datesHealthTxt.text = event.getData().get("x").toString()
                    }
                }
                datesHealthTxt.text = text
            }
        })



        var dataPie = arrayListOf<DataEntry>()
        dataPie.add(ValueDataEntry(health1, arrayDatesHealth1.size))
        dataPie.add(ValueDataEntry(health2, arrayDatesHealth2.size))
        dataPie.add(ValueDataEntry(health3, arrayDatesHealth3.size))
        dataPie.add(ValueDataEntry(health4, arrayDatesHealth4.size))
        dataPie.add(ValueDataEntry(health5, arrayDatesHealth5.size))
        dataPie.add(ValueDataEntry(health6, arrayDatesHealth6.size))
        dataPie.add(ValueDataEntry(health7, arrayDatesHealth7.size))
        dataPie.add(ValueDataEntry(health8, arrayDatesHealth8.size))
        dataPie.add(ValueDataEntry(health9, arrayDatesHealth9.size))

        pie.data(dataPie)
        pie.title("")

        pie.labels().position("outside")
        pie.selected().explode(0)

       /* pie.legend().title().enabled(true)
        pie.legend().title()
            .text("Retail channels")
            .padding(0.0, 0.0, 10.0, 0.0)

        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)*/
        anyChart.setChart(pie)



    }
}
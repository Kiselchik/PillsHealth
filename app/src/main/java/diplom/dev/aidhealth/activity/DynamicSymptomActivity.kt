package diplom.dev.aidhealth.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.Symptom
import diplom.dev.aidhealth.db.handler.DbPillsHandler
import diplom.dev.aidhealth.db.handler.DbStatusPointHandler
import diplom.dev.aidhealth.db.handler.DbSymptomHandler
import diplom.dev.aidhealth.db.handler.DbSymptomPointHandler
import diplom.dev.aidhealth.dynamic.CoursePointDynamic
import kotlinx.android.synthetic.main.activity_add_course_layout2.view.*
import kotlinx.android.synthetic.main.fragment_dose_stably.*
import java.text.SimpleDateFormat

class DynamicSymptomActivity : AppCompatActivity() {
    private lateinit var chooseSymptomSpinner: Spinner
    private lateinit var anyChartViewSymptom: AnyChartView
    var chooseSymptom = 0
    var dates = arrayListOf<String>()
    var values = arrayListOf<Int>()
    var systemSymptomID = arrayListOf<String>()

    var check = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_symptom)
        initialise()
        chart(Symptom.symptomPointId)

    }

   private fun initialise(){
      // chooseSymptomSpinner = findViewById(R.id.choose_symptom_spinner)
       anyChartViewSymptom = findViewById(R.id.any_chart_view_symptom)
    }



    private fun chart(id: Int){
        //получить все поинты этого симптома
        var dbSymptomPointUser = DbSymptomPointHandler(context = this)
        var dataPoint = dbSymptomPointUser.getOneSymptomPoint(id)
        var arrayValue = arrayListOf<String>()
        var arraySymptomId = arrayListOf<String>()
        var arrayDates = arrayListOf<String>()
        for (i in 0..dataPoint.size-1){

            arrayValue.add(dataPoint.get(i).value)
            arraySymptomId.add(dataPoint.get(i).symptomId.toString())
          //  val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss").parse(dataPoint.get(i).datetime)
            arrayDates.add(dataPoint.get(i).datetime)
        }

        var arrayMeasurement = arrayListOf<String>()
        var dbSymptom = DbSymptomHandler(context = this)
        for(i in 0..arraySymptomId.size-1){
            var dbSymptom = DbSymptomHandler(context = this)
            var data = dbSymptom.getOneSymptom(arraySymptomId.get(i).toInt())
            if(data.get(0).systemSymptomId.equals("")){
                //cвой симптом
                 dates.add(arrayDates.get(i))
                 values.add(arrayValue.get(i).toInt())




            }else{
                //системный симпотм
                var mesurementSymptom = dbSymptom.getOneSymptom(arraySymptomId.get(i).toInt())
               // arrayMeasurement.add(mesurementSymptom.get(0).)
            }

        }
        val seriesData = arrayListOf<DataEntry>()
        for(i in 0..dates.size-1){
            var customDataEntry : ValueDataEntry
            customDataEntry = ValueDataEntry(dates.get(i), values.get(i))
            seriesData.add(customDataEntry)
            //   var dataPoint: DataPoint
            //  dataPoint = DataPoint(i.toDouble(), arrayA dherence . get (i).toDouble())
            //  arrayDataPoint.add(dataPoint)
            //TODO: узнать как поменять подписи осей
        }
        val cartesian: Cartesian = AnyChart.line()
        val set : Set = Set.instantiate()

        val series1Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value' }")

        val series1: Line = cartesian.line(series1Mapping)


        cartesian.animation(true)

        cartesian.padding(10.0, 20.0, 5.0, 20.0)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
            .yLabel(true) // TODO ystroke
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.title("")

        cartesian.yAxis(0).title("")
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        set.data(seriesData)


        series1.name("Измрения")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(3.0)
        series1.tooltip()
            .position("right")
            //  .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        //  graph.addSeries(series)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 0, 0.0)
        //  anyChartViewSymptom.clear()
        anyChartViewSymptom.setChart(cartesian)

    }



    }





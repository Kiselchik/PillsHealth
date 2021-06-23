package diplom.dev.aidhealth.activity


import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbCourseHandler
import diplom.dev.aidhealth.db.handler.DbCoursePointHandler
import diplom.dev.aidhealth.db.handler.DbStatusPointHandler
import diplom.dev.aidhealth.dynamic.CoursePointDynamic
import java.text.SimpleDateFormat
import java.util.*


class DynamicActivity  : AppCompatActivity() {
    private lateinit var graph: GraphView
    private lateinit var anyChart: AnyChartView
    private lateinit var courseInfoTxt: TextView
    var arrayCoursePointDynamic = arrayListOf<CoursePointDynamic>()
    //var arrayDates = arrayListOf<Date>()
    var arrayDatesString = arrayListOf<String>()
    var arrayAdherence = arrayListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)
        initialise()
        getDate()
        chart()
      //  graph()
    }
   private fun initialise(){
    //    graph = findViewById(R.id.graph)
       anyChart = findViewById(R.id.any_chart_view)
       courseInfoTxt = findViewById(R.id.courseInfoTxt)
    }

    fun getDate(){
        var date = ""
        var arrayStatuses = arrayListOf<Int>()
        var dbCoursePoint = DbCoursePointHandler(context = this)
        //DataRecyclerCourse.courseID = 1
        var coursePoint = dbCoursePoint.getOneCourse()
        var dbCourse = DbCourseHandler(context = this)
        var courseInfo = dbCourse.getOneCourse()
        courseInfoTxt.text = "Название курса: "+courseInfo.get(0).title +"\n Дата добавления: "+courseInfo.get(0).date
        var st=""

        Toast.makeText(this, "${coursePoint.get(0).day}", Toast.LENGTH_SHORT).show()
        var dayFirst = coursePoint.get(0).day
        for(i in 0..coursePoint.size-1){

            if(dayFirst.equals(coursePoint.get(i).day)  ){
            date = coursePoint.get(i).day
                if(st.length==0){
                    st = coursePoint.get(i).statusPointID.toString()
                }else{
                st = st+ "," +coursePoint.get(i).statusPointID}
               // arrayStatuses.add(coursePoint.get(i).statusPointID)
            }else{
                var coursePointDynamic = CoursePointDynamic(date, st)
                arrayCoursePointDynamic.add(coursePointDynamic)
                dayFirst = coursePoint.get(i).day
                date =dayFirst
                st=coursePoint.get(i).statusPointID.toString()
                if(i==coursePoint.size-1){
                    var coursePointDynamic = CoursePointDynamic(date, st)
                    arrayCoursePointDynamic.add(coursePointDynamic)
                }
            }
        }
      /*  for(i in 0..coursePoint.size-1){

            if(id==coursePoint.get(i).id){
                arrayDates.add(coursePoint.get(i).day)
                arrayStatuses.add(coursePoint.get(i).statusPointID)
            }
                else{
                    var coursePointDynamic = CoursePointDynamic(id, arrayDates, arrayStatuses)
                arrayCoursePointDynamic.add(coursePointDynamic)
                    id = coursePoint.get(i).id

        }
        }*/
    }


    private fun chart(){
        //подтвержден /(ожидание +отложен)
        //id статуса ожидание
        var dbStatusPoint = DbStatusPointHandler(context = this)
        var idWait = dbStatusPoint.getIdStatusPointWait().get(0).id
        //id статуса отложен
        var idPostponed = dbStatusPoint.getIdStatusPointPostponed().get(0).id
        //id статса подтвержден
        var idDone = dbStatusPoint.getIdStatusPointConfirmed().get(0).id

        var done = 0
        var wait = 0
        var postponed = 0

        for(i in 0..arrayCoursePointDynamic.size-1){
            var point: CoursePointDynamic
            point = arrayCoursePointDynamic.get(i)
            var statuses =  point.statuses.split(",")

            for(j in 0..statuses.size-1){
                if(statuses.get(j).equals(idDone.toString()) ){
                    done++
                }
                if(statuses.get(j).equals(idWait.toString())){
                    wait++
                }
                if(statuses.get(j).equals(idPostponed.toString())){
                    postponed++
                }
              //  val datePoint: Date = SimpleDateFormat("dd/mm/yyyy").parse("${point.date}")
                arrayDatesString.add(point.date)
                arrayAdherence.add(done / statuses.size)
            done = 0
                wait = 0
                postponed = 0
            }


        }
        val seriesData = arrayListOf<DataEntry>()
        for(i in 0..arrayAdherence.size-1){
            var customDataEntry : ValueDataEntry
            customDataEntry = ValueDataEntry(arrayDatesString.get(i), arrayAdherence.get(i))
            seriesData.add(customDataEntry)
         //   var dataPoint: DataPoint
          //  dataPoint = DataPoint(i.toDouble(), arrayA dherence . get (i).toDouble())
          //  arrayDataPoint.add(dataPoint)
            //TODO: узнать как поменять подписи осей
        }
        val cartesian: Cartesian = AnyChart.line()

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

        val set : Set = Set.instantiate()
        set.data(seriesData)
        val series1Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value' }")


        val series1: Line = cartesian.line(series1Mapping)
        series1.name("Приверженность")
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
       // cartesian.removeAllSeries()
        anyChart.setChart(cartesian)

    }
}
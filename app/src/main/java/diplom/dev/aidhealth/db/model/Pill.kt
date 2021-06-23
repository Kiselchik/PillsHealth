package diplom.dev.aidhealth.db.model

class Pill {
    var id: Int = 0//
    var email: String = ""//
    var title: String = ""//
    var num = 0.toDouble()
  //  var pack: Int = 0//
 //   var unity: Float = 0f //
    var measurement: String = "" //добавить
    var datetime: String = "" //дата добавления количества лекарства
    //добавить сюда описание (для чего лекарство)
   // var lost: String = "" //Float

    constructor(title: String, num: Double, measurement: String, email: String, datetime: String ) {
        this.title = title
        this.num = num
        this.measurement = measurement
        this.email = email
        this.datetime = datetime

    }

    constructor(){

    }
}
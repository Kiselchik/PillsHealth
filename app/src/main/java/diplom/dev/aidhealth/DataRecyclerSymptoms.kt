package diplom.dev.aidhealth

object DataRecyclerSymptoms {
    var ID = mutableListOf<Int>() //готовый перечень выбранный айди
    var symptomId = mutableListOf<Int>() //перечень всех выведенных сиптомов
    var chooseSymptomId = mutableListOf<Int>() // position выбранных сиптомов
    var dataRecycler = arrayListOf<Int>()
}
package diplom.dev.aidhealth.fragment

import diplom.dev.aidhealth.R
import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import diplom.dev.aidhealth.activity.SetDoseActivity


class DoseStablyFragment: androidx.fragment.app.Fragment() {
    private lateinit var stablyDoseEdTxt: EditText
    private lateinit var measurementText: TextView
    private lateinit var saveCourseBtn: Button
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dose_stably, container ,false)
        stablyDoseEdTxt = view.findViewById(R.id.stablyDoseEdTxt)
        measurementText = view.findViewById(R.id.measurementText)
        saveCourseBtn = view.findViewById(R.id.saveCourseBtn)
        var measurement = arguments?.getString("MEASUREMENT")
        measurementText.text = measurement
        initialise()
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initialise(){
        saveCourseBtn.setOnClickListener(){
            (activity as SetDoseActivity).saveCourse(stablyDoseEdTxt.text.toString().toInt())
        }
    }






}
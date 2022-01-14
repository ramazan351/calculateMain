package com.ramazanaltun.calculatemain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.ramazanaltun.calculatemain.databinding.ActivityMainBinding
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private val lessons= arrayOf("COMPUTER ENGINEERING ORIENTATION","INTRODUCTION TO COMPUTER ENG. CONCEPTS","C PROGRAMMING","LOGIC DESIGN","ALGORITHMS")
    private  var allNotes:ArrayList<LessonModel> = ArrayList()
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)
        var textAdapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,lessons)
        viewBinding.atLessonName.setAdapter(textAdapter)
        if (viewBinding.srclLessonView.childCount==0){
            viewBinding.btnCalcul.visibility = View.INVISIBLE
        }else             viewBinding.btnCalcul.visibility=View.VISIBLE
        Log.d("ramazan",viewBinding.srclLessonView.childCount.toString())
        viewBinding.btnSave.setOnClickListener {
            if (viewBinding.atLessonName.text.isNullOrEmpty()){
                Toast.makeText(this,"add a lesson name",Toast.LENGTH_LONG).show()
            }else{
                // we convert xml code to java with inflater
                var inflater=LayoutInflater.from(this)
                var mylessonView= inflater.inflate(R.layout.new_lesson_layout,null)


                var lessonName=viewBinding.atLessonName.text.toString()
                var lessonCredit=viewBinding.spnCredit.selectedItem.toString()
                var lessonNote=viewBinding.spnNote.selectedItem.toString()
                mylessonView.findViewById<AutoCompleteTextView>(R.id.etNewLessonName).setText(lessonName)
                mylessonView.findViewById<AutoCompleteTextView>(R.id.etNewLessonName).setAdapter(textAdapter)
                var newLessonCredit=mylessonView.findViewById<Spinner>(R.id.spnNewLessonsCredits)
                var newLessonNote=mylessonView.findViewById<Spinner>(R.id.spnNewLessonNote)
                var deleteLessonBtn=mylessonView.findViewById<Button>(R.id.btnDeleteNewLesson)
                newLessonCredit.setSelection(getSpinnerIndexFromValue(viewBinding.spnCredit,lessonCredit))
                newLessonNote.setSelection(getSpinnerIndexFromValue(viewBinding.spnNote,lessonNote))
                deleteLessonBtn.setOnClickListener { viewBinding.srclLessonView.removeView(mylessonView)
                    if (viewBinding.srclLessonView.childCount==0){
                        viewBinding.btnCalcul.visibility=View.INVISIBLE
                    }else             viewBinding.btnCalcul.visibility=View.VISIBLE}

                viewBinding.srclLessonView.addView(mylessonView)
                viewBinding.btnCalcul.visibility=View.VISIBLE
                setEmptyLesson()
            }
        }
    }
    fun setEmptyLesson(){
        viewBinding.atLessonName.text.clear()
        viewBinding.spnCredit.setSelection(0)
        viewBinding.spnNote.setSelection(0)
    }
fun getSpinnerIndexFromValue(spinner:Spinner, value:String):Int{
    var index=0
    for (i in 0..spinner.count){
        if (spinner.getItemAtPosition(i).toString().equals(value)){

            index=i
            break
        }
    }
    return index
}
fun calculateMain(view: View){
    for (i in 0 until viewBinding.srclLessonView.childCount-1){
        var sinleRow=viewBinding.srclLessonView.getChildAt(i)
        var lessonName= sinleRow.findViewById<AutoCompleteTextView>(R.id.etNewLessonName)
        var lessonCredit= sinleRow.findViewById<Spinner>(R.id.spnNewLessonsCredits)
        var lessonNote= sinleRow.findViewById<Spinner>(R.id.spnNewLessonNote)
        var templateLessonModel = LessonModel(lessonName = lessonName.text.toString(), lessonCredit = (lessonCredit.selectedItemPosition+1).toString(), lessonNote = (lessonNote.selectedItemPosition+1).toString()
        )
    }
}

}
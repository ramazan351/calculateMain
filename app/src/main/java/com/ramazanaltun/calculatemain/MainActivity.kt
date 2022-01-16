package com.ramazanaltun.calculatemain

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.ramazanaltun.calculatemain.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val lessons= arrayOf("COMPUTER ENGINEERING ORIENTATION","INTRODUCTION TO COMPUTER ENG. CONCEPTS","C PROGRAMMING","LOGIC DESIGN","ALGORITHMS")
    private  var allLessons:ArrayList<LessonModel> = ArrayList()
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding= ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)
        viewBinding.root.setOnClickListener {
            //but it it doesn't work perfectly because there are another element on activity
            // we shoul put this even in another logic click listener ex: saveBtn
            // hide soft keyboard on rot layout click
            // it hide soft keyboard on edit text outside root layout click
            viewBinding.root.hideKeyboard()
            Log.d("clickevent","clicked")

            // remove focus from edit text
            viewBinding.atLessonName.clearFocus()
        }
        var textAdapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,lessons)
        viewBinding.atLessonName.setAdapter(textAdapter)
        if (viewBinding.srclLessonView.childCount==0){
            viewBinding.btnCalcul.visibility = View.INVISIBLE
        }else             viewBinding.btnCalcul.visibility=View.VISIBLE
        Log.d("ramazan",viewBinding.srclLessonView.childCount.toString())
        viewBinding.btnSave.setOnClickListener {
            viewBinding.root.hideKeyboard()
            Log.d("clickevent","clicked")

            // remove focus from edit text
            viewBinding.atLessonName.clearFocus()
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
        viewBinding.btnCalcul.setOnClickListener { calculateAvarage() }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
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
fun calculateAvarage(){
    var allNotesResult=0.0
    var allCreditResult=0.0
    for (i in 0 until viewBinding.srclLessonView.childCount){
        var sinleRow=viewBinding.srclLessonView.getChildAt(i)
        var lessonName= sinleRow.findViewById<AutoCompleteTextView>(R.id.etNewLessonName)
        var lessonCredit= sinleRow.findViewById<Spinner>(R.id.spnNewLessonsCredits)
        var lessonNote= sinleRow.findViewById<Spinner>(R.id.spnNewLessonNote)
        var templateLessonModel = LessonModel(lessonName = lessonName.text.toString(), lessonCredit = (lessonCredit.selectedItemPosition+1).toString(), lessonNote = (lessonNote.selectedItem).toString()
        )
        allLessons.add(templateLessonModel)
    }
    for (lesson in allLessons){
        Log.d("mylog","noote "+lesson.lessonNote.toString())
        allNotesResult+=parseNoteToValue(lesson.lessonNote)*(lesson.lessonCredit.toDouble())
        allCreditResult+=lesson.lessonCredit.toDouble()
//        Log.d("mylog",lesson.lessonCredit.toDouble().toString())
//        Log.d("mylog",allCreditResult.toString())
    }
    Log.d("mylog","credit "+allCreditResult.toString())
    Log.d("mylog","noote "+allNotesResult.toString())
    Toast.makeText(this, "Lessons' avarage |: "+(allNotesResult/allCreditResult),Toast.LENGTH_LONG).show()
    allLessons.clear()
}
fun parseNoteToValue(strNote:String):Double{
    var value=0.0
    when(strNote){
        "AA"->value=4.0
        "BA"->value=3.5
        "BB"->value=3.0
        "BC"->value=2.5
        "CB"->value=2.0
        "CC"->value=1.5
        "DC"->value=1.0
        "DD"->value=0.5
        "FF"->value=0.0
    }
    return value
}
}
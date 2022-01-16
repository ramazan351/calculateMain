package com.ramazanaltun.calculatemain

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        var myImageView=findViewById<ImageView>(R.id.myImageView)
        var myButton=findViewById<Button>(R.id.myButton)
        var buttonComesRight=AnimationUtils.loadAnimation(this,R.anim.buttoncomesright)
        var imageComesLeft=AnimationUtils.loadAnimation(this,R.anim.imagecomesleft)
        myButton.animation=buttonComesRight
        myImageView.animation=imageComesLeft


        /*reverse animation with click*/
        var buttonGoesBottom=AnimationUtils.loadAnimation(this,R.anim.buttongoesbottom)
        var imageGoesTop=AnimationUtils.loadAnimation(this,R.anim.imagegoestop)



        myButton.setOnClickListener {
            myButton.startAnimation(buttonGoesBottom)
            myImageView.startAnimation(imageGoesTop)
            object  : CountDownTimer(1000,1000){
                override fun onTick(millisUntilFinished: Long) {
                    //when countDownInterval tick then  it works
                    //we don't need it for this status

                }

                override fun onFinish() {
                    var intent=Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                }

            }.start()

        }

    }
}
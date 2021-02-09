package codes.yucellmustafa.pasciro

import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var mach = 0
    var scoreMe = 0
    var scorePc = 0
    var isNight = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    //-----------------------------------------------
    fun night(view: View){
        if (isNight){
            menu.setBackgroundColor(Color.TRANSPARENT)
            isNight = false
        }else{
            menu.setBackgroundColor(Color.parseColor("#FF131313"))
            isNight = true
        }
    }
    fun sound(music : Int){
        var breakk = MediaPlayer.create(this,music)
        breakk.start()
    }
    fun machSecim() : Int{
        mach = Random.nextInt(1,4)
        return mach
    }
    fun colorDis(){
        rock.setBackgroundColor(Color.TRANSPARENT)
        paper.setBackgroundColor(Color.TRANSPARENT)
        scissors.setBackgroundColor(Color.TRANSPARENT)
    }
    fun enabled(enable : Boolean){
        rock.isEnabled = enable
        paper.isEnabled = enable
        scissors.isEnabled = enable
        imgRestart.isEnabled = enable
    }
    fun restart(view: View){
        scoreMe = 0
        scorePc = 0
        txtStatus.text = "Status"
        txtStatus.setTextColor(Color.parseColor("#AAAAAA"))
        colorDis()
        imgMach.setImageResource(R.drawable.question)
        txtScoreMe.text = "Me : $scoreMe"
        txtScorePc.text = "$scorePc : Mach"
    }
    //-----------------------------------------------
    fun scoreFun (sonuc : Int){
        Handler().postDelayed({
            imgMach.isVisible = true
            gifMach.isVisible = false
            when(mach){
                1->imgMach.setImageResource(R.drawable.rock)
                2->imgMach.setImageResource(R.drawable.paper)
                3->imgMach.setImageResource(R.drawable.scissors)
            }
            when(sonuc){
                1->{scoreMe++
                    txtStatus.text = "Win"
                    txtStatus.setTextColor(Color.GREEN)}
                2->{ scorePc++
                    txtStatus.text = "Lose"
                    txtStatus.setTextColor(Color.RED)}
                3->{txtStatus.text = "Retry"
                    txtStatus.setTextColor(Color.parseColor("#AAAAAA"))}
            }
            txtScoreMe.text = "Me : $scoreMe"
            txtScorePc.text = "$scorePc : Mach"
            enabled(true)
            },2000)

        enabled(false)
        imgMach.isVisible = false
        gifMach.isVisible = true
    }
    //-----------------------------------------------
    fun rockSecim(view: View){
        colorDis()
        rock.setBackgroundColor(Color.BLUE)
        when(machSecim()){
            1->scoreFun(3)
            2->{scoreFun(2)
                Handler().postDelayed({
                    sound(R.raw.crumbling)
                },2000)}
            3->{scoreFun(1)
                Handler().postDelayed({
                    sound(R.raw.breakk)
                },2000)}
        }
    }
    fun paperSecim(view: View){
        colorDis()
        paper.setBackgroundColor(Color.BLUE)
        when(machSecim()){
            1->{scoreFun(1)
                Handler().postDelayed({
                    sound(R.raw.crumbling)
                },2000)}
            2->scoreFun(3)
            3->{scoreFun(2)
                Handler().postDelayed({
                    sound(R.raw.cut)
                },2000)}
        }
    }
    fun scissorsSecim(view: View){
        colorDis()
        scissors.setBackgroundColor(Color.BLUE)
        when(machSecim()){
            1->{scoreFun(2)
                Handler().postDelayed({
                    sound(R.raw.breakk)
                },2000)}
            2->{scoreFun(1)
                Handler().postDelayed({
                    sound(R.raw.cut)
                },2000)}
            3->scoreFun(3)
        }
    }
    //-----------------------------------------------
}
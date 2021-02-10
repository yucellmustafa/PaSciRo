package codes.yucellmustafa.pasciro

import android.content.SharedPreferences
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var mach = 0
    var scoreMe = 0
    var scorePc = 0
    var isNight = true
    lateinit var datas : SharedPreferences
    //-----------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        datas = this@MainActivity.getSharedPreferences("codes.yucellmustafa.pasciro", MODE_PRIVATE)

        isNight = datas.getBoolean("isNight",true)
        scoreMe = datas.getInt("scoreMe",0)
        scorePc = datas.getInt("scorePc",0)

        nightMode()
        print()
    }
    override fun onPause() {
        super.onPause()
        datas.edit().putInt("scoreMe",scoreMe).apply()
        datas.edit().putInt("scorePc",scorePc).apply()
        datas.edit().putBoolean("isNight",isNight).apply()
    }
    //-----------------------------------------------
    fun night(view: View){
        if (!isNight){
            Toast.makeText(this@MainActivity,"Night Mode",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@MainActivity,"Day Mode",Toast.LENGTH_SHORT).show()
        }
        isNight = !isNight
        nightMode()
    }
    fun nightMode(){
        if (isNight){
            menu.setBackgroundColor(Color.parseColor("#FF131313"))
        }else{
            menu.setBackgroundColor(Color.TRANSPARENT)
        }
    }
    //-----------------------------------------------
    fun machSecim() : Int{
        mach = Random.nextInt(1,4)
        return mach
    }
    fun print(){
        txtScoreMe.text = "Me : $scoreMe"
        txtScorePc.text = "$scorePc : Mach"
    }
    fun colorDis(color1 : Int,color2 : Int,color3 : Int){
        rock.setBackgroundColor(color1)
        paper.setBackgroundColor(color2)
        scissors.setBackgroundColor(color3)
    }
    fun sound(music : Int){
        Handler().postDelayed({
            var mp = MediaPlayer.create(this, music)
            mp.start()
            mp.setOnCompletionListener({ mp -> mp.release() })
        },2000)
    }
    fun enabled(enable : Boolean){
        rock.isEnabled = enable
        paper.isEnabled = enable
        scissors.isEnabled = enable
        imgRestart.isEnabled = enable
    }
    fun restart(view: View){
        var restartAlert = AlertDialog.Builder(this@MainActivity)

        restartAlert.setTitle("Restart")
        restartAlert.setMessage("Scores will be reset.\nAre you sure?")

        restartAlert.setPositiveButton("Yes") {dialog, which ->
            scoreMe = 0
            scorePc = 0
            txtStatus.text = "Status"
            txtStatus.setTextColor(Color.parseColor("#AAAAAA"))
            colorDis(Color.TRANSPARENT,Color.TRANSPARENT,Color.TRANSPARENT)
            gifMach.setImageResource(R.drawable.question)
            print()
        }
        restartAlert.setNegativeButton("No"){dialog,which-> }

        restartAlert.show()
    }
    //-----------------------------------------------
    fun scoreFun (sonuc : Int){
        Handler().postDelayed({
            when(mach){
                1->gifMach.setImageResource(R.drawable.rockk)
                2->gifMach.setImageResource(R.drawable.paperr)
                3->gifMach.setImageResource(R.drawable.scissorss)
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
            print()
            enabled(true)
            },2000)

        enabled(false)
        gifMach.setImageResource(R.drawable.anim)
    }
    //-----------------------------------------------
    fun rockSecim(view: View){
        colorDis(Color.BLUE,Color.TRANSPARENT,Color.TRANSPARENT)
        rock.setBackgroundColor(Color.BLUE)
        when(machSecim()){
            1->scoreFun(3)
            2->{scoreFun(2)
                sound(R.raw.crumbling)}
            3->{scoreFun(1)
                sound(R.raw.breakk)
                }
        }
    }
    fun paperSecim(view: View){
        colorDis(Color.TRANSPARENT,Color.BLUE,Color.TRANSPARENT)
        when(machSecim()){
            1->{scoreFun(1)
                sound(R.raw.crumbling)
                }
            2->scoreFun(3)
            3->{scoreFun(2)
                sound(R.raw.cut)
                }
        }
    }
    fun scissorsSecim(view: View){
        colorDis(Color.TRANSPARENT,Color.TRANSPARENT,Color.BLUE)
        when(machSecim()){
            1->{scoreFun(2)
                sound(R.raw.breakk)
                }
            2->{scoreFun(1)
                sound(R.raw.cut)
                }
            3->scoreFun(3)
        }
    }
    //-----------------------------------------------
}
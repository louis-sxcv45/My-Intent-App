package com.example.myintentapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvResult : TextView
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result->
        if (result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null){
            val selectedValue =
                result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
            tvResult.text = "Hasil : $selectedValue"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val moveActivity : Button = findViewById(R.id.btn_move_activity)
        moveActivity.setOnClickListener(this)


        val moveWithData : Button = findViewById(R.id.btn_move_activity_data)
        moveWithData.setOnClickListener(this)

        val moveWithObject : Button = findViewById(R.id.btn_move_activity_object)
        moveWithObject.setOnClickListener(this)

        val btnDialPhone : Button = findViewById(R.id.btn_dial_number)
        btnDialPhone.setOnClickListener(this)

        val btn_move_for_result : Button = findViewById(R.id.btn_move_for_result)
        btn_move_for_result.setOnClickListener(this)

        tvResult = findViewById(R.id.tv_result)
    }

    override fun onClick(view: View?){
        when(view?.id){
            R.id.btn_move_activity->{
                val moveIntent = Intent(this@MainActivity, MoveActivity::class.java)
                startActivity(moveIntent)
            }


            R.id.btn_move_activity_data->{
                val moveIntentData = Intent(this@MainActivity, MoveActivityWithData::class.java)
                moveIntentData.putExtra(MoveActivityWithData.EXTRA_NAME, "Louis")
                moveIntentData.putExtra(MoveActivityWithData.EXTRA_AGE, 20)
                startActivity(moveIntentData)
            }

            R.id.btn_move_activity_object->{
                val person = Person(
                    "Louis",
                    12,
                    "louis@gmail.com",
                    "Medan"
                )
                val moveIntentObject = Intent(this@MainActivity, MoveWithObjectActivity::class.java)
                moveIntentObject.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person)
                startActivity(moveIntentObject)
            }

            R.id.btn_dial_number->{
                val phone = "08114811802"
//                try {
                val dialPhone = Intent(Intent.ACTION_DIAL, Uri.parse("tel: $phone"))
                startActivity(dialPhone)
//                }catch (e: ActivityNotFoundException){
//                    val toast = Toast.makeText(this, "Error $e", Toast.LENGTH_SHORT)
//                    toast.show()
//                }
            }

            R.id.btn_move_for_result->{
                val dataResult = Intent(this@MainActivity, MoveForResultActivity::class.java)
                resultLauncher.launch(dataResult)
            }
        }
    }
}
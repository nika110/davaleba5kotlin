package davaleba5kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import ge.edu.btu.davaleba5kotlin.data.AppDatabase
import ge.edu.btu.davaleba5kotlin.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var range: EditText
    private lateinit var swim: EditText
    private lateinit var cals: EditText
    private lateinit var statistic: TextView
    private lateinit var save: Button
    private lateinit var Db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swim = findViewById(R.id.swim)
        cals = findViewById(R.id.Cals)
        statistic = findViewById(R.id.stats)
        save = findViewById(R.id.save)
        range = findViewById(R.id.Run)



        Db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "user_db"
        ).build()

        CoroutineScope(Dispatchers.IO).launch {
            setupData()
        }

        save.setOnClickListener {
            if (validatorFunc()) {
                Toast.makeText(this, "this field could not be empty", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    Db.userDao().insert(
                        User(
                            calorie = cals.toDouble(),
                            runRange = range.toDouble(),
                            swimRange = swim.toDouble()

                        )
                    )
                    to_clearInputs()
                    setupData()
                }
            }
        }
    }
    private fun validatorFunc(): Boolean {
        return  cals.text.toString().isEmpty()|| swim.text.toString()
            .isEmpty() || range.text.toString().isEmpty()
    }
    private fun to_clearInputs() {
        range.setText("")
        swim.setText("")
        cals.setText("")
    }

    private suspend fun setupData() {
        val userDao = Db.userDao()
        val totalDist = userDao.totalDistance()

        val mean_of_Run = userDao.avgOfRunRange().toString()
        val mean_of_Swim = userDao.avgOfSwimRange().toString()
        val mean_of_Cals = userDao.avgOfCalories().toString()

        withContext(Dispatchers.Main) {
            statistic.text = getString(
                R.string.average_stats,
                mean_of_Run,
                mean_of_Swim,
                mean_of_Cals,
                totalDist.toString()
            )
        }
    }





    private fun EditText.toDouble(): Double {
        return this.text.toString().toDouble()
    }
}

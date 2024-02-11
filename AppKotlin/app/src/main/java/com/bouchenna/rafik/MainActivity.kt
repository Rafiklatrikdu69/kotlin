package com.bouchenna.rafik
import android.content.Intent
import androidx.activity.R
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar
import java.util.regex.Pattern



class MainActivity : AppCompatActivity() {
    //Declaration des variables
    private lateinit var button: Button
    private lateinit var textNom: TextView
    private lateinit var textPrenom: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var textDate: EditText
    private lateinit var textTel: TextView
    private lateinit var textEmail: TextView
    private lateinit var checkBoxFavori: CheckBox
    private var date: String? = ""
    private var  EmailValidator:Email?=null
    lateinit var datePicker: DatePickerSpinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val message = intent.getStringExtra(EXTRA_MESSAGE)
        textPrenom = findViewById(R.id.prenom)
        textPrenom.text = message
        if (message != null) {
            Log.d("tag",message)
        }

        datePicker = DatePickerSpinner(this,true)

        textDate = findViewById(R.id.date)


        textDate.setOnClickListener {
            showDatePickerDialog()
        }
        val imageView = findViewById<ImageView>(R.id.imageView)
        val rg = findViewById<View>(R.id.groupe) as RadioGroup
        rg.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.radioButton4 -> {
                        imageView.setImageResource(R.drawable.homme)
                    }
                    R.id.radioButton5 -> {
                        imageView.setImageResource(R.drawable.femme)

                    }
                    R.id.radioButton6 -> {
                        imageView.setImageResource(R.drawable.autre)

                    }
                }
            }
        })
        button = findViewById(R.id.button2)
        button.setOnClickListener {
            textNom = findViewById(R.id.nom)

            textEmail = findViewById(R.id.email)
            textTel = findViewById(R.id.telephone)
            radioGroup = findViewById(R.id.groupe)
            checkBoxFavori = findViewById(R.id.checkBox)

            val selected = radioGroup.checkedRadioButtonId
            val errorNom = textNom.text.isEmpty()
            val errorPrenom = textPrenom.text.isEmpty()
            val errorDate = date?.isEmpty()
            val errorTel = textTel.text.isEmpty()
            val errorEmail = textEmail.text.isEmpty()

            val str1 = if (errorTel) "Aucune" else textTel.text.toString()
            val str2 = if (errorDate == true) "Aucune" else date

            val checked = if (checkBoxFavori.isChecked) "Ajouter au favori " else "Non"

            val selectRadioButton = if (selected > 0) {
                val radioButton = findViewById<RadioButton>(selected)
                radioButton.text.toString()

            } else {
                "Aucun"
            }

            var snack =
                Snackbar.make(it, "", Snackbar.LENGTH_LONG)

            if (errorNom) textNom.error = "Remplir le champ nom"
            if (errorPrenom) textPrenom.error = "Remplir le champ prenom"
            if (errorEmail) textEmail.error = "Remplir le champ email"
            if(!checkEmail(textEmail.text.toString())) {
                textEmail.error = "Email Invalide"
                snack =  Snackbar.make(it, "Email Invalide !", Snackbar.LENGTH_LONG)
                snack.show()
            }
            if (errorNom || errorPrenom || errorEmail ) {
                snack=   Snackbar.make(it, "Veuillez remplir les champs obligatoires*", Snackbar.LENGTH_LONG)

                snack.show()
            } else if(checkEmail(textEmail.text.toString())){
                val text = "Informations client"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(this, text, duration)
                toast.show()
                val builder = AlertDialog.Builder(this)
                builder
                    .setMessage("Contact")
                    .setTitle("Informations du Contact")
                    .setMessage(
                        ("nom : ${textNom.text.toString()}\n" +
                                "Prénom : ${textPrenom.text.toString()}\n" +
                                "Sexe : $selectRadioButton\n" +
                                "téléphone : $str1\n" +
                                "Date : $str2\n" +
                                "Email : ${textEmail.text.toString()}\n"
                                + "Favori : $checked")
                    )
                    .setPositiveButton("Close") { dialog, which ->
                        val text = "Fermeture..."
                        val toast = Toast.makeText(this, text, duration)
                        toast.show()
                    }
                val dialog = builder.create()
                dialog.show()
            }
            Log.d("MainActivity", "click !")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        datePicker.showDialog(d, m, y, object : DatePickerSpinner.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                date = "${dayStr}-${monthStr}-${year}"
                textDate.setText(date, TextView.BufferType.EDITABLE);
            }
        })
    }
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    private fun checkEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }
}

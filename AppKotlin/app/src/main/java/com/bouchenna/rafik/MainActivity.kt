package com.bouchenna.rafik

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var textNom: TextView
    private lateinit var textPrenom: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton: RadioButton
    private lateinit var textDate: TextView
    private lateinit var textTel: TextView
    private lateinit var textEmail: TextView
    private lateinit var checkBoxFavori: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {

            textNom = findViewById(R.id.nom)
            textPrenom = findViewById(R.id.prenom)
            textDate = findViewById(R.id.date)
            textEmail = findViewById(R.id.email)
            textTel = findViewById(R.id.telephone)
            radioGroup = findViewById(R.id.groupe);
            checkBoxFavori = findViewById(R.id.checkBox)
            var selected = radioGroup.getCheckedRadioButtonId()
            var errorNom = textNom.text.length == 0;
            var errorPrenom = textPrenom.text.length == 0;
            var errorDate = textDate.text.length == 0;
            var errorTel = textTel.text.length == 0;
            var errorEmail = textEmail.text.length == 0;
            var str1 = if (errorTel) "Aucune" else textTel.text.toString()//erreur a regler
            var str2 = if (errorDate) "Aucune" else textDate.text.toString()
            var checked = if (checkBoxFavori.isChecked()) "Ajouter au favori " else "Non"
            val selectRadioButton = if (selected > 0) {
                val radioButton = findViewById<View>(selected)
                if (radioButton is TextView) {
                    radioButton.text.toString()
                } else {
                    "Aucun"
                }
            } else {
                "Aucun"
            }

            val snack =
                Snackbar.make(it, "Veuillez remplir les champs obligatoires*", Snackbar.LENGTH_LONG)

            if (errorNom) {
                textNom.error = "Remplir le champ nom ";
            }
            if (errorPrenom) {
                textPrenom.error = "Remplir le champ prenom ";
            }
            if (errorEmail) {
                textEmail.error = "Remplir le champ email ";
            }
            if (errorNom || errorPrenom || errorEmail) {
                snack.show()
            } else {
                val text = "Contact Créer"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(this, text, duration)
                toast.show()
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder
                    .setMessage("Contact")
                    .setTitle("Informations du Contact")
                    .setMessage(
                        ("nom : ${textNom.text.toString()}\n" +
                                "Prénom : ${textPrenom.text.toString()}\n" +
                                "Sexe : $selectRadioButton\n" +
                                "téléphone : ${str1}\n" +
                                "Date : ${str2}\n" +
                                "Email : ${textEmail.text.toString()}\n"
                                + "Favori : $checked")
                    )
                    .setPositiveButton("Close") { dialog, which ->
                        val text = "Fermeture..."
                        val toast = Toast.makeText(this, text, duration)
                        toast.show()
                    }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            Log.d("MainActivity", "click !")
        }
    }

    override fun onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart")
    }

}
package com.kong.currencyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kong.currencyapp.data.Status
import com.kong.currencyapp.databinding.ActivityMainBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var selectedCountry = R.id.krw
    private var currentCurrency: Double? = 0.0
    private val baseCountry = "USD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.currencyData.observe(this, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    val currencyResponse = resource.data
                    //binding.timeResult.text = getTime(1682684223L) ?? 
                    binding.timeResult.text = getTime(currencyResponse?.timestamp)

                    when (selectedCountry) {
                        R.id.krw -> {
                            currentCurrency =
                                currencyResponse?.quotes?.get("${baseCountry}KRW")?.toDouble()
                        }

                        R.id.jpy -> {
                            currentCurrency =
                                currencyResponse?.quotes?.get("${baseCountry}JPY")?.toDouble()
                        }

                        R.id.php -> {
                            currentCurrency =
                                currencyResponse?.quotes?.get("${baseCountry}PHP")?.toDouble()
                        }
                    }
                    binding.currencyResult.text = getCurrencyFormat(currentCurrency)

                    // 화면에 표시할 데이터를 처리하는 로직
                }

                Status.ERROR -> {
                    val message = resource.message
                    // 에러 처리 로직
                    Toast.makeText(
                        applicationContext,
                        message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Status.LOADING -> {
                    // 로딩 중 처리 로직
                    binding.progressCircular.visibility = View.VISIBLE
                }
            }
            binding.progressCircular.visibility = View.GONE
        })

        //수취 국가 선택
        binding.recipientCountryResult.setOnClickListener { v ->
            showRecipientCountryList(v)
        }

        binding.calculateBtn.setOnClickListener {
            val inputNum =
                if (binding.inputSendingResult.text.isNotBlank()) {
                    binding.inputSendingResult.text.toString().toInt()
                } else {
                    0
                }
            if (inputNum in 1..9999) {
                val total = binding.inputSendingResult.text.toString().toInt() * currentCurrency!!
                binding.resultText.text =
                    getString(R.string.calculate_result, getCurrencyFormat(total),getCountry(selectedCountry))
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.calculate_warn),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.getCurrencyData(baseCountry, "KRW")
    }

    private fun showRecipientCountryList(v: View) {
        val menu = PopupMenu(this, v)
        menu.inflate(R.menu.main_menu)
        menu.setOnMenuItemClickListener { menuItem ->
            selectedCountry = menuItem.itemId
            when (menuItem.itemId) {
                R.id.krw -> {
                    binding.recipientCountryResult.text = menuItem.title
                    viewModel.getCurrencyData(baseCountry, "KRW")
                    true
                }

                R.id.jpy -> {
                    binding.recipientCountryResult.text = menuItem.title
                    viewModel.getCurrencyData(baseCountry, "JPY")
                    true
                }

                R.id.php -> {
                    binding.recipientCountryResult.text = menuItem.title
                    viewModel.getCurrencyData(baseCountry, "PHP")
                    true
                }

                else -> false
            }
        }
        menu.show()
    }

    private fun getTime(time: Long?): String {
        if (time != null) {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val date = Date(time)
            return format.format(date)
        }
        return ""
    }

    private fun getCurrencyFormat(currency: Double?): String {
        val decimalFormat = DecimalFormat("#,##0.00")
        return decimalFormat.format(currency)

    }

    private fun getCountry(country_id: Int) = when (country_id) {
        R.id.krw -> {
            "KRW"
        }

        R.id.jpy -> {
            "JPY"
        }

        R.id.php -> {
            "PHP"
        }

        else -> {
            ""
        }
    }

}
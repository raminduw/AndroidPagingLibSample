package raminduweeraman.com.androidpaging.lib.sample.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import raminduweeraman.com.androidpaging.lib.sample.R
import raminduweeraman.com.androidpaging.lib.sample.SEARCH_BUILD_DATES
import raminduweeraman.com.androidpaging.lib.sample.SEARCH_MAIN_TYPES
import raminduweeraman.com.androidpaging.lib.sample.SEARCH_MANUFACTURE
import raminduweeraman.com.androidpaging.lib.sample.api.SearchParams
import raminduweeraman.com.androidpaging.lib.sample.model.ListItem

class MainActivity : AppCompatActivity(), BaseFragment.OnFragmentInteractionListener {

    var searchParams: SearchParams =
        SearchParams(SEARCH_MANUFACTURE, ListItem("", ""), ListItem("", ""), ListItem("", ""))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startManufactureSearchFragment()
    }

    private fun startManufactureSearchFragment() {
        searchParams = SearchParams(SEARCH_MANUFACTURE, ListItem("", ""), ListItem("", ""), ListItem("", ""));
        val fragment = ManufactureSearchFragment.newInstance()
        fragment.searchParams = searchParams
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun startMainTypeSearchFragment() {
        val fragment = MainTypeSearchFragment.newInstance()
        fragment.searchParams = searchParams
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun startBuiltDateSearchFragment() {
        val fragment = BuiltDateSearchFragment.newInstance()
        fragment.searchParams = searchParams
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }


    override fun onFragmentInteraction(key: String, value: String, type: Int) {
        if (type == SEARCH_MANUFACTURE) {
            searchParams.searchType = SEARCH_MAIN_TYPES
            searchParams.manufacture = ListItem(key, value)
            startMainTypeSearchFragment()
        } else if (type == SEARCH_MAIN_TYPES) {
            searchParams.searchType = SEARCH_BUILD_DATES
            searchParams.mainType = ListItem(key, value)
            startBuiltDateSearchFragment()
        } else {
            searchParams.builtDate = ListItem(key, value)
            showSearchCarAlert()
        }
    }

    private fun showSearchCarAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Search Your Car")
        builder.setMessage(
            "Manufacture : " + searchParams.manufacture.value + "\n" + "Main Type : "
                    + searchParams.mainType.value + "\n" + "Built Year : " + searchParams.builtDate.value
        )

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(
                applicationContext,
                "Start your car search", Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }

        builder.setNeutralButton("Search Again") { dialog, which ->
            startManufactureSearchFragment()
        }
        builder.show()
    }


}

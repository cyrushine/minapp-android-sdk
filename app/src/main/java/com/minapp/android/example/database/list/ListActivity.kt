package com.minapp.android.example.database.list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minapp.android.example.R
import com.minapp.android.example.base.BaseActivity
import com.minapp.android.example.database.edit.EditActivity
import com.minapp.android.example.database.list.rv.HorseAdapter
import kotlinx.android.synthetic.main.activity_db.*

class ListActivity : BaseActivity() {

    private var viewModel: ListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)
        initPagedList()
    }

    fun initPagedList() {
        viewModel = provideViewModel(ListViewModel::class.java).apply {
            refresh.setOnRefreshListener { onRefresh() }

            val adapter = HorseAdapter()
            rv.layoutManager = LinearLayoutManager(this@ListActivity, RecyclerView.VERTICAL, false)
            rv.adapter = adapter
            horses.observe(this@ListActivity, Observer {
                adapter.submitList(it)
            })
            editAction.observe(this@ListActivity, Observer {
                EditActivity.editEntity(this@ListActivity, EDIT, it)
            })
            addAction.observe(this@ListActivity, Observer {
                if (it == true) {
                    EditActivity.createEntity(this@ListActivity, ADD)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.db_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.add -> {
                viewModel?.onAdd()
            }

            R.id.edit -> {
                viewModel?.onEdit()
            }

            R.id.delete -> {
                viewModel?.onDelete()
            }

            R.id.filter -> {
                viewModel?.onFilter()
            }

            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            EDIT, ADD -> {
                if (resultCode == RESULT_OK) {
                    viewModel?.onRefresh()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {

        const val EDIT = 9
        const val ADD = 10
    }
}
